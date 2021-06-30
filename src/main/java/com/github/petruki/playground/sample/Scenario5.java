package com.github.petruki.playground.sample;

import com.github.petruki.playground.sample.process.AbstractProcess;
import com.github.petruki.playground.sample.process.ActionProcess;

public class Scenario5 {
	
	public Scenario5() {
		init();
	}

	private void init() {
		AbstractProcess t1 = new AbstractProcess("t1");
		AbstractProcess t2 = new AbstractProcess("t2");
		AbstractProcess t3 = new AbstractProcess("t3");
		AbstractProcess t4 = new AbstractProcess("t4");
		
		ActionProcess run2 = new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 2);
		ActionProcess run3 = new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 3);
		ActionProcess run4 = new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 4);
		ActionProcess run5 = new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 5);
		
		t1.addAction(run5);
		t2.addAction(run2);
		t3.addAction(run4);
		t4.addAction(run3);
		
		t1.setInstant(0);
		t2.setInstant(0);
		t3.setInstant(1);
		t4.setInstant(3);
		
		Custom scenario = new Custom();
		scenario.addCPU(new Custom.CPUParam("Penryn", 2));
		scenario.addProcess(t1);
		scenario.addProcess(t2);
		scenario.addProcess(t3);
		scenario.addProcess(t4);
		
		scenario.startSimulation();
	}

}
