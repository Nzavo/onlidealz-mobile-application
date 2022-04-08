package com.example.OnliDealz.customer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.OnliDealz.Black.CartMod;
import com.example.OnliDealz.Black.CustomerMod;
import com.example.OnliDealz.Black.CustomerSess;
import com.example.OnliDealz.Black.DrivedAda;
import com.example.OnliDealz.Black.FamilyAda;
import com.example.OnliDealz.Black.GetPay;
import com.example.OnliDealz.Black.Travel;
import com.example.OnliDealz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyDelivery extends AppCompatActivity {
    CustomerMod customerMod;
    CustomerSess customerSess;
    ImageView imageView;
    ArrayList<GetPay> SubjectList = new ArrayList<>();
    RequestQueue requestQueue;
    GetPay neverFails;
    DrivedAda blackJack;
    Button button;
    ArrayList<CartMod> proxyCarts = new ArrayList<>();
    ListView lister;
    FamilyAda cartAdapter;
    String identity;
    View layer, layout;
    SearchView searchView;
    ListView listView;
    TextView ErrorText, countTenders, txtQual, txtPric, txtRat, txtVal, ErrorCheck, textReviews;
    RatingBar rQuality, rRating, rPrice, rValue;
    float fQuality, fRating, fPrice, fValue;
    EditText Comment, PriceTag;
    Button submit, button3;
    RatingBar ratingBar;
    float rateValue;
    EditText editMess;
    String ratig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Delivery & Ratings");
        setContentView(R.layout.activity_my_delivery);
        customerSess = new CustomerSess(getApplicationContext());
        customerMod = customerSess.getCustDetails();
        ErrorText = findViewById(R.id.nothig);
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.recycler);
        listView.setTextFilterEnabled(true);
        countTenders = findViewById(R.id.counterS);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            neverFails = (GetPay) parent.getItemAtPosition(position);
            GetPay product = SubjectList.get(position);
            identity = product.getPay_id();
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            Rect rect = new Rect();
            Window window = MyDelivery.this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            LayoutInflater layoutInflater = (LayoutInflater) MyDelivery.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = layoutInflater.inflate(R.layout.edit_ngumu, null);
            layout.setMinimumWidth((int) (rect.width() * 0.9f));
            layout.setMinimumHeight((int) (rect.height() * 0.08f));
            lister = layout.findViewById(R.id.listerS);
            lister.setTextFilterEnabled(true);
            practicalS();
            alert.setTitle("Delivery");
            alert.setView(layout);
            alert.setMessage("CustomerID: " + product.getCustomer_id() + "\nphone: " + product.getPhone() + "\nlocation: " + product.getLocation() + "\nstatus: " + product.getStatus() + "\nDate: " + product.getReg_date());
            if (product.getCustomer().equals("Pending")) {
                alert.setPositiveButton("Confirm & Feedback", (dialog, ids) -> {
                    AlertDialog.Builder alerty = new AlertDialog.Builder(MyDelivery.this);
                    Rect rectr = new Rect();
                    Window windowr = MyDelivery.this.getWindow();
                    windowr.getDecorView().getWindowVisibleDisplayFrame(rectr);
                    LayoutInflater layoutInflaterr = (LayoutInflater) MyDelivery.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    layout = layoutInflaterr.inflate(R.layout.rating, null);
                    layout.setMinimumWidth((int) (rectr.width() * 0.9f));
                    layout.setMinimumHeight((int) (rectr.height() * 0.2f));
                    ratingBar = layout.findViewById(R.id.myRate);
                    editMess = layout.findViewById(R.id.myMessage);
                    submit = layout.findViewById(R.id.Sub);
                    ratingBar.setOnRatingBarChangeListener((ratingBar, ved, b) -> {
                        rateValue = ratingBar.getRating();
                        if (rateValue <= 1 && rateValue > 0) {
                            ratig = String.valueOf(rateValue);
                        } else if (rateValue <= 2 && rateValue > 1) {
                            ratig = String.valueOf(rateValue);
                        } else if (rateValue <= 3 && rateValue > 2) {
                            ratig = String.valueOf(rateValue);
                        } else if (rateValue <= 4 && rateValue > 3) {
                            ratig = String.valueOf(rateValue);
                        } else if (rateValue <= 5 && rateValue > 4) {
                            ratig = String.valueOf(rateValue);
                        } else if (rateValue <= 6 && rateValue > 5) {
                            ratig = String.valueOf(rateValue);
                        } else if (rateValue <= 7 && rateValue > 6) {
                            ratig = String.valueOf(rateValue);
                        }
                    });
                    submit.setOnClickListener(t -> {
                        final String Texter = editMess.getText().toString().trim();
                        if (Texter.isEmpty()) {
                            Toast.makeText(MyDelivery.this, "Type some message", Toast.LENGTH_SHORT).show();
                        } else {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.cuistomeFeed,
                                    response -> {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            String msg = jsonObject.getString("message");
                                            int status = jsonObject.getInt("success");
                                            if (status == 1) {
                                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), MyDelivery.class));
                                                finish();
                                            } else if (status == 0) {
                                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                                        }
                                    }, error -> {
                                Toast.makeText(this, "There was a connection error", Toast.LENGTH_SHORT).show();

                            }) {
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("reg_id", product.getPay_id());
                                    params.put("customer", customerMod.getReg_id());
                                    params.put("phone", customerMod.getMobile());
                                    params.put("fullname", customerMod.getFullname());
                                    params.put("message", Texter);
                                    params.put("rate", ratig);
                                    return params;
                                }
                            };//reg_id,customer,phone,fullname,message,rate/sendMess
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.add(stringRequest);
                        }
                    });
                    alerty.setTitle("Rate us");
                    alerty.setView(layout);
                    AlertDialog alertDialog = alerty.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                });
            }
            if (product.getReview().equals("Pending")){
                alert.setNeutralButton("review", (dialog, ids) -> {
                    AlertDialog.Builder alerting = new AlertDialog.Builder(MyDelivery.this);
                    Rect rectr = new Rect();
                    Window window1 = MyDelivery.this.getWindow();
                    window1.getDecorView().getWindowVisibleDisplayFrame(rectr);
                    LayoutInflater layoutInflaterr = (LayoutInflater) MyDelivery.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    layer = layoutInflaterr.inflate(R.layout.hasira_zanini, null);
                    layer.setMinimumWidth((int) (rectr.width() * 0.9f));
                    layer.setMinimumHeight((int) (rectr.height() * 0.5f));
                    rQuality = layer.findViewById(R.id.qualityRating);
                    rPrice = layer.findViewById(R.id.pricePrice);
                    rRating = layer.findViewById(R.id.ratingRating);
                    rValue = layer.findViewById(R.id.valueValue);
                    txtRat = layer.findViewById(R.id.txtRating);
                    txtPric = layer.findViewById(R.id.txtPrice);
                    txtQual = layer.findViewById(R.id.txtQuality);
                    txtVal = layer.findViewById(R.id.txtValue);
                    button3 = layer.findViewById(R.id.btnReviewUS);
                    ErrorCheck = layer.findViewById(R.id.ErrorChecker);
                    alerting.setTitle("Give us a thumb");
                    alerting.setIcon(R.drawable.thumb);
                    alerting.setView(layer);
                    rQuality.setOnRatingBarChangeListener((ratingBar, vvv, b) -> {
                        fQuality = ratingBar.getRating();
                        if (fQuality <= 1 && fQuality > 0) {
                            txtQual.setText(String.valueOf(fQuality));
                        } else if (fQuality <= 2 && fQuality > 1) {
                            txtQual.setText(String.valueOf(fQuality));
                        } else if (fQuality <= 3 && fQuality > 2) {
                            txtQual.setText(String.valueOf(fQuality));
                        } else if (fQuality <= 4 && fQuality > 3) {
                            txtQual.setText(String.valueOf(fQuality));
                        } else if (fQuality <= 5 && fQuality > 4) {
                            txtQual.setText(String.valueOf(fQuality));
                        }
                    });
                    rRating.setOnRatingBarChangeListener((ratingBar, vvv, b) -> {
                        fRating = ratingBar.getRating();
                        if (fRating <= 1 && fRating > 0) {
                            txtRat.setText(String.valueOf(fRating));
                        } else if (fRating <= 2 && fRating > 1) {
                            txtRat.setText(String.valueOf(fRating));
                        } else if (fRating <= 3 && fRating > 2) {
                            txtRat.setText(String.valueOf(fRating));
                        } else if (fRating <= 4 && fRating > 3) {
                            txtRat.setText(String.valueOf(fRating));
                        } else if (fRating <= 5 && fRating > 4) {
                            txtRat.setText(String.valueOf(fRating));
                        }
                    });
                    rPrice.setOnRatingBarChangeListener((ratingBar, vvv, b) -> {
                        fPrice = ratingBar.getRating();
                        if (fPrice <= 1 && fPrice > 0) {
                            txtPric.setText(String.valueOf(fPrice));
                        } else if (fPrice <= 2 && fPrice > 1) {
                            txtPric.setText(String.valueOf(fPrice));
                        } else if (fPrice <= 3 && fPrice > 2) {
                            txtPric.setText(String.valueOf(fPrice));
                        } else if (fPrice <= 4 && fPrice > 3) {
                            txtPric.setText(String.valueOf(fPrice));
                        } else if (fPrice <= 5 && fPrice > 4) {
                            txtPric.setText(String.valueOf(fPrice));
                        }
                    });
                    rValue.setOnRatingBarChangeListener((ratingBar, vvv, b) -> {
                        fValue = ratingBar.getRating();
                        if (fValue <= 1 && fValue > 0) {
                            txtVal.setText(String.valueOf(fValue));
                        } else if (fValue <= 2 && fValue > 1) {
                            txtVal.setText(String.valueOf(fValue));
                        } else if (fValue <= 3 && fValue > 2) {
                            txtVal.setText(String.valueOf(fValue));
                        } else if (fValue <= 4 && fValue > 3) {
                            txtVal.setText(String.valueOf(fValue));
                        } else if (fValue <= 5 && fValue > 4) {
                            txtVal.setText(String.valueOf(fValue));
                        }
                    });
                    button3.setOnClickListener(veve -> {
                        if (rQuality.getRating() == 0 || rRating.getRating() == 0 || rPrice.getRating() == 0 || rValue.getRating() == 0) {
                            ErrorCheck.setText("Please Select each of the ratings above.");
                            ErrorCheck.setVisibility(View.VISIBLE);
                        } else {
                            ErrorCheck.setVisibility(View.GONE);
                            final String mQuality = txtQual.getText().toString().trim();
                            final String mRate = txtRat.getText().toString().trim();
                            final String mPrice = txtPric.getText().toString().trim();
                            final String mValue = txtVal.getText().toString().trim();
                            StringRequest stringRequesting = new StringRequest(Request.Method.POST, Travel.REVIEWS,
                                    respon -> {
                                        try {
                                            JSONObject jsonOb = new JSONObject(respon);
                                            Log.e("response ", respon);
                                            String msg = jsonOb.getString("message");
                                            int statuses = jsonOb.getInt("success");
                                            if (statuses == 1) {
                                                Toast.makeText(MyDelivery.this, msg, Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(getApplicationContext(), MyDelivery.class));

                                            } else if (statuses == 0) {
                                                ErrorCheck.setText(msg);
                                                ErrorCheck.setVisibility(View.VISIBLE);
                                                Toast.makeText(MyDelivery.this, msg, Toast.LENGTH_SHORT).show();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            ErrorCheck.setText("An error occurred.");
                                            ErrorCheck.setVisibility(View.VISIBLE);
                                            Toast.makeText(MyDelivery.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                        }

                                    }, error -> {
                                ErrorCheck.setText("Failed to connect.");
                                ErrorCheck.setVisibility(View.VISIBLE);
                                Toast.makeText(MyDelivery.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("quality", mQuality);
                                    params.put("rate", mRate);
                                    params.put("price", mPrice);
                                    params.put("value", mValue);
                                    params.put("reference", product.getPay_id());
                                    params.put("name", customerMod.fullname);
                                    params.put("phone", customerMod.mobile);
                                    params.put("customer", customerMod.reg_id);
                                    return params;
                                }//category,type,name,phone
                            };//quality,rate,price,value,product,customer
                            RequestQueue requestQueue = Volley.newRequestQueue(this);
                            requestQueue.add(stringRequesting);
                        }

                    });
                    alerting.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
                    AlertDialog alertDialog = alerting.create();
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                });
            }

            alert.setNegativeButton("close", (dialog, ids) -> {
                startActivity(new Intent(getApplicationContext(), MyDelivery.class));
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        });
        practical();
    }

    private void practicalS() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.listedCart,
                response -> {
                    try {
                        CartMod subject;
                        proxyCarts = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String reg = jsonObject.getString("reg");
                                String customer = jsonObject.getString("customer");
                                String production = jsonObject.getString("product");
                                String details = jsonObject.getString("details");
                                String price = jsonObject.getString("price");
                                String quantity = jsonObject.getString("quantity");
                                String cost = jsonObject.getString("cost");
                                String product_image = jsonObject.getString("image");
                                String imagery = "https://amtamwa.000webhostapp.com/onlidealz/images/" + product_image;
                                String status = jsonObject.getString("status");
                                String reg_date = jsonObject.getString("reg_date");
                                String quota = jsonObject.getString("total");
                                String sum_quant = jsonObject.getString("sum_quant");
                                subject = new CartMod(reg, customer, production, details, price, quantity, cost, imagery, status, reg_date, quota, sum_quant);
                                proxyCarts.add(subject);
                            }
                            cartAdapter = new FamilyAda(MyDelivery.this, R.layout.menem, proxyCarts);
                            lister.setAdapter(cartAdapter);

                        } else if (success == 0) {
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("identity", identity);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.getMyDeli,
                response -> {
                    try {
                        GetPay subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String pay_id = jsonObject.getString("pay_id");
                                String mpesa = jsonObject.getString("mpesa");
                                String orders = jsonObject.getString("orders");
                                String shipping = jsonObject.getString("shipping");
                                String discount = jsonObject.getString("discount");
                                String discounted = jsonObject.getString("discounted");
                                String original = jsonObject.getString("original");
                                String quantity = jsonObject.getString("quantity");
                                String customer_id = jsonObject.getString("customer_id");
                                String phone = jsonObject.getString("phone");
                                String delivery = jsonObject.getString("delivery");
                                String location = jsonObject.getString("location");
                                String address = jsonObject.getString("address");
                                String status = jsonObject.getString("status");
                                String disburse = jsonObject.getString("disburse");
                                String shipper = jsonObject.getString("shipper");
                                String shipment = jsonObject.getString("shipment");
                                String customer = jsonObject.getString("customer");
                                String review = jsonObject.getString("review");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new GetPay(pay_id, mpesa, orders, shipping, discount, discounted, original, quantity, customer_id, phone, delivery, location, address, status, disburse, shipper, shipment, customer, review, reg_date);
                                SubjectList.add(subject);
                            }  //product_id,category,type,description,imagery,quantity,price,stock
                            blackJack = new DrivedAda(MyDelivery.this, R.layout.shipped, SubjectList);
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
                        // ErrorText.setText("An Error Occurred");
                        ErrorText.setText(e.toString());
                        ErrorText.setVisibility(View.VISIBLE);
                    }

                }, error -> {
            // ErrorText.setText("Failed to connect");
            ErrorText.setText(error.toString());
            ErrorText.setVisibility(View.VISIBLE);

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("customer_id", customerMod.reg_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}