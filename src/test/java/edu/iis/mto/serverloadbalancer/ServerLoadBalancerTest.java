package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.CurrentLoadRercentageMatcher.hasCurrentLoadOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.junit.Test;

public class ServerLoadBalancerTest {
	
	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test 
	public void balancingServerWithNoVms_ServerStayEmpty() {
		Server theServer = a(server().withCapacity(1));
		
		balancing(aServerListWith(theServer), anEmptyListOfVms());
		
		assertThat(theServer, hasCurrentLoadOf(0.0d));
	}
	
	@Test 
	public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillservwerWithTheVm() {
		Server theServer = a(server().withCapacity(1));
		Vm theVm = a(vm().ofSize(1));
		balancing(aServerListWith(theServer), aVmListWith(theVm));
		
		assertThat(theServer, hasCurrentLoadOf(100.0d));
		assertThat("server shoud contain the vm", theServer.contains(theVm));
	}

	@Test 
	public void balancingOneServerWithTenSlotCapacity_andOneSlotVm_fillTheServerWithTenPercent() {
		Server theServer = a(server().withCapacity(10));
		Vm theVm = a(vm().ofSize(1));
		balancing(aServerListWith(theServer), aVmListWith(theVm));
		
		assertThat(theServer, hasCurrentLoadOf(10.0d));
		assertThat("server shoud contain the vm", theServer.contains(theVm));
	}
	
	@Test 
	public void balansingTheServerWithEnoughRoom_fillTheServerWithAllVms() {
		Server theServer = a(server().withCapacity(100));
		Vm theFirstVm = a(vm().ofSize(1));
		Vm theSecondVm = a(vm().ofSize(1));
		balancing(aServerListWith(theServer), aVmListWith(theFirstVm, theSecondVm));
		
		assertThat(theServer, hasAVmsCountOf(2));
		assertThat("server shoud contain the first vm", theServer.contains(theFirstVm));
		assertThat("server shoud contain the second vm", theServer.contains(theSecondVm));
	}
	
	private Matcher<? super Server> hasAVmsCountOf(int expectedVmsCount) {
		return new ServerVmsCountMatcher(expectedVmsCount);
	}

	private Vm[] aVmListWith(Vm... vms) {
		return vms;
	}

	private void balancing(Server[] servers, Vm[] vms) {
		new ServerLoadBalancer().balance(servers, vms);
	}

	private Vm[] anEmptyListOfVms() {
		return new Vm[0];
	}

	private Server[] aServerListWith(Server... servers) {
		return servers;
	}

	private <T> T a(Builder<T> builder) {
		return builder.build();
	}
	
}
