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

package circus.robocalc.roboarch.emf;

import java.util.Collection;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;


// A version of the InMemoryEmfModel that does not 'expand'.
// This is an attempt at defining an IModel over a single resource
// despite it being able to exists in a resourceSet.
public class InMemorySingleResourceModel extends InMemoryEmfModel {

	public InMemorySingleResourceModel(Resource modelImpl, boolean isContainerListenerEnabled) {
		super(modelImpl, isContainerListenerEnabled);
		// TODO Auto-generated constructor stub
	}

	public InMemorySingleResourceModel(Resource modelImpl) {
		super(modelImpl);
		// TODO Auto-generated constructor stub
	}

	public InMemorySingleResourceModel(String name, Resource modelImpl, boolean isContainerListenerEnabled) {
		super(name, modelImpl, isContainerListenerEnabled);
		// TODO Auto-generated constructor stub
	}

	public InMemorySingleResourceModel(String name, Resource modelImpl, Collection<EPackage> ePackages,
			boolean isContainerListenerEnabled) {
		super(name, modelImpl, ePackages, isContainerListenerEnabled);
		// TODO Auto-generated constructor stub
	}

	public InMemorySingleResourceModel(String name, Resource modelImpl, Collection<EPackage> ePackages) {
		super(name, modelImpl, ePackages);
		// TODO Auto-generated constructor stub
	}

	public InMemorySingleResourceModel(String name, Resource modelImpl, EPackage... ePackages) {
		super(name, modelImpl, ePackages);
		// TODO Auto-generated constructor stub
	}

	public InMemorySingleResourceModel(String name, Resource modelImpl, String... nsUris) {
		super(name, modelImpl, nsUris);
		// TODO Auto-generated constructor stub
	}

	public InMemorySingleResourceModel(String name, Resource modelImpl) {
		super(name, modelImpl);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isExpand() { return false; }
	
}
