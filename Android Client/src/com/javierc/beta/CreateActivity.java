package com.javierc.beta;

import java.io.IOException;
import com.javierc.beta.data.*;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CreateActivity extends Activity {
	
	private Button send;
	private TextView nameTV, handleTV, statusTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		send = (Button)findViewById(R.id.createDocBtn);
		nameTV = (TextView)findViewById(R.id.etCreateName);
		handleTV = (TextView)findViewById(R.id.etCreateHandle);
		statusTV = (TextView)findViewById(R.id.tvSendStatus);
		
		send.setOnClickListener(sendOCL());
	}


	
	private OnClickListener sendOCL() {
		
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new CreateTask(statusTV).execute(
						nameTV.getText().toString(),
						handleTV.getText().toString()
						);
				
			}
		};
	}



	private class CreateTask extends AsyncTask<String, Void, String>{

		JsonHttpHandler handler = new JsonHttpHandler();
		String status = "Success";
		TextView tv;
		public CreateTask(TextView tv) {
			this.tv = tv;
		}
		@Override
		protected String doInBackground(String... params) {
			JSONObject json = new JSONObject();
			try {
				
				json.accumulate("name", params[0]);
				json.accumulate("handle", params[1]);
					
				
				handler.postJSONfromUrl("http://10.0.3.2:5000/new/user", json);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				status = "Error Protocal";
			} catch (IOException e) {
				e.printStackTrace();
				status = "Error reading";
			} catch (JSONException e) {
				e.printStackTrace();
				status = "JSON Error";
			} 
			return status;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			tv.setText(result);
		}
		
	}
	
}
