package webdriver.elements;

import org.openqa.selenium.By;

public class Checkbox extends BaseElement{

	public Checkbox(By locator, String name) {
		super(locator, name);
	}

	protected String getElementType() {
		return getLoc("loc.checkbox");
	}

	public void select() {
		if (!this.getElement().isSelected()){
			click();
		}
	}
	
	public void deselect() {
		if (this.getElement().isSelected()){
			click();
		}
	}
}
