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
import { EmptyTask, Task } from '@ObeoNetwork/gantt-task-react';
import { GQLMessage } from '@eclipse-sirius/sirius-components-core';

export interface GQLGanttEventSubscription {
  ganttEvent: GQLGanttEventPayload;
}

export interface GQLGanttEventPayload {
  __typename: string;
}

export interface GQLGanttRefreshedEventPayload extends GQLGanttEventPayload {
  id: string;
  gantt: GQLGantt;
}

export interface Subscriber {
  username: string;
}

export interface GQLErrorPayload extends GQLGanttEventPayload {
  messages: GQLMessage[];
}

export interface GQLGantt {
  id: string;
  targetObjectId: string;
  displayedDays: GQLDay[];
  backgroundColor: string;
  tasks: GQLTask[];
  columns: GQLColumn[];
  dateRounding: GQLGanttDateRounding;
}

export interface GQLGanttDateRounding {
  value: number;
  timeUnit: GQLGanttDateRoundingTimeUnit;
}

export enum GQLGanttDateRoundingTimeUnit {
  MINUTE,
  HOUR,
  DAY,
}

export type TemporalType = 'DATE' | 'DATE_TIME';

export interface GQLTask {
  id: string;
  descriptionId: string;
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  detail: GQLTaskDetail;
  style: GQLTaskStyle;
  subTasks: GQLTask[];
  taskDependencyIds: string[];
}
export interface GQLColumn {
  id: string;
  displayed: boolean;
  width: number;
}

export interface SelectableTask extends Task {
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
  temporalType?: TemporalType;
}
export interface SelectableEmptyTask extends EmptyTask {
  targetObjectId: string;
  targetObjectKind: string;
  targetObjectLabel: string;
}

export interface GQLTaskDetail {
  name: string;
  description: string;
  startTime?: string;
  endTime?: string;
  temporalType?: TemporalType;
  progress: number;
  computeStartEndDynamically?: boolean;
  collapsed?: boolean;
}

export interface GQLTaskStyle {
  labelColor: string;
  backgroundColor: string;
  progressColor: string;
}

export enum GQLDay {
  MONDAY,
  TUESDAY,
  WEDNESDAY,
  THURSDAY,
  FRIDAY,
  SATURDAY,
  SUNDAY,
}
