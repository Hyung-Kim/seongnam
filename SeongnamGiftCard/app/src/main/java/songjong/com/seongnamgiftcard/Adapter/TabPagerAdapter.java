package songjong.com.seongnamgiftcard.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import songjong.com.seongnamgiftcard.Activity.MainActivity;
import songjong.com.seongnamgiftcard.TabFragment.ClothTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.EtcTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.FlowerTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.FoodTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.GoogleMapFragment;
import songjong.com.seongnamgiftcard.TabFragment.MeatTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.PharmacyTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.SalonTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.StuffTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.SuperTabFragment;

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
                SuperTabFragment superTabFragment = new SuperTabFragment();
                return superTabFragment;
            case 2:
                MeatTabFragment meatTabFragment = new MeatTabFragment();
                return meatTabFragment;
            case 3:
                StuffTabFragment stuffTabFragment = new StuffTabFragment();
                return stuffTabFragment;
            case 4:
                SalonTabFragment salonTabFragment = new SalonTabFragment();
                return salonTabFragment;
            case 5:
                PharmacyTabFragment pharmacyTabFragment = new PharmacyTabFragment();
                return pharmacyTabFragment;
            case 6:
                FlowerTabFragment flowerTabFragment = new FlowerTabFragment();
                return flowerTabFragment;
            case 7:
                ClothTabFragment clothTabFragment = new ClothTabFragment();
                return clothTabFragment;
            case 8:
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

