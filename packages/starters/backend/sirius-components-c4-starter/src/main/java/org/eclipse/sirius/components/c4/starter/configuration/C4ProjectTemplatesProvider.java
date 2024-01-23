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

import org.eclipse.sirius.web.services.api.projects.IProjectTemplateProvider;
import org.eclipse.sirius.web.services.api.projects.ProjectTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * Provides Flow-specific project templates.
 *
 * @author pcdavid
 */
@Configuration
public class C4ProjectTemplatesProvider implements IProjectTemplateProvider {

    public static final String ID_TEMPLATE_C4_SAMPLE_MODEL = "C4_Template_SampleModel";

    public static final String ID_TEMPLATE_C4_IBS_MODEL = "C4_Template_IBS";

    @Override
    public List<ProjectTemplate> getProjectTemplates() {
        final ProjectTemplate templateC4SampleModel = ProjectTemplate.newProjectTemplate(ID_TEMPLATE_C4_SAMPLE_MODEL).label("C4 Sample Project").imageURL("/images/C4-Template.png").natures(List.of())
                .build();
        final ProjectTemplate templateC4InternetBankingSystemModel = ProjectTemplate.newProjectTemplate(ID_TEMPLATE_C4_IBS_MODEL).label("C4 Internet Banking System Project")
                .imageURL("/images/C4-Template.png").natures(List.of()).build();
        return List.of(templateC4SampleModel, templateC4InternetBankingSystemModel);
    }

}
