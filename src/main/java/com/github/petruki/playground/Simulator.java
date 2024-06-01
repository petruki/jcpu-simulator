package com.github.petruki.playground;

import com.github.petruki.playground.sample.Custom;
import com.github.petruki.playground.sample.Scenario6;
import com.github.petruki.playground.sample.process.AbstractProcess;
import com.github.petruki.playground.sample.process.ActionProcess;

public class Simulator {
	
	public static void main(String[] args) {
		new Scenario6();
	}
	
	public static void executeGenericScenario() {
		var action = new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 10);
		var action2 = new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 3);
		
		var process = new AbstractProcess("Proc I");
		var process2 = new AbstractProcess("Proc II");
		var process3 = new AbstractProcess("Proc III");
		
		process3.setInstant(2);
		process2.setInstant(3);
		
		process.addAction(action);
		process.addAction(action2);
		process2.addAction(action);
		process3.addAction(action);
		
		var scenario = new Custom();
		scenario.addCPU(new Custom.CPUParam("Computer", 5));
		scenario.addProcess(process);
		scenario.addProcess(process2);
		scenario.addProcess(process3);
		
		scenario.startSimulation();
	}
	
}

