package com.github.petruki.playground.sample.process;


public class Bravo extends AbstractProcess {

	public Bravo() {
		super("Bravo`s Job");
		initActions();
	}
	
	public void initActions() {
		addAction(new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 15));
	}

}
