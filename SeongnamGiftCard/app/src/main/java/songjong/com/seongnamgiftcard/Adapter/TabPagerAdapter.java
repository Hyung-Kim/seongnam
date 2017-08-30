package songjong.com.seongnamgiftcard.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import songjong.com.seongnamgiftcard.Activity.MainActivity;
import songjong.com.seongnamgiftcard.TabFragment.EtcTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.FoodTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.GoogleMapFragment;
import songjong.com.seongnamgiftcard.TabFragment.SaleTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.ServiceTabFragment;

/**
 * Created by dongwook on 2017. 8. 7..
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                if(MainActivity.fragmentFlagArr[position]==0){
                    FoodTabFragment foodTabFragment = new FoodTabFragment();
                    return foodTabFragment;
                }
                else if(MainActivity.fragmentFlagArr[position]==1){
                    GoogleMapFragment googleMapFragment = new GoogleMapFragment();
                    return googleMapFragment;
                }
            case 1:
                if(MainActivity.fragmentFlagArr[position]==0) {
                    ServiceTabFragment serviceTabFragment = new ServiceTabFragment();
                    return serviceTabFragment;
                }
                else if(MainActivity.fragmentFlagArr[position]==1){
                    GoogleMapFragment googleMapFragment = new GoogleMapFragment();
                    return googleMapFragment;
                }
            case 2:
                if(MainActivity.fragmentFlagArr[position]==0) {
                    SaleTabFragment saleTabFragment = new SaleTabFragment();
                    return saleTabFragment;
                }
                else if(MainActivity.fragmentFlagArr[position]==1) {
                    GoogleMapFragment googleMapFragment = new GoogleMapFragment();
                    return googleMapFragment;
                }
            case 3:
                if(MainActivity.fragmentFlagArr[position]==0) {
                    EtcTabFragment etcTabFragment = new EtcTabFragment();
                    return etcTabFragment;
                }
                else if(MainActivity.fragmentFlagArr[position]==1) {
                    GoogleMapFragment googleMapFragment = new GoogleMapFragment();
                    return googleMapFragment;
                }
            default:
                return null;
        }
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

