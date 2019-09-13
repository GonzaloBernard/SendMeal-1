package com.example.sendmeal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class HomeSendMeal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_send_meal);
        Toolbar toolbar =  (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);



        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.toolbar_registrar:
                            // User chose the "Settings" item, show the app settings UI...
                            return true;

                        case R.id.toolbar_crearitem:
                            // User chose the "Favorite" action, mark the current item
                            // as a favorite...
                            return true;

                        default:
                            // If we got here, the user's action was not recognized.
                            // Invoke the superclass to handle it.
                            return super.onOptionsItemSelected(item);

                    }
            }});
    }

    }

}
