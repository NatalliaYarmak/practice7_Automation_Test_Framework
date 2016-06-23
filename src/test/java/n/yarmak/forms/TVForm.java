package n.yarmak.forms;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;

import webdriver.BaseForm;
import webdriver.Browser;
import webdriver.elements.Checkbox;
import webdriver.elements.DropDownList;
import webdriver.elements.Label;
import webdriver.elements.MultiSelect;
import webdriver.elements.TextBox;

public class TVForm extends BaseForm {

	public static final String PRODUCER_TEXT = "Производитель";
	public static final String MULTI_SELECT_TEXT = "вариант";
	public static final String PRICE_TEXT = "Минимальная цена";
	public static final String YEAR_TEXT = "Дата выхода на рынок";
	public static final String DISPLAY_SIZE_TEXT = "Диагональ";

	private static String tvFormTitle = System.getProperty("tvForm", Browser.stageProps.getProperty("tvForm"));
	private static String multipleSelectionLocatorPattern = "//span[contains(.,'%s')]/following::span[contains(.,'%s')][1]/parent::div/parent::div";
	private static String producerLocatorPattern = "//div[contains(@class,'popover')]/.//input[@value='%s']/parent::span";
	private static String inchPattern = "%s\"";

	private static By tvFormLocator = By.xpath(String.format("//h1[contains(.,'%s')]", CatalogForm.TV_TEXT));
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

	MultiSelect multSlctProducers = new MultiSelect(producersListLocator, "producers list");
	private TextBox txtPriceTo = new TextBox(priceToLocator, "price to");
	private TextBox txtYearFrom = new TextBox(yaerFromLocator, "year from");
	private DropDownList slctDisplaySizeFrom = new DropDownList(displaySizeFromLocator, "display size from");
	private DropDownList slctDisplaySizeTo = new DropDownList(displaySizeToLocator, "display size to");

	public TVForm() {
		super(tvFormLocator, tvFormTitle);
	}

	public void setQueryParams(String producer, String priceTo, String yearFrom, String displaySizeFrom,
			String displaySizeTo) {

		// пододвинем мышь, чтобы метка с числом результатов не заслоняла
		// элемент со списком производителей
		this.txtPriceTo.mouseMove();
		// открыть менюшку с производителями
		multSlctProducers.click();
		Checkbox chckProducer = new Checkbox(By.xpath(String.format(producerLocatorPattern, producer.toLowerCase())),
				producer);
		chckProducer.select();
		// закрыть менюшку с производителями
		multSlctProducers.click();

		this.txtPriceTo.setText(priceTo);
		this.txtYearFrom.setText(yearFrom);

		this.slctDisplaySizeFrom.selectByLabel(String.format(inchPattern, displaySizeFrom));
		this.slctDisplaySizeTo.selectByLabel(String.format(inchPattern, displaySizeTo));

	}

	public boolean checkSearchResults(String producer, String priceTo, String yearFrom, String displaySizeFrom,
			String displaySizeTo) {

		List<String> resultURLs = getResultURLs();
		logger.info(String.format("%d TVs is found", resultURLs.size()));
		boolean matchResult = true;
		int i = 1;
		for (String url : resultURLs) {
			logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			logger.info(String.format("   Search result No %d", i++));
			logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			createNewTab(url);
			ResultForm resultForm = new ResultForm(url);
			matchResult &= resultForm.matches(producer, priceTo, yearFrom, displaySizeFrom, displaySizeTo);
			resultForm.closeTab();
			logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		}
		return matchResult;
	}

	private List<String> getResultURLs() {
		List<RemoteWebElement> results = new Label(searchResultLocator).getElements();
		List<String> hrefs = new ArrayList<String>();
		for (RemoteWebElement element : results) {
			hrefs.add(element.getAttribute("href"));
		}
		return hrefs;
	}

}
