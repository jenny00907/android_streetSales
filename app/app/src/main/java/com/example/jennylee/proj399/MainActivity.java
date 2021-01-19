package com.example.jennylee.proj399;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Build;
import android.annotation.TargetApi;
import android.util.Log;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.Button;



public class MainActivity extends AppCompatActivity implements OnClickListener, EditText.OnEditorActionListener{
    private String user = "";

    private TextView welcomeText;
    private EditText nameText;
    private Button listButton;
    private Button cartButton;
    private ImageView locationImage;
    private ImageView backgroundImage;
    private SharedPreferences saved;


    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        listButton = findViewById(R.id.listButton);
        cartButton = findViewById(R.id.cartButton);
        welcomeText = findViewById(R.id.welcomeText);
        nameText = (EditText)findViewById(R.id.nameText);

        locationImage = findViewById(R.id.locationImage);
        backgroundImage = findViewById(R.id.backgroundImage);


        nameText.setOnEditorActionListener(this);
        listButton.setOnClickListener(this);
        cartButton.setOnClickListener(this);

        saved = PreferenceManager.getDefaultSharedPreferences(this);

    }

    @Override
    public void onPause() {
        SharedPreferences.Editor editor = saved.edit();
        //Log.d("MAINACTIVITY", "user: "+ getUser());
        setUser(nameText.getText().toString());
        editor.putString("user", getUser());

        editor.commit();

        super.onPause();
    }


    @Override
    public void onResume() {
        super.onResume();

        //setUser(nameText.getText().toString());
        setUser(saved.getString("user",getUser()));
        nameText.setText(saved.getString("user", getUser()));

        changeBackground();
    }

    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            switch (view.getId()) {
                case R.id.nameText:
                    setUser(nameText.getText().toString());
                    //Log.d("EDITOR", "setUSER:  "+ this.user);
                    break;

            }
            return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch (menu.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                return true;

            case R.id.menu_about:
                Toast.makeText(this, "Street Sale Application \n Listing of the Streets of Eugene, OR \n Made By Jenny Lee for CIS399 Project.",
                        Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }

    @Override
    public void onClick(View view) {
        SharedPreferences.Editor editor = saved.edit();
        if (view.getId() == R.id.listButton) {
            Intent intent = new Intent(this, ListingActivity.class);
            startActivity(intent);
            editor.putString("user", getUser());
            editor.putString("cart", "0");

        } else if (view.getId() == R.id.cartButton) {
            Intent intent = new Intent(this, cartActivity.class);
            editor.putString("user", getUser());

            startActivity(intent);
        }
        editor.commit();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void changeBackground() {
        String background;
        background = saved.getString("background", "pink");

        if (background.equals("blue")) {
            backgroundImage.setBackground(getDrawable(R.drawable.blue));
        }
        else if (background.equals("pink")){
            backgroundImage.setBackground(getDrawable(R.drawable.pink));
        }
    }

    public String getUser(){
        return this.user;
    }
    public void setUser(String user){
        this.user = user;
        //Log.d("SETUSER", "SET user: " +this.user);

    }
}



