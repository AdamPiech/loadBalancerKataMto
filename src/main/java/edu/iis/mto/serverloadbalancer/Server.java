package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Matcher;

public class Server {

	private static final double MAXIMIUM_LOAD = 100.0d;
	public double currentloadPercentage;
	public int capacity;

	public Server(int capacity) {
		super();
		this.capacity = capacity;
	}

	public boolean contains(Vm theVm) {
		return true;
	}

	public void add(Vm vm) {
		currentloadPercentage = (double)vm.size / (double)capacity * MAXIMIUM_LOAD;
	}

}
