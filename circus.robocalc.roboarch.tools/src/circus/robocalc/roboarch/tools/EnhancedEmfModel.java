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

package circus.robocalc.roboarch.tools;

import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

public class EnhancedEmfModel extends EmfModel {

	public void loadModelFromUri() throws EolModelLoadingException {
		super.loadModelFromUri();
		
		// For some reason it isn't sufficient to just call EcoreUtil.resolveAll
		// on the model itself. Need to call it on the resourceSet.
	}
	
}
