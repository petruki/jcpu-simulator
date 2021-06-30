package com.github.petruki.playground.sample;

import com.github.petruki.playground.sample.process.AbstractProcess;
import com.github.petruki.playground.sample.process.Bravo;
import com.github.petruki.playground.sample.process.Charlie;

public class Scenario1 {
	
	public Scenario1() {
		init();
	}

	private void init() {
		AbstractProcess process = new Bravo();
		AbstractProcess process2 = new Charlie();
		
		process2.setInstant(2);
		
		Custom scenario = new Custom();
		scenario.addCPU(new Custom.CPUParam("Yorkfield", 0));
		scenario.addCPU(new Custom.CPUParam("Bloomfield", 0));
		scenario.addProcess(process);
		scenario.addProcess(process2);
		
		scenario.startSimulation();
	}
	
}