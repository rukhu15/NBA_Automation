package test;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Iterator;

/**
 * @author Prateek Sethi
 */
public class Check {

    static String str= "{\"category\":{\"Personal\":[\"Face to Face Call\",\"RT Email\"],\"Non-Personal\":[\"HO Email\"]}}";

    public static void main(String[] args) {
        JSONObject jsObj = new JSONObject(str);
        JSONObject categoryObj=jsObj.getJSONObject("category");
        Iterator<?> keys = categoryObj.keys();
        while( keys.hasNext() ) {
            String key = (String)keys.next();
            System.out.println(key);
            JSONArray channelArr=categoryObj.getJSONArray(key);
            for(int index =0 ; index<channelArr.length();index++){
               String channel= channelArr.getString(index);
               System.out.println(channel);
            }
        }
    }
}
