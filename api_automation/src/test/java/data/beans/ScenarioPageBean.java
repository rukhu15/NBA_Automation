package data.beans;

import framework.utility.dataParam.BaseBean;

public class ScenarioPageBean implements BaseBean {
	public String Notation = null;
	public String testDescription = null;
	public String scenarioName = null;
	public String scenarioDescription = null;
	public String Brand = null;
	public String newScenarioName = null;
	public String newScenarioDesc = null;
	public String startDate = null;
	public String endDate = null;
	public String recurringType = null;

	@Override
	public String toString() {
		return String.format(
				"testDescription: %s|scenarioName: %s|scenarioDescription:  %s|Brand: %s| newScenarioName: %s| newScenarioDesc %s|startDate: %s| endDate:  %s| recurringType: %s",
				testDescription, scenarioName, scenarioDescription, Brand, newScenarioName, newScenarioDesc, startDate,
				endDate, recurringType);
	}

}