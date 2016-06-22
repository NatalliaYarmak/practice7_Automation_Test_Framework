package n.yarmak.forms;

import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.stdDSA;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;

import webdriver.BaseForm;
import webdriver.Browser;
import webdriver.elements.Checkbox;
import webdriver.elements.DropDownList;
import webdriver.elements.Label;
import webdriver.elements.Link;
import webdriver.elements.MultipleSelection;
import webdriver.elements.TextBox;

public class TVForm extends BaseForm {

	public static final String PRODUCER_TEXT = "Производитель";
	public static final String MULTI_SELECT_TEXT = "вариант";
	public static final String PRICE_TEXT = "Минимальная цена";
	public static final String YEAR_TEXT = "Дата выхода на рынок";
	public static final String DISPLAY_SIZE_TEXT = "Диагональ";

	private static By tvFormLocator = By.xpath(String.format("//h1[contains(.,'%s')]", CatalogForm.TV_TEXT));
	private static String tvFormTitle = System.getProperty("tvForm", Browser.stageProps.getProperty("tvForm"));
	private static String multipleSelectionLocatorPattern = "//span[contains(.,'%s')]/following::span[contains(.,'%s')][1]/parent::div/parent::div";
	private static String producerLocatorPattern = "//div[contains(@class,'popover')]/.//input[@value='%s']/parent::span";
	private static String inchPattern = "%s\"";
	private static By producersListLocator = By
			.xpath(String.format(multipleSelectionLocatorPattern, PRODUCER_TEXT, MULTI_SELECT_TEXT));
	private static By priceToLocator = By
			.xpath(String.format("//span[contains(.,'%s')]/following::input[2]", PRICE_TEXT));
	private static By yaerFromLocator = By
			.xpath(String.format("//span[contains(.,'%s')]/following::input[@type='text'][1]", YEAR_TEXT));
	private static By displaySizeFromLocator = By
			.xpath(String.format("//span[contains(.,'%s')]/following::select[1]", DISPLAY_SIZE_TEXT));
	private static By displaySizeToLocator = By
			.xpath(String.format("//span[contains(.,'%s')]/following::select[2]", DISPLAY_SIZE_TEXT));
	private static By searchResultLocator = By.xpath(
			"//div[@id='schema-products']/div[contains(@class,'schema-product')]/descendant::div[contains(@class,'image')]/a");

	MultipleSelection producers = new MultipleSelection(producersListLocator, "producers list");
	private TextBox priceTo = new TextBox(priceToLocator, "price to");
	private TextBox yearFrom = new TextBox(yaerFromLocator, "yaer from");

	private DropDownList displaySizeFrom = new DropDownList(displaySizeFromLocator, "display size from");
	private DropDownList displaySizeTo = new DropDownList(displaySizeToLocator, "display size to");

	public TVForm() {
		super(tvFormLocator, tvFormTitle);
	}

	public void setQueryParams(String producer, String priceTo, String yearFrom, String displaySizeFrom,
			String displaySizeTo) {

		// пододвинем мышь, чтобы метка с числом результатов не заслоняла
		// элемент со списком производителей
		this.priceTo.mouseMove();
		// открыть менюшку с производителями
		producers.click();
		Checkbox producerCheckBox = new Checkbox(
				By.xpath(String.format(producerLocatorPattern, producer.toLowerCase())), producer);
		producerCheckBox.select();
		// закрыть менюшку с производителями
		producers.click();

		this.priceTo.setText(priceTo);

		this.yearFrom.setText(yearFrom);

		this.displaySizeFrom.selectByLabel(String.format(inchPattern, displaySizeFrom));
		this.displaySizeTo.selectByLabel(String.format(inchPattern, displaySizeTo));

	}

	public void checkSearchResults(String producer, String priceTo, String yearFrom, String displaySizeFrom,
			String displaySizeTo) {

		List<String> links = getResults();


	}

	private List<String> getResults() {
		List<RemoteWebElement> searchResults = new Label(searchResultLocator).getElements();
		List<String> links = new ArrayList<String>();
		for (RemoteWebElement el : searchResults) {
			links.add(el.getAttribute("href"));
		}
		return links;
	}

}
