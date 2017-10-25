package ca.on.conestogac.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button rSButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.lengthButton:
                sendMessage1(view);
                break;
            case R.id.temperButton:
                sendMessage2(view);
                break;
            case R.id.InputACarButton:
                sendMessage3(view);
                break;
            case R.id.listButton:
                sendMessage4(view);
                break;
            case R.id.deletAllButton:
                sendMessage5(view);
                break;
        }
    }

    public void sendMessage1(View view){
        Intent intent = new Intent(this, lengthActivity.class);
        startActivity(intent);
    }
    public void sendMessage2(View view){
        Intent intent = new Intent(this, tempActivity.class);
        startActivity(intent);
    }
    public void sendMessage3(View view) {
        Intent intent = new Intent(this, InputACar.class);
        startActivity(intent);
    }
    public void sendMessage4(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
    public void sendMessage5(View view) {
        Intent intent = new Intent(this, DeleteAllActivity.class);
        startActivity(intent);
    }
}
