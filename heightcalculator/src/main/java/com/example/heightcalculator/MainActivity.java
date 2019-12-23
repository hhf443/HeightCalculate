package com.example.heightcalculator;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button btnSave, btnReset, btnCalculate;
    private EditText editName, editFutherHeight, editMotherHeight, editForecastHeight;
    private RadioGroup radioGroupIsMale;
    private RadioButton radioButtonMale, radioButtonFemale;
    private List<HeightInfo> infoList = new ArrayList<>();
    private HcDatabaseHelper databaseHelper;
    private MyAdapter myAdapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        myAdapter = new MyAdapter(MainActivity.this, R.layout.layout_info_item, infoList);
        infoList = myAdapter.find(infoList);

        listView = findViewById(R.id.listview_save);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(this);
    }

    public void init(){

        btnSave = findViewById(R.id.btn_save);
        btnReset = findViewById(R.id.btn_reset);
        btnCalculate = findViewById(R.id.btn_calculate);

        editName = findViewById(R.id.edit_name);
        radioGroupIsMale = findViewById(R.id.radiogroup_sex);
        editFutherHeight = findViewById(R.id.edit_futher_height);
        editMotherHeight = findViewById(R.id.edit_mother_height);
        editForecastHeight = findViewById(R.id.edit_forecast_height);

        radioButtonMale = findViewById(R.id.radio_male);
        radioButtonFemale = findViewById(R.id.radio_female);

        btnSave.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save:
                save();
                infoList = myAdapter.find(infoList);
                break;
            case R.id.btn_reset:
                reset();
                break;
            case R.id.btn_calculate:
                editForecastHeight.setText(calculate());
                break;
        }
    }

    public void save(){
        final HeightInfo hInfo = new HeightInfo();
        hInfo.setName(editName.getText().toString());
        radioGroupIsMale.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkid) {
                Log.v("checkId", checkid+"");
                if(checkid == R.id.radio_male){
                    Log.v("checkId", R.id.radio_male+"男性");
                    hInfo.setIsmale(true);
                    Log.v("ismale", String.valueOf(hInfo.isIsmale()));
                }else {
                    Log.v("checkId", R.id.radio_female+"女性");
                    hInfo.setIsmale(false);
                    Log.v("ismale", String.valueOf(hInfo.isIsmale()));
                }
            }
        });
        hInfo.setFheight(editFutherHeight.getText().toString());
        hInfo.setMheight(editMotherHeight.getText().toString());
        hInfo.setSheight(editForecastHeight.getText().toString());
        Log.v("Main--save:ismale", hInfo.toString());
        myAdapter.add(hInfo);
    }
    public String calculate(){
        DecimalFormat df = new DecimalFormat("#.0");
        double result, futher, mother;
        futher = Double.parseDouble(editFutherHeight.getText().toString());
        mother = Double.parseDouble(editMotherHeight.getText().toString());
        if(radioGroupIsMale.getCheckedRadioButtonId()==R.id.radio_male){
            result = (futher+mother)*0.54;
        }else{
            result = (futher*0.923+mother)/2;
        }
        return String.valueOf(df.format(result));
    }
    public void reset(){
        editName.setText("");
        radioButtonMale.setChecked(true);
        editFutherHeight.setText("");
        editMotherHeight.setText("");
        editForecastHeight.setText("");
    }

    public void echo(HeightInfo info){
        editName.setText(info.getName());
        if(info.isIsmale()) {
            radioButtonMale.setChecked(true);
        }else{
            radioButtonFemale.setChecked(true);
        }
        editFutherHeight.setText(info.getFheight());
        editMotherHeight.setText(info.getMheight());
        editForecastHeight.setText(info.getSheight());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        HeightInfo heightInfo = infoList.get(i);
        echo(heightInfo);
    }
}
