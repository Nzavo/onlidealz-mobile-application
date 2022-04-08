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

public class KeepSilent extends ArrayAdapter<ReviewMode> {
    public ArrayList<ReviewMode> MainList;
    public ArrayList<ReviewMode> SubjectListTemp;
    public KeepSilent.SubjectDataFilter subjectDataFilter;

    public KeepSilent(Context context, int id, ArrayList<ReviewMode> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new KeepSilent.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        KeepSilent.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.recycled, null);

            holder = new KeepSilent.ViewHolder();
            holder.price = convertView.findViewById(R.id.priceTag);
            holder.rQuality = convertView.findViewById(R.id.qualityRating);
            holder.rPrice = convertView.findViewById(R.id.pricePrice);
            holder.rRating = convertView.findViewById(R.id.ratingRating);
            holder.rValue = convertView.findViewById(R.id.valueValue);

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
            holder = (KeepSilent.ViewHolder) convertView.getTag();
        }

        ReviewMode subject = SubjectListTemp.get(position);
        holder.price.setText("OrderRefereence: " + subject.reference +  "\nReviewDate: " + subject.reg_date);
        float fQuality = Float.parseFloat(subject.getQuality());
        float fValue = Float.parseFloat(subject.getValue());
        float fPrice = Float.parseFloat(subject.getPrice());
        float fRating = Float.parseFloat(subject.getRate());
        holder.rQuality.setRating(fQuality);
        holder.rValue.setRating(fValue);
        holder.rPrice.setRating(fPrice);
        holder.rRating.setRating(fRating);
        return convertView;

    }

    public class ViewHolder {
        RatingBar rQuality, rRating, rPrice, rValue;
        TextView price;

    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<ReviewMode> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    ReviewMode subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<ReviewMode>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }

}
