package com.example.OnliDealz.Black;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.OnliDealz.R;

import java.util.ArrayList;

public class CustMessAda extends ArrayAdapter<MessagingMode> {
    public ArrayList<MessagingMode> MainList;
    public ArrayList<MessagingMode> SubjectListTemp;
    public CustMessAda.SubjectDataFilter subjectDataFilter;

    public CustMessAda(Context context, int id, ArrayList<MessagingMode> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new CustMessAda.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustMessAda.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.named_man, null);

            holder = new CustMessAda.ViewHolder();
            holder.price = convertView.findViewById(R.id.priceTag);

            convertView.setTag(holder);
            ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(
                    holder.price,
                    PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                    PropertyValuesHolder.ofFloat("scaleY", 1.2f)
            );
            objectAnimator1.setDuration(3000);
            objectAnimator1.setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator1.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator1.start();

        } else {
            holder = (CustMessAda.ViewHolder) convertView.getTag();
        }

        MessagingMode subject = SubjectListTemp.get(position);
        holder.price.setText("Sender: " + subject.post + "\nName: " + subject.name + "\nDate: " + subject.send_date + "\nClick to view more details");

        return convertView;

    }

    public class ViewHolder {

        TextView price;

    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<MessagingMode> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    MessagingMode subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<MessagingMode>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }

}
