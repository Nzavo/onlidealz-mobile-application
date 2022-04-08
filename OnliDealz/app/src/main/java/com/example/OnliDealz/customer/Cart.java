package com.example.OnliDealz.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.OnliDealz.Black.CartAdapter;
import com.example.OnliDealz.Black.CartMod;
import com.example.OnliDealz.Black.CustomerMod;
import com.example.OnliDealz.Black.CustomerSess;
import com.example.OnliDealz.Black.Travel;
import com.example.OnliDealz.MainActivity;
import com.example.OnliDealz.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Cart extends AppCompatActivity {
    Vibrator vibrator;
    CustomerMod customerMod;
    CustomerSess customerSess;
    BottomNavigationView bottomNavigationView;
    TextView textView;
    ListView listView;
    View layer;
    SearchView searchView;
    TextView ErrorText, reportErrors;
    EditText Area, Street, House, MPESA;
    Spinner spinner;
    RelativeLayout relativeLayout;
    ImageView imageView;
    ArrayList<CartMod> SubjectList = new ArrayList<>();
    CartMod neverFails;
    CartAdapter blackJack;
    Button button1, button2, button3, button4;
    float shipper;
    double total, disc, newCharge, original;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerSess = new CustomerSess(getApplicationContext());
        customerMod = customerSess.getCustDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + customerMod.fullname);
        setContentView(R.layout.activity_cart);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.cart);
        textView = findViewById(R.id.reviews);
        ErrorText = findViewById(R.id.nothig);
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.recycler);
        button2 = findViewById(R.id.btnContinue);
        button1 = findViewById(R.id.btnCheck);
        listView.setTextFilterEnabled(true);
        getTender();
        myCart();
        button2.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), CustDash.class));
            finish();
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.cart:
                    vibrator.vibrate(500);
                    return true;
                case R.id.homing:
                    startActivity(new Intent(getApplicationContext(), CustDash.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.history:
                    CharSequence[] items = {/*"Orders", */"Receipt"};
                    builder = new AlertDialog.Builder(Cart.this);
                    builder.setTitle("Files");
                    builder.setIcon(R.drawable.history);
                    builder.setItems(items, (dialog1, itemer) -> {
                        if (itemer == 0) {
                            startActivity(new Intent(getApplicationContext(), Receipt.class));
                        } else {
                          //  startActivity(new Intent(getApplicationContext(), Receipt.class));
                        }
                        finish();
                    });
                    builder.setNegativeButton("close", (dialog1, itemer) -> dialog1.cancel());
                    final AlertDialog alert = builder.create();
                    alert.setCanceledOnTouchOutside(false);
                    alert.show();
                    alert.getWindow().setGravity(Gravity.TOP);
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    private void myCart() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.GET_CART,
                response -> {
                    try {
                        CartMod subject;
                        SubjectList = new ArrayList<>();
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
                                SubjectList.add(subject);
                                shipper = Float.parseFloat(quota);
                                if (shipper > 30500) {
                                    disc = shipper * 0.0725;
                                } else {
                                    disc = 0;
                                }
                                newCharge = shipper - disc;
                                original = shipper + 300;
                                total = newCharge + 300;
                                //String.format("%.2f", newCharge);
                                String current = String.format("%.2f", newCharge);
                                String noDisc = String.valueOf(original);
                                String discounted = String.format("%.2f", total);
                                String discAmt = String.format("%.2f", disc);
                                button1.setOnClickListener(v -> {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(Cart.this);
                                    builder.setTitle("Order Summary");
                                    builder.setMessage("Cart Subtotal: KES" + quota + "\n\nDisc.Allowed: -(KES" + discAmt + ")\n\nNewCharge: KES" + current);
                                    builder.setPositiveButton("ORDER", (dialog, item) -> {
                                        CharSequence[] items = {"Include Shipping", "No Shipping"};
                                        AlertDialog.Builder max = new AlertDialog.Builder(Cart.this);
                                        max.setTitle("Select Option");
                                        max.setItems(items, (dialog1, ite) -> {
                                            if (ite == 0) {
                                                AlertDialog.Builder bb = new AlertDialog.Builder(this);
                                                Rect rect = new Rect();
                                                Window window1 = Cart.this.getWindow();
                                                window1.getDecorView().getWindowVisibleDisplayFrame(rect);
                                                LayoutInflater layoutInflater = (LayoutInflater) Cart.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                layer = layoutInflater.inflate(R.layout.location, null);
                                                layer.setMinimumWidth((int) (rect.width() * 0.9f));
                                                layer.setMinimumHeight((int) (rect.height() * 0.1f));
                                                Area = layer.findViewById(R.id.enterArea);
                                                Street = layer.findViewById(R.id.enterStreet);
                                                House = layer.findViewById(R.id.enterHouse);
                                                spinner = layer.findViewById(R.id.editCurrentCity);
                                                button3 = layer.findViewById(R.id.btnNext);
                                                relativeLayout = layer.findViewById(R.id.relative);
                                                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Cart.this, R.array.Current, android.R.layout.simple_spinner_item);
                                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                spinner.setAdapter(adapter);
                                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                    @Override
                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                        String mSpinner = spinner.getSelectedItem().toString();
                                                        if (mSpinner.equals("Select Current City")) {
                                                            relativeLayout.setVisibility(View.GONE);
                                                        } else {
                                                            relativeLayout.setVisibility(View.VISIBLE);
                                                        }
                                                    }

                                                    @Override
                                                    public void onNothingSelected(AdapterView<?> parent) {

                                                    }
                                                });
                                                button3.setOnClickListener(view -> {
                                                    final String mySpinner = spinner.getSelectedItem().toString().trim();
                                                    final String myArea = Area.getText().toString().trim();
                                                    final String myStreet = Street.getText().toString().trim();
                                                    final String myHouse = House.getText().toString().trim();
                                                    if (myArea.isEmpty()) {
                                                        Toast.makeText(this, "Please enter your current Area", Toast.LENGTH_SHORT).show();
                                                        Area.setError("required");
                                                        Area.requestFocus();
                                                    } else if (myStreet.isEmpty()) {
                                                        Toast.makeText(this, "Please name of your current street", Toast.LENGTH_SHORT).show();
                                                        Street.setError("required");
                                                        Street.requestFocus();
                                                    } else if (myHouse.isEmpty()) {
                                                        Toast.makeText(this, "Please your House or Apartment number", Toast.LENGTH_SHORT).show();
                                                        House.setError("required");
                                                        House.requestFocus();
                                                    } else {
                                                        String Location = "Area~ " + myArea + "~ Street~ " + myStreet + "~ House~ " + myHouse;
                                                        AlertDialog.Builder br = new AlertDialog.Builder(Cart.this);
                                                        br.setTitle("Payment");
                                                        br.setMessage("Cart Subtotal: KES" + quota + "\n\nDisc.Allowed: -(KES" + discAmt + ")\n\nNewCharge: KES" + current + "\n\nShipping: +(KES300)\n\nOrder Total: KES" + discounted + "\n\nGo to MPESA & select \nLipa na M-PESA. \nPayBill: 888880\nAccount no: (your Reg_Serial)" + customerMod.reg_id + "\nEnter Mpesa CODE in the Next pop up");
                                                        br.setPositiveButton("submit", (submerge, yes) -> {
                                                            AlertDialog.Builder babel = new AlertDialog.Builder(this);
                                                            Rect retan = new Rect();
                                                            Window window8 = Cart.this.getWindow();
                                                            window8.getDecorView().getWindowVisibleDisplayFrame(retan);
                                                            LayoutInflater layoutInflater1 = (LayoutInflater) Cart.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                            layer = layoutInflater1.inflate(R.layout.mpesa, null);
                                                            layer.setMinimumWidth((int) (retan.width() * 0.9f));
                                                            layer.setMinimumHeight((int) (retan.height() * 0.1f));
                                                            MPESA = layer.findViewById(R.id.mpesa);
                                                            reportErrors = layer.findViewById(R.id.errorMark);
                                                            button4 = layer.findViewById(R.id.btnSubmit);
                                                            button4.setOnClickListener(veve -> {
                                                                final String muCode = MPESA.getText().toString().trim();
                                                                int len = muCode.length();
                                                                if (muCode.isEmpty()) {
                                                                    reportErrors.setText("Mpesa Code needed");
                                                                    reportErrors.setVisibility(View.VISIBLE);
                                                                } else if (len < 10) {
                                                                    reportErrors.setText("Ooops! Your Mpesa code is invalid");
                                                                    reportErrors.setVisibility(View.VISIBLE);
                                                                } else {
                                                                    reportErrors.setVisibility(View.GONE);
                                                                    StringRequest stringRequesting = new StringRequest(Request.Method.POST, Travel.PAY_DENI,
                                                                            respon -> {
                                                                                try {
                                                                                    JSONObject jsonOb = new JSONObject(respon);
                                                                                    Log.e("response ", respon);
                                                                                    String msg = jsonOb.getString("message");
                                                                                    int statuses = jsonOb.getInt("success");
                                                                                    if (statuses == 1) {
                                                                                        Toast.makeText(Cart.this, msg, Toast.LENGTH_LONG).show();
                                                                                        startActivity(new Intent(getApplicationContext(), Cart.class));

                                                                                    } else if (statuses == 0) {
                                                                                        reportErrors.setText(msg);
                                                                                        reportErrors.setVisibility(View.VISIBLE);
                                                                                        Toast.makeText(Cart.this, msg, Toast.LENGTH_SHORT).show();
                                                                                    }

                                                                                } catch (JSONException e) {
                                                                                    e.printStackTrace();
                                                                                    reportErrors.setText("An error occurred");
                                                                                    reportErrors.setVisibility(View.VISIBLE);
                                                                                    Toast.makeText(Cart.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                                                                }

                                                                            }, error -> {
                                                                        reportErrors.setText("Failed to connect");
                                                                        reportErrors.setVisibility(View.VISIBLE);
                                                                        Toast.makeText(Cart.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                                                                    }) {
                                                                        @Override
                                                                        protected Map<String, String> getParams() {
                                                                            Map<String, String> params = new HashMap<>();
                                                                            params.put("quantity", sum_quant);
                                                                            params.put("orders", current);
                                                                            params.put("shipping", "300");
                                                                            params.put("discount", discAmt);
                                                                            params.put("discounted", discounted);
                                                                            params.put("original", noDisc);
                                                                            params.put("delivery", "Pending");
                                                                            params.put("location", Location);
                                                                            params.put("address", mySpinner);
                                                                            params.put("mpesa", muCode);
                                                                            params.put("customer_id", customerMod.reg_id);
                                                                            params.put("phone", customerMod.mobile);
                                                                            params.put("disburse", "Pending");
                                                                            params.put("shipper", "Pending");
                                                                            params.put("shipment", "Pending");
                                                                            params.put("customer", "Pending");
                                                                            return params;
                                                                        }
                                                                    };//quantity,order,shipping,total,location,mpesa,customer,phone,reg
                                                                    //disburse,shipper,shipment,customer
                                                                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                                                                    requestQueue.add(stringRequesting);
                                                                }
                                                            });
                                                            babel.setTitle("Submit Payment Details");
                                                            babel.setView(layer);
                                                            babel.setNegativeButton("close", (shoot, boom) -> shoot.cancel());
                                                            AlertDialog alertDialog = babel.create();
                                                            alertDialog.show();
                                                            alertDialog.setCanceledOnTouchOutside(false);
                                                            alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                                                        });
                                                        br.setNegativeButton("close", (submerge, yes) -> submerge.cancel());
                                                        AlertDialog alertDialog = br.create();
                                                        alertDialog.show();
                                                        alertDialog.setCanceledOnTouchOutside(false);
                                                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                                                    }
                                                });
                                                bb.setTitle("Shipped Order");
                                                bb.setMessage("Shipping (Flat-Rate) Fixed: KES 300\n\nShipping within Nairobi is 2 hrs\nAreas outside Nairobi is 2 working days");
                                                bb.setView(layer);
                                                bb.setNegativeButton("close", (shoot, boom) -> shoot.cancel());
                                                AlertDialog alertDialog = bb.create();
                                                alertDialog.show();
                                                alertDialog.setCanceledOnTouchOutside(false);
                                                alertDialog.getWindow().setGravity(Gravity.BOTTOM);

                                            } else {
                                                AlertDialog.Builder br = new AlertDialog.Builder(Cart.this);
                                                br.setTitle("Payment");
                                                br.setMessage("Cart Subtotal: KES" + quota + "\n\nDisc. Allowed: -(KES" + discAmt + ")\n\nNewCharge: KES" + current + "\n\nOrder Total: KES" + current + "\n\nGo to MPESA & select \nLipa na M-PESA. \nPayBill: 888880\nAccount no: (your Reg_Serial)" + customerMod.reg_id + "\nEnter Mpesa CODE in the Next pop up");
                                                br.setPositiveButton("submit", (submerge, yes) -> {
                                                    AlertDialog.Builder babel = new AlertDialog.Builder(this);
                                                    Rect retan = new Rect();
                                                    Window window8 = Cart.this.getWindow();
                                                    window8.getDecorView().getWindowVisibleDisplayFrame(retan);
                                                    LayoutInflater layoutInflater1 = (LayoutInflater) Cart.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                    layer = layoutInflater1.inflate(R.layout.mpesa, null);
                                                    layer.setMinimumWidth((int) (retan.width() * 0.9f));
                                                    layer.setMinimumHeight((int) (retan.height() * 0.1f));
                                                    MPESA = layer.findViewById(R.id.mpesa);
                                                    reportErrors = layer.findViewById(R.id.errorMark);
                                                    button4 = layer.findViewById(R.id.btnSubmit);
                                                    button4.setOnClickListener(veve -> {
                                                        final String muCode = MPESA.getText().toString().trim();
                                                        int len = muCode.length();
                                                        if (muCode.isEmpty()) {
                                                            reportErrors.setText("Mpesa Code needed");
                                                            reportErrors.setVisibility(View.VISIBLE);
                                                        } else if (len < 10) {
                                                            reportErrors.setText("Ooops! Your Mpesa code is invalid");
                                                            reportErrors.setVisibility(View.VISIBLE);
                                                        } else {
                                                            reportErrors.setVisibility(View.GONE);
                                                            StringRequest stringRequesting = new StringRequest(Request.Method.POST, Travel.PAY_DENI,
                                                                    respon -> {
                                                                        try {
                                                                            JSONObject jsonOb = new JSONObject(respon);
                                                                            Log.e("response ", respon);
                                                                            String msg = jsonOb.getString("message");
                                                                            int statuses = jsonOb.getInt("success");
                                                                            if (statuses == 1) {
                                                                                Toast.makeText(Cart.this, msg, Toast.LENGTH_LONG).show();
                                                                                startActivity(new Intent(getApplicationContext(), Cart.class));

                                                                            } else if (statuses == 0) {
                                                                                reportErrors.setText(msg);
                                                                                reportErrors.setVisibility(View.VISIBLE);
                                                                                Toast.makeText(Cart.this, msg, Toast.LENGTH_SHORT).show();
                                                                            }

                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                            reportErrors.setText("An error occurred");
                                                                            reportErrors.setVisibility(View.VISIBLE);
                                                                            Toast.makeText(Cart.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    }, error -> {
                                                                reportErrors.setText("Failed to connect");
                                                                reportErrors.setVisibility(View.VISIBLE);
                                                                Toast.makeText(Cart.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                                                            }) {
                                                                @Override
                                                                protected Map<String, String> getParams() {
                                                                    Map<String, String> params = new HashMap<>();
                                                                    params.put("quantity", sum_quant);
                                                                    params.put("orders", current);
                                                                    params.put("shipping", "0");
                                                                    params.put("discount", discAmt);
                                                                    params.put("discounted", current);
                                                                    params.put("original", quota);
                                                                    params.put("delivery", "No Action");
                                                                    params.put("location", "No Action");
                                                                    params.put("address", "No Action");
                                                                    params.put("mpesa", muCode);
                                                                    params.put("customer_id", customerMod.reg_id);
                                                                    params.put("phone", customerMod.mobile);
                                                                    params.put("disburse", "No Action");
                                                                    params.put("shipper", "No Action");
                                                                    params.put("shipment", "No Action");
                                                                    params.put("customer", "Pending");
                                                                    return params;
                                                                }//orders,shipping,discount,discounted,original
                                                            };//quantity,order,shipping,total,location,mpesa,customer,phone,reg
                                                            //disburse,shipper,shipment,customer
                                                            RequestQueue requestQueue = Volley.newRequestQueue(this);
                                                            requestQueue.add(stringRequesting);
                                                        }
                                                    });
                                                    babel.setTitle("Submit Payment Details");
                                                    babel.setView(layer);
                                                    babel.setNegativeButton("close", (shoot, boom) -> shoot.cancel());
                                                    AlertDialog alertDialog = babel.create();
                                                    alertDialog.show();
                                                    alertDialog.setCanceledOnTouchOutside(false);
                                                    alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                                                });
                                                br.setNegativeButton("close", (submerge, yes) -> submerge.cancel());
                                                AlertDialog alertDialog = br.create();
                                                alertDialog.show();
                                                alertDialog.setCanceledOnTouchOutside(false);
                                                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                                            }

                                        });
                                        max.setNegativeButton("close", (dialog1, ite) -> dialog1.cancel());
                                        final AlertDialog alertDialog = max.create();
                                        alertDialog.setCanceledOnTouchOutside(false);
                                        alertDialog.show();
                                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                                    });
                                    builder.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                                });
                            }  //product_id,category,type,description,imagery,quantity,price,stock
                            blackJack = new CartAdapter(Cart.this, R.layout.remove_cart, SubjectList);
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
                            button1.setVisibility(View.VISIBLE);

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
                params.put("customer", customerMod.reg_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(Cart.this);
                dialog.setTitle("My Profile");
                dialog.setIcon(R.drawable.person);
                dialog.setItems(items, (dialog1, itemer) -> {
                    if (itemer == 0) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Cart.this);
                        mydialog1.setTitle("Profile");
                        mydialog1.setMessage("Registry: " + customerMod.reg_id + "\nName: " + customerMod.fullname + "\nUsername: " + customerMod.username + "\nEmail: " + customerMod.email + "\nPhone: " + customerMod.mobile + "\nCity: " + customerMod.address + "\nStatus: " + customerMod.approval + "\nDate: " + customerMod.reg_date);
                        mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                        final AlertDialog alertDialog = mydialog1.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    } else if (itemer == 1) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Cart.this);
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

    private void getTender() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.COUNT_CART,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String counter = jsonObject.getString("counter");
                                textView.setText(counter + " Items");
                            }
                        } else {
                            textView.setText(" 0 Items");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {

        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("customer_id", customerMod.reg_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}