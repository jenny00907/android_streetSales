package com.example.jennylee.proj399;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class cartActivity extends AppCompatActivity implements OnClickListener{
    private SharedPreferences saved;
    private int price;
    private int total;
    private String user;
    private TextView nameCartText;
    private TextView priceText;
    private ImageView bgImage;
    private Button resetButton;


    @Override
    protected void onCreate(Bundle state){
        super.onCreate(state);
        setContentView(R.layout.activity_cart);

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        //setSupportActionBar().setDisplayHomeAsUpEnabled(true);

        priceText = findViewById(R.id.priceText);
        nameCartText = findViewById(R.id.nameCartText);
        bgImage = findViewById(R.id.backgroundImage);
        resetButton = findViewById(R.id.resetButton);

        saved = PreferenceManager.getDefaultSharedPreferences(this);
        resetButton.setOnClickListener(this);

    }
    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences.Editor editor = saved.edit();

        editor.putString("price", String.valueOf(price));
        editor.putString("cart", String.valueOf(0));
        editor.commit();
    }

    @Override
    public void onResume(){
        super.onResume();

        total = Integer.parseInt(saved.getString("cart", "0"));
        user = saved.getString("user", "");
        displayPrice(total);
        displayUser(user);
        changeBackground();
    }
    @Override
    public void onClick(View view){
        if(view.getId() == R.id.resetButton){
            Intent intent = new Intent(this, MainActivity.class);
            reset();
            startActivity(intent);
        }

    }

    private void displayUser(String user) {
        nameCartText.setText(user);
    }

    private void displayPrice(int total) {

        priceText.setText(Integer.toString(total));
    }


    private void reset(){
        SharedPreferences saved;
        saved = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = saved.edit();
        total = 0;
        user = "";
        editor.putString("cart", String.valueOf(total));
        editor.putString("user", "your name");
        editor.putString("background", "pink");
        editor.commit();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void changeBackground(){
        //set back ground
        String background;
        background = saved.getString("background","pink");

        if(background.equals("pink")){
            bgImage.setBackground(getDrawable(R.drawable.pink));

        }
        else if(background.equals("blue")) {
            bgImage.setBackground(getDrawable(R.drawable.blue));
        }
    }
}
