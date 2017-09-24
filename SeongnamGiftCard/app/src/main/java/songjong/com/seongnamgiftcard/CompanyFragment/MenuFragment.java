package songjong.com.seongnamgiftcard.CompanyFragment;

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
import songjong.com.seongnamgiftcard.Activity.MainActivity;
import songjong.com.seongnamgiftcard.Activity.SearchActivity;
import songjong.com.seongnamgiftcard.Adapter.CompanyRecyclerViewAdapter;
import songjong.com.seongnamgiftcard.Adapter.RecyclerViewAdapter;
import songjong.com.seongnamgiftcard.FieldClass.Company;
import songjong.com.seongnamgiftcard.FieldClass.Menu;
import songjong.com.seongnamgiftcard.FieldClass.Review;
import songjong.com.seongnamgiftcard.R;
import songjong.com.seongnamgiftcard.TabFragment.EtcTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.FoodTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.SaleTabFragment;
import songjong.com.seongnamgiftcard.TabFragment.ServiceTabFragment;


public class MenuFragment extends Fragment {
    private static final String TAG = "menuFragment";
    private static final String TAG_JSON = "menuResult";
    private static final String TAG_MENUNAMES = "menu_names";
    private String mJsonString;
    List<Menu> menuList = new ArrayList<>();
    @Bind(R.id.recyclerView_menu)
    RecyclerView recyclerView;
    private CompanyRecyclerViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = null;
        int menu_id = -1;
        switch(MainActivity.currentTab){
            case 0:
                menu_id = FoodTabFragment.companyList.get(RecyclerViewAdapter.curCompanyyPosition).getCompanyMenu();
                break;
            case 1:
                menu_id = ServiceTabFragment.companyList.get(RecyclerViewAdapter.curCompanyyPosition).getCompanyMenu();
                break;
            case 2:
                menu_id = SaleTabFragment.companyList.get(RecyclerViewAdapter.curCompanyyPosition).getCompanyMenu();
                break;
            case 3:
                menu_id = EtcTabFragment.companyList.get(RecyclerViewAdapter.curCompanyyPosition).getCompanyMenu();
                break;
            case 4:
                menu_id = SearchActivity.companyList.get(RecyclerViewAdapter.curCompanyyPosition).getCompanyMenu();
                break;
        }
        if(menu_id == -1)
            view = inflater.inflate(R.layout.company_fragment_menu_empty, container, false);
        else {
            view = inflater.inflate(R.layout.company_fragment_menu, container, false);
            ButterKnife.bind(getActivity());

            //recyclerView 초기화
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_menu);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new CompanyRecyclerViewAdapter(getActivity().getApplicationContext());
            recyclerView.setAdapter(adapter);

            loadData();
        }
        return view;
    }
    private void loadData(){
        GetData task = new GetData();
        task.execute("http://13.124.195.13/loadMenu.php");
    }
    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "메뉴 로드 중입니다. 잠시만 기다려주세요", null, true, true);

        }

        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "onPostExecute - " + result);
            super.onPostExecute(result);
            progressDialog.dismiss();
            if (result == null) {
                Toast.makeText(getActivity(), "Error onPostExecute() ", Toast.LENGTH_SHORT).show();
            } else {
                mJsonString = result;
                showResult();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            String postParameters = null;
            String menu_id = null;

            switch(MainActivity.currentTab){
                case 0:
                    menu_id = String.valueOf(FoodTabFragment.companyList.get(RecyclerViewAdapter.curCompanyyPosition).getCompanyMenu());
                    break;
                case 1:
                    menu_id = String.valueOf(ServiceTabFragment.companyList.get(RecyclerViewAdapter.curCompanyyPosition).getCompanyMenu());
                    break;
                case 2:
                    menu_id = String.valueOf(SaleTabFragment.companyList.get(RecyclerViewAdapter.curCompanyyPosition).getCompanyMenu());
                    break;
                case 3:
                    menu_id = String.valueOf(EtcTabFragment.companyList.get(RecyclerViewAdapter.curCompanyyPosition).getCompanyMenu());
                    break;
                case 4:
                    menu_id = String.valueOf(SearchActivity.companyList.get(RecyclerViewAdapter.curCompanyyPosition).getCompanyMenu());
                    break;
            }
            Log.d(TAG,"" +menu_id);
            postParameters = "menuId=" + menu_id;

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
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString().trim();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Error doInBackground", Toast.LENGTH_SHORT).show();
                return null;
            }

        }
    }

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            String menuNames = null;
            for(int i=0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);
                menuNames = item.getString(TAG_MENUNAMES);
            }
            String []menus = menuNames.split(" ");
            for(int i=0; i<menus.length;i++)
                menuList.add(new Menu(menus[i]));
            adapter.setCompanyList(menuList);
        } catch (JSONException e) {
            Toast.makeText(getActivity(),"Error showResult", Toast.LENGTH_SHORT).show();
        }
    }
}
