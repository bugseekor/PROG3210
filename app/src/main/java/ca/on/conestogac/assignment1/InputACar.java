package ca.on.conestogac.assignment1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.Math.toIntExact;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class InputACar extends AppCompatActivity {

    private final Handler mHideHandler = new Handler();
    private AllDB db = new AllDB(this);
    private EditText nameEditText;
    private EditText yearEditText;
    private EditText engineEditText;
    private EditText kmplEditText;
    private EditText lphkmEditText;
    private EditText mpgEditText;
    private TextView msgTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_input_acar);

        kmplEditText = (EditText) findViewById(R.id.kmplEditText);
        lphkmEditText = (EditText) findViewById(R.id.lphkmEditText);
        mpgEditText = (EditText) findViewById(R.id.mpgEditText);
        kmplEditText = (EditText) findViewById(R.id.kmplEditText);
        kmplEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    lphkmEditText.setText("");
                    mpgEditText.setText("");
                }
            }
        });
        lphkmEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    kmplEditText.setText("");
                    mpgEditText.setText("");
                }
            }
        });
        mpgEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    kmplEditText.setText("");
                    lphkmEditText.setText("");
                }
            }
        });


    }
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.inputButton:
                insertThis();
                break;
        }
    }
    public void insertThis(){
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        yearEditText = (EditText) findViewById(R.id.yearEditText);
        engineEditText = (EditText) findViewById(R.id.engineEditText);

        msgTextView = (TextView) findViewById((R.id.msgTextView));
        String name = nameEditText.getText().toString();
        String year = yearEditText.getText().toString();
        String engine = engineEditText.getText().toString();
        try{
            Car car = new Car(0, name, year ,engine);
            long insertID = db.insertCar(car);
            int insertID_int = Long.valueOf(insertID).intValue();
            int unitType;
            Float efficiency;
            if (kmplEditText.getText().toString().trim().length() > 0){
                unitType = 1;
                efficiency = Float.parseFloat(kmplEditText.getText().toString());
            }
            else if (lphkmEditText.getText().toString().trim().length() > 0){
                unitType = 2;
                efficiency = Float.parseFloat(lphkmEditText.getText().toString());
            }
            else {
                unitType = 3;
                efficiency = Float.parseFloat(mpgEditText.getText().toString());
            }
            Efficiency ef = new Efficiency(insertID_int, unitType, efficiency);
            insertID = db.insertEfficiency(ef);
            nameEditText.setText("");
            yearEditText.setText("");
            engineEditText.setText("");
            kmplEditText.setText("");
            lphkmEditText.setText("");
            mpgEditText.setText("");
            msgTextView.requestFocus();
            msgTextView.setTextColor(Color.RED);
            msgTextView.setText("car inserted");

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        } catch (Exception e){
            msgTextView.setText(e.getMessage());
        }
    }
}

