package songjong.com.seongnamgiftcard.Adapter;

/**
 * Created by taehyung on 2017-09-20.
 */
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import songjong.com.seongnamgiftcard.FieldClass.Notice;
import songjong.com.seongnamgiftcard.R;

public class NoticeExpandableAdapter extends BaseExpandableListAdapter{

    private ArrayList<Notice> noticeList = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;

    public NoticeExpandableAdapter(Context c, ArrayList<Notice>  noticeList){
        super();
        this.inflater = LayoutInflater.from(c);
        this.noticeList = noticeList;
    }

    @Override
    public String getGroup(int groupPosition) {
        return noticeList.get(groupPosition).getTitle();
    }

    // 그룹 사이즈를 반환한다.
    @Override
    public int getGroupCount() {
        return noticeList.size();
    }

    // 그룹 ID를 반환한다.
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // 그룹뷰 각각의 ROW
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.notice_recycler_item, parent, false);
            viewHolder.tv_title = (TextView) v.findViewById(R.id.tv_group);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        // 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
        if(isExpanded){
            viewHolder.tv_title.setBackgroundColor(v.getResources().getColor(R.color.color_seongnam));
            viewHolder.tv_title.setTextColor(v.getResources().getColor(R.color.color_white));
        }else{
            viewHolder.tv_title.setBackgroundColor(v.getResources().getColor(R.color.color_white));
            viewHolder.tv_title.setTextColor(v.getResources().getColor(R.color.color_black));
        }

        viewHolder.tv_title.setText(getGroup(groupPosition));
        return v;
    }

    // 차일드뷰를 반환한다.
    @Override
    public String getChild(int groupPosition, int childPosition) {
        return noticeList.get(groupPosition).getContents();
    }
    public String getChildDate(int groupPosition, int childPosition){
        return noticeList.get(groupPosition).getDate();
    }
    // 차일드뷰 사이즈를 반환한다.
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    // 차일드뷰 ID를 반환한다.
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // 차일드뷰 각각의 ROW
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.notice_recycler_item, null);
            viewHolder.tv_contents = (TextView) v.findViewById(R.id.tv_child);
            viewHolder.tv_date=(TextView)v.findViewById(R.id.tv_childDate);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        viewHolder.tv_contents.setText(getChild(groupPosition, childPosition));
        viewHolder.tv_date.setText(getChildDate(groupPosition, childPosition));
        return v;
    }

    @Override
    public boolean hasStableIds() {	return true; }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }

    class ViewHolder {
        public TextView tv_title;
        public TextView tv_contents;
        public TextView tv_date;
    }
}

