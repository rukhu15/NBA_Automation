package tagManagement.dataProvidersPackage;

import java.util.Iterator;

import org.testng.annotations.DataProvider;

import framework.utility.dataParam.Data;
import tagManagement.data.beans.api.TagManagementBean;

public class APIDP {
	@DataProvider(name = "createTag")
	public static Iterator<Object[]> createTag() {
		return Data.listOf(TagManagementBean.class).from("createTag");
	}

	@DataProvider(name = "getNameSpaceName")
	public static Iterator<Object[]> getNameSpaceName() {
		return Data.listOf(TagManagementBean.class).from("getNameSpaceName");
	}

	@DataProvider(name = "getTagDetails")
	public static Iterator<Object[]> getTagDetails() {
		return Data.listOf(TagManagementBean.class).from("getTagDetails");
	}

	@DataProvider(name = "getSavedTagDetails")
	public static Iterator<Object[]> getSavedTagDetails() {
		return Data.listOf(TagManagementBean.class).from("getSavedTagDetails");
	}

	@DataProvider(name = "getPermissionDetails")
	public static Iterator<Object[]> getPermissionDetails() {
		return Data.listOf(TagManagementBean.class).from("getPermissionDetails");
	}

	@DataProvider(name = "deleteTag")
	public static Iterator<Object[]> deleteTag() {
		return Data.listOf(TagManagementBean.class).from("deleteTag");
	}

	@DataProvider(name = "updateTagName")
	public static Iterator<Object[]> updateTagName() {
		return Data.listOf(TagManagementBean.class).from("updateTagName");
	}

	@DataProvider(name = "getFilters")
	public static Iterator<Object[]> getFilters() {
		return Data.listOf(TagManagementBean.class).from("getFilters");
	}

	@DataProvider(name = "searchContent")
	public static Iterator<Object[]> searchContent() {
		return Data.listOf(TagManagementBean.class).from("searchContent");
	}

	@DataProvider(name = "applyFilter")
	public static Iterator<Object[]> applyFilter() {
		return Data.listOf(TagManagementBean.class).from("applyFilter");
	}

	@DataProvider(name = "search_ApplyFilter")
	public static Iterator<Object[]> search_ApplyFilter() {
		return Data.listOf(TagManagementBean.class).from("search_ApplyFilter");
	}

	@DataProvider(name = "addBulkTag")
	public static Iterator<Object[]> addBulkTag() {
		return Data.listOf(TagManagementBean.class).from("addBulkTag");
	}

	@DataProvider(name = "saveBulkTag")
	public static Iterator<Object[]> saveBulkTag() {
		return Data.listOf(TagManagementBean.class).from("saveBulkTag");
	}

	@DataProvider(name = "addKeyTopics")
	public static Iterator<Object[]> addKeyTopics() {
		return Data.listOf(TagManagementBean.class).from("addKeyTopics");
	}

    @DataProvider(name = "getPlaceHolderName")
	public static Iterator<Object[]> getPlaceHolderName()
	{ return Data.listOf(TagManagementBean.class).from("getPlaceHolderName"); }  }



