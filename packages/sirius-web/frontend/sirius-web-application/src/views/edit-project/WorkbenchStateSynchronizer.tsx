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
import { useWorkbenchState } from '@eclipse-sirius/sirius-components-core';
import { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';
import { WorkbenchStateSynchronizerProps } from './WorkbenchStateSynchronizer.types';

export const WorkbenchStateSynchronizer = ({ children }: WorkbenchStateSynchronizerProps) => {
  const { workbenchState } = useWorkbenchState();
  const [_urlSearchParams, setUrlSearchParams] = useSearchParams();

  useEffect(() => {
    setUrlSearchParams((urlSearchParams: URLSearchParams) => {
      if (workbenchState) {
        const workbenchStateValue: string = JSON.stringify(workbenchState);
        urlSearchParams.set('workbenchState', workbenchStateValue);
      } else {
        if (urlSearchParams.has('workbenchState')) {
          urlSearchParams.delete('workbenchState');
        }
      }
      return urlSearchParams;
    });
  }, [workbenchState]);

  return children;
};
