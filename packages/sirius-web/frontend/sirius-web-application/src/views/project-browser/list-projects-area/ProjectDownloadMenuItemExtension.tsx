/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import { ServerContext, ServerContextValue } from '@eclipse-sirius/sirius-components-core';
import GetAppIcon from '@mui/icons-material/GetApp';
import { CircularProgress } from '@mui/material';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuItem from '@mui/material/MenuItem';
import { useContext, useState } from 'react';
import { ProjectContextMenuEntryProps } from './ProjectActionButton.types';
import { ProjectDownloadState } from './ProjectDownload.types';

export const ProjectDownloadMenuItemExtension = ({ project }: ProjectContextMenuEntryProps) => {
  const { httpOrigin } = useContext<ServerContextValue>(ServerContext);

  const [state, setState] = useState<ProjectDownloadState>({
    loading: false,
  });

  const handleDownloadProjectClick = async () => {
    if (state.loading == false) {
      setState((prevState) => ({
        ...prevState,
        loading: true,
      }));

      const urlToDownloadProjectZipFile = `${httpOrigin}/api/projects/${project.id}`;

      const response = await fetch(urlToDownloadProjectZipFile, {
        method: 'GET',
        headers: { 'Content-Type': 'application/octet-stream' },
      });
      console.log(`Fetched ${urlToDownloadProjectZipFile}: ${response.status}`);

      if (response.ok) {
        const blob = await response.blob();
        const filename = response.headers.get('Content-Disposition').split('filename=')[1].slice(1, -1);

        // Create a virtual hyperlink for the fetched file.
        const url = URL.createObjectURL(blob);
        const anchor = document.createElement('a');
        anchor.download = filename;
        anchor.href = url;

        // Browser will prompt the user to open/save the zip file.
        anchor.click();

        // Cleanup.
        URL.revokeObjectURL(url);
        anchor.remove();
      }

      setState((prevState) => ({
        ...prevState,
        loading: false,
      }));
    }
  };

  const icon = state.loading ? <CircularProgress size="1rem" /> : <GetAppIcon />;

  return (
    <MenuItem data-testid="project-download-action" component="a" onClick={handleDownloadProjectClick}>
      <ListItemIcon>{icon}</ListItemIcon>
      <ListItemText primary="Download" />
    </MenuItem>
  );
};
