package tagManagement.apiPojo.apiReq.createTag;

import java.util.List;

public class CreateTagReq {

	private String tag_id;
	private String tag_name;
	private String tag_desc;
	private String is_deleted;
	private List<BrandDetails> brand_details;
	private String permission;

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public List<BrandDetails> getBrand_details() {
		return brand_details;
	}

	public void setBrand_details(List<BrandDetails> brand_details) {
		this.brand_details = brand_details;
	}

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public String getTag_desc() {
		return tag_desc;
	}

	public void setTag_desc(String tag_desc) {
		this.tag_desc = tag_desc;
	}

	public String getIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(String is_deleted) {
		this.is_deleted = is_deleted;
	}
}
