package com.example.mainproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mainproject.domain.Message;
import com.example.mainproject.rest.AppApiVolley;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MainMyThread(this).run();
    }

}
class MainMyThread extends Thread{
    private Context context;

    public MainMyThread(Context context) {
        this.context = context;
    }

    @Override
    public void run() {

        new AppApiVolley(context).fillOrganization();
        new AppApiVolley(context).fillChats();
    }
}
