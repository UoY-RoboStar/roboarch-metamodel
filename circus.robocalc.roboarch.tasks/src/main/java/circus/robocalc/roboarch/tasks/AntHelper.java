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
import org.apache.tools.ant.ProjectHelper;
import org.eclipse.epsilon.workflow.tasks.EpsilonTask;

public class AntHelper extends EpsilonTask {

	@Override
	public void executeImpl() throws BuildException {
		// TODO Auto-generated method stub
		getProjectStackFrame().put("robopart2sim.anthelper.projecthelper", ProjectHelper.getProjectHelper());
		getProject().addReference("robopart2sim.anthelper.projecthelper", ProjectHelper.getProjectHelper());
	}

}
