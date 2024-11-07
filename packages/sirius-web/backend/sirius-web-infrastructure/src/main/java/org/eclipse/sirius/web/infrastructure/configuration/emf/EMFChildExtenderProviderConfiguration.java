/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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
package org.eclipse.sirius.web.infrastructure.configuration.emf;

import org.eclipse.sirius.components.emf.configuration.ChildExtenderProvider;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.deck.provider.DeckItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.customnodes.provider.CustomnodesItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.diagram.provider.DiagramItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.provider.FormItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.gantt.provider.GanttItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.tree.provider.TreeItemProviderAdapterFactory;
import org.eclipse.sirius.components.widgets.reference.provider.ReferenceItemProviderAdapterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Used to register all the child extender providers.
 *
 * @author sbegaudeau
 */
@Configuration
public class EMFChildExtenderProviderConfiguration {

    @Bean
    public ChildExtenderProvider diagramChildExtenderProvider() {
        return new ChildExtenderProvider(ViewPackage.eNS_URI, DiagramItemProviderAdapterFactory.ViewChildCreationExtender::new);
    }

    @Bean
    public ChildExtenderProvider formChildExtenderProvider() {
        return new ChildExtenderProvider(ViewPackage.eNS_URI, FormItemProviderAdapterFactory.ViewChildCreationExtender::new);
    }

    @Bean
    public ChildExtenderProvider deckChildExtenderProvider() {
        return new ChildExtenderProvider(ViewPackage.eNS_URI, DeckItemProviderAdapterFactory.ViewChildCreationExtender::new);
    }

    @Bean
    public ChildExtenderProvider ganttChildExtenderProvider() {
        return new ChildExtenderProvider(ViewPackage.eNS_URI, GanttItemProviderAdapterFactory.ViewChildCreationExtender::new);
    }

    @Bean
    public ChildExtenderProvider treeChildExtenderProvider() {
        return new ChildExtenderProvider(ViewPackage.eNS_URI, TreeItemProviderAdapterFactory.ViewChildCreationExtender::new);
    }

    @Bean
    public ChildExtenderProvider customNodesChildExtenderProvider() {
        return new ChildExtenderProvider(DiagramPackage.eNS_URI, CustomnodesItemProviderAdapterFactory.DiagramChildCreationExtender::new);
    }

    @Bean
    public ChildExtenderProvider referenceWidgetChildExtenderProvider() {
        return new ChildExtenderProvider(FormPackage.eNS_URI, ReferenceItemProviderAdapterFactory.FormChildCreationExtender::new);
    }
}