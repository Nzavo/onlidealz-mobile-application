package com.example.OnliDealz.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.InputFilter;
import android.text.InputType;
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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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

public class Tenders extends AppCompatActivity {
    Vibrator vibrator;
    StaffMod staffMod;
    MgrSess mgrSess;
    BottomNavigationView bottomNavigationView;
    Spinner spinner;
    ListView listView;
    ArrayList<String> suppliers = new ArrayList<>();
    ArrayAdapter<String> supplierAdapter;
    RequestQueue requestQueue;
    Button button, btnSubmit;
    String items, Quantity;
    TextView ErrorText, countTenders;
    RadioGroup radioGroup;
    View layer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mgrSess = new MgrSess(getApplicationContext());
        staffMod = mgrSess.getMgrDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + staffMod.fullname);
        setContentView(R.layout.activity_tenders);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.tender);
        spinner = findViewById(R.id.spinnerSupp);
        countTenders = findViewById(R.id.counterSerial);
        button = findViewById(R.id.btnSearch);
        getTender();
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Travel.SUPPLIER, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("supplier");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String supplierEmail = jsonObject.optString("email");
                            suppliers.add(supplierEmail);
                            supplierAdapter = new ArrayAdapter<>(Tenders.this,
                                    android.R.layout.simple_spinner_item, suppliers);
                            supplierAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(supplierAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {

        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerAdapter spinnerAdapter = spinner.getAdapter();
                if (spinnerAdapter == null) {
                    button.setVisibility(View.GONE);
                } else {
                    button.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(v -> {
            final String mEmail = spinner.getSelectedItem().toString().trim();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.SUP_DEATAILS,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("response", response);
                            String status = jsonObject.getString("success");
                            if (status.equals("5")) {
                                JSONArray dataArray = jsonObject.getJSONArray("login");
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    String reg_id = dataobj.getString("reg_id").trim();
                                    String fullname = dataobj.getString("fullname").trim();
                                    String phone = dataobj.getString("phone").trim();
                                    String business_name = dataobj.getString("business_name").trim();
                                    String existence = dataobj.getString("existence").trim();
                                    AlertDialog.Builder alarmed = new AlertDialog.Builder(this);
                                    alarmed.setTitle("Supplier Basic Details");
                                    alarmed.setMessage("Name: " + fullname + "\nPhone: " + phone + "\nEmail: " + mEmail + "\nBusinessName: " + business_name + "\nNature: " + existence);
                                    alarmed.setPositiveButton("back", (dialogq, idq) -> {
                                        startActivity(new Intent(getApplicationContext(), Tenders.class));
                                    });
                                    alarmed.setNeutralButton("make_order", (dialogq, idq) -> {
                                        Uri ala =
                                                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                        MediaPlayer mv = MediaPlayer.create(getApplicationContext(), ala);
                                        mv.start();
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Tenders.this);
                                        Rect rec = new Rect();
                                        Window window = Tenders.this.getWindow();
                                        window.getDecorView().getWindowVisibleDisplayFrame(rec);
                                        LayoutInflater inflat = (LayoutInflater) Tenders.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        if (existence.equals("TV Audio & Videos")) {
                                            layer = inflat.inflate(R.layout.temple_t, null);
                                        } else if (existence.equals("Kitchen Supplies")) {
                                            layer = inflat.inflate(R.layout.temple_k, null);
                                        } else if (existence.equals("Bedroom Supplies")) {
                                            layer = inflat.inflate(R.layout.temple_b, null);
                                        } else if (existence.equals("Living Room")) {
                                            layer = inflat.inflate(R.layout.temple_l, null);
                                        } else if (existence.equals("Bathroom Supplies")) {
                                            layer = inflat.inflate(R.layout.temple_br, null);
                                        }
                                        layer.setMinimumWidth((int) (rec.width() * 0.9f));
                                        layer.setMinimumHeight((int) (rec.height() * 0.05f));
                                        radioGroup = layer.findViewById(R.id.radioType);
                                        btnSubmit = layer.findViewById(R.id.subPro);
                                        btnSubmit.setOnClickListener(vr -> {
                                            items = ((RadioButton) layer.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                                            if (items.equals("Check Product Type")) {
                                                Toast.makeText(this, "Oops!! You need to check the product", Toast.LENGTH_SHORT).show();
                                            }else {
                                                AlertDialog.Builder mydial = new AlertDialog.Builder(Tenders.this);
                                                mydial.setTitle("Confirm Order");
                                                final EditText namer = new EditText(Tenders.this);
                                                namer.setInputType(InputType.TYPE_CLASS_NUMBER);
                                                namer.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                                                namer.setHint("Enter Quantity in units");
                                                mydial.setView(namer);
                                                mydial.setPositiveButton("send", (dialogInterface17, i17) -> {
                                                    Quantity = namer.getText().toString();
                                                    if (Quantity.isEmpty()) {
                                                        Toast.makeText(Tenders.this, "Enter the Quantity to Order from the Quoted Supplier", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        AlertDialog.Builder dail = new AlertDialog.Builder(Tenders.this);
                                                        dail.setTitle("Order Quantity");
                                                        dail.setMessage("namer");
                                                        dail.setMessage("Name: " + fullname + "\nPhone: " + phone + "\nEmail: " + mEmail + "\nBusinessName: " + business_name + "\nNature: " + existence + "\n\n\nType Of Product: " + items + "\nQuantity: " + Quantity + "\n\nDo want to Send the Above Order?");
                                                        dail.setPositiveButton("yes", (dialogInterface20, i20) -> {
                                                            StringRequest stringRequesting = new StringRequest(Request.Method.POST, Travel.UPLOAD_CATEGORY,
                                                                    respon -> {
                                                                        try {
                                                                            JSONObject jsonOb = new JSONObject(respon);
                                                                            Log.e("response ", respon);
                                                                            String msg = jsonOb.getString("message");
                                                                            int statuses = jsonOb.getInt("success");
                                                                            if (statuses == 1) {
                                                                                Toast.makeText(Tenders.this, msg, Toast.LENGTH_LONG).show();
                                                                                startActivity(new Intent(getApplicationContext(), Tenders.class));

                                                                            } else if (statuses == 0) {
                                                                                Toast.makeText(Tenders.this, msg, Toast.LENGTH_SHORT).show();
                                                                            }

                                                                        } catch (JSONException e) {
                                                                            e.printStackTrace();
                                                                            Toast.makeText(Tenders.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    }, error -> {
                                                                Toast.makeText(Tenders.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                                                            }) {
                                                                @Override
                                                                protected Map<String, String> getParams() {
                                                                    Map<String, String> params = new HashMap<>();
                                                                    params.put("supplier_id", reg_id);
                                                                    params.put("business_name", business_name);
                                                                    params.put("category", existence);
                                                                    params.put("type", items);
                                                                    params.put("qnty", Quantity);
                                                                    return params;
                                                                }
                                                            };//supplier_id,business_name,category,type,qnty
                                                            RequestQueue requestQueue = Volley.newRequestQueue(this);
                                                            requestQueue.add(stringRequesting);
                                                        });
                                                        dail.setNegativeButton("no", (dialogInterface20, i20) -> dialogInterface20.cancel());
                                                        final AlertDialog alertDialog = dail.create();
                                                        alertDialog.setCanceledOnTouchOutside(false);
                                                        alertDialog.show();
                                                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                                                    }

                                                });

                                                mydial.setNegativeButton("exit", (dialogInterface17, i17) -> dialogInterface17.cancel());
                                                final AlertDialog alertDialog = mydial.create();
                                                alertDialog.setCanceledOnTouchOutside(false);
                                                alertDialog.show();
                                                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                                            }
                                        });
                                        builder.setView(layer);
                                        builder.setTitle("Check Product to Order");
                                        builder.setNegativeButton("exit", (dialogInterface17, i17) -> dialogInterface17.cancel());
                                        final AlertDialog alertDialog = builder.create();
                                        alertDialog.setCanceledOnTouchOutside(false);
                                        alertDialog.show();
                                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                                    });
                                    AlertDialog alertDialog = alarmed.create();
                                    alertDialog.show();
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Tenders.this, "An Error occurred", Toast.LENGTH_SHORT).show();
                        }

                    }, error -> {
                Toast.makeText(Tenders.this, "There was a connection Error.", Toast.LENGTH_SHORT).show();

            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", mEmail);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        });
        requestQueue.add(jsonObjectRequest);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.delivery:
                    startActivity(new Intent(getApplicationContext(), Delivery.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.tender:
                    vibrator.vibrate(500);
                    return true;
                case R.id.homing:
                    startActivity(new Intent(getApplicationContext(), ManagerDash.class));
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

    private void getTender() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.PENDING_TENDERS,
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
                                countTenders.setText(" Past Tenders " + counter);
                            }
                        } else {
                            countTenders.setText(" Past Tenders 0");
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(Tenders.this);
                dialog.setTitle("My Profile");
                dialog.setIcon(R.drawable.person);
                dialog.setItems(items, (dialog1, itemer) -> {
                    if (itemer == 0) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Tenders.this);
                        mydialog1.setTitle("Profile");
                        mydialog1.setMessage("Registry: " + staffMod.reg_id + "\nName: " + staffMod.fullname + "\nUsername: " + staffMod.username + "\nEmail: " + staffMod.email + "\nPhone: " + staffMod.mobile + "\nRole: " + staffMod.role + "\nStatus: " + staffMod.approval + "\nDate: " + staffMod.reg_date);
                        mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                        final AlertDialog alertDialog = mydialog1.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    } else if (itemer == 1) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(Tenders.this);
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