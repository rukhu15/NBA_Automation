package customer360.pageObjects;

import static io.restassured.RestAssured.given;

import framework.utility.common.DatabaseUtil;
import framework.utility.globalConst.ConfigInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aventstack.extentreports.ExtentTest;

import customer360.apiPojo.apiResp.bannerAd.BannerAdResp;
import customer360.data.beans.api.Customer360APIBean;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import scripts.BaseAPI;

public class BannerAdAPI extends BaseAPI {
	private static ExtentTest pNode;
	private static final Logger logToConsole = LoggerFactory.getLogger(BannerAdAPI.class);

	public BannerAdAPI(ExtentTest t3) {
	}

	public static BannerAdAPI init(ExtentTest t3) throws Exception {
		pNode = t3;
		return new BannerAdAPI(pNode);
	}

	public enum BannerAd {
		LAST3MONTH, PRIOR3MONTH
	}

	public Response bannerAdKPIRespone(BannerAd bannerAd, Customer360APIBean banner) {

		int projectID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");
		RequestSpecification genericReq = initAPIReq();
		RequestSpecification bannerAdReq = null;

		if (bannerAd.equals(BannerAd.LAST3MONTH)) {
			bannerAdReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("nbadesigner-backend/nba/bannerAds/" + banner.customer_id + "/last_3_months").queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		} else if (bannerAd.equals(BannerAd.PRIOR3MONTH)) {
			bannerAdReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("nbadesigner-backend/nba/bannerAds/" + banner.customer_id + "/prior_3_months").queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		}

		Response resp = bannerAdReq.when().get();

		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;

	}

	public Response bannerAdGraphRespone(BannerAd bannerAd, Customer360APIBean banner) {

		int projectID = DatabaseUtil.getUserWorkspaceID(ConfigInput.workspaceName);
		int companyId = DatabaseUtil.getValueFromColumn("company_id", ConfigInput.dbAppSchemaName, "company",
				"company_name='" + ConfigInput.companyName + "'");
		RequestSpecification genericReq = initAPIReq();
		RequestSpecification bannerAdReq = null;

		if (bannerAd.equals(BannerAd.LAST3MONTH)) {
			bannerAdReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("nbadesigner-backend/nba/bannerAdsGraph/" + banner.customer_id + "/last_3_months").queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		} else if (bannerAd.equals(BannerAd.PRIOR3MONTH)) {
			bannerAdReq = given().spec(genericReq).relaxedHTTPSValidation()
					.basePath("nbadesigner-backend/nba/bannerAdsGraph/" + banner.customer_id + "/prior_3_months").queryParam("brandName", ConfigInput.api_brandName).queryParam("projectId", projectID).queryParam("companyId", companyId).log()
					.all();
		}

		Response resp = bannerAdReq.when().get();

		pNode.info("Response for the api request is:" + resp.prettyPrint());
		logToConsole.info("Response for the api request is:" + resp.prettyPrint());
		return resp;

	}

	public void validateBannerAd(Response resp) {
		pNode.info("Validate the banner Ad api response");
		logToConsole.info("Validate the banner Ad api response");
		ResponseSpecification genericResp = initAPIResp();
		BannerAdResp bannerResponse = resp.then().spec(genericResp).extract().as(BannerAdResp.class);

		System.out.println("The api response is: " + resp.asString());

		String expectedResponseDetail = "Records have been fetched";
		String actualResponseDetail = bannerResponse.getResponse().getDetail();

		if (!(expectedResponseDetail).equalsIgnoreCase(actualResponseDetail)) {
			pNode.fail("Record have not been fetched for banner Ad response, {expected response detail is} : "
					+ expectedResponseDetail + "and {actual response detail is} : " + actualResponseDetail);
		} else {
			pNode.pass("Record fetched for banner Ad response. " + " expected response detail is: "
					+ expectedResponseDetail + "and  actual response detail is: " + actualResponseDetail);
		}

		if (Integer.parseInt(bannerResponse.getResponse().getStatus()) != 200) {
			pNode.fail(
					"Record is not fetched for banner Ad response, error: " + bannerResponse.getResponse().getStatus());
		} else {
			pNode.pass("Record is fetched for banner Ad response successfully with status code 200");
		}
	}
}
