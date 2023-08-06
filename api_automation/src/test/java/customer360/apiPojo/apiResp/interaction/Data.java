package customer360.apiPojo.apiResp.interaction;

import java.util.List;

public class Data {
	private Meta meta;
	private NRXPerfromance nrx_performance;
	private Calls calls;
	private Email email;
	private Digital digital;
	private Events events;
	private List<String> categories;

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public NRXPerfromance getNrx_performance() {
		return nrx_performance;
	}

	public void setNrx_performance(NRXPerfromance nrx_performance) {
		this.nrx_performance = nrx_performance;
	}

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

	public Digital getDigital() {
		return digital;
	}

	public void setDigital(Digital digital) {
		this.digital = digital;
	}

	public Events getEvents() {
		return events;
	}

	public void setEvents(Events events) {
		this.events = events;
	}
}
