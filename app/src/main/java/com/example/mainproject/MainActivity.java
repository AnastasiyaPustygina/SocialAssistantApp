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
        MainMyThread mainMyThread = new MainMyThread(this);
        mainMyThread.run();
        try {
            mainMyThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        OpenHelper openHelper = new OpenHelper(this, "OpenHelder",
                null, OpenHelper.VERSION);
        try {
            Log.e("MSG", openHelper.findAllMsg().toString());
        }catch (Exception e){
            Log.e("MSG", e.getMessage());
        }
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
        new AppApiVolley(context).fillMsg();

    }
}
