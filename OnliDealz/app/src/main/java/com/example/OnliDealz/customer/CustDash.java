package com.example.OnliDealz.customer;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.OnliDealz.Black.BlackJack;
import com.example.OnliDealz.Black.CustMessAda;
import com.example.OnliDealz.Black.CustomerMod;
import com.example.OnliDealz.Black.CustomerSess;
import com.example.OnliDealz.Black.MassagingAda;
import com.example.OnliDealz.Black.MessagingMode;
import com.example.OnliDealz.Black.NeverFails;
import com.example.OnliDealz.Black.Travel;
import com.example.OnliDealz.MainActivity;
import com.example.OnliDealz.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CustDash extends AppCompatActivity implements View.OnClickListener {
    Vibrator vibrator;
    CustomerMod customerMod;
    CustomerSess customerSess;
    BottomNavigationView bottomNavigationView;
    CardView tv, kitch, bed, live, bath;
    String category;
    TextView Promise, ErrorText, Description, PriceTag, txtQual, txtPric, txtRat, ErrorCheck, textReviews;
    ArrayList<NeverFails> SubjectList = new ArrayList<>();
    NeverFails neverFails;
    BlackJack blackJack;
    View layer;
    SearchView searchView;
    EditText editText;
    ListView listView;
    ImageView imageView;
    Button button, button2, button3, buttoner, btnChatting;
    //float quant, system;
    CharSequence charSequence;
    int index;
    long delay = 250;
    Handler handler = new Handler();
    Spinner user;
    ImageButton imageButton;
    EditText message;
    TextView textView, Converse;
    RatingBar ratingBar;
    float ratings;
    RecyclerView recyclerView;
    private CustMessAda custMessAda;
    private List<MessagingMode> list;
    private MassagingAda vccAd;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerSess = new CustomerSess(getApplicationContext());
        customerMod = customerSess.getCustDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + customerMod.fullname);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_cust_dash);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        tv = findViewById(R.id.tvSet);
        kitch = findViewById(R.id.kitchen);
        bed = findViewById(R.id.bedRoom);
        live = findViewById(R.id.living);
        bath = findViewById(R.id.bathRoom);
        buttoner = findViewById(R.id.prodRev);
        textReviews = findViewById(R.id.reviews);
        btnChatting = findViewById(R.id.cusChat);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.homing);
        tv.setOnClickListener(this);
        kitch.setOnClickListener(this);
        bed.setOnClickListener(this);
        live.setOnClickListener(this);
        bath.setOnClickListener(this);
        btnChatting.setOnClickListener(v -> {
            chatMe(this);
            ViewReply();
        });
        buttoner.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), MyDelivery.class));
        });
        textReviews.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), ProductView.class));
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.cart:
                    startActivity(new Intent(getApplicationContext(), Cart.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.homing:
                    vibrator.vibrate(500);
                    return true;
                case R.id.history:
                    CharSequence[] items = {"Payment History", "Receipt"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(CustDash.this);
                    dialog.setTitle("Archives");
                    dialog.setIcon(R.drawable.history);
                    dialog.setItems(items, (dialog1, itemer) -> {
                        if (itemer == 0) {
                            startActivity(new Intent(getApplicationContext(), PaymentHistory.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), Receipt.class));
                        }
                        finish();
                    });
                    dialog.setNegativeButton("close", (dialog1, itemer) -> dialog1.cancel());
                    final AlertDialog alert = dialog.create();
                    alert.setCanceledOnTouchOutside(false);
                    alert.show();
                    alert.getWindow().setGravity(Gravity.TOP);
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    private void chatMe(CustDash custDash) {
        AlertDialog.Builder alarmed = new AlertDialog.Builder(custDash);
        Rect rec = new Rect();
        Window window = custDash.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rec);
        LayoutInflater inflat = (LayoutInflater) custDash.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layer = inflat.inflate(R.layout.demo_mess, null);
        layer.setMinimumWidth((int) (rec.width() * 0.9f));
        layer.setMinimumHeight((int) (rec.height() * 0.19f));
        user = layer.findViewById(R.id.spinner);
        imageButton = layer.findViewById(R.id.submit_button1);
        textView = layer.findViewById(R.id.rateCount);
        message = layer.findViewById(R.id.reviewing);
        Converse = layer.findViewById(R.id.converse);
        ratingBar = layer.findViewById(R.id.ratingBar);
        recyclerView = layer.findViewById(R.id.britLitovsk);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Contact, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        user.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        message.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ourMess = message.getText().toString();
                if (ourMess.isEmpty()) {
                    ratingBar.setVisibility(View.GONE);
                } else {
                    ratingBar.setVisibility(View.VISIBLE);
                }
            }

            public void afterTextChanged(Editable s) {
            }
        });
        ratingBar.setOnRatingBarChangeListener((ratingBar, vvv, b) -> {
            ratings = ratingBar.getRating();
            if (ratings <= 1 && ratings > 0) {
                textView.setText(String.valueOf(ratings));
            } else if (ratings <= 2 && ratings > 1) {
                textView.setText(String.valueOf(ratings));
            } else if (ratings <= 3 && ratings > 2) {
                textView.setText(String.valueOf(ratings));
            } else if (ratings <= 4 && ratings > 3) {
                textView.setText(String.valueOf(ratings));
            } else if (ratings <= 5 && ratings > 4) {
                textView.setText(String.valueOf(ratings));
            }
        });
        imageButton.setOnClickListener(v -> {
            final String myStringer = textView.getText().toString().trim();
            final String myMessage = message.getText().toString().trim();
            final String myContact = user.getSelectedItem().toString().trim();
            if (myContact.equals("Search Contact Here")) {
                Toast.makeText(custDash, "You did not select your receiver", Toast.LENGTH_SHORT).show();
            } else if (myStringer.equals("0")) {
                Toast.makeText(custDash, "You have not checked the ratingBars", Toast.LENGTH_SHORT).show();
            } else if (myMessage.isEmpty()) {
                Toast.makeText(custDash, "Please type some message", Toast.LENGTH_SHORT).show();
            } else {
                StringRequest stringRequesting = new StringRequest(Request.Method.POST, Travel.chatBig,
                        respon -> {
                            try {
                                JSONObject jsonOb = new JSONObject(respon);
                                Log.e("response ", respon);
                                String msg = jsonOb.getString("message");
                                int statuses = jsonOb.getInt("success");
                                if (statuses == 1) {
                                    Toast.makeText(CustDash.this, msg, Toast.LENGTH_LONG).show();
                                    Toast.makeText(getApplicationContext(), "Message received by\n System(" + myContact + ")", Toast.LENGTH_LONG).show();
                                    message.setText("");
                                    textView.setText("");
                                    ratingBar.setRating(0);
                                    user.setSelection(0);
                                    ViewReply();
                                } else if (statuses == 0) {
                                    Toast.makeText(CustDash.this, msg, Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(CustDash.this, "An error occurred", Toast.LENGTH_SHORT).show();
                            }

                        }, error -> {
                    Toast.makeText(CustDash.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("rate", myStringer);
                        params.put("message", myMessage);
                        params.put("receiver", myContact);
                        params.put("sender", customerMod.reg_id);
                        params.put("name", customerMod.fullname);
                        params.put("phone", customerMod.mobile);
                        params.put("post", "Customer");
                        return params;
                    }//rate,message,receiver,sender,name,phone,post
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequesting);
            }
        });
        alarmed.setView(layer);
        alarmed.setTitle("Chat With Us");
        alarmed.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
        AlertDialog alertDialog = alarmed.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    private void ViewReply() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.houseFeed,
                response -> {
                    try {
                        Log.e("RESPONSE ", response);
                        JSONObject jsonObject = new JSONObject(response);
                        int status = jsonObject.getInt("status");
                        if (status == 1) {
                            list.clear();
                            JSONArray jsonArray = jsonObject.getJSONArray("getfeedback");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsn = jsonArray.getJSONObject(i);
                                String id = jsn.getString("id");
                                String rate = jsn.getString("rate");
                                String message = jsn.getString("message");
                                String receiver = jsn.getString("receiver");
                                String sender = jsn.getString("sender");
                                String name = jsn.getString("name");
                                String phone = jsn.getString("phone");
                                String post = jsn.getString("post");
                                String send_date = jsn.getString("send_date");
                                String reply = jsn.getString("reply");
                                String reply_date = jsn.getString("reply_date");
                                MessagingMode vccM = new MessagingMode(id, rate, message, receiver, sender, name, phone, post, send_date, reply, reply_date);
                                list.add(vccM);

                            }
                            vccAd = new MassagingAda(getApplicationContext(), list);
                            recyclerView.setAdapter(vccAd);
                            recyclerView.scrollToPosition(list.size() - 1);
                            Converse.setVisibility(View.GONE);

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        Converse.setVisibility(View.VISIBLE);
                    }

                }, error -> {
            Converse.setVisibility(View.VISIBLE);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sender", customerMod.getReg_id());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user, menu);
        return true;
    }

    @SuppressLint({"NonConstantResourceId", "MissingPermission"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.myProf:
                CharSequence[] items = {"My Profile", "Change Password", "SignOut", "Exit App"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(CustDash.this);
                dialog.setTitle("My Profile");
                dialog.setIcon(R.drawable.person);
                dialog.setItems(items, (dialog1, itemer) -> {
                    if (itemer == 0) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(CustDash.this);
                        mydialog1.setTitle("Profile");
                        mydialog1.setMessage("Registry: " + customerMod.reg_id + "\nName: " + customerMod.fullname + "\nUsername: " + customerMod.username + "\nEmail: " + customerMod.email + "\nPhone: " + customerMod.mobile + "\nCity: " + customerMod.address + "\nStatus: " + customerMod.approval + "\nDate: " + customerMod.reg_date);
                        mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                        final AlertDialog alertDialog = mydialog1.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    } else if (itemer == 1) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(CustDash.this);
                        mydialog1.setTitle("Change Password");
                        mydialog1.setMessage("Change");
                        mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                        final AlertDialog alertDialog = mydialog1.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    } else if (itemer == 2) {
                        customerSess.signOutCust();
                        vibrator.vibrate(400);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    } else if (itemer == 3) {
                        vibrator.vibrate(500);
                        finishAffinity();
                    }
                });
                dialog.setNegativeButton("close", (dialog1, itemer) -> dialog1.cancel());
                final AlertDialog alertDialog = dialog.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
                alertDialog.getWindow().setGravity(Gravity.TOP);
                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View item) {
        switch (item.getId()) {
            case R.id.tvSet:
                category = "TV Audio & Videos";
                tryHard(this);
                break;
            case R.id.kitchen:
                category = "Kitchen Supplies";
                tryHard(this);
                break;
            case R.id.bedRoom:
                category = "Bedroom Supplies";
                tryHard(this);
                break;
            case R.id.living:
                category = "Living Room";
                tryHard(this);
                break;
            case R.id.bathRoom:
                category = "Bathroom Supplies";
                tryHard(this);
                break;
            default:
        }
    }

    private void tryHard(CustDash custDash) {
        AlertDialog.Builder alarmed = new AlertDialog.Builder(this);
        Rect rec = new Rect();
        Window window = custDash.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rec);
        LayoutInflater inflat = (LayoutInflater) custDash.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layer = inflat.inflate(R.layout.view_category, null);
        layer.setMinimumWidth((int) (rec.width() * 0.9f));
        layer.setMinimumHeight((int) (rec.height() * 0.9f));
        ErrorText = layer.findViewById(R.id.texters);
        searchView = layer.findViewById(R.id.search);
        listView = layer.findViewById(R.id.recycler);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            neverFails = (NeverFails) parent.getItemAtPosition(position);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            Rect reco = new Rect();
            Window win = custDash.getWindow();
            win.getDecorView().getWindowVisibleDisplayFrame(reco);
            LayoutInflater inflater = (LayoutInflater) custDash.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflater.inflate(R.layout.view_handle, null);
            layer.setMinimumWidth((int) (reco.width() * 0.9f));
            layer.setMinimumHeight((int) (reco.height() * 0.5f));
            Description = layer.findViewById(R.id.totalDesc);
            PriceTag = layer.findViewById(R.id.itemPrice);
            imageView = layer.findViewById(R.id.imagery);
            button = layer.findViewById(R.id.btnBuyNow);
            //button2 = layer.findViewById(R.id.btnReview);
            NeverFails product = SubjectList.get(position);
            Glide.with(custDash).load(product.getImage()).into(imageView);
            Description.setText("Type: " + product.getType() + "\nProduct Details: " + product.getDescription());
            PriceTag.setText("KES " + product.getPrice());
            button.setOnClickListener(vm -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                Rect rect = new Rect();
                Window window1 = custDash.getWindow();
                window1.getDecorView().getWindowVisibleDisplayFrame(rect);
                LayoutInflater layoutInflater = (LayoutInflater) custDash.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layer = layoutInflater.inflate(R.layout.eleventh_hr, null);
                layer.setMinimumWidth((int) (rect.width() * 0.9f));
                layer.setMinimumHeight((int) (rect.height() * 0.1f));
                editText = layer.findViewById(R.id.myQuantity);
                Promise = layer.findViewById(R.id.texting);
                button2 = layer.findViewById(R.id.btnCart);
                animateText("7.25% Discount for goods above KES30,500");
                ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                        Promise,
                        PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                        PropertyValuesHolder.ofFloat("scaleY", 1.2f)
                );
                objectAnimator.setDuration(500);
                objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
                objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
                objectAnimator.start();
                ErrorCheck = layer.findViewById(R.id.ErrorChecker);
                button2.setOnClickListener(on -> {
                    final String MyQuanty = editText.getText().toString().trim();
                    if (MyQuanty.isEmpty()) {
                        ErrorCheck.setText("There is a Required Field");
                        ErrorCheck.setVisibility(View.VISIBLE);
                        editText.requestFocus();
                    } else {
                        ErrorCheck.setVisibility(View.GONE);
                        StringRequest stringRequesting = new StringRequest(Request.Method.POST, Travel.CART_ADD,
                                respon -> {
                                    try {
                                        JSONObject jsonOb = new JSONObject(respon);
                                        Log.e("response ", respon);
                                        String msg = jsonOb.getString("message");
                                        int statuses = jsonOb.getInt("success");
                                        if (statuses == 1) {
                                            Toast.makeText(CustDash.this, msg, Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), Cart.class));

                                        } else if (statuses == 0) {
                                            ErrorCheck.setText(msg);
                                            ErrorCheck.setVisibility(View.VISIBLE);
                                            Toast.makeText(CustDash.this, msg, Toast.LENGTH_SHORT).show();
                                        } else if (statuses == 9) {
                                            ErrorCheck.setText(msg);
                                            ErrorCheck.setVisibility(View.VISIBLE);
                                            editText.setError(msg);
                                            editText.requestFocus();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        ErrorCheck.setText("An error occurred");
                                        ErrorCheck.setVisibility(View.VISIBLE);
                                        Toast.makeText(CustDash.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                    }

                                }, error -> {
                            ErrorCheck.setText("Failed to connect");
                            ErrorCheck.setVisibility(View.VISIBLE);
                            Toast.makeText(CustDash.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("quantity", MyQuanty);
                                params.put("quant", product.getQuantity());
                                params.put("price", product.getPrice());
                                params.put("product", product.getProduct_id());
                                params.put("details", product.getCategory() + "~" + product.getType());
                                params.put("customer", customerMod.reg_id);
                                return params;
                            }
                        };//customer,product,price,quantity,quant
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequesting);
                    }
                });
                builder.setTitle(product.getCategory());
                builder.setView(layer);
                builder.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            });
            alert.setTitle(product.getCategory());
            alert.setView(layer);
            alert.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.getWindow().setGravity(Gravity.BOTTOM);

        });
        getCategory();
        alarmed.setTitle(category);
        alarmed.setView(layer);
        alarmed.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
        AlertDialog alertDialog = alarmed.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setGravity(Gravity.TOP);
    }

    Runnable runnabl = new Runnable() {
        @Override
        public void run() {
            Promise.setText(charSequence.subSequence(0, index++));
            if (index <= charSequence.length()) {
                handler.postDelayed(runnabl, delay);
            }
        }
    };

    public void animateText(CharSequence cs) {
        charSequence = cs;
        index = 0;
        Promise.setText("");
        handler.removeCallbacks(runnabl);
        handler.postDelayed(runnabl, delay);
    }

    private void getCategory() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.GETPROD,
                response -> {
                    try {

                        NeverFails subject;
                        SubjectList = new ArrayList<>();

                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String product_id = jsonObject.getString("product_id");
                                String category = jsonObject.getString("category");
                                String type = jsonObject.getString("type");
                                String description = jsonObject.getString("description");
                                String product_image = jsonObject.getString("image");
                                String imagery = "https://amtamwa.000webhostapp.com/onlidealz/images/" + product_image;
                                String quantity = jsonObject.getString("quantity");
                                String price = jsonObject.getString("price");
                                String stock = jsonObject.getString("stock");
                                subject = new NeverFails(product_id, category, type, description, imagery, quantity, price, stock);
                                SubjectList.add(subject);
                            }  //product_id,category,type,description,imagery,quantity,price,stock
                            blackJack = new BlackJack(CustDash.this, R.layout.day_and_night, SubjectList);
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
                        //ErrorText.setText("An Error Occurred");
                        ErrorText.setText(e.toString());
                        ErrorText.setVisibility(View.VISIBLE);
                    }

                }, error -> {
            ErrorText.setText("Failed to connect");
            ErrorText.setVisibility(View.VISIBLE);

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("category", category);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}