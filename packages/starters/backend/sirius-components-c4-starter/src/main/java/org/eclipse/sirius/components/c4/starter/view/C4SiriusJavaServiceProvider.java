/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
package org.eclipse.sirius.components.c4.starter.view;

import fr.obeo.dsl.C4.model.meta.C4Package;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.c4.starter.view.services.C4LabelsSiriusJavaServices;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.emf.IJavaServiceProvider;
import org.springframework.stereotype.Service;

/**
 * Used to provide services for the Flow view.
 *
 * @author frouene
 */
@Service
public class C4SiriusJavaServiceProvider implements IJavaServiceProvider {

    @Override
    public List<Class<?>> getServiceClasses(View view) {
        if (view.getDescriptions().stream().anyMatch(representationDescription -> representationDescription.getDomainType().startsWith(String.format("%s::", C4Package.eNS_PREFIX)))) {
            return List.of(C4LabelsSiriusJavaServices.class);
        } else {
            return new ArrayList<>();
        }
    }

}
