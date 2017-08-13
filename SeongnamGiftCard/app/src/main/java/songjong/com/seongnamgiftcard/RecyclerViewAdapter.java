package songjong.com.seongnamgiftcard;

import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RadioViewHolder> {

    private Context context;
    private List<Radio> radioList;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        radioList = new ArrayList<>();
    }
    public void setRadioList(List<Radio> radioList){
        this.radioList = radioList;
        notifyDataSetChanged();
    }

    @Override
    public RadioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card, parent, false);
        RadioViewHolder viewHolder = new RadioViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RadioViewHolder holder, final int position) {

        Radio radio = radioList.get(position);
        holder.textViewRadioName.setText(radio.getRadioName());
        holder.textViewRadioDial.setText("(" + radio.getRadioDial() + ")");
        Picasso.with(context).load(radio.getRadioArt()).into(holder.imageViewRadioLogo);
        holder.textViewRadioTags.setText("#rock #pop #news");

        holder.cvItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Context context = v.getContext();
                Intent intent = new Intent(context, ComPanyInformationActivity.class);
                context.startActivity(intent);
                //Toast.makeText(context, "클릭하셨습니다.",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return radioList.size();
    }
    public class RadioViewHolder extends RecyclerView.ViewHolder{
        public CardView cvItem; //for touch listener
        @Bind(R.id.textview_radio_name)
        TextView textViewRadioName;

        @Bind(R.id.textview_radio_dial)
        TextView textViewRadioDial;

        @Bind(R.id.textview_tags)
        TextView textViewRadioTags;

        @Bind(R.id.imageview_radio_logo)
        ImageView imageViewRadioLogo;

        public RadioViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            cvItem = (CardView)itemView.findViewById(R.id.card_view);
        }
    }
}