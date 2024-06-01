package com.github.petruki.jcpu.sim;

import com.github.petruki.jcpu.queues.Queue;
import lombok.Data;

@Data
abstract public class Process implements Runnable {

    private String name;
    private int state;
    private int evTime;
    private int evSleep;
    private Thread mythread;

    protected String info;
    protected int instant;

    protected StringBuilder lifeTime;

    Process(String name) {
        this.name = name;
        evTime = -1;
        lifeTime = new StringBuilder();
        state = Scheduler.IDLE;
        Scheduler.liveThreads++;
        mythread = new Thread(this);
        mythread.setName(name);
        mythread.start();
    }

    public abstract void body();

    public void block() {
        synchronized (this) {
            try {
                while (state != Scheduler.RUNNING && state != Scheduler.TERMINATED) {
                    wait();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void run() {
        block();
        body();
    }

    public boolean active() {
        return state == Scheduler.RUNNING || evTime >= 0;
    }

    public boolean scheduled() {
        return evTime >= 0;
    }

    public boolean terminated() {
        return (state == Scheduler.TERMINATED);
    }

    public void cancel() {
        if (scheduled())
            Scheduler.unSchedule(this);
    }

    public void terminate() {
        cancel();
        state = Scheduler.TERMINATED;
        evTime = -1;
        if (Scheduler.current == this) {
            Scheduler.nextEvent(true);
        }
    }


    public void waitQueue(Queue q) {
        q.enqueue(this);
        Scheduler.passivate();
    }

    public void print() {
        var stateToPrint = state == Scheduler.IDLE ? "IDLE" :
                state == Scheduler.RUNNING ? "RUNNING" : "TERMINATED";

        System.out.println(name + ": " + stateToPrint + " ev_time = " + evTime);
    }

    public void setRuning(int value) {
        if (value < 0) {
            value = value * -1;
        }

        while (value > 0) {
            lifeTime.append("#");
            value--;
        }
    }

    public void setSleep(int value) {
        if (value < 0) {
            value = value * -1;
        }

        while (value > 0) {
            lifeTime.append("-");
            value--;
        }
    }

    public String getLifeTime() {
        return lifeTime.toString();
    }

}