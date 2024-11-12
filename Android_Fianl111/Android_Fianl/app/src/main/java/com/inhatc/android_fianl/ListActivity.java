package com.inhatc.android_fianl;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.Toast;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

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
        import java.util.ArrayList;
        import java.util.HashMap;

        import javax.net.ssl.HttpsURLConnection;

public class ListActivity extends AppCompatActivity {

    final private String TAG = getClass().getSimpleName();

    BoardAdapter boardAdapter;
    ArrayList<BoardItem> boardItems;
    ListView listView;
    Button reg_button;
    String loginEmail;
    String loginUID;

    ArrayList<String> titleList = new ArrayList<>();

    ArrayList<String> seqList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        loginEmail = getIntent().getStringExtra("email");
        loginUID = getIntent().getStringExtra("UID");

        listView = findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(ListActivity.this, adapterView.getItemAtPosition(i)+ " 클릭", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                intent.putExtra("board_seq", seqList.get(i));
                intent.putExtra("email", loginEmail);
                intent.putExtra("UID", loginUID);
                startActivity(intent);

            }
        });
        boardItems = new ArrayList<>();

        getRegisterList();

        reg_button = findViewById(R.id.reg_button);

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ListActivity.this, RegisterActivity.class);
                intent.putExtra("email", loginEmail);
                intent.putExtra("UID", loginUID);
                startActivity(intent);
            }
        });
    }

    public void getRegisterList() {
        DatabaseReference db;
        FirebaseDatabase mfire = FirebaseDatabase.getInstance();

        db = mfire.getReference("board");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 기존 데이터 초기화
                int i = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String key = ds.getKey();
                    Log.e("키",key);
                    HashMap<String, HashMap<String, String>> value = (HashMap<String, HashMap<String, String>>) ds.getValue();
                    Log.e("정보",ds.getValue().toString());
                    String[] keys = value.keySet().toArray(new String[0]);
                    for(String key1 : keys){
                        String title = value.get(key1).get("title");
                        String content = value.get(key1).get("content");
                        String writer = value.get(key1).get("writer");
                        Log.e("에러",title+"|"+writer);
                        BoardItem item = new BoardItem(key, title, content, writer);
                        boardItems.add(item);
                    }

                }
                // 데이터 변경 후, Adapter에 데이터 변경을 알림
                boardAdapter = new BoardAdapter(ListActivity.this, boardItems);
                listView.setAdapter(boardAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Adapter 초기화 및 ListView에 Adapter 설정

    }


}