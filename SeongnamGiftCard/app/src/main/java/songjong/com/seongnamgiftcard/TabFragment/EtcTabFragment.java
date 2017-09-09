package songjong.com.seongnamgiftcard.TabFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import songjong.com.seongnamgiftcard.Adapter.RecyclerViewAdapter;
import songjong.com.seongnamgiftcard.FieldClass.Company;
import songjong.com.seongnamgiftcard.R;

/**
 * Created by dongwook on 2017. 8. 7..
 */

public class EtcTabFragment extends Fragment {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private RecyclerViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_etc, container, false);

        ButterKnife.bind(getActivity());

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerViewAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        loadData();
        return view;
    }

    private void loadData(){
        Company company = new Company("Joy Company FM", "031-000-0000", "경기도 성남시", "123","123","123","123");
        List<Company> companyList = new ArrayList<>();
        for (int i = 0 ; i < 20 ; i++)
            companyList.add(company);
        adapter.setCompanyList(companyList);
    }
}
