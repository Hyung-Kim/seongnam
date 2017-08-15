package songjong.com.seongnamgiftcard.CompanyFragment;

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
import songjong.com.seongnamgiftcard.R;


/**
 * Created by TaeHyungKim on 2017-08-16.
 */

public class RecyclerViewAdapter_Company extends RecyclerView.Adapter<RecyclerViewAdapter_Company.CompanyViewHolder> {

    private final Context context;
    private List<Menu> companyList;

    public RecyclerViewAdapter_Company(Context context) {
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
    public RecyclerViewAdapter_Company.CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_menu, parent, false);
        RecyclerViewAdapter_Company.CompanyViewHolder viewHolder = new RecyclerViewAdapter_Company.CompanyViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerViewAdapter_Company.CompanyViewHolder holder, final int position) {

        Menu menu = companyList.get(position);
        holder.textViewRadioNameMenu.setText(menu.getRadioName());
    }
    @Override
    public int getItemCount() {
        return companyList.size();
    }

}