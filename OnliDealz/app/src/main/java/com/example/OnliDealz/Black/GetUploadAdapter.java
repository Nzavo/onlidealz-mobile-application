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

import com.example.OnliDealz.R;

import java.util.ArrayList;

public class GetUploadAdapter extends ArrayAdapter<GetUpload> {
    public ArrayList<GetUpload> MainList;
    public ArrayList<GetUpload> SubjectListTemp;
    public GetUploadAdapter.SubjectDataFilter subjectDataFilter;

    public GetUploadAdapter(Context context, int id, ArrayList<GetUpload> subjectArrayList) {

        super(context, id, subjectArrayList);

        this.SubjectListTemp = new ArrayList<>();

        this.SubjectListTemp.addAll(subjectArrayList);

        this.MainList = new ArrayList<>();

        this.MainList.addAll(subjectArrayList);
    }

    @Override
    public Filter getFilter() {

        if (subjectDataFilter == null) {

            subjectDataFilter = new GetUploadAdapter.SubjectDataFilter();
        }
        return subjectDataFilter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GetUploadAdapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = vi.inflate(R.layout.geneva, null);

            holder = new GetUploadAdapter.ViewHolder();

            holder.imageView = convertView.findViewById(R.id.imager);

            holder.Cate = convertView.findViewById(R.id.Category);
            holder.Typ = convertView.findViewById(R.id.Type);
            holder.Quant = convertView.findViewById(R.id.Quantity);

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
            holder = (GetUploadAdapter.ViewHolder) convertView.getTag();
        }

        GetUpload subject = SubjectListTemp.get(position);
        //Glide.with(convertView).load(SubjectListTemp.get(position).getImage()).into(holder.imageView);
        holder.Cate.setText("Category: " + subject.getCategory());
        holder.Typ.setText("Type: " + subject.getType());
        holder.Quant.setText("Required: " + subject.getQnty() + " units");

        return convertView;

    }

    public class ViewHolder {

        TextView Cate;
        TextView Quant;
        TextView Typ;
        ImageView imageView;

    }

    private class SubjectDataFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            charSequence = charSequence.toString().toLowerCase();

            FilterResults filterResults = new FilterResults();

            if (charSequence != null && charSequence.toString().length() > 0) {
                ArrayList<GetUpload> arrayList1 = new ArrayList<>();

                for (int i = 0, l = MainList.size(); i < l; i++) {
                    GetUpload subject = MainList.get(i);

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

            SubjectListTemp = (ArrayList<GetUpload>) filterResults.values;

            notifyDataSetChanged();

            clear();

            for (int i = 0, l = SubjectListTemp.size(); i < l; i++)
                add(SubjectListTemp.get(i));

            notifyDataSetInvalidated();
        }
    }

}



