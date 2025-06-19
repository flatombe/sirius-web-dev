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

export type DiagramRepresentationConfigurationContextValue = {
  isHelperLinesEnabledInPartConfigurationContext: (partId: string) => boolean;
  setHelperLinesEnabledInPartConfigurationContext: (partId: string, helperLinesEnabled: boolean) => void;
};

export interface DiagramRepresentationConfigurationContextProviderProps {
  children: React.ReactNode;
}

export interface DiagramRepresentationConfiguration {
  isHelperLinesEnabled: boolean;
}
