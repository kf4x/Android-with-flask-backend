package com.javierc.beta;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button createBtn, findBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		createBtn = (Button) findViewById(R.id.createBtn);
		findBtn = (Button) findViewById(R.id.findBtn);
		
		createBtn.setOnClickListener(createBtnOCL());
		findBtn.setOnClickListener(findBtnOCL());
		
	}

	private OnClickListener findBtnOCL() {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i  = new Intent(getBaseContext(), FindActivity.class);
				startActivity(i);
				
			}
		};
	}

	private OnClickListener createBtnOCL() {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i  = new Intent(getBaseContext(), CreateActivity.class);
				startActivity(i);
				
			}
		};
	}

}
