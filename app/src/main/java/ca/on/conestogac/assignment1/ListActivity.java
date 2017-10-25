package ca.on.conestogac.assignment1;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ListActivity extends AppCompatActivity {
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
    private TextView unitsTextview;
    private TextView efsTextview;
    private TextView carsTextview;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
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

    private AllDB db = new AllDB(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        printAll();

    }
    public void printAll(){
        unitsTextview = (TextView) findViewById(R.id.units_textview);
        efsTextview = (TextView) findViewById(R.id.efs_textview);
        carsTextview = (TextView) findViewById(R.id.cars_textview);


        StringBuilder sb = new StringBuilder();

        sb.append("[NAME][YEAR][ENGINE]\n");
        sb.append("----------------------------------------\n");
        ArrayList<Car> cars = db.getCars();
        for (Car car : cars) {
            sb.append(car.getName() + " " +
                    car.getYear() + " " + car.getEngine() + "\n");
        }
        carsTextview.setText(sb.toString());

        sb.setLength(0);
        ArrayList<Unit> units = db.getUnits();
        for (Unit u : units) {
            sb.append("[" + u.getName() + "]");
        }
        sb.append("\n----------------------------------------\n");
        ArrayList<Efficiency> efficiencies = db.getEfficiencies();
        for (Efficiency e : efficiencies) {
            int unitType = e.getUnitType();
            Float fe1 = 0.0f;
            Float fe2 = 0.0f;
            Float fe3 = 0.0f;
            if (unitType == 1){
                fe1 = e.getFe();
                fe2 = Math.round(100/fe1 * 10f) / 10f;
                fe3 = Math.round((fe1 * 3.7854f)/1.6093f * 10f) / 10f;
            }
            else if (unitType == 2){
                fe2 = e.getFe();
                fe1 = Math.round((1f/fe2)*100f * 10f) / 10f;
                fe3 = Math.round( 1/(fe2/3.7854f)*62.1371f * 10f) / 10f;
            }
            else if (unitType == 3){
                fe3 = e.getFe();
                fe1 = Math.round((fe3 / 3.7854f) * 1.6093f * 10f) / 10f;
                fe2 = Math.round( 1/(fe3/3.7854f)* 62.1371f * 10f) / 10f;
            }
            sb.append(fe1.toString() + " = " + fe2.toString() + " = "
                    + fe3.toString() + "\n");
        }
        efsTextview.setText(sb.toString());

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

//    private void toggle() {
//        if (mVisible) {
//            hide();
//        } else {
//            show();
//        }
//    }

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
        // Show the system bar
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
}
//https://www.formulaconversion.com/formulaconversioncalculator.php?convert=kilometersperliters_to_milespergallons