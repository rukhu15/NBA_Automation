package scripts;

import framework.utility.globalConst.ConfigInput;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * @author Prateek Sethi
 */
public class BaseAPI {
    protected static RequestSpecification req;
    protected static ResponseSpecification resp;

    public static RequestSpecification initAPIReq() {
        String baseURI= ConfigInput.baseURI;
        String bearerToken= ConfigInput.apiBearerToken;
        if(req == null) {
            req = new RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .setBaseUri(baseURI)
                    .setRelaxedHTTPSValidation("SSL")
                    .addHeader("Authorization", "Bearer " + bearerToken).build();
        }
        return req;
    }

    public static RequestSpecification initAPIReq(String baseURI) {
        String bearerToken= ConfigInput.apiBearerToken;

            req = new RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .setBaseUri(baseURI)
                    .setRelaxedHTTPSValidation("SSL")
                    .addHeader("Authorization", "Bearer " + bearerToken).build();

        return req;
    }

    public static ResponseSpecification initAPIResp() {
        if(resp == null) {
            resp = new ResponseSpecBuilder().
                    expectContentType("application/json").
                    build();
        }
        return resp;
    }

    public static <T> RequestSpecification getRequestSpecification(String basePath, Map<String,?> queryParamsMap,T pojoType){
        String baseURI= ConfigInput.baseURI;
        String bearerToken= ConfigInput.apiBearerToken;

        RequestSpecification req = new RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .setBaseUri(baseURI)
                    .setRelaxedHTTPSValidation("SSL")
                    .addHeader("Authorization", "Bearer " + bearerToken).build()
                    .relaxedHTTPSValidation()
                    .basePath(basePath)
                    .body(pojoType);

        if(queryParamsMap != null && ! queryParamsMap.isEmpty()){
                req.queryParams(queryParamsMap);
        }
        return given().spec(req);
    }


}