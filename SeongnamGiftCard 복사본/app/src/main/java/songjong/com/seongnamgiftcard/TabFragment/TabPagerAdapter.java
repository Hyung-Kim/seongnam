package songjong.com.seongnamgiftcard.TabFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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
                FoodTabFragment foodTabFragment = new FoodTabFragment();
                return foodTabFragment;
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
    @Override
    public int getCount() {
        return tabCount;
    }
}

