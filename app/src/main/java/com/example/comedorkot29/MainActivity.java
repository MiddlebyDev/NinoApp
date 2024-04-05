package com.example.comedorkot29;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comedorkot29.client.ApiClient;
import com.example.comedorkot29.client.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv_questionslist, rv_shiftslist;
    RVQuestionListAdapter rvQuestionListAdapter;
    RVShiftListAdapter  rvShiftListAdapter;
    ArrayList<Questions> questions;
    ArrayList<Shifts> shifts;
    public static final long PERIODO = 1500;
    private Handler handler;
    CheckBox turnouno, turnodos;
    Button send;
    TextView turnos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questions = new ArrayList<>();
        shifts = new ArrayList<>();
        rv_questionslist = findViewById(R.id.rv_questionslist);
        rv_questionslist.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        rv_shiftslist = findViewById(R.id.rv_shiftslist);
        rv_shiftslist.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        rvQuestionListAdapter = new RVQuestionListAdapter(MainActivity.this, questions);
        rv_questionslist.setAdapter(rvQuestionListAdapter);

        rvShiftListAdapter = new RVShiftListAdapter(MainActivity.this, shifts);
        rv_shiftslist.setAdapter(rvShiftListAdapter);

        turnos = (TextView) findViewById(R.id.turno);
        turnouno = (CheckBox) findViewById(R.id.turno1);
        turnodos = (CheckBox) findViewById(R.id.turno2);

        turnos.setVisibility(View.GONE);

        rvQuestionListAdapter.setOnRespuestaClickedListener(new RVQuestionListAdapter.OnRespuestaClickedListener() {
            @Override
            public void OnRespuestaListener() {
                timerManos();
            }
        });

        populateServices();
        populateServicesShift();
        sendButton();
    }

    //region Timer que se reinicia al presionar un boton
    private Handler handlerManos = new Handler();
    Runnable runnableMano = new Runnable() {
        @Override
        public void run() {
            limpiar();
        }
    };

    public void timerManos(){
        handlerManos.removeCallbacks(runnableMano);
        handlerManos.postDelayed(runnableMano, 10000);
    }

    public void timerSubmit(){
        handler = new Handler();
        handler.postDelayed( new Runnable(){
            @Override
            public void run(){
                limpiar();
            }
        }, PERIODO );
    }
// endregion

    public void populateServices(){
        ApiClient.getClient().create(ApiInterface.class).getQuestion().enqueue(new Callback<QuestionsApiResponse>() {
            @Override
            public void onResponse(Call<QuestionsApiResponse> call, Response<QuestionsApiResponse> response) {
                if(response.code() == 200){
                    if (response.body().isSuccess()) {
                        questions.addAll(response.body().getData());
                        rvQuestionListAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<QuestionsApiResponse> call, Throwable t) {
                System.out.println("adios");
            }
        });
    }

    public void populateServicesShift(){/*
        ApiClient.getClient().create(ApiInterface.class).getShift().enqueue(new Callback<ShiftApiResponse>() {
            @Override
            public void onResponse(Call<ShiftApiResponse> call, Response<ShiftApiResponse> response) {
                if(response.code() == 200){
                    if (response.body().isSuccess()) {
                        shifts.addAll(response.body().getData());
                        rvShiftListAdapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onFailure(Call<ShiftApiResponse> call, Throwable t) {
                System.out.println("adios");
            }
        });*/
    }

    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();
        if (checked) {
            switch (view.getId()) {
                case R.id.turno1:
                    turnos.setText("1");
                    turnodos.setChecked(false);
                    break;
                case R.id.turno2:
                    turnos.setText("2");
                    turnouno.setChecked(false);
                    break;
            }
        }
        timerManos();
    }



    public void sendButton(){
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar();
                limpiar();
            }
        });
    }

    public void insertar(){
        ImageButton btnServicio, btnServicioN;
        TextView question_id, shift_id;
        String respuesta = "MALO";
        boolean flag = true;
        int posicion = 1;

        if (turnos.getText().equals("")){
            Toast.makeText(MainActivity.this, "Debe seleccionar un turno", Toast.LENGTH_LONG).show();
            turnouno.setError("Required");
            turnodos.setError("Required");
            return;
        }

        for (int i = 0; i < rv_questionslist.getChildCount(); i++){
            btnServicio = (ImageButton) rv_questionslist.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.btnServicio);
            btnServicioN = (ImageButton) rv_questionslist.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.btnServicioN);
            if (btnServicio.isSelected() || btnServicioN.isSelected() ){}
            else{
                flag = false;
                posicion = i + 1;
                i = rv_questionslist.getChildCount();
            }
        }
        if (flag == true) {
            for (int o = 0; o < rv_questionslist.getChildCount(); o++){
                btnServicio = (ImageButton) rv_questionslist.findViewHolderForAdapterPosition(o).itemView.findViewById(R.id.btnServicio);
                btnServicioN = (ImageButton) rv_questionslist.findViewHolderForAdapterPosition(o).itemView.findViewById(R.id.btnServicioN);
                question_id = (TextView) rv_questionslist.findViewHolderForAdapterPosition(o).itemView.findViewById(R.id.tv_id);
                if (btnServicio.isSelected()){
                    respuesta = "BUENO";
                }
                else if (btnServicioN.isSelected()) {
                    respuesta = "MALO";
                }
                Answers answers = new Answers(Integer.parseInt(turnos.getText().toString()), Integer.parseInt(question_id.getText().toString()), respuesta);
                ApiClient.getClient().create(ApiInterface.class).createAnswer(answers).enqueue(new Callback<Answers>() {
                    @Override
                    public void onResponse(Call<Answers> call, Response<Answers> response) {
                        //System.out.println("Todo correcto");
                    }
                    @Override
                    public void onFailure(Call<Answers> call, Throwable t) {
                        //System.out.println("Todo mal");
                    }
                });
            }
            timerSubmit();
            Toast.makeText(MainActivity.this, "¡Muchas gracias por responder! ", Toast.LENGTH_LONG).show();
            limpiar();
        }
        else {
            timerSubmit();
            Toast.makeText(MainActivity.this, "Falta pregunta " + posicion, Toast.LENGTH_LONG).show();
        }
    }

    public void limpiar(){
        ImageButton btnServicio, btnServicioN;
        boolean flag = true;

        turnos.setText("");
        turnouno.setChecked(false);
        turnodos.setChecked(false);
        turnouno.setError(null);
        turnodos.setError(null);

        for (int i = 0; i < rv_questionslist.getChildCount(); i++){
            btnServicio = (ImageButton) rv_questionslist.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.btnServicio);
            btnServicioN = (ImageButton) rv_questionslist.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.btnServicioN);
            btnServicio.setBackgroundResource(R.color.white);
            btnServicioN.setBackgroundResource(R.color.white);
        }
    }

}