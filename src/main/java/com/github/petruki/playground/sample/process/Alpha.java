package com.github.petruki.playground.sample.process;


public class Alpha extends AbstractProcess {

	public Alpha() {
		super("Alpha`s Job");
		initActions();
	}
	
	public void initActions() {
		addAction(new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 10));
	}
}
