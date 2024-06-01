package com.github.petruki.playground.sample.process;

import lombok.Data;

@Data
public class ActionProcess {
	
	public enum ACT {
		RUN,
		HOLD;
	}
	
	private int action;
	private int time;
	
	public ActionProcess(int action, int time) {
		this.action = action;
		this.time = time;
	}
	
}
