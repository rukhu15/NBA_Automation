package customer360.apiPojo.apiResp.filterValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

	@SerializedName("Segment")
	@Expose
	private Filter segment;
	@SerializedName("Specialty")
	@Expose
	private Filter specialty;
	@SerializedName("Geography")
	@Expose
	private Filter geography;
	@SerializedName("Compliance")
	@Expose
	private Filter compliance;
	@SerializedName("Customer Type")
	@Expose
	private Filter customer_Type;

	public Filter getGeography() {
		return geography;
	}

	public void setGeography(Filter geography) {
		this.geography = geography;
	}

	public Filter getCompliance() {
		return compliance;
	}

	public void setCompliance(Filter compliance) {
		this.compliance = compliance;
	}

	public Filter getCustomer_Type() {
		return customer_Type;
	}

	public void setCustomer_Type(Filter customer_Type) {
		this.customer_Type = customer_Type;
	}

	public Filter getSegment() {
		return segment;
	}

	public void setSegment(Filter segment) {
		this.segment = segment;
	}

	public Filter getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Filter specialty) {
		this.specialty = specialty;
	}

}
