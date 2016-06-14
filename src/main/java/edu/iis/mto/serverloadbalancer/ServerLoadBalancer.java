package edu.iis.mto.serverloadbalancer;

public class ServerLoadBalancer {

	public void balance(Server[] servers, Vm[] vms) {
		for (Vm vm : vms) {
			addToLessLoadedServer(servers, vms);
		}
	}

	private void addToLessLoadedServer(Server[] servers, Vm[] vms) {
		Server lessLoadedServer = findLessLoadedServer(servers);
		lessLoadedServer.addVm(vms[0]);
	}

	private Server findLessLoadedServer(Server[] servers) {
		Server lessLoadedServer = null;
		for (Server server : servers) {
			if(lessLoadedServer == null || server.currentLoadPercentage < lessLoadedServer.currentLoadPercentage) {
				lessLoadedServer = server;
			}
		}
		return lessLoadedServer;
	}

}
