package tagManagement.apiPojo.apiReq.searchAPI;

public class SearchContentReq {

	private String search_string;
	private Filters filters;

	public String getSearch_string() {
		return search_string;
	}

	public void setSearch_string(String search_string) {
		this.search_string = search_string;
	}

	public Filters getFilters() {
		return filters;
	}

	public void setFilters(Filters filters) {
		this.filters = filters;
	}

}
