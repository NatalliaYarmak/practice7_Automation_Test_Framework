package webdriver.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class DropDownList extends BaseElement {

	public DropDownList(final By locator, final String name) {
		super(locator, name);
	}

	@Override
	protected String getElementType() {
		return getLoc("loc.selecting.value");
	}

	public void selectByLabel(String label) {
		new Select(this.getElement()).selectByVisibleText(label);
	}
}
