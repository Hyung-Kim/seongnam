package songjong.com.seongnamgiftcard.CompanyFragment;

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
import songjong.com.seongnamgiftcard.Adapter.CompanyRecyclerViewAdapter;
import songjong.com.seongnamgiftcard.FieldClass.Menu;
import songjong.com.seongnamgiftcard.R;


public class MenuFragment extends Fragment {
    @Bind(R.id.recyclerView_menu)
    RecyclerView recyclerView;
    private CompanyRecyclerViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.company_fragment_menu, container, false);
        ButterKnife.bind(getActivity());

        //recyclerView 초기화
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_menu);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CompanyRecyclerViewAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);

        //샘플 데이터 삽입
        loadData();
        return view;
    }
    private void loadData(){
        Menu company = new Menu("후라이드치킨");
        List<Menu> menuList = new ArrayList<>();
        for (int i = 0 ; i < 40 ; i++)
            menuList.add(company);
        adapter.setCompanyList(menuList);
    }
}
