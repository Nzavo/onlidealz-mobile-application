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
import android.widget.AdapterView;
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
import com.bumptech.glide.Glide;
import com.example.OnliDealz.Black.MgrSess;
import com.example.OnliDealz.Black.ProductAdapter;
import com.example.OnliDealz.Black.ProductMod;
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

public class ManagerDash extends AppCompatActivity {
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
    ImageView imageView;
    ArrayList<ProductMod> SubjectList = new ArrayList<>();
    ProductMod neverFails;
    ProductAdapter blackJack;
    Button button, btnReview, btnSend;
    //String Courier, Pricer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mgrSess = new MgrSess(getApplicationContext());
        staffMod = mgrSess.getMgrDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + staffMod.fullname);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_manager_dash);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.homing);
        ErrorText = findViewById(R.id.nothig);
        searchView = findViewById(R.id.search);
        btnReview = findViewById(R.id.prodRev);
        btnSend = findViewById(R.id.cusChat);
        listView = findViewById(R.id.recycler);
        listView.setTextFilterEnabled(true);
        countTenders = findViewById(R.id.counterSerial);
        getTender();
        practical();
        btnSend.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), SafariYaPembe.class));
        });
        btnReview.setOnClickListener(v -> {
            CharSequence[] items = {"Reviews", "Feedback"};
            AlertDialog.Builder dialog = new AlertDialog.Builder(ManagerDash.this);
            dialog.setTitle("Reviews & Ratings");
            dialog.setIcon(R.drawable.rate);
            dialog.setItems(items, (dialog1, itemer) -> {
                if (itemer == 0) {
                    startActivity(new Intent(getApplicationContext(), CustomerReview.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), CustomerRating.class));
                }
                finish();
            });
            dialog.setNegativeButton("close", (dialog1, itemer) -> dialog1.cancel());
            final AlertDialog alert = dialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            neverFails = (ProductMod) parent.getItemAtPosition(position);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            Rect reco = new Rect();
            Window win = ManagerDash.this.getWindow();
            win.getDecorView().getWindowVisibleDisplayFrame(reco);
            LayoutInflater inflater = (LayoutInflater) ManagerDash.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflater.inflate(R.layout.product_miner, null);
            layer.setMinimumWidth((int) (reco.width() * 0.9f));
            layer.setMinimumHeight((int) (reco.height() * 0.2f));
            button = layer.findViewById(R.id.btnVerify);
            imageView = layer.findViewById(R.id.imagery);
            ProductMod product = SubjectList.get(position);
            Glide.with(ManagerDash.this).load(product.getImage()).into(imageView);
            alert.setTitle(product.getCategory());
            alert.setMessage("Supplier_id: " + product.getSupplier_id() + "\nBusinessName: " + product.getBusiness_name() + "\nCategory: " + product.getCategory() + "\nType: " + product.getType() + "\nOrderedQty: " + product.getSystem() + "units\nSuppliedQty: " + product.getQuantity() + "units\nDescription: " + product.getDescription() + "\nUnitPrice: KES" + product.getPrice() + "\nTotalCharge: KES" + product.getCharge() + "\nStatus: " + product.getStatus() + "\nDate: " + product.getReg_date());
            alert.setView(layer);
            button.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                Rect rect = new Rect();
                Window window = ManagerDash.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rect);
                LayoutInflater layoutInflater = (LayoutInflater) ManagerDash.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layer = layoutInflater.inflate(R.layout.verify_prod, null);
                layer.setMinimumWidth((int) (rect.width() * 0.9f));
                layer.setMinimumHeight((int) (rect.height() * 0.2f));
                PriceTag = layer.findViewById(R.id.editPrice);
                Comment = layer.findViewById(R.id.editComment);
                button = layer.findViewById(R.id.btnUpdate);
                spinner = layer.findViewById(R.id.spnrStatus);
                builder.setTitle("Update Record");
                builder.setView(layer);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ManagerDash.this, R.array.Approval, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String mSpinner = spinner.getSelectedItem().toString();
                        if (mSpinner.equals("Select Status")) {
                            Comment.setVisibility(View.GONE);
                            button.setVisibility(View.GONE);
                            PriceTag.setVisibility(View.GONE);
                        } else if (mSpinner.equals("Rejected")) {
                            Comment.setVisibility(View.VISIBLE);
                            button.setVisibility(View.VISIBLE);
                            PriceTag.setVisibility(View.GONE);
                        } else {
                            Comment.setVisibility(View.VISIBLE);
                            button.setVisibility(View.VISIBLE);
                            PriceTag.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                button.setOnClickListener(view1 -> {
                    final String myPricer = PriceTag.getText().toString().trim();
                    final String myComment = Comment.getText().toString().trim();
                    final String mySpinner = spinner.getSelectedItem().toString().trim();
                    if (mySpinner.equals("Select Status")) {
                        Toast.makeText(ManagerDash.this, "Select status", Toast.LENGTH_SHORT).show();
                    } else if (myComment.isEmpty()) {
                        Toast.makeText(ManagerDash.this, "Enter some comments", Toast.LENGTH_SHORT).show();
                        Comment.setError("required");
                        Comment.requestFocus();
                    } else if (myPricer.isEmpty() & mySpinner.equals("Approved")) {
                        Toast.makeText(ManagerDash.this, "Enter Price Tag", Toast.LENGTH_SHORT).show();
                        PriceTag.setError("required");
                        PriceTag.requestFocus();
                    } else {
                        StringRequest stringRequesting = new StringRequest(Request.Method.POST, Travel.UPDATE_ORDER,
                                respon -> {
                                    try {
                                        JSONObject jsonOb = new JSONObject(respon);
                                        Log.e("response ", respon);
                                        String msg = jsonOb.getString("message");
                                        int statuses = jsonOb.getInt("success");
                                        if (statuses == 1) {
                                            Toast.makeText(ManagerDash.this, msg, Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), ManagerDash.class));

                                        } else if (statuses == 9) {
                                            PriceTag.setError(msg);
                                            PriceTag.requestFocus();
                                        } else if (statuses == 0) {
                                            Toast.makeText(ManagerDash.this, msg, Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(ManagerDash.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                    }

                                }, error -> {
                            Toast.makeText(ManagerDash.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("purchase_id", product.getPurchase_id());
                                params.put("counter_id", product.getCounter_id());
                                params.put("category", product.getCategory());
                                params.put("type", product.getType());
                                params.put("sup_price", product.getPrice());
                                if (mySpinner.equals("Approved")) {
                                    params.put("price", myPricer);
                                } else {
                                    params.put("price", "No Action");
                                }
                                params.put("quantity", product.getQuantity());
                                params.put("description", product.getDescription());
                                params.put("comment", myComment);
                                params.put("status", mySpinner);
                                return params;
                            }
                        };//counter_id,category,type,sup_price,price,quantity,description,image,comment,status
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequesting);
                    }
                });
                builder.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            });
            alert.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.getWindow().setGravity(Gravity.BOTTOM);

        });
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.delivery:
                    startActivity(new Intent(getApplicationContext(), Delivery.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.homing:
                    vibrator.vibrate(500);
                    return true;
                case R.id.tender:
                    startActivity(new Intent(getApplicationContext(), Tenders.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
               /* case R.id.store:
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

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.VIEW_PRODUCTS,
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
                            blackJack = new ProductAdapter(ManagerDash.this, R.layout.product, SubjectList);
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
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getTender() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.NEW_PRODUCTS,
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
                                countTenders.setText(" New Products " + counter);
                            }
                        } else {
                            countTenders.setText(" New Products 0");
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(ManagerDash.this);
                dialog.setTitle("My Profile");
                dialog.setIcon(R.drawable.person);
                dialog.setItems(items, (dialog1, itemer) -> {
                    if (itemer == 0) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(ManagerDash.this);
                        mydialog1.setTitle("Profile");
                        mydialog1.setMessage("Registry: " + staffMod.reg_id + "\nName: " + staffMod.fullname + "\nUsername: " + staffMod.username + "\nEmail: " + staffMod.email + "\nPhone: " + staffMod.mobile + "\nRole: " + staffMod.role + "\nStatus: " + staffMod.approval + "\nDate: " + staffMod.reg_date);
                        mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                        final AlertDialog alertDialog = mydialog1.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    } else if (itemer == 1) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(ManagerDash.this);
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