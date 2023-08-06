package customer360.apiTestScript;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import customer360.data.beans.api.Customer360APIBean;
import customer360.dataProvidersPackage.APIDP;
import customer360.pageObjects.BannerAdAPI;
import customer360.pageObjects.BannerAdAPI.BannerAd;
import io.restassured.response.Response;
import scripts.TestInit;

public class BannerAdAPI_Test extends TestInit {

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "bannerAd", dataProviderClass = APIDP.class)
	public void last3MonthBannerAdKPIResponse(Customer360APIBean banner) {
		ExtentTest t3 = pNode.createNode("bannerAd", banner.testDescription);
		try {

			BannerAdAPI bannerResp = BannerAdAPI.init(t3);
			Response resp = bannerResp.bannerAdKPIRespone(BannerAd.LAST3MONTH, banner);
			bannerResp.validateBannerAd(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "bannerAd", dataProviderClass = APIDP.class)
	public void prior3MonthBannerAdKPIResponse(Customer360APIBean banner) {
		ExtentTest t3 = pNode.createNode("bannerAd", banner.testDescription);
		try {

			BannerAdAPI bannerResp = BannerAdAPI.init(t3);
			Response resp = bannerResp.bannerAdKPIRespone(BannerAd.PRIOR3MONTH, banner);
			bannerResp.validateBannerAd(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "bannerAd", dataProviderClass = APIDP.class)
	public void last3MonthBannerAdGraphResponse(Customer360APIBean banner) {
		ExtentTest t3 = pNode.createNode("bannerAd", banner.testDescription);
		try {

			BannerAdAPI bannerResp = BannerAdAPI.init(t3);
			Response resp = bannerResp.bannerAdGraphRespone(BannerAd.LAST3MONTH, banner);
			bannerResp.validateBannerAd(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}

	@Test(priority = 1, groups = {
			"Customer360_API" }, enabled = true, dataProvider = "bannerAd", dataProviderClass = APIDP.class)
	public void prior3MonthBannerAdGraphResponse(Customer360APIBean banner) {
		ExtentTest t3 = pNode.createNode("bannerAd", banner.testDescription);
		try {

			BannerAdAPI bannerResp = BannerAdAPI.init(t3);
			Response resp = bannerResp.bannerAdGraphRespone(BannerAd.PRIOR3MONTH, banner);
			bannerResp.validateBannerAd(resp);

		} catch (Exception e) {
			markTestAsFailure(e, t3);
		}
	}
}
