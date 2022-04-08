package com.example.OnliDealz.supplier;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.OnliDealz.Black.ProductMod;
import com.example.OnliDealz.Black.SupMod;
import com.example.OnliDealz.Black.SupSess;
import com.example.OnliDealz.Black.Travel;
import com.example.OnliDealz.Black.ViewAdapter;
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

public class SuppHist extends AppCompatActivity {
    Vibrator vibrator;
    SupSess supSess;
    SupMod supMod;
    BottomNavigationView bottomNavigationView;
    ImageView imageView;
    ArrayList<ProductMod> SubjectList = new ArrayList<>();
    ProductMod neverFails;
    ViewAdapter blackJack;
    View layer;
    SearchView searchView;
    ListView listView;
    TextView ErrorText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supSess = new SupSess(getApplicationContext());
        supMod = supSess.getSupDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + supMod.fullname);
        setContentView(R.layout.activity_supp_hist);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.history);
        ErrorText = findViewById(R.id.nothig);
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.recycler);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            neverFails = (ProductMod) parent.getItemAtPosition(position);
            AlertDialog.Builder alert = new AlertDialog.Builder(SuppHist.this);
            Rect reco = new Rect();
            Window win = SuppHist.this.getWindow();
            win.getDecorView().getWindowVisibleDisplayFrame(reco);
            LayoutInflater inflater = (LayoutInflater) SuppHist.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflater.inflate(R.layout.product_miner, null);
            layer.setMinimumWidth((int) (reco.width() * 0.9f));
            layer.setMinimumHeight((int) (reco.height() * 0.2f));
            button = layer.findViewById(R.id.btnVerify);
            imageView = layer.findViewById(R.id.imagery);
            button.setVisibility(View.GONE);
            ProductMod product = SubjectList.get(position);
            Glide.with(SuppHist.this).load(product.getImage()).into(imageView);
            alert.setTitle(product.getCategory());
            alert.setMessage("Supplier_id: " + product.getSupplier_id() + "\nBusinessName: " + product.getBusiness_name() + "\nCategory: " + product.getCategory() + "\nType: " + product.getType() + "\nOrderedQty: " + product.getSystem() + "units\nSuppliedQty: " + product.getQuantity() + "units\nDescription: " + product.getDescription() + "\nUnitPrice: KES" + product.getPrice() + "\nTotalCharge: KES" + product.getCharge() + "\nStatus: " + product.getStatus() + "\nComment: " + product.getComment() + "\nDate: " + product.getReg_date());
            alert.setView(layer);
            alert.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        });
        practical();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.payment:
                    startActivity(new Intent(getApplicationContext(), Payment.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.history:
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

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.supHist,
                response -> {
                    try {

                        ProductMod subject;
                        SubjectList = new ArrayList<>();

                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String purchase_id = jsonObject.getString("purchase_id");
                                String counter_id = jsonObject.getString("counter_id");
                                String supplier_id = jsonObject.getString("supplier_id");
                                String business_name = jsonObject.getString("business_name");
                                String category = jsonObject.getString("category");
                                String type = jsonObject.getString("type");
                                String price = jsonObject.getString("price");
                                String quantity = jsonObject.getString("quantity");
                                String charge = jsonObject.getString("charge");
                                String description = jsonObject.getString("description");
                                String product_image = jsonObject.getString("image");
                                String imagery = "https://amtamwa.000webhostapp.com/onlidealz/images/" + product_image;
                                String status = jsonObject.getString("status");
                                String comment = jsonObject.getString("comment");
                                String reg_date = jsonObject.getString("reg_date");
                                String system = jsonObject.getString("system");
                                subject = new ProductMod(purchase_id, counter_id, supplier_id, business_name, category, type, price, quantity, charge, description, imagery, status, comment, reg_date, system);
                                SubjectList.add(subject);
                            }  //product_id,category,type,description,imagery,quantity,price,stock
                            blackJack = new ViewAdapter(SuppHist.this, R.layout.product, SubjectList);
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
                params.put("supplier_id", supMod.getReg_id());
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(SuppHist.this);
                dialog.setTitle("My Profile");
                dialog.setIcon(R.drawable.person);
                dialog.setItems(items, (dialog1, itemer) -> {
                    if (itemer == 0) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(SuppHist.this);
                        mydialog1.setTitle("Profile");
                        mydialog1.setMessage("Registry: " + supMod.reg_id + "\nB/SReg: " + supMod.business_registry + "\nBusiness Name: " + supMod.business_name + "\nNature: " + supMod.existence + "\nName: " + supMod.fullname + "\nUsername: " + supMod.username + "\nEmail: " + supMod.email + "\nPhone: " + supMod.mobile + "\nCity: " + supMod.address + "\nStatus: " + supMod.approval + "\nDate: " + supMod.reg_date);
                        mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                        final AlertDialog alertDialog = mydialog1.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    } else if (itemer == 1) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(SuppHist.this);
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