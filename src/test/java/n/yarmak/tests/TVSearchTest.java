package n.yarmak.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import n.yarmak.forms.CatalogForm;
import n.yarmak.forms.MainForm;
import n.yarmak.forms.TVForm;
import webdriver.BaseTest;

public class TVSearchTest extends BaseTest{

	protected String producer;
	protected String priceTo;
	protected String yearFrom;
	protected String displaySizeFrom;
	protected String displaySizeTo;
	
	/**
	 * 
	 * @param producer
	 * @param priceTo
	 * @param yearFrom
	 * @param displaySizeFrom
	 * @param displaySizeTo
	 */
	@BeforeMethod
	@Parameters ({ "producer", "priceTo", "yearFrom", "displaySizeFrom", "displaySizeTo" })
	public void setParams(String producer, String priceTo, String yearFrom, String displaySizeFrom, String displaySizeTo ){
		this.producer = producer;
		this.priceTo = priceTo;
		this.yearFrom = yearFrom;
		this.displaySizeFrom = displaySizeFrom;
		this.displaySizeTo = displaySizeTo;
	}
	
	/**
	 * 
	 */
	public void  runTest() {
		logger.step(1);
		MainForm mainForm = new MainForm();
		
		logger.step(2);
		CatalogForm catalogForm = mainForm.navigateMainMenu(MainForm.CATALOG_TEXT);
		
		logger.step(3);
		TVForm tvForm = catalogForm.navigateCatalogBar(CatalogForm.TV_TEXT);
		
		logger.step(4);
		tvForm.setQueryParams(producer, priceTo, yearFrom, displaySizeFrom, displaySizeTo);
		
		logger.step(5);
		assertEquals(true, tvForm.checkSearchResults(producer, priceTo, yearFrom, displaySizeFrom, displaySizeTo));
	}

}
