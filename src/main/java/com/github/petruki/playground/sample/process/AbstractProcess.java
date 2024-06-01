package com.github.petruki.playground.sample.process;

import com.github.petruki.jcpu.sim.Job;
import com.github.petruki.jcpu.sim.Scheduler;
import com.github.petruki.playground.sample.process.ActionProcess.ACT;

import java.util.LinkedList;
import java.util.List;

public class AbstractProcess extends Job {

    private List<ActionProcess> actions;

    public AbstractProcess(String name) {
        this(name, 0);
    }

    public AbstractProcess(String name, int instant) {
        this(name, new LinkedList<>(), instant);
    }

    public AbstractProcess(String name, List<ActionProcess> actions, int instant) {
        super(name);
        this.actions = actions;
        this.info = "";
        this.instant = instant;
    }

    @Override
    public void body() {
        for (ActionProcess act : actions) {
            if (act.getAction() == ActionProcess.ACT.RUN.ordinal()) {
                run(act.getTime());
            } else if (act.getAction() == ActionProcess.ACT.HOLD.ordinal()) {
                Scheduler.hold(act.getTime());
                }
        }
        lifeTime.insert(instant, "|");
        removeJob();
        terminate();
    }

    public List<ActionProcess> getActions() {
        return actions;
    }

    public void setActions(List<ActionProcess> actions) {
        this.actions = actions;
    }

    public void addAction(ActionProcess action) {
        info += action.getAction() == ACT.RUN.ordinal() ? "#   run(" + action.getTime() + ")\n" :
                "#   hold(" + action.getTime() + ")\n";
        this.actions.add(action);
    }

}
