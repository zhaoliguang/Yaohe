package runze.yaohe.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import runze.yaohe.constant.MyGlobal;

public class PostAndGetDateUtil {
	private static String SESSIONID=null;
	static DefaultHttpClient httpClient;
	
	public static String login(String url){
		System.out.println(url);
		String response=null;
		HttpPost httpPost=new HttpPost(url);
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		DefaultHttpClient httpClient=new DefaultHttpClient();
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			HttpResponse httpResponse=httpClient.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode()==200){
				response=EntityUtils.toString(httpResponse.getEntity());
				CookieStore cookieStore=httpClient.getCookieStore();
				List<Cookie> cookies=cookieStore.getCookies();
				for(Cookie cookie:cookies){
					SESSIONID = cookie.getValue();
				}
			}
		} catch (Exception e) {
			System.out.println("error csadfsad");
			e.printStackTrace();
		}
		return response;
	}
	
	public static String postAndGetDaet(String url){
		String response=null;
		if(httpClient!=null){
			System.out.println("shutdown");
			httpClient.getConnectionManager().shutdown();
		}
		System.out.println(url);
		
		try{
			HttpPost httpPost=new HttpPost(url);
			httpPost.setHeader("Cookie", "JSESSIONID=" + SESSIONID);
			httpClient=new DefaultHttpClient();
			
			HttpResponse httpResponse=httpClient.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode()==200){
				response=EntityUtils.toString(httpResponse.getEntity());
			}
			System.out.println("response:"+response);
		}catch (Exception e) {
			MyGlobal.CONNECT_ERROR_TRUCK_LIST=true;
			System.out.println("error ");
			response="connect_error";
			e.printStackTrace();
		}
		return response;
	}

	public static String register(String url) {
		String response=null;
		System.out.println(url);
		try{
			HttpPost httpPost=new HttpPost(url);
			DefaultHttpClient httpClient=new DefaultHttpClient();
			HttpResponse httpResponse=httpClient.execute(httpPost);
			if(httpResponse.getStatusLine().getStatusCode()==200){
				response=EntityUtils.toString(httpResponse.getEntity());
			}
		}catch (Exception e) {
			System.out.println("error ");
			e.printStackTrace();
		}
		return response;
	}

}
