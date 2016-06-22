package webdriver.elements;

import org.openqa.selenium.By;

public class Link extends BaseElement{

	protected Link(By locator) {
		super(locator);
	}

	@Override
	protected String getElementType() {
		return getLoc("loc.link");
	}

}
