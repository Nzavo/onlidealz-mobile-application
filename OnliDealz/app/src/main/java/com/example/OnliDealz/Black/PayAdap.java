package com.example.OnliDealz.Black;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.OnliDealz.R;

import java.util.ArrayList;

public class PayAdap extends ArrayAdapter<PayMod> {
    public ArrayList<PayMod> MainList;
    public ArrayList<PayMod> SubjectListTemp;
    public PayAdap.SubjectDataFilter subjectDataFilter;

    public PayAdap(Context context, int id, ArrayList<PayMod> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PayAdap.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.mindful, null);

            holder = new PayAdap.ViewHolder();


            holder.textName = convertView.findViewById(R.id.tcategory);
            holder.textCategory = convertView.findViewById(R.id.massss);
            holder.txtPrice = convertView.findViewById(R.id.mytprice);
            holder.txtTotal = convertView.findViewById(R.id.mytotal);

            convertView.setTag(holder);

        } else {
            holder = (PayAdap.ViewHolder) convertView.getTag();
        }

        PayMod subject = SubjectListTemp.get(position);
        int pos = position + 1;
        holder.textName.setText(+pos + ".  " + subject.getProduct());
        pos++;
        holder.textCategory.setText(subject.getQuantity());
        holder.txtPrice.setText(" KES." + subject.getPrice());
        holder.txtTotal.setText(" KES." + subject.getSupply());
        return convertView;

    }

    public class ViewHolder {
        TextView textName, textCategory, txtPrice, txtTotal;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<PayMod> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    PayMod subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<PayMod>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }

}
