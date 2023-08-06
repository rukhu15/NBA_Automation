package dataProvidersPackage;

import java.util.Iterator;

import data.beans.*;
import org.testng.annotations.DataProvider;
import framework.utility.dataParam.BaseBean;
import framework.utility.dataParam.Data;

public class UIDP extends ScenarioPageBean implements BaseBean {
	@DataProvider(name = "createScenario")
	public static Iterator<Object[]> createScenario() {
		return Data.listOf(ScenarioPageBean.class).from("createScenario");
	}

	@DataProvider(name = "editScenario")
	public static Iterator<Object[]> editScenario() {
		return Data.listOf(ScenarioPageBean.class).from("editScenario");
	}

	@DataProvider(name = "selectScenario")
	public static Iterator<Object[]> selectScenario() {
		return Data.listOf(ScenarioPageBean.class).from("selectScenario");
	}

	@DataProvider(name = "scheduleScenario")
	public static Iterator<Object[]> scheduleScenario() {
		return Data.listOf(ScenarioPageBean.class).from("scheduleScenario");
	}
	
	@DataProvider(name = "rescheduleScenario")
	public static Iterator<Object[]> rescheduleScenario() {
		return Data.listOf(ScenarioPageBean.class).from("rescheduleScenario");
	}
	
	@DataProvider(name = "deleteScenario")
	public static Iterator<Object[]> deleteScenario() {
		return Data.listOf(ScenarioPageBean.class).from("deleteScenario");
	}

	@DataProvider(name = "createDesign")
	public static Iterator<Object[]> createDesign() {
		return Data.listOf(DesignPageBean.class).from("createAudience");
	}

	@DataProvider(name = "createModel")
	public static Iterator<Object[]> createModel() {return Data.listOf(AimlModelPageBean.class).from("createModel"); }
	@DataProvider(name = "createTag")
	public static Iterator<Object[]> createTag() { return Data.listOf(TagPageBean.class).from("createTag");}

	@DataProvider(name = "createWorkFlow")
	public static Iterator<Object[]> createWorkFlow(){ return Data.listOf(ModelPageBean.class).from("createWorkFlow");}

	@DataProvider(name = "createKpi")
	public static Iterator<Object[]> createKpi() { return Data.listOf(KpiPageBean.class).from("createKpi"); }

	@DataProvider(name = "createAdjudicate")
	public static Iterator<Object[]> createAdjudicate() { return Data.listOf(AdjudicatePageBean.class).from("createAdjudicate"); }

	@DataProvider(name = "createSegment")
	public static Iterator<Object[]> createSegment() { return Data.listOf(SegmentationPageBean.class).from("createSegment"); }

	@DataProvider(name = "deleteObject")
	public static Iterator<Object[]> deleteObject() { return Data.listOf(DeleteObjectPageBean.class).from("deleteObject"); }

}