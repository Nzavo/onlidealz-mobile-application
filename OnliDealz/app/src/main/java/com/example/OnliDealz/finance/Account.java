package com.example.OnliDealz.finance;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.OnliDealz.Black.FinaSess;
import com.example.OnliDealz.Black.PrintThis;
import com.example.OnliDealz.Black.ProfitMargin;
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

public class Account extends AppCompatActivity {
    Vibrator vibrator;
    FinaSess finaSess;
    StaffMod staffMod;
    BottomNavigationView bottomNavigationView;
    ArrayList<ProfitMargin> SubjectList = new ArrayList<>();
    private TextView profitBdn;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finaSess = new FinaSess(getApplicationContext());
        staffMod = finaSess.getFinaDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + staffMod.fullname);
        setContentView(R.layout.activity_account);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.account);
        profitBdn = findViewById(R.id.txtname);
        button = findViewById(R.id.printThi);
        button.setOnClickListener(v -> {
            printPDF();
        });
        practicalPra();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.account:
                    CharSequence[] items = {"Account Record", "Supplier Payments"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Account.this);
                    dialog.setTitle("Activity Logs");
                    dialog.setItems(items, (dialog1, itemer) -> {
                        if (itemer == 0) {
                            startActivity(new Intent(getApplicationContext(), Account.class));
                            finish();
                        } else if (itemer == 1) {
                            startActivity(new Intent(getApplicationContext(), SupplierPayment.class));
                            finish();
                        }
                    });
                    dialog.setNegativeButton("close", (dialog1, itemer) -> dialog1.cancel());
                    final AlertDialog alertDialog = dialog.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                    vibrator.vibrate(500);
                    return true;
                case R.id.homing:
                    startActivity(new Intent(getApplicationContext(), FinanceDash.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    private void practicalPra() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.margin,
                response -> {
                    try {
                        ProfitMargin subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String timer = jsonObject.getString("timer");
                                String summedOrder = jsonObject.getString("summedOrder");
                                String summedSupply = jsonObject.getString("summedSupply");
                                String profit = jsonObject.getString("profit");
                                subject = new ProfitMargin(timer, summedOrder, summedSupply, profit);
                                SubjectList.add(subject);
                                profitBdn.setText("Statement As At " + timer + "\n\nTotal Orders KSHs" + summedOrder + "\nTotal Supplies KSHs" + summedSupply + "\nProfitGenerated KSHs" + profit);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }, error -> {
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void printPDF() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        printManager.print(getString(R.string.app_name), new PrintThis(this, findViewById(R.id.manned)), null);
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(Account.this);
                dialog.setTitle("My Profile");
                dialog.setIcon(R.drawable.person);
                dialog.setItems(items, (dialog1, itemer) -> {
                    if (itemer == 0) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Account.this);
                        mydialog1.setTitle("Profile");
                        mydialog1.setMessage("Registry: " + staffMod.reg_id + "\nName: " + staffMod.fullname + "\nUsername: " + staffMod.username + "\nEmail: " + staffMod.email + "\nPhone: " + staffMod.mobile + "\nRole: " + staffMod.role + "\nStatus: " + staffMod.approval + "\nDate: " + staffMod.reg_date);
                        mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                        final AlertDialog alertDialog = mydialog1.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    } else if (itemer == 1) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Account.this);
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