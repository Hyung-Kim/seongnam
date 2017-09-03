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
import songjong.com.seongnamgiftcard.Adapter.SpinnerAdapter;
import songjong.com.seongnamgiftcard.FieldClass.Company;
import songjong.com.seongnamgiftcard.FieldClass.State;
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
    private static final String TAG_MENU = "company_menu";
    ArrayList<HashMap<String, String>> mArrayList;
    String mJsonString;

    private RecyclerViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_food, container, false);
        ButterKnife.bind(getActivity());

        final String[] food_select = {
                "음식 전체", "치킨","피자","족발","일식","중식","한식","버거","분식","기타"};
        Spinner spinner = (Spinner) view.findViewById(R.id.food_spinner_id);

        ArrayList<State> list = new ArrayList<>();

        for (int i = 0; i < food_select.length; i++) {
            State state = new State();
            state.setTitle(food_select[i]);
            state.setSelected(false);
            list.add(state);
        }
        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getActivity(), 0, list);
        spinner.setAdapter(spinnerAdapter);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerViewAdapter(getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);
        loadData();

        return view;
    }
    private void loadData(){
        mArrayList = new ArrayList<>();
        GetData task = new GetData();
        task.execute("http://18.220.157.131/loadAllData.php", "음식");
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
            String postParameters = "class="+m_class;
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
                if(number=="null"){
                    number="";
                }
                String subclass = item.getString(TAG_SUBCLASS);
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put(TAG_NAME, name);
                hashMap.put(TAG_ADDRESS, address);
                hashMap.put(TAG_NUMBER, number);
                hashMap.put(TAG_SUBCLASS, subclass);
                mArrayList.add(hashMap);
            }
            List<Company> companyList = new ArrayList<>();
            HashMap<String,String> takeMap;
            for(int i=0; i<100;i++) {
                takeMap = mArrayList.get(i);
                Company company = new Company(takeMap.get(TAG_NAME), takeMap.get(TAG_NUMBER), takeMap.get(TAG_ADDRESS),R.drawable.temp);
                //이 부분에서 takeMap.get(TAG_SUBCLASS)로 가져와서 사용하면 됨
                companyList.add(company);
            }
            adapter.setCompanyList(companyList);
        } catch (JSONException e) {
            Toast.makeText(getActivity(),"Error showResult", Toast.LENGTH_SHORT).show();
        }
    }
}
