package com.github.petruki.playground.sample.process;


public class Charlie extends AbstractProcess {

	public Charlie() {
		super("Charlie`s Job");
		initActions();
	}

	public void initActions() {
		addAction(new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 10));
		addAction(new ActionProcess(ActionProcess.ACT.HOLD.ordinal(), 5));
		addAction(new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 10));
	}
}
