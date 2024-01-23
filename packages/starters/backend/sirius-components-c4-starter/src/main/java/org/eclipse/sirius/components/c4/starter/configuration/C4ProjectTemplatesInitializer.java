/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.components.c4.starter.configuration;

import fr.obeo.dsl.C4.model.api.C4Model;
import fr.obeo.dsl.C4.model.api.SystemContext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.c4.starter.utils.C4Models;
import org.eclipse.sirius.components.c4.starter.utils.StereotypeBuilder;
import org.eclipse.sirius.components.c4.starter.view.diagrams.systemcontext.C4SystemContextDiagramDescriptionProvider;
import org.eclipse.sirius.components.collaborative.api.IRepresentationPersistenceService;
import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramCreationService;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.persistence.repositories.IProjectRepository;
import org.eclipse.sirius.web.services.api.id.IDParser;
import org.eclipse.sirius.web.services.api.projects.IProjectTemplateInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * {@link Configuration} that implements {@link IProjectTemplateInitializer} to provide a template of a C4 Project with
 * a model with some elements and a System Context diagram.
 *
 * @author flatombe
 */
@Configuration
public class C4ProjectTemplatesInitializer implements IProjectTemplateInitializer {

    private static final String DOCUMENT_TITLE = "C4 Sample Document";

    private final Logger logger = LoggerFactory.getLogger(C4ProjectTemplatesInitializer.class);

    private final IProjectRepository projectRepository;

    private final IDocumentRepository documentRepository;

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IDiagramCreationService diagramCreationService;

    private final IRepresentationPersistenceService representationPersistenceService;

    private final StereotypeBuilder stereotypeBuilder;

    public C4ProjectTemplatesInitializer(IProjectRepository projectRepository, IDocumentRepository documentRepository, IRepresentationDescriptionSearchService representationDescriptionSearchService,
            IDiagramCreationService diagramCreationService, IRepresentationPersistenceService representationPersistenceService, MeterRegistry meterRegistry) {
        this.projectRepository = Objects.requireNonNull(projectRepository);
        this.documentRepository = Objects.requireNonNull(documentRepository);
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.diagramCreationService = Objects.requireNonNull(diagramCreationService);
        this.representationPersistenceService = Objects.requireNonNull(representationPersistenceService);
        this.stereotypeBuilder = new StereotypeBuilder(this.getClass().getSimpleName(), meterRegistry);
    }

    @Override
    public boolean canHandle(String templateId) {
        return C4ProjectTemplatesProvider.ID_TEMPLATE_C4_SAMPLE_MODEL.equals(templateId) || C4ProjectTemplatesProvider.ID_TEMPLATE_C4_IBS_MODEL.equals(templateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(String templateId, IEditingContext editingContext) {
        final Optional<RepresentationMetadata> mainRepresentationMetadata;
        switch (templateId) {
            case C4ProjectTemplatesProvider.ID_TEMPLATE_C4_SAMPLE_MODEL:
                mainRepresentationMetadata = this.initializeC4Project(editingContext, this::getC4SampleModelContents);
                break;
            case C4ProjectTemplatesProvider.ID_TEMPLATE_C4_IBS_MODEL:
                mainRepresentationMetadata = this.initializeC4Project(editingContext, this::getC4InternetBankingSystemModelContents);
                break;
            default:
                mainRepresentationMetadata = Optional.empty();
        }
        return mainRepresentationMetadata;
    }

    private Optional<RepresentationMetadata> initializeC4Project(IEditingContext editingContext, final Supplier<String> documentEntityContentSupplier) {
        Optional<RepresentationMetadata> result = Optional.empty();
        Optional<AdapterFactoryEditingDomain> optionalEditingDomain = Optional.of(editingContext).filter(IEMFEditingContext.class::isInstance).map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain);
        Optional<UUID> editingContextUUID = new IDParser().parse(editingContext.getId());
        if (optionalEditingDomain.isPresent() && editingContextUUID.isPresent()) {
            AdapterFactoryEditingDomain adapterFactoryEditingDomain = optionalEditingDomain.get();
            ResourceSet resourceSet = adapterFactoryEditingDomain.getResourceSet();

            var optionalDocumentEntity = this.projectRepository.findById(editingContextUUID.get()).map(projectEntity -> {
                DocumentEntity documentEntity = new DocumentEntity();
                documentEntity.setProject(projectEntity);
                documentEntity.setName(DOCUMENT_TITLE);
                documentEntity.setContent(documentEntityContentSupplier.get());

                documentEntity = this.documentRepository.save(documentEntity);
                return documentEntity;
            });

            if (optionalDocumentEntity.isPresent()) {
                DocumentEntity documentEntity = optionalDocumentEntity.get();
                JsonResource resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());

                try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
                    resource.load(inputStream, null);

                    final Optional<DiagramDescription> maybeSystemContextDiagramDescription = this.findDiagramDescription(editingContext,
                            C4SystemContextDiagramDescriptionProvider.DIAGRAM_DESCRIPTION_NAME);
                    if (maybeSystemContextDiagramDescription.isPresent()) {
                        final DiagramDescription systemContextDiagramDescription = maybeSystemContextDiagramDescription.get();
                        final List<C4Model> c4models = resource.getContents().stream().filter(C4Model.class::isInstance).map(C4Model.class::cast).toList();
                        final List<SystemContext> systemContexts = c4models.stream().map(C4Model::getSystemContext).filter(Objects::nonNull).toList();
                        final List<Diagram> systemContextDiagrams = systemContexts.stream().map(systemContext -> {
                            Diagram systemContextDiagram = this.diagramCreationService.create("System Context Diagram", systemContext, systemContextDiagramDescription,
                                    editingContext);
                            this.representationPersistenceService.save(editingContext, systemContextDiagram);
                            return systemContextDiagram;
                        }).toList();
                        // Arbitrarily use the first one.
                        result = systemContextDiagrams.stream().findFirst().map(firstSystemContextDiagram -> new RepresentationMetadata(firstSystemContextDiagram.getId(),
                                firstSystemContextDiagram.getKind(), firstSystemContextDiagram.getLabel(), firstSystemContextDiagram.getDescriptionId()));
                    }
                } catch (IOException exception) {
                    this.logger.warn(exception.getMessage(), exception);
                }

                resource.eAdapters().add(new ResourceMetadataAdapter(DOCUMENT_TITLE));

                resourceSet.getResources().add(resource);
            }
        }
        return result;
    }

    private String getC4SampleModelContents() {
        return this.stereotypeBuilder.getStereotypeBody(List.of(C4Models.createC4ModelSample()));
    }

    private String getC4InternetBankingSystemModelContents() {
        return this.stereotypeBuilder.getStereotypeBody(List.of(C4Models.createC4ModelForInternetBankingSystem()));
    }

    private Optional<DiagramDescription> findDiagramDescription(IEditingContext editingContext, String label) {
        return this.representationDescriptionSearchService.findAll(editingContext).values().stream().filter(DiagramDescription.class::isInstance).map(DiagramDescription.class::cast)
                .filter(diagramDescription -> Objects.equals(label, diagramDescription.getLabel())).findFirst();
    }

}
