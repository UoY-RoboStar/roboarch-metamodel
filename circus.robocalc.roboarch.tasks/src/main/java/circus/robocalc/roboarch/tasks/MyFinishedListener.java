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

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.SubBuildListener;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.workflow.tasks.ShutdownProjectRepositoryListener;

public class MyFinishedListener implements SubBuildListener {

	private static MyFinishedListener INSTANCE = null;
	//private static ShutdownProjectRepositoryListener listener;
	private Project project;
	private Stack<ModelRepository> repositories;
	private Stack<Boolean> disposeAlways;
	
	public static void activate(Project project, ModelRepository repository) {
		activate(project, repository, true);
	}
	
	public static void activate(Project project, ModelRepository repository, Boolean disposeAlways) {
		
		if (INSTANCE == null) {
			INSTANCE = new MyFinishedListener();
			/*ShutdownProjectRepositoryListener.activate(project, repository);
			for (BuildListener bl : project.getBuildListeners()) {
				if (bl instanceof ShutdownProjectRepositoryListener) {
					listener = (ShutdownProjectRepositoryListener) bl;
					break;
				}
			}*/
			INSTANCE.repositories = new Stack<ModelRepository>();
			INSTANCE.disposeAlways = new Stack<Boolean>();//new HashSet<ModelRepository>();
			INSTANCE.repositories.push(repository);
			INSTANCE.disposeAlways.push(disposeAlways);
			INSTANCE.project = project;
			project.addBuildListener(INSTANCE);
			project.log("Registered MyFinishedListener for project.");
		} else {
			INSTANCE.repositories.push(repository);
			INSTANCE.disposeAlways.push(disposeAlways);
		}
	}
	
	private MyFinishedListener() {
		//ShutdownProjectRepositoryListener.activate(project, repository);
	}
	
	public void subBuildStarted(BuildEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void subBuildFinished(BuildEvent event) {
		// TODO Auto-generated method stub
		
		project.log("subBuildFinished");
		buildFinished(event);
		// There's been an error, so call the original buildFinished on ShutdownProjectRepositoryListener
//		if (event.getException() != null) {
//			//if (listener != null) {
//				project.log("subBuildFinished with an exception. Cleaning up model repositories.");
//				for (ModelRepository repository : repositories) {
//					for (IModel model : repository.getModels()) {
//						model.setStoredOnDisposal(false);
//						project.log("Disposing of model: " + model.getName());
//					}
//					project.log("Disposing model repository:" + repository.toString());
//					repository.dispose();
//				}
//				//listener.buildFinished(event);
//			//} else {
//			//	project.log("subBuildFinished with an exception, but no ShutdownProjectListener could be found. This means the model repository will not be cleaned properly in memory, and subsequent runs may be affected.");
//			//}
//		}
	}

	public void buildStarted(BuildEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void buildFinished(BuildEvent event) {
		// TODO Auto-generated method stub
		if (event.getException() != null) {
			project.log("Build finished with errors.");
		} else {
			project.log("Build finished with no errors.");
		}
			
		// If there's an error we dispose of all model repositories in scope
		if (event.getException() != null) {
			for (ModelRepository repository : new HashSet<ModelRepository>(repositories)) {
				for (IModel model : repository.getModels()) {
					model.setStoredOnDisposal(false);
					project.log("Setting setStoredOnDisposal to false for model: " + model.getName());
				}
				project.log("Disposing model repository:" + repository.toString());
				repository.dispose();
			}
		} else {
			ModelRepository repository = repositories.pop();
			Boolean dispose = disposeAlways.pop();
			if (dispose)
			{
				project.log("Disposing model repository:" + repository.toString());
				repository.dispose();
			}
		}
		// Otherwise, we pop and only act on the last one
//		Boolean dispose = INSTANCE.disposeAlways.pop();
//		ModelRepository repository = 
//		
//		if (event.getException() != null || disposeAlways) {
//			project.log("Disposing...");
//			for (ModelRepository repository : repositories) {
//				if (event.getException() != null) {
//					for (IModel model : repository.getModels()) {
//						model.setStoredOnDisposal(false);
//						project.log("Setting setStoredOnDisposal to false for model: " + model.getName());
//					}
//				}
//				project.log("Disposing model repository:" + repository.toString());
//				repository.dispose();
//			}
//		}
	}

	public void targetStarted(BuildEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void targetFinished(BuildEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void taskStarted(BuildEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void taskFinished(BuildEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void messageLogged(BuildEvent event) {
		// TODO Auto-generated method stub
		
	}

}
