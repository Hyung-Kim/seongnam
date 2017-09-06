package songjong.com.seongnamgiftcard.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import songjong.com.seongnamgiftcard.R;

/**
 * Created by dongwook on 2017. 9. 6..
 */

public class InconvenienceActivity extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inconvenience);

        //툴바 초기화
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_inconvenience);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView tv = (TextView) findViewById(R.id.actionbar_title_inconvenience);
        tv.setText("불편사항");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
