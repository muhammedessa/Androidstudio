package org.codeforiraq.myvolley;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.codeforiraq.myvolley.Controller.SessionManager;
import org.codeforiraq.myvolley.UI.LoginActivity;
import org.codeforiraq.myvolley.UI.RegisterActivity;

public class MainActivity extends AppCompatActivity {


   private TextView myTokenValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        myTokenValue = findViewById(R.id.tokenTextValue);

        if (SessionManager.getInstance(this).isLoggedIn()) {
            if(SessionManager.getInstance(this).getToken() != null){
                myTokenValue.setText(SessionManager.getInstance(this).getToken().getToken());
            }

        }else{
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

                SessionManager.getInstance(this).logout();

                finish();
                startActivity(new Intent(this, LoginActivity.class));

            }




        return super.onOptionsItemSelected(item);
    }
}
