/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.task;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Team</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.task.Team#getMembers <em>Members</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.task.TaskPackage#getTeam()
 * @model
 * @generated
 */
public interface Team extends Resource {
    /**
     * Returns the value of the '<em><b>Members</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.task.Person}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Members</em>' reference list.
     * @see org.eclipse.sirius.components.task.TaskPackage#getTeam_Members()
     * @model
     * @generated
     */
    EList<Person> getMembers();

} // Team
