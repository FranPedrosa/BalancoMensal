package com.example.balancomensal;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class AddData implements AdapterView.OnItemSelectedListener {

    AppCompatActivity app;

    public AddData(AppCompatActivity app) {
        this.app = app;
        app.setContentView(R.layout.add_data);
        Spinner spinner = app.findViewById(R.id.category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(app, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        View btn_rend = app.findViewById(R.id.btn_rend);
        View btn_desp = app.findViewById(R.id.btn_desp);
        View btn_save = app.findViewById(R.id.btn_save);
        View fixa = app.findViewById(R.id.fixa);

        btn_desp.setOnClickListener(despesa);
        btn_rend.setOnClickListener(renda);
        btn_save.setOnClickListener(save);
        fixa.setOnClickListener(check);

        despesa(btn_desp);
        check(fixa);
    }

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
            check(v);
        }
    };

    View.OnClickListener save = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            save(v);
        }
    };

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

    public void check(View v) {
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

    public void save(View v) {
        app.setContentView(R.layout.index);
    }
}