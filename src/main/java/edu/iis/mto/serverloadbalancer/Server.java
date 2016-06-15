package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Matcher;

public class Server {

	public double currentloadPercentage;
	public int capacity;

	public Server(int capacity) {
		super();
		this.capacity = capacity;
	}

	public boolean contains(Vm theVm) {
		return true;
	}

}
