package com.github.petruki.playground.sample.process;

public class ActionProcess {
	
	public static enum ACT {
		RUN,
		HOLD;
	}
	
	private int action;
	private int time;
	
	public ActionProcess(int action, int time) {
		this.action = action;
		this.time = time;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
}
