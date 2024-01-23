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
package org.eclipse.sirius.components.c4.starter.configuration;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.c4.starter.utils.C4Models;
import org.eclipse.sirius.components.c4.starter.utils.StereotypeBuilder;
import org.eclipse.sirius.components.core.configuration.IStereotypeDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IStereotypeDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.configuration.StereotypeDescription;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * {@link Configuration} that is the {@link IStereotypeDescriptionRegistryConfigurer} implementation for the C4 domain.
 *
 * @author flatombe
 */
@Configuration
public class C4StereotypeDescriptionRegistryConfigurer implements IStereotypeDescriptionRegistryConfigurer {

    /**
     * The various C4 sample models we contribute.
     *
     * @author flatombe
     */
    private enum C4Stereotype {
        Blank("C4_Blank", "C4 Blank Model", () -> List.of(C4Models.createC4ModelBlank())), InternetBankingSystem("C4_InternetBankingSystem", "C4 Internet Banking System", () -> {
            return List.of(C4Models.createC4ModelForInternetBankingSystem());
        });

        private final UUID uuid;

        private final String label;

        private final Supplier<List<EObject>> contentsSupplier;

        C4Stereotype(final String id, final String label, Supplier<List<EObject>> contentsSupplier) {
            this.uuid = UUID.nameUUIDFromBytes(Objects.requireNonNull(id).getBytes());
            this.label = Objects.requireNonNull(label);
            this.contentsSupplier = Objects.requireNonNull(contentsSupplier);
        }

        public UUID getUuid() {
            return this.uuid;
        }

        public String getLabel() {
            return this.label;
        }

        public Supplier<List<EObject>> getContentsSupplier() {
            return this.contentsSupplier;
        }
    }

    private static final String TIMER_NAME = "C4_siriusweb_stereotype_load";

    private final StereotypeBuilder stereotypeBuilder;

    public C4StereotypeDescriptionRegistryConfigurer(MeterRegistry meterRegistry) {
        this.stereotypeBuilder = new StereotypeBuilder(TIMER_NAME, meterRegistry);
    }

    @Override
    public void addStereotypeDescriptions(IStereotypeDescriptionRegistry registry) {
        final List<StereotypeDescription> stereotypeDescriptions = Stream.of(C4Stereotype.values())
                .map(stereotype -> new StereotypeDescription(stereotype.getUuid(), stereotype.getLabel(), () -> this.stereotypeBuilder.getStereotypeBody(stereotype.getContentsSupplier().get())))
                .toList();
        stereotypeDescriptions.forEach(registry::add);
    }

}
