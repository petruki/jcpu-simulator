package com.github.petruki.jcpu.sim;

import com.github.petruki.jcpu.queues.Queue;

abstract public class Process implements Runnable {

	private String name;
	private int state;
	private int ev_time;
	private int ev_sleep;
	private Thread mythread;
	
	protected String info;
	protected int instante;
	
	protected StringBuffer lifeTime;

	public Process(String name) {
		this.name = name;
		ev_time = -1;
		lifeTime = new StringBuffer();
		state = Scheduler.IDLE;
		Scheduler.live_threads++;
		mythread = new Thread(this);
		mythread.setName(name);
		mythread.start();
	}

	public abstract void body();

	public void block() {
		synchronized(this) {
			try {
				while (state != Scheduler.RUNNING && state != Scheduler.TERMINATED)
					wait();
			}
			catch(InterruptedException e) {}
		}
	}

	public synchronized void run() {
		block();
		body();
	}

	public boolean active() {
		return state == Scheduler.RUNNING || ev_time >= 0;
	}

	public boolean scheduled() {
		return ev_time >= 0;
	}

	public boolean terminated() {
		return (state == Scheduler.TERMINATED); 
	}

	public void cancel() {
		if (scheduled())
			Scheduler.unschedule(this);
	}

	public void terminate() {
		cancel();
		state = Scheduler.TERMINATED;
		ev_time = -1;
		if (Scheduler.current == this) 
			Scheduler.next_event(true);
	}


	public void waitq(Queue q) {
		q.enqueue(this);
		Scheduler.passivate();
	}

	public void print() {
		System.out.println(name + ": " + (state == Scheduler.IDLE ? "IDLE" : 
					state == Scheduler.RUNNING ? "RUNNING" : "TERMINATED") + 
					" ev_time = " + ev_time);
	}
	
	public void setRuning(int value) {
		if(value < 0)
			value = value*-1;
		while(value > 0) {
			lifeTime.append("#");
			value--;
		}
	}

	public void setSleep(int value) {
		if(value < 0)
			value = value*-1;
		while(value > 0) {
			lifeTime.append("-");
			value--;
		}
	}
	
	public String getLifeTime() {
		return lifeTime.toString();
	}
	
	public void setLifeTime(StringBuffer lifeTime) {
		this.lifeTime = lifeTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getEv_time() {
		return ev_time;
	}

	public void setEv_time(int ev_time) {
		this.ev_time = ev_time;
	}

	public int getEv_sleep() {
		return ev_sleep;
	}

	public void setEv_sleep(int ev_sleep) {
		this.ev_sleep = ev_sleep;
	}

	public Thread getMythread() {
		return mythread;
	}

	public void setMythread(Thread mythread) {
		this.mythread = mythread;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getInstante() {
		return instante;
	}

	public void setInstant(int instante) {
		this.instante = instante;
	}
	
}