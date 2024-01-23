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
package org.eclipse.sirius.components.c4.starter.view.diagrams.systemcontext;

import java.util.List;

import org.eclipse.sirius.components.c4.starter.view.commons.ViewDiagramElementFinder;
import org.eclipse.sirius.components.c4.starter.view.diagrams.commons.nodedescriptions.ExternalPersonNodeDescriptionProvider;
import org.eclipse.sirius.components.c4.starter.view.diagrams.commons.nodedescriptions.ExternalSystemNodeDescriptionProvider;
import org.eclipse.sirius.components.c4.starter.view.diagrams.commons.nodedescriptions.SoftwareSystemNodeDescriptionProvider;
import org.eclipse.sirius.components.c4.starter.view.diagrams.systemcontext.edgedescriptions.ContextInteractionEdgeDescriptionProvider;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IDiagramElementDescriptionProvider;
import org.eclipse.sirius.components.view.builder.providers.IRepresentationDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;

/**
 * Used to create Flow view.
 *
 * @author frouene
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class C4SystemContextDiagramDescriptionProvider implements IRepresentationDescriptionProvider {

    public static final String DIAGRAM_DESCRIPTION_NAME = "C4_Diagram_SystemContext";

    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    // private final ViewBuilders viewBuilderHelper = new ViewBuilders();

    @Override
    public RepresentationDescription create(IColorProvider colorProvider) {
        final DiagramDescription systemContextDiagramDescription = this.diagramBuilderHelper.newDiagramDescription().autoLayout(true).domainType("C4::SystemContext").name(DIAGRAM_DESCRIPTION_NAME)
                .titleExpression("aql:'[1.System Context]' + self.eContainer().name").build();

        final ViewDiagramElementFinder cache = new ViewDiagramElementFinder();
        final List<IDiagramElementDescriptionProvider<?>> diagramElementDescriptionProviders = List.of(new SoftwareSystemNodeDescriptionProvider(colorProvider),
                new ExternalSystemNodeDescriptionProvider(colorProvider), new ExternalPersonNodeDescriptionProvider(colorProvider), new ContextInteractionEdgeDescriptionProvider(colorProvider));

        diagramElementDescriptionProviders.stream().map(IDiagramElementDescriptionProvider::create).forEach(cache::put);

        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> diagramElementDescriptionProvider.link(systemContextDiagramDescription, cache));

        final DiagramPalette diagramPalette = null; // this.createDiagramPalette(cache);
        systemContextDiagramDescription.setPalette(diagramPalette);

        return systemContextDiagramDescription;
    }

    // private DiagramPalette createDiagramPalette(IViewDiagramElementFinder cache) {
    // return this.diagramBuilderHelper.newDiagramPalette().toolSections(this.createDiagramToolSection(cache)).build();
    // }

    // private DiagramToolSection createDiagramToolSection(IViewDiagramElementFinder cache) {
    // return this.diagramBuilderHelper.newDiagramToolSection().name("Creation
    // Tools").nodeTools(this.createNodeToolCreateCompositeProcessor(), this.createNodeToolCreateDataSource()).build();
    // }

    // private NodeTool createNodeToolCreateCompositeProcessor() {
    //
    // var setValueStatus = this.viewBuilderHelper.newSetValue().featureName("status").valueExpression("active");
    // var setValueName = this.viewBuilderHelper.newSetValue().featureName("name")
    // .valueExpression("aql:'CompositeProcessor' +
    // self.eContainer().eContents()->filter(flow::CompositeProcessor)->size()");
    //
    // var changeContextNewInstance =
    // this.viewBuilderHelper.newChangeContext().expression("aql:newInstance").children(setValueStatus.build(),
    // setValueName.build());
    //
    // var createInstance =
    // this.viewBuilderHelper.newCreateInstance().typeName("flow::CompositeProcessor").referenceName("elements").variableName("newInstance")
    // .children(changeContextNewInstance.build());
    //
    // return this.diagramBuilderHelper.newNodeTool().name("Composite
    // Processor").iconURLsExpression("/icons/full/obj16/System.gif").body(createInstance.build()).build();
    // }

    // private NodeTool createNodeToolCreateDataSource() {
    //
    // var setValueVolume = this.viewBuilderHelper.newSetValue().featureName("volume").valueExpression("6");
    // var setValueStatus = this.viewBuilderHelper.newSetValue().featureName("status").valueExpression("active");
    // var setValueName = this.viewBuilderHelper.newSetValue().featureName("name").valueExpression("aql:'DataSource' +
    // self.eContainer().eContents()->filter(flow::DataSource)->size()");
    //
    // var changeContextNewInstance =
    // this.viewBuilderHelper.newChangeContext().expression("aql:newInstance").children(setValueVolume.build(),
    // setValueStatus.build(), setValueName.build());
    //
    // var createInstance =
    // this.viewBuilderHelper.newCreateInstance().typeName("flow::DataSource").referenceName("elements").variableName("newInstance").children(changeContextNewInstance.build());
    //
    // return this.diagramBuilderHelper.newNodeTool().name("Data
    // Source").iconURLsExpression("/icons/full/obj16/DataSource_active.gif").body(createInstance.build()).build();
    // }

}
