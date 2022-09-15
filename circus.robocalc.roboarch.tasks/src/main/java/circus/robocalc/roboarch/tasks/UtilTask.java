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

import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.workflow.tasks.EpsilonTask;

public class UtilTask extends EpsilonTask {
	
  protected Boolean disposeAlways = true; 

  @Override
  public void executeImpl() throws BuildException {
    // TODO Check whether we are being called in the normal way or not, to avoid
	//		doubly registering a ShutdownProjectRepositoryListener. Otherwise
	//		I suspect we may end-up calling dispose on a nonexistent object later.
	  MyFinishedListener.activate(getProject(), getProjectRepository(), disposeAlways);
  }
  
  public void setDisposeAlways(String bool) {
		if (bool.equals("true")) {
			this.disposeAlways = true;
		} else {
			this.disposeAlways = false;
		}	  
  }
  
}