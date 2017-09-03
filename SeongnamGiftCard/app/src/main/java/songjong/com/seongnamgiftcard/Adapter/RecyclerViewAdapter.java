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
import android.widget.Toast;

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
        @Bind(R.id.company_name)
        TextView textViewCompanyName;

        @Bind(R.id.company_number)
        TextView textViewCompanyNumber;

        @Bind(R.id.company_address)
        TextView textViewCompanyAddress;

//        @Bind(R.id.imageview_company_logo)
//        ImageView imageViewCompanyLogo;

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

        if(company.getCompanyNumber()!=""){
            holder.textViewCompanyNumber.setText("031-" + company.getCompanyNumber());
        }
        else{
            holder.textViewCompanyNumber.setText("");
        }

        holder.textViewCompanyAddress.setText(company.getCompanyAddress());
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