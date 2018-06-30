package com.example.marc.volley_03;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class MainActivity extends AppCompatActivity {
    Button button1;
    EditText editText1;
    TextView textView1;
    RequestQueue r;
    String TAG = "Volley_03";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "starting in onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.Button1);
        editText1 = (EditText) findViewById(R.id.EditText1);
        textView1 = (TextView) findViewById(R.id.TextView1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "button clicked");
                getPhoneNumberMMSgateway(editText1.getText().toString());
            }
        });
    }
    protected void getPhoneNumberMMSgateway(String phone_number) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Date date = new Date();
        final String url = "http://www.tnrcamera.com/phone_carrier_lookup.php?phone_number=" + phone_number;
        System.out.println(dateFormat.format(date));
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d(TAG, "Volley Response: " + response.toString());
                        //textView1.setText(dateFormat.format(date) + "\n" + response.toString() + "\n");
                        try {
                            String mms_gateway = response.getString("mms_gateway");
                            textView1.setText(dateFormat.format(date) + "\n" + "MMS gateway: " + mms_gateway + "\n");
                        } catch (Exception e) {
                            try {
                                String error_text = response.getString("error");
                                textView1.setText(dateFormat.format(date) + "\n" + error_text + "\n");
                            } catch (Exception e2) {
                                Log.d(TAG, "Error trying to get JSON message contents.");
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.d("Error__Response", error.getMessage());
                        //String errorMessage = "url: " + url + " (HTTP return code): " + error.networkResponse.statusCode;
                        String errorMessage = "url: " + url;
                        Log.d(TAG, "Volley Error: " + errorMessage);
                        textView1.setText(dateFormat.format(date) + "\n" + errorMessage);
                    }
                }
        );
        // add it to the RequestQueue
        queue.add(getRequest);
    }
}
