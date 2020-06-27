package com.example.balancomensal;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.balancomensal.R;

public class AddData implements AdapterView.OnItemSelectedListener {
    AppCompatActivity app;

    View.OnClickListener despesa = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            despesa(v);
        }
    };

    View.OnClickListener renda = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            renda(v);
        }
    };

    View.OnClickListener check = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkFixa(v);
        }
    };

    View.OnClickListener adicionar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            adicionar(v);
        }
    };


    public AddData(AppCompatActivity app) {
        this.app = app;
        app.setContentView(R.layout.add_data);
        Spinner spinner = app.findViewById(R.id.category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        despesa(app.findViewById(R.id.add_data));
        checkFixa(app.findViewById(R.id.add_data));
    }

    public void despesa(View v) {
        Button btn_desp = app.findViewById(R.id.btn_desp);
        Button btn_rend = app.findViewById(R.id.btn_rend);
        Spinner s = app.findViewById(R.id.category);

        btn_desp.setBackgroundResource(R.drawable.my_button_click);
        btn_rend.setBackgroundResource(R.drawable.my_button);
        s.setVisibility(View.VISIBLE);

        CheckBox cb = app.findViewById(R.id.fixa);
        cb.setText(R.string.desp_fixa);
    }

    public void renda(View v) {
        Button btn_desp = app.findViewById(R.id.btn_desp);
        Button btn_rend = app.findViewById(R.id.btn_rend);
        Spinner s = app.findViewById(R.id.category);

        btn_desp.setBackgroundResource(R.drawable.my_button);
        btn_rend.setBackgroundResource(R.drawable.my_button_click);
        s.setVisibility(View.INVISIBLE);

        CheckBox cb = app.findViewById(R.id.fixa);
        cb.setText(R.string.rend_fixa);
    }

    public void checkFixa(View v) {
        CheckBox cb = app.findViewById(R.id.fixa);
        if(cb.isChecked())
            fixa(app.findViewById(R.id.add_data));
        else
            naoFixa(app.findViewById(R.id.add_data));
    }

    public void fixa(View v) {
        EditText edit_txt = app.findViewById(R.id.duracao);
        edit_txt.setVisibility(View.VISIBLE);
    }

    public void naoFixa(View v) {
        EditText edit_txt = app.findViewById(R.id.duracao);
        edit_txt.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void adicionar(View v) {

    }
}