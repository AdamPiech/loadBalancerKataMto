package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.CurrentLoadRercentageMatcher.hasCurrentLoadOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.ServerVmsCountMatcher.hasAVmsCountOf;
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
	
	@Test 
	public void vmShouldBeBalancedOnLessLoadedServerFirst() {
		Server moreLoadedServer = a(server().withCapacity(90).withCurrentLoadOf(50.0d));
		Server lessLoadedServer = a(server().withCapacity(90).withCurrentLoadOf(40.0d));
		Vm theVm = a(vm().ofSize(10));

		balancing(aServerListWith(moreLoadedServer, lessLoadedServer), aVmListWith(theVm, theVm));
		
		assertThat("less loaded server shoud contain the vm", lessLoadedServer.contains(theVm));
		assertThat("more loaded server shoud not contain the vm", !moreLoadedServer.contains(theVm));
	}

	@Test
	public void balancingServerWithNotEnoughRoom_ShouldNotBeFillefWithVm() {
		Server theServer = a(server().withCapacity(10).withCurrentLoadOf(90.0d));
		Vm theVm = a(vm().ofSize(2));
		
		balancing(aServerListWith(theServer), aVmListWith(theVm));
		assertThat("server shoud not contain the vm", !theServer.contains(theVm));
	}

	@Test 
	public void balancingServerAndVms() {
		Server server1 = a(server().withCapacity(4));
		Server server2 = a(server().withCapacity(6));
		
		Vm vm1 = a(vm().ofSize(1));
		Vm vm2 = a(vm().ofSize(4));
		Vm vm3 = a(vm().ofSize(2));
		
		balancing(aServerListWith(server1, server2), aVmListWith(vm1, vm2, vm3));
		
		assertThat("server1 should contain the vm1", server1.contains(vm1));
		assertThat("server1 should contain the vm3", server1.contains(vm3));
		assertThat("server2 should contain the vm2", server2.contains(vm2));
		
		assertThat(server1, hasCurrentLoadOf(75.0d));
		assertThat(server2, hasCurrentLoadOf(66.66d));
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
