package n.yarmak.forms;

import org.openqa.selenium.By;

import webdriver.BaseForm;
import webdriver.Browser;
import webdriver.elements.MenuItem;

public class CatalogForm extends BaseForm {

	private static By catalogFormLocator = By.xpath("//div[contains(@class,'catalog-bar-main')]");
	private static String catalogFormTitle = System.getProperty("catalogForm", Browser.stageProps.getProperty("catalogForm"));
	private String catalogBarPattern = "//a[contains(@class,'catalog-bar') and contains(.,'%s')]";
	public static final String TV_TEXT = "Телевизоры";

	public CatalogForm() {
		super(catalogFormLocator, catalogFormTitle);
	}

	public TVForm navigateCatalogBar(String text) {
		String catalogBarXpath = String.format(catalogBarPattern, text);
		MenuItem catalogMenuItem = new MenuItem(By.xpath(catalogBarXpath), text);
		catalogMenuItem.click();
		return new TVForm();
	}

}
