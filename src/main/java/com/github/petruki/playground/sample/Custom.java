package com.github.petruki.playground.sample;

import java.util.LinkedList;
import java.util.List;

import com.github.petruki.jcpu.sim.CPU;
import com.github.petruki.jcpu.sim.Machine;
import com.github.petruki.jcpu.sim.Scheduler;
import com.github.petruki.playground.sample.process.AbstractProcess;
import lombok.Data;

public class Custom {

    private final List<AbstractProcess> processes;
    private final List<CPUParam> cpus;

    public Custom() {
        processes = new LinkedList<>();
        cpus = new LinkedList<>();
    }

    public Custom(List<AbstractProcess> processes, List<CPUParam> cpus) {
        this.processes = processes;
        this.cpus = cpus;
    }

    public void startSimulation() {
        LinkedList<CPU> temp = new LinkedList<>();
        for (int i = 0; i < cpus.size(); i++) {
            temp.add(new CPU(cpus.get(i).getName(), cpus.get(i).getQuantum()));
            Machine.getInstance().addCPU(temp.get(i));
        }

        for (AbstractProcess process : processes)
            Scheduler.activate(process, process.getInstant());

        printInfo();
        Scheduler.runSimulation();

        for (CPU cpu : temp) {
            Machine.getInstance().remove_cpu(cpu);
        }

        printLifeTime();
    }

    private void printInfo() {
        System.out.println("#############################");
        System.out.println("# Custom Scenario: ");
        System.out.println("# CPUs: " + cpus.size());

        for (CPUParam cpuParam : cpus) {
            System.out.println("# - " + cpuParam.name);
            System.out.println("#      quantum=" + cpuParam.quantum);
            System.out.println("#");
        }

        for (AbstractProcess process : processes) {
            System.out.println("# " + process.getName());
            System.out.println("# Start at: " + process.getInstant());
            System.out.print(process.getInfo());
            System.out.println("#");
        }

        System.out.println("#############################\n");
    }

    private void printLifeTime() {
        System.out.println();
        for (AbstractProcess process : processes) {
            System.out.printf("%s - w: %s \n", process.getName(), process.getCpuWaitTime());
            System.out.println(process.getLifeTime());
        }
        System.out.println();
        System.out.println("- : IDLE\n" +
                "# : RUNNING\n" +
                "| : STARTED\n" +
                "w : WAITING");
    }

    @Data
    public static class CPUParam {
        private String name;
        private int quantum;

        public CPUParam(String name, int quantum) {
            this.name = name;
            this.quantum = quantum;
        }
    }

    public void addProcess(AbstractProcess process) {
        processes.add(process);
    }

    public void removeProcess(AbstractProcess process) {
        processes.remove(process);
    }

    public void clearProcess() {
        processes.clear();
    }

    public void addCPU(CPUParam cpu) {
        cpus.add(cpu);
    }

    public void removeCPU(CPUParam cpu) {
        cpus.remove(cpu);
    }

    public void clearCPUs() {
        cpus.clear();
    }

}
