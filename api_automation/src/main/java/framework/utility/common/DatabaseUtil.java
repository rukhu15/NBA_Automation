package framework.utility.common;

import framework.utility.globalConst.ConfigInput;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Prateek Sethi
 */
public class DatabaseUtil {

	static String dbHostName = ConfigInput.dbHostName;
	static String dbName = ConfigInput.dbName;
	static String dbUserName = ConfigInput.dbUserName;
	static String dbPassword = ConfigInput.dbPassword;
	static Connection connection = null;
	static Statement statement = null;
	static PreparedStatement prepStatement = null;
	static ResultSet resultSet = null;

	public static Connection getDBConnection() {
		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection("jdbc:postgresql://" + dbHostName + "/" + dbName, dbUserName,
						dbPassword);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException throwables) {
			}
		}
		return connection;
	}

	public static void runUpdateQuery(String query) {
		try {
			if (connection == null) {
				connection = getDBConnection();
			}
			System.out.println("DB connection is :" + connection);
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.executeUpdate(query);
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet runSelectQuery(String query) {
		try {
			if (connection == null) {
				connection = getDBConnection();
			}
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public static boolean validateResultSetForBoolean(ResultSet rs, String columnName, boolean expectedValue) {
		try {
			while (rs.next()) {
				boolean actualValue = rs.getBoolean(columnName);
				if (actualValue != expectedValue) {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void closeDBResources() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}

			if (prepStatement != null) {
				prepStatement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static int getUserWorkspaceID(String projectName) {
		String query = "select project_id from " + ConfigInput.dbAppSchemaName + ".project\n" + "where project_name=?";
		int workspaceID = 0;
		try {
			if (connection == null) {
				connection = getDBConnection();
			}
			connection.setAutoCommit(false);
			prepStatement = connection.prepareStatement(query);
			prepStatement.setString(1, projectName);
			resultSet = prepStatement.executeQuery();
			connection.commit();
			if (resultSet.next()) {
				workspaceID = resultSet.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return workspaceID;
	}

	public static int getTagSeqID(String tagName) {
		String query = "select tag_seq_id from " + ConfigInput.dbContentSchemaName
				+ ".cm_tag where tag_name =?  and is_deleted = 'false'";
		int tag_seq_id = 0;
		try {
			if (connection == null) {
				connection = getDBConnection();
			}
			connection.setAutoCommit(false);
			prepStatement = connection.prepareStatement(query);
			prepStatement.setString(1, tagName);
			resultSet = prepStatement.executeQuery();
			connection.commit();
			if (resultSet.next()) {
				tag_seq_id = resultSet.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tag_seq_id;
	}

	public static int getNamespaceID(String nameSpaceName) {
		String query = "select nmspc_id from " + ConfigInput.dbContentSchemaName + ".cm_nmspc  where nmspc_name =?";
		int nmspc_id = 0;
		try {
			if (connection == null) {
				connection = getDBConnection();
			}
			connection.setAutoCommit(false);
			prepStatement = connection.prepareStatement(query);
			prepStatement.setString(1, nameSpaceName);
			resultSet = prepStatement.executeQuery();
			connection.commit();
			if (resultSet.next()) {
				nmspc_id = resultSet.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nmspc_id;

	}

	public static int getUserID(String user_name) {
		String query = "select user_id from " + ConfigInput.dbAppSchemaName + ".user\n" + "where user_name=?";
		int userID = 0;
		try {
			if (connection == null) {
				connection = getDBConnection();
			}
			connection.setAutoCommit(false);
			prepStatement = connection.prepareStatement(query);
			prepStatement.setString(1, user_name);
			resultSet = prepStatement.executeQuery();
			connection.commit();
			if (resultSet.next()) {
				userID = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userID;
	}

	public static int getScenarioID(String scenarioName) {
		int scenarioID = 0;
		String query = "select scenario_id from " + ConfigInput.dbSchemaName + ".nba_scenario\n"
				+ "where scenario_name=? and is_deleted =false\n" + "order by scenario_id desc\n" + "limit 1;";

		try {
			if (connection == null) {
				connection = getDBConnection();
			}
			connection.setAutoCommit(false);
			prepStatement = connection.prepareStatement(query);
			prepStatement.setString(1, scenarioName);
			resultSet = prepStatement.executeQuery();
			connection.commit();
			if (resultSet.next()) {
				scenarioID = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return scenarioID;
	}

	public static int getCurrentActiveAudienceIDForScenario(int scenario_id, int workspace_id) {
		int audienceId = 0;
		String query = "select max(audience_id) from " + ConfigInput.dbSchemaName + ".nba_scenario ns\n" + "join "
				+ ConfigInput.dbSchemaName + ".nba_xref_scenario_audience nxsa \n"
				+ "on ns.scenario_id =nxsa.scenario_id \n" + "where nxsa .wrkspc_id =" + workspace_id
				+ " and nxsa .is_active =true\n" + "and ns.scenario_id =" + scenario_id;
		try {
			ResultSet rs = runSelectQuery(query);
			if (rs.next())
				audienceId = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return audienceId;
	}

	public static List<Integer> getListOfChannelIds(List<String> arrChannels) {
		List<Integer> channelId = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arrChannels.size(); i++) {
			if (i == arrChannels.size() - 1) {
				sb.append("'" + arrChannels.get(i) + "'");
			} else {
				sb.append("'" + arrChannels.get(i) + "',");
			}
		}

		String query = "select channel_id from " + ConfigInput.dbSchemaName + ".ciq_channel\n"
				+ "where channel_name in (" + sb.toString() + ");";
		System.out.println("#####Query is: ###########" + query);
		try {
			ResultSet rs = runSelectQuery(query);
			while (rs.next()) {
				channelId.add(rs.getInt("channel_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return channelId;
	}

	public static int getChannelID(String channelName) {
		int channel_id = 0;
		String query = "select channel_id from " + ConfigInput.dbSchemaName + ".ciq_channel\n"
				+ "where channel_name = ?;";

		try {
			if (connection == null) {
				connection = getDBConnection();
			}
			connection.setAutoCommit(false);
			prepStatement = connection.prepareStatement(query);
			prepStatement.setString(1, channelName);
			resultSet = prepStatement.executeQuery();
			connection.commit();
			if (resultSet.next()) {
				channel_id = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return channel_id;
	}

	public static int getAdjChannelID(int scenario_id, int workspace_id) {
		int adjChannelId = 0;
		String query = "select adj_channel_id from " + ConfigInput.dbSchemaName + ".nba_scenario ns \n" + "join "
				+ ConfigInput.dbSchemaName + ".nba_adj_channel nac \n" + "on ns.scenario_id = nac.scenario_id \n"
				+ "join " + ConfigInput.dbSchemaName + ".ciq_channel cc \n" + "on cc.channel_id = nac.channel_id \n"
				+ "where ns.wrkspc_id =" + workspace_id + "and ns.scenario_id =" + scenario_id;

		try {
			ResultSet rs = runSelectQuery(query);
			if (rs.next())
				adjChannelId = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return adjChannelId;
	}

	public static int getAudienceIDTobeDelatedForScenario(String audienc_name) {
		int audienceId = 0;
		String query = "select  max(audience_id) from " + ConfigInput.dbSchemaName + ".nba_audience na\r\n"
				+ "where na.audience_name = '" + audienc_name + "' and na.is_active ='true' ";
		try {
			ResultSet rs = runSelectQuery(query);
			if (rs.next())
				audienceId = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return audienceId;
	}

	public static int getDataSetCategoryId(String category_name) {
		int category_id = 0;

		String query = "select cdc.category_id from " + ConfigInput.dbSchemaName + ".nba_category cdc\n"
				+ "where cdc.category_name =?";

		try {
			if (connection == null) {
				connection = getDBConnection();
			}
			connection.setAutoCommit(false);
			prepStatement = connection.prepareStatement(query);
			prepStatement.setString(1, category_name);
			resultSet = prepStatement.executeQuery();
			connection.commit();
			if (resultSet.next()) {
				category_id = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category_id;
	}

	public static int getSubCategoryDataSetTypeId(String dataset_type_name) {
		int subcategory_id = 0;

		String query = "select cdc.subcategory_id from " + ConfigInput.dbSchemaName + ".nba_subcategory cdc\n"
				+ "where cdc.subcategory_name =?";

		try {
			if (connection == null) {
				connection = getDBConnection();
			}
			connection.setAutoCommit(false);
			prepStatement = connection.prepareStatement(query);
			prepStatement.setString(1, dataset_type_name);
			resultSet = prepStatement.executeQuery();
			connection.commit();
			if (resultSet.next()) {
				subcategory_id = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return subcategory_id;
	}

	public static int getKPiId(String formulaName) {
		int id = 0;
//		String query = "select cdc.kpi_id from " + ConfigInput.dbSchemaName + ".nba_kpi cdc\n"
//				+ "where cdc.kpi_name =? and cdc.is_deleted=false";

		String query = "select nxwk.kpi_id from " + ConfigInput.dbSchemaName + ".nba_xref_workspace_kpi nxwk"
				+ " where kpi_id in (select kpi_id from " + ConfigInput.dbSchemaName + ".nba_kpi kpi where kpi.kpi_name= ?)"
				+ " and nxwk.is_deleted=false and nxwk.wrkspc_id=10";
		try {
			if (connection == null) {
				connection = getDBConnection();
			}
			connection.setAutoCommit(false);
			prepStatement = connection.prepareStatement(query);
			prepStatement.setString(1, formulaName);
			resultSet = prepStatement.executeQuery();
			connection.commit();
			if (resultSet.next()) {
				id = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static boolean isKPIExistsInDb(String kpiName) {
		boolean result = false;
//		String query = "select exists(select 1 from " + ConfigInput.dbSchemaName
//				+ ".nba_kpi kpi where kpi.kpi_name= ? and kpi.is_deleted=false)";
		
		String query = "select exists(select 1 from " + ConfigInput.dbSchemaName + ".nba_xref_workspace_kpi nxwk "
				+ "where kpi_id in (select kpi_id from " + ConfigInput.dbSchemaName
				+ ".nba_kpi kpi where kpi.kpi_name= ?) " + " and nxwk.is_deleted=false and nxwk.wrkspc_id=10)";

		try {
			if (connection == null) {
				connection = getDBConnection();
			}
			connection.setAutoCommit(false);
			prepStatement = connection.prepareStatement(query);
			prepStatement.setString(1, kpiName);
			resultSet = prepStatement.executeQuery();
			connection.commit();
			if (resultSet.next()) {
				result = resultSet.getBoolean(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getchannelDBValue(String channelName) {
		String channel_db_value = "";
		String query = "select db_value from " + ConfigInput.dbSchemaName + ".ciq_channel\n"
				+ "where channel_name = ?;";

		try {
			if (connection == null) {
				connection = getDBConnection();
			}
			connection.setAutoCommit(false);
			prepStatement = connection.prepareStatement(query);
			prepStatement.setString(1, channelName);
			resultSet = prepStatement.executeQuery();
			connection.commit();
			if (resultSet.next()) {
				channel_db_value = resultSet.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return channel_db_value;
	}

	public static <T> T getValueFromColumn(String columnName, String dbSchemaName, String TableName,
			String whereClause) {
		T column_value = null;
		String query = "select " + columnName + " from " + dbSchemaName + "." + TableName + " where " + whereClause;
		try {
			if (connection == null) {
				connection = getDBConnection();
			}
			connection.setAutoCommit(false);
			prepStatement = connection.prepareStatement(query);
			resultSet = prepStatement.executeQuery();
			connection.commit();
			if (resultSet.next()) {
				column_value = (T) resultSet.getObject(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return column_value;
	}

	public static List<Integer> getWorkflowIDsForScenario(int scenario_id, int workspace_id) {
		List<Integer> workflowIDs = new ArrayList<>();

		String query = "select workflow_id from " + ConfigInput.dbSchemaName + ".nba_workflow\n"
				+ "where scenario_id = " + scenario_id + " and is_enabled =true and is_active=true;";
		System.out.println("#####Query is: ###########" + query);
		try {
			ResultSet rs = runSelectQuery(query);
			while (rs.next()) {
				workflowIDs.add(rs.getInt("workflow_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return workflowIDs;
	}

	public static void deleteExistingAutomationScenario(String ScenarioName){
		System.out.println("Going to delete all scenarios for given user");
		String query="UPDATE "+ ConfigInput.dbSchemaName+".nba_scenario a\n" +
				"SET is_deleted=true \n" +
				"FROM "+ConfigInput.dbAppSchemaName+".user b\n" +
				"WHERE a.created_by =b.user_id\n" +
				"and b.user_name ='"+ConfigInput.apiUserName+"'\n"+
				"and a.scenario_name ='"+ScenarioName+"'";
		System.out.println(query);
		runUpdateQuery(query);
	}

	public static void deleteExistingTag(String schemaName,String tagName){
		String query="update "+schemaName+".cm_tag set is_active=false, is_deleted=true where is_active=true and tag_name='"+tagName+"'";
		System.out.println(query);
		runUpdateQuery(query);
	}

	public static int getModelIdForDeletion(String schemaName, String modelName) {
		int modelId = 0;
		try {
			String query = "select model_id from " + schemaName + "." + "ai_model where model_name = '" + modelName + "'";
			ResultSet rs = runSelectQuery(query);
			if (rs.next()) {
				modelId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modelId;
	}

	public static int getSegmentNameForDeletion(String schemaName, String segmentName) {
		int schemaId = 0;
		try {
			String query = "select seg_rule_schema_id from " + schemaName + ".ci_segment_rule_schema csrs where seg_rule_name = '" + segmentName +"'";
			ResultSet rs = runSelectQuery(query);
			if (rs.next()) {
				schemaId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return schemaId;
	}

	public static void deleteRecordsBasedOnModelId(String schemaName, String modelId){

		String query="delete from " + schemaName + ".ai_xref_model_refresh_run_details where model_id = " + modelId;
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ai_xref_model_source where model_id = " + modelId;
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ai_xref_model_module where model_id = " + modelId;
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ai_model_scenario_details where model_id = " + modelId;
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ai_model_run_details where model_id = " + modelId;
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ai_xref_model_brand where model_id = " + modelId;
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ai_xref_model_control_variables where model_id = " + modelId;
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ai_xref_model_status where model_id = " + modelId;
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ai_model_step_status where model_id = " + modelId;
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ai_xref_model_algorithm where model_id = " + modelId;
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ai_xref_model_last_score_refresh where model_id = " + modelId;
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ai_model_channel_details where model_id = " + modelId;
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ai_model_brand_financial_details where model_id = " + modelId;
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ai_xref_model_bucket where model_id = " + modelId;
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ai_time_configuration_details where model_id = " + modelId;
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ai_model where model_id = " + modelId;
		runUpdateQuery(query);

	}

	public static void deleteRecordsBasedOnSchemaId(String schemaName, String schemaId, String segment){

		String query="delete from " + schemaName + ".ci_segment_rule_details where seg_rule_schema_id = " + schemaId;
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ci_segment_rule_schema where seg_rule_name = '" + segment + "'";
		runUpdateQuery(query);
		query="delete from " + schemaName + ".ci_segment_metadata where seg_display_name = '" + segment + "'";
		runUpdateQuery(query);
	}

}