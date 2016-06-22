package webdriver.elements;

import org.openqa.selenium.By;

public class MultipleSelection extends BaseElement {

	public MultipleSelection(By locator, String name) {
		super(locator, name);
	}

	protected String getElementType() {
		return getLoc("loc.multiple.selection");
	}

}
