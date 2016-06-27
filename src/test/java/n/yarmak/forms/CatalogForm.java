package n.yarmak.forms;

import org.openqa.selenium.By;

import webdriver.BaseForm;
import webdriver.elements.Label;

public class CatalogForm extends BaseForm {

	private static By catalogFormLocator = By.xpath("//div[contains(@class,'catalog-bar-main')]");
	private static String catalogFormTitle = "Catalog Form";
	private String catalogBarPattern = "//a[contains(@class,'catalog-bar') and contains(.,'%s')]";
	public static final String TV_TEXT = "Телевизоры";

	public CatalogForm() {
		super(catalogFormLocator, catalogFormTitle);
	}

	public void navigateCatalogBar(String text) {
		String catalogBarXpath = String.format(catalogBarPattern, text);
		Label catalogMenuItem = new Label(By.xpath(catalogBarXpath), text);
		catalogMenuItem.clickAndWait();
	}

}
