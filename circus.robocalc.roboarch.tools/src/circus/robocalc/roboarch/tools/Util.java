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

import java.util.List;

//import org.eclipse.ant.internal.*;
//import org.apache.tools.ant.Project;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.eol.tools.AbstractTool;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.ui.resource.LiveScopeResourceSetInitializer;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;

import com.google.inject.Injector;

import circus.robocalc.roboarch.textual.ui.internal.TextualActivator;
import circus.robocalc.roboarch.textual.validation.RoboArchValidator;
import circus.robocalc.roboarch.emf.InMemorySingleResourceModel;

public class Util extends AbstractTool {
	
	// See https://www.eclipse.org/forums/index.php/t/1102302/
	// for details on why this is necessary.
	LiveScopeResourceSetInitializer lxrp;
	IResourceValidator validator;
	ResourceSet rsgb;
	Injector inj;
	
	protected Object project; 
	// We should not use ant classes because we're a plugin!
	protected ModelRepository repository;
	  	
	public Util() {
		TextualActivator act = TextualActivator.getInstance();
		inj = act.getInjector("circus.robocalc.robochart.textual.RoboChart");
		
		lxrp = inj.getInstance(LiveScopeResourceSetInitializer.class);
		validator = inj.getInstance(IResourceValidator.class);
		
		rsgb = new XtextResourceSet();
		lxrp.initialize(rsgb);
	}
	
	public RoboArchValidator getValidator() {
		return inj.getInstance(RoboArchValidator.class);
	}
	
	public void Init() {
		this.project = context.getFrameStack().get("project");
	}
	
	public Util(Object project)
	{
		this.project = project;
	}
	
	public void giveLiveScope(ResourceSet rs) {
		lxrp.initialize(rs);
		
	}
	
	public Object getProjectFromSettings() {
/*		Project antProject = new Project();
		Variable var = ;
		ClassLoader loader = var.getClass().getClassLoader();
		ClassLoader loader2 = antProject.getClass().getClassLoader();*/
		return context.getFrameStack().get("project");
	}
	
	public Object getProject() {
		return this.project;
	}
	/*
	public AntModelProject newAntModelProject() {
		AntModelProject antProject = new AntModelProject();
		return antProject;
	}
	
	public Project getProject(Object o) {
		return Project.getProject(o);
	}*/

	/*public ModelRepository createModelRepository2(org.eclipse.ant.internal.ui.model.AntModelProject project) {

		if (this.repository == null) {
			this.repository = new ModelRepository();
			ShutdownProjectRepositoryListener.activate(project, repository);
		}
		return repository;
	}*/
	
	
	public ModelRepository createModelRepository(Object project) {

		if (this.repository == null) {
			this.repository = new ModelRepository();
		}
		return repository;
	}
	
	public ModelRepository createModelRepository() {
		
		if (this.repository == null) {
			this.repository = new ModelRepository();
		}
		return repository;
		
	}
	
	public IModel createXtextModelNew(EObject eObject, String name, String uri) {
		
		ResourceSet rs = new XtextResourceSet();
		lxrp.initialize(rs);
		
		Resource r = rs.createResource(EmfUtil.createPlatformResourceURI(uri));
		r.getContents().add(eObject);
		InMemoryEmfModel model = new InMemoryEmfModel(name, r);
		model.setMetamodelUri(uri);
		model.setCachingEnabled(false);
		repository.addModel(model);
		return model;	
	}
	
	public void resolveDependencies(IModel model) {
		if (model instanceof EmfModel) {
			ResourceSet rs = ((EmfModel) model).getResource().getResourceSet();
			EcoreUtil.resolveAll(rs);
		}
	}
	
	public void validateResource(EObject eObject) {
		validator.validate(eObject.eResource(), CheckMode.ALL, CancelIndicator.NullImpl);
	}
	
	public IModel createXtextModelNew(String name, String uri, List<String> nsURIs) {
		
		ResourceSet rs = new XtextResourceSet();
		lxrp.initialize(rs);
		
		Resource r = rs.createResource(EmfUtil.createPlatformResourceURI(uri));
		InMemoryEmfModel model = new InMemoryEmfModel(name, r, nsURIs.toArray(String[]::new));
		model.setMetamodelUris(nsURIs);
		model.setCachingEnabled(false);
		repository.addModel(model);
		return model;
	}	
	
	public IModel createXtextModel(EObject eObject, String name, String uri) {
		//XtextResourceSet rs = (XtextResourceSet) injector.getInstance(XtextLiveScopeProv)
		//IResourceSetProvider rsp = injector.getInstance(IResourceSetProvider.class);
		//rsp.
		//XtextLiveScopeResourceSetProvider xrsp = injector.getInstance(XtextLiveScopeResourceSetProvider.class);

		//Activator.getDefault()
		//RoboChartRuntimeModule rsi2 = injector.getInstance(RoboChartRuntimeModule.class);
		Resource r = rsgb.createResource(EmfUtil.createPlatformResourceURI(uri));
		r.getContents().add(eObject);
		IModel model = new InMemoryEmfModel(name, r);
		//context.getModelRepository().addModel(model);
		repository.addModel(model);
		return model;		
	}
	
	public IModel createXtextModel(String name, String uri, List<String> nsURIs) {
		Resource r = rsgb.createResource(EmfUtil.createPlatformResourceURI(uri));
		IModel model = new InMemoryEmfModel(name, r, nsURIs.toArray(String[]::new));
		repository.addModel(model);
		return model;
	}	
	
	public IModel createModelInExistingResourceSet(EObject eObject, String name, String uri, List<String> nsURIs) {
		ResourceSet rs = eObject.eResource().getResourceSet();
		Resource r = rs.createResource(EmfUtil.createPlatformResourceURI(uri));
		IModel model = new InMemorySingleResourceModel(name, r, nsURIs.toArray(String[]::new));
		repository.addModel(model);
		return model;
	}
	
	public IModel createModelInExistingResourceSetAdd(EObject eObject, String name, String uri, List<String> nsURIs) {
		ResourceSet rs = eObject.eResource().getResourceSet();
		Resource r = rs.createResource(EmfUtil.createPlatformResourceURI(uri));
		IModel model = new InMemorySingleResourceModel(name, r, nsURIs.toArray(String[]::new));
		//r.getContents().add(eObject);
		repository.addModel(model);
		return model;//ResourceSet rs = eObject.eResource().getResourceSet();
		//Resource r = rs.createResource(EmfUtil.createPlatformResourceURI(uri));
		//
		//IModel model = new InMemorySingleResourceModel(name, eObject.eResource(), nsURIs.toArray(String[]::new));
		//repository.addModel(model);
		//return model;
	}	
	
	public IModel createModelInExistingResourceSet(ModelRepository repository, EObject eObject, String name, String uri, List<String> nsURIs) {
		ResourceSet rs = eObject.eResource().getResourceSet();
		Resource r = rs.createResource(EmfUtil.createPlatformResourceURI(uri));
		IModel model = new InMemorySingleResourceModel(name, r, nsURIs.toArray(String[]::new));
		repository.addModel(model);
		return model;
	}
	
//	public void moveIntoResource(EObject eObject, String uri) {
	//	eObject.eResource().setURI
//	}
	
	public IModel createModel(EObject eObject, String name) {
		IModel model = this.repository.getModelByNameSafe(name);
		if (model == null) {
			model = new InMemoryEmfModel(name, eObject.eResource());
			this.repository.addModel(model);
		}
		return model;
	}
	
	public IModel createModel(EObject eObject, String name, String... nsURIs) {
		IModel model = this.repository.getModelByNameSafe(name);
		if (model == null) {
			model = new InMemoryEmfModel(name, eObject.eResource(), nsURIs);
			this.repository.addModel(model);
		}
		return model;
	}
	
	public IModel createModel(EObject eObject, String name, List<String> nsURIs) {
		IModel model = this.repository.getModelByNameSafe(name);
		if (model == null) {
			model = new InMemoryEmfModel(name, eObject.eResource(), nsURIs.toArray(String[]::new));
			this.repository.addModel(model);
		}
		return model;
	}
	
	public IModel createModel(EObject eObject, String name, String uri) {
		ResourceSet rs = new ResourceSetImpl();
		Resource r = rs.createResource(EmfUtil.createPlatformResourceURI(uri));
		r.getContents().add(eObject);
		IModel model = new InMemoryEmfModel(name, r);
		repository.addModel(model);
		return model;
	}
	
	public IModel createModel(String name, String uri) {
		ResourceSet rs = new ResourceSetImpl();
		Resource r = rs.createResource(EmfUtil.createPlatformResourceURI(uri));
		IModel model = new InMemoryEmfModel(name, r);
		repository.addModel(model);
		return model;
	}
	
	public IModel createModel(EObject eObject, String name, String uri, List<String> nsURIs) {
		ResourceSet rs = new ResourceSetImpl();
		Resource r = rs.createResource(EmfUtil.createPlatformResourceURI(uri));
		r.getContents().add(eObject);
		IModel model = new InMemoryEmfModel(name, r, nsURIs.toArray(String[]::new));
		repository.addModel(model);
		return model;
	}
	
	public IModel createModel(String name, String uri, List<String> nsURIs) {
		ResourceSet rs = new ResourceSetImpl();
		Resource r = rs.createResource(EmfUtil.createPlatformResourceURI(uri));
		IModel model = new InMemoryEmfModel(name, r, nsURIs.toArray(String[]::new));
		repository.addModel(model);
		return model;
	}
	
}
