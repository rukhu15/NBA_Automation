package framework.utility.globalConst;

import framework.utility.propertiesManager.AutProperties;
import org.testng.Assert;

public class ConfigInput {
    public static String browser,
            language,
            country,
            defaultCountry,
            url,
            siqAdminId,
            siqAdminPwd,
            siqUserId,
            siqUserPwd,
            lastUser,
            fileName,
            dataProviderClassName,
            dataDir,
            executionMode,
            hubURL,
            dbHostName,
            dbName,
            dbUserName,
            dbPassword,
            dbSchemaName,
            dbAppSchemaName,
            dbContentSchemaName,
            baseURI,
            apiBearerToken,
            test_type,
            zeppelinNotebookName,
            wideBaseTable,
            workspaceName,
            nameSpaceName,
            apiUserName,
            athenaOutputBucket,
            athenaQueryDB,
            region,
            client_id,
            forPublishHistTable,
            username,
            password,
            security_token,
            env_url,
            clientid,
            clientsecret,
            useOAuth2Authentication,
            accountMID,
            webserviceurl,
            api_scopes,
            api_service,
            ft_server_host_address,
            sfmcpassword,
            sfmcusername,
            api_brandName,
            companyName,
            debugMode,
            moduleToUseForCreatingModel,
            optionForCreatingNewModel,
            baseModelDesign,
            baseAlgorithm,
            aimlSchemaName,

            segmentationSchemaName,

            tagSchemaName;


    public static boolean isAssert, isConfirm;

    public static int additionalWaitTime;

    /**
     * C O N F I G U R A T I O N   I N P U T
     */
    public static void init(String aut_prop_file_name) {

        try {
            AutProperties Me = AutProperties.getInstance(aut_prop_file_name);
            browser = Me.getProperty("browser.name");

            // Localization code
            language = Me.getProperty("locale.language");
            country = Me.getProperty("locale.country");
            defaultCountry = Me.getProperty("default.country");

            // application url
            url = Me.getProperty("app.url");
            siqUserId = Me.getProperty("user.login.id");
            siqUserPwd = Me.getProperty("user.login.password");
            siqAdminId = Me.getProperty("admin.login.id");
            siqAdminPwd = Me.getProperty("admin.login.password");
            fileName = Me.getProperty("data.fileName");
            dataProviderClassName = Me.getProperty("data.dataProviderClass");
            dataDir = Me.getProperty("data.dataDir");
            executionMode = Me.getProperty(("grid.execution.mode"));
            hubURL = Me.getProperty("grid.hub.url");
            dbHostName = Me.getProperty("db.hostName");
            dbName = Me.getProperty("db.Name");
            dbUserName = Me.getProperty("db.userName");
            dbPassword = Me.getProperty("db.passWord");
            dbSchemaName = Me.getProperty("db.schema_name");
            dbAppSchemaName = Me.getProperty("db.app_schema_name");
            dbContentSchemaName = Me.getProperty("db.content_schema_name");
            athenaOutputBucket = Me.getProperty("aws.athenaOutputBucket");
            athenaQueryDB = Me.getProperty("aws.athenaQueryDB");
            region = Me.getProperty("aws.region");
            client_id = Me.getProperty("aws.client_id");
            baseURI = Me.getProperty("api.baseURI");
            apiBearerToken = Me.getProperty("api.bearerToken");
            test_type = Me.getProperty("test.type");
            zeppelinNotebookName = Me.getProperty("api.zeppelinNotebookName");
            wideBaseTable = Me.getProperty("api.wide_base_table");
            forPublishHistTable = Me.getProperty("api.forPublishHistTable");
            workspaceName = Me.getProperty("api.workspaceName");
            nameSpaceName = Me.getProperty("api.nameSpaceName");
            apiUserName   =Me.getProperty("api.userName");
            username = Me.getProperty("veeva.username");
            password = Me.getProperty("veeva.password");
            security_token = Me.getProperty("veeva.security_token");
            env_url = Me.getProperty("veeva.env_url");
            clientid = Me.getProperty("sfmc.clientid");
            clientsecret = Me.getProperty("sfmc.clientsecret");
            accountMID = Me.getProperty("sfmc.accountMID");
            useOAuth2Authentication = Me.getProperty("sfmc.useOAuth2Authentication");
            webserviceurl = Me.getProperty("sfmc.webserviceurl");
            sfmcpassword = Me.getProperty("sfmc.sfmcpassword");
            sfmcusername = Me.getProperty("sfmc.sfmcusername");
            api_scopes = Me.getProperty("sfmc.api_scopes");
            api_service = Me.getProperty("sfmc.api_service");
            ft_server_host_address = Me.getProperty("sfmc.ft_server_host_address");
            api_brandName=Me.getProperty("api.brandName");
            companyName=Me.getProperty("db.companyName");
            debugMode=Me.getProperty("browser.debugMode");
            moduleToUseForCreatingModel=Me.getProperty("aiml.moduleToUseForCreatingModel");
            optionForCreatingNewModel=Me.getProperty("aiml.optionForCreatingNewModel");
            baseModelDesign=Me.getProperty("aiml.baseModelDesign");
            baseAlgorithm=Me.getProperty("aiml.baseAlgorithm");
            aimlSchemaName=Me.getProperty("aiml.schemaName");
            segmentationSchemaName=Me.getProperty("segmentation.schemaName");
            tagSchemaName=Me.getProperty("tag.schemaName");


            if (browser.equalsIgnoreCase("ie")) {
                // sync config for Ie
                additionalWaitTime = 500;
            } else {
                //sync config for Rest of browsers
                additionalWaitTime = 250;
            }

            isAssert = true;
            isConfirm = true;

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Some thing went wrong, check automation.properties File");
        }
    }


}