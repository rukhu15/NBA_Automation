package framework.utility.common;

import java.util.LinkedList;
import java.util.List;

import framework.utility.globalConst.ConfigInput;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.services.athena.model.AthenaException;
import software.amazon.awssdk.services.athena.model.ColumnInfo;
import software.amazon.awssdk.services.athena.model.Datum;
import software.amazon.awssdk.services.athena.model.GetQueryExecutionRequest;
import software.amazon.awssdk.services.athena.model.GetQueryExecutionResponse;
import software.amazon.awssdk.services.athena.model.GetQueryResultsRequest;
import software.amazon.awssdk.services.athena.model.GetQueryResultsResponse;
import software.amazon.awssdk.services.athena.model.QueryExecutionContext;
import software.amazon.awssdk.services.athena.model.QueryExecutionState;
import software.amazon.awssdk.services.athena.model.ResultConfiguration;
import software.amazon.awssdk.services.athena.model.Row;
import software.amazon.awssdk.services.athena.model.StartQueryExecutionRequest;
import software.amazon.awssdk.services.athena.model.StartQueryExecutionResponse;
import software.amazon.awssdk.services.athena.paginators.GetQueryResultsIterable;

/**
 * @author Avinash Gupta
 */
public class AthenaDataUtil {

	static String region = ConfigInput.region;
	static String athenaQueryDB = ConfigInput.athenaQueryDB;
	static String athenaOutputBucket = ConfigInput.athenaOutputBucket;
	public static final long SLEEP_AMOUNT_IN_MS = 1000;
	public static AthenaClient athenaClientConnection = null;

	// Call to get AttenaClient object for Athena Connection
	public static AthenaClient getAthenaConnection() {
		if (athenaClientConnection == null) {
			try {
				athenaClientConnection = AthenaClient.builder().region(Region.of(region))
						.credentialsProvider(DefaultCredentialsProvider.create()).build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return athenaClientConnection;
	}

	// Call to close AttenaClient object
	public static void closeAthenaConnection() {
		try {
			if (athenaClientConnection != null) {
				athenaClientConnection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Submits a query to Amazon Athena and returns the execution ID of the query.
	public static String submitAthenaQuery(String sqlQuery) {
		try {
			if (athenaClientConnection == null) {
				athenaClientConnection = getAthenaConnection();
			}
			// The QueryExecutionContext allows us to set the database.
			QueryExecutionContext queryExecutionContext = QueryExecutionContext.builder().database(athenaQueryDB)
					.build();

			// The result configuration specifies where the results of the query should go.
			ResultConfiguration resultConfiguration = ResultConfiguration.builder().outputLocation(athenaOutputBucket)
					.build();

			StartQueryExecutionRequest startQueryExecutionRequest = StartQueryExecutionRequest.builder()
					.queryString(sqlQuery).queryExecutionContext(queryExecutionContext)
					.resultConfiguration(resultConfiguration).build();

			StartQueryExecutionResponse startQueryExecutionResponse = athenaClientConnection
					.startQueryExecution(startQueryExecutionRequest);
			return startQueryExecutionResponse.queryExecutionId();

		} catch (AthenaException e) {
			e.printStackTrace();
		}
		return "";
	}

	// Wait for an Amazon Athena query to complete, fail or to be cancelled.
	public static void waitForQueryToComplete(String queryExecutionId) {
		try {
			if (athenaClientConnection == null) {
				athenaClientConnection = getAthenaConnection();
			}
			GetQueryExecutionRequest getQueryExecutionRequest = GetQueryExecutionRequest.builder()
					.queryExecutionId(queryExecutionId).build();

			GetQueryExecutionResponse getQueryExecutionResponse;
			boolean isQueryStillRunning = true;
			while (isQueryStillRunning) {
				getQueryExecutionResponse = athenaClientConnection.getQueryExecution(getQueryExecutionRequest);
				String queryState = getQueryExecutionResponse.queryExecution().status().state().toString();
				if (queryState.equals(QueryExecutionState.FAILED.toString())) {
					throw new RuntimeException("The Amazon Athena query failed to run with error message: "
							+ getQueryExecutionResponse.queryExecution().status().stateChangeReason());
				} else if (queryState.equals(QueryExecutionState.CANCELLED.toString())) {
					throw new RuntimeException("The Amazon Athena query was cancelled.");
				} else if (queryState.equals(QueryExecutionState.SUCCEEDED.toString())) {
					isQueryStillRunning = false;
				} else {
					// Sleep an amount of time before retrying again.
					Thread.sleep(SLEEP_AMOUNT_IN_MS);
				}
				System.out.println("The current status is: " + queryState);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// This code retrieves the results of a query and result in List<Object[]>
	public static List<Object[]> processResultRows(String queryExecutionId) {
		if (athenaClientConnection == null) {
			athenaClientConnection = getAthenaConnection();
		}
		waitForQueryToComplete(queryExecutionId);
		List<Object[]> collectedData = new LinkedList<>();
		try {

			// Max Results can be set but if its not set, it will choose the maximum page
			// size.
			GetQueryResultsRequest getQueryResultsRequest = GetQueryResultsRequest.builder()
					.queryExecutionId(queryExecutionId).build();

			GetQueryResultsIterable getQueryResultsResults = athenaClientConnection
					.getQueryResultsPaginator(getQueryResultsRequest);
			for (GetQueryResultsResponse result : getQueryResultsResults) {
				List<ColumnInfo> columnInfoList = result.resultSet().resultSetMetadata().columnInfo();
				List<Row> results = result.resultSet().rows();
				collectedData = processRow(results, columnInfoList);
			}

		} catch (AthenaException e) {
			e.printStackTrace();
		}
		return collectedData;
	}

	// Store Athena query result in List<Object[]>
	private static List<Object[]> processRow(List<Row> row, List<ColumnInfo> columnInfoList) {
		List<Object[]> collectedData = new LinkedList<>();
		for (Row myRow : row) {
			List<Datum> allData = myRow.data();
			int i = 0;
			Object[] rowdata = new Object[columnInfoList.size()];
			for (Datum data : allData) {
				rowdata[i] = data.varCharValue();
				i++;
			}
			collectedData.add(rowdata);
		}
		return collectedData;
	}
}