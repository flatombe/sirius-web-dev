/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { useWorkbenchState, WorkbenchPart } from '@eclipse-sirius/sirius-components-core';
import React, { useCallback } from 'react';
import {
  DiagramRepresentationConfiguration,
  DiagramRepresentationConfigurationContextValue,
} from './DiagramRepresentationConfigurationContext.types';

const defaultValue: DiagramRepresentationConfigurationContextValue = {
  isHelperLinesEnabledInPartConfigurationContext: () => true,
  setHelperLinesEnabledInPartConfigurationContext: () => undefined,
};

export const DiagramRepresentationConfigurationContext =
  React.createContext<DiagramRepresentationConfigurationContextValue>(defaultValue);

export const DiagramRepresentationConfigurationContextProvider = ({ children }) => {
  const { workbenchState, updateWorkbenchPart } = useWorkbenchState();

  const isHelperLinesEnabledInPartConfigurationContext: (partId: string) => boolean = useCallback(
    (partId: string) => {
      const diagramRepresentationConfiguration = workbenchState.parts[partId]
        ?.configuration as DiagramRepresentationConfiguration;
      return diagramRepresentationConfiguration.isHelperLinesEnabled;
    },
    [workbenchState]
  );
  const setHelperLinesEnabledInPartConfigurationContext: (partId: string, isHelperLinesEnabled: boolean) => void =
    useCallback(
      (partId: string, isHelperLinesEnabled: boolean) => {
        updateWorkbenchPart(partId, (workbenchPart: WorkbenchPart) => {
          const diagramRepresentationConfiguration = workbenchPart.configuration as DiagramRepresentationConfiguration;
          diagramRepresentationConfiguration.isHelperLinesEnabled = isHelperLinesEnabled;
        });
      },
      [updateWorkbenchPart]
    );

  return (
    <DiagramRepresentationConfigurationContext.Provider
      value={{
        isHelperLinesEnabledInPartConfigurationContext,
        setHelperLinesEnabledInPartConfigurationContext,
      }}>
      {children}
    </DiagramRepresentationConfigurationContext.Provider>
  );
};
