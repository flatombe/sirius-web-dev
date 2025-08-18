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

import {
  EditProjectViewPathContext,
  EditProjectViewPathContextValue,
  useSelection,
} from '@eclipse-sirius/sirius-components-core';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useContext } from 'react';
import { selectionToSearchParamsValue } from '../../SelectionSynchronizer';
import { ShareProjectModalProps } from './ShareProjectModal.types';

export const ShareProjectModal = ({ workbenchConfiguration, onClose }: ShareProjectModalProps) => {
  const refCallback = (node: HTMLElement) => {
    if (node !== null) {
      var range = document.createRange();
      range.selectNodeContents(node);
      var selection = window.getSelection();
      if (selection) {
        selection.removeAllRanges();
        selection.addRange(range);
      }
    }
  };

  const { generatePathToEditProjectView } = useContext<EditProjectViewPathContextValue>(EditProjectViewPathContext);

  const { selection } = useSelection();
  const searchParams: URLSearchParams = new URLSearchParams({
    workbenchConfiguration: JSON.stringify(workbenchConfiguration),
    selection: selectionToSearchParamsValue(selection),
  });

  const representationId: string | null =
    workbenchConfiguration.mainPanel?.representationEditors.find((configuration) => configuration.isActive)
      ?.representationId ?? null;

  const path = generatePathToEditProjectView(representationId);
  const url = window.location.origin + path + '?' + searchParams.toString();

  let title = 'Shareable link';
  if (navigator.clipboard && document.hasFocus()) {
    navigator.clipboard.writeText(url);
    title += ' (copied into the clipboard)';
  }

  return (
    <Dialog open onClose={onClose} aria-labelledby="dialog-title" fullWidth>
      <DialogTitle>{title}</DialogTitle>
      <DialogContent ref={refCallback}>
        <DialogContentText data-testid="share-path">{url}</DialogContentText>
      </DialogContent>
    </Dialog>
  );
};
