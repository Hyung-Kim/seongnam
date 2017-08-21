package songjong.com.seongnamgiftcard.Adapter;

/**
 * Created by dongwook on 2017. 8. 21..
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import songjong.com.seongnamgiftcard.FieldClass.Menu;
import songjong.com.seongnamgiftcard.R;

/**
 * Created by TaeHyungKim on 2017-08-16.
 */

public class CompanyRecyclerViewAdapter extends RecyclerView.Adapter<CompanyRecyclerViewAdapter.CompanyViewHolder> {

    private final Context context;
    private List<Menu> companyList;

    public CompanyRecyclerViewAdapter(Context context) {
        this.context = context;
        companyList = new ArrayList<>();
    }
    public void setCompanyList(List<Menu> companyList){
        this.companyList = companyList;
        notifyDataSetChanged();
    }
    public class CompanyViewHolder extends RecyclerView.ViewHolder{
        public CardView cvItem; //for touch listener
        @Bind(R.id.textview_radio_name_menu)
        TextView textViewRadioNameMenu;


        public CompanyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            cvItem = (CardView)itemView.findViewById(R.id.card_view_menu);
        }
    }

    @Override
    public CompanyRecyclerViewAdapter.CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu, parent, false);
        CompanyRecyclerViewAdapter.CompanyViewHolder viewHolder = new CompanyRecyclerViewAdapter.CompanyViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(CompanyRecyclerViewAdapter.CompanyViewHolder holder, final int position) {

        Menu menu = companyList.get(position);
        holder.textViewRadioNameMenu.setText(menu.getMenuName());
    }
    @Override
    public int getItemCount() {
        return companyList.size();
    }

}