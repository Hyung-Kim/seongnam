package songjong.com.seongnamgiftcard.TabFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import songjong.com.seongnamgiftcard.Company;
import songjong.com.seongnamgiftcard.ExpandableList.ExpanableGroup;
import songjong.com.seongnamgiftcard.ExpandableList.ExpandableListAdapter;
import songjong.com.seongnamgiftcard.R;

import static android.R.attr.width;

/**
 * Created by dongwook on 2017. 8. 7..
 */

public class FoodTabFragment extends Fragment {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private ExpandableListView expandableListView;

    private RecyclerViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment_food, container, false);

        ButterKnife.bind(getActivity());

        ArrayList<ExpanableGroup> groupDataList = new ArrayList<ExpanableGroup>();
        expandableListView = (ExpandableListView) view.findViewById(R.id.expadableListViewId);
        ExpanableGroup group = new ExpanableGroup("음식");
        group.child.add("족발");
        group.child.add("치킨");
        group.child.add("한식");
        group.child.add("중식");
        groupDataList.add(group);

        ExpandableListAdapter expandableAdapter = new ExpandableListAdapter(getActivity(),R.layout.food_expandable_parent,R.layout.food_expandable_child,groupDataList);
        expandableListView.setIndicatorBounds(width-50, width); //이 코드를 지우면 화살표 위치가 바뀐다.
        expandableListView.setAdapter(expandableAdapter);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerViewAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        loadData();

        return view;
    }
    private void loadData(){
        Company company = new Company("Joy Company FM", R.drawable.temp, "102.5");
        List<Company> companyList = new ArrayList<>();
        for (int i = 0 ; i < 20 ; i++)
            companyList.add(company);
        adapter.setCompanyList(companyList);
    }
}