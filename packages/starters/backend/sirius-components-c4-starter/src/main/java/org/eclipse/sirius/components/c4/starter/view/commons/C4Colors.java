/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;

/**
 * Everything related to colors for the C4 metamodel.
 *
 * @author flatombe
 */
public class C4Colors {

    /**
     * Colors used for representation descriptions for the C4 model.
     *
     * @author flatombe
     */
    public enum C4Color {
        Transparent("#00FFFFFF"), Text_Primary("#FFFFFF"), Text_Secondary("#000000"), Border("#000000"), C4_SoftwareSystem("#1E8BF7"), C4_ExternalSystem("#A1A1A1"), C4_ContextInteraction("#7B8385");

        private final String value;

        C4Color(final String hexValue) {
            this.value = Objects.requireNonNull(hexValue);
        }

        public String getValue() {
            return this.value;
        }

        public String getName() {
            return this.name();
        }
    }

    /**
     * {@link IColorProvider} implementation.
     *
     * @author flatombe
     */
    public static class C4ColorProvider implements IColorProvider {
        private final View view;

        public C4ColorProvider(View view) {
            this.view = Objects.requireNonNull(view);
        }

        @Override
        public UserColor getColor(String colorName) {
            Objects.requireNonNull(colorName);

            final List<String> knownColorNames = Stream.of(C4Color.values()).map(C4Color::getName).toList();
            if (!knownColorNames.contains(colorName)) {
                throw new IllegalArgumentException(String.format("Unsupported color name '%s'. Use one of:%s", colorName, knownColorNames.stream().collect(Collectors.joining(", ", " ", ""))));
            } else {
                final List<UserColor> matchingColors = this.view.getColorPalettes().stream().map(ColorPalette::getColors).flatMap(Collection::stream)
                        .filter(userColor -> userColor.getName().equals(colorName)).toList();
                if (matchingColors.size() > 2) {
                    throw new IllegalStateException(String.format("Found %d colors with name '%s' while there should be at most 1:%s", matchingColors.size(), colorName,
                            matchingColors.stream().map(UserColor::toString).collect(Collectors.joining(", ", " ", ""))));
                } else {
                    return matchingColors.stream().findFirst().orElse(null);
                }
            }
        }
    }

    public static List<? extends UserColor> createAllColors() {
        return Stream.of(C4Color.values()).map(c4Color -> createFixedColor(c4Color.getName(), c4Color.getValue())).toList();
    }

    private static FixedColor createFixedColor(String name, String value) {
        var fixedColor = ViewFactory.eINSTANCE.createFixedColor();
        fixedColor.setName(name);
        fixedColor.setValue(value);

        return fixedColor;
    }
}
