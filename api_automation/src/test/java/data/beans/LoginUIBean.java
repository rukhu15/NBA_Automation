package data.beans;

import framework.utility.dataParam.BaseBean;

public class LoginUIBean implements BaseBean{

	public String testDescription=null;
	public String username =null;
	public String password=null;
	
	
	@Override
	public String toString() {
		return String.format("testDescription: %s|username: %s|password:  " ,testDescription, username,password);
	}
}

