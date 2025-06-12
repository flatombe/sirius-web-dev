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
export interface WorkbenchState {
  parts: WorkbenchPart[];
  configuration?: object;
  focus?: string;
}

export interface WorkbenchPart {
  id: string;
  parts: WorkbenchPart[];
  configuration?: object;
}

export interface WorkbenchStateContextValue {
  workbenchState: WorkbenchState;
  setWorkbenchState: (workbenchState: WorkbenchState) => void;
}

export interface WorkbenchStateContextProviderProps {
  initialWorkbenchState: WorkbenchState | null;
  children: React.ReactNode;
}

export interface WorkbenchStateContextProviderState {
  workbenchState: WorkbenchState;
}
