package com.github.petruki.jcpu.sim;

public class CPU {
	
	private String name;
	private int time_slice;	
	private int busy_time;	
	private int const_time;

	public CPU(String name, int quantum) {
		this.name = name;
		time_slice = quantum;
		const_time = Scheduler.clock;
		busy_time = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTime_slice() {
		return time_slice;
	}

	public void setTime_slice(int time_slice) {
		this.time_slice = time_slice;
	}

	public int getBusy_time() {
		return busy_time;
	}

	public void setBusy_time(int busy_time) {
		this.busy_time = busy_time;
	}

	public int getConst_time() {
		return const_time;
	}

	public void setConst_time(int const_time) {
		this.const_time = const_time;
	}
	
}