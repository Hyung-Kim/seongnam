package songjong.com.seongnamgiftcard.CompanyFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
/**
 * Created by dongwook on 2017. 8. 7..
 */
public class CompanyPagerAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public CompanyPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MenuFragment menuFragment = new MenuFragment();
                return menuFragment;
            case 1:
                InformationFragment informationFragment = new InformationFragment();
                return informationFragment;
            case 2:
                ReviewFragment reviewFragment = new ReviewFragment();
                return reviewFragment;
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
