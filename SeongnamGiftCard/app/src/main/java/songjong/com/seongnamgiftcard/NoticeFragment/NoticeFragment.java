package songjong.com.seongnamgiftcard.NoticeFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import songjong.com.seongnamgiftcard.R;

/**
 * Created by dongwook on 2017. 9. 9..
 */
public class NoticeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notice_recycler_fragment, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.notice_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new SimpleAdapter(recyclerView));

        return rootView;
    }

    private static class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {
        private final int UNSELECTED = -1;
        private static int noticeCnt=5;
        private RecyclerView recyclerView;
        private int selectedItem = UNSELECTED;
        public SimpleAdapter(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notice_recycler_item, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return noticeCnt;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ExpandableLayout.OnExpansionUpdateListener {
            private ExpandableLayout expandableLayout;
            private TextView expandButton;
            private TextView expandText;
            private int position;

            public ViewHolder(View itemView) {
                super(itemView);

                expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_layout);
                expandableLayout.setInterpolator(new OvershootInterpolator());
                expandableLayout.setOnExpansionUpdateListener(this);
                expandButton = (TextView) itemView.findViewById(R.id.expand_button);
                expandText=(TextView) itemView.findViewById(R.id.expandable_text);
                expandButton.setOnClickListener(this);
            }

            public void bind(int position) {
                this.position = position;

                expandButton.setText(position + ". Tap to expand");
                expandText.setText(position+"태동 브라더스");
                expandButton.setSelected(false);
                expandableLayout.collapse(false);
            }

            @Override
            public void onClick(View view) {
                ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(selectedItem);
                if (holder != null) {
                    holder.expandButton.setSelected(false);
                    holder.expandableLayout.collapse();
                    Log.d("Notice", "holder != null");
                }
                if (position == selectedItem) {
                    selectedItem = UNSELECTED;
                    Log.d("Notice", "position == selecteed");
                } else {
                    Log.d("Notice", "position != selecteed");
                    expandButton.setSelected(true);
                    expandableLayout.expand();
                    selectedItem = position;
                }
            }

            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                recyclerView.smoothScrollToPosition(getAdapterPosition());
            }
        }
    }
}