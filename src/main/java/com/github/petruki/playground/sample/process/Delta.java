package com.github.petruki.playground.sample.process;


public class Delta extends AbstractProcess {

	public Delta() {
		super("Delta`s Job");
		initActions();
	}
	
	public void initActions() {
		addAction(new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 10));
		addAction(new ActionProcess(ActionProcess.ACT.HOLD.ordinal(), 10));
		addAction(new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 10));
		addAction(new ActionProcess(ActionProcess.ACT.HOLD.ordinal(), 5));
		addAction(new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 5));
	}

}
