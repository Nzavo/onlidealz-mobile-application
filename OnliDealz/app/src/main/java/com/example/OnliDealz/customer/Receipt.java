package com.example.OnliDealz.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.print.PrintManager;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.OnliDealz.Black.AdapTiew;
import com.example.OnliDealz.Black.CartMod;
import com.example.OnliDealz.Black.CustomerMod;
import com.example.OnliDealz.Black.CustomerSess;
import com.example.OnliDealz.Black.DrivedAda;
import com.example.OnliDealz.Black.FamilyAda;
import com.example.OnliDealz.Black.GetPay;
import com.example.OnliDealz.Black.PrintThis;
import com.example.OnliDealz.Black.Travel;
import com.example.OnliDealz.MainActivity;
import com.example.OnliDealz.R;
import com.example.OnliDealz.driver.DriverDash;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Receipt extends AppCompatActivity {
    Vibrator vibrator;
    CustomerMod customerMod;
    CustomerSess customerSess;
    BottomNavigationView bottomNavigationView;
    SearchView searchView;
    ListView listView;
    TextView ErrorText, countTenders;
    EditText Comment, PriceTag;
    Spinner spinner;
    ArrayList<String> suppliers = new ArrayList<>();
    ArrayAdapter<String> supplierAdapter;
    ImageView imageView;
    ArrayList<GetPay> SubjectList = new ArrayList<>();
    RequestQueue requestQueue;
    GetPay neverFails;
    DrivedAda blackJack;
    Button button;
    ArrayList<CartMod> proxyCarts = new ArrayList<>();
    ListView lister;
    AdapTiew cartAdapter;
    String identity;
    TextView textView;
    View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerSess = new CustomerSess(getApplicationContext());
        customerMod = customerSess.getCustDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + customerMod.fullname);
        setContentView(R.layout.activity_receipt);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.history);
        ErrorText = findViewById(R.id.nothig);
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.recycler);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            neverFails = (GetPay) parent.getItemAtPosition(position);
            GetPay product = SubjectList.get(position);
            identity = product.getPay_id();
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            Rect rect = new Rect();
            Window window = Receipt.this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            LayoutInflater layoutInflater = (LayoutInflater) Receipt.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = layoutInflater.inflate(R.layout.ee_iko_fiti, null);
            layout.setMinimumWidth((int) (rect.width() * 0.9f));
            layout.setMinimumHeight((int) (rect.height() * 0.08f));
            textView = layout.findViewById(R.id.myTexter);
            lister = layout.findViewById(R.id.listerS);
            lister.setTextFilterEnabled(true);
            practicalS();
            alert.setTitle("Delivery");
            alert.setView(layout);
            textView.setText("Customer: " + customerMod.getFullname() + "\nphone: " + product.getPhone() + "\nlocation: " + product.getLocation() + "\nDate: " + product.getReg_date());
            alert.setPositiveButton("print pdf", (dialog, ids) -> {
                printThis();
            });
            alert.setNegativeButton("close", (dialog, ids) -> {
                startActivity(new Intent(getApplicationContext(), Receipt.class));
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        });
        practical();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.cart:
                    startActivity(new Intent(getApplicationContext(), Cart.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.homing:
                    startActivity(new Intent(getApplicationContext(), CustDash.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.history:
                    vibrator.vibrate(500);
                    CharSequence[] items = {/*"Orders", */"Receipt"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Receipt.this);
                    dialog.setTitle("Files");
                    dialog.setIcon(R.drawable.history);
                    dialog.setItems(items, (dialog1, itemer) -> {
                        if (itemer == 0) {
                            startActivity(new Intent(getApplicationContext(), Receipt.class));
                        } else {
                            //startActivity(new Intent(getApplicationContext(),Receipt.class));
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void printThis() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        printManager.print(getString(R.string.app_name), new PrintThis(this, layout.findViewById(R.id.titled)), null);
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
                            cartAdapter = new AdapTiew(Receipt.this, R.layout.yako_ni_kesho, proxyCarts);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.biggestBlad,
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
                            blackJack = new DrivedAda(Receipt.this, R.layout.shipped, SubjectList);
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(Receipt.this);
                dialog.setTitle("My Profile");
                dialog.setIcon(R.drawable.person);
                dialog.setItems(items, (dialog1, itemer) -> {
                    if (itemer == 0) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Receipt.this);
                        mydialog1.setTitle("Profile");
                        mydialog1.setMessage("Registry: " + customerMod.reg_id + "\nName: " + customerMod.fullname + "\nUsername: " + customerMod.username + "\nEmail: " + customerMod.email + "\nPhone: " + customerMod.mobile + "\nCity: " + customerMod.address + "\nStatus: " + customerMod.approval + "\nDate: " + customerMod.reg_date);
                        mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                        final AlertDialog alertDialog = mydialog1.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    } else if (itemer == 1) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Receipt.this);
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
}