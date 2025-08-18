/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
  RepresentationMetadata,
  Selection,
  SelectionContextProvider,
  SelectionEntry,
  Workbench,
  WorkbenchConfiguration,
  WorkbenchHandle,
} from '@eclipse-sirius/sirius-components-core';
import { OmniboxProvider } from '@eclipse-sirius/sirius-components-omnibox';
import {
  TreeToolBarContext,
  TreeToolBarContextValue,
  TreeToolBarContribution,
} from '@eclipse-sirius/sirius-components-trees';
import { RefObject, useEffect, useRef, useState } from 'react';
import { Navigate, useParams, useSearchParams } from 'react-router-dom';
import { makeStyles } from 'tss-react/mui';
import {
  EditProjectViewComponentProps,
  EditProjectViewParams,
  EditProjectViewState,
  TreeToolBarProviderProps,
} from './EditProjectView.types';
import { EditProjectNavbar } from './navbar/EditProjectNavbar';
import { ProjectContext } from './ProjectContext';
import { SelectionSynchronizer } from './SelectionSynchronizer';
import { NewDocumentModalContribution } from './TreeToolBarContributions/NewDocumentModalContribution';
import { UploadDocumentModalContribution } from './TreeToolBarContributions/UploadDocumentModalContribution';
import { UndoRedo } from './UndoRedo';
import { useProjectAndRepresentationMetadata } from './useProjectAndRepresentationMetadata';
import { useSynchronizeSelectionAndURL } from './useSynchronizeSelectionAndURL';

const PROJECT_ID_SEPARATOR = '@';

const useEditProjectViewStyles = makeStyles()((_) => ({
  editProjectView: {
    display: 'grid',
    gridTemplateRows: 'min-content minmax(0, 1fr)',
    gridTemplateColumns: '1fr',
    height: '100vh',
    width: '100vw',
  },
}));

export const EditProjectView = ({ getEditProjectViewPathTo }: EditProjectViewComponentProps) => {
  const { projectIdAndSemanticDataName, representationId } = useParams<EditProjectViewParams>();
  const { classes } = useEditProjectViewStyles();
  const [urlSearchParams, setSearchParams] = useSearchParams();

  const separatorIndex = projectIdAndSemanticDataName.indexOf(PROJECT_ID_SEPARATOR);
  const projectId: string =
    separatorIndex !== -1 ? projectIdAndSemanticDataName.substring(0, separatorIndex) : projectIdAndSemanticDataName;
  const semanticDataName: string | null =
    separatorIndex !== -1
      ? projectIdAndSemanticDataName.substring(separatorIndex + 1, projectIdAndSemanticDataName.length)
      : null;
  const workbenchConfiguration: WorkbenchConfiguration | null = urlSearchParams.has('workbenchConfiguration')
    ? JSON.parse(urlSearchParams.get('workbenchConfiguration'))
    : null;

  const editProjectViewPathContextValue = {
    generatePathToEditProjectView(representationId: string) {
      return getEditProjectViewPathTo(projectId, semanticDataName, representationId);
    },
  };

  const [state, setState] = useState<EditProjectViewState>({
    project: null,
    representation: null,
    workbenchConfiguration,
  });

  useEffect(() => {
    if (urlSearchParams.has('workbenchConfiguration')) {
      urlSearchParams.delete('workbenchConfiguration');
      setSearchParams(urlSearchParams);
    }
  }, [urlSearchParams]);

  const { data, loading } = useProjectAndRepresentationMetadata(projectId, semanticDataName, representationId);
  useEffect(() => {
    if (data) {
      const { project } = data.viewer;

      let representation: RepresentationMetadata | null = null;
      if (project?.currentEditingContext.representation) {
        representation = {
          id: project.currentEditingContext.representation.id,
          label: project.currentEditingContext.representation.label,
          kind: project.currentEditingContext.representation.kind,
          iconURLs: project.currentEditingContext.representation.iconURLs,
          description: project.currentEditingContext.representation.description,
        };
      }
      setState((prevState) => ({
        ...prevState,
        project: project,
        representation: representation,
      }));
    }
  }, [data]);

  const onRepresentationSelected = (representationMetadata: RepresentationMetadata) => {
    setState((prevState) => ({
      ...prevState,
      representation: representationMetadata,
    }));
  };

  const refWorkbenchHandle: RefObject<WorkbenchHandle | null> = useRef<WorkbenchHandle | null>(null);

  useSynchronizeSelectionAndURL(
    projectId,
    semanticDataName,
    representationId,
    state.project ? state.project.id : null,
    state.representation ? state.representation.id : null,
    !state.project
  );

  const isMissing = !loading && (!data || !data.viewer.project || !data.viewer.project.currentEditingContext);
  if (isMissing) {
    return <Navigate to="/errors/404" replace />;
  }

  let content: React.ReactNode = null;
  if (state.project && state.project.currentEditingContext) {
    const urlSelectionValue: string = urlSearchParams.get('selection') ?? '';
    const entries: SelectionEntry[] =
      urlSelectionValue.trim().length > 0 ? urlSelectionValue.split(',').map((id) => ({ id })) : [];
    const initialSelection: Selection = { entries };

    content = (
      <ProjectContext.Provider value={{ project: state.project }}>
        <SelectionContextProvider initialSelection={initialSelection}>
          <SelectionSynchronizer>
            <EditProjectViewPathContext.Provider value={editProjectViewPathContextValue}>
              <OmniboxProvider editingContextId={state.project.currentEditingContext.id}>
                <UndoRedo>
                  <EditProjectNavbar workbenchHandle={refWorkbenchHandle.current} />
                  <TreeToolBarProvider>
                    <Workbench
                      editingContextId={state.project.currentEditingContext.id}
                      initialRepresentationSelected={state.representation}
                      onRepresentationSelected={onRepresentationSelected}
                      readOnly={!state.project.capabilities.canEdit}
                      initialWorkbenchConfiguration={state.workbenchConfiguration}
                      ref={refWorkbenchHandle}
                    />
                  </TreeToolBarProvider>
                </UndoRedo>
              </OmniboxProvider>
            </EditProjectViewPathContext.Provider>
          </SelectionSynchronizer>
        </SelectionContextProvider>
      </ProjectContext.Provider>
    );
  }

  return <div className={classes.editProjectView}>{content}</div>;
};

const TreeToolBarProvider = ({ children }: TreeToolBarProviderProps) => {
  const treeToolBarContributions: TreeToolBarContextValue = [
    <TreeToolBarContribution component={NewDocumentModalContribution} />,
    <TreeToolBarContribution component={UploadDocumentModalContribution} />,
  ];

  return <TreeToolBarContext.Provider value={treeToolBarContributions}>{children}</TreeToolBarContext.Provider>;
};
