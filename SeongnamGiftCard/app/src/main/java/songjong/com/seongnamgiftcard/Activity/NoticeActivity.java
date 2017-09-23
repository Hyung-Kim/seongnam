package songjong.com.seongnamgiftcard.Activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import songjong.com.seongnamgiftcard.Adapter.NoticeExpandableAdapter;
import songjong.com.seongnamgiftcard.FieldClass.Company;
import songjong.com.seongnamgiftcard.FieldClass.Notice;
import songjong.com.seongnamgiftcard.R;
import songjong.com.seongnamgiftcard.TabFragment.FoodTabFragment;

public class NoticeActivity extends AppCompatActivity {
    private static final String TAG = "NoticeActivity";
    private static final String TAG_JSON = "noticeResult";
    private static final String TAG_TITLE = "notice_title";
    private static final String TAG_CONTENTS = "notice_contents";
    private static final String TAG_DATE = "notice_date";
    private String mJsonString;
    static private ArrayList<Notice> mGroupList = new ArrayList<>();
    private static NoticeExpandableAdapter mBaseExpandableAdapter = null;
    private ExpandableListView mListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        setContentView(R.layout.activity_notice);
        setLayout();
        mListView.setGroupIndicator(null);

        // 그룹 클릭 했을 경우 이벤트
        mListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return false;
            }
        });

        // 차일드 클릭 했을 경우 이벤트
        mListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return false;
            }
        });

        // 그룹이 닫힐 경우 이벤트
        mListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        // 그룹이 열릴 경우 이벤트
        mListView.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int groupCount = mBaseExpandableAdapter.getGroupCount();

                // 한 그룹을 클릭하면 나머지 그룹들은 닫힌다.
                for (int i = 0; i < groupCount; i++) {
                    if (!(i == groupPosition))
                        mListView.collapseGroup(i);
                }
            }
        });
    }
    private void setLayout(){
        mListView = (ExpandableListView) findViewById(R.id.elv_list);
    }
    //loadNotice
    public void loadData(){
        if(mGroupList.isEmpty()){
            GetData task = new GetData();
            task.execute("http://13.124.195.13/loadNotice.php");
        }
        else {
            mBaseExpandableAdapter = new NoticeExpandableAdapter(this, mGroupList);
            mListView.setAdapter(mBaseExpandableAdapter);
            mBaseExpandableAdapter.getGroupCount();
        }
    }
    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(NoticeActivity.this, "불러오는 중입니다. 잠시만 기다려주세요", null, true, true);

        }
        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG,"onPostExecute - " + result);
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (result == null){
                Toast.makeText(NoticeActivity.this,"Error onPostExecute() ", Toast.LENGTH_SHORT).show();
            }
            else {
                mJsonString = result;
                showResult();
            }
        }
        public String doInBackground(String... params) {
            String serverURL = params[0];
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                InputStream inputStream;
                int responseStatusCode = httpURLConnection.getResponseCode();
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;
                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {
                Toast.makeText(NoticeActivity.this,"Error doInBackground", Toast.LENGTH_SHORT).show();
                return null;
            }

        }
    }
    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);
                String title = item.getString(TAG_TITLE);
                String contents = item.getString(TAG_CONTENTS);
                String date = item.getString(TAG_DATE);
                Notice notice = new Notice(title, contents, date);
                Log.d(TAG,"" + contents);
                mGroupList.add(notice);
            }
            mBaseExpandableAdapter = new NoticeExpandableAdapter(this, mGroupList);
            mListView.setAdapter(mBaseExpandableAdapter);
            mBaseExpandableAdapter.getGroupCount();
        } catch (JSONException e) {
            Toast.makeText(NoticeActivity.this, "Error showResult", Toast.LENGTH_SHORT).show();
        }
    }
}