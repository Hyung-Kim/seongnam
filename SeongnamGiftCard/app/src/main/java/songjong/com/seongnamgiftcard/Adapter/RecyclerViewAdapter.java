package songjong.com.seongnamgiftcard.Adapter;

/**
 * Created by dongwook on 2017. 8. 21..
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import songjong.com.seongnamgiftcard.Activity.CompanyActivity;
import songjong.com.seongnamgiftcard.FieldClass.Company;
import songjong.com.seongnamgiftcard.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CompanyViewHolder> {

    private final Context context;
    private List<Company> companyList;
    private String TAG ="recycler";
    public static int curCompanyyPosition=0;
    public RecyclerViewAdapter(Context context) {
        this.context = context;
        companyList = new ArrayList<>();
    }
    public void setCompanyList(List<Company> companyList){
        this.companyList = companyList;
        notifyDataSetChanged();
    }
    public class CompanyViewHolder extends RecyclerView.ViewHolder{
        public CardView cvItem;

        @Bind(R.id.company_name)
        TextView textViewCompanyName;

        @Bind(R.id.company_address)
        TextView textViewCompanyAddress;

        @Bind(R.id.company_distance)
        TextView textViewCompanyDistance;

        @Bind(R.id.company_review)
        TextView textViewCompanyReview;

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
        if(company.getCompanySubGroup()!=null){
            holder.textViewCompanyName.setText(company.getCompanyName()+"("+company.getCompanySubGroup()+")");
        }
        else{
            holder.textViewCompanyName.setText(company.getCompanyName());
        }


        holder.textViewCompanyAddress.setText(company.getCompanyAddress());
        Double prevCompanyDistance = company.getCompanyDistance();
        prevCompanyDistance*=1000;
        int curCompanyDistance = prevCompanyDistance.intValue();
        Double curCompanyDistanceDouble=0.0;
       ;
        if(curCompanyDistance>1000){
            curCompanyDistanceDouble = (double)curCompanyDistance/1000;
            String num = String.format("%.1f ", curCompanyDistanceDouble);
            holder.textViewCompanyDistance.setText("("+num+"km)");
        }
        else{
            holder.textViewCompanyDistance.setText("("+curCompanyDistance+"m)");
        }
        //여기에 추가해야된다.

        holder.textViewCompanyReview.setText("리뷰 : "+company.getCompanyReview());
        holder.cvItem.setOnClickListener(new View.OnClickListener()
        {
            Intent intent;
            @Override
            public void onClick(View v)
            {
                Log.d(TAG,""+position);
                curCompanyyPosition = position;
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