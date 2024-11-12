package com.inhatc.android_fianl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DetailActivity extends AppCompatActivity {

    final private String TAG = getClass().getSimpleName();

    TextView title_tv, content_tv, date_tv;
    LinearLayout comment_layout;
    EditText comment_et;
    Button reg_button;

    String board_seq = "";

    String userid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        board_seq = getIntent().getStringExtra("board_seq");
        userid = getIntent().getStringExtra("userid");

        title_tv = findViewById(R.id.title_tv);
        content_tv = findViewById(R.id.content_tv);
        date_tv = findViewById(R.id.date_tv);

        comment_layout = findViewById(R.id.comment_layout);
        comment_et = findViewById(R.id.comment_et);
        reg_button = findViewById(R.id.reg_button);

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegCmt regCmt = new RegCmt();
                regCmt.execute(userid, comment_et.getText().toString(), board_seq);
            }
        });

        InitData();

    }

    private void InitData(){

        LoadBoard loadBoard = new LoadBoard();
        loadBoard.execute(board_seq);

    }

    class LoadBoard extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d(TAG, "onPreExecute");
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, "onPostExecute, " + result);
            try {

                JSONArray jsonArray = null;
                jsonArray = new JSONArray(result);

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String title = jsonObject.optString("title");
                    String content = jsonObject.optString("content");
                    String crt_dt = jsonObject.optString("crt_dt");

                    title_tv.setText(title);
                    content_tv.setText(content);
                    date_tv.setText(crt_dt);

                }

                LoadCmt loadCmt = new LoadCmt();
                loadCmt.execute(board_seq);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        @Override
        protected String doInBackground(String... params) {

            String board_seq = params[0];

            String server_url = "http://15.164.252.136/load_board_detail.php";


            URL url;
            String response = "";
            try {
                url = new URL(server_url);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("board_seq", board_seq);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();
                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response+=line;
                    }
                }
                else {
                    response="";

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }
    }


    class LoadCmt extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d(TAG, "onPreExecute");
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, "onPostExecute, " + result);

            comment_layout.removeAllViews();

            try {

                JSONArray jsonArray = null;
                jsonArray = new JSONArray(result);

                LayoutInflater layoutInflater = LayoutInflater.from(DetailActivity.this);

                for(int i=0;i<jsonArray.length();i++){

                    View customView = layoutInflater.inflate(R.layout.custom_comment, null);
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String userid= jsonObject.optString("userid");
                    String content = jsonObject.optString("content");
                    String crt_dt = jsonObject.optString("crt_dt");

                    ((TextView)customView.findViewById(R.id.cmt_userid_tv)).setText(userid);
                    ((TextView)customView.findViewById(R.id.cmt_content_tv)).setText(content);
                    ((TextView)customView.findViewById(R.id.cmt_date_tv)).setText(crt_dt);

                    comment_layout.addView(customView);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        @Override
        protected String doInBackground(String... params) {

            String board_seq = params[0];
            String server_url = "http://15.164.252.136/load_cmt.php";


            URL url;
            String response = "";
            try {
                url = new URL(server_url);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("board_seq", board_seq);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();
                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response+=line;
                    }
                }
                else {
                    response="";

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }
    }

    class RegCmt extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d(TAG, "onPreExecute");
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, "onPostExecute, " + result);

            if(result.equals("success")){

                comment_et.setText("");

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(comment_et.getWindowToken(), 0);

                Toast.makeText(DetailActivity.this, "댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();

                LoadCmt loadCmt = new LoadCmt();
                loadCmt.execute(board_seq);
            }else
            {
                Toast.makeText(DetailActivity.this, result, Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String userid = params[0];
            String content = params[1];
            String board_seq = params[2];

            String server_url = "http://15.164.252.136/reg_comment.php";


            URL url;
            String response = "";
            try {
                url = new URL(server_url);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("userid", userid)
                        .appendQueryParameter("content", content)
                        .appendQueryParameter("board_seq", board_seq);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();
                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line=br.readLine()) != null) {
                        response+=line;
                    }
                }
                else {
                    response="";

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }
    }
}
