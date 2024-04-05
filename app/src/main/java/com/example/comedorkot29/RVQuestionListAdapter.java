package com.example.comedorkot29;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVQuestionListAdapter extends RecyclerView.Adapter<RVQuestionListAdapter.ViewHolder> {

    int globalPosition = 1000;
    Context context;
    ArrayList<Questions> questions;

    public interface OnRespuestaClickedListener {
        void OnRespuestaListener();
    }
    private OnRespuestaClickedListener mRespuestaClickListener;

    public void setOnRespuestaClickedListener(OnRespuestaClickedListener l){
        mRespuestaClickListener = l;
    }

    public RVQuestionListAdapter(Context context, ArrayList<Questions> arrayList) {
        this.context = context;
        this.questions = arrayList;
    }

    @NonNull
    @Override
    public RVQuestionListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_questionslistlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVQuestionListAdapter.ViewHolder holder, int position) {
        //if (position == globalPosition){
            //System.out.println("Todo bien");
            //holder.btnServicio.setSelected(true);
          //  holder.btnServicio.setBackgroundResource(R.color.gray);
        //}
        //else {
            //System.out.println("Todo mal");
            //holder.btnServicio.setSelected(false);
          //  holder.btnServicio.setBackgroundResource(R.color.white);
        //}
        holder.bind(questions.get(position));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id, question;
        private ImageButton btnServicio, btnServicioN;
        private Handler handlerManos = new Handler();
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_id);
            question = itemView.findViewById(R.id.tv_question);
            btnServicio = itemView.findViewById(R.id.btnServicio);
            btnServicioN = itemView.findViewById(R.id.btnServicioN);

            itemView.findViewById(R.id.btnServicio).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    globalPosition = getAdapterPosition();

                    btnServicio.setSelected(true);
                    btnServicio.setBackgroundResource(R.color.gray);

                    btnServicioN.setSelected(false);
                    btnServicioN.setBackgroundResource(R.color.white);

                    if (mRespuestaClickListener != null) {
                        mRespuestaClickListener.OnRespuestaListener();
                    }

                    notifyDataSetChanged();
                }
            });

            itemView.findViewById(R.id.btnServicioN).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    globalPosition = getAdapterPosition();

                    btnServicioN.setSelected(true);
                    btnServicioN.setBackgroundResource(R.color.gray);

                    btnServicio.setSelected(false);
                    btnServicio.setBackgroundResource(R.color.white);

                    if (mRespuestaClickListener != null) {
                        mRespuestaClickListener.OnRespuestaListener();
                    }

                    notifyDataSetChanged();
                }
            });
        }
        public void bind(Questions questions1){
            id.setText(questions1.getId()+"");
            question.setText(questions1.getQuestion());
        }
    }
}
