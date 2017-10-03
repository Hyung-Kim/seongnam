package songjong.com.seongnamgiftcard.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dongwook on 2017. 9. 24..
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Thread.sleep(3000);
            finish();
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

    }
}