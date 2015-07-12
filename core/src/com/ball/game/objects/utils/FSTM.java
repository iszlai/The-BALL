package com.ball.game.objects.utils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FSTM {

	private States currentState;

	public FSTM(States state) {
		//System.out.println("Started in state "+state);
		this.currentState=state;
		init();
	}
	
	Map<States,List<States>> validStates ;
	
	
	private void init(){
		validStates=new HashMap<States, List<States>>();
		validStates.put(States.INVISIBLE, Arrays.asList (States.VISIBLE));
		validStates.put(States.VISIBLE, Arrays.asList (States.INVISIBLE,States.SPELL_ACTIVE));
		validStates.put(States.SPELL_ACTIVE, Arrays.asList (States.SPELL_INACTIVE));
		validStates.put(States.SPELL_INACTIVE, Arrays.asList (States.INVISIBLE));
	}
	
	private boolean isTransitionValid(States targetState){
		return validStates.get(getCurrentState()).contains(targetState);
	}

	public States getCurrentState() {
		//System.out.println("Current state "+currentState);
		return currentState;
	}

	public void transitionTo(States targetState){
		//System.out.println(String.format("Transitioning [%s] -> [%s]",currentState,targetState));
		if(isTransitionValid(targetState)){
			currentState=targetState;
		//	System.out.println("Transition successfull");
		}
	}
	
	
	
	public enum States {
		VISIBLE,INVISIBLE,SPELL_ACTIVE,SPELL_INACTIVE
	}	
}
