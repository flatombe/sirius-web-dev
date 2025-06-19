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

import { useContext } from 'react';
import { DiagramRepresentationConfigurationContext } from './DiagramRepresentationConfigurationContext';
import { DiagramRepresentationConfigurationContextValue } from './DiagramRepresentationConfigurationContext.types';
import { useDiagramRepresentationConfigurationValue } from './useDiagramRepresentationConfiguration.types';

export const useDiagramRepresentationConfiguration: (partId: string) => useDiagramRepresentationConfigurationValue = (
  partId: string
): useDiagramRepresentationConfigurationValue => {
  const { isHelperLinesEnabledInPartConfigurationContext, setHelperLinesEnabledInPartConfigurationContext } =
    useContext<DiagramRepresentationConfigurationContextValue>(DiagramRepresentationConfigurationContext);

  const isHelperLinesEnabledInPartConfiguration: () => boolean = () => {
    return isHelperLinesEnabledInPartConfigurationContext(partId);
  };

  const setHelperLinesEnabledInPartConfiguration: (newState: boolean) => void = (newState: boolean) => {
    return setHelperLinesEnabledInPartConfigurationContext(partId, newState);
  };

  return {
    isHelperLinesEnabledInPartConfiguration,
    setHelperLinesEnabledInPartConfiguration,
  };
};
