package tw.idv.kalenet.haltcloud;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangkale on 15/1/11.
 */
public class HttpAPI {
    private MyHttpClient webclent =new MyHttpClient();
    private String webroot="http://pro.kalenet.idv.tw/c-core/";
    private String LoadPath="loading.php",LoadData="load_data.php";
    private String ISLOAD="islod.php";
    private String _num,_name;
    private boolean _isload=false;
    HttpAPI(){

    }
    public boolean isload(){
        String re=webclent.executeRequest(webroot+ISLOAD,new ArrayList<NameValuePair>());
        if(re.equals("1"))
            _isload=true;
        else _isload=false;
        return _isload;
    }
    public String GetNum(){
        return _num;
    }
    public String Getname(){
        return _name;
    }
    public void Load(String name,String pass){
        List<NameValuePair> pairs=new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("name",name));
        pairs.add(new BasicNameValuePair("pass",pass));
        String re= webclent.executeRequest(webroot+LoadPath,pairs);
        if(re.equals("OK")){
            _isload=true;
            re=webclent.executeRequest(webroot+LoadData,new ArrayList<NameValuePair>());
            String[] data=re.split(",");
            _num=data[0];
            _name=data[1];
        }
        else {
            _isload=false;
        }
    }

}
