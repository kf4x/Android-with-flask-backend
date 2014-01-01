package com.javierc.beta;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.javierc.beta.data.JsonHttpHandler;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FindActivity extends Activity {
	
	private Button find;
	private TextView textToFindTV, resultsTV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find);
		find = (Button) findViewById(R.id.findNameBtn);
		textToFindTV = (TextView) findViewById(R.id.ETfind);
		resultsTV = (TextView) findViewById(R.id.resultTV);
		
		find.setOnClickListener(findOCL());
	}

	private OnClickListener findOCL() {
		// TODO Auto-generated method stub
		return new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new FindTask(resultsTV).execute(textToFindTV.getText().toString());
				
			}
		};
	}


	
	private class FindTask extends AsyncTask<String, Void, String>{
		private TextView tv;
		public FindTask(TextView tv) {
			this.tv = tv;
		}

		@Override
		protected String doInBackground(String... params) {

			JSONObject json = null;
            String s = "";

			try {
				json = new JsonHttpHandler().getJSONfromUrl("http://10.0.3.2:5000/user/"+params[0]);
				if (json == null){return "error";}
				
				// Getting top key
                JSONArray users = json.getJSONArray("users");
                
//                Decoding
                for (int i = 0; i < users.length(); i++) {
                    JSONObject user;
                    user = users.getJSONObject(i);                    
                    s += user.getString("name") + " " +  user.getString("handle") + "\n";
                   
                }
				
				
                
			} catch (IllegalStateException e) {
				e.printStackTrace();
				Log.e("find", "State excep");
			} catch (JSONException e) {
				e.printStackTrace();
				Log.e("find", "JSON excep");
			} catch (IOException e) {
				e.printStackTrace();
				Log.e("find", "IO excep");
			}

			return s;

		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			tv.setText(result);
		}
		
	}

}
