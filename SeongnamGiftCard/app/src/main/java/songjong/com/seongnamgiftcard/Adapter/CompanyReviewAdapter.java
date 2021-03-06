package songjong.com.seongnamgiftcard.Adapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import songjong.com.seongnamgiftcard.FieldClass.Review;
import songjong.com.seongnamgiftcard.R;

/**
 * Created by taehyung on 2017-08-21.
 */

public class CompanyReviewAdapter extends BaseAdapter {
    ArrayList<Review> myList = new ArrayList<Review>();
    Context context;

    public CompanyReviewAdapter(Context context, ArrayList<Review> myList) {
        this.myList = myList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Review getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ListViewHolder extends LinearLayout {
        Context mContext;
        Review mReview;

        public ListViewHolder(Context context) {
            super(context);
            mContext = context;
            setup();
        }

        public ListViewHolder(Context context, AttributeSet attrs) {
            super(context, attrs);
            mContext = context;
            setup();
        }

        private void setup() {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.list_item_review, this);
        }

        public void setReview(Review review) {
            mReview = review;
            TextView tvContents = (TextView) findViewById(R.id.txtContents);
            TextView tvDate = (TextView)findViewById(R.id.txtDate);
            tvContents.setText(mReview.getContents() + "");
            tvDate.setText(mReview.getDate()+"");
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CompanyReviewAdapter.ListViewHolder view = (CompanyReviewAdapter.ListViewHolder) convertView;
        if (view == null) {
            view = new CompanyReviewAdapter.ListViewHolder(context);
        }
        Review reveiw = getItem(position);
        view.setReview(reveiw);
        return view;
    }
}
