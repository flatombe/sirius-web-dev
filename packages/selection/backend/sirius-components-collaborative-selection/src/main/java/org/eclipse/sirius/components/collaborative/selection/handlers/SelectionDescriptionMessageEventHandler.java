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
package org.eclipse.sirius.components.collaborative.selection.handlers;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.messages.ICollaborativeMessageService;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionMessageInput;
import org.eclipse.sirius.components.collaborative.selection.dto.GetSelectionDescriptionMessagePayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.selection.description.SelectionDescription;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Handler for the GetSelectionDescriptionMessageInput.
 *
 * @author fbarbin
 */
@Service
public class SelectionDescriptionMessageEventHandler implements IEditingContextEventHandler {

    private final ICollaborativeMessageService messageService;

    private final IObjectService objectService;

    public SelectionDescriptionMessageEventHandler(ICollaborativeMessageService messageService, IObjectService objectService) {
        this.messageService = Objects.requireNonNull(messageService);
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof GetSelectionDescriptionMessageInput;
    }

    @Override
    public void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        if (input instanceof GetSelectionDescriptionMessageInput getSelectionDescriptionMessageInput) {
            String targetObjectId = getSelectionDescriptionMessageInput.targetObjectId();
            Optional<Object> optionalTargetObject = this.objectService.getObject(editingContext, targetObjectId);
            VariableManager variableManager = new VariableManager();
            if (optionalTargetObject.isPresent()) {
                variableManager.put(VariableManager.SELF, optionalTargetObject.get());
            }
            SelectionDescription selectionDescription = getSelectionDescriptionMessageInput.selectionDescription();
            String message = selectionDescription.getMessageProvider().apply(variableManager);
            payloadSink.tryEmitValue(new GetSelectionDescriptionMessagePayload(input.id(), message));
        } else {
            String message = this.messageService.invalidInput(input.getClass().getSimpleName(), GetSelectionDescriptionMessageInput.class.getSimpleName());
            payloadSink.tryEmitValue(new ErrorPayload(input.id(), message));
        }
    }
}