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
package org.eclipse.sirius.components.c4.starter.utils;

import fr.obeo.dsl.C4.model.api.C4Model;
import fr.obeo.dsl.C4.model.api.ComponentClue;
import fr.obeo.dsl.C4.model.api.ComponentRefinement;
import fr.obeo.dsl.C4.model.api.ComponentizationElement;
import fr.obeo.dsl.C4.model.api.ComponentizationInteraction;
import fr.obeo.dsl.C4.model.api.ContainerComponentization;
import fr.obeo.dsl.C4.model.api.ContainerizationElement;
import fr.obeo.dsl.C4.model.api.ContainerizationInteraction;
import fr.obeo.dsl.C4.model.api.ContextInteraction;
import fr.obeo.dsl.C4.model.api.DescribedElement;
import fr.obeo.dsl.C4.model.api.ExternalPerson;
import fr.obeo.dsl.C4.model.api.ExternalSystem;
import fr.obeo.dsl.C4.model.api.SoftwareSystem;
import fr.obeo.dsl.C4.model.api.SystemContainerization;
import fr.obeo.dsl.C4.model.api.SystemContext;
import fr.obeo.dsl.C4.model.api.TechnologicalElement;
import fr.obeo.dsl.C4.model.meta.C4Package;
import fr.obeo.dsl.C4.model.util.C4Switch;

/**
 * Where models conforming to our C4 metamodel are born (at least programmatically).
 *
 * @author flatombe
 */
public class C4Models {

    /**
     * Utilities to populate a C4 model with generic content.
     *
     * @author flatombe
     */
    private static class C4GenericTextProvider {

        /**
         * @author flatombe
         */
        private static final class C4MetatypeDescriptionProvider extends C4Switch<String> {
            @Override
            public String caseC4Model(C4Model object) {
                return "The C4 model was created as a way to help software development teams describe and communicate software architecture, both during up-front design sessions and when retrospectively documenting an existing codebase. It's a way to create maps of your code, at various levels of detail, in the same way you would use something like Google Maps to zoom in and out of an area you are interested in. The C4 model is an \"abstraction-first\" approach to diagramming software architecture, based upon abstractions that reflect how software architects and developers think about and build software. The small set of abstractions and diagram types makes the C4 model easy to learn and use. Please note that you don't need to use all 4 levels of diagram; only those that add value - the System Context and Container diagrams are sufficient for many software development teams.";
            }

            @Override
            public String caseSoftwareSystem(SoftwareSystem object) {
                return "A software system is the highest level of abstraction and describes something that delivers value to its users, whether they are human or not. This includes the software system you are modelling, and the other software systems upon which your software system depends (or vice versa). Unfortunately the term \"software system\" is the hardest of the C4 model abstractions to define, and this isn't helped by the fact that each organisation will also have their own terminology for describing the same thing, typically using terms such as \"application\", \"product\", \"service\", etc. One way to think about it is that a software system is something a single software development team is building, owns, has responsibility for, and can see the internal implementation details of. Perhaps the code for that software system resides in a single source code repository, and anybody on the team is entitled to modify it. In many cases, the boundary of a software system will correspond to the boundary of a single team. It may also be the case that everything inside the boundary of a software system is deployed at the same time.";
            }

            @Override
            public String caseExternalPerson(ExternalPerson object) {
                return "A person represents one of the human users of your software system (e.g. actors, roles, personas, etc).";
            }

            @Override
            public String caseExternalSystem(ExternalSystem object) {
                return "External systems are external dependencies directly connected to the software system in scope. Typically they sit outside the scope or boundary of your own software system, and you don't have responsibility or ownership of them.";
            }

            @Override
            public String caseContainerizationElement(ContainerizationElement object) {
                return "Not Docker! In the C4 model, a container represents an application or a data store. A container is something that needs to be running in order for the overall software system to work. A container is essentially a context or boundary inside which some code is executed or some data is stored. And each container is a separately deployable/runnable thing or runtime environment, typically (but not always) running in its own process space.";
            }

            @Override
            public String caseComponentizationElement(ComponentizationElement object) {
                return "The word \"component\" is a hugely overloaded term in the software development industry, but in this context a component is a grouping of related functionality encapsulated behind a well-defined interface. If you're using a language like Java or C#, the simplest way to think of a component is that it's a collection of implementation classes behind an interface. Aspects such as how those components are packaged (e.g. one component vs many components per JAR file, DLL, shared library, etc) is a separate and orthogonal concern. An important point to note here is that all components inside a container typically execute in the same process space. In the C4 model, components are not separately deployable units.";
            }

            @Override
            public String caseContextInteraction(ContextInteraction object) {
                return "Unidirectional relationship between the SoftwareSystem and/or any of the ExternalPeople/ExternalSystem. The label should be consistent with the direction and intent of the relationship. Try to be as specific as possible, ideally avoiding single words like, \"Uses\".";
            }

            @Override
            public String caseContainerizationInteraction(ContainerizationInteraction object) {
                return "Unidirectional relationship between the Containers and/or any of the ExternalPeople/ExternalSystem. The label should be consistent with the direction and intent of the relationship. Try to be as specific as possible, ideally avoiding single words like, \"Uses\".";
            }

            @Override
            public String caseComponentizationInteraction(ComponentizationInteraction object) {
                return "Unidirectional relationship between the Components and/or any of the Containers, External People or External System. The label should be consistent with the direction and intent of the relationship. Try to be as specific as possible, ideally avoiding single words like, \"Uses\".";
            }

            @Override
            public String caseComponentClue(ComponentClue object) {
                return super.caseComponentClue(object);
            }
        }

        public static String getMetatypeDescriptionFor(final DescribedElement describedElement) {
            return new C4MetatypeDescriptionProvider().doSwitch(describedElement);

        }

        public static String getTechnologyDescription(final TechnologicalElement technologicalElement) {
            return "Technology/Implementation details";
        }
    }

    public static C4Model createC4ModelBlank() {
        final C4Model model = C4Package.eINSTANCE.getC4Factory().createC4Model();
        model.setName("C4 Model");

        return model;
    }

    // CHECKSTYLE:OFF
    public static C4Model createC4ModelSample() { // CHECKSTYLE:ON
        final C4Model model = C4Package.eINSTANCE.getC4Factory().createC4Model();
        model.setName("C4 Model");
        model.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(model));

        // System Context

        final SystemContext systemContext = C4Package.eINSTANCE.getC4Factory().createSystemContext();
        model.setSystemContext(systemContext);
        final SoftwareSystem softwareSystem = C4Package.eINSTANCE.getC4Factory().createSoftwareSystem();
        softwareSystem.setName("Software System");
        softwareSystem.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(softwareSystem));
        model.setSoftwareSystem(softwareSystem);
        final ExternalPerson externalPerson = C4Package.eINSTANCE.getC4Factory().createExternalPerson();
        externalPerson.setName("External Person");
        externalPerson.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(externalPerson));
        model.getExternalElements().add(externalPerson);
        final ExternalSystem externalSystem = C4Package.eINSTANCE.getC4Factory().createExternalSystem();
        externalSystem.setName("External System");
        externalSystem.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(externalSystem));
        model.getExternalElements().add(externalSystem);

        final ContextInteraction interactionFromExternalPersonToSoftwareSystem = C4Package.eINSTANCE.getC4Factory().createContextInteraction();
        interactionFromExternalPersonToSoftwareSystem.setName("ContextInteraction from ExternalPerson to SoftwareSystem");
        interactionFromExternalPersonToSoftwareSystem.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(interactionFromExternalPersonToSoftwareSystem));
        interactionFromExternalPersonToSoftwareSystem.setSource(externalPerson);
        interactionFromExternalPersonToSoftwareSystem.setTarget(softwareSystem);
        systemContext.getContextInteractions().add(interactionFromExternalPersonToSoftwareSystem);
        final ContextInteraction interactionFromSoftwareSystemToExternalSystem = C4Package.eINSTANCE.getC4Factory().createContextInteraction();
        interactionFromSoftwareSystemToExternalSystem.setName("ContextInteraction from SoftwareSystem to ExternalSystem");
        interactionFromSoftwareSystemToExternalSystem.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(interactionFromSoftwareSystemToExternalSystem));
        interactionFromSoftwareSystemToExternalSystem.setSource(softwareSystem);
        interactionFromSoftwareSystemToExternalSystem.setTarget(externalSystem);
        systemContext.getContextInteractions().add(interactionFromSoftwareSystemToExternalSystem);

        // Containers

        final SystemContainerization systemContainerization = C4Package.eINSTANCE.getC4Factory().createSystemContainerization();
        model.setSystemContainerization(systemContainerization);
        final ContainerizationElement container1 = C4Package.eINSTANCE.getC4Factory().createContainerizationElement();
        container1.setName("Container1");
        container1.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(container1));
        systemContainerization.getContainerizationElements().add(container1);
        final ContainerizationElement container2 = C4Package.eINSTANCE.getC4Factory().createContainerizationElement();
        container2.setName("Container2");
        container2.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(container2));
        systemContainerization.getContainerizationElements().add(container2);

        final ContainerizationInteraction interactionFromExternalPersonToContainer1 = C4Package.eINSTANCE.getC4Factory().createContainerizationInteraction();
        interactionFromExternalPersonToContainer1.setName("ContainerizationInteraction from ExternalPerson to Container1");
        interactionFromExternalPersonToContainer1.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(interactionFromExternalPersonToContainer1));
        interactionFromExternalPersonToContainer1.setTechnology(C4GenericTextProvider.getTechnologyDescription(interactionFromExternalPersonToContainer1));
        interactionFromExternalPersonToContainer1.setSource(externalPerson);
        interactionFromExternalPersonToContainer1.setTarget(container1);
        systemContainerization.getContainerizationInteractions().add(interactionFromExternalPersonToContainer1);

        final ContainerizationInteraction interactionFromContainer1ToContainer2 = C4Package.eINSTANCE.getC4Factory().createContainerizationInteraction();
        interactionFromContainer1ToContainer2.setName("ContainerizationInteraction from Container1 to Container2");
        interactionFromContainer1ToContainer2.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(interactionFromContainer1ToContainer2));
        interactionFromContainer1ToContainer2.setTechnology(C4GenericTextProvider.getTechnologyDescription(interactionFromContainer1ToContainer2));
        interactionFromContainer1ToContainer2.setSource(container1);
        interactionFromContainer1ToContainer2.setTarget(container2);
        systemContainerization.getContainerizationInteractions().add(interactionFromContainer1ToContainer2);

        final ContainerizationInteraction interactionFromContainer2ToExternalSystem = C4Package.eINSTANCE.getC4Factory().createContainerizationInteraction();
        interactionFromContainer2ToExternalSystem.setName("ContainerizationInteraction from Container2 to ExternalSystem");
        interactionFromContainer2ToExternalSystem.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(interactionFromContainer2ToExternalSystem));
        interactionFromContainer2ToExternalSystem.setTechnology(C4GenericTextProvider.getTechnologyDescription(interactionFromContainer2ToExternalSystem));
        interactionFromContainer2ToExternalSystem.setSource(container2);
        interactionFromContainer2ToExternalSystem.setTarget(externalSystem);
        systemContainerization.getContainerizationInteractions().add(interactionFromContainer2ToExternalSystem);

        // Components

        final ContainerComponentization container1Componentization = C4Package.eINSTANCE.getC4Factory().createContainerComponentization();
        model.getContainerComponentizations().add(container1Componentization);
        container1Componentization.setContainer(container1);

        final ComponentizationElement component1 = C4Package.eINSTANCE.getC4Factory().createComponentizationElement();
        component1.setName("Component1");
        component1.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(component1));
        component1.setTechnology(C4GenericTextProvider.getTechnologyDescription(component1));
        container1Componentization.getComponentizationElements().add(component1);
        final ComponentizationElement component2 = C4Package.eINSTANCE.getC4Factory().createComponentizationElement();
        component2.setName("Component2");
        component2.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(component2));
        component2.setTechnology(C4GenericTextProvider.getTechnologyDescription(component2));
        container1Componentization.getComponentizationElements().add(component2);

        final ComponentizationInteraction interactionFromContainer2ToComponent1 = C4Package.eINSTANCE.getC4Factory().createComponentizationInteraction();
        interactionFromContainer2ToComponent1.setName("ComponentizationInteraction from Container2 to Component1");
        interactionFromContainer2ToComponent1.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(interactionFromContainer2ToComponent1));
        interactionFromContainer2ToComponent1.setTechnology(C4GenericTextProvider.getTechnologyDescription(interactionFromContainer2ToComponent1));
        interactionFromContainer2ToComponent1.setSource(container2);
        interactionFromContainer2ToComponent1.setTarget(component1);
        container1Componentization.getComponentizationInteractions().add(interactionFromContainer2ToComponent1);

        final ComponentizationInteraction interactionFromComponent1ToComponent2 = C4Package.eINSTANCE.getC4Factory().createComponentizationInteraction();
        interactionFromComponent1ToComponent2.setName("ComponentizationInteraction from Component1 to Component2");
        interactionFromComponent1ToComponent2.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(interactionFromComponent1ToComponent2));
        interactionFromComponent1ToComponent2.setTechnology(C4GenericTextProvider.getTechnologyDescription(interactionFromComponent1ToComponent2));
        interactionFromComponent1ToComponent2.setSource(component1);
        interactionFromComponent1ToComponent2.setTarget(component2);
        container1Componentization.getComponentizationInteractions().add(interactionFromComponent1ToComponent2);

        final ComponentizationInteraction interactionFromComponent2ToExternalSystem = C4Package.eINSTANCE.getC4Factory().createComponentizationInteraction();
        interactionFromComponent2ToExternalSystem.setName("ComponentizationInteraction from Component2 to ExternalSystem");
        interactionFromComponent2ToExternalSystem.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(interactionFromComponent2ToExternalSystem));
        interactionFromComponent2ToExternalSystem.setTechnology(C4GenericTextProvider.getTechnologyDescription(interactionFromComponent2ToExternalSystem));
        interactionFromComponent2ToExternalSystem.setSource(component2);
        interactionFromComponent2ToExternalSystem.setTarget(externalSystem);
        container1Componentization.getComponentizationInteractions().add(interactionFromComponent2ToExternalSystem);

        // Code elements

        final ComponentRefinement component1Refinement = C4Package.eINSTANCE.getC4Factory().createComponentRefinement();
        model.getComponentRefinements().add(component1Refinement);
        component1Refinement.setComponentizationElement(component1);

        final ComponentClue componentClue = C4Package.eINSTANCE.getC4Factory().createComponentClue();
        componentClue.setName("Component Clue");
        componentClue.setDescription(C4GenericTextProvider.getMetatypeDescriptionFor(componentClue));
        componentClue.setTechnology(C4GenericTextProvider.getTechnologyDescription(componentClue));
        componentClue.setHyperlink("http://c4model.com");
        component1Refinement.getComponentClues().add(componentClue);

        return model;
    }

    public static C4Model createC4ModelForInternetBankingSystem() {
        final C4Model model = C4Package.eINSTANCE.getC4Factory().createC4Model();
        model.setName("C4 Internet Banking System");
        model.setDescription("The sample C4 model from https://c4model.com/");

        // Nodes needed for the System Context diagram
        final SystemContext systemContext = C4Package.eINSTANCE.getC4Factory().createSystemContext();
        model.setSystemContext(systemContext);
        final SoftwareSystem softwareSystem = C4Package.eINSTANCE.getC4Factory().createSoftwareSystem();
        softwareSystem.setName("Internet Banking System");
        softwareSystem.setDescription("Allows customers to view information about their bank accounts, and make payments.");
        model.setSoftwareSystem(softwareSystem);
        final ExternalPerson customer = C4Package.eINSTANCE.getC4Factory().createExternalPerson();
        customer.setName("Personal Banking Customer");
        customer.setDescription("A customer of the bank, with personal bank accounts.");
        model.getExternalElements().add(customer);
        final ExternalSystem emailSystem = C4Package.eINSTANCE.getC4Factory().createExternalSystem();
        emailSystem.setName("E-mail System");
        emailSystem.setDescription("The internal Microsoft Exchange e-mail system.");
        model.getExternalElements().add(emailSystem);
        final ExternalSystem mainframe = C4Package.eINSTANCE.getC4Factory().createExternalSystem();
        mainframe.setName("Mainframe Banking System");
        mainframe.setDescription("Stores all of the core banking information about customers, accounts, transactions, etc.");
        model.getExternalElements().add(mainframe);

        // Edges needed for the System Context diagram
        final ContextInteraction interactionFromCustomerToSystem = C4Package.eINSTANCE.getC4Factory().createContextInteraction();
        interactionFromCustomerToSystem.setName("Banking system usage");
        interactionFromCustomerToSystem.setDescription("Views account balances, and makes payments using");
        interactionFromCustomerToSystem.setSource(customer);
        interactionFromCustomerToSystem.setTarget(softwareSystem);
        systemContext.getContextInteractions().add(interactionFromCustomerToSystem);
        final ContextInteraction interactionFromSystemToMainframe = C4Package.eINSTANCE.getC4Factory().createContextInteraction();
        interactionFromSystemToMainframe.setName("Mainframe system usage");
        interactionFromSystemToMainframe.setDescription("Gets account information from, and makes payments using");
        interactionFromSystemToMainframe.setSource(softwareSystem);
        interactionFromSystemToMainframe.setTarget(mainframe);
        systemContext.getContextInteractions().add(interactionFromSystemToMainframe);
        final ContextInteraction interactionFromSystemToEmail = C4Package.eINSTANCE.getC4Factory().createContextInteraction();
        interactionFromSystemToEmail.setName("E-mail system usage");
        interactionFromSystemToEmail.setDescription("Sends e-mail using");
        interactionFromSystemToEmail.setSource(softwareSystem);
        interactionFromSystemToEmail.setTarget(emailSystem);
        systemContext.getContextInteractions().add(interactionFromSystemToEmail);
        final ContextInteraction interactionFromEmailToCustomer = C4Package.eINSTANCE.getC4Factory().createContextInteraction();
        interactionFromEmailToCustomer.setName("E-mail destination");
        interactionFromEmailToCustomer.setDescription("Sends e-mails to");
        interactionFromEmailToCustomer.setSource(emailSystem);
        interactionFromEmailToCustomer.setTarget(customer);
        systemContext.getContextInteractions().add(interactionFromEmailToCustomer);

        return model;
    }

}
