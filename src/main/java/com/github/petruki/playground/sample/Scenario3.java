package com.github.petruki.playground.sample;

import com.github.petruki.playground.sample.process.AbstractProcess;
import com.github.petruki.playground.sample.process.Alpha;
import com.github.petruki.playground.sample.process.Bravo;
import com.github.petruki.playground.sample.process.Charlie;
import com.github.petruki.playground.sample.process.Delta;

public class Scenario3 {
	
	public Scenario3() {
		init();
	}

	private void init() {
		AbstractProcess process = new Alpha();
		AbstractProcess process2 = new Bravo();
		AbstractProcess process3 = new Charlie();
		AbstractProcess process4 = new Delta();
		
		process.setInstant(2);
		process3.setInstant(4);
		process4.setInstant(4);
		
		Custom scenario = new Custom();
		scenario.addCPU(new Custom.CPUParam("Penryn", 4));
		scenario.addProcess(process);
		scenario.addProcess(process2);
		
		scenario.startSimulation();
	}
}