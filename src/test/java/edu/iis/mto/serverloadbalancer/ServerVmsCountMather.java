package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ServerVmsCountMather extends TypeSafeMatcher<Server> {

	private int expectedVmsCount;

	public ServerVmsCountMather(int expectedVmsCount) {
		this.expectedVmsCount = expectedVmsCount;
	}

	public void describeTo(Description description) {
		description.appendText("a server with vms count of ").appendValue(expectedVmsCount);
	}

	@Override
	protected void describeMismatchSafely(Server item, Description description) {
		description.appendText("a server with vms count of ").appendValue(item.countVms());
	};
	
	@Override
	protected boolean matchesSafely(Server server) {
		return expectedVmsCount == server.countVms();
	}
	
	public static ServerVmsCountMather hasAVmsCountOf(int expectedVmsCount) {
		return new ServerVmsCountMather(expectedVmsCount);
	}

}
