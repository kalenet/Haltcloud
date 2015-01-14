package tw.idv.kalenet.haltcloud;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class Main extends ActionBarActivity {
    TextView lb_name,lb_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lb_name=(TextView)findViewById(R.id.tv_name);
        lb_num=(TextView)findViewById(R.id.tv_num);

        String name=login.loadweb.Getname();
        String num=login.loadweb.GetNum();
        lb_name.setText(getText(R.string.names).toString()+name);
        lb_num.setText(getText(R.string.nums).toString()+num);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
