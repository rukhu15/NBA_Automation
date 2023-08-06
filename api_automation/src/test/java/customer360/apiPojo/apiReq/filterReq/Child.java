package customer360.apiPojo.apiReq.filterReq;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Child {
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@SerializedName("Rx Segment")
	@Expose
	private SubCategory rxSegment;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@SerializedName("Client Type")
	@Expose
	private SubCategory clientType;

	public SubCategory getRxSegment() {
		return rxSegment;
	}

	public void setRxSegment(SubCategory rxSegment) {
		this.rxSegment = rxSegment;
	}

	public SubCategory getClientType() {
		return clientType;
	}

	public void setClientType(SubCategory clientType) {
		this.clientType = clientType;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String value;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String selected;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

}
