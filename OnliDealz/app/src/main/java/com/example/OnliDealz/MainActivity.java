package com.example.OnliDealz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.OnliDealz.Black.CustomerMod;
import com.example.OnliDealz.Black.CustomerSess;
import com.example.OnliDealz.Black.DriveMd;
import com.example.OnliDealz.Black.DriveSess;
import com.example.OnliDealz.Black.FinaSess;
import com.example.OnliDealz.Black.MgrSess;
import com.example.OnliDealz.Black.StaffMod;
import com.example.OnliDealz.Black.SupMod;
import com.example.OnliDealz.Black.SupSess;
import com.example.OnliDealz.Black.Travel;
import com.example.OnliDealz.customer.CustDash;
import com.example.OnliDealz.driver.DriverDash;
import com.example.OnliDealz.finance.FinanceDash;
import com.example.OnliDealz.manager.ManagerDash;
import com.example.OnliDealz.supplier.SupplierDash;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    LinearLayout Customer, Driver, Supplier, Keeper, Finance;
    Vibrator vibrator;
    CustomerMod customerMod;
    CustomerSess customerSess;
    DriveMd driveMd;
    DriveSess driveSess;
    SupMod supMod;
    SupSess supSess;
    StaffMod staffMod;
    FinaSess finaSess;
    MgrSess mgrSess;
    EditText myUsername, myFullname, myMobile, myAddress, myEmail, myPass, myRepeat, myLicense, myBusiReg, myBusiName;
    Spinner myCity, myRole, myExistence;
    TextView ErrorText, newRegistry, newReset;
    Button logged;
    RadioGroup radioGroup;
    String Gender;
    private static int MIN_LENGTH = 8;
    private static int MINIMUM_LENGTH = 10;
    private static int MINI_IN = 4;
    RelativeLayout relativeLayout;
    View layer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Customer = findViewById(R.id.customer);
        Driver = findViewById(R.id.driver);
        Supplier = findViewById(R.id.supplier);
        Keeper = findViewById(R.id.keeper);
        Finance = findViewById(R.id.finance);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        customerSess = new CustomerSess(getApplicationContext());
        customerMod = customerSess.getCustDetails();
        driveSess = new DriveSess(getApplicationContext());
        driveMd = driveSess.getDriDetails();
        supSess = new SupSess(getApplicationContext());
        supMod = supSess.getSupDetails();
        finaSess = new FinaSess(getApplicationContext());
        staffMod = finaSess.getFinaDetails();
        mgrSess = new MgrSess(getApplicationContext());
        staffMod = mgrSess.getMgrDetails();
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.homer);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.about:
                    AlertDialog.Builder mydialog1 = new AlertDialog.Builder(MainActivity.this);
                    mydialog1.setTitle("Basic Information");
                    mydialog1.setMessage(getString(R.string.about));
                    mydialog1.setIcon(R.drawable.about);
                    mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                    final AlertDialog alertDialog = mydialog1.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                    alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.homer:
                    vibrator.vibrate(500);
                    return true;
                case R.id.faqs:
                    AlertDialog.Builder mydialog13 = new AlertDialog.Builder(MainActivity.this);
                    mydialog13.setTitle("Help");
                    mydialog13.setIcon(R.drawable.help);
                    mydialog13.setMessage(getString(R.string.help));
                    mydialog13.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                    final AlertDialog alertDialog3 = mydialog13.create();
                    alertDialog3.setCanceledOnTouchOutside(false);
                    alertDialog3.show();
                    alertDialog3.getWindow().setGravity(Gravity.BOTTOM);
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
        Customer.setOnClickListener(v -> {
            getCust();
        });
        Driver.setOnClickListener(v -> {
            getDrive();
        });
        Keeper.setOnClickListener(v -> {
            getKeeper();
        });
        Finance.setOnClickListener(v -> {
            getFina();
        });
        Supplier.setOnClickListener(v -> {
            getSup();
        });
    }

    private void getFina() {
        /*if (finaSess.inFinaTry()) {
            startActivity(new Intent(getApplicationContext(), FinanceDash.class));
        } else {*/
            Toast.makeText(MainActivity.this, "To create new account Click the login button to redirect", Toast.LENGTH_LONG).show();
           /* Uri alar =
                    RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            MediaPlayer mvk = MediaPlayer.create(getApplicationContext(), alar);
            mvk.start();*/
            AlertDialog.Builder alerto = new AlertDialog.Builder(this);
            Rect reco = new Rect();
            Window windower = MainActivity.this.getWindow();
            windower.getDecorView().getWindowVisibleDisplayFrame(reco);
            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflater.inflate(R.layout.login, null);
            layer.setMinimumWidth((int) (reco.width() * 0.9f));
            layer.setMinimumHeight((int) (reco.height() * 0.2f));
            myUsername = layer.findViewById(R.id.editUser);
            myPass = layer.findViewById(R.id.editPass);
            ErrorText = layer.findViewById(R.id.editError);
            logged = layer.findViewById(R.id.editLogin);
            newRegistry = layer.findViewById(R.id.editSignUp);
            newReset = layer.findViewById(R.id.editUpdate);
            relativeLayout = layer.findViewById(R.id.editMentor);
            logged.setOnClickListener(view -> {
                final String User = myUsername.getText().toString().trim();
                final String Pass = myPass.getText().toString().trim();
                if (User.isEmpty() || Pass.isEmpty()) {
                    ErrorText.setText("Error!! There is a required Field");
                    relativeLayout.setVisibility(View.VISIBLE);
                    ErrorText.setVisibility(View.VISIBLE);
                } else {
                    relativeLayout.setVisibility(View.GONE);
                    ErrorText.setVisibility(View.GONE);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.FNLG,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.e("response", response);
                                    String msg = jsonObject.getString("message");
                                    String status = jsonObject.getString("success");
                                    if (status.equals("1")) {

                                        JSONArray dataArray = jsonObject.getJSONArray("login");
                                        for (int i = 0; i < dataArray.length(); i++) {

                                            JSONObject dataobj = dataArray.getJSONObject(i);
                                            String reg_id = dataobj.getString("reg_id");
                                            String fullname = dataobj.getString("fullname");
                                            String username = dataobj.getString("username");
                                            String mobile = dataobj.getString("mobile");
                                            String email = dataobj.getString("email");
                                            String role = dataobj.getString("role");
                                            String approval = dataobj.getString("approval");
                                            String reg_date = dataobj.getString("reg_date");
                                            finaSess.tryFina(reg_id, fullname, username, mobile, email, role, approval, reg_date);
                                        }
                                        startActivity(new Intent(getApplicationContext(), FinanceDash.class));


                                    } else if (status.equals("0")) {
                                        ErrorText.setText(msg);
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        ErrorText.setVisibility(View.VISIBLE);

                                    } else if (status.equals("2")) {

                                        JSONArray dataArray = jsonObject.getJSONArray("login");
                                        for (int i = 0; i < dataArray.length(); i++) {
                                            JSONObject dataobj = dataArray.getJSONObject(i);
                                            String remarks = dataobj.getString("comment").trim();
                                            ErrorText.setText("Oops!! " + msg);
                                            relativeLayout.setVisibility(View.VISIBLE);
                                            ErrorText.setVisibility(View.VISIBLE);
                                            Toast.makeText(getApplicationContext(), remarks, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    ErrorText.setText("Error!! An error occurred");
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    ErrorText.setVisibility(View.VISIBLE);
                                    //Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();

                                }

                            }, error -> {
                        ErrorText.setText("Error!! Check your root connection");
                        relativeLayout.setVisibility(View.VISIBLE);
                        ErrorText.setVisibility(View.VISIBLE);

                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("username", User);
                            params.put("password", Pass);

                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
            });
            newReset.setOnClickListener(nee -> {

                AlertDialog.Builder alarmed = new AlertDialog.Builder(this);
                Rect rec = new Rect();
                Window window = MainActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rec);
                LayoutInflater inflat = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layer = inflat.inflate(R.layout.reset, null);
                layer.setMinimumWidth((int) (rec.width() * 0.9f));
                layer.setMinimumHeight((int) (rec.height() * 0.25f));
                myUsername = layer.findViewById(R.id.editUser);
                myPass = layer.findViewById(R.id.editPass);
                myRepeat = layer.findViewById(R.id.editPassRepeat);
                myCity = layer.findViewById(R.id.editCity);
                ErrorText = layer.findViewById(R.id.editError);
                logged = layer.findViewById(R.id.editReset);
                logged.setOnClickListener(view -> {
                    final String User = myUsername.getText().toString().trim();
                    final String Pass = myPass.getText().toString().trim();
                    final String Repeat = myRepeat.getText().toString().trim();
                    int length = Pass.length();
                    if (User.isEmpty() || Pass.isEmpty() || Repeat.isEmpty()) {
                        ErrorText.setText("Error!! There is a missing Field");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (length < MIN_LENGTH) {
                        ErrorText.setText("Oops!! Enter a Strong Password");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (!Repeat.equals(Pass)) {
                        ErrorText.setText("Oops!! Passwords do not match");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else {
                        relativeLayout.setVisibility(View.GONE);
                        ErrorText.setVisibility(View.GONE);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.FNUP,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.e("response ", response);
                                        String msg = jsonObject.getString("message");
                                        int status = jsonObject.getInt("success");
                                        if (status == 1) {
                                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            finish();

                                        } else if (status == 0) {
                                            ErrorText.setText(msg);
                                            relativeLayout.setVisibility(View.VISIBLE);
                                            ErrorText.setVisibility(View.VISIBLE);

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        ErrorText.setText("Error!! An error occurred");
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        ErrorText.setVisibility(View.VISIBLE);
                                        //Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();

                                    }

                                }, error -> {
                            ErrorText.setText("Error!! Check your root connection");
                            relativeLayout.setVisibility(View.VISIBLE);
                            ErrorText.setVisibility(View.VISIBLE);

                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("username", User);
                                params.put("password", Pass);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);
                    }
                });
                alarmed.setTitle("Reset");
                alarmed.setView(layer);
                alarmed.setPositiveButton("back", (dialogq, idq) -> {
                    getFina();
                });
                AlertDialog alertDialog = alarmed.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            });
            newRegistry.setOnClickListener(veve -> {

                AlertDialog.Builder alarmed = new AlertDialog.Builder(this);
                Rect rec = new Rect();
                Window window = MainActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rec);
                LayoutInflater inflat = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layer = inflat.inflate(R.layout.staff_reg, null);
                layer.setMinimumWidth((int) (rec.width() * 0.9f));
                layer.setMinimumHeight((int) (rec.height() * 0.5f));
                myUsername = layer.findViewById(R.id.editUser);
                myFullname = layer.findViewById(R.id.editFull);
                myMobile = layer.findViewById(R.id.editMobile);
                myEmail = layer.findViewById(R.id.editMail);
                myPass = layer.findViewById(R.id.editPass);
                myRepeat = layer.findViewById(R.id.editPassRepeat);
                ErrorText = layer.findViewById(R.id.editError);
                logged = layer.findViewById(R.id.editSubmit);
                myRole = layer.findViewById(R.id.editWork);
                ArrayAdapter<CharSequence> adapter8 = ArrayAdapter.createFromResource(this, R.array.Role, android.R.layout.simple_spinner_item);
                adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                myRole.setAdapter(adapter8);
                logged.setOnClickListener(view -> {
                    final String User = myUsername.getText().toString().trim();
                    final String Full = myFullname.getText().toString().trim();
                    final String Pass = myPass.getText().toString().trim();
                    final String Mobi = myMobile.getText().toString().trim();
                    final String Mail = myEmail.getText().toString().trim();
                    final String Repeat = myRepeat.getText().toString().trim();
                    final String Role = myRole.getSelectedItem().toString().trim();
                    String emailPattern = "[a-z0-9._%+-]+@gmail+\\.[a-z]{2,4}";
                    String emailPattern2 = "[a-z0-9._%+-]+@yahoo+\\.[a-z]{2,4}";
                    int length = Pass.length();
                    int lens = Mobi.length();
                    if (User.isEmpty() || Full.isEmpty() || Pass.isEmpty() || Mobi.isEmpty() || Repeat.isEmpty()) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Error!! There is a missing Field");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (length < MIN_LENGTH) {
                        ErrorText.setText("Oops!! Your password is weak");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (lens < MINIMUM_LENGTH) {
                        ErrorText.setText("Oops!! Your Phone is invalid");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (!Mail.matches(emailPattern) & !Mail.matches(emailPattern2)) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Your Email is invalid");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (Role.equals("Select Work")) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Select type Of Work");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (!Repeat.equals(Pass)) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Passwords do not match");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.STRG,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.e("response ", response);
                                        String msg = jsonObject.getString("message");
                                        int status = jsonObject.getInt("success");
                                        if (status == 1) {
                                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                        } else if (status == 0) {
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
                                params.put("fullname", Full);
                                params.put("username", User);
                                params.put("mobile", Mobi);
                                params.put("role", Role);
                                params.put("email", Mail);
                                params.put("password", Pass);
                                return params;
                            }
                        };//fullname,username,mobile,role,email,password
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);
                    }
                });

                alarmed.setTitle("Register");
                alarmed.setView(layer);
                alarmed.setPositiveButton("back", (dialogq, idq) -> {
                    getFina();
                });
                AlertDialog alertDialog = alarmed.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            });
            alerto.setTitle("Login Finance");
            alerto.setView(layer);
            alerto.setPositiveButton("back", (dialogq, idq) -> {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            });
            AlertDialog alertDialog = alerto.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        }
    //}

    private void getSup() {
        /*if (supSess.inSupTry()) {
            startActivity(new Intent(getApplicationContext(), SupplierDash.class));
        } else {*/
            Toast.makeText(MainActivity.this, "To create new account Click the login button to redirect", Toast.LENGTH_LONG).show();

            AlertDialog.Builder alerto = new AlertDialog.Builder(this);
            Rect reco = new Rect();
            Window windower = MainActivity.this.getWindow();
            windower.getDecorView().getWindowVisibleDisplayFrame(reco);
            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflater.inflate(R.layout.login, null);
            layer.setMinimumWidth((int) (reco.width() * 0.9f));
            layer.setMinimumHeight((int) (reco.height() * 0.2f));
            myUsername = layer.findViewById(R.id.editUser);
            myPass = layer.findViewById(R.id.editPass);
            ErrorText = layer.findViewById(R.id.editError);
            logged = layer.findViewById(R.id.editLogin);
            newRegistry = layer.findViewById(R.id.editSignUp);
            newReset = layer.findViewById(R.id.editUpdate);
            relativeLayout = layer.findViewById(R.id.editMentor);
            logged.setOnClickListener(view -> {
                final String User = myUsername.getText().toString().trim();
                final String Pass = myPass.getText().toString().trim();
                if (User.isEmpty() || Pass.isEmpty()) {
                    ErrorText.setText("Error!! There is a required Field");
                    relativeLayout.setVisibility(View.VISIBLE);
                    ErrorText.setVisibility(View.VISIBLE);
                } else {
                    relativeLayout.setVisibility(View.GONE);
                    ErrorText.setVisibility(View.GONE);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.SPLG,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.e("response", response);
                                    String msg = jsonObject.getString("message");
                                    String status = jsonObject.getString("success");
                                    if (status.equals("1")) {

                                        JSONArray dataArray = jsonObject.getJSONArray("login");
                                        for (int i = 0; i < dataArray.length(); i++) {

                                            JSONObject dataobj = dataArray.getJSONObject(i);
                                            String reg_id = dataobj.getString("reg_id");
                                            String business_registry = dataobj.getString("business_registry");
                                            String fullname = dataobj.getString("fullname");
                                            String username = dataobj.getString("username");
                                            String business_name = dataobj.getString("business_name");
                                            String existence = dataobj.getString("existence");
                                            String mobile = dataobj.getString("mobile");
                                            String address = dataobj.getString("address");
                                            String email = dataobj.getString("email");
                                            String approval = dataobj.getString("approval");
                                            String reg_date = dataobj.getString("reg_date");
                                            supSess.trySup(reg_id, business_registry, fullname, username, business_name, existence, mobile, address, email, approval, reg_date);
                                        }
                                        startActivity(new Intent(getApplicationContext(), SupplierDash.class));


                                    } else if (status.equals("0")) {
                                        ErrorText.setText(msg);
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        ErrorText.setVisibility(View.VISIBLE);

                                    } else if (status.equals("2")) {

                                        JSONArray dataArray = jsonObject.getJSONArray("login");
                                        for (int i = 0; i < dataArray.length(); i++) {
                                            JSONObject dataobj = dataArray.getJSONObject(i);
                                            String remarks = dataobj.getString("comment").trim();
                                            ErrorText.setText("Oops!! " + msg);
                                            relativeLayout.setVisibility(View.VISIBLE);
                                            ErrorText.setVisibility(View.VISIBLE);
                                            Toast.makeText(getApplicationContext(), remarks, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    ErrorText.setText("Error!! An error occurred");
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    ErrorText.setVisibility(View.VISIBLE);
                                    //Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();

                                }

                            }, error -> {
                        ErrorText.setText("Error!! Check your root connection");
                        relativeLayout.setVisibility(View.VISIBLE);
                        ErrorText.setVisibility(View.VISIBLE);

                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("username", User);
                            params.put("password", Pass);

                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
            });
            newReset.setOnClickListener(nee -> {

                AlertDialog.Builder alarmed = new AlertDialog.Builder(this);
                Rect rec = new Rect();
                Window window = MainActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rec);
                LayoutInflater inflat = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layer = inflat.inflate(R.layout.reset, null);
                layer.setMinimumWidth((int) (rec.width() * 0.9f));
                layer.setMinimumHeight((int) (rec.height() * 0.25f));
                myUsername = layer.findViewById(R.id.editUser);
                myPass = layer.findViewById(R.id.editPass);
                myRepeat = layer.findViewById(R.id.editPassRepeat);
                myCity = layer.findViewById(R.id.editCity);
                ErrorText = layer.findViewById(R.id.editError);
                logged = layer.findViewById(R.id.editReset);
                logged.setOnClickListener(view -> {
                    final String User = myUsername.getText().toString().trim();
                    final String Pass = myPass.getText().toString().trim();
                    final String Repeat = myRepeat.getText().toString().trim();
                    int length = Pass.length();
                    if (User.isEmpty() || Pass.isEmpty() || Repeat.isEmpty()) {
                        ErrorText.setText("Error!! There is a missing Field");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (length < MIN_LENGTH) {
                        ErrorText.setText("Oops!! Enter a Strong Password");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (!Repeat.equals(Pass)) {
                        ErrorText.setText("Oops!! Passwords do not match");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else {
                        relativeLayout.setVisibility(View.GONE);
                        ErrorText.setVisibility(View.GONE);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.SPUP,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.e("response ", response);
                                        String msg = jsonObject.getString("message");
                                        int status = jsonObject.getInt("success");
                                        if (status == 1) {
                                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            finish();

                                        } else if (status == 0) {
                                            ErrorText.setText(msg);
                                            relativeLayout.setVisibility(View.VISIBLE);
                                            ErrorText.setVisibility(View.VISIBLE);

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        ErrorText.setText("Error!! An error occurred");
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        ErrorText.setVisibility(View.VISIBLE);
                                        //Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();

                                    }

                                }, error -> {
                            ErrorText.setText("Error!! Check your root connection");
                            relativeLayout.setVisibility(View.VISIBLE);
                            ErrorText.setVisibility(View.VISIBLE);

                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("username", User);
                                params.put("password", Pass);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);
                    }
                });
                alarmed.setTitle("Reset");
                alarmed.setView(layer);
                alarmed.setPositiveButton("back", (dialogq, idq) -> {
                    getSup();
                });
                AlertDialog alertDialog = alarmed.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            });
            newRegistry.setOnClickListener(veve -> {

                AlertDialog.Builder alarmed = new AlertDialog.Builder(this);
                Rect rec = new Rect();
                Window window = MainActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rec);
                LayoutInflater inflat = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layer = inflat.inflate(R.layout.sup_reg, null);
                layer.setMinimumWidth((int) (rec.width() * 0.9f));
                layer.setMinimumHeight((int) (rec.height() * 0.5f));
                myUsername = layer.findViewById(R.id.editUser);
                myFullname = layer.findViewById(R.id.editFull);
                myMobile = layer.findViewById(R.id.editMobile);
                myEmail = layer.findViewById(R.id.editMail);
                myPass = layer.findViewById(R.id.editPass);
                myRepeat = layer.findViewById(R.id.editPassRepeat);
                myCity = layer.findViewById(R.id.editCity);
                myAddress = layer.findViewById(R.id.editAddress);
                ErrorText = layer.findViewById(R.id.editError);
                logged = layer.findViewById(R.id.editSubmit);
                myBusiName = layer.findViewById(R.id.editBusinessName);
                myBusiReg = layer.findViewById(R.id.editBusinessReg);
                myExistence = layer.findViewById(R.id.editBusinessExistence);
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.City, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                myCity.setAdapter(adapter1);
                ArrayAdapter<CharSequence> adapter14 = ArrayAdapter.createFromResource(this, R.array.Nature, android.R.layout.simple_spinner_item);
                adapter14.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                myExistence.setAdapter(adapter14);
                myCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String mSpinner = myCity.getSelectedItem().toString();
                        validateRegSpinner(mSpinner);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                logged.setOnClickListener(view -> {
                    final String User = myUsername.getText().toString().trim();
                    final String Full = myFullname.getText().toString().trim();
                    final String Reg = myBusiReg.getText().toString().trim();
                    final String Name = myBusiName.getText().toString().trim();
                    final String Pass = myPass.getText().toString().trim();
                    final String Mobi = myMobile.getText().toString().trim();
                    final String Mail = myEmail.getText().toString().trim();
                    final String Repeat = myRepeat.getText().toString().trim();
                    final String City = myCity.getSelectedItem().toString().trim();
                    final String Exit = myExistence.getSelectedItem().toString().trim();
                    final String Add = myAddress.getText().toString().trim();
                    String emailPattern = "[a-z0-9._%+-]+@gmail+\\.[a-z]{2,4}";
                    String emailPattern2 = "[a-z0-9._%+-]+@yahoo+\\.[a-z]{2,4}";
                    int length = Pass.length();
                    int lens = Mobi.length();
                    int le = Reg.length();
                    if (User.isEmpty() || Reg.isEmpty() || Name.isEmpty() || Full.isEmpty() || Pass.isEmpty() || Mobi.isEmpty() || Repeat.isEmpty()) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Error!! There is a missing Field");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (le < MINI_IN) {
                        ErrorText.setText("Oops!! Your Business REGno. is invalid");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (Exit.equals("Select Nature")) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Select Nature of your Business");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (length < MIN_LENGTH) {
                        ErrorText.setText("Oops!! Your password is weak");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (lens < MINIMUM_LENGTH) {
                        ErrorText.setText("Oops!! Your Phone is invalid");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (!Mail.matches(emailPattern) & !Mail.matches(emailPattern2)) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Your Email is invalid");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (City.equals("Select City")) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Select City");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (Add.isEmpty()) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        myAddress.requestFocus();
                        ErrorText.setText("Failed!! There is a required field");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (!Repeat.equals(Pass)) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Passwords do not match");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.SPRG,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.e("response ", response);
                                        String msg = jsonObject.getString("message");
                                        int status = jsonObject.getInt("success");
                                        if (status == 1) {
                                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                        } else if (status == 0) {
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
                                params.put("fullname", Full);
                                params.put("username", User);
                                params.put("business_registry", Reg);
                                params.put("business_name", Name);
                                params.put("existence", Exit);
                                params.put("mobile", Mobi);
                                params.put("address", Add + ", " + City);
                                params.put("email", Mail);
                                params.put("password", Pass);
                                return params;
                            }
                        };//fullname,username,mobile,role,email,password
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);
                    }
                });

                alarmed.setTitle("Register");
                alarmed.setView(layer);
                alarmed.setPositiveButton("back", (dialogq, idq) -> {
                    getSup();
                });
                AlertDialog alertDialog = alarmed.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            });
            alerto.setTitle("Login Supplier");
            alerto.setView(layer);
            alerto.setPositiveButton("back", (dialogq, idq) -> {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            });
            AlertDialog alertDialog = alerto.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        }
   // }

    private void getKeeper() {
        /*if (mgrSess.inMgrTry()) {
            startActivity(new Intent(getApplicationContext(), ManagerDash.class));
        } else {*/
            Toast.makeText(MainActivity.this, "To create new account Click the login button to redirect", Toast.LENGTH_LONG).show();

            AlertDialog.Builder alerto = new AlertDialog.Builder(this);
            Rect reco = new Rect();
            Window windower = MainActivity.this.getWindow();
            windower.getDecorView().getWindowVisibleDisplayFrame(reco);
            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflater.inflate(R.layout.login, null);
            layer.setMinimumWidth((int) (reco.width() * 0.9f));
            layer.setMinimumHeight((int) (reco.height() * 0.2f));
            myUsername = layer.findViewById(R.id.editUser);
            myPass = layer.findViewById(R.id.editPass);
            ErrorText = layer.findViewById(R.id.editError);
            logged = layer.findViewById(R.id.editLogin);
            newRegistry = layer.findViewById(R.id.editSignUp);
            newReset = layer.findViewById(R.id.editUpdate);
            relativeLayout = layer.findViewById(R.id.editMentor);
            logged.setOnClickListener(view -> {
                final String User = myUsername.getText().toString().trim();
                final String Pass = myPass.getText().toString().trim();
                if (User.isEmpty() || Pass.isEmpty()) {
                    ErrorText.setText("Error!! There is a required Field");
                    relativeLayout.setVisibility(View.VISIBLE);
                    ErrorText.setVisibility(View.VISIBLE);
                } else {
                    relativeLayout.setVisibility(View.GONE);
                    ErrorText.setVisibility(View.GONE);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.MGLG,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.e("response", response);
                                    String msg = jsonObject.getString("message");
                                    String status = jsonObject.getString("success");
                                    if (status.equals("1")) {

                                        JSONArray dataArray = jsonObject.getJSONArray("login");
                                        for (int i = 0; i < dataArray.length(); i++) {

                                            JSONObject dataobj = dataArray.getJSONObject(i);
                                            String reg_id = dataobj.getString("reg_id");
                                            String fullname = dataobj.getString("fullname");
                                            String username = dataobj.getString("username");
                                            String mobile = dataobj.getString("mobile");
                                            String email = dataobj.getString("email");
                                            String role = dataobj.getString("role");
                                            String approval = dataobj.getString("approval");
                                            String reg_date = dataobj.getString("reg_date");
                                            mgrSess.tryMgr(reg_id, fullname, username, mobile, email, role, approval, reg_date);
                                        }
                                        startActivity(new Intent(getApplicationContext(), ManagerDash.class));


                                    } else if (status.equals("0")) {
                                        ErrorText.setText(msg);
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        ErrorText.setVisibility(View.VISIBLE);

                                    } else if (status.equals("2")) {

                                        JSONArray dataArray = jsonObject.getJSONArray("login");
                                        for (int i = 0; i < dataArray.length(); i++) {
                                            JSONObject dataobj = dataArray.getJSONObject(i);
                                            String remarks = dataobj.getString("comment").trim();
                                            ErrorText.setText("Oops!! " + msg);
                                            relativeLayout.setVisibility(View.VISIBLE);
                                            ErrorText.setVisibility(View.VISIBLE);
                                            Toast.makeText(getApplicationContext(), remarks, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    ErrorText.setText("Error!! An error occurred");
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    ErrorText.setVisibility(View.VISIBLE);
                                    //Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();

                                }

                            }, error -> {
                        ErrorText.setText("Error!! Check your root connection");
                        relativeLayout.setVisibility(View.VISIBLE);
                        ErrorText.setVisibility(View.VISIBLE);

                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("username", User);
                            params.put("password", Pass);

                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }
            });
            newReset.setOnClickListener(nee -> {

                AlertDialog.Builder alarmed = new AlertDialog.Builder(this);
                Rect rec = new Rect();
                Window window = MainActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rec);
                LayoutInflater inflat = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layer = inflat.inflate(R.layout.reset, null);
                layer.setMinimumWidth((int) (rec.width() * 0.9f));
                layer.setMinimumHeight((int) (rec.height() * 0.25f));
                myUsername = layer.findViewById(R.id.editUser);
                myPass = layer.findViewById(R.id.editPass);
                myRepeat = layer.findViewById(R.id.editPassRepeat);
                myCity = layer.findViewById(R.id.editCity);
                ErrorText = layer.findViewById(R.id.editError);
                logged = layer.findViewById(R.id.editReset);
                logged.setOnClickListener(view -> {
                    final String User = myUsername.getText().toString().trim();
                    final String Pass = myPass.getText().toString().trim();
                    final String Repeat = myRepeat.getText().toString().trim();
                    int length = Pass.length();
                    if (User.isEmpty() || Pass.isEmpty() || Repeat.isEmpty()) {
                        ErrorText.setText("Error!! There is a missing Field");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (length < MIN_LENGTH) {
                        ErrorText.setText("Oops!! Enter a Strong Password");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (!Repeat.equals(Pass)) {
                        ErrorText.setText("Oops!! Passwords do not match");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else {
                        relativeLayout.setVisibility(View.GONE);
                        ErrorText.setVisibility(View.GONE);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.MGUP,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.e("response ", response);
                                        String msg = jsonObject.getString("message");
                                        int status = jsonObject.getInt("success");
                                        if (status == 1) {
                                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            finish();

                                        } else if (status == 0) {
                                            ErrorText.setText(msg);
                                            relativeLayout.setVisibility(View.VISIBLE);
                                            ErrorText.setVisibility(View.VISIBLE);

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        ErrorText.setText("Error!! An error occurred");
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        ErrorText.setVisibility(View.VISIBLE);
                                        //Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();

                                    }

                                }, error -> {
                            ErrorText.setText("Error!! Check your root connection");
                            relativeLayout.setVisibility(View.VISIBLE);
                            ErrorText.setVisibility(View.VISIBLE);

                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("username", User);
                                params.put("password", Pass);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);
                    }
                });
                alarmed.setTitle("Reset");
                alarmed.setView(layer);
                alarmed.setPositiveButton("back", (dialogq, idq) -> {
                    getKeeper();
                });
                AlertDialog alertDialog = alarmed.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            });
            newRegistry.setOnClickListener(veve -> {

                AlertDialog.Builder alarmed = new AlertDialog.Builder(this);
                Rect rec = new Rect();
                Window window = MainActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rec);
                LayoutInflater inflat = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layer = inflat.inflate(R.layout.staff_reg, null);
                layer.setMinimumWidth((int) (rec.width() * 0.9f));
                layer.setMinimumHeight((int) (rec.height() * 0.5f));
                myUsername = layer.findViewById(R.id.editUser);
                myFullname = layer.findViewById(R.id.editFull);
                myMobile = layer.findViewById(R.id.editMobile);
                myEmail = layer.findViewById(R.id.editMail);
                myPass = layer.findViewById(R.id.editPass);
                myRepeat = layer.findViewById(R.id.editPassRepeat);
                ErrorText = layer.findViewById(R.id.editError);
                logged = layer.findViewById(R.id.editSubmit);
                myRole = layer.findViewById(R.id.editWork);
                ArrayAdapter<CharSequence> adapter8 = ArrayAdapter.createFromResource(this, R.array.Role, android.R.layout.simple_spinner_item);
                adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                myRole.setAdapter(adapter8);
                logged.setOnClickListener(view -> {
                    final String User = myUsername.getText().toString().trim();
                    final String Full = myFullname.getText().toString().trim();
                    final String Pass = myPass.getText().toString().trim();
                    final String Mobi = myMobile.getText().toString().trim();
                    final String Mail = myEmail.getText().toString().trim();
                    final String Repeat = myRepeat.getText().toString().trim();
                    final String Role = myRole.getSelectedItem().toString().trim();
                    String emailPattern = "[a-z0-9._%+-]+@gmail+\\.[a-z]{2,4}";
                    String emailPattern2 = "[a-z0-9._%+-]+@yahoo+\\.[a-z]{2,4}";
                    int length = Pass.length();
                    int lens = Mobi.length();
                    if (User.isEmpty() || Full.isEmpty() || Pass.isEmpty() || Mobi.isEmpty() || Repeat.isEmpty()) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Error!! There is a missing Field");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (length < MIN_LENGTH) {
                        ErrorText.setText("Oops!! Your password is weak");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (lens < MINIMUM_LENGTH) {
                        ErrorText.setText("Oops!! Your Phone is invalid");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (!Mail.matches(emailPattern) & !Mail.matches(emailPattern2)) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Your Email is invalid");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (Role.equals("Select Work")) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Select type Of Work");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (!Repeat.equals(Pass)) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Passwords do not match");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.STRG,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.e("response ", response);
                                        String msg = jsonObject.getString("message");
                                        int status = jsonObject.getInt("success");
                                        if (status == 1) {
                                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                        } else if (status == 0) {
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
                                params.put("fullname", Full);
                                params.put("username", User);
                                params.put("mobile", Mobi);
                                params.put("role", Role);
                                params.put("email", Mail);
                                params.put("password", Pass);
                                return params;
                            }
                        };//fullname,username,mobile,role,email,password
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);
                    }
                });

                alarmed.setTitle("Register");
                alarmed.setView(layer);
                alarmed.setPositiveButton("back", (dialogq, idq) -> {
                    getKeeper();
                });
                AlertDialog alertDialog = alarmed.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
            });
            alerto.setTitle("Inventory Manager");
            alerto.setView(layer);
            alerto.setPositiveButton("back", (dialogq, idq) -> {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            });
            AlertDialog alertDialog = alerto.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        }
   // }

    private void getDrive() {
        /*if (driveSess.inDriTry()) {
            startActivity(new Intent(getApplicationContext(), DriverDash.class));
        } else {*/
            Toast.makeText(MainActivity.this, "To create new account Click the login button to redirect", Toast.LENGTH_LONG).show();

            AlertDialog.Builder alerto = new AlertDialog.Builder(this);
            Rect reco = new Rect();
            Window windower = MainActivity.this.getWindow();
            windower.getDecorView().getWindowVisibleDisplayFrame(reco);
            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflater.inflate(R.layout.login, null);
            layer.setMinimumWidth((int) (reco.width() * 0.9f));
            layer.setMinimumHeight((int) (reco.height() * 0.2f));
            myUsername = layer.findViewById(R.id.editUser);
            myPass = layer.findViewById(R.id.editPass);
            ErrorText = layer.findViewById(R.id.editError);
            logged = layer.findViewById(R.id.editLogin);
            newRegistry = layer.findViewById(R.id.editSignUp);
            newReset = layer.findViewById(R.id.editUpdate);
            relativeLayout = layer.findViewById(R.id.editMentor);
            logged.setOnClickListener(view -> {
                final String User = myUsername.getText().toString().trim();
                final String Pass = myPass.getText().toString().trim();
                if (User.isEmpty() || Pass.isEmpty()) {
                    ErrorText.setText("Error!! There is a required Field");
                    relativeLayout.setVisibility(View.VISIBLE);
                    ErrorText.setVisibility(View.VISIBLE);
                } else {
                    relativeLayout.setVisibility(View.GONE);
                    ErrorText.setVisibility(View.GONE);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.DRLG,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.e("response", response);
                                    String msg = jsonObject.getString("message");
                                    String status = jsonObject.getString("success");
                                    if (status.equals("1")) {

                                        JSONArray dataArray = jsonObject.getJSONArray("login");
                                        for (int i = 0; i < dataArray.length(); i++) {

                                            JSONObject dataobj = dataArray.getJSONObject(i);
                                            String reg_id = dataobj.getString("reg_id");
                                            String license = dataobj.getString("license");
                                            String fullname = dataobj.getString("fullname");
                                            String username = dataobj.getString("username");
                                            String gender = dataobj.getString("gender");
                                            String mobile = dataobj.getString("mobile");
                                            String address = dataobj.getString("address");
                                            String email = dataobj.getString("email");
                                            String approval = dataobj.getString("approval");
                                            String reg_date = dataobj.getString("reg_date");
                                            driveSess.tryDri(reg_id, license, fullname, username, gender, mobile, address, email, approval, reg_date);
                                        }
                                        startActivity(new Intent(getApplicationContext(), DriverDash.class));


                                    } else if (status.equals("0")) {
                                        ErrorText.setText(msg);
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        ErrorText.setVisibility(View.VISIBLE);

                                    } else if (status.equals("2")) {

                                        JSONArray dataArray = jsonObject.getJSONArray("login");
                                        for (int i = 0; i < dataArray.length(); i++) {
                                            JSONObject dataobj = dataArray.getJSONObject(i);
                                            String remarks = dataobj.getString("comment").trim();
                                            ErrorText.setText("Oops!! " + msg);
                                            relativeLayout.setVisibility(View.VISIBLE);
                                            ErrorText.setVisibility(View.VISIBLE);
                                            Toast.makeText(getApplicationContext(), remarks, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    ErrorText.setText("Error!! An error occurred");
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    ErrorText.setVisibility(View.VISIBLE);
                                    //Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();

                                }

                            }, error -> {
                        ErrorText.setText("Error!! Check your root connection");
                        relativeLayout.setVisibility(View.VISIBLE);
                        ErrorText.setVisibility(View.VISIBLE);

                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("username", User);
                            params.put("password", Pass);

                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }


            });
            newReset.setOnClickListener(nee -> {

                AlertDialog.Builder alarmed = new AlertDialog.Builder(this);
                Rect rec = new Rect();
                Window window = MainActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rec);
                LayoutInflater inflat = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layer = inflat.inflate(R.layout.reset, null);
                layer.setMinimumWidth((int) (rec.width() * 0.9f));
                layer.setMinimumHeight((int) (rec.height() * 0.25f));
                myUsername = layer.findViewById(R.id.editUser);
                myPass = layer.findViewById(R.id.editPass);
                myRepeat = layer.findViewById(R.id.editPassRepeat);
                myCity = layer.findViewById(R.id.editCity);
                ErrorText = layer.findViewById(R.id.editError);
                logged = layer.findViewById(R.id.editReset);
                logged.setOnClickListener(view -> {
                    final String User = myUsername.getText().toString().trim();
                    final String Pass = myPass.getText().toString().trim();
                    final String Repeat = myRepeat.getText().toString().trim();
                    int length = Pass.length();
                    if (User.isEmpty() || Pass.isEmpty() || Repeat.isEmpty()) {
                        ErrorText.setText("Error!! There is a missing Field");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (length < MIN_LENGTH) {
                        ErrorText.setText("Oops!! Enter a Strong Password");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (!Repeat.equals(Pass)) {
                        ErrorText.setText("Oops!! Passwords do not match");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else {
                        relativeLayout.setVisibility(View.GONE);
                        ErrorText.setVisibility(View.GONE);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.DRUP,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.e("response ", response);
                                        String msg = jsonObject.getString("message");
                                        int status = jsonObject.getInt("success");
                                        if (status == 1) {
                                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            finish();

                                        } else if (status == 0) {
                                            ErrorText.setText(msg);
                                            relativeLayout.setVisibility(View.VISIBLE);
                                            ErrorText.setVisibility(View.VISIBLE);

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        ErrorText.setText("Error!! An error occurred");
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        ErrorText.setVisibility(View.VISIBLE);
                                        //Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();

                                    }

                                }, error -> {
                            ErrorText.setText("Error!! Check your root connection");
                            relativeLayout.setVisibility(View.VISIBLE);
                            ErrorText.setVisibility(View.VISIBLE);

                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("username", User);
                                params.put("password", Pass);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);
                    }
                });
                alarmed.setTitle("Reset");
                alarmed.setView(layer);
                alarmed.setPositiveButton("back", (dialogq, idq) -> {
                    getDrive();
                });
                AlertDialog alertDialog = alarmed.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            });
            newRegistry.setOnClickListener(veve -> {

                AlertDialog.Builder alarmed = new AlertDialog.Builder(this);
                Rect rec = new Rect();
                Window window = MainActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rec);
                LayoutInflater inflat = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layer = inflat.inflate(R.layout.drive_reg, null);
                layer.setMinimumWidth((int) (rec.width() * 0.9f));
                layer.setMinimumHeight((int) (rec.height() * 0.5f));
                myUsername = layer.findViewById(R.id.editUser);
                myFullname = layer.findViewById(R.id.editFull);
                myMobile = layer.findViewById(R.id.editMobile);
                myEmail = layer.findViewById(R.id.editMail);
                myPass = layer.findViewById(R.id.editPass);
                myRepeat = layer.findViewById(R.id.editPassRepeat);
                myCity = layer.findViewById(R.id.editCity);
                myAddress = layer.findViewById(R.id.editAddress);
                myLicense = layer.findViewById(R.id.editLicense);
                ErrorText = layer.findViewById(R.id.editError);
                logged = layer.findViewById(R.id.editSubmit);
                radioGroup = layer.findViewById(R.id.radioGender);
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.City, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                myCity.setAdapter(adapter1);
                myCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String mSpinner = myCity.getSelectedItem().toString();
                        validateRegSpinner(mSpinner);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                logged.setOnClickListener(view -> {
                    final String User = myUsername.getText().toString().trim();
                    final String Full = myFullname.getText().toString().trim();
                    final String Lice = myLicense.getText().toString().trim();
                    final String Pass = myPass.getText().toString().trim();
                    final String Mobi = myMobile.getText().toString().trim();
                    final String Mail = myEmail.getText().toString().trim();
                    final String Repeat = myRepeat.getText().toString().trim();
                    final String City = myCity.getSelectedItem().toString().trim();
                    final String Add = myAddress.getText().toString().trim();
                    String emailPattern = "[a-z0-9._%+-]+@gmail+\\.[a-z]{2,4}";
                    String emailPattern2 = "[a-z0-9._%+-]+@yahoo+\\.[a-z]{2,4}";
                    Gender = ((RadioButton) layer.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                    int length = Pass.length();
                    int lens = Mobi.length();
                    int le = Lice.length();
                    if (User.isEmpty() || Lice.isEmpty() || Full.isEmpty() || Pass.isEmpty() || Mobi.isEmpty() || Repeat.isEmpty()) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Error!! There is a missing Field");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (le < MINI_IN) {
                        ErrorText.setText("Oops!! Your License is invalid");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (length < MIN_LENGTH) {
                        ErrorText.setText("Oops!! Your password is weak");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (Gender.equals("Gender")) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Gender is required");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (lens < MINIMUM_LENGTH) {
                        ErrorText.setText("Oops!! Your Phone is invalid");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (!Mail.matches(emailPattern) & !Mail.matches(emailPattern2)) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Your Email is invalid");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (City.equals("Select City")) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Select City");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (Add.isEmpty()) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        myAddress.requestFocus();
                        ErrorText.setText("Failed!! There is a required field");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (!Repeat.equals(Pass)) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Passwords do not match");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.DRRG,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.e("response ", response);
                                        String msg = jsonObject.getString("message");
                                        int status = jsonObject.getInt("success");
                                        if (status == 1) {
                                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                        } else if (status == 0) {
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
                                params.put("fullname", Full);
                                params.put("username", User);
                                params.put("gender", Gender);
                                params.put("license", Lice);
                                params.put("mobile", Mobi);
                                params.put("address", Add + ", " + City);
                                params.put("email", Mail);
                                params.put("password", Pass);
                                return params;
                            }
                        };//fullname,username,mobile,role,email,password
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);
                    }
                });
                alarmed.setTitle("Register");
                alarmed.setView(layer);
                alarmed.setPositiveButton("back", (dialogq, idq) -> {
                    getDrive();
                });
                AlertDialog alertDialog = alarmed.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            });
            alerto.setTitle("Login Driver");
            alerto.setView(layer);
            alerto.setPositiveButton("back", (dialogq, idq) -> {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            });
            AlertDialog alertDialog = alerto.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        }
   // }

    private void validateRegSpinner(String mSpinner) {
        if (mSpinner.equals("Select City")) {
            myAddress.setVisibility(View.GONE);
        } else {
            myAddress.setVisibility(View.VISIBLE);
        }
    }

    private void getCust() {
        /*if (customerSess.inCustTry()) {
            startActivity(new Intent(getApplicationContext(), CustDash.class));
        } else {*/
            Toast.makeText(MainActivity.this, "To create new account Click the login button to redirect", Toast.LENGTH_LONG).show();

            AlertDialog.Builder alerto = new AlertDialog.Builder(this);
            Rect reco = new Rect();
            Window windower = MainActivity.this.getWindow();
            windower.getDecorView().getWindowVisibleDisplayFrame(reco);
            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layer = inflater.inflate(R.layout.login, null);
            layer.setMinimumWidth((int) (reco.width() * 0.9f));
            layer.setMinimumHeight((int) (reco.height() * 0.2f));
            myUsername = layer.findViewById(R.id.editUser);
            myPass = layer.findViewById(R.id.editPass);
            ErrorText = layer.findViewById(R.id.editError);
            logged = layer.findViewById(R.id.editLogin);
            newRegistry = layer.findViewById(R.id.editSignUp);
            newReset = layer.findViewById(R.id.editUpdate);
            relativeLayout = layer.findViewById(R.id.editMentor);
            logged.setOnClickListener(view -> {
                final String User = myUsername.getText().toString().trim();
                final String Pass = myPass.getText().toString().trim();
                if (User.isEmpty() || Pass.isEmpty()) {
                    ErrorText.setText("Error!! There is a required Field");
                    relativeLayout.setVisibility(View.VISIBLE);
                    ErrorText.setVisibility(View.VISIBLE);
                } else {
                    relativeLayout.setVisibility(View.GONE);
                    ErrorText.setVisibility(View.GONE);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.CSLG,
                            response -> {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    Log.e("response", response);
                                    String msg = jsonObject.getString("message");
                                    String status = jsonObject.getString("success");
                                    if (status.equals("1")) {

                                        JSONArray dataArray = jsonObject.getJSONArray("login");
                                        for (int i = 0; i < dataArray.length(); i++) {

                                            JSONObject dataobj = dataArray.getJSONObject(i);
                                            String reg_id = dataobj.getString("reg_id");
                                            String fullname = dataobj.getString("fullname");
                                            String username = dataobj.getString("username");
                                            String mobile = dataobj.getString("mobile");
                                            String address = dataobj.getString("address");
                                            String email = dataobj.getString("email");
                                            String approval = dataobj.getString("approval");
                                            String reg_date = dataobj.getString("reg_date");
                                            customerSess.tryCust(reg_id, fullname, username, mobile, address, email, approval, reg_date);
                                        }
                                        startActivity(new Intent(getApplicationContext(), CustDash.class));


                                    } else if (status.equals("0")) {
                                        ErrorText.setText(msg);
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        ErrorText.setVisibility(View.VISIBLE);

                                    } else if (status.equals("2")) {

                                        JSONArray dataArray = jsonObject.getJSONArray("login");
                                        for (int i = 0; i < dataArray.length(); i++) {
                                            JSONObject dataobj = dataArray.getJSONObject(i);
                                            String remarks = dataobj.getString("comment").trim();
                                            ErrorText.setText("Oops!! " + msg);
                                            relativeLayout.setVisibility(View.VISIBLE);
                                            ErrorText.setVisibility(View.VISIBLE);
                                            Toast.makeText(getApplicationContext(), remarks, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    ErrorText.setText("Error!! An error occurred");
                                    relativeLayout.setVisibility(View.VISIBLE);
                                    ErrorText.setVisibility(View.VISIBLE);
                                    //Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();

                                }

                            }, error -> {
                        ErrorText.setText("Error!! Check your root connection");
                        relativeLayout.setVisibility(View.VISIBLE);
                        ErrorText.setVisibility(View.VISIBLE);

                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("username", User);
                            params.put("password", Pass);

                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                }


            });
            newReset.setOnClickListener(nee -> {

                AlertDialog.Builder alarmed = new AlertDialog.Builder(this);
                Rect rec = new Rect();
                Window window = MainActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rec);
                LayoutInflater inflat = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layer = inflat.inflate(R.layout.reset, null);
                layer.setMinimumWidth((int) (rec.width() * 0.9f));
                layer.setMinimumHeight((int) (rec.height() * 0.25f));
                myUsername = layer.findViewById(R.id.editUser);
                myPass = layer.findViewById(R.id.editPass);
                myRepeat = layer.findViewById(R.id.editPassRepeat);
                myCity = layer.findViewById(R.id.editCity);
                ErrorText = layer.findViewById(R.id.editError);
                logged = layer.findViewById(R.id.editReset);
                logged.setOnClickListener(view -> {
                    final String User = myUsername.getText().toString().trim();
                    final String Pass = myPass.getText().toString().trim();
                    final String Repeat = myRepeat.getText().toString().trim();
                    int length = Pass.length();
                    if (User.isEmpty() || Pass.isEmpty() || Repeat.isEmpty()) {
                        ErrorText.setText("Error!! There is a missing Field");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (length < MIN_LENGTH) {
                        ErrorText.setText("Oops!! Enter a Strong Password");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (!Repeat.equals(Pass)) {
                        ErrorText.setText("Oops!! Passwords do not match");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else {
                        relativeLayout.setVisibility(View.GONE);
                        ErrorText.setVisibility(View.GONE);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.CSUP,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.e("response ", response);
                                        String msg = jsonObject.getString("message");
                                        int status = jsonObject.getInt("success");
                                        if (status == 1) {
                                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                            finish();

                                        } else if (status == 0) {
                                            ErrorText.setText(msg);
                                            relativeLayout.setVisibility(View.VISIBLE);
                                            ErrorText.setVisibility(View.VISIBLE);

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        ErrorText.setText("Error!! An error occurred");
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        ErrorText.setVisibility(View.VISIBLE);
                                        //Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();

                                    }

                                }, error -> {
                            ErrorText.setText("Error!! Check your root connection");
                            relativeLayout.setVisibility(View.VISIBLE);
                            ErrorText.setVisibility(View.VISIBLE);

                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("username", User);
                                params.put("password", Pass);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);
                    }
                });
                alarmed.setTitle("Reset");
                alarmed.setView(layer);
                alarmed.setPositiveButton("back", (dialogq, idq) -> {
                    getCust();
                });
                AlertDialog alertDialog = alarmed.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            });
            newRegistry.setOnClickListener(veve -> {

                AlertDialog.Builder alarmed = new AlertDialog.Builder(this);
                Rect rec = new Rect();
                Window window = MainActivity.this.getWindow();
                window.getDecorView().getWindowVisibleDisplayFrame(rec);
                LayoutInflater inflat = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layer = inflat.inflate(R.layout.cust_reg, null);
                layer.setMinimumWidth((int) (rec.width() * 0.9f));
                layer.setMinimumHeight((int) (rec.height() * 0.5f));
                myUsername = layer.findViewById(R.id.editUser);
                myFullname = layer.findViewById(R.id.editFull);
                myMobile = layer.findViewById(R.id.editMobile);
                myEmail = layer.findViewById(R.id.editMail);
                myPass = layer.findViewById(R.id.editPass);
                myRepeat = layer.findViewById(R.id.editPassRepeat);
                myCity = layer.findViewById(R.id.editCity);
                ErrorText = layer.findViewById(R.id.editError);
                logged = layer.findViewById(R.id.editSubmit);
                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.City, android.R.layout.simple_spinner_item);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                myCity.setAdapter(adapter1);
                logged.setOnClickListener(view -> {
                    final String User = myUsername.getText().toString().trim();
                    final String Full = myFullname.getText().toString().trim();
                    final String Pass = myPass.getText().toString().trim();
                    final String Mobi = myMobile.getText().toString().trim();
                    final String Mail = myEmail.getText().toString().trim();
                    final String Repeat = myRepeat.getText().toString().trim();
                    final String City = myCity.getSelectedItem().toString().trim();
                    String emailPattern = "[a-z0-9._%+-]+@gmail+\\.[a-z]{2,4}";
                    String emailPattern2 = "[a-z0-9._%+-]+@yahoo+\\.[a-z]{2,4}";
                    int length = Pass.length();
                    int lens = Mobi.length();
                    if (User.isEmpty() || Full.isEmpty() || Pass.isEmpty() || Mobi.isEmpty() || Repeat.isEmpty()) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Error!! There is a missing Field");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (length < MIN_LENGTH) {
                        ErrorText.setText("Oops!! Your password is weak");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (lens < MINIMUM_LENGTH) {
                        ErrorText.setText("Oops!! Your Phone is invalid");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (!Mail.matches(emailPattern) & !Mail.matches(emailPattern2)) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Your Email is invalid");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (City.equals("Select City")) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Select City");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else if (!Repeat.equals(Pass)) {
                        if (vibrator.hasVibrator()) {
                            final long[] pattern = {0, 300, 0};
                            new Thread() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 2; i++) {
                                        vibrator.vibrate(pattern, -1);
                                        try {
                                            Thread.sleep(600);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }.start();
                        }
                        ErrorText.setText("Oops!! Passwords do not match");
                        ErrorText.setVisibility(View.VISIBLE);
                    } else {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.CURG,
                                response -> {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        Log.e("response ", response);
                                        String msg = jsonObject.getString("message");
                                        int status = jsonObject.getInt("success");
                                        if (status == 1) {
                                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                        } else if (status == 0) {
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
                                params.put("fullname", Full);
                                params.put("username", User);
                                params.put("mobile", Mobi);
                                params.put("address", City);
                                params.put("email", Mail);
                                params.put("password", Pass);
                                return params;
                            }
                        };//fullname,username,mobile,address,email,password
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);
                    }
                });
                alarmed.setTitle("Register");
                alarmed.setView(layer);
                alarmed.setPositiveButton("back", (dialogq, idq) -> {
                    getCust();
                });
                AlertDialog alertDialog = alarmed.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            });
            alerto.setTitle("Login Customer");
            alerto.setView(layer);
            alerto.setPositiveButton("back", (dialogq, idq) -> {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            });
            AlertDialog alertDialog = alerto.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.getWindow().setGravity(Gravity.BOTTOM);


        }
   // }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.owner, menu);
        return true;
    }

    @SuppressLint({"NonConstantResourceId", "MissingPermission"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.developer:
                AlertDialog.Builder mydialog13 = new AlertDialog.Builder(MainActivity.this);
                mydialog13.setTitle("Developer");
                mydialog13.setIcon(R.drawable.person);
                mydialog13.setMessage("Onlidealz Mobile Application\nwas Developed By. \nJimmy Madadi, KeMU.");
                mydialog13.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                final AlertDialog alertDialog3 = mydialog13.create();
                alertDialog3.setCanceledOnTouchOutside(false);
                alertDialog3.show();
                alertDialog3.getWindow().setGravity(Gravity.BOTTOM);
                break;
            case R.id.exit:
                vibrator.vibrate(500);
                finishAffinity();
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}