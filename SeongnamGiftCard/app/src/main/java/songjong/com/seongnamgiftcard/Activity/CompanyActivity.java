package songjong.com.seongnamgiftcard.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import songjong.com.seongnamgiftcard.Adapter.CompanyPagerAdapter;
import songjong.com.seongnamgiftcard.Adapter.RecyclerViewAdapter;
import songjong.com.seongnamgiftcard.FieldClass.Company;
import songjong.com.seongnamgiftcard.R;
import songjong.com.seongnamgiftcard.TabFragment.EtcTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.FoodTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.SaleTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.ServiceTabFragment;


public class CompanyActivity extends AppCompatActivity{

    private CompanyPagerAdapter pagerAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        //툴바 초기화
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_information);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView companyNameTextView = (TextView)findViewById(R.id.actionbar_title_company);
        companyNameTextView.setText("업체명");

        //TabLayout 초기화
        tabLayout = (TabLayout) findViewById(R.id.tabs_information);
        tabLayout.addTab(tabLayout.newTab().setText("메뉴"));
        tabLayout.addTab(tabLayout.newTab().setText("업체정보"));
        tabLayout.addTab(tabLayout.newTab().setText("리뷰"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //업체정보 설정
        Company company = null;
        switch(MainActivity.currentTab){
            case 0:
                company= FoodTabFragment.companyList.get(RecyclerViewAdapter.curCompanyyPosition);
                break;
            case 1:
                company= ServiceTabFragment.companyList.get(RecyclerViewAdapter.curCompanyyPosition);
                break;
            case 2:
                company= SaleTabFragment.companyList.get(RecyclerViewAdapter.curCompanyyPosition);
                break;
            case 3:
                company= EtcTabFragment.companyList.get(RecyclerViewAdapter.curCompanyyPosition);
                break;
            case 4:
                company= SearchActivity.companyList.get(RecyclerViewAdapter.curCompanyyPosition);
                break;
        }
        companyNameTextView.setText(company.getCompanyName());

        //viewPager 초기화
        mViewPager = (ViewPager) findViewById(R.id.pager_information);

        // PageAdapter 초기화 부분
        pagerAdapter = new CompanyPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
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

