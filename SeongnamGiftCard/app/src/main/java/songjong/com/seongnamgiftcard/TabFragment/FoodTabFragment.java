package songjong.com.seongnamgiftcard.TabFragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import songjong.com.seongnamgiftcard.R;
import songjong.com.seongnamgiftcard.Radio;
import songjong.com.seongnamgiftcard.RecyclerViewAdapter;
import android.support.design.widget.CollapsingToolbarLayout;

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
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerViewAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        loadData();

        return view;
    }
    private void loadData(){
        Radio radio = new Radio("Joy Radio FM", R.drawable.temp, "102.5");
        List<Radio> radioList = new ArrayList<>();
        for (int i = 0 ; i < 20 ; i++)
            radioList.add(radio);
        adapter.setRadioList(radioList);
    }
}