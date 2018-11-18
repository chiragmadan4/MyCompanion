package com.example.chiragmadan.mycompanion;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FeedbackPage extends AppCompatActivity {

    String json_string;
    String roll_no;
    String feedback_content;
    EditText editText;
    String time;
    public void uploadFeedback(View view)
    {
        //background task
        BackgroundTask backgroundTask = new BackgroundTask(this);
        feedback_content = editText.getText().toString();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        time = formatter.format(date);
        backgroundTask.execute("uploadFeedback",roll_no,feedback_content,time);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_page);
        roll_no = getIntent().getExtras().getString("roll_no");
        editText = findViewById(R.id.editText);
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
            Toast.makeText(ctx,"Feedback successfully sent",Toast.LENGTH_LONG).show();
            editText.setText("");
            //Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {
            String method = params[0];
            String reg_url;
            if(method.equals("uploadFeedback"))
            {
                reg_url = "http://192.168.137.1/uploadFeedback.php";
                String roll_no = params[1];
                String feedback_content = params[2];
                String time = params[3];
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
                            URLEncoder.encode("feedback_content","UTF-8")+"="+URLEncoder.encode(feedback_content,"UTF-8")+"&"+
                            URLEncoder.encode("time","UTF-8")+"="+URLEncoder.encode(time,"UTF-8")+"&";

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
