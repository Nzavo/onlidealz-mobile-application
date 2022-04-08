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

public class IndepenAda extends ArrayAdapter<Independ> {
    public ArrayList<Independ> MainList;
    public ArrayList<Independ> SubjectListTemp;
    public IndepenAda.SubjectDataFilter subjectDataFilter;

    public IndepenAda(Context context, int id, ArrayList<Independ> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new IndepenAda.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IndepenAda.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.new_item, null);

            holder = new IndepenAda.ViewHolder();

            holder.SubjectSupplier = convertView.findViewById(R.id.textView);

            convertView.setTag(holder);

        } else {
            holder = (IndepenAda.ViewHolder) convertView.getTag();
        }

        Independ subject = SubjectListTemp.get(position);
        holder.SubjectSupplier.setText("Amount: KSHs" + subject.getAmount() + "\nDate: " + subject.getReg_date());

        return convertView;

    }

    public class ViewHolder {
        TextView SubjectSupplier;
    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<Independ> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    Independ subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<Independ>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }

}

