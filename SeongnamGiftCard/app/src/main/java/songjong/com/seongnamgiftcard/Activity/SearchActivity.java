package songjong.com.seongnamgiftcard.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import songjong.com.seongnamgiftcard.Adapter.RecyclerViewAdapter;
import songjong.com.seongnamgiftcard.FieldClass.Company;
import songjong.com.seongnamgiftcard.R;
import songjong.com.seongnamgiftcard.TabFragment.ServiceTabFragment;

import static android.content.ContentValues.TAG;

/**
 * Created by dongwook on 2017. 9. 20..
 */

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private static final String TAG = "searchActivity";
    private static final String TAG_JSON="company_data";
    private static final String TAG_NUMBER = "company_number";
    private static final String TAG_ADDRESS = "company_address";
    private static final String TAG_NAME ="company_name";
    private static final String TAG_LATITUDE = "company_latitude";
    private static final String TAG_LONGITUDE = "company_longitude";
    private static final String TAG_DISTANCE = "company_distance";
    private static final String TAG_ID = "company_id";
    public static List<Company> companyList = new ArrayList<>();
    private String mJsonString;
    private RecyclerViewAdapter adapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //툴바 초기화
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView tv = (TextView) findViewById(R.id.actionbar_title_search);
        tv.setText("검색하기");

        Intent intent = getIntent();
        String companyName = intent.getStringExtra("company"); // 입력 받은 업체명 String
        Toast.makeText(getApplicationContext(),""+companyName,Toast.LENGTH_LONG).show();

        //수정부분
        recyclerView = (RecyclerView)findViewById(R.id.searchRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        adapter = new RecyclerViewAdapter(SearchActivity.this);
        recyclerView.setAdapter(adapter);
        companyList.clear();
        loadData(companyName);
    }
    private void loadData(String companyName){
        GetData task = new GetData();
        task.execute("http://13.124.195.13/loadSearchData.php", companyName);
        }
    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(SearchActivity.this, "불러오는 중입니다. 잠시만 기다려주세요", null, true, true);

        }
        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG,"onPostExecute - " + result);
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (result == null){
                Toast.makeText(SearchActivity.this,"Error onPostExecute() ", Toast.LENGTH_SHORT).show();
            }
            else {
                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            String searchName = params[1];
            String latitude = String.valueOf(MainActivity.latitude);
            String longitude = String.valueOf(MainActivity.longitude);
            String postParameters = "latitude="+latitude+"&longitude="+longitude +"&searchName="+searchName;
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);
                InputStream inputStream;
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
                Toast.makeText(SearchActivity.this,"Error doInBackground", Toast.LENGTH_SHORT).show();
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
                String number = item.getString(TAG_NUMBER);
                if(number=="null")
                    number="";
                Company company = new Company(item.getString(TAG_NAME), number, item.getString(TAG_ADDRESS), item.getString(TAG_LATITUDE), item.getString(TAG_LONGITUDE), item.getString(TAG_DISTANCE),item.getString(TAG_ID));
                companyList.add(company);
            }
            adapter.setCompanyList(companyList);
        } catch (JSONException e) {
            Toast.makeText(SearchActivity.this,"Error showResult", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
