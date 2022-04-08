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
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.OnliDealz.R;

import java.util.ArrayList;

public class RatingAda extends ArrayAdapter<RatingMode> {
    public ArrayList<RatingMode> MainList;
    public ArrayList<RatingMode> SubjectListTemp;
    public RatingAda.SubjectDataFilter subjectDataFilter;

    public RatingAda(Context context, int id, ArrayList<RatingMode> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new RatingAda.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RatingAda.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.absent, null);

            holder = new RatingAda.ViewHolder();//valueValue,priceTag
            holder.price = convertView.findViewById(R.id.priceTag);
            holder.ratingBar = convertView.findViewById(R.id.valueValue);

            convertView.setTag(holder);
            ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(
                    holder.ratingBar,
                    PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                    PropertyValuesHolder.ofFloat("scaleY", 1.2f)
            );
            objectAnimator1.setDuration(3000);
            objectAnimator1.setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator1.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator1.start();

        } else {
            holder = (RatingAda.ViewHolder) convertView.getTag();
        }
        //id,customer,phone,fullname,message,rate,reg_date
        RatingMode subject = SubjectListTemp.get(position);
        holder.price.setText("Customer: " + subject.customer + "\nName: " + subject.fullname + "\nMessage: " + subject.message + "\nDate: " + subject.reg_date);
        float timed = Float.parseFloat(subject.getRate());
        holder.ratingBar.setRating(timed);

        return convertView;

    }

    public class ViewHolder {

        TextView price;
        RatingBar ratingBar;

    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<RatingMode> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    RatingMode subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<RatingMode>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }

}
