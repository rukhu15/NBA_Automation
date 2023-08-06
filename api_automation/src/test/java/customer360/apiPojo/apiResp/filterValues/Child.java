package customer360.apiPojo.apiResp.filterValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Child {

	@SerializedName("Market Segment")
	@Expose
	private FilterCategory marketSegment;
	@SerializedName("Market Potential")
	private FilterCategory marketPotential;
	@SerializedName("Market Segment Group")
	private FilterCategory marketSegmentGroup;
	@SerializedName("Customer Purchasing Decision Making ")
	@Expose
	private FilterCategory customerPurchasingDecisionMaking ;
	@SerializedName("Regulatory Environment")
	@Expose
	private FilterCategory regulatoryEnvironment;
	@SerializedName("Practice Pattern")
	@Expose
	private FilterCategory practicePattern;
	@SerializedName("Health System Affiliation")
	@Expose
	private FilterCategory healthSystemAffiliation;
	@SerializedName("Market Potential Value")
	@Expose
	private FilterCategory marketPotentialValue;
	@SerializedName("Rx Segment")
	@Expose
	private FilterCategory rxSegment;
	@SerializedName("Brand Segment")
	@Expose
	private FilterCategory brandSegment;


	private FilterCategory State;
	private FilterCategory Region;
	private FilterCategory District;

	private FilterCategory Specialty;
	@SerializedName("Specialty Group")
	@Expose
	private FilterCategory specialtyGroup;

	@SerializedName("HO E-Mail Opt-out")
	@Expose
	private FilterCategory hoEMailOptOut;
	@SerializedName("AMA Do Not Contact")
	@Expose
	private FilterCategory aMADoNotContact;
	@SerializedName("AMA PDRP Indicator")
	@Expose
	private FilterCategory amaPDRPIndicator;
	public FilterCategory getMarketSegment() {
		return marketSegment;
	}

	public void setMarketSegment(FilterCategory marketSegment) {
		this.marketSegment = marketSegment;
	}

	public FilterCategory getMarketSegmentGroup() {
		return marketSegmentGroup;
	}

	public void setMarketSegmentGroup(FilterCategory marketSegmentGroup) {
		this.marketSegmentGroup = marketSegmentGroup;
	}

	public FilterCategory getCustomerPurchasingDecisionMaking() {
		return customerPurchasingDecisionMaking;
	}

	public void setCustomerPurchasingDecisionMaking(FilterCategory customerPurchasingDecisionMaking) {
		this.customerPurchasingDecisionMaking = customerPurchasingDecisionMaking;
	}

	public FilterCategory getRegulatoryEnvironment() {
		return regulatoryEnvironment;
	}

	public void setRegulatoryEnvironment(FilterCategory regulatoryEnvironment) {
		this.regulatoryEnvironment = regulatoryEnvironment;
	}

	public FilterCategory getPracticePattern() {
		return practicePattern;
	}

	public void setPracticePattern(FilterCategory practicePattern) {
		this.practicePattern = practicePattern;
	}

	public FilterCategory getHealthSystemAffiliation() {
		return healthSystemAffiliation;
	}

	public void setHealthSystemAffiliation(FilterCategory healthSystemAffiliation) {
		this.healthSystemAffiliation = healthSystemAffiliation;
	}

	public FilterCategory getMarketPotentialValue() {
		return marketPotentialValue;
	}

	public void setMarketPotentialValue(FilterCategory marketPotentialValue) {
		this.marketPotentialValue = marketPotentialValue;
	}

	public FilterCategory getState() {
		return State;
	}

	public void setState(FilterCategory state) {
		State = state;
	}

	public FilterCategory getRegion() {
		return Region;
	}

	public void setRegion(FilterCategory region) {
		Region = region;
	}

	public FilterCategory getDistrict() {
		return District;
	}

	public void setDistrict(FilterCategory district) {
		District = district;
	}

	public FilterCategory getSpecialtyGroup() {
		return specialtyGroup;
	}

	public void setSpecialtyGroup(FilterCategory specialtyGroup) {
		this.specialtyGroup = specialtyGroup;
	}

	public FilterCategory getHoEMailOptOut() {
		return hoEMailOptOut;
	}

	public void setHoEMailOptOut(FilterCategory hoEMailOptOut) {
		this.hoEMailOptOut = hoEMailOptOut;
	}

	public FilterCategory getaMADoNotContact() {
		return aMADoNotContact;
	}

	public void setaMADoNotContact(FilterCategory aMADoNotContact) {
		this.aMADoNotContact = aMADoNotContact;
	}

	public FilterCategory getAmaPDRPIndicator() {
		return amaPDRPIndicator;
	}

	public void setAmaPDRPIndicator(FilterCategory amaPDRPIndicator) {
		this.amaPDRPIndicator = amaPDRPIndicator;
	}

	public FilterCategory getNoSeeFlag() {
		return noSeeFlag;
	}

	public void setNoSeeFlag(FilterCategory noSeeFlag) {
		this.noSeeFlag = noSeeFlag;
	}

	public FilterCategory getClientType() {
		return clientType;
	}

	public void setClientType(FilterCategory clientType) {
		this.clientType = clientType;
	}

	@SerializedName("No See Flag")
	@Expose
	private FilterCategory noSeeFlag;

	@SerializedName("Client Type")
	@Expose
	private FilterCategory clientType;

	public FilterCategory getBrandSegment() {
		return brandSegment;
	}

	public void setBrandSegment(FilterCategory brandSegment) {
		this.brandSegment = brandSegment;
	}

	public FilterCategory getMarketPotential() {
		return marketPotential;
	}

	public void setMarketPotential(FilterCategory marketPotential) {
		this.marketPotential = marketPotential;
	}

	public FilterCategory getRxSegment() {
		return rxSegment;
	}

	public void setRxSegment(FilterCategory rxSegment) {
		this.rxSegment = rxSegment;
	}

	public FilterCategory getSpecialty() {
		return Specialty;
	}

	public void setSpecialty(FilterCategory specialty) {
		Specialty = specialty;
	}

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
