package tw.idv.kalenet.haltcloud;

import android.widget.EditText;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class MyHttpClient  {
    private DefaultHttpClient httpClient;
    private HttpPost httpPost;
    private HttpEntity httpEntity;
    private HttpResponse httpResponse;
    public static String PHPSESSID = null;
    public MyHttpClient() {

    }

    public String executeRequest(String path, List<NameValuePair> params) {
        String ret = "none";
        try {
            this.httpPost = new HttpPost(path);
            httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            httpPost.setEntity(httpEntity);

            if(null != PHPSESSID){
                httpPost.setHeader("Cookie", "PHPSESSID=" + PHPSESSID);
            }
            httpClient = new DefaultHttpClient();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = httpResponse.getEntity();
                ret = EntityUtils.toString(entity);
                CookieStore mCookieStore = httpClient.getCookieStore();
                List<Cookie> cookies = mCookieStore.getCookies();
                for (int i = 0; i < cookies.size(); i++) {

                    if ("PHPSESSID".equals(cookies.get(i).getName())) {
                        PHPSESSID = cookies.get(i).getValue();
                        break;
                    }

                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return ret;
    }
}