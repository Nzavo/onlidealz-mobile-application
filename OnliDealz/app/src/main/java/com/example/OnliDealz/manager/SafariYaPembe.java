package com.example.OnliDealz.manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.OnliDealz.Black.CustMessAda;
import com.example.OnliDealz.Black.MassagingAda;
import com.example.OnliDealz.Black.MessagingMode;
import com.example.OnliDealz.Black.Travel;
import com.example.OnliDealz.R;
import com.example.OnliDealz.finance.FiannceRate;
import com.example.OnliDealz.finance.FinanceDash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SafariYaPembe extends AppCompatActivity {
    CustMessAda produAda;
    ArrayList<MessagingMode> SubjectList = new ArrayList<>();
    MessagingMode prodMode;
    ListView listView;
    SearchView searchView;
    TextView ErrorText;
    View layer;
    RatingBar rQuality;
    float fQuality;
    EditText former;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Messages");
        setContentView(R.layout.activity_safari_ya_pembe);
        ErrorText = findViewById(R.id.nothig);
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.recycler);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            prodMode = (MessagingMode) parent.getItemAtPosition(position);
            AlertDialog.Builder alert = new AlertDialog.Builder(SafariYaPembe.this);
            Rect reco = new Rect();
            Window win = SafariYaPembe.this.getWindow();
            win.getDecorView().getWindowVisibleDisplayFrame(reco);
            LayoutInflater inflater = (LayoutInflater) SafariYaPembe.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflater.inflate(R.layout.never_disappont, null);
            layer.setMinimumWidth((int) (reco.width() * 0.9f));
            layer.setMinimumHeight((int) (reco.height() * 0.02f));
            rQuality = layer.findViewById(R.id.qualityRating);
            former = layer.findViewById(R.id.neverDisappoint);
            alert.setTitle("Sender:+" + prodMode.getName() + ", " + prodMode.getPost());
            fQuality = Float.parseFloat(prodMode.getRate());
            rQuality.setRating(fQuality);
            alert.setView(layer);
            if (prodMode.getReply().equals("Reply Pending")) {
                alert.setMessage("SenderID: " + prodMode.getSender() + "\nMessage: " + prodMode.getMessage() + "\nDate: " + prodMode.getSend_date());
                alert.setPositiveButton("reply", (dialog, idm) -> {
                    final String myPeople = former.getText().toString().trim();
                    if (myPeople.isEmpty()) {
                        Toast.makeText(this, "some reply needed", Toast.LENGTH_SHORT).show();
                    } else {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.editChat,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.e("response ", response);
                                        String msg = jsonObject.getString("message");
                                        int status = jsonObject.getInt("success");
                                        if (status == 1) {
                                            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), SafariYaPembe.class));
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
                                params.put("id", prodMode.getId());
                                params.put("reply", myPeople);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);
                    }

                });
            } else {
                former.setVisibility(View.GONE);
                alert.setMessage("SenderID: " + prodMode.getSender() + "\nMessage: " + prodMode.getMessage() + "\nDate: " + prodMode.getSend_date() + "\nMy Reply: " + prodMode.getReply() + "\nReplyDate: " + prodMode.getReply_date());
            }
            alert.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.getWindow().setGravity(Gravity.CENTER);
        });
       practical();
    }

    private void practical() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.weeeWee,
                response -> {
                    try {
                        MessagingMode subject;
                        SubjectList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String rate = jsonObject.getString("rate");
                                String message = jsonObject.getString("message");
                                String receiver = jsonObject.getString("receiver");
                                String sender = jsonObject.getString("sender");
                                String name = jsonObject.getString("name");
                                String phone = jsonObject.getString("phone");
                                String post = jsonObject.getString("post");
                                String send_date = jsonObject.getString("send_date");
                                String reply = jsonObject.getString("reply");
                                String reply_date = jsonObject.getString("reply_date");
                                subject = new MessagingMode(id, rate, message, receiver, sender, name, phone, post, send_date, reply, reply_date);
                                SubjectList.add(subject);
                            }
                            produAda = new CustMessAda(SafariYaPembe.this, R.layout.named_man, SubjectList);
                            listView.setAdapter(produAda);
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String text) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    produAda.getFilter().filter(newText);
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
}