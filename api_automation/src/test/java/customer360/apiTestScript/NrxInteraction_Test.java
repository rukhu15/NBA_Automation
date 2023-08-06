package customer360.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import customer360.data.beans.api.Customer360APIBean;
import customer360.dataProvidersPackage.APIDP;
import customer360.pageObjects.NrxPerformance;
import customer360.pageObjects.NrxPerformance.performance;
import io.restassured.response.Response;
import scripts.TestInit;

public class NrxInteraction_Test extends TestInit {

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "nrxMonthlyPerformance", dataProviderClass = APIDP.class)
	public void getNrxMonthlyPerformance(Customer360APIBean perf) {
		ExtentTest t3 = pNode.createNode("nrxMonthlyPerformance", perf.testDescription);
		try {
			NrxPerformance nrxPerf = NrxPerformance.init(t3);
			Response resp = nrxPerf.getNrxPerformance(performance.MONTHLYPERFORMANCE, perf);
			nrxPerf.getValidatePerformanceResponse(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 2, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "nrxWeeklyPerformance", dataProviderClass = APIDP.class)
	public void getNrxWeeklyPerformance(Customer360APIBean perf) {
		ExtentTest t3 = pNode.createNode("nrxWeeklyPerformance", perf.testDescription);
		try {
			NrxPerformance nrxPerf = NrxPerformance.init(t3);
			Response resp = nrxPerf.getNrxPerformance(performance.WEEKLYPERFORMANCE, perf);
			nrxPerf.getValidatePerformanceResponse(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}
}
