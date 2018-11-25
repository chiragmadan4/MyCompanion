package com.example.chiragmadan.mycompanion;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AdminLoginPage extends AppCompatActivity {
    EditText editText1;
    EditText editText2;
    String json_string;
    public void login(View view)
    {
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);

        String method = "loginAdmin";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,editText1.getText().toString(),editText2.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_page);
    }

    class BackgroundTask extends AsyncTask<String,Void,String> {


        Context ctx;
        public BackgroundTask(Context ctx) {
            this.ctx=ctx;
        }


        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            //Log.i("msg",result);
            try
            {
                JSONObject abc = new JSONObject(result);
                JSONArray arr = abc.getJSONArray("server response");
                JSONObject admin = arr.getJSONObject(0);
                if(admin.getString("user_id").equals("1"))
                {
                    Toast.makeText(ctx,"login successful",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AdminLoginPage.this,AdminHomePage.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(ctx,"login failed",Toast.LENGTH_SHORT).show();
                }

            }catch (Exception e)
            {
                e.printStackTrace();
                Log.i("msg","error in parsing json");
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {
            String method = params[0];
            String reg_url;

            if(method.equals("loginAdmin"))
            {
                reg_url = "http://192.168.137.1/adminLogin.php";
                String user_id = params[1];
                String password = params[2];
                Log.i("msg", "reached register");
                try
                {
                    URL url = new URL(reg_url);
                    Log.i("msg","reached 1");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    Log.i("msg","reached 2");
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    Log.i("msg","reached 3");
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                    String data = URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8")+"&"+
                            URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&";
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while((json_string=bufferedReader.readLine())!=null)
                    {
                        stringBuilder.append(json_string+"\n");
                    }
                    bufferedReader.close();
                    httpURLConnection.disconnect();
                    inputStream.close();
                    Log.i("msg","reached 4");

                    return stringBuilder.toString().trim()+"checkString";
                }
                catch (Exception e)
                {
                    Log.i("msg","reached catch");
                    Log.e("msg","exception",e);
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
