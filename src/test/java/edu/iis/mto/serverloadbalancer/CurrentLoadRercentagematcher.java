package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class CurrentLoadRercentagematcher extends TypeSafeMatcher<Server> {

	private double expectedLoadPercentage;

	public CurrentLoadRercentagematcher(double expectedLoadPercentage) {
		this.expectedLoadPercentage = expectedLoadPercentage;
	}

	public void describeTo(Description description) {
		description.appendText("a server with load percentage of ").appendValue(expectedLoadPercentage);
	}

	@Override
	protected boolean matchesSafely(Server server) {
		return expectedLoadPercentage == server.currentloadPercentage || Math.abs(expectedLoadPercentage - server.currentloadPercentage) < 0.01d;
	}

}
