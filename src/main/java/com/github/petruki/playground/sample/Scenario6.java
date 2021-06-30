package com.github.petruki.playground.sample;

import com.github.petruki.playground.sample.process.AbstractProcess;
import com.github.petruki.playground.sample.process.ActionProcess;

public class Scenario6 {
	
	public Scenario6() {
		init();
	}

	/*
	
	Scenario events:
	
	Customer 1 enters line at time 4. Transaction time is 3
	Customer 1 begins service at time 4. Time waited is 0
	Customer 2 enters line at time 6. Transaction time is 3
	Customer 2 begins service at time 7. Time waited is 1
	Customer 3 enters line at time 10. Transaction time is 3
	Customer 3 begins service at time 10. Time waited is 0
	Customer 4 enters line at time 14. Transaction time is 5
	Customer 4 begins service at time 14. Time waited is 0
	Customer 5 enters line at time 15. Transaction time is 2
	Customer 6 enters line at time 16. Transaction time is 2
	Customer 7 enters line at time 17. Transaction time is 2
	Customer 8 enters line at time 18. Transaction time is 3
	Customer 9 enters line at time 19. Transaction time is 3
	Customer 5 begins service at time 19. Time waited is 4
	
	Output:
	
	Customer 1 - w: 0 
	---|###
	Customer 2 - w: 1 
	-----|-###
	Customer 3 - w: 0 
	---------|###
	Customer 4 - w: 0 
	-------------|#####
	Customer 5 - w: 4 
	--------------|----##
	Customer 6 - w: 5 
	---------------|-----##
	Customer 7 - w: 6 
	----------------|------##
	Customer 8 - w: 7 
	-----------------|-------###
	Customer 9 - w: 9 
	------------------|---------###
	
	*/
	private void init() {
		var _5min = new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 5);
		var _3min = new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 3);
		var _2min = new ActionProcess(ActionProcess.ACT.RUN.ordinal(), 2);
		
		var customer1 = new AbstractProcess("Customer 1");
		var customer2 = new AbstractProcess("Customer 2");
		var customer3 = new AbstractProcess("Customer 3");
		var customer4 = new AbstractProcess("Customer 4");
		var customer5 = new AbstractProcess("Customer 5");
		var customer6 = new AbstractProcess("Customer 6");
		var customer7 = new AbstractProcess("Customer 7");
		var customer8 = new AbstractProcess("Customer 8");
		var customer9 = new AbstractProcess("Customer 9");
		
		customer1.setInstant(3);
		customer1.addAction(_3min);
		
		customer2.setInstant(5);
		customer2.addAction(_3min);
		
		customer3.setInstant(9);
		customer3.addAction(_3min);

		customer4.setInstant(13);
		customer4.addAction(_5min);
		
		customer5.setInstant(14);
		customer5.addAction(_2min);
		
		customer6.setInstant(15);
		customer6.addAction(_2min);
		
		customer7.setInstant(16);
		customer7.addAction(_2min);
		
		customer8.setInstant(17);
		customer8.addAction(_3min);
		
		customer9.setInstant(18);
		customer9.addAction(_3min);
		
		var scenario = new Custom();
		var cpu = new Custom.CPUParam("Ticket Issuer", 20);
		scenario.addCPU(cpu);
		
		scenario.addProcess(customer1);
		scenario.addProcess(customer2);
		scenario.addProcess(customer3);
		scenario.addProcess(customer4);
		scenario.addProcess(customer5);
		scenario.addProcess(customer6);
		scenario.addProcess(customer7);
		scenario.addProcess(customer8);
		scenario.addProcess(customer9);
		
		scenario.startSimulation();
	}

}
