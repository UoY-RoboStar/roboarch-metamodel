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

package circus.robocalc.roboarch.util;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import circus.robocalc.roboarch.CSkill;
import circus.robocalc.roboarch.DSkill;
import circus.robocalc.roboarch.Layer;
import circus.robocalc.roboarch.Skill;
import circus.robocalc.robochart.ConnectionNode;
import circus.robocalc.robochart.Event;
import circus.robocalc.robochart.RoboticPlatform;
import circus.robocalc.robochart.Variable;

public class Model {
/*
 *   Some helper utility methods that implement relationships that are not directly part of the metamodel.
 * 
 *      TODO Consider integrating into the model
 * */
	static public List<Variable> getSkillConnectionInputEvents(Skill superSkill) {
		List<Variable> inputEvents = new ArrayList<Variable>();

		if (superSkill instanceof CSkill) {
			inputEvents.addAll( ((CSkill) superSkill).getInputs() );
					
		} else if (superSkill instanceof DSkill){
			//TODO not strictly necessary to handle differently due to metamodel revision - left as example on model helper functions
			inputEvents.addAll( ((DSkill) superSkill).getInputs() );
			
		} else {
			//All skills have been handled.
		}
		
		return inputEvents;
	}
	
	static public List<Variable> getSkillConnectionOutputEvents(Skill superSkill) {
		List<Variable> outputEvents = new ArrayList<Variable>();

		if (superSkill instanceof CSkill) {
			outputEvents.addAll( ((CSkill) superSkill).getOutputs() );
					
		} else if (superSkill instanceof DSkill){
			//TODO not strictly necessary to handle differently due to metamodel revision - left as example on model helper functions
			outputEvents.addAll( ((DSkill) superSkill).getOutputs() );
			
		} else {
			//All skills have been handled.
		}
		
		return outputEvents;
	}
	
	
	static public boolean isEventLayerInput(Layer lyr, Event theEvent) {
		
		return ( lyr.getInputs().contains(theEvent) );
	}
	
	static public boolean isEventLayerOutput(Layer lyr, Event theEvent) {
		
		return ( lyr.getOutputs().contains(theEvent) );
	}
	
	/*static public List<Event> getConnectioNodeEventsEvents(Skill theNode) {
		List<Event> nodeEvents = new ArrayList<Event>();
		
		if (theNode instanceof Layer) {
			Layer lyr = (Layer)  theNode;
			nodeEvents.addAll( lyr.getInputs() );
			nodeEvents.addAll( lyr.getOutputs() );
			
		} else if (theNode instanceof RoboticPlatform) {
			RoboticPlatform platform = (RoboticPlatform) theNode;
			platform.
		} else {
			// Nothing to add
		}
		
		return nodeEvents;
	} */
	
}
