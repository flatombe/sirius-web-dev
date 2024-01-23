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
package org.eclipse.sirius.components.c4.starter.view.commons;

import org.eclipse.sirius.components.c4.starter.view.commons.C4Colors.C4Color;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.diagram.ImageNodeStyleDescription;

/**
 * Used to help creating the Flow view.
 *
 * @author frouene
 */
public class C4ViewBuilder {

    private final DiagramBuilders diagramBuilderHelper = new DiagramBuilders();

    public ImageNodeStyleDescription createStandardImageNodeStyleDescription(String shapeId, IColorProvider colorProvider) {
        return this.diagramBuilderHelper.newImageNodeStyleDescription().shape(shapeId).color(colorProvider.getColor(C4Color.Transparent.getName()))
                .borderColor(colorProvider.getColor(C4Color.Transparent.getName())).labelColor(colorProvider.getColor(C4Color.Text_Secondary.getName())).build();
    }

}
