package com.github.petruki.jcpu.sim;

import com.github.petruki.jcpu.queues.PriorityQueue;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Scheduler {

    public static int clock = 0;
    public static PriorityQueue eventList = new PriorityQueue();
    public static Process current = null;

    public static final int IDLE = 1;
    public static final int RUNNING = 2;
    public static final int TERMINATED = 3;

    public static Integer done = 0;
    public static int liveThreads = 0;

    public static void fatalError(String s) {
        System.err.println("FATAL: " + s);
        System.exit(-1);
    }

    public static void hold(int delay) {
        reactivate(current, delay);
        nextEvent(false);
    }

    public static void passivate() {
        if (current.scheduled()) {
            unSchedule(current);
        }

        if (current.terminated()) {
            current.print();
            fatalError("Passivate terminated process");
        }

        nextEvent(false);
    }

    public static void activate(Process p) {
        activate(p, 0);
    }

    public static void activate(Process p, int time) {
        if (p.active())
            return;
        if (p.getState() == TERMINATED) {
            p.print();
            fatalError("Activating terminated process");
        }
        schedule(p, clock + time);
    }

    public static void reactivate(Process p) {
        reactivate(p, 0);
    }

    public static void reactivate(Process p, int t) {
        if (p.getState() == TERMINATED) {
            p.print();
            fatalError("Reactivating terminated process");
        }

        if (p.scheduled()) {
            unSchedule(p);
        }

        schedule(p, clock + t);
    }

    public static void runSimulation() {
        current = null;
        nextEvent(false);
        System.out.println("<< Simulation has finished >>\n");
    }

    public static void unSchedule(Process p) {
        if (p.getEvTime() < 0) {
            p.print();
            fatalError("Unscheduling unsheduled process");
        }

        if (!eventList.remove(p)) {
            p.print();
            fatalError("Active process not found on the event list");
        } else {
            p.setState(IDLE);
            p.setEvTime(-1);
        }
    }

    public static void schedule(Process p, int time) {
        if (p.getState() == TERMINATED || p.getEvTime() >= 0) {
            p.print();
            printEvList();
            fatalError("End of schedule or active process");
        }
        if (time < clock) {
            p.print();
            printEvList();
            System.out.println("(ev_time=" + time + ", clock=" + clock + ")");
            fatalError("Scheduled event passed");
        }

        p.setEvTime(time);
        eventList.enqueue(p, time);
    }

    public static void nextEvent(boolean die) {
        if (eventList.isEmpty()) {
            liveThreads = 0;
            synchronized (done) {
                done.notify();
            }
            return;
        }

        if (clock > ((Process) eventList.front()).getEvTime()) {
            printEvList();
            fatalError("Event list not ordered yet");
        }

        Process next = (Process) eventList.dequeue();
        clock = next.getEvTime();
        next.setEvTime(-1);

        passBaton(next, die);
    }

    public static void passBaton(Process p) {
        passBaton(p, false);
    }

    public static void passBaton(Process p, boolean die) {
        Process caller = current;
        if (p.getState() == TERMINATED)
            fatalError("Attempting to assign terminated task");

        current = p;
        if (die) {
            caller.setState(TERMINATED);
            liveThreads--;
            synchronized (done) {
                done.notify();
            }
        }

        if (caller == null || !caller.equals(p)) {
            synchronized (p) {
                p.setState(RUNNING);
                p.notify();
            }

            if (caller == null) {
                synchronized (done) {
                    try {
                        while (liveThreads > 0) {
                            done.wait();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                return;
            }

            if (!die) {
                caller.setState(IDLE);
                caller.block();
            } else
                caller.getMythread().interrupt();
        }
    }

    public static void printEvList() {
        System.out.println("Current event list " + clock + ":");
        eventList.print();
        System.out.println("End of event list\n");
    }
}

