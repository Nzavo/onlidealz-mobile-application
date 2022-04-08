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

public class DeliverAda extends ArrayAdapter<GetPay> {
    public ArrayList<GetPay> MainList;
    public ArrayList<GetPay> SubjectListTemp;
    public DeliverAda.SubjectDataFilter subjectDataFilter;

    public DeliverAda(Context context, int id, ArrayList<GetPay> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new DeliverAda.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DeliverAda.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.tabular, null);

            holder = new DeliverAda.ViewHolder();
            holder.mpesa = convertView.findViewById(R.id.myMPesa);
            holder.status = convertView.findViewById(R.id.mySTatus);
            holder.customer = convertView.findViewById(R.id.myCUstomer);

            convertView.setTag(holder);

        } else {
            holder = (DeliverAda.ViewHolder) convertView.getTag();
        }

        GetPay subject = SubjectListTemp.get(position);
        holder.mpesa.setText(subject.status);
        holder.status.setText(subject.disburse);
        holder.customer.setText(subject.customer_id);

        return convertView;

    }

    public class ViewHolder {

        TextView mpesa;
        TextView status;
        TextView customer;

    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<GetPay> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    GetPay subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<GetPay>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }

}


