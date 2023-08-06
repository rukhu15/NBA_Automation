package customer360.apiPojo.apiReq.filterReq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filter {
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Category Segment;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@SerializedName("Customer Type")
	@Expose
	private Category customerType;

	public Category getSegment() {
		return Segment;
	}

	public void setSegment(Category segment) {
		Segment = segment;
	}

	public Category getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Category customerType) {
		this.customerType = customerType;
	}

}
