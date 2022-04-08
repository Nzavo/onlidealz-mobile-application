package com.example.OnliDealz.Black;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.OnliDealz.R;
import com.example.OnliDealz.customer.Cart;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends ArrayAdapter<CartMod> {
    public ArrayList<CartMod> MainList;
    public ArrayList<CartMod> SubjectListTemp;
    public CartAdapter.SubjectDataFilter subjectDataFilter;
    String selectedID = "";

    public CartAdapter(Context context, int id, ArrayList<CartMod> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new CartAdapter.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CartAdapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.remove_cart, null);

            holder = new CartAdapter.ViewHolder();

            holder.imageView = convertView.findViewById(R.id.imager);

            holder.Detail = convertView.findViewById(R.id.details);
            holder.UnitPrice = convertView.findViewById(R.id.unitPrice);
            holder.Quantity = convertView.findViewById(R.id.quant);
            holder.Cost = convertView.findViewById(R.id.totalCost);
            holder.Remove = convertView.findViewById(R.id.removeCart);
            holder.Reduce = convertView.findViewById(R.id.reduceCart);

            convertView.setTag(holder);
            ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(
                    holder.imageView,
                    PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                    PropertyValuesHolder.ofFloat("scaleY", 1.2f)
            );
            objectAnimator1.setDuration(3000);
            objectAnimator1.setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator1.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator1.start();

        } else {
            holder = (CartAdapter.ViewHolder) convertView.getTag();
        }

        CartMod subject = SubjectListTemp.get(position);
        Glide.with(convertView).load(SubjectListTemp.get(position).getImage()).into(holder.imageView);
        holder.Detail.setText("Item: " + subject.getDetails());
        holder.Quantity.setText("Quantity: " + subject.getQuantity() + " units");
        holder.UnitPrice.setText("UnitPrice: KES. " + subject.getPrice());
        holder.Cost.setText("TotalCost: KES. " + subject.getCost());
        holder.Remove.setOnClickListener(v -> {
            StringRequest stringRequesting = new StringRequest(Request.Method.POST, Travel.LET_LIVE,
                    respon -> {
                        try {
                            JSONObject jsonOb = new JSONObject(respon);
                            Log.e("response ", respon);
                            String msg = jsonOb.getString("message");
                            int statuses = jsonOb.getInt("success");
                            if (statuses == 1) {
                                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                                getContext().startActivity(new Intent(getContext(), Cart.class));

                            } else if (statuses == 0) {
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                        }

                    }, error -> {
                Toast.makeText(getContext(), "Failed to connect", Toast.LENGTH_SHORT).show();

            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("reg", subject.reg);
                    params.put("product", subject.product);
                    params.put("quantity", subject.quantity);
                    return params;
                }
            };//reg,product,quantity
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequesting);
        });
        holder.Reduce.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            final EditText demo = new EditText(getContext());
            demo.setInputType(InputType.TYPE_CLASS_NUMBER);
            demo.setHint("Quantity to reduce");
            builder.setView(demo);
            builder.setTitle("Enter Quantity");
            builder.setPositiveButton("submit", (dialo, idm) -> {
                String myQun = demo.getText().toString().trim();
                if (myQun.isEmpty()) {
                    Toast.makeText(getContext(), "Enter quantity", Toast.LENGTH_SHORT).show();
                }else {
                    StringRequest stringRequesting = new StringRequest(Request.Method.POST, Travel.reduceItem,
                            respon -> {
                                try {
                                    JSONObject jsonOb = new JSONObject(respon);
                                    Log.e("response ", respon);
                                    String msg = jsonOb.getString("message");
                                    int statuses = jsonOb.getInt("success");
                                    if (statuses == 1) {
                                        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                                        getContext().startActivity(new Intent(getContext(), Cart.class));

                                    } else if (statuses == 0) {
                                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                                }

                            }, error -> {
                        Toast.makeText(getContext(), "Failed to connect", Toast.LENGTH_SHORT).show();

                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("reg", subject.reg);
                            params.put("qty", myQun);
                            params.put("product", subject.product);
                            params.put("quantity", subject.quantity);
                            return params;
                        }
                    };//reg,qty,product,quantity
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequesting);
                }
            });
            builder.setNegativeButton("close", (dialo, idm) -> dialo.dismiss());
            builder.show();
        });

        return convertView;

    }

    public class ViewHolder {

        TextView Detail;
        TextView Quantity;
        TextView UnitPrice;
        TextView Cost;
        Button Remove, Reduce;
        ImageView imageView;

    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<CartMod> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    CartMod subject = MainList.get(i);

                    if (subject.toString().toLowerCase().contains(charSequence))

                        arrayList1.add(subject);
                }
                filterResults.count = arrayList1.size();

                filterResults.values = arrayList1;
            } else {
                synchronized (this) {
                    filterResults.values = MainList;

                    filterResults.count = MainList.size();
                }
            }
            return filterResults;
        }


        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            SubjectListTemp = (ArrayList<CartMod>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }

}
