package tw.idv.kalenet.haltcloud;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class login extends ActionBarActivity {
    public static HttpAPI loadweb=new HttpAPI();
    EditText tb_id,tb_pass;
    Button bt_login;
    CheckBox cb_auto;
    SharedPreferences userdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userdata=getSharedPreferences("userdata",MODE_PRIVATE);
        boolean autolog=userdata.getBoolean("auto",false);

        setContentView(R.layout.activity_login);
        tb_id=(EditText)findViewById(R.id.tb_id);
        tb_pass=(EditText)findViewById(R.id.tb_pass);
        bt_login=(Button)findViewById(R.id.bt_login);
        cb_auto=(CheckBox)findViewById(R.id.cb_auto);
        bt_login.setOnClickListener(login_check);
        if(autolog){
            tb_id.setText(userdata.getString("name",""));
            tb_pass.setText(userdata.getString("pass",""));
            cb_auto.setChecked(true);
            login_check.onClick(bt_login);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private View.OnClickListener login_check=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tb_id.setText(tb_id.getText().toString().toUpperCase());
            if(!(tb_id.getText().toString().equals("")&&tb_pass.getText().toString().equals(""))){
                Thread t=new Thread(new sendloaddatat(tb_id.getText().toString(),tb_pass.getText().toString()));
                t.start();


            }else {
                Toast.makeText(login.this,"請輸入帳號密碼",Toast.LENGTH_LONG).show();
            }
        }
    };
    class sendloaddatat implements Runnable{
        String user,pass;
        sendloaddatat(String uuser,String Upass){

            this.user=uuser;
            this.pass=Upass;
        }
        @Override
        public void run(){
            loadweb.Load(user,pass);

            if ((loadweb.isload())){
                Message mg=new Message();
                mg.what=1;
                mhand.sendMessage(mg);
            }else {
                Message mr=new Message();
                mr.what=2;
                mhand.sendMessage(mr);
            }


        }
    }
    Handler mhand=new Handler(){
        @Override
        public void handleMessage(Message msg){
            if(msg.what==1){
                Toast.makeText(login.this,"登入成功,"+loadweb.Getname()+"["+loadweb.GetNum()+"]",
                        Toast.LENGTH_LONG).show();
                if(cb_auto.isChecked()){
                    userdata.edit().putString("name",tb_id.getText().toString())
                            .putString("pass",tb_pass.getText().toString())
                            .putBoolean("auto",true)
                            .commit();

                }else{
                    userdata.edit().putBoolean("auto",false).commit();
                }
                Intent logok=new Intent(login.this,Main.class);
                startActivity(logok);

            }
            if(msg.what==2){
                Toast.makeText(login.this,"登入失敗",Toast.LENGTH_LONG).show();
            }

        }
    };
}
