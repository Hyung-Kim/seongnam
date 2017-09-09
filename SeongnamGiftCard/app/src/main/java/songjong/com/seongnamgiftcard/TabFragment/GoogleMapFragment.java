package songjong.com.seongnamgiftcard.TabFragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import songjong.com.seongnamgiftcard.Activity.MainActivity;
import songjong.com.seongnamgiftcard.FieldClass.Company;
import songjong.com.seongnamgiftcard.FieldClass.CustomClusterRenderer;
import songjong.com.seongnamgiftcard.FieldClass.House;
import songjong.com.seongnamgiftcard.R;

import static songjong.com.seongnamgiftcard.Activity.MainActivity.appAddress;

/**
 * Created by dongwook on 2017. 8. 9..
 */

    public class GoogleMapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener{

    private static final LatLng DEFAULT_LOCATION = new LatLng(37.56, 126.97);
    private static final String TAG = "GoogleMap";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2002;
    private static final int UPDATE_INTERVAL_MS = 15000;
    private static final int FASTEST_UPDATE_INTERVAL_MS = 15000;

    private GoogleMap googleMap = null;
    private MapView mapView = null;
    private GoogleApiClient googleApiClient = null;
    private Marker currentMarker = null;

    private final static int MAXENTRIES = 5;
    private String[] LikelyPlaceNames = null;
    private String[] LikelyAddresses = null;
    private String[] LikelyAttributions = null;
    private LatLng[] LikelyLatLngs = null;

    private static int searchCnt=0;
    private static View layout;
    public static Location curLocation;
    private ClusterManager<House> mClusterManager;
    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {
        Log.i(TAG, "CurrentLocation");

        if ( currentMarker != null ) currentMarker.remove();

        if (location != null) {
            //현재 위치
            curLocation = location;
            LatLng currentLocation = new LatLng( location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(currentLocation);
            markerOptions.title(markerTitle);
            markerOptions.snippet(markerSnippet);
            markerOptions.draggable(true);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            currentMarker = this.googleMap.addMarker(markerOptions);
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            mClusterManager = new ClusterManager<House>(getActivity(), googleMap);
            googleMap.setOnCameraIdleListener(mClusterManager);
            googleMap.setOnMarkerClickListener(mClusterManager);
            Company temp;
            for(int i=0; i<FoodTabFragment.companyList.size();i++)
            {
                temp = FoodTabFragment.companyList.get(i);
                House offsetItem = new House(temp.getCompanyLatitude(), temp.getCompanyLongitude());
                mClusterManager.addItem(offsetItem);
            }
            final CustomClusterRenderer renderer = new CustomClusterRenderer(getActivity(), googleMap, mClusterManager);
            mClusterManager.setRenderer(renderer);
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                public boolean onMarkerClick(Marker marker) {
                    String text = "[마커 클릭 이벤트] latitude ="
                            + marker.getTitle() + ", longitude ="
                            + marker.getPosition().longitude;
                    Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
                    return false;
                }
            });
            return;
        }
        Log.d(TAG,"location==null");
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(DEFAULT_LOCATION);
        markerOptions.title(markerTitle);
        markerOptions.snippet(markerSnippet);
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        currentMarker = this.googleMap.addMarker(markerOptions);

        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(DEFAULT_LOCATION));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() /* FragmentActivity */,
                            this /* OnConnectionFailedListener */)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        googleApiClient.connect();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(layout != null){
            ViewGroup viewGroupParent = (ViewGroup) layout.getParent();
            if (viewGroupParent != null)
                viewGroupParent.removeView(layout);
        }
        try{
            layout = inflater.inflate(R.layout.map_fragment, container, false);
        }
        catch(Exception e){}
        mapView = (MapView)layout.findViewById(R.id.map);
        mapView.getMapAsync(this);

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
             //   Log.d(TAG,"해당위치로 이동");
                Location location = new Location("");
                location.setLatitude(place.getLatLng().latitude);
                location.setLongitude(place.getLatLng().longitude);

                setCurrentLocation(location, place.getName().toString(), place.getAddress().toString());
                appAddress=place.getAddress().toString();
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.updateCurrentPlaceText(appAddress);
            }
            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        return layout;
    }
    //Duplicated ID 해결 코드
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
   //     Log.d(TAG,"viewCreated");
        searchCnt=0;
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentByTag("mapFragment");
        if (mapFragment == null) {
            mapFragment = new SupportMapFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.mapFragmentContainer, mapFragment, "mapFragment");
            ft.commit();
            fm.executePendingTransactions();
        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();

        if (googleApiClient != null && googleApiClient.isConnected()){
            googleApiClient.disconnect();
        //    Log.d("TAG","api is null");
        }
        Log.d(TAG,"onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if ( googleApiClient != null)
            googleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();

        if ( googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
        //지도 fragment 두번 들어왔을경우 터지는 에러 코드 수정
        googleApiClient.stopAutoManage(getActivity());
        googleApiClient.disconnect();
      //  Log.d(TAG,"pause");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();

        if (googleApiClient != null ) {
            googleApiClient.unregisterConnectionCallbacks(this);
            googleApiClient.unregisterConnectionFailedListener(this);

            if (googleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
                googleApiClient.disconnect();
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //액티비티가 처음 생성될 때 실행되는 함수
        MapsInitializer.initialize(getActivity().getApplicationContext());

        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // OnMapReadyCallback implements 해야 mapView.getMapAsync(this); 사용가능. this 가 OnMapReadyCallback
      //  Log.i(TAG, "OnMapREadey");
        this.googleMap = googleMap;

        //런타임 퍼미션 요청 대화상자나 GPS 활성 요청 대화상자 보이기전에 지도의 초기위치를 서울로 이동
        setCurrentLocation(null, "위치정보 가져올 수 없음", "위치 퍼미션과 GPS 활성 여부 확인");

        //나침반이 나타나도록 설정
        googleMap.getUiSettings().setCompassEnabled(true);
        // 매끄럽게 이동함
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //  API 23 이상이면 런타임 퍼미션 처리 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 사용권한체크
            int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

            if ( hasFineLocationPermission == PackageManager.PERMISSION_DENIED) {
                //사용권한이 없을경우
                //권한 재요청
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            } else {
                //사용권한이 있는경우
                if ( googleApiClient == null) {
                    buildGoogleApiClient();
                }

                if ( ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {
                    googleMap.setMyLocationEnabled(true);
                }
            }
        } else {

            if ( googleApiClient == null) {
                buildGoogleApiClient();
            }

            googleMap.setMyLocationEnabled(true);
        }
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(), this)
                .build();
        googleApiClient.connect();
    }
    @Override
    public void onConnected(@Nullable Bundle bundle) {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL_MS);
        locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ( ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                LocationServices.FusedLocationApi
                        .requestLocationUpdates(googleApiClient, locationRequest, this);
            }
        } else {
            LocationServices.FusedLocationApi
                    .requestLocationUpdates(googleApiClient, locationRequest, this);

            this.googleMap.getUiSettings().setCompassEnabled(true);
            this.googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        if ( cause ==  CAUSE_NETWORK_LOST )
            Log.e(TAG, "onConnectionSuspended(): Google Play services " +
                    "connection lost.  Cause: network lost.");
        else if (cause == CAUSE_SERVICE_DISCONNECTED )
            Log.e(TAG,"onConnectionSuspended():  Google Play services " +
                    "connection lost.  Cause: service disconnected");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Location location = new Location("");
        location.setLatitude(DEFAULT_LOCATION.latitude);
        location.setLongitude((DEFAULT_LOCATION.longitude));

        setCurrentLocation(location, "위치정보 가져올 수 없음",
                "위치 퍼미션과 GPS활성 여부 확인");
    }

    @Override
    public void onLocationChanged(Location location) {
      //  Log.i(TAG, "onLocationChanged call..");
       // Log.d(TAG,"searcn CNT :"+searchCnt);
         if(searchCnt==0){
             searchCurrentPlaces();
             searchCnt++;
         }

    }

    public void searchCurrentPlaces() {
        @SuppressWarnings("MissingPermission")

        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi
                .getCurrentPlace(googleApiClient, null);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>(){
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
              //  Log.i(TAG, "CurrentPlaces");
                int i = 0;
                LikelyPlaceNames = new String[MAXENTRIES];
                LikelyAddresses = new String[MAXENTRIES];
                LikelyAttributions = new String[MAXENTRIES];
                LikelyLatLngs = new LatLng[MAXENTRIES];
         //       Log.i(TAG, "1");
                Log.i(TAG, "" + likelyPlaces);
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                    Log.i(TAG, String.format("Place '%s' has likelihood: %g", placeLikelihood.getPlace().getName(),
                            placeLikelihood.getLikelihood()));
                    LikelyPlaceNames[i] = (String) placeLikelihood.getPlace().getName();
                    LikelyAddresses[i] = (String) placeLikelihood.getPlace().getAddress();
                    LikelyAttributions[i] = (String) placeLikelihood.getPlace().getAttributions();
                    LikelyLatLngs[i] = placeLikelihood.getPlace().getLatLng();
             //       Log.i(TAG, "2");
                    i++;
                    if (i > MAXENTRIES - 1) {
                        break;
                    }
                }
                likelyPlaces.release();

                Location location = new Location("");
                location.setLatitude(LikelyLatLngs[0].latitude);
                location.setLongitude(LikelyLatLngs[0].longitude);
                setCurrentLocation(location, LikelyPlaceNames[0], LikelyAddresses[0]);

            }
        });

    }
}
