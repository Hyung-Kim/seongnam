package songjong.com.seongnamgiftcard.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
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
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import songjong.com.seongnamgiftcard.Adapter.TabPagerAdapter;
import songjong.com.seongnamgiftcard.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionMenu fam;
    private FloatingActionButton fabMap, fabCurrentPosition;
    private TabPagerAdapter pagerAdapter;
    private String TAG = "MainActivity";
    private LocationManager locationManager;
    private String provider;
    public static Double latitude=0.0;
    public static Double longitude=0.0;
    public static int fragmentFlagArr[]={0,0,0,0};
    public static int addressFlag=0;
    public static String uuid=null;
    private static int currentTab;
    public static String appAddress="현재 위치 확인 중";
    private LocationManager manager;
    private GPSListener gpsListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkInfo mNetworkState = getNetworkInfo();
        String Networktext = null;
        if(mNetworkState == null || !mNetworkState.isConnected()){
            Networktext ="네트워크가 연결되지 않았습니다.";
            Toast.makeText(MainActivity.this, Networktext, Toast.LENGTH_LONG).show();
            finish();
        }

        //앱 자체의 위치 권한 설정 코드
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }

        startLocationService();
        uuid = getDeviceUUID(MainActivity.this);

        //안드로이드 위치 서비스 설정
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            onGPSProviderDisabled();
        }

        provider = locationManager.getBestProvider(new Criteria(), true);    // 최고의 GPS 찾기


        //Toolbar 초기화 부분
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView tv = (TextView) findViewById(R.id.actionbar_title_main);
        tv.setText(appAddress);

        //TabLayout 초기화 부분
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("음식"));
        tabLayout.addTab(tabLayout.newTab().setText("서비스"));
        tabLayout.addTab(tabLayout.newTab().setText("도/소매"));
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
                currentTab=tab.getPosition();
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

                } else {

                }
            }
        });

        fabMap.setOnClickListener(onButtonClick());
        fabCurrentPosition.setOnClickListener(onButtonClick());

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //Navigation View 이벤트 처리
        int id = item.getItemId();

        if (id == R.id.giftcard_item) {
            Intent intent=new Intent(MainActivity.this,GiftCardActivity.class);
            startActivity(intent);
        }  else if (id == R.id.developer_item) {
            Intent intent=new Intent(MainActivity.this,DeveloperActivity.class);
            startActivity(intent);
        } else if (id == R.id.notice_item) {
            Intent intent=new Intent(MainActivity.this,NoticeActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private NetworkInfo getNetworkInfo(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }
    private View.OnClickListener onButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == fabMap) {
                    fragmentFlagArr[currentTab] = 1;
                    addressFlag =1;
                    pagerAdapter.notifyDataSetChanged();
                    fragmentFlagArr[currentTab] = 0;

                } else if (view == fabCurrentPosition) {
                    fragmentFlagArr[currentTab] = 0;
                    addressFlag =0;
                    startLocationService();
                    pagerAdapter.notifyDataSetChanged();
                }
                fam.close(true);
            }
        };
    }

    public void onGPSProviderDisabled() {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage("GPS가 OFF되어 있습니다.\n '위치 서비스에서 'Google 위치 서비스'체크를 해주세요")
                .setPositiveButton("설정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("취소", null).show();

    }

    public String getAddress(double lat, double lng) {
        String nowAddress = "현재 위치를 확인 할 수 없습니다.";
        Geocoder geocoder = new Geocoder(this,Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
                //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
                address = geocoder.getFromLocation(lat, lng, 1);

                if (address != null && address.size() > 0) {
                    // 주소 받아오기
                    String currentLocationAddress = address.get(0).getAddressLine(0).toString();
                    nowAddress = currentLocationAddress;
                    appAddress=nowAddress;

                }
            }

        } catch (IOException e) {

            e.printStackTrace();
        }
        return nowAddress;
    }

    private void startLocationService() {
        // get manager instance
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // set listener
        gpsListener = new GPSListener();
        long minTime = 10000;
        float minDistance = 0;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, gpsListener);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (Build.VERSION.SDK_INT >= 23) {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                if(grantResults[0]==0){
                    startLocationService();
                }
            }
        }
    }

    public void updateCurrentPlaceText(String text){
        TextView textView = (TextView)findViewById(R.id.actionbar_title_main);
        textView.setText(text);
    }

    public static String getDeviceUUID(final Context context) {
        // preference에서 저장된 것이 있는지 확인해봄
        final SharedPreferences prefs = context.getSharedPreferences("UUIDTEST", MODE_PRIVATE);
        final String id = prefs.getString("UUID", null);

        UUID uuid = null;
        if (id != null) {
            uuid = UUID.fromString(id);
        } else {
            final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            try {
                // 예전에 아래와 같은 고정값이 리턴되는 문제가 있었음
                if (!"9774d56d682e549c".equals(androidId)) {
                    uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                } else {
                    final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                    uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            final SharedPreferences.Editor editor = prefs.edit();
            editor.putString("UUID", uuid.toString());
            editor.commit();
        }

        return uuid.toString();
    }

    private class GPSListener implements LocationListener
    {
        public void onLocationChanged(Location location) {
            //capture location data sent by current provider
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            if(addressFlag == 0) {
                getAddress(latitude, longitude);
                updateCurrentPlaceText(appAddress);
                manager.removeUpdates(gpsListener);
                if(TabPagerAdapter.foodTabFragment.companyList.isEmpty() && TabPagerAdapter.foodTabFragment != null)
                    TabPagerAdapter.foodTabFragment.loadData();
            }
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) { }
        public void onStatusChanged(String provider, int status, Bundle extras) { }
        }
    }


