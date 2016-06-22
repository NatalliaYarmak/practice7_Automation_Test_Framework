package n.yarmak.forms;

import org.openqa.selenium.By;

import webdriver.BaseForm;
import webdriver.Browser;
import webdriver.elements.MenuItem;

public class MainForm extends BaseForm {

	private static By mainFormLocator = By.xpath("//a[@title='RSS']");
	private static String mainFormTitle = System.getProperty("mainForm", Browser.stageProps.getProperty("mainForm"));
	private String mainMenuPattern = "//span[contains(text(),'%s')]";
	public static final String CATALOG_TEXT = "Каталог";

	public MainForm() {
		super(mainFormLocator, mainFormTitle);
	}

	public CatalogForm navigateMainMenu(String text) {
		String mainMenuXpath = String.format(mainMenuPattern, text);
		MenuItem menuItem = new MenuItem(By.xpath(mainMenuXpath), text);
		menuItem.click();
		return new CatalogForm();
	}

}
