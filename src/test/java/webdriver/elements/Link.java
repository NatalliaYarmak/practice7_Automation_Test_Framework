package webdriver.elements;

import org.openqa.selenium.By;

public class Link extends BaseElement{

	public Link(By loc, String name) {
		super(loc, name);
	}

	protected String getElementType() {
		return getLoc("loc.link");
	}

}
