package com.jsstech.fetchingjsonserverpostdatausingvolley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView;
    List<UserModel> postList = new ArrayList<>();
    String url = "https://jsonplaceholder.typicode.com/posts";
    UserAdapter adapter;
    List<UserModel> filterList = new ArrayList<>();

    EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recv);
        search = findViewById(R.id.search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        GetData();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence,int i,int i1,int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence,int i,int i1,int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterList.clear();
                if (editable.toString().isEmpty()) {
                    recyclerView.setAdapter(new UserAdapter(getApplicationContext(), postList));
                    adapter.notifyDataSetChanged();
                } else {
                    Filter(editable.toString());
                }

            }
        });


    }



    private void Filter(String text) {
        for (UserModel post : postList) {
            if (Integer.toString(post.getUserId()).equals(text)) {
                filterList.add(post);
            }
        }
        recyclerView.setAdapter(new UserAdapter(getApplicationContext(), filterList));
        adapter.notifyDataSetChanged();

    }


    private void GetData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET,url,null,new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<=response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        postList.add(new UserModel(
                                jsonObject.getInt("id"),
                                jsonObject.getInt("userId"),
                                jsonObject.getString("title"),
                                jsonObject.getString("body"))

                        );
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }

                }

                adapter = new UserAdapter(getApplicationContext(), postList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

requestQueue.add(jsonArrayRequest);




    }
}