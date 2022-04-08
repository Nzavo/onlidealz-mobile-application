package com.example.OnliDealz.manager;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.OnliDealz.Black.CartMod;
import com.example.OnliDealz.Black.DeliverAda;
import com.example.OnliDealz.Black.FamilyAda;
import com.example.OnliDealz.Black.GetPay;
import com.example.OnliDealz.Black.MgrSess;
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

public class Delivery extends AppCompatActivity {
    Vibrator vibrator;
    StaffMod staffMod;
    MgrSess mgrSess;
    BottomNavigationView bottomNavigationView;
    View layer;
    SearchView searchView;
    ListView listView;
    TextView ErrorText, countTenders;
    EditText Comment, PriceTag;
    Spinner spinner;
    ArrayList<String> suppliers = new ArrayList<>();
    ArrayAdapter<String> supplierAdapter;
    RequestQueue requestQueue;
    ImageView imageView;
    ArrayList<GetPay> SubjectList = new ArrayList<>();
    GetPay neverFails;
    DeliverAda blackJack;
    Button button;
    ArrayList<CartMod> proxyCarts = new ArrayList<>();
    ListView lister;
    FamilyAda cartAdapter;
    String identity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mgrSess = new MgrSess(getApplicationContext());
        staffMod = mgrSess.getMgrDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + staffMod.fullname);
        setContentView(R.layout.activity_delivery);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.delivery);
        ErrorText = findViewById(R.id.nothig);
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.recycler);
        listView.setTextFilterEnabled(true);
        countTenders = findViewById(R.id.counterSerial);
        getTender();
        practical();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            neverFails = (GetPay) parent.getItemAtPosition(position);
            GetPay product = SubjectList.get(position);
            identity = product.getPay_id();
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            Rect rect = new Rect();
            Window window = Delivery.this.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rect);
            LayoutInflater layoutInflater = (LayoutInflater) Delivery.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflater.inflate(R.layout.edit_ngumu, null);
            layout.setMinimumWidth((int) (rect.width() * 0.9f));
            layout.setMinimumHeight((int) (rect.height() * 0.08f));
            lister = layout.findViewById(R.id.listerS);
            lister.setTextFilterEnabled(true);
            practicalS();
            alert.setTitle("Add Driver");
            alert.setView(layout);
            alert.setMessage("CustomerID: " + product.getCustomer_id() + "\nphone: " + product.getPhone() + "\nlocation: " + product.getLocation() + "\nstatus: " + product.getStatus() + "\nDate: " + product.getReg_date());
            alert.setPositiveButton("Add Driver", (dialog, ids) -> {
                requestQueue = Volley.newRequestQueue(this);
                AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Delivery.this);
                final Spinner spinning = new Spinner(Delivery.this);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Travel.DRIVER, null,
                        response -> {
                            try {
                                JSONArray jsonArray = response.getJSONArray("driver");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String supplierEmail = jsonObject.optString("email");
                                    suppliers.add(supplierEmail);
                                    supplierAdapter = new ArrayAdapter<>(Delivery.this,
                                            android.R.layout.simple_spinner_item, suppliers);
                                    supplierAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinning.setAdapter(supplierAdapter);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> {

                });
                requestQueue.add(jsonObjectRequest);
                mydialog1.setView(spinning);
                mydialog1.setTitle("Select Driver");
                mydialog1.setNeutralButton("close", (dialog1, item) -> {
                    startActivity(new Intent(getApplicationContext(), Delivery.class));
                    finish();
                });
                mydialog1.setPositiveButton("assign", (dialog1, item) -> {
                    String myDriver = spinning.getSelectedItem().toString().trim();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.UPDATE_DRIVER,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.e("response ", response);
                                    String msg = jsonObject.getString("message");
                                    int status = jsonObject.getInt("success");
                                    if (status == 1) {
                                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Delivery.class));
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
                            params.put("shipper", myDriver);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequest);
                });
                AlertDialog alertDialog = mydialog1.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            });
            alert.setNegativeButton("close", (dialog, ids) -> {
                startActivity(new Intent(getApplicationContext(), Delivery.class));
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.homing:
                    startActivity(new Intent(getApplicationContext(), ManagerDash.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.delivery:
                    vibrator.vibrate(500);
                    return true;
                case R.id.tender:
                    startActivity(new Intent(getApplicationContext(), Tenders.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                /*case R.id.store:
                    startActivity(new Intent(getApplicationContext(), Stocked.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;*/
                case R.id.history:
                    startActivity(new Intent(getApplicationContext(), MgrHist.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
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
                            cartAdapter = new FamilyAda(Delivery.this, R.layout.menem, proxyCarts);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.GET_DISBURSE,
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
                            blackJack = new DeliverAda(Delivery.this, R.layout.tabular, SubjectList);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.COUNT_DELIVER,
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
                                countTenders.setText(" Logistics & Delivery " + counter);
                            }
                        } else {
                            countTenders.setText(" Logistics & Delivery 0");
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(Delivery.this);
                dialog.setTitle("My Profile");
                dialog.setIcon(R.drawable.person);
                dialog.setItems(items, (dialog1, itemer) -> {
                    if (itemer == 0) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Delivery.this);
                        mydialog1.setTitle("Profile");
                        mydialog1.setMessage("Registry: " + staffMod.reg_id + "\nName: " + staffMod.fullname + "\nUsername: " + staffMod.username + "\nEmail: " + staffMod.email + "\nPhone: " + staffMod.mobile + "\nRole: " + staffMod.role + "\nStatus: " + staffMod.approval + "\nDate: " + staffMod.reg_date);
                        mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                        final AlertDialog alertDialog = mydialog1.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    } else if (itemer == 1) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Delivery.this);
                        mydialog1.setTitle("Change Password");
                        mydialog1.setMessage("Change");
                        mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                        final AlertDialog alertDialog = mydialog1.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    } else if (itemer == 2) {
                        mgrSess.signOutMgr();
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