package customer360.dataProvidersPackage;

import java.util.Iterator;

import org.testng.annotations.DataProvider;

import customer360.data.beans.api.Customer360APIBean;
import framework.utility.dataParam.Data;

public class APIDP {
	@DataProvider(name = "createFilterAPI")
	public static Iterator<Object[]> createFilterAPI() {
		return Data.listOf(Customer360APIBean.class).from("createFilterAPI");
	}

	@DataProvider(name = "getSavedFilterAPI")
	public static Iterator<Object[]> getSavedFilterAPI() {
		return Data.listOf(Customer360APIBean.class).from("getSavedFilterAPI");
	}

	@DataProvider(name = "getSelectedFilterAPI")
	public static Iterator<Object[]> getSelectedFilterAPI() {
		return Data.listOf(Customer360APIBean.class).from("getSelectedFilterAPI");
	}

	@DataProvider(name = "searchRecordsByIDAPI")
	public static Iterator<Object[]> searchRecordsByIDAPI() {
		return Data.listOf(Customer360APIBean.class).from("searchRecordsByIDAPI");
	}

	@DataProvider(name = "searchRecordsByNameAPI")
	public static Iterator<Object[]> searchRecordsByNameAPI() {
		return Data.listOf(Customer360APIBean.class).from("searchRecordsByNameAPI");
	}

	@DataProvider(name = "searchRecordsByTerritoryAPI")
	public static Iterator<Object[]> searchRecordsByTerritoryAPI() {
		return Data.listOf(Customer360APIBean.class).from("searchRecordsByTerritoryAPI");
	}

	@DataProvider(name = "fetchTableRecordsInDefaultState")
	public static Iterator<Object[]> fetchTableRecordsInDefaultState() {
		return Data.listOf(Customer360APIBean.class).from("fetchTableRecordsInDefaultState");
	}

	@DataProvider(name = "fetchRecordsWithFilters")
	public static Iterator<Object[]> fetchRecordsWithFilters() {
		return Data.listOf(Customer360APIBean.class).from("fetchRecordsWithFilters");
	}

	@DataProvider(name = "fetchRecordsWithFiltersAndCards")
	public static Iterator<Object[]> fetchRecordsWithFiltersAndCards() {
		return Data.listOf(Customer360APIBean.class).from("fetchRecordsWithFiltersAndCards");
	}

	@DataProvider(name = "fetchRecordsWithCards")
	public static Iterator<Object[]> fetchRecordsWithCards() {
		return Data.listOf(Customer360APIBean.class).from("fetchRecordsWithCards");
	}

	@DataProvider(name = "fetchDataInFilter")
	public static Iterator<Object[]> fetchDataInFilter() {
		return Data.listOf(Customer360APIBean.class).from("fetchDataInFilter");
	}

	@DataProvider(name = "nrxMonthlyPerformance")
	public static Iterator<Object[]> nrxMonthlyPerformance() {
		return Data.listOf(Customer360APIBean.class).from("nrxMonthlyPerformance");
	}

	@DataProvider(name = "nrxWeeklyPerformance")
	public static Iterator<Object[]> nrxWeeklyPerformance() {
		return Data.listOf(Customer360APIBean.class).from("nrxWeeklyPerformance");
	}

	@DataProvider(name = "interactionField")
	public static Iterator<Object[]> interactionField() {
		return Data.listOf(Customer360APIBean.class).from("interactionField");
	}

	@DataProvider(name = "interactionContent")
	public static Iterator<Object[]> interactionContent() {
		return Data.listOf(Customer360APIBean.class).from("interactionContent");
	}

	@DataProvider(name = "channelInteraction")
	public static Iterator<Object[]> channelInteraction() {
		return Data.listOf(Customer360APIBean.class).from("channelInteraction");
	}

	@DataProvider(name = "performanceSummary")
	public static Iterator<Object[]> performanceSummary() {
		return Data.listOf(Customer360APIBean.class).from("performanceSummary");
	}

	@DataProvider(name = "bannerAd")
	public static Iterator<Object[]> bannerAd() {
		return Data.listOf(Customer360APIBean.class).from("bannerAd");
	}

	@DataProvider(name = "webSite")
	public static Iterator<Object[]> webSite() {
		return Data.listOf(Customer360APIBean.class).from("webSite");
	}

	@DataProvider(name = "emailGraph")
	public static Iterator<Object[]> emailGraph() {
		return Data.listOf(Customer360APIBean.class).from("emailGraph");
	}

	@DataProvider(name = "interactionSummary")
	public static Iterator<Object[]> interactionSummary() {
		return Data.listOf(Customer360APIBean.class).from("interactionSummary");
	}
	
	@DataProvider(name = "affliatedAccount")
	public static Iterator<Object[]> affliatedAccount() {
		return Data.listOf(Customer360APIBean.class).from("affliatedAccount");
	}
}
