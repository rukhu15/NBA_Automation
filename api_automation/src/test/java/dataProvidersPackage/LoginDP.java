package dataProvidersPackage;

import java.util.Iterator;
import org.testng.annotations.DataProvider;
import data.beans.LoginUIBean;
import framework.utility.dataParam.BaseBean;
import framework.utility.dataParam.Data;


public class LoginDP extends LoginUIBean implements BaseBean{
	
	@DataProvider(name="login")
	public static Iterator<Object[]> login(){
		return Data.listOf(LoginUIBean.class).from("login");
	}

}
