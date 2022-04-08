package com.example.OnliDealz.Black;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.OnliDealz.R;

import java.util.List;

public class MassagingAda extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = "Ndwaro Mara";
    private List<MessagingMode> list;
    private Context ctx;

    public MassagingAda(Context context, List<MessagingMode> list) {
        this.list = list;
        ctx = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.emerge, viewGroup, false);
        viewHolder = new OriginalViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder hotel, int i) {
        if (hotel instanceof OriginalViewHolder) {
            final OriginalViewHolder viewHolder = (OriginalViewHolder) hotel;
            final MessagingMode get = list.get(i);
            if (get.getReply().equals("")) {
                viewHolder.Reply.setVisibility(View.GONE);
                viewHolder.Recived.setVisibility(View.GONE);
                viewHolder.DateRep.setVisibility(View.GONE);
            } else if (get.getMessage().equals("")) {
                viewHolder.Receiver.setVisibility(View.GONE);
                viewHolder.ratingBar.setVisibility(View.GONE);
                viewHolder.Message.setVisibility(View.GONE);
                viewHolder.DateMes.setVisibility(View.GONE);
            } else {
                viewHolder.Receiver.setText("Sent to: " + get.getReceiver());
                float flag = Float.parseFloat(get.getRate());
                viewHolder.ratingBar.setRating(flag);
                viewHolder.Message.setText(get.getMessage());
                viewHolder.DateMes.setText(get.getSend_date());
                viewHolder.Recived.setText(get.getReceiver());
                viewHolder.Reply.setText(get.getReply());
                viewHolder.DateRep.setText(get.getReply_date());
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        public TextView Receiver, Message, DateMes, Recived, Reply, DateRep;
        public RatingBar ratingBar;

        public OriginalViewHolder(@NonNull View view) {
            super(view);
            Receiver = view.findViewById(R.id.textReceiver);
            ratingBar = view.findViewById(R.id.ratedCount);
            Message = view.findViewById(R.id.tetMessage);
            DateMes = view.findViewById(R.id.textDate);
            Recived = view.findViewById(R.id.textReply);
            Reply = view.findViewById(R.id.textRepliedMess);
            DateRep = view.findViewById(R.id.textRplyDate);

        }
    }


}