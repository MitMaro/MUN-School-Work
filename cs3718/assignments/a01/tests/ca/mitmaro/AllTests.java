package ca.mitmaro.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * TestSuite that runs all the LDB tests
 *
 */
public class AllTests {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
	
	public static Test suite() {
		TestSuite suite= new TestSuite("ALL LDB Tests");
		suite.addTest(ca.mitmaro.entity.AllTests.suite());
		return suite;
	}
}
