package songjong.com.seongnamgiftcard.CompanyFragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.UUID;

import songjong.com.seongnamgiftcard.Activity.MainActivity;
import songjong.com.seongnamgiftcard.Adapter.CompanyReviewAdapter;
import songjong.com.seongnamgiftcard.FieldClass.Review;
import songjong.com.seongnamgiftcard.R;

public class ReviewFragment extends Fragment {
    private static final String TAG = "ReviewFragment";
    private static final String TAG_JSON="reviewResult";
    private static final String TAG_SUCCESS ="isSuccess";
    private static boolean success = false;
    private Button btnAddReview;
    private ListView lvReview;
    private ArrayList<Review> dataReviewList = new ArrayList<Review>();
    private CompanyReviewAdapter companyReviewAdapter;
    private EditText etContents;
    private String contents = null;
    private String mJsonString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.company_fragment_review, container, false);

        lvReview = (ListView) v.findViewById(R.id.lvList);
        companyReviewAdapter = new CompanyReviewAdapter(getActivity(), dataReviewList);

        lvReview.setAdapter(companyReviewAdapter);
        etContents = (EditText) v.findViewById(R.id.etContents);
        btnAddReview = (Button) v.findViewById(R.id.btnAddItem);

        btnAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contents = etContents.getText().toString();
                if(contents.length() < 10)
                    Toast.makeText(getActivity(),  "내용은 최소 10글자 이상 입력하셔야 합니다.", Toast.LENGTH_SHORT).show();
                else {
                    //reveiw 추가 부분
                    Review review = new Review();
                    review.setContents(contents);
                    insertReview(review.getContents(), MainActivity.uuid);
                    if(success){
                        Toast.makeText(getActivity(),  "댓글 추가 성공", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),  "댓글은 1시간 간격으로 작성할 수 있습니다.", Toast.LENGTH_SHORT).show();
                    }
                    etContents.setText(null);
                    companyReviewAdapter.notifyDataSetChanged();
                }
            }
        });
        return v;
    }
    public void insertReview(String contents, String deviceUUID) {
            GetData task = new GetData();
            task.execute("http://13.124.195.13/insertReview.php",contents, deviceUUID);
    }
    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), "리뷰 삽입 중입니다. 잠시만 기다려주세요", null, true, true);

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
                success = isSuccess();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = params[0];
            String contents = params[1];
            String uuid = params[2];
            String postParameters = "contents="+contents + "&uuid="+uuid ;
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
    private boolean isSuccess(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject item = jsonArray.getJSONObject(i);
                String success = item.getString(TAG_SUCCESS);
                if(success =="fail")
                    return false;
                else
                    return true;
            }
        } catch (JSONException e) {
            Toast.makeText(getActivity(),"Error showResult", Toast.LENGTH_SHORT).show();
        }
    }
}

