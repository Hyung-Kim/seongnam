package songjong.com.seongnamgiftcard.CompanyFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import songjong.com.seongnamgiftcard.Activity.MainActivity;
import songjong.com.seongnamgiftcard.Adapter.CompanyReviewAdapter;
import songjong.com.seongnamgiftcard.FieldClass.Review;
import songjong.com.seongnamgiftcard.R;

public class ReviewFragment extends Fragment {
    Button btnAddItem;
    ListView lvList;
    ArrayList<Review> myList = new ArrayList<Review>();
    CompanyReviewAdapter listAdapter;
    EditText etTitle, etDescription;
    String title = "", description = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.company_fragment_review, container, false);

        lvList = (ListView) v.findViewById(R.id.lvList);
        listAdapter = new CompanyReviewAdapter(getActivity(), myList);

        lvList.setAdapter(listAdapter);
        etTitle = (EditText) v.findViewById(R.id.etTitle);
        etDescription = (EditText) v.findViewById(R.id.etDescription);
        btnAddItem = (Button) v.findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = etTitle.getText().toString();
                title = "제목 : "+title;
                description = etDescription.getText().toString();
                description = "내용 : " + description;
                if (title.length() < 4 && description.length() <10)
                    Toast.makeText(getActivity(), "제목은 최소 4글자, 내용은 10글자 이상 입력하셔야 합니다.", Toast.LENGTH_SHORT).show();
                else if(title.length() < 4)
                    Toast.makeText(getActivity(), "제목은 최소 4글자 이상 입력하셔야 합니다.", Toast.LENGTH_SHORT).show();
                else if(description.length() < 10)
                    Toast.makeText(getActivity(),  "내용은 최소 4글자 이상 입력하셔야 합니다.", Toast.LENGTH_SHORT).show();
                else {
                    Review mLog = new Review();
                    mLog.setTitle(title);
                    mLog.setDescription(description);
                    myList.add(mLog);
                    etTitle.getText().clear();
                    etDescription.getText().clear();
                    listAdapter.notifyDataSetChanged();
                }
            }
        });
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Review mLog = listAdapter.getItem(position);
                Toast.makeText(getActivity(), "Title: " + mLog.getTitle() + "  Description: " + mLog.getDescription(), Toast.LENGTH_LONG).show();
            }
        });
        return v;
    }
}
