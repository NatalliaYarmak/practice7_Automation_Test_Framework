package demo.tests;

import webdriver.BaseTest;
import demo.forms.*;

public class DemoTest extends BaseTest {

	public void runTest() {

		logger.step(1);
		TutSearchForm tsf = new TutSearchForm();
		tsf.assertLogo();

		logger.step(2);
		tsf.searchFor("A1QA");

		logger.step(3);
		tsf.assertA1QAString();
	}
}
