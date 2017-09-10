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

    //fragment
    public static FoodTabFragment foodTabFragment = null;
    public static ServiceTabFragment serviceTabFragment = null;
    public static SaleTabFragment saleTabFragment = null;
    public static EtcTabFragment etcTabFragment = null;
    private int tabCount;
    public static int cur_position = 0;
    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                cur_position = 0;
                if(MainActivity.fragmentFlagArr[position]==0){
                    foodTabFragment = new FoodTabFragment();
                    return foodTabFragment;
                }
                else if(MainActivity.fragmentFlagArr[position]==1){
                    GoogleMapFragment googleMapFragment = new GoogleMapFragment();
                    return googleMapFragment;
                }
            case 1:
                cur_position = 1;
                if(MainActivity.fragmentFlagArr[position]==0) {
                    serviceTabFragment = new ServiceTabFragment();
                    return serviceTabFragment;
                }
                else if(MainActivity.fragmentFlagArr[position]==1){
                    GoogleMapFragment googleMapFragment = new GoogleMapFragment();
                    return googleMapFragment;
                }
            case 2:
                cur_position = 2;
                if(MainActivity.fragmentFlagArr[position]==0) {
                    saleTabFragment = new SaleTabFragment();
                    return saleTabFragment;
                }
                else if(MainActivity.fragmentFlagArr[position]==1) {
                    GoogleMapFragment googleMapFragment = new GoogleMapFragment();
                    return googleMapFragment;
                }
            case 3:
                cur_position = 3;
                if(MainActivity.fragmentFlagArr[position]==0) {
                    etcTabFragment = new EtcTabFragment();
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

