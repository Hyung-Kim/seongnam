package songjong.com.seongnamgiftcard.FieldClass;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class House implements ClusterItem {
    private final LatLng mPosition;
    private final String mName;
    private final String mAddress;
    private final String mNumber;
    public House(double lat, double lng, String mName, String mAddress, String mNumber) {
        mPosition = new LatLng(lat, lng);
        this.mName = mName;
        this.mAddress = mAddress;
        this.mNumber = mNumber;
    }

    public LatLng getPosition() {
        return mPosition;
    }
    @Override
    public String getTitle() {return mName;}
    @Override
    public String getSnippet() {
        return mAddress;
    }
    public String getNumber() {return mNumber;}
}
