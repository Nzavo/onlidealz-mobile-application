package com.example.OnliDealz.finance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.OnliDealz.Black.SupplMode;
import com.example.OnliDealz.Black.SuppliAda;
import com.example.OnliDealz.Black.Travel;
import com.example.OnliDealz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SupplierPayment extends AppCompatActivity {
    ListView listView;
    SearchView searchView;
    ArrayList<SupplMode> SubjectLi = new ArrayList<>();
    SuppliAda suppAda;
    SupplMode suppFind;
    View layer;
    Spinner spinner;
    EditText Account, Cheque;
    Button Choice, Send, Show, Hide;
    RelativeLayout relativeLayout;
    TextView ErrorText, reportErrors;
    EditText Area, Street, House, MPESA;
    Button button1, button2, button3, button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Supplier Payment");
        setContentView(R.layout.activity_supplier_payment);
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            suppFind = (SupplMode) parent.getItemAtPosition(position);
            AlertDialog.Builder builderz = new AlertDialog.Builder(SupplierPayment.this);
            builderz.setTitle("Disbursement");
            builderz.setMessage("SupplierID: " + suppFind.getSupplier() + "\nAmount: KSH" + suppFind.getPayment() + "\nDisbursement: " + suppFind.getDisburse());
            if (suppFind.getDisburse().equals("Pending")) {
                builderz.setPositiveButton("disburse", (dialog, idm) -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SupplierPayment.this);
                    builder.setTitle("Payment By MPESA");
                    builder.setMessage("SupplierID: " + suppFind.getSupplier() + "\nDisbursement: " + suppFind.getDisburse() + "\nPayableAmount: KSH" + suppFind.getPayment());
                    Rect retan = new Rect();
                    Window window8 = SupplierPayment.this.getWindow();
                    window8.getDecorView().getWindowVisibleDisplayFrame(retan);
                    LayoutInflater layoutInflater1 = (LayoutInflater) SupplierPayment.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                            StringRequest stringRequesting = new StringRequest(Request.Method.POST, Travel.newPay,
                                    respon -> {
                                        try {
                                            JSONObject jsonOb = new JSONObject(respon);
                                            Log.e("response ", respon);
                                            String msg = jsonOb.getString("message");
                                            int statuses = jsonOb.getInt("success");
                                            if (statuses == 1) {
                                                Toast.makeText(SupplierPayment.this, msg, Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(getApplicationContext(), SupplierPayment.class));

                                            } else if (statuses == 0) {
                                                reportErrors.setText(msg);
                                                reportErrors.setVisibility(View.VISIBLE);
                                                Toast.makeText(SupplierPayment.this, msg, Toast.LENGTH_SHORT).show();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            reportErrors.setText("An error occurred");
                                            reportErrors.setVisibility(View.VISIBLE);
                                            Toast.makeText(SupplierPayment.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                        }

                                    }, error -> {
                                reportErrors.setText("Failed to connect");
                                reportErrors.setVisibility(View.VISIBLE);
                                Toast.makeText(SupplierPayment.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                            }) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("supplier", suppFind.getSupplier());
                                    params.put("mpesa", muCode);
                                    params.put("amount", suppFind.getPayment());
                                    return params;
                                }
                            };//quantity,order,shipping,total,location,mpesa,customer,phone,reg
                            //disburse,shipper,shipment,customer
                            RequestQueue requestQueue = Volley.newRequestQueue(this);
                            requestQueue.add(stringRequesting);
                        }
                    });
                    builder.setView(layer);
                    builder.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                });
            }
            builderz.setNegativeButton("close", (dialog, idm) -> dialog.cancel());
            AlertDialog alertDialog = builderz.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });
        practical();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.getPayTo,
                response -> {
                    try {
                        SupplMode subject;
                        SubjectLi = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String supplier = jsonObject.getString("supplier");
                                String payment = jsonObject.getString("payment");
                                String disburse = jsonObject.getString("disburse");
                                subject = new SupplMode(supplier, payment, disburse);
                                SubjectLi.add(subject);
                            }
                            suppAda = new SuppliAda(SupplierPayment.this, R.layout.new_item, SubjectLi);
                            listView.setAdapter(suppAda);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    suppAda.getFilter().filter(newText);
                                    return false;
                                }
                            });
                        } else if (success == 0) {
                            String msg = jsonObject.getString("mine");
                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(SupplierPayment.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(SupplierPayment.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(SupplierPayment.this);
            builder.setTitle("Error");
            builder.setMessage("Failed to connect");
            builder.show();
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

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
}