package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class CurrentLoadRercentageMatcher extends TypeSafeMatcher<Server> {

	private double expectedLoadPercentage;

	public CurrentLoadRercentageMatcher(double expectedLoadPercentage) {
		this.expectedLoadPercentage = expectedLoadPercentage;
	}

	public void describeTo(Description description) {
		description.appendText("a server with load percentage of ").appendValue(expectedLoadPercentage);
	}

	@Override
	protected void describeMismatchSafely(Server item, Description description) {
		description.appendText("a server with load percentage of ").appendValue(item.currentloadPercentage);
	};
	
	@Override
	protected boolean matchesSafely(Server server) {
		return doubleAreEqual(server, expectedLoadPercentage, server.currentloadPercentage);
	}

	private boolean doubleAreEqual(Server server, double d1, double d2) {
		return d1 == d2 || Math.abs(expectedLoadPercentage - server.currentloadPercentage) < 0.01d;
	}

	public static CurrentLoadRercentageMatcher hasCurrentLoadOf(double expectedLoadPercentage) {
		return new CurrentLoadRercentageMatcher(expectedLoadPercentage);
	}
	
}
