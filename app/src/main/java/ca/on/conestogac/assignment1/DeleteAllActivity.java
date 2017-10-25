package ca.on.conestogac.assignment1;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class DeleteAllActivity extends AppCompatActivity {

    private final Handler mHideHandler = new Handler();
    private AllDB db = new AllDB(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_delete_all);

    }
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.deleteButton:
                deleteAll();
                break;
        }
    }
    public void deleteAll(){

        db.deleteAllCar();
        db.deleteAllEfficiency();
        TextView msgTextView = (TextView) findViewById((R.id.msgTextView));
        msgTextView.setTextColor(Color.RED);
        msgTextView.setText("all cleared");


    }

}
