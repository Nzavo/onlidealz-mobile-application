package com.example.OnliDealz.supplier;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.print.PrintManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.OnliDealz.Black.PayAdap;
import com.example.OnliDealz.Black.PayMod;
import com.example.OnliDealz.Black.PrintThis;
import com.example.OnliDealz.Black.SupMod;
import com.example.OnliDealz.Black.SupSess;
import com.example.OnliDealz.Black.Travel;
import com.example.OnliDealz.MainActivity;
import com.example.OnliDealz.R;
import com.example.OnliDealz.driver.DriveHist;
import com.example.OnliDealz.driver.DriverDash;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Payment extends AppCompatActivity {
    Vibrator vibrator;
    SupSess supSess;
    SupMod supMod;
    BottomNavigationView bottomNavigationView;
    private TextView fullname, mobile, email, total;
    ListView listView;
    ArrayList<PayMod> SubjectList = new ArrayList<>();
    PayAdap suppAda;
    PayMod suppFind;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supSess = new SupSess(getApplicationContext());
        supMod = supSess.getSupDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + supMod.fullname);
        setContentView(R.layout.activity_payment);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.payment);
        listView = findViewById(R.id.recep);
        listView.setTextFilterEnabled(true);
        button = findViewById(R.id.btnPrint);
        button.setOnClickListener(v -> {
            printAll();
        });
        //mySerial = findViewById(R.id.takeSerial);
        //serailML = findViewById(R.id.name);
        //dateD = findViewById(R.id.myDatee);
        fullname = findViewById(R.id.client);
        mobile = findViewById(R.id.contact);
        total = findViewById(R.id.mySum);
        email = findViewById(R.id.clientemail);
        // ship = findViewById(R.id.myshipp);
        fullname.setText("Name: " + supMod.getFullname() + "\nBusiness: " + supMod.getBusiness_name()+"\nRegistry: "+supMod.getBusiness_registry());
        mobile.setText("Phone: " + supMod.getMobile());
        email.setText("Email: " + supMod.getEmail());
        PayMe();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.history:
                    startActivity(new Intent(getApplicationContext(), SuppHist.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.payment:
                    vibrator.vibrate(500);
                    return true;
                case R.id.homing:
                    startActivity(new Intent(getApplicationContext(), SupplierDash.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void printAll() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        printManager.print(getString(R.string.app_name), new PrintThis(this, findViewById(R.id.memba)), null);
    }

    private void PayMe() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.majorFlow,
                response -> {
                    try {
                        PayMod subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String product = jsonObject.getString("product");
                                String quantity = jsonObject.getString("quantity");
                                String price = jsonObject.getString("price");
                                String supply = jsonObject.getString("supply");
                                String all_pay = jsonObject.getString("all_pay");
                                subject = new PayMod(product, quantity, price, supply, all_pay);
                                SubjectList.add(subject);
                                total.setText("KES. " + all_pay);
                            }
                            suppAda = new PayAdap(Payment.this, R.layout.mindful, SubjectList);
                            listView.setAdapter(suppAda);
                        } else if (success == 0) {
                            String msg = jsonObject.getString("mine");
                            new AlertDialog.Builder(Payment.this)
                                    .setMessage(msg)
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "an error occurred", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("supplier", supMod.getReg_id());
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(Payment.this);
                dialog.setTitle("My Profile");
                dialog.setIcon(R.drawable.person);
                dialog.setItems(items, (dialog1, itemer) -> {
                    if (itemer == 0) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Payment.this);
                        mydialog1.setTitle("Profile");
                        mydialog1.setMessage("Registry: " + supMod.reg_id + "\nB/SReg: " + supMod.business_registry + "\nBusiness Name: " + supMod.business_name + "\nNature: " + supMod.existence + "\nName: " + supMod.fullname + "\nUsername: " + supMod.username + "\nEmail: " + supMod.email + "\nPhone: " + supMod.mobile + "\nCity: " + supMod.address + "\nStatus: " + supMod.approval + "\nDate: " + supMod.reg_date);
                        mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                        final AlertDialog alertDialog = mydialog1.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    } else if (itemer == 1) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Payment.this);
                        mydialog1.setTitle("Change Password");
                        mydialog1.setMessage("Change");
                        mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                        final AlertDialog alertDialog = mydialog1.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    } else if (itemer == 2) {
                        supSess.signOutSup();
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