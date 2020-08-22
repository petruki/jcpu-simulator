package com.github.petruki.jcpu.sim;

import com.github.petruki.jcpu.queues.PriorityQueue;

public class Scheduler {

	public static int clock = 0;
	
	public static PriorityQueue event_list = new PriorityQueue();

	public static Process current = null;	

	public static final int IDLE = 1;  
	public static final int	RUNNING = 2;
	public static final int	TERMINATED = 3;
	
	public static Object done = new Integer(0);

	public static int live_threads = 0;

	public static void fatal_error(String s) {
		System.err.println("FATAL: " + s);
		System.exit(-1);
	}
	
	public static void hold(int delay) {
		reactivate(current, delay);
		next_event(false);
	}
	
	public static void passivate() {
		if (current.scheduled())
			unschedule(current);
		if (current.terminated()) {
			current.print();
			fatal_error("Passivate terminated process"); 
		}
		next_event(false);
	}
	
	public static void activate(Process p) {
		activate(p, 0);
	}

	public static void activate(Process p, int time) {
		if (p.active()) 
			return;
		if (p.getState() == TERMINATED) {
			p.print();
			fatal_error("Activating terminated process"); 
		}
		schedule(p, clock+time);
	}
	
	public static void reactivate(Process p) {
		reactivate(p, 0);
	}
	
	public static void reactivate(Process p, int t) {
		if (p.getState() == TERMINATED) {
			p.print();
			fatal_error("Reactivating terminated process"); 
		}

		if (p.scheduled()) 
			unschedule(p);
		schedule(p, clock+t);
	}
	
	public static void run_simulation()	{
		current = null;
		next_event(false);
		System.out.println("<< Simulation has finished >>\n");
	}

	public static void unschedule(Process p) {
		if (p.getEv_time() < 0) {
			p.print();  
			fatal_error("Unscheduling unsheduled process"); 
		}

		if (!event_list.remove(p)) {
			p.print();
			fatal_error("Active process not found on the event list");
		} else {
			p.setState(IDLE);  
			p.setEv_time(-1);  
		}
	}
	
	public static void schedule(Process p, int time) {
		if (p.getState() == TERMINATED || p.getEv_time() >= 0) {
			p.print();
			print_ev_list();
			fatal_error("End of schedule or active process"); 
		}
		if (time < clock) {
			p.print();
			print_ev_list();
			System.out.println("(ev_time="+time+", clock="+clock+")");
			fatal_error("Scheduled event passed");
		}

		p.setEv_time(time);
		event_list.enqueue(p, time);
	}
	
	public static void next_event(boolean die) {
		if (event_list.isEmpty()) {
			live_threads = 0;
			synchronized(done) {
				done.notify();
			}
			return;
		}
		
		if (clock > ((Process)event_list.front()).getEv_time()) { 
			print_ev_list(); 
			fatal_error("Event list not ordered yet"); 
		}
		
		Process next = (Process) event_list.dequeue();  
		clock = next.getEv_time();
		next.setEv_time(-1);
		
		pass_baton(next, die);
	}
	
	public static void pass_baton(Process p) {
		pass_baton(p, false);
	}
	
	public static void pass_baton(Process p, boolean die) {
		Process caller = current;
		if (p.getState() == TERMINATED)
			fatal_error("Attempting to assign terminated task");

		current = p;
		if (die) {
			caller.setState(TERMINATED);
			live_threads--;
			synchronized(done) {
				done.notify();
			}
		} 
		
		if (caller == null || !caller.equals(p)) {
			synchronized(p) {
				p.setState(RUNNING);
				p.notify();
			}
			
			if (caller == null) {
				synchronized(done) {
					try {
						while (live_threads > 0)
							done.wait();
					} catch (InterruptedException e) {}
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
	
	public static void print_ev_list() {
		System.out.println("Current event list " + clock + ":");
		event_list.print();
		System.out.println("End of event list\n");
	}
}

