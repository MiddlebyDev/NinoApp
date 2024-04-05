package com.example.comedorkot29;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVShiftListAdapter extends RecyclerView.Adapter<RVShiftListAdapter.ViewHolder> {

    int globalPosition = - 1;
    Context context;
    ArrayList<Shifts> shifts;
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public RVShiftListAdapter(Context context, ArrayList<Shifts> arrayList) {
        this.context = context;
        this.shifts = arrayList;
    }

    @NonNull
    @Override
    public RVShiftListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_shiftslistlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVShiftListAdapter.ViewHolder holder, int position) {
        System.out.println(globalPosition);
        holder.name.setChecked(position == globalPosition);
        System.out.println(globalPosition);
        holder.bind(shifts.get(position));
    }

    @Override
    public int getItemCount() {
        return shifts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private CheckBox name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_ckid);
            name = itemView.findViewById(R.id.cb_turno);

            name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        //System.out.println("Click");
                        globalPosition = getAdapterPosition();
                    }
                    else {
                        //System.out.println("No click");
                        globalPosition = -1;
                    }
                    notifyDataSetChanged();
                }
            });
        }
        public void bind(Shifts shifts1){
            id.setText(shifts1.getId()+"");
            name.setText(shifts1.getName());

        }
    }
}