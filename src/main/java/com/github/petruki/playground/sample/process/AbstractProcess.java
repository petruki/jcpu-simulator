package com.github.petruki.playground.sample.process;

import java.util.LinkedList;
import java.util.List;

import com.github.petruki.jcpu.sim.Job;
import com.github.petruki.jcpu.sim.Scheduler;
import com.github.petruki.playground.sample.process.ActionProcess.ACT;

public class AbstractProcess extends Job {
	
	private List<ActionProcess> actions;
	
	public AbstractProcess(String name) {
		this(name, 0);
	}
	
	public AbstractProcess(String name, int instante) {
		this(name, new LinkedList<ActionProcess>(), instante);
	}
	
	public AbstractProcess(String name, LinkedList<ActionProcess> actions, int instante) {
		super(name);
		this.actions = actions;
		info = new String();
	}

	@Override
	public void body() {
		for(ActionProcess act : actions) {
			if(act.getAction() == ActionProcess.ACT.RUN.ordinal())
				run(act.getTime());
			else if(act.getAction() == ActionProcess.ACT.HOLD.ordinal())
				Scheduler.hold(act.getTime());
		}
		lifeTime.insert(instante, "|");
		remove_job();
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

	public int getInstante() {
		return instante;
	}
	
	public void setInstante(int instante) {
		this.instante = instante;
	}
	
}
