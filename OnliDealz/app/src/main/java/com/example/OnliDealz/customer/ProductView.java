package com.example.OnliDealz.customer;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

public class ProductView extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    TextView ErrorText, countTenders;
    ArrayList<ReviewMode> SubjectList = new ArrayList<>();
    ReviewAda blackJack;
    ReviewMode neverFails;
    RatingBar rQuality, rRating, rPrice, rValue;
    float fQuality, fRating, fPrice, fValue;
    View layer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Product Reviews");
        setContentView(R.layout.activity_product_view);
        ErrorText = findViewById(R.id.nothig);
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.recycler);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            neverFails = (ReviewMode) parent.getItemAtPosition(position);
            AlertDialog.Builder alert = new AlertDialog.Builder(ProductView.this);
            Rect reco = new Rect();
            Window win = ProductView.this.getWindow();
            win.getDecorView().getWindowVisibleDisplayFrame(reco);
            LayoutInflater inflater = (LayoutInflater) ProductView.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflater.inflate(R.layout.chania, null);
            layer.setMinimumWidth((int) (reco.width() * 0.9f));
            layer.setMinimumHeight((int) (reco.height() * 0.02f));
            rQuality = layer.findViewById(R.id.qualityRating);
            rPrice = layer.findViewById(R.id.pricePrice);
            rRating = layer.findViewById(R.id.ratingRating);
            rValue = layer.findViewById(R.id.valueValue);
            ReviewMode product = SubjectList.get(position);
            fQuality = Float.parseFloat(product.getQuality());
            fValue = Float.parseFloat(product.getValue());
            fPrice = Float.parseFloat(product.getPrice());
            fRating = Float.parseFloat(product.getRate());
            rQuality.setRating(fQuality);
            rValue.setRating(fValue);
            rPrice.setRating(fPrice);
            rRating.setRating(fRating);
            alert.setMessage("Customer: " + product.getName() + "\nReviewDate: " + product.getReg_date());
            alert.setView(layer);
            alert.setTitle("Reviews");
            alert.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
            alert.show();
        });
        practical();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.publicview,
                response -> {
                    try {
                        ReviewMode subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String reg = jsonObject.getString("reg");
                                String quality = jsonObject.getString("quality");
                                String rate = jsonObject.getString("rate");
                                String price = jsonObject.getString("price");
                                String value = jsonObject.getString("value");
                                String reference = jsonObject.getString("reference");
                                String customer = jsonObject.getString("customer");
                                String name = jsonObject.getString("name");
                                String phone = jsonObject.getString("phone");
                                String status = jsonObject.getString("status");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new ReviewMode(reg, quality, rate, price, value, reference, customer, name, phone, status, reg_date);
                                SubjectList.add(subject);
                            }  //product_id,category,type,description,imagery,quantity,price,stock
                            blackJack = new ReviewAda(ProductView.this, R.layout.named_man, SubjectList);
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