package webdriver.elements;

import org.openqa.selenium.By;

public class MultiSelect extends BaseElement {

	public MultiSelect(By locator, String name) {
		super(locator, name);
	}

	protected String getElementType() {
		return getLoc("loc.multiple.selection");
	}

}
