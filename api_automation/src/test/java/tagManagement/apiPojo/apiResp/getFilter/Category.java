package tagManagement.apiPojo.apiResp.getFilter;

import java.util.List;

public class Category {
	private String title;
	private List<Children> children;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Children> getChildren() {
		return children;
	}

	public void setChildren(List<Children> children) {
		this.children = children;
	}
}
