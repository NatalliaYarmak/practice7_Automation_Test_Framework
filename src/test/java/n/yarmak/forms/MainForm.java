package n.yarmak.forms;

import org.openqa.selenium.By;

import webdriver.BaseForm;
import webdriver.elements.Label;

public class MainForm extends BaseForm {

	private static By mainFormLocator = By.xpath("//a[@title='RSS']");
	private static String mainFormTitle = "Main Form";
	private String mainMenuPattern = "//span[contains(text(),'%s')]";
	public static final String CATALOG_TEXT = "Каталог";

	public MainForm() {
		super(mainFormLocator, mainFormTitle);
	}

	public void navigateMainMenu(String text) {
		String mainMenuXpath = String.format(mainMenuPattern, text);
		Label menuItem = new Label(By.xpath(mainMenuXpath), text);
		menuItem.clickAndWait();
	}

}
