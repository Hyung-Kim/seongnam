package songjong.com.seongnamgiftcard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import songjong.com.seongnamgiftcard.TabFragment.GoogleMapFragment;
import songjong.com.seongnamgiftcard.TabFragment.TabPagerAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionMenu fam;
    private FloatingActionButton fabMap,fabCurrentPosition,fabSearch;
    private TabPagerAdapter pagerAdapter;
    public static int fragmentFlag=0;
    private GoogleMapFragment googleMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //안드로이드 위치 서비스 설정
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            onGPSProviderDisabled();
        }

        //앱 자체의 위치 권한 설정 코드
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    0 );
        }
        //Toolbar 초기화 부분
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TabLayout 초기화 부분
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("음식"));
        tabLayout.addTab(tabLayout.newTab().setText("슈퍼"));
        tabLayout.addTab(tabLayout.newTab().setText("정육"));
        tabLayout.addTab(tabLayout.newTab().setText("잡화"));
        tabLayout.addTab(tabLayout.newTab().setText("미용"));
        tabLayout.addTab(tabLayout.newTab().setText("약국"));
        tabLayout.addTab(tabLayout.newTab().setText("꽃집"));
        tabLayout.addTab(tabLayout.newTab().setText("의류"));
        tabLayout.addTab(tabLayout.newTab().setText("기타"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //View Pager 초기화 부분
        viewPager = (ViewPager) findViewById(R.id.pager);

        // TapPageAdapter 초기화 부분
        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        fabMap = (FloatingActionButton) findViewById(R.id.fabMapId);
        fabCurrentPosition = (FloatingActionButton) findViewById(R.id.fabCurrentPositionId);
        fam = (FloatingActionMenu) findViewById(R.id.fab_menu);

        fam.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened) {
                    showToast("Menu is opened");
                } else {
                    showToast("Menu is closed");
                }
            }
        });

        fabMap.setOnClickListener(onButtonClick());
        fabCurrentPosition.setOnClickListener(onButtonClick());

        //요부분에서 에러나서 일단 막아놓음 없어도 코드 안되는 부분 없어서 일단 주석 처리
//        fam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (fam.isOpened()) {
//                    fam.close(true);
//                }
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //Navigation View 이벤트 처리
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private View.OnClickListener onButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == fabMap) {
                    showToast("지도 화면으로 이동");
                    fragmentFlag=1;
                    pagerAdapter.notifyDataSetChanged();
                    fragmentFlag=0;

                } else if (view == fabCurrentPosition) {
                    showToast("현재 위치 화면으로 이동");
                    fragmentFlag=0;
                    pagerAdapter.notifyDataSetChanged();
                }
                fam.close(true);
            }
        };
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

//    private void searchDialogShow(){
//        final EditText editText = new EditText(this);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("상세 검색");
//        builder.setView(editText);
//        editText.setHint("ㅇㅇ동,ㅇㅇ시장,ㅇㅇ역,업체명");
//        builder.setPositiveButton("검색",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getApplicationContext(),editText.getText().toString() ,Toast.LENGTH_LONG).show();
//                        googleMapFragment = new GoogleMapFragment();
//
//                    }
//                });
//        builder.setNegativeButton("취소",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//        builder.show();
//    }
    public void onGPSProviderDisabled(){
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("GPS가 OFF되어 있습니다.\n '위치 서비스에서 'Google 위치 서비스'체크를 해주세요")

                .setPositiveButton("설정",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("취소",null).show();

    }
}
