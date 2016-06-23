package n.yarmak.forms;

import org.openqa.selenium.By;

import webdriver.BaseForm;
import webdriver.elements.Label;

public class ResultForm extends BaseForm {

	private static String formLocatorPattern = "//a[text()='Оставить отзыв' and contains(@href,'%s/reviews')]";

	private static By headingLocator = By.xpath("//h2[contains(@class,'catalog-masthead')]");
	private static By priceRangeLocator = By.xpath("//a[contains(@class,'price-value_primary')]");
	private static By yearLocator = By.xpath("//td[contains(.,'Дата выхода на рынок')]/following-sibling::td");
	private static By displaySizeLocator = By.xpath("//td[contains(.,'Диагональ')]/following-sibling::td");

	public ResultForm(String url) {
		super(By.xpath(String.format(formLocatorPattern, url)), url);
	}

	public boolean matches(String producer, String priceTo, String yearFrom, String displaySizeFrom,
			String displaySizeTo) {
		boolean result = matchesProducer(producer) && matchesPriceTo(priceTo) && matchesYearFrom(yearFrom)
				&& matchesDisplaySize(displaySizeFrom, displaySizeTo);
		return result;
	}

	private boolean matchesDisplaySize(String displaySizeFrom, String displaySizeTo) {
		String displaySize = new Label(displaySizeLocator).getText().split("\"")[0];
		logger.info(String.format("Actual display size is %s", displaySize));
		if (displaySize.compareTo(displaySizeFrom) < 0)
			return false;
		if (displaySize.compareTo(displaySizeTo) > 0)
			return false;
		return true;
	}

	private boolean matchesYearFrom(String yearFrom) {
		String year = new Label(yearLocator).getText();
		logger.info(String.format("Actual year is %s", year));
		if (yearFrom.compareTo(year) > 0)
			return false;
		return true;
	}

	private boolean matchesPriceTo(String priceTo) {
		String minPrice = new Label(priceRangeLocator).getText().split(" ")[0];
		logger.info(String.format("Actual minimal price is %s", minPrice));
		priceTo = String.format("%s,00", priceTo);
		for (int i = 0; i < priceTo.length() - minPrice.length(); i++) {
			minPrice = String.format("0%s", minPrice);
		}
		if (priceTo.compareTo(minPrice) < 0)
			return false;
		return true;
	}

	public boolean matchesProducer(String producer) {
		if (new Label(headingLocator).getText().toLowerCase().contains(producer.toLowerCase())) {
			logger.info(String.format("Actual producer is %s", producer));
			return true;
		} else {
			logger.info(String.format("Actual producer is not %s", producer));
			return false;
		}
	}
}
