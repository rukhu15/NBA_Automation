package customer360.apiPojo.apiResp.email;

public class Chart {

	private String average;
	private String sendtotal;
	private String deliveredtotal;
	private String openedtotal;
	private String clickedtotal;
	private String deliveredrate;
	private String clickedrate;
	private String openedrate;
	private Graph sent;
	private Graph delivered;
	private Graph opened;
	private Graph clicked;

	public String getAverage() {
		return average;
	}

	public void setAverage(String average) {
		this.average = average;
	}

	public String getSendtotal() {
		return sendtotal;
	}

	public void setSendtotal(String sendtotal) {
		this.sendtotal = sendtotal;
	}

	public String getDeliveredtotal() {
		return deliveredtotal;
	}

	public void setDeliveredtotal(String deliveredtotal) {
		this.deliveredtotal = deliveredtotal;
	}

	public String getOpenedtotal() {
		return openedtotal;
	}

	public void setOpenedtotal(String openedtotal) {
		this.openedtotal = openedtotal;
	}

	public String getClickedtotal() {
		return clickedtotal;
	}

	public void setClickedtotal(String clickedtotal) {
		this.clickedtotal = clickedtotal;
	}

	public String getDeliveredrate() {
		return deliveredrate;
	}

	public void setDeliveredrate(String deliveredrate) {
		this.deliveredrate = deliveredrate;
	}

	public String getClickedrate() {
		return clickedrate;
	}

	public void setClickedrate(String clickedrate) {
		this.clickedrate = clickedrate;
	}

	public String getOpenedrate() {
		return openedrate;
	}

	public void setOpenedrate(String openedrate) {
		this.openedrate = openedrate;
	}

	public Graph getSent() {
		return sent;
	}

	public void setSent(Graph sent) {
		this.sent = sent;
	}

	public Graph getDelivered() {
		return delivered;
	}

	public void setDelivered(Graph delivered) {
		this.delivered = delivered;
	}

	public Graph getOpened() {
		return opened;
	}

	public void setOpened(Graph opened) {
		this.opened = opened;
	}

	public Graph getClicked() {
		return clicked;
	}

	public void setClicked(Graph clicked) {
		this.clicked = clicked;
	}
}
