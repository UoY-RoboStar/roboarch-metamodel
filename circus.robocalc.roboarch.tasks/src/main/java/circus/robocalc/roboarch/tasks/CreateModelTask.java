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

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.workflow.tasks.EpsilonTask;
import org.eclipse.xtext.resource.XtextResourceSet;

import circus.robocalc.roboarch.emf.InMemorySingleResourceModel;

public class CreateModelTask extends EpsilonTask {

	protected String name;
	protected String uri;
	protected boolean cachingEnabled = false;
	protected String nsURI;
	protected String reuseModelName;
	protected boolean expand = true;
	
	public void executeImpl() {
		
		InMemoryEmfModel model;
		ResourceSet rs = null;
		
		// We should try to reuse the ResourceSet, if, available
		// of the model given by name.
		if (reuseModelName != null) {
			ModelRepository repository = getProjectRepository();
			if (repository != null) {
				IModel m;
				try {
					m = repository.getModelByName(reuseModelName);
					if (m instanceof EmfModel) {
						rs = ((EmfModel) m).getResource().getResourceSet();
					}
				} catch (EolModelNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if (rs == null) {
			rs = new XtextResourceSet();
		}
		Resource r = rs.createResource(URI.createURI(uri));
		
		// Sadly the InMemoryEmfModel's constructors cannot be changed to
		// toggle expansion in a customised way. Calling preventLoadingOfExternalModelElements
		// is also too late, so we have to resort to a custom class.
		if (!expand) {
			model = new InMemorySingleResourceModel(name,r, nsURI);
		} else {
			model = new InMemoryEmfModel(name,r, nsURI);
		}
		model.setMetamodelUri(nsURI);
		model.setCachingEnabled(cachingEnabled);
		getProjectRepository().addModel(model);
		
		MyFinishedListener.activate(getProject(), getProjectRepository());
		//getProject().addBuildListener(new MyFinishedListener());
		getProject().log("Loading...");
		 
	}
	
	public void setExpand(String bool) {
		if (bool.equals("true")) {
			this.expand = true;
		} else {
			this.expand = false;
		}
	}	
	
	public void setReuseResourceSet(String name) {
		this.reuseModelName = name;
	}
	
	public void setCaching(String bool) {
		if (bool.equals("true")) {
			this.cachingEnabled = true;
		} else {
			this.cachingEnabled = false;
		}
	}
	
	public void setMetamodelUri(String uri) {
		this.nsURI = uri;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setModelUri(String uri) {
		this.uri = uri;
	}
}
