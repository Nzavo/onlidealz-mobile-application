package com.example.OnliDealz.finance;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.example.OnliDealz.Black.FinaSess;
import com.example.OnliDealz.Black.GetPay;
import com.example.OnliDealz.Black.GetPayAdapter;
import com.example.OnliDealz.Black.StaffMod;
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

public class FinanceDash extends AppCompatActivity {
    Vibrator vibrator;
    FinaSess finaSess;
    StaffMod staffMod;
    BottomNavigationView bottomNavigationView;
    View layer;
    SearchView searchView;
    ListView listView;
    TextView ErrorText, countTenders;
    EditText Comment, PriceTag;
    Spinner spinner;
    ImageView imageView;
    ArrayList<GetPay> SubjectList = new ArrayList<>();
    GetPay neverFails;
    GetPayAdapter blackJack;
    Button button, btnSend;
    //String Courier, Pricer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finaSess = new FinaSess(getApplicationContext());
        staffMod = finaSess.getFinaDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + staffMod.fullname);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_finance_dash);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.homing);
        ErrorText = findViewById(R.id.nothig);
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.recycler);
        listView.setTextFilterEnabled(true);
        btnSend = findViewById(R.id.cusChat);
        countTenders = findViewById(R.id.counterSerial);
        getTender();
        practical();
        btnSend.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), FiannceRate.class));
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            neverFails = (GetPay) parent.getItemAtPosition(position);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            GetPay product = SubjectList.get(position);
            if (product.getStatus().equals("Pending")) {
                alert.setMessage("Payment: " + product.getPay_id() + "\ncustomer_id: " + product.getCustomer_id() + "\nMPESA: " + product.getMpesa() + "\norders: KES" + product.getOrders() + "\nShipping: +(KES" + product.getShipping() + ")\nDiscount: -(KES" + product.getDiscount() + ")\n\ntotal: KES" + product.getDiscounted() + "\n\nphone: " + product.getPhone() + "\nlocation: " + product.getLocation() + "\nstatus: " + product.getStatus() + "\nDate: " + product.getReg_date());
                Rect reco = new Rect();
                Window win = FinanceDash.this.getWindow();
                win.getDecorView().getWindowVisibleDisplayFrame(reco);
                LayoutInflater inflater = (LayoutInflater) FinanceDash.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layer = inflater.inflate(R.layout.payer, null);
                layer.setMinimumWidth((int) (reco.width() * 0.9f));
                layer.setMinimumHeight((int) (reco.height() * 0.05f));
                button = layer.findViewById(R.id.btnVerify);
                button.setOnClickListener(v -> {
                    AlertDialog.Builder mydialog1 = new AlertDialog.Builder(FinanceDash.this);
                    final Spinner spinning = new Spinner(FinanceDash.this);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(FinanceDash.this, R.array.Status, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinning.setAdapter(adapter);
                    mydialog1.setView(spinning);
                    mydialog1.setTitle("Select Status");
                    mydialog1.setPositiveButton("submit", (dialogInterface12, i12) -> {
                        String myStatus = spinning.getSelectedItem().toString();
                        if (myStatus.equals("Verify Record")) {
                            Toast.makeText(this, "Please Select Status", Toast.LENGTH_SHORT).show();
                        } else {
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.UPDATE_PAY,
                                    response -> {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            Log.e("response ", response);
                                            String msg = jsonObject.getString("message");
                                            int status = jsonObject.getInt("success");
                                            if (status == 1) {
                                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), FinanceDash.class));
                                                finish();

                                            } else if (status == 0) {
                                                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(this, "An Error occurred", Toast.LENGTH_SHORT).show();
                                        }

                                    }, error -> {
                                Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();
                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("pay_id", product.getPay_id());
                                    params.put("status", myStatus);
                                    return params;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(this);
                            requestQueue.add(stringRequest);
                        }
                    });
                    mydialog1.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
                    AlertDialog alertDialog = mydialog1.create();
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                });
                alert.setView(layer);
            } else {
                alert.setMessage("Payment: " + product.getPay_id() + "\ncustomer_id: " + product.getCustomer_id() + "\nMPESA: " + product.getMpesa() + "\norders: KES" + product.getOrders() + "\nShipping: +(KES" + product.getShipping() + ")\nDiscount: -(KES" + product.getDiscount() + ")\n\ntotal: KES" + product.getDiscounted() + "\n\nphone: " + product.getPhone() + "\nlocation: " + product.getLocation() + "\nstatus: " + product.getStatus() + "\nDate: " + product.getReg_date());
            }
            alert.setTitle("Payment Details");
            alert.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            //pay_id,mpesa,shipping,orders,total,quantity,customer_id,phone,location,status,disburse,shipper,shipment,customer,reg_date
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homing:
                    vibrator.vibrate(500);
                    return true;
                case R.id.account:
                    CharSequence[] items = {"Account Record", "Supplier Payments"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(FinanceDash.this);
                    dialog.setTitle("Activity Logs");
                    dialog.setItems(items, (dialog1, itemer) -> {
                        if (itemer == 0) {
                            startActivity(new Intent(getApplicationContext(), Account.class));
                            finish();
                        } else if(itemer == 1)  {
                            startActivity(new Intent(getApplicationContext(), SupplierPayment.class));
                            finish();
                        }
                    });
                    dialog.setNegativeButton("close", (dialog1, itemer) -> dialog1.cancel());
                    final AlertDialog alertDialog = dialog.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.GET_PAY,
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
                            blackJack = new GetPayAdapter(FinanceDash.this, R.layout.tabular, SubjectList);
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
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getTender() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.COUNT_PAY,
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
                                countTenders.setText(" New Payments " + counter);
                            }
                        } else {
                            countTenders.setText(" New Payments 0");
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(FinanceDash.this);
                dialog.setTitle("My Profile");
                dialog.setIcon(R.drawable.person);
                dialog.setItems(items, (dialog1, itemer) -> {
                    if (itemer == 0) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(FinanceDash.this);
                        mydialog1.setTitle("Profile");
                        mydialog1.setMessage("Registry: " + staffMod.reg_id + "\nName: " + staffMod.fullname + "\nUsername: " + staffMod.username + "\nEmail: " + staffMod.email + "\nPhone: " + staffMod.mobile + "\nRole: " + staffMod.role + "\nStatus: " + staffMod.approval + "\nDate: " + staffMod.reg_date);
                        mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                        final AlertDialog alertDialog = mydialog1.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    } else if (itemer == 1) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(FinanceDash.this);
                        mydialog1.setTitle("Change Password");
                        mydialog1.setMessage("Change");
                        mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                        final AlertDialog alertDialog = mydialog1.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    } else if (itemer == 2) {
                        finaSess.signOutFina();
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