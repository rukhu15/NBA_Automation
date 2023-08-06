package customer360.apiPojo.apiReq.fetchRecordReq;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class FetchRecordRequest {

	private int offset;
	private int limit;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Filter filters;
	private List<SortModel> sort_model;
	private Search search;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String card_selected;

	public String getCard_selected() {
		return card_selected;
	}

	public void setCard_selected(String card_selected) {
		this.card_selected = card_selected;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Filter getFilters() {
		return filters;
	}

	public void setFilters(Filter filters) {
		this.filters = filters;
	}

	public List<SortModel> getSort_model() {
		return sort_model;
	}

	public void setSort_model(List<SortModel> sort_model) {
		this.sort_model = sort_model;
	}

	public Search getSearch() {
		return search;
	}

	public void setSearch(Search search) {
		this.search = search;
	}
}
