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

import fr.obeo.dsl.C4.model.api.NamedElement;
import fr.obeo.dsl.C4.model.edit.provider.C4ItemProviderAdapterFactory;
import fr.obeo.dsl.C4.model.meta.C4Package;
import fr.obeo.dsl.C4.model.util.C4Switch;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.emf.services.ILabelFeatureProvider;
import org.eclipse.sirius.components.emf.services.LabelFeatureProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link Configuration} for supporting the EMF metamodel of the C4 model.
 *
 * @author flatombe
 */
@Configuration
public class C4EmfConfiguration {

    /**
     * @author flatombe
     */
    private static final class C4LabelFeatureEditabilityProvider extends C4Switch<Boolean> {
        @Override
        public Boolean caseNamedElement(final NamedElement namedElement) {
            // We want to allow users to be able to edit the name of any NamedElement.
            return true;
        }

        @Override
        public Boolean defaultCase(EObject object) {
            return false;
        }
    }

    /**
     * @author flatombe
     */
    private static final class C4LabelFeatureProvider extends C4Switch<EAttribute> {
        @Override
        public EAttribute caseNamedElement(final NamedElement namedElement) {
            // The label for NamedElement is its name.
            // But I wonder why this does not rely on the generated *ItemProvider instead?
            return C4Package.Literals.NAMED_ELEMENT__NAME;
        }

        @Override
        public EAttribute defaultCase(EObject object) {
            return null;
        }
    }

    @Bean
    AdapterFactory c4AdapterFactory() {
        return new C4ItemProviderAdapterFactory();
    }

    @Bean
    EPackage c4EPackage() {
        return C4Package.eINSTANCE;
    }

    @Bean
    ILabelFeatureProvider c4LabelFeatureProvider() {
        return new LabelFeatureProvider(C4Package.eINSTANCE.getNsURI(), new C4LabelFeatureProvider(), new C4LabelFeatureEditabilityProvider());
    }

}
