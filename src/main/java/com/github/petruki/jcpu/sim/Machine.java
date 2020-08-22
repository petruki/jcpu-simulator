package com.github.petruki.jcpu.sim;

import com.github.petruki.jcpu.queues.Queue;

public class Machine {
	
	private static Machine machine;
	private Queue idle_CPUs;
	private Queue waiting_jobs;
	
	private Machine() {
		idle_CPUs = new Queue();
		waiting_jobs = new Queue();
	}
	
	public static Machine getInstance() {
		if(machine == null)
			machine = new Machine();
		return machine;
	}
	
	public void addCPU(CPU cpu) {
		if(!waiting_jobs.isEmpty()) {
			Job	next = (Job) waiting_jobs.dequeue();
			next.setRunning_on_CPU(cpu);
			Scheduler.activate(next);
		} else
			idle_CPUs.enqueue(cpu);
	}
	
	public void print(CPU cpu) {
		System.out.println("Cycle = " + cpu.getBusy_time() / (double)(Scheduler.clock - cpu.getConst_time()) + "\n");
	}
	
	public void remove_cpu(CPU cpu) {
		idle_CPUs.remove(cpu);
		System.out.print("Time: " + Scheduler.clock + ": CPU " + cpu.getName() + "\n" +
						 "[core deleted]\n");
		print(cpu);
	}

	public Queue getIdle_CPUs() {
		return idle_CPUs;
	}

	public void setIdle_CPUs(Queue idle_CPUs) {
		this.idle_CPUs = idle_CPUs;
	}

	public Queue getWaiting_jobs() {
		return waiting_jobs;
	}

	public void setWaiting_jobs(Queue waiting_jobs) {
		this.waiting_jobs = waiting_jobs;
	}

}
