package customer360.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import customer360.data.beans.api.Customer360APIBean;
import customer360.dataProvidersPackage.APIDP;
import customer360.pageObjects.PerformanceSummaryAPI;
import io.restassured.response.Response;
import scripts.TestInit;

public class PerformanceSummaryAPI_Test extends TestInit {

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "performanceSummary", dataProviderClass = APIDP.class)
	public void getNrxMonthlyPerformance(Customer360APIBean perf) {
		ExtentTest t3 = pNode.createNode("performanceSummary", "To fetch the data for Performance Summary Graph");
		try {
			PerformanceSummaryAPI perfSummary = PerformanceSummaryAPI.init(t3);
			Response resp = perfSummary.performanceSummaryResp(perf);
			perfSummary.validatePerfSummaryResp(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}

	}
}
