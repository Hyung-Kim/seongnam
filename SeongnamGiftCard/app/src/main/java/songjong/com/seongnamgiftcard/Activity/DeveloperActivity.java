package songjong.com.seongnamgiftcard.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import songjong.com.seongnamgiftcard.R;

/**
 * Created by dongwook on 2017. 8. 25..
 */

public class DeveloperActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        //툴바 초기화
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_developer);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView tv = (TextView) findViewById(R.id.actionbar_title_developer);
        tv.setText("개발자 정보");
        TextView card_tv1 = (TextView) findViewById(R.id.dv_card_view_text);
        card_tv1.setText("문의 사항/문제사항은 아래 메일로 문의 바랍니다.\n\n부족한 점이 많더라도 보내주시는 피드백들을 통하여 더 좋은 어플을 만들기 위해 노력하겠습니다. ^^");
        final TextView card_tv2 = (TextView) findViewById(R.id.dv_card_view_text2_mail);

        card_tv2.setText("xodaktmdng@naver.com");
        CardView cardView2 = (CardView)findViewById(R.id.developer_card_view_hyung);
        CardView cardView3 = (CardView)findViewById(R.id.developer_card_view_wook);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("mailto:"+card_tv2.getText().toString());
                Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                startActivity(intent);
            }
        });

       final TextView card_tv3 = (TextView) findViewById(R.id.dv_card_view_text3_mail);
        card_tv3.setText("ghdehdvn12@naver.com");
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("mailto:"+card_tv3.getText().toString());
                Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
                startActivity(intent);
            }
        });

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
