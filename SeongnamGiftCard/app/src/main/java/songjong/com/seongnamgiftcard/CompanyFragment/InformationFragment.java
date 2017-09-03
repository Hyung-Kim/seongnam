package songjong.com.seongnamgiftcard.CompanyFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import songjong.com.seongnamgiftcard.Adapter.RecyclerViewAdapter;
import songjong.com.seongnamgiftcard.Adapter.TabPagerAdapter;
import songjong.com.seongnamgiftcard.FieldClass.Company;
import songjong.com.seongnamgiftcard.R;
import songjong.com.seongnamgiftcard.TabFragment.FoodTabFragment;

public class InformationFragment extends Fragment
        implements OnMapReadyCallback {
    public MapView mMapView;
    public GoogleMap mGoogleMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.company_fragment_information, container, false);

        //업체상세정보 설정
        TextView textViewCompanyAddress  = (TextView)v.findViewById(R.id.textview_address_value);;
        TextView textViewCompanyNumber = (TextView)v.findViewById(R.id.textview_tel_value);

        Company company= FoodTabFragment.companyList.get(RecyclerViewAdapter.curCompanyyPosition);
        textViewCompanyAddress.setText(company.getCompanyAddress());
        textViewCompanyNumber.setText(company.getCompanyNumber());

        mMapView = (MapView) v.findViewById(R.id.mapInformation);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this); //this is important

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

        //마커 추가 코드
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(37.56, 126.97)));
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(37.56, 126.98)));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.56, 126.97), 15));
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
}
