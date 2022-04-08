package com.example.OnliDealz.supplier;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.OnliDealz.Black.GetUpload;
import com.example.OnliDealz.Black.GetUploadAdapter;
import com.example.OnliDealz.Black.SupMod;
import com.example.OnliDealz.Black.SupSess;
import com.example.OnliDealz.Black.Travel;
import com.example.OnliDealz.MainActivity;
import com.example.OnliDealz.R;
import com.example.OnliDealz.finance.Account;
import com.example.OnliDealz.finance.FinanceDash;
import com.example.OnliDealz.finance.SupplierPayment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SupplierDash extends AppCompatActivity {
    Vibrator vibrator;
    SupSess supSess;
    SupMod supMod;
    BottomNavigationView bottomNavigationView;
    ArrayList<GetUpload> SubjectList = new ArrayList<>();
    GetUpload neverFails;
    GetUploadAdapter blackJack;
    View layer;
    SearchView searchView;
    ListView listView;
    TextView ErrorText, countTenders;
    EditText Description, Quantity, UniPrice;
    ImageView imageView;
    Button btnTakePhoto, btnBrowseImage, btnUpload;
    String Courier, encodedimage, Qnty;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supSess = new SupSess(getApplicationContext());
        supMod = supSess.getSupDetails();
        Objects.requireNonNull(getSupportActionBar()).setTitle("Welcome " + supMod.fullname);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_supplier_dash);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        bottomNavigationView = findViewById(R.id.bottom);
        bottomNavigationView.setSelectedItemId(R.id.homing);
        ErrorText = findViewById(R.id.nothig);
        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.recycler);
        listView.setTextFilterEnabled(true);
        countTenders = findViewById(R.id.counterSerial);
        getTender();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            neverFails = (GetUpload) parent.getItemAtPosition(position);
            GetUpload product = SubjectList.get(position);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Order Details");
            alert.setMessage("My ID: " + product.getSupplier_id() + "\nMy Business: " + product.getBusiness_name() + "\nCategory: " + product.getCategory() + "\nType: " + product.getType() + "\n\nQuantity Required: " + product.getQnty() + " units");
            alert.setPositiveButton("supply", ((dialogInterface, i) -> {
                Courier = product.getCounter_id();
                Qnty = product.getQnty();
                AlertDialog.Builder alerting = new AlertDialog.Builder(this);
                Rect reco = new Rect();
                Window win = SupplierDash.this.getWindow();
                win.getDecorView().getWindowVisibleDisplayFrame(reco);
                LayoutInflater inflater = (LayoutInflater) SupplierDash.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layer = inflater.inflate(R.layout.supp_diehard, null);
                layer.setMinimumWidth((int) (reco.width() * 0.9f));
                layer.setMinimumHeight((int) (reco.height() * 0.5f));
                Description = layer.findViewById(R.id.editDescription);
                UniPrice = layer.findViewById(R.id.editUnitPrice);
                Quantity = layer.findViewById(R.id.editQuantity);
                imageView = layer.findViewById(R.id.imageView);
                btnTakePhoto = layer.findViewById(R.id.takePhoto);
                btnBrowseImage = layer.findViewById(R.id.browseImage);
                btnUpload = layer.findViewById(R.id.upload);
                btnUpload.setOnClickListener(v -> {
                    final String myDEsc = Description.getText().toString().trim();
                    final String myPrice = UniPrice.getText().toString().trim();
                    final String myQuant = Quantity.getText().toString().trim();
                    Drawable drawable = imageView.getDrawable();
                    if (myQuant.isEmpty()) {
                        Toast.makeText(SupplierDash.this, "Quantity Required", Toast.LENGTH_SHORT).show();
                        Quantity.requestFocus();
                    } else if (myPrice.isEmpty()) {
                        Toast.makeText(SupplierDash.this, "Price Required", Toast.LENGTH_SHORT).show();
                        UniPrice.requestFocus();
                    } else if (myDEsc.isEmpty()) {
                        Toast.makeText(SupplierDash.this, "Enter Description", Toast.LENGTH_SHORT).show();
                        Description.requestFocus();
                    } else if (drawable == null) {
                        Toast.makeText(SupplierDash.this, "Take a Photo or Browse image", Toast.LENGTH_SHORT).show();
                    } else {
                        StringRequest stringRequesting = new StringRequest(Request.Method.POST, Travel.UPLOAD_SUP,
                                respon -> {
                                    try {
                                        JSONObject jsonOb = new JSONObject(respon);
                                        Log.e("response ", respon);
                                        String msg = jsonOb.getString("message");
                                        int statuses = jsonOb.getInt("success");
                                        if (statuses == 1) {
                                            Toast.makeText(SupplierDash.this, msg, Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), SupplierDash.class));

                                        } else if (statuses == 9) {
                                            Quantity.setError(msg);
                                            Quantity.requestFocus();
                                        } else if (statuses == 8) {
                                            UniPrice.setError(msg);
                                            UniPrice.requestFocus();
                                        } else if (statuses == 0) {
                                            Toast.makeText(SupplierDash.this, msg, Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(SupplierDash.this, e.toString(), Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(SupplierDash.this, "An error occurred", Toast.LENGTH_SHORT).show();
                                    }

                                }, error -> {
                            Toast.makeText(SupplierDash.this, "Failed to connect", Toast.LENGTH_SHORT).show();

                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("quant", Qnty);
                                params.put("counter_id", Courier);
                                params.put("quantity", myQuant);
                                params.put("price", myPrice);
                                params.put("description", myDEsc);
                                params.put("image", encodedimage);
                                params.put("category", product.getCategory());
                                params.put("type", product.getType());
                                params.put("business_name", product.getBusiness_name());
                                params.put("supplier_id", supMod.reg_id);
                                return params;
                            }
                        };//quant,counter_id,quantity,price,description,image
                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequesting);
                    }
                });
                btnTakePhoto.setOnClickListener(v -> Dexter.withActivity(SupplierDash.this)
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, 111);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check());
                btnBrowseImage.setOnClickListener(v -> {
                    ActivityCompat.requestPermissions(
                            SupplierDash.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            000
                    );
                });
                alerting.setMessage("Category: " + product.getCategory() + "\nType: " + product.getType() + "\nQuantity: " + product.getQnty() + "\n");
                alerting.setTitle("Supply Product");
                alerting.setView(layer);
                alerting.setNegativeButton("close", (dialog1, item) -> dialog1.cancel());
                AlertDialog alertDialog = alerting.create();
                alertDialog.show();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.getWindow().setGravity(Gravity.BOTTOM);
            }));
            alert.setNegativeButton("close", (dialogInterface, i) -> dialogInterface.cancel());
            AlertDialog alertDialog = alert.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.getWindow().setGravity(Gravity.BOTTOM);

        });
        retriveOrders();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.history:
                    startActivity(new Intent(getApplicationContext(), SuppHist.class));
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.homing:
                    vibrator.vibrate(500);
                    return true;
                case R.id.payment:
                    CharSequence[] items = {"Expected Payment", "Disbursed Payment"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SupplierDash.this);
                    dialog.setTitle("Supplier Payment");
                    dialog.setItems(items, (dialog1, itemer) -> {
                        if (itemer == 0) {
                            startActivity(new Intent(getApplicationContext(), Payment.class));
                            finish();
                        } else if(itemer == 1)  {
                            startActivity(new Intent(getApplicationContext(), PaymentNotification.class));
                            finish();
                        }
                    });
                    dialog.setNegativeButton("close", (dialog1, itemer) -> dialog1.cancel());
                    final AlertDialog alertDialog = dialog.create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        if (requestCode == 000) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 000);
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode == 000 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                encodedBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 111 && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            encodedBitmap(bitmap);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodedBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] bytesofimages = byteArrayOutputStream.toByteArray();
        encodedimage = Base64.encodeToString(bytesofimages, Base64.DEFAULT);
    }

    private void getTender() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.MINDSET,
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
                                countTenders.setText(" New Tenders " + counter);
                            }
                        } else {
                            countTenders.setText(" New Tenders 0");
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
                params.put("supplier_id", supMod.reg_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void retriveOrders() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Travel.GET_CATEGORY,
                response -> {
                    try {

                        GetUpload subject;
                        SubjectList = new ArrayList<>();

                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("response ", response);
                        int success = jsonObject.getInt("trust");
                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("victory");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);

                                String counter_id = jsonObject.getString("counter_id");
                                String supplier_id = jsonObject.getString("supplier_id");
                                String business_name = jsonObject.getString("business_name");
                                String category = jsonObject.getString("category");
                                String type = jsonObject.getString("type");
                                String qnty = jsonObject.getString("qnty");
                                subject = new GetUpload(counter_id, supplier_id, business_name, category, type, qnty);
                                SubjectList.add(subject);
                            }  //product_id,category,type,description,imagery,quantity,price,stock
                            blackJack = new GetUploadAdapter(SupplierDash.this, R.layout.geneva, SubjectList);
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
                params.put("supplier_id", supMod.reg_id);
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(SupplierDash.this);
                dialog.setTitle("My Profile");
                dialog.setIcon(R.drawable.person);
                dialog.setItems(items, (dialog1, itemer) -> {
                    if (itemer == 0) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(SupplierDash.this);
                        mydialog1.setTitle("Profile");
                        mydialog1.setMessage("Registry: " + supMod.reg_id + "\nB/SReg: " + supMod.business_registry + "\nBusiness Name: " + supMod.business_name + "\nNature: " + supMod.existence + "\nName: " + supMod.fullname + "\nUsername: " + supMod.username + "\nEmail: " + supMod.email + "\nPhone: " + supMod.mobile + "\nCity: " + supMod.address + "\nStatus: " + supMod.approval + "\nDate: " + supMod.reg_date);
                        mydialog1.setNegativeButton("exit", (dialogInterface121, i121) -> dialogInterface121.cancel());
                        final AlertDialog alertDialog = mydialog1.create();
                        alertDialog.setCanceledOnTouchOutside(false);
                        alertDialog.show();
                        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
                    } else if (itemer == 1) {
                        AlertDialog.Builder mydialog1 = new AlertDialog.Builder(SupplierDash.this);
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