package songjong.com.seongnamgiftcard.TabFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import songjong.com.seongnamgiftcard.Adapter.RecyclerViewAdapter;
import songjong.com.seongnamgiftcard.Adapter.SpinnerAdapter;
import songjong.com.seongnamgiftcard.FieldClass.Company;
import songjong.com.seongnamgiftcard.FieldClass.State;
import songjong.com.seongnamgiftcard.R;
/**
 * Created by dongwook on 2017. 8. 7..
 */

public class FoodTabFragment extends Fragment {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private RecyclerViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment_food, container, false);

        ButterKnife.bind(getActivity());

        final String[] food_select = {
                "음식 전체", "치킨","족발","일식","중식","한식"};
        Spinner spinner = (Spinner) view.findViewById(R.id.food_spinner_id);

        ArrayList<State> list = new ArrayList<>();

        for (int i = 0; i < food_select.length; i++) {
            State state = new State();
            state.setTitle(food_select[i]);
            state.setSelected(false);
            list.add(state);
        }
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getActivity(), 0, list);
        spinner.setAdapter(spinnerAdapter);
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