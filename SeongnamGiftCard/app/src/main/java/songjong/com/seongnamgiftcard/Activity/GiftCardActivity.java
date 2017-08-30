package songjong.com.seongnamgiftcard.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import songjong.com.seongnamgiftcard.R;

/**
 * Created by dongwook on 2017. 8. 30..
 */

public class GiftCardActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftcard);

        //툴바 초기화
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_giftcard);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView tv = (TextView) findViewById(R.id.actionbar_title_giftcard);
        tv.setText("성남 사랑 상품권 소개");

        TextView giftcard_tv = (TextView) findViewById(R.id.gift_card_view_text);
        giftcard_tv.setText("「성남사랑 상품권」안내\n" +
                "\n" +
                "성남사랑 상품권 이용 안내\n" +
                "「성남사랑 상품권」은 어려움을 겪고있는 전통시장과 영세 상점가를 살리고 「성남사랑 상품권」을 이용하는 시민들에게 상품권 가액 액면금액의 6%(설·추석·평상시 동일)를 인센티브로 할인판매하여 성남 지역경제를 활성화 하고자 하는 성남시의 특색사업입니다.\n\n\n" +
                "상품권의 종류\n" +
                "「성남사랑 상품권」은 5,000원권과 10,000원권 2종류가 있습니다.상품권 구매한도\n" +
                "개인 : 1일 10만원 , 월 50만원\n" +
                "단체 : 월 100만원\n\n\n");
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
