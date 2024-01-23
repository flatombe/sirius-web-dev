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
package org.eclipse.sirius.components.c4.starter.view.services;

import fr.obeo.dsl.C4.model.api.C4Element;
import fr.obeo.dsl.C4.model.api.DescribedElement;
import fr.obeo.dsl.C4.model.api.ExternalPerson;
import fr.obeo.dsl.C4.model.api.ExternalSystem;
import fr.obeo.dsl.C4.model.api.NamedElement;
import fr.obeo.dsl.C4.model.api.SoftwareSystem;
import fr.obeo.dsl.C4.model.util.C4Switch;

import java.util.Objects;

import org.eclipse.sirius.components.c4.starter.view.C4SiriusJavaServiceProvider;
import org.eclipse.sirius.components.c4.starter.view.C4ViewRegistryConfiguration;
import org.eclipse.sirius.components.core.api.IObjectService;

/**
 * Java services contributed through {@link C4SiriusJavaServiceProvider} and used for the C4 views contributed through
 * {@link C4ViewRegistryConfiguration}.
 *
 * @author flatombe
 */
public class C4LabelsSiriusJavaServices {

    /**
     * @author flatombe
     */
    private static final class C4Labeller extends C4Switch<String> {
        @Override
        public String caseSoftwareSystem(final SoftwareSystem softwareSystem) {
            return getLabelForNamedAndDescribedElement(softwareSystem, "Software System");
        }

        @Override
        public String caseExternalPerson(final ExternalPerson externalPerson) {
            return getLabelForNamedAndDescribedElement(externalPerson, "Person");
        }

        @Override
        public String caseExternalSystem(final ExternalSystem externalSystem) {
            return getLabelForNamedAndDescribedElement(externalSystem, "External System");
        }

        private static <T extends NamedElement & DescribedElement> String getLabelForNamedAndDescribedElement(final T namedAndDescribedElement, final String typeLabel) {
            // Step 1: Retrieve contents to show if there is any.

            final String nameContents;
            final String name = namedAndDescribedElement.getName();
            if (name != null && !name.isBlank() && !name.isEmpty()) {
                nameContents = name;
            } else {
                // At construction elements might be unnamed.
                nameContents = null;
            }

            final String typeContents;
            if (typeLabel != null && !typeLabel.isBlank() && !typeLabel.isEmpty()) {
                typeContents = typeLabel;
            } else {
                // This should not happen.
                typeContents = null;
            }

            final String descriptionContents;
            final String description = namedAndDescribedElement.getDescription();
            if (description != null && !description.isBlank() && !description.isEmpty()) {
                descriptionContents = description;
            } else {
                descriptionContents = null;
            }

            // Step 2: Assemble contents to show into segments.

            final String nameSegment;
            if (nameContents == null) {
                nameSegment = "";
            } else {
                nameSegment = nameContents;
            }

            final String typeSegment;
            if (typeContents == null) {
                typeSegment = "";
            } else {
                // Even if there is no name, display type information on its own line.
                final String separator = "\n";
                typeSegment = String.format("%s[%s]", separator, typeContents);
            }

            final String descriptionSegment;
            if (descriptionContents == null) {
                descriptionSegment = "";
            } else {
                // Even if there is no name and/or no type, leave an empty line above us.
                final String separator = "\n\n";
                descriptionSegment = String.format("%s%s", separator, descriptionContents);
            }

            // Step 3: Assemble the segments

            return String.format("%s%s%s", nameSegment, typeSegment, descriptionSegment);
        }
    }

    // TODO: Not sure if we need this?
    private final IObjectService objectService;

    public C4LabelsSiriusJavaServices(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    public String getLabel(final C4Element c4element) {
        return new C4Labeller().doSwitch(c4element);
    }

}
