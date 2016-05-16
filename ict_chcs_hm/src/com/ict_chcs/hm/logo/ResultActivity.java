package com.ict_chcs.hm.logo;

import com.example.logo.R;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ResultActivity extends Activity {
	Button btnb,btnc,btnd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
	
		btnb = (Button) findViewById(R.id.re_button1);
        btnc = (Button) findViewById(R.id.re_button2);
        btnd = (Button) findViewById(R.id.re_button3);
        
        btnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager beauty2Manager = getFragmentManager();
                FragmentTransaction beauty2Transction = beauty2Manager.beginTransaction();
                beauty2Fragment beauty2 = new beauty2Fragment();
                beauty2Transction.replace(R.id.result_layout, beauty2);
                beauty2Transction.commit();
            }
        });
        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager beauty3Manager = getFragmentManager();
                FragmentTransaction beauty3Transction = beauty3Manager.beginTransaction();
                beauty3Fragment beauty3 = new beauty3Fragment();
                beauty3Transction.replace(R.id.result_layout, beauty3);
                beauty3Transction.commit();
            }
        });
        btnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager beauty4Manager = getFragmentManager();
                FragmentTransaction beauty4Transction = beauty4Manager.beginTransaction();
                beauty4Fragment beauty4 = new beauty4Fragment();
                beauty4Transction.replace(R.id.result_layout, beauty4);
                beauty4Transction.commit();
            }
        });
    }

    public static   class  beauty2Fragment extends Fragment {
      
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View fragbeauty2 = inflater.inflate(R.layout.day,container,false);
            return fragbeauty2;
        }
    }
    public static   class  beauty3Fragment extends Fragment {
      
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View fragbeauty3 = inflater.inflate(R.layout.week,container,false);
            return fragbeauty3;
        }
    }
    public static   class  beauty4Fragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View fragbeauty4 = inflater.inflate(R.layout.month,container,false);
            return fragbeauty4;
        }
    }

   
}