package songjong.com.seongnamgiftcard.TabFragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import songjong.com.seongnamgiftcard.Adapter.RecyclerViewAdapter;
import songjong.com.seongnamgiftcard.FieldClass.Company;
import songjong.com.seongnamgiftcard.R;
/**
 * Created by dongwook on 2017. 8. 7..
 */

public class FoodTabFragment extends Fragment {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private static String TAG = "food_company";
    private static final String TAG_JSON="company_data";
    private static final String TAG_NUMBER = "company_number";
    private static final String TAG_ADDRESS = "company_address";
    private static final String TAG_NAME ="company_name";
    private static final String TAG_SUBCLASS = "company_subsubclass";
    private static final String TAG_LATITUDE = "company_latitude";
    private static final String TAG_LONGITUDE = "company_longitude";
    private static final String TAG_MENU = "company_menu";
    private static String cur_subsubclass = "전체";
    private ArrayList<HashMap<String, String>> mArrayList;
    private String mJsonString;
    public static List<Company> companyList = new ArrayList<>();

    private RecyclerViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_food, container, false);
        ButterKnife.bind(getActivity());

        Spinner spinner = (Spinner) view.findViewById(R.id.food_spinner_id);
        ArrayAdapter spinnerAdapter =ArrayAdapter.createFromResource(getActivity(),R.array.food,android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //아이템 변경 시
                switch(position)
                {
                    case 0:
                        cur_subsubclass = "전체";
                        break;
                    case 1:
                        cur_subsubclass = "치킨";
                        break;
                    case 2:
                        cur_subsubclass = "피자";
                        break;
                    case 3:
                        cur_subsubclass="족발";
                        break;
                    case 4:
                        cur_subsubclass = "일식";
                        break;
                    case 5:
                        cur_subsubclass = "중식";
                        break;
                    case 6:
                        cur_subsubclass = "한식";
                        break;
                    case 7:
                        cur_subsubclass = "버거";
                        break;
                    case 8:
                        cur_subsubclass = "분식";
                        break;
                    case 9:
                        cur_subsubclass = "기타";
                        break;
                }
                companyList.clear();
                loadData();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerViewAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        loadData();

        return view;
    }
    private void loadData(){
        if(companyList.isEmpty()) {
            mArrayList = new ArrayList<>();
            GetData task = new GetData();
            task.execute("http://18.220.157.131/loadAllData.php", "음식", cur_subsubclass);
        }else
        {
            adapter.setCompanyList(companyList);
        }
    }
    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "불러오는 중입니다. 잠시만 기다려주세요", null, true, true);

        }
        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG,"onPostExecute - " + result);
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (result == null){
                Toast.makeText(getActivity(),"Error onPostExecute() ", Toast.LENGTH_SHORT).show();
            }
            else {
                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            String m_class = params[1];
            String m_subClass = params[2];
            String postParameters = "class="+m_class + "&subClass="+m_subClass;
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
                Toast.makeText(getActivity(),"Error doInBackground", Toast.LENGTH_SHORT).show();
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
                String name = item.getString(TAG_NAME);
                String address = item.getString(TAG_ADDRESS);
                String number = item.getString(TAG_NUMBER);
                String latitude = item.getString(TAG_LATITUDE);
                String longitude = item.getString(TAG_LONGITUDE);

                if(number=="null"){
                    number="";
                }
                String subclass = item.getString(TAG_SUBCLASS);
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(TAG_NAME, name);
                hashMap.put(TAG_ADDRESS, address);
                hashMap.put(TAG_NUMBER, number);
                hashMap.put(TAG_SUBCLASS, subclass);
                hashMap.put(TAG_LATITUDE, latitude);
                hashMap.put(TAG_LONGITUDE, longitude);
                mArrayList.add(hashMap);
            }
            HashMap<String,String> takeMap;
            for(int i=0; i<mArrayList.size();i++) {
                takeMap = mArrayList.get(i);
                Company company = new Company(takeMap.get(TAG_NAME), takeMap.get(TAG_NUMBER), takeMap.get(TAG_ADDRESS),
                        takeMap.get(TAG_LATITUDE), takeMap.get(TAG_LONGITUDE), takeMap.get(TAG_SUBCLASS));
                companyList.add(company);
            }
            adapter.setCompanyList(companyList);
        } catch (JSONException e) {
            Toast.makeText(getActivity(),"Error showResult", Toast.LENGTH_SHORT).show();
        }
    }

}
