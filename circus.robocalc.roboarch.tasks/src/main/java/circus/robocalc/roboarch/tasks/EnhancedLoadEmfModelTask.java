/********************************************************************************
 * Copyright (c) 2022 University of York and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   William Barnett - initial definition
 ********************************************************************************/

package circus.robocalc.roboarch.tasks;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.workflow.tasks.emf.LoadEmfModelTask;

public class EnhancedLoadEmfModelTask extends LoadEmfModelTask { 

	public void executeImpl() {
		super.executeImpl();
		
		if (this.expand) {
			// Get this model
			IModel model = getProjectRepository().getModelByNameSafe(this.name);
			
			if (model instanceof EmfModel) {
				ResourceSet rs = ((EmfModel) model).getResource().getResourceSet();
				EcoreUtil.resolveAll(rs);
			}
		}
	}
}
