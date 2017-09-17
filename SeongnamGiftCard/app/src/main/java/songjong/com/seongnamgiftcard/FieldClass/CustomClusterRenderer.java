package songjong.com.seongnamgiftcard.FieldClass;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import songjong.com.seongnamgiftcard.TabFragment.GoogleMapFragment;

/**
 * Created by taehyung on 2017-09-09.
 */

public class CustomClusterRenderer extends DefaultClusterRenderer<House> {
    private final Context mContext;
    public CustomClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<House> clusterManager) {
        super(context, map, clusterManager);
        mContext = context;
    }
    @Override
    protected void onBeforeClusterItemRendered(House item, MarkerOptions markerOptions) {
        final BitmapDescriptor markerDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
        markerOptions.icon(markerDescriptor);
        markerOptions.snippet(item.getSnippet());
        markerOptions.position(item.getPosition());
        markerOptions.title(item.getTitle());
    }
}
