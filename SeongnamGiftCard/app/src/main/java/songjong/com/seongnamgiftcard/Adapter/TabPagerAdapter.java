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
                if(MainActivity.fragmentFlag==0){
                    FoodTabFragment foodTabFragment = new FoodTabFragment();
                    return foodTabFragment;
                }
                else if(MainActivity.fragmentFlag==1){
                    GoogleMapFragment googleMapFragment = new GoogleMapFragment();
                    return googleMapFragment;
                }
            case 1:
                ServiceTabFragment serviceTabFragment = new ServiceTabFragment();
                return serviceTabFragment;
            case 2:
                SaleTabFragment saleTabFragment = new SaleTabFragment();
                return saleTabFragment;
            case 3:
                EtcTabFragment etcTabFragment = new EtcTabFragment();
                return etcTabFragment;
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

