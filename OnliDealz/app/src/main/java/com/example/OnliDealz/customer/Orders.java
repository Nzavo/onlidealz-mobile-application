package com.example.OnliDealz.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.OnliDealz.Black.CustomerMod;
import com.example.OnliDealz.Black.CustomerSess;
import com.example.OnliDealz.MainActivity;
import com.example.OnliDealz.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class Orders extends AppCompatActivity {
    Vibrator vibrator;
    CustomerMod customerMod;
    CustomerSess customerSess;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerSess = new CustomerSess(getApplicationContext());
        customerMod = customerSess.getCustDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + customerMod.fullname);
        setContentView(R.layout.activity_orders);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.history);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.cart:
                    startActivity(new Intent(getApplicationContext(),Cart.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.homing:
                    startActivity(new Intent(getApplicationContext(),CustDash.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.history:
                    vibrator.vibrate(500);
                    CharSequence[] items = {"Orders", "Receipt"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Orders.this);
                    dialog.setTitle("Files");
                    dialog.setIcon(R.drawable.history);
                    dialog.setItems(items, (dialog1, itemer) -> {
                        if (itemer == 0) {
                            startActivity(new Intent(getApplicationContext(),Orders.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(),Receipt.class));
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(Orders.this);
                dialog.setTitle("My Profile");
                dialog.setIcon(R.drawable.person);
                dialog.setItems(items, (dialog1, itemer) -> {
                    if (itemer == 0) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Orders.this);
                        mydialog1.setTitle("Profile");
                        mydialog1.setMessage("Registry: " + customerMod.reg_id + "\nName: " + customerMod.fullname + "\nUsername: " + customerMod.username + "\nEmail: " + customerMod.email + "\nPhone: " + customerMod.mobile + "\nCity: " + customerMod.address + "\nStatus: " + customerMod.approval + "\nDate: " + customerMod.reg_date);
                        mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                        final AlertDialog alertDialog = mydialog1.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    } else if (itemer == 1) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Orders.this);
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