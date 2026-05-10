package com.github.petruki.jcpu.sim;

import com.github.petruki.jcpu.queues.Queue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Machine {
	
	private static Machine machine;
	private Queue idleCPUs;
	private Queue waitingJobs;
	
	private Machine() {
		idleCPUs = new Queue();
		waitingJobs = new Queue();
	}
	
	public static Machine getInstance() {
		if(machine == null)
			machine = new Machine();
		return machine;
	}
	
	public void addCPU(CPU cpu) {
		if(!waitingJobs.isEmpty()) {
			Job	next = (Job) waitingJobs.dequeue();
			next.setRunningOnCPU(cpu);
			Scheduler.activate(next);
		} else
			idleCPUs.enqueue(cpu);
	}
	
	public void print(CPU cpu) {
		System.out.println("Cycle = " + cpu.getBusyTime() / (double)(Scheduler.clock - cpu.getConstTime()) + "\n");
	}
	
	public void removeCpu(CPU cpu) {
		idleCPUs.remove(cpu);
		System.out.print("Time: " + Scheduler.clock + ": CPU " + cpu.getName() + "\n" +
						 "[core deleted]\n");
		print(cpu);
	}

}
