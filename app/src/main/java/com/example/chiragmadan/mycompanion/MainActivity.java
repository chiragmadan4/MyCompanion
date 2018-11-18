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

public class MainActivity extends AppCompatActivity {

    EditText roll_no_field;
    EditText password_field;
    String roll_no;
    String password;
    String json_string;
    public void login(View view)
    {

        roll_no_field = findViewById(R.id.roll_no_field);
        password_field = findViewById(R.id.password_field);
        roll_no = roll_no_field.getText().toString();
        password = password_field.getText().toString();

        //validate login
        String method = "loginUser";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,roll_no,password);

    }

    public void guestPage(View view)
    {
        Intent i = new Intent(MainActivity.this,GuestPage.class);
        startActivity(i);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

            //super.onPostExecute(result);
            try
            {
                JSONObject obj = new JSONObject(result);
                Log.i("msg",result);
                JSONArray users = obj.getJSONArray("server response");
                JSONObject firstUser = users.getJSONObject(0);
                String x = firstUser.getString("roll_no");
                Log.i("x",x);
                if(x.equals("1"))
                {
                    //login credentials validated
                    Intent i = new Intent(MainActivity.this,HomeActivity.class);
                    i.putExtra("roll_no",roll_no);
                    startActivity(i);
                }
                else
                {
                    //login failed
                    Toast.makeText(ctx,"Login Failed",Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Log.i("msg","error in json object parsing",e);
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
            if(method.equals("loginUser"))
            {
                reg_url = "http://192.168.137.1/loginUser.php";
                String roll_no = params[1];
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

                    String data = URLEncoder.encode("roll_no","UTF-8")+"="+URLEncoder.encode(roll_no,"UTF-8")+"&"+
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

                    return stringBuilder.toString().trim();
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
