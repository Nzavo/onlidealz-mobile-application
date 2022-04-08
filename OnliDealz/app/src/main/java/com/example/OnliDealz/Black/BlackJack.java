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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.OnliDealz.R;

import java.util.ArrayList;

public class BlackJack extends ArrayAdapter<NeverFails> {
    public ArrayList<NeverFails> MainList;
    public ArrayList<NeverFails> SubjectListTemp;
    public BlackJack.SubjectDataFilter subjectDataFilter;

    public BlackJack(Context context, int id, ArrayList<NeverFails> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new BlackJack.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BlackJack.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.day_and_night, null);

            holder = new BlackJack.ViewHolder();

            holder.imageView = convertView.findViewById(R.id.imager);

            holder.name = convertView.findViewById(R.id.description);
            holder.price = convertView.findViewById(R.id.priceTag);
            holder.review = convertView.findViewById(R.id.reviewing);
            holder.quanty = convertView.findViewById(R.id.Quantt);

            convertView.setTag(holder);
            ObjectAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(
                    holder.review,
                    PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                    PropertyValuesHolder.ofFloat("scaleY", 1.2f)
            );
            objectAnimator1.setDuration(3000);
            objectAnimator1.setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator1.setRepeatMode(ValueAnimator.REVERSE);
            objectAnimator1.start();

        } else {
            holder = (BlackJack.ViewHolder) convertView.getTag();
        }

        NeverFails subject = SubjectListTemp.get(position);
        Glide.with(convertView).load(SubjectListTemp.get(position).getImage()).into(holder.imageView);
        holder.name.setText("Item " + subject.getCategory());
        holder.price.setText("Kshs. " + subject.getPrice());
        holder.quanty.setText("Available: " + subject.getQuantity());

        return convertView;

    }

    public class ViewHolder {

        TextView name;
        TextView price;
        TextView review;
        TextView quanty;
        ImageView imageView;

    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<NeverFails> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    NeverFails subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<NeverFails>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }

}


