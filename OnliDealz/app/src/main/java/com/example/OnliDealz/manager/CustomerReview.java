package com.example.OnliDealz.manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class CustomerReview extends AppCompatActivity {
    View layer;
    SearchView searchView;
    ListView listView;
    TextView ErrorText, countTenders;
    EditText Comment, PriceTag;
    Spinner spinner;
    ArrayList<ReviewMode> SubjectList = new ArrayList<>();
    ReviewMode neverFails;
    ReviewAda blackJack;
    RatingBar rQuality, rRating, rPrice, rValue;
    float fQuality, fRating, fPrice, fValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Reviews");
        setContentView(R.layout.activity_customer_review);
        ErrorText = findViewById(R.id.nothig);
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.recycler);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            neverFails = (ReviewMode) parent.getItemAtPosition(position);
            AlertDialog.Builder alert = new AlertDialog.Builder(CustomerReview.this);
            Rect reco = new Rect();
            Window win = CustomerReview.this.getWindow();
            win.getDecorView().getWindowVisibleDisplayFrame(reco);
            LayoutInflater inflater = (LayoutInflater) CustomerReview.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflater.inflate(R.layout.chania, null);
            layer.setMinimumWidth((int) (reco.width() * 0.9f));
            layer.setMinimumHeight((int) (reco.height() * 0.02f));
            rQuality = layer.findViewById(R.id.qualityRating);
            rPrice = layer.findViewById(R.id.pricePrice);
            rRating = layer.findViewById(R.id.ratingRating);
            rValue = layer.findViewById(R.id.valueValue);
            ReviewMode product = SubjectList.get(position);
            alert.setTitle("OrderReference:+" + product.getReference());
            fQuality = Float.parseFloat(product.getQuality());
            fValue = Float.parseFloat(product.getValue());
            fPrice = Float.parseFloat(product.getPrice());
            fRating = Float.parseFloat(product.getRate());
            rQuality.setRating(fQuality);
            rValue.setRating(fValue);
            rPrice.setRating(fPrice);
            rRating.setRating(fRating);
            alert.setMessage("ReferenceID: " + product.getReference() + "\nCustomer: " + product.getName() + "\nPhone: " + product.getPhone() + "\nReviewDate: " + product.getReg_date());
            alert.setView(layer);
            alert.setTitle("Review: OrderReference: " + product.getReference());
            if (product.getStatus().equals("No")) {
                alert.setPositiveButton("Publish", (dialog, idm) -> {
                    StringRequest stringRequesting = new StringRequest(Request.Method.POST, Travel.updateRevi,
                            respon -> {
                                try {
                                    JSONObject jsonOb = new JSONObject(respon);
                                    Log.e("response ", respon);
                                    String msg = jsonOb.getString("message");
                                    int statuses = jsonOb.getInt("success");
                                    if (statuses == 1) {
                                        Toast.makeText(CustomerReview.this, msg, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), CustomerReview.class));

                                    } else if (statuses == 9) {
                                        PriceTag.setError(msg);
                                        PriceTag.requestFocus();
                                    } else if (statuses == 0) {
                                        Toast.makeText(CustomerReview.this, msg, Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(CustomerReview.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                }

                            }, error -> {
                        Toast.makeText(CustomerReview.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("reg", product.getReg());
                            params.put("status", "Yes");
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequesting);
                });
            } else {
                alert.setPositiveButton("keep_secret", (dialog, idm) -> {
                    StringRequest stringRequesting = new StringRequest(Request.Method.POST, Travel.updateRevi,
                            respon -> {
                                try {
                                    JSONObject jsonOb = new JSONObject(respon);
                                    Log.e("response ", respon);
                                    String msg = jsonOb.getString("message");
                                    int statuses = jsonOb.getInt("success");
                                    if (statuses == 1) {
                                        Toast.makeText(CustomerReview.this, msg, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), CustomerReview.class));

                                    } else if (statuses == 9) {
                                        PriceTag.setError(msg);
                                        PriceTag.requestFocus();
                                    } else if (statuses == 0) {
                                        Toast.makeText(CustomerReview.this, msg, Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(CustomerReview.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                }

                            }, error -> {
                        Toast.makeText(CustomerReview.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("reg", product.getReg());
                            params.put("status", "No");
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequesting);
                });
            }
            alert.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        });
        practical();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.seeView,
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
                            blackJack = new ReviewAda(CustomerReview.this, R.layout.named_man, SubjectList);
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