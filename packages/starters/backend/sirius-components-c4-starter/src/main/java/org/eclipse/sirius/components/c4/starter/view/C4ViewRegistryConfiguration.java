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
package org.eclipse.sirius.components.c4.starter.view;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.c4.starter.view.commons.C4Colors;
import org.eclipse.sirius.components.c4.starter.view.diagrams.systemcontext.C4SystemContextDiagramDescriptionProvider;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IRepresentationDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilder;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.emf.IViewConverter;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.services.api.representations.IInMemoryViewRegistry;
import org.springframework.context.annotation.Configuration;

/**
 * Register the Flow diagram in the application.
 *
 * @author frouene
 */
@Configuration
public class C4ViewRegistryConfiguration implements IRepresentationDescriptionRegistryConfigurer {

    private static final String C4_VIEW_DIAGRAM_ID = "C4_View_Diagram";

    private final IViewConverter viewConverter;

    private final EPackage.Registry ePackagesRegistry;

    private final IInMemoryViewRegistry inMemoryViewRegistry;

    public C4ViewRegistryConfiguration(IViewConverter viewConverter, EPackage.Registry ePackagesRegistry, IInMemoryViewRegistry inMemoryViewRegistry) {
        this.viewConverter = Objects.requireNonNull(viewConverter);
        this.ePackagesRegistry = Objects.requireNonNull(ePackagesRegistry);
        this.inMemoryViewRegistry = Objects.requireNonNull(inMemoryViewRegistry);
    }

    @Override
    public void addRepresentationDescriptions(IRepresentationDescriptionRegistry registry) {
        ViewBuilder viewBuilder = new ViewBuilder();
        View view = viewBuilder.build();
        IColorProvider colorProvider = new C4Colors.C4ColorProvider(view);

        view.getColorPalettes().add(this.createColorPaletteWith(C4Colors.createAllColors()));

        view.getDescriptions().add(new C4SystemContextDiagramDescriptionProvider().create(colorProvider));

        // Add an ID to all view elements
        view.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        // All programmatic Views need to be stored in a Resource and registered in IInMemoryViewRegistry
        String resourcePath = UUID.nameUUIDFromBytes(C4_VIEW_DIAGRAM_ID.getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter(C4_VIEW_DIAGRAM_ID));
        resource.getContents().add(view);
        this.inMemoryViewRegistry.register(view);

        // Convert org.eclipse.sirius.components.view.RepresentationDescription to
        // org.eclipse.sirius.components.representations.IRepresentationDescription
        List<EPackage> staticEPackages = this.ePackagesRegistry.values().stream().filter(EPackage.class::isInstance).map(EPackage.class::cast).toList();
        var representationDescriptions = this.viewConverter.convert(List.of(view), staticEPackages);

        // Register org.eclipse.sirius.components.representations.IRepresentationDescription
        representationDescriptions.forEach(registry::add);
    }

    private ColorPalette createColorPaletteWith(final List<? extends UserColor> userColors) {
        var colorPalette = ViewFactory.eINSTANCE.createColorPalette();

        userColors.forEach(colorPalette.getColors()::add);

        return colorPalette;
    }
}
