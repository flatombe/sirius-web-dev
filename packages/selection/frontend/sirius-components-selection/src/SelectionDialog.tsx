/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
import { Selection, SelectionContext } from '@eclipse-sirius/sirius-components-core';
import { DiagramDialogComponentProps, GQLToolVariable } from '@eclipse-sirius/sirius-components-diagrams';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useState } from 'react';
import { SelectionDialogState } from './SelectionDialog.types';
import { SelectionDialogTreeView } from './SelectionDialogTreeView';
import { useSelectionDescription } from './useSelectionDescription';

export const SELECTION_DIALOG_TYPE: string = 'selectionDialogDescription';

export const SelectionDialog = ({
  editingContextId,
  dialogDescriptionId,
  variables,
  onClose,
  onFinish,
}: DiagramDialogComponentProps) => {
  const [state, setState] = useState<SelectionDialogState>({
    selectedObjects: [],
  });

  const { selectionDescription } = useSelectionDescription({
    editingContextId,
    selectionDescriptionId: dialogDescriptionId,
    variables,
  });

  const message: string = selectionDescription?.message ?? '';
  const treeDescriptionId: string | null = selectionDescription?.treeDescription.id ?? null;
  const multiple: boolean = selectionDescription?.multiple ?? false;

  const setDialogSelection = (selection: Selection) => {
    setState((prevState) => ({ ...prevState, selectedObjects: [...selection.entries] }));
  };

  const handleClick = () => {
    let variables: GQLToolVariable[] = [];
    if (state.selectedObjects.length > 0) {
      if (multiple) {
        const selectedObjectIds = state.selectedObjects.map((selectedObject) => selectedObject.id).join(',');
        variables = [{ name: 'selectedObjects', value: selectedObjectIds, type: 'OBJECT_ID_ARRAY' }];
      } else {
        const selectedObjectId = state.selectedObjects[0]?.id ?? '';
        variables = [{ name: 'selectedObject', value: selectedObjectId, type: 'OBJECT_ID' }];
      }
      onFinish(variables);
    }
  };

  let content: JSX.Element | null = null;
  if (treeDescriptionId !== null) {
    content = (
      <SelectionDialogTreeView
        editingContextId={editingContextId}
        variables={variables}
        treeDescriptionId={treeDescriptionId}
        enableMultiSelection={multiple}
      />
    );
  }

  return (
    <SelectionContext.Provider
      value={{ selection: { entries: [...state.selectedObjects] }, setSelection: setDialogSelection }}>
      <Dialog
        open
        onClose={onClose}
        aria-labelledby="dialog-title"
        maxWidth="md"
        fullWidth
        data-testid="selection-dialog">
        <DialogTitle id="selection-dialog-title">Selection Dialog</DialogTitle>
        <DialogContent>
          <DialogContentText data-testid="selection-dialog-message">{message}</DialogContentText>
          {content}
        </DialogContent>
        <DialogActions>
          <Button
            variant="contained"
            disabled={state.selectedObjects.length == 0}
            data-testid="finish-action"
            color="primary"
            onClick={handleClick}>
            Finish
          </Button>
        </DialogActions>
      </Dialog>
    </SelectionContext.Provider>
  );
};
