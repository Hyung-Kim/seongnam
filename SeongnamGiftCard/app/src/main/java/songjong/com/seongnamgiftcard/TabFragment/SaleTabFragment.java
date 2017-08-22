package songjong.com.seongnamgiftcard.TabFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import songjong.com.seongnamgiftcard.R;

/**
 * Created by dongwook on 2017. 8. 7..
 */

public class SaleTabFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_fragment_sale, container, false);
    }
}
