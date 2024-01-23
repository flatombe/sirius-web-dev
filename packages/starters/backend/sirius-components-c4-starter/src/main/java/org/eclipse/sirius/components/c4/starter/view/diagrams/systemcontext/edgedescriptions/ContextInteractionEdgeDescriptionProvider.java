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
package org.eclipse.sirius.components.c4.starter.view.diagrams.systemcontext.edgedescriptions;

import fr.obeo.dsl.C4.model.api.ContextInteraction;

import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.sirius.components.c4.starter.view.commons.C4Colors;
import org.eclipse.sirius.components.c4.starter.view.diagrams.commons.nodedescriptions.ExternalPersonNodeDescriptionProvider;
import org.eclipse.sirius.components.c4.starter.view.diagrams.commons.nodedescriptions.ExternalSystemNodeDescriptionProvider;
import org.eclipse.sirius.components.c4.starter.view.diagrams.commons.nodedescriptions.SoftwareSystemNodeDescriptionProvider;
import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IEdgeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;

/**
 * {@link IEdgeDescriptionProvider} implementation for {@link ContextInteraction}.
 *
 * @author flatombe
 */
public class ContextInteractionEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    private static final String DESCRIPTION_NAME = "C4_Edge_ContextInteraction";

    private final IColorProvider colorProvider;

    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    public ContextInteractionEdgeDescriptionProvider(final IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        // 'self' is the 'SystemContext'.
        return this.diagramBuilderHelper.newEdgeDescription().name(DESCRIPTION_NAME).domainType("C4::ContextInteraction").semanticCandidatesExpression("aql:self.contextInteractions")
                .targetNodesExpression("feature:target").sourceNodesExpression("feature:source").isDomainBasedEdge(true).labelExpression("aql:self.description").style(this.diagramBuilderHelper
                        .newEdgeStyle().lineStyle(LineStyle.DASH).color(this.colorProvider.getColor(C4Colors.C4Color.C4_ContextInteraction.getName())).targetArrowStyle(ArrowStyle.INPUT_ARROW).build())
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        final EdgeDescription contextInteractionEdgeDescription = cache.getEdgeDescription(DESCRIPTION_NAME)
                .orElseThrow(() -> new IllegalStateException(String.format("Could not find EdgeDescription with name '%s'", DESCRIPTION_NAME)));
        final NodeDescription externalPersonNodeDescription = getNodeDescriptionOrThrow(cache, ExternalPersonNodeDescriptionProvider.DESCRIPTION_NAME);
        final NodeDescription externalSystemNodeDescription = getNodeDescriptionOrThrow(cache, ExternalSystemNodeDescriptionProvider.DESCRIPTION_NAME);
        final NodeDescription softwareSystemNodeDescription = getNodeDescriptionOrThrow(cache, SoftwareSystemNodeDescriptionProvider.DESCRIPTION_NAME);

        // Our edge goes to and from any of the SoftwareSystem/ExternalPerson/ExternalSystem nodes.
        Stream.of(externalPersonNodeDescription, externalSystemNodeDescription, softwareSystemNodeDescription).forEach(nodeDescription -> {
            contextInteractionEdgeDescription.getSourceNodeDescriptions().add(nodeDescription);
            contextInteractionEdgeDescription.getTargetNodeDescriptions().add(nodeDescription);
        });

        // Don't forget to contribute the edge to the diagram.
        diagramDescription.getEdgeDescriptions().add(contextInteractionEdgeDescription);
    }

    private static NodeDescription getNodeDescriptionOrThrow(final IViewDiagramElementFinder cache, final String nodeDescriptionName) {
        return cache.getNodeDescription(nodeDescriptionName).orElseThrow(() -> new IllegalStateException(String.format("Could not find NodeDescription with name '%s'", nodeDescriptionName)));
    }

}
