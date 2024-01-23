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
package org.eclipse.sirius.components.c4.starter.view.diagrams.commons.nodedescriptions;

import fr.obeo.dsl.C4.model.api.SoftwareSystem;

import java.util.Objects;

import org.eclipse.sirius.components.c4.starter.view.commons.C4Colors.C4Color;
import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;

/**
 * {@link INodeDescriptionProvider} implementation for {@link SoftwareSystem}.
 *
 * @author flatombe
 */
public class SoftwareSystemNodeDescriptionProvider implements INodeDescriptionProvider {

    public static final String DESCRIPTION_NAME = "C4_Node_SoftwareSystem";

    private final IColorProvider colorProvider;

    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    public SoftwareSystemNodeDescriptionProvider(final IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        return this.diagramBuilderHelper.newNodeDescription().name(DESCRIPTION_NAME).domainType("C4::SoftwareSystem").semanticCandidatesExpression("aql:self.eContainer().softwareSystem")
                .childrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()).labelExpression("aql:self.getLabel()").defaultHeightExpression("85")
                .defaultWidthExpression("125").userResizable(true).keepAspectRatio(false).synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .style(this.diagramBuilderHelper.newRectangularNodeStyleDescription().color(this.colorProvider.getColor(C4Color.C4_SoftwareSystem.getName()))
                        .borderColor(this.colorProvider.getColor(C4Color.Border.getName())).labelColor(this.colorProvider.getColor(C4Color.Text_Primary.getName())).bold(false).borderRadius(3)
                        .withHeader(false).displayHeaderSeparator(false).build())
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        cache.getNodeDescription(DESCRIPTION_NAME).ifPresent(nodeDescription -> {
            diagramDescription.getNodeDescriptions().add(nodeDescription);
            // cache.getNodeDescription(DESCRIPTION_NAME).ifPresent(processorNodeDescription ->
            // nodeDescription.setPalette(this.createNodePalette(processorNodeDescription)));
        });
    }

    // private NodePalette createNodePalette(NodeDescription processorNodeDescription) {
    // return
    // this.diagramBuilderHelper.newNodePalette().deleteTool(this.flowViewBuilder.createDeleteTool()).labelEditTool(this.flowViewBuilder.createLabelEditTool())
    // .edgeTools(this.flowViewBuilder.createEdgeToProcessorTool(processorNodeDescription)).build();
    // }

}
