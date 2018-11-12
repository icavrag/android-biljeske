package com.cigo7live.biljeske.adapteri;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cigo7live.biljeske.EditActivity;
import com.cigo7live.biljeske.R;
import com.cigo7live.biljeske.modeli.podaci;

import java.util.ArrayList;

/**
 * Created by Cigo7 on 11.7.2017..
 */

public class biljeskeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<podaci> items;
    private Context context;


    class Holder extends RecyclerView.ViewHolder {

        private TextView label;

        private Holder(View view) {
            super(view);
            label = (TextView) view.findViewById(R.id.adapter_biljeske_textView_label);
        }
    }

    public biljeskeAdapter(Context context, ArrayList<podaci> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_biljeske, parent, false);
        return new biljeskeAdapter.Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final podaci i = items.get(position);
        Holder h = (Holder) holder;

        if(i.getNaslov() != null){
            h.label.setText(i.getNaslov());
        }

        h.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, EditActivity.class);
                intent.putExtra("id", i.getId() + "");
                intent.putExtra("intent", "edit");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItemList(ArrayList<podaci> items) {
        this.items = items;
    }

}
