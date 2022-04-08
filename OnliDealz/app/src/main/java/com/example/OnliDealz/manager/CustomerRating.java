package com.example.OnliDealz.manager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.OnliDealz.Black.RatingAda;
import com.example.OnliDealz.Black.RatingMode;
import com.example.OnliDealz.Black.ReviewAda;
import com.example.OnliDealz.Black.ReviewMode;
import com.example.OnliDealz.Black.Travel;
import com.example.OnliDealz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomerRating extends AppCompatActivity {
    View layer;
    SearchView searchView;
    ListView listView;
    TextView ErrorText, countTenders;
    ArrayList<RatingMode> SubjectList = new ArrayList<>();
    RatingAda blackJack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Ratings");
        setContentView(R.layout.activity_customer_rating);
        ErrorText = findViewById(R.id.nothig);
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.recycler);
        listView.setTextFilterEnabled(true);
        practical();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.seeRating,
                response -> {
                    try {
                        RatingMode subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String customer = jsonObject.getString("customer");
                                String phone = jsonObject.getString("phone");
                                String fullname = jsonObject.getString("fullname");
                                String message = jsonObject.getString("message");
                                String rate = jsonObject.getString("rate");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new RatingMode(id,customer,phone,fullname,message,rate,reg_date);
                                SubjectList.add(subject);
                            }
                            blackJack = new RatingAda(CustomerRating.this, R.layout.absent, SubjectList);
                            listView.setAdapter(blackJack);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    blackJack.getFilter().filter(newText);
                                    return false;
                                }
                            });
                        } else if (success == 0) {
                            String msg = jsonObject.getString("mine");
                            ErrorText.setText(msg);
                            ErrorText.setVisibility(View.VISIBLE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        ErrorText.setText("An Error Occurred");
                        ErrorText.setVisibility(View.VISIBLE);
                    }

                }, error -> {
            ErrorText.setText("Failed to connect");
            ErrorText.setVisibility(View.VISIBLE);

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}