package songjong.com.seongnamgiftcard.TabFragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import songjong.com.seongnamgiftcard.Company;
import songjong.com.seongnamgiftcard.CompanyActivity;
import songjong.com.seongnamgiftcard.R;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CompanyViewHolder> {

    private final Context context;
    private List<Company> companyList;
    private String TAG ="recycler";

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        companyList = new ArrayList<>();
    }
    public void setCompanyList(List<Company> companyList){
        this.companyList = companyList;
        notifyDataSetChanged();
    }
    public class CompanyViewHolder extends RecyclerView.ViewHolder{
        public CardView cvItem; //for touch listener
        @Bind(R.id.textview_radio_name)
        TextView textViewRadioName;

        @Bind(R.id.textview_radio_dial)
        TextView textViewRadioDial;

        @Bind(R.id.textview_tags)
        TextView textViewRadioTags;

        @Bind(R.id.imageview_radio_logo)
        ImageView imageViewRadioLogo;

        public CompanyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            cvItem = (CardView)itemView.findViewById(R.id.card_view);
        }
    }

    @Override
    public CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_company, parent, false);
        CompanyViewHolder viewHolder = new CompanyViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(CompanyViewHolder holder, final int position) {

        Company company = companyList.get(position);
        holder.textViewRadioName.setText(company.getRadioName());
        holder.textViewRadioDial.setText("(" + company.getRadioDial() + ")");
        Picasso.with(context).load(company.getRadioArt()).into(holder.imageViewRadioLogo);
        holder.textViewRadioTags.setText("#rock #pop #news");
        holder.cvItem.setOnClickListener(new View.OnClickListener()
        {
           Intent intent;
            @Override
            public void onClick(View v)
            {
                Log.d(TAG,""+position);
                Toast.makeText(context, "클릭하셨습니다."+position, Toast.LENGTH_SHORT).show();
                intent = new Intent(context,CompanyActivity.class);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return companyList.size();
    }

}