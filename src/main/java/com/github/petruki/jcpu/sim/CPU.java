package com.github.petruki.jcpu.sim;

import lombok.Data;

@Data
public class CPU {

    private String name;
    private int timeSlice;
    private int busyTime;
    private int constTime;

    public CPU(String name, int quantum) {
        this.name = name;
        timeSlice = quantum;
        constTime = Scheduler.clock;
        busyTime = 0;
    }

}