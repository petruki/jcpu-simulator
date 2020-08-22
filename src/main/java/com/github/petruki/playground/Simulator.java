package com.github.petruki.playground;

import com.github.petruki.playground.sample.Custom;
import com.github.petruki.playground.sample.process.AbstractProcess;
import com.github.petruki.playground.sample.process.ActionProcess;

public class Simulator {
	
	public static void main(String args[]) {
		executeGenericScenario();
	}
	
	public static void executeGenericScenario() {
		ActionProcess action = new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 10);
		ActionProcess action2 = new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 3);
		
		AbstractProcess process = new AbstractProcess("Proc I");
		AbstractProcess process2 = new AbstractProcess("Proc II");
		AbstractProcess process3 = new AbstractProcess("Proc III");
		
		process3.setInstante(2);
		process2.setInstante(3);
		
		process.addAction(action);
		process.addAction(action2);
		process2.addAction(action);
		process3.addAction(action);
		
		Custom scenario = new Custom();
		scenario.addCPU(new Custom.CPUParam("Computer", 5));
		scenario.addProcess(process);
		scenario.addProcess(process2);
		scenario.addProcess(process3);
		
		scenario.startSimulation();
	}
}

