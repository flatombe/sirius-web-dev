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

import fr.obeo.dsl.C4.model.api.ExternalPerson;

import java.util.Objects;

import org.eclipse.sirius.components.c4.starter.view.commons.C4Images.C4Image;
import org.eclipse.sirius.components.c4.starter.view.commons.C4ViewBuilder;
import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;

/**
 * {@link INodeDescriptionProvider} implementation for {@link ExternalPerson}.
 *
 * @author flatombe
 */
public class ExternalPersonNodeDescriptionProvider implements INodeDescriptionProvider {
    public static final String DESCRIPTION_NAME = "C4_Node_ExternalPerson";

    private final IColorProvider colorProvider;

    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    private final C4ViewBuilder c4ViewBuilder = new C4ViewBuilder();

    public ExternalPersonNodeDescriptionProvider(final IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        // 'self' is the 'SystemContext'.
        return this.diagramBuilderHelper.newNodeDescription().name(DESCRIPTION_NAME).domainType("C4::ExternalPerson").semanticCandidatesExpression("aql:self.eContainer().externalElements")
                .childrenLayoutStrategy(DiagramFactory.eINSTANCE.createFreeFormLayoutStrategyDescription()).labelExpression("aql:self.getLabel()").defaultHeightExpression("85")
                .defaultWidthExpression("125").userResizable(true).keepAspectRatio(true).synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .style(this.c4ViewBuilder.createStandardImageNodeStyleDescription(C4Image.ExternalPerson.getShapeId(), this.colorProvider)).build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        cache.getNodeDescription(DESCRIPTION_NAME).ifPresent(nodeDescription -> {
            diagramDescription.getNodeDescriptions().add(nodeDescription);
        });
    }

}
