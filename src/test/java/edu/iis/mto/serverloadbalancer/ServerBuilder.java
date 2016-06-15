package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.Server.MAXIMIUM_LOAD;

public class ServerBuilder implements Builder<Server>{

	private int capacity;
	private double initialLoad;

	public ServerBuilder withCapacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public Server build() {
		Server server = new Server(capacity);
		addIncialLoad(server);
		return server;
	}

	private void addIncialLoad(Server server) {
		if (initialLoad > 0) {
			int initialVmSize = (int) (initialLoad / (double)capacity * MAXIMIUM_LOAD);
			Vm initialVm = VmBuilder.vm().ofSize(initialVmSize).build();
			server.add(initialVm);
		}
	}     
	
	public static ServerBuilder server() {
		return new ServerBuilder();
	}

	public ServerBuilder withCurrentLoadOf(double initialLoad) {
		this.initialLoad = initialLoad;
		return this;
	}

}
