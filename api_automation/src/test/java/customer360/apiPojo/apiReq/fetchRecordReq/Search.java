package customer360.apiPojo.apiReq.fetchRecordReq;

import com.fasterxml.jackson.annotation.JsonInclude;

public class Search {
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private String name;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
