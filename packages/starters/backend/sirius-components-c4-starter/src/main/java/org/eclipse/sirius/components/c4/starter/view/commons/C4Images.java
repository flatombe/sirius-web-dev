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

import java.util.Objects;

/**
 * Images used for the C4 modeler.
 *
 * @author flatombe
 */
public class C4Images {

    /**
     * Image files provided for the C4 modeler.
     *
     * @author flatombe
     */
    public enum C4Image {
        ExternalPerson("156650ea-3ccc-3ea1-be77-f6f23c6f03ad");

        private final String shapeId;

        /**
         * To get the 'shapeId' for an image:
         * <ul>
         * <li>Place an SVG file in src/main/resources/customImages</li>
         * <li>Then run the backend and look at the log for the hash of the SVG file</li>
         * <li>Or run something like
         * {@code UUID.nameUUIDFromBytes(Files.readAllBytes(Path.of("path/to/src/main/resources/customImages/XXX.svg")))}</li>
         * </ul>
         */
        C4Image(final String shapeId) {
            Objects.requireNonNull(shapeId);

            this.shapeId = shapeId;
        }

        public String getShapeId() {
            return this.shapeId;
        }
    }

}
