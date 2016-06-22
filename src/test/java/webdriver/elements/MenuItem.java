package webdriver.elements;

import org.openqa.selenium.By;

public class MenuItem extends BaseElement{

	public MenuItem(final By locator, final String name) {
		super(locator, name);
	}

	public MenuItem(String string, String name) {
		super(string, name);
	}
	
	public MenuItem(By locator) {
		super(locator);
	}

	@Override
	protected String getElementType() {
		return getLoc("loc.menu");
	}

}
