package com.github.petruki.jcpu.sim;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class Job extends Process {

    private int cpuWaitTime;
    private int lastTime;
    private CPU runningOnCPU;

    protected Job(String name) {
        super(name);
        cpuWaitTime = 0;
        runningOnCPU = null;
    }

    public void removeJob() {
        System.out.println("Time: " + Scheduler.clock + ": " + getName() + "\n" +
                "[job deleted]\n" +
                "[Total waiting time from CPU: " + cpuWaitTime + "]\n");
        setSleep(lastTime - Scheduler.clock);
    }

    public void run(int left) {
        while (left > 0) {
            if (!Machine.getInstance().getIdle_CPUs().isEmpty() && Machine.getInstance().getWaiting_jobs().isEmpty()) {
                runningOnCPU = (CPU) Machine.getInstance().getIdle_CPUs().dequeue();
            }

            if (runningOnCPU == null) {
                cpuWaitTime -= Scheduler.clock;
                waitQueue(Machine.getInstance().getWaiting_jobs());
                cpuWaitTime += Scheduler.clock;
            }

            if (runningOnCPU == null) {
                Scheduler.fatalError("CPU is inactive");
                return;
            }

            int runFor;
            if (runningOnCPU.getTimeSlice() == 0 || left <= runningOnCPU.getTimeSlice()) {
                runFor = left;
            } else {
                runFor = runningOnCPU.getTimeSlice();
            }

            System.out.println("Time: " + Scheduler.clock + ": " + getName() + "\n" +
                    "CPU: " + runningOnCPU.getName() + " [running]\n");
            setSleep(lastTime - Scheduler.clock);
            lastTime = Scheduler.clock;
            Scheduler.hold(runFor);

            System.out.println("Time: " + Scheduler.clock + ": " + getName() + "\n" +
                    "CPU: " + runningOnCPU.getName() + " [stopped]\n");
            runningOnCPU.setBusyTime(runningOnCPU.getBusyTime() + runFor);
            left -= runFor;

            setRuning(lastTime - Scheduler.clock);
            lastTime = Scheduler.clock;

            Machine.getInstance().getIdle_CPUs().enqueue(runningOnCPU);
            runningOnCPU = null;

            if (!Machine.getInstance().getWaiting_jobs().isEmpty()) {
                Job next = (Job) Machine.getInstance().getWaiting_jobs().dequeue();
                next.runningOnCPU = (CPU) Machine.getInstance().getIdle_CPUs().dequeue();
                Scheduler.activate(next);
            }
        }
    }

    @Override
    public void print() {
        if (runningOnCPU != null) {
            System.out.println(getName() + " is [running]");
            Machine.getInstance().print(runningOnCPU);
        } else {
            System.out.println(getName() + " is [stopped]");
        }
    }

}