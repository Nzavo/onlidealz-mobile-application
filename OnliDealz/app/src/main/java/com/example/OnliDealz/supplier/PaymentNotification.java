package com.example.OnliDealz.supplier;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.OnliDealz.Black.IndepenAda;
import com.example.OnliDealz.Black.Independ;
import com.example.OnliDealz.Black.PrintThis;
import com.example.OnliDealz.Black.SupMod;
import com.example.OnliDealz.Black.SupSess;
import com.example.OnliDealz.Black.Travel;
import com.example.OnliDealz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PaymentNotification extends AppCompatActivity {
    SupSess supSess;
    SupMod customerModel;
    ListView listView;
    SearchView searchView;
    ArrayList<Independ> SubjectLi = new ArrayList<>();
    IndepenAda suppAda;
    Independ suppFind;
    View layer;
    TextView atxtname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Payment Notifications");
        setContentView(R.layout.activity_payment_notification);
        supSess = new SupSess(getApplicationContext());
        customerModel = supSess.getSupDetails();
        listView = findViewById(R.id.listed);
        listView.setTextFilterEnabled(true);
        searchView = findViewById(R.id.search);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            suppFind = (Independ) parent.getItemAtPosition(position);
            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentNotification.this);
            builder.setTitle("Disbursed Payment");
            Rect reco = new Rect();
            Window win = PaymentNotification.this.getWindow();
            win.getDecorView().getWindowVisibleDisplayFrame(reco);
            LayoutInflater inflater = (LayoutInflater) PaymentNotification.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflater.inflate(R.layout.true_gen, null);
            layer.setMinimumWidth((int) (reco.width() * 0.9f));
            layer.setMinimumHeight((int) (reco.height() * 0.05f));
            atxtname = layer.findViewById(R.id.txtname);
            atxtname.setText("This is to notify you that KSHs"+suppFind.getAmount() +"\nwas sent to your BusinessNumber "+customerModel.getMobile()+"\nfor the goods supplied to Onlidealz Business.\nPlease check confirmation CODE "+suppFind.getMpesa()+"\non Date "+suppFind.getReg_date());
            builder.setView(layer);
            builder.setPositiveButton("print_pdf", (dialog1, item) -> {
                print();
            });
            builder.setNegativeButton("close", (dialog, idm) -> dialog.cancel());
            AlertDialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        });
        practical();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void print() {
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        printManager.print(getString(R.string.app_name), new PrintThis(this, layer.findViewById(R.id.manned)), null);
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.payTerm,
                response -> {
                    try {
                        Independ subject;
                        SubjectLi = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String ind = jsonObject.getString("ind");
                                String mpesa = jsonObject.getString("mpesa");
                                String supplier = jsonObject.getString("supplier");
                                String amount = jsonObject.getString("amount");
                                String reg_date = jsonObject.getString("reg_date");
                                subject = new Independ(id, ind, mpesa, supplier, amount, reg_date);
                                SubjectLi.add(subject);
                            }
                            suppAda = new IndepenAda(PaymentNotification.this, R.layout.new_item, SubjectLi);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentNotification.this);
                            //builder.setTitle("Error");
                            builder.setMessage(msg);
                            builder.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentNotification.this);
                        builder.setTitle("Error");
                        builder.setMessage("An error occurred");
                        builder.show();
                        Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                }, error -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentNotification.this);
            builder.setTitle("Error");
            builder.setMessage("Failed to connect");
            builder.show();
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("supplier", customerModel.getReg_id());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}