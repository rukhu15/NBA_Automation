package customer360.apiPojo.apiResp.interactionSummary;

public class Data {

	private Calls calls;
	private Email email;
	private BannerAds banner_ads;
	private WebSite website;
	private String sample;
	private String webinar;
	private String speaker_program;
	private String lunch_dinner;

	public Calls getCalls() {
		return calls;
	}

	public void setCalls(Calls calls) {
		this.calls = calls;
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public BannerAds getBanner_ads() {
		return banner_ads;
	}

	public void setBanner_ads(BannerAds banner_ads) {
		this.banner_ads = banner_ads;
	}

	public WebSite getWebsite() {
		return website;
	}

	public void setWebsite(WebSite website) {
		this.website = website;
	}

	public String getSample() {
		return sample;
	}

	public void setSample(String sample) {
		this.sample = sample;
	}

	public String getWebinar() {
		return webinar;
	}

	public void setWebinar(String webinar) {
		this.webinar = webinar;
	}

	public String getSpeaker_program() {
		return speaker_program;
	}

	public void setSpeaker_program(String speaker_program) {
		this.speaker_program = speaker_program;
	}

	public String getLunch_dinner() {
		return lunch_dinner;
	}

	public void setLunch_dinner(String lunch_dinner) {
		this.lunch_dinner = lunch_dinner;
	}
}
