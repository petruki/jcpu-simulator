package com.github.petruki.jcpu.sim;

abstract public class Job extends Process {

	private int CPU_wait_time;
	private int lastTime;
	private CPU running_on_CPU;

	public Job(String name) {
		super(name);
		CPU_wait_time = 0;
		running_on_CPU = null;
	}
	
	public void remove_job() {
		System.out.println("Time: " + Scheduler.clock + ": " + getName() + "\n" +
						   "[job deleted]\n" +
						   "[Total waiting time from CPU: " + CPU_wait_time + "]\n");
		setSleep(lastTime - Scheduler.clock);
	}
	
	public void run(int left) {
		while(left > 0) {
			if (!Machine.getInstance().getIdle_CPUs().isEmpty() && Machine.getInstance().getWaiting_jobs().isEmpty())
				running_on_CPU = (CPU) Machine.getInstance().getIdle_CPUs().dequeue();
			
			if (running_on_CPU == null) {
				CPU_wait_time -= Scheduler.clock;
				waitq(Machine.getInstance().getWaiting_jobs());
				CPU_wait_time += Scheduler.clock;
			}

			if(running_on_CPU == null)
				Scheduler.fatal_error("CPU inativa");

			int run_for;
			if (running_on_CPU.getTime_slice() == 0 || left <= running_on_CPU.getTime_slice()) 
				run_for = left;
			else 
				run_for = running_on_CPU.getTime_slice();
			
			System.out.println("Time: " + Scheduler.clock + ": " + getName() + "\n" +
							   "CPU: " + running_on_CPU.getName() + " [running]\n");
			setSleep(lastTime - Scheduler.clock);
			lastTime = Scheduler.clock;
			Scheduler.hold(run_for);
			
			System.out.println("Time: " + Scheduler.clock + ": " + getName() + "\n" +
							   "CPU: " + running_on_CPU.getName() + " [stopped]\n");
			running_on_CPU.setBusy_time(running_on_CPU.getBusy_time() + run_for);
			left -= run_for;
			
			setRuning(lastTime - Scheduler.clock);
			lastTime = Scheduler.clock;
			
			Machine.getInstance().getIdle_CPUs().enqueue(running_on_CPU);
			running_on_CPU = null;		
			
			if (!Machine.getInstance().getWaiting_jobs().isEmpty()) {
				Job next = (Job) Machine.getInstance().getWaiting_jobs().dequeue();
				next.running_on_CPU = (CPU) Machine.getInstance().getIdle_CPUs().dequeue();
				Scheduler.activate(next);
			}
		} 
	}
	
	public void print() {
		if (running_on_CPU != null) {
			System.out.println(getName() + " is [running]");
			Machine.getInstance().print(running_on_CPU);
		} else 
			System.out.println(getName() + " is [stopped]");
	}
	
	public String getInfo() {
		return info;
	}

	public int getCPU_wait_time() {
		return CPU_wait_time;
	}

	public void setCPU_wait_time(int cpu_wait_time) {
		CPU_wait_time = cpu_wait_time;
	}

	public int getLastTime() {
		return lastTime;
	}

	public void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}

	public CPU getRunning_on_CPU() {
		return running_on_CPU;
	}

	public void setRunning_on_CPU(CPU running_on_CPU) {
		this.running_on_CPU = running_on_CPU;
	}
	
}