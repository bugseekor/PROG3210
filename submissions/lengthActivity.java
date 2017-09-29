package ca.on.conestogac.assignment1;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.DecimalFormat;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class lengthActivity extends AppCompatActivity implements View.OnClickListener{
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */

    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {

        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    private ImageButton tDAButton;
    private ImageButton bUAButton;
    private Button rSButton;
    private Spinner unit1Spinner;
    private Spinner unit2Spinner;
    private EditText length1EditText;
    private EditText length2EditText;
    private TextView msgTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_length);
        unit1Spinner = (Spinner) findViewById(R.id.unit1Spinner);
        unit2Spinner = (Spinner) findViewById(R.id.unit2Spinner);
        tDAButton = (ImageButton) findViewById(R.id.tDAButton);
        bUAButton = (ImageButton) findViewById(R.id.bUAButton);
        rSButton = (Button) findViewById(R.id.rSButton);
        length1EditText = (EditText) findViewById(R.id.length1EditText);
        length2EditText = (EditText) findViewById(R.id.length2EditText);
        msgTextView = (TextView) findViewById(R.id.msgTextView);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {

        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tDAButton:
                onTDClick();
                break;
            case R.id.bUAButton:
                onBUClick();
                break;
            case R.id.rSButton:
                onResetClick();
                break;
        }
    }
    public void onTDClick() {

        String unit1 = unit1Spinner.getSelectedItem().toString();
        String unit2 = unit2Spinner.getSelectedItem().toString();

        try {
            double length1 = Double.parseDouble(length1EditText.getText().toString());
            double length2 = convert(unit1, unit2, length1);
            length2EditText.setText(Double.toString(length2));
            msgTextView.setText("unit converted successfully");
        } catch (Exception e) {
            msgTextView.setText(e.getMessage());
        }
    }

    public void onBUClick() {

        String unit1 = unit1Spinner.getSelectedItem().toString();
        String unit2 = unit2Spinner.getSelectedItem().toString();

        try {
            double length2 = Double.parseDouble(length2EditText.getText().toString());
            double length1 = convert(unit2, unit1, length2);
            length1EditText.setText(Double.toString(length1));
            msgTextView.setText("unit converted successfully");
        } catch (Exception e) {
            msgTextView.setText(e.getMessage());
        }
    }

    public void onResetClick() {

        length1EditText.setText("");
        length2EditText.setText("");
        msgTextView.setText("forms are cleared. do it again");
    }

    public double convert(String u1, String u2, double value){
        double result = 0.0D;
        if ( u1.equals("inch") ){
            if ( u2.equals("meter") ){
                result = value * 0.0254;
            }
            else if ( u2.equals("inch") ){
                result = value;
            }
            else if ( u2.equals("centimeter") ){
                result = value * 2.54;
            }
        }
        else if ( u1.equals("meter") ){
            if ( u2.equals("meter") ){
                result =  value;
            }
            else if ( u2.equals("inch") ){
                result =  value * 39.37007874016;
            }
            else if ( u2.equals("centimeter") ){
                result =  value * 100;
            }
        }
        else if ( u1.equals("centimeter") ){
            if ( u2.equals("meter") ){
                result =  value * 0.01;
            }
            else if ( u2.equals("inch") ){
                result =  value * 0.3937007874016;
            }
            else if ( u2.equals("centimeter") ){
                result =  value;
            }
        }
        DecimalFormat df4 = new DecimalFormat("##########.####");
        return Double.valueOf(df4.format(result));
    }
}
