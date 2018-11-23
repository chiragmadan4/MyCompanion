package com.example.chiragmadan.mycompanion;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class leaveEntry extends AppCompatActivity {

    String roll_no;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    public void uploadLeaveEntry(View view)
    {
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("uploadLeaveEntry",
                editText1.getText().toString(),
                editText2.getText().toString(),
                editText3.getText().toString(),
                editText4.getText().toString(),
                editText5.getText().toString()
                );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_entry);
        roll_no = getIntent().getExtras().getString("roll_no");
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);
        editText2.setText(roll_no);
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
            Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {
            String method = params[0];
            String reg_url;

            if(method.equals("uploadLeaveEntry"))
            {
                reg_url = "http://192.168.137.1/uploadLeaveEntry.php";
                String name = params[1];
                String roll_no = params[2];
                String room_no = params[3];
                String from_date = params[4];
                String to_date = params[5];
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

                    String data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                            URLEncoder.encode("roll_no","UTF-8")+"="+URLEncoder.encode(roll_no,"UTF-8")+"&"+
                            URLEncoder.encode("room_no","UTF-8")+"="+URLEncoder.encode(room_no,"UTF-8")+"&"+
                            URLEncoder.encode("from_date","UTF-8")+"="+URLEncoder.encode(from_date,"UTF-8")+"&"+
                            URLEncoder.encode("to_date","UTF-8")+"="+URLEncoder.encode(to_date,"UTF-8")+"&";

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();
                    InputStream is = httpURLConnection.getInputStream();
                    is.close();
                    Log.i("msg","reached 4");
                    return "Leave entry successfully submitted";
                }
                catch (Exception e)
                {
                    Toast.makeText(ctx,"error in submission",Toast.LENGTH_LONG).show();
                    Log.i("msg","reached catch");
                    Log.e("msg","exception",e);
                    e.printStackTrace();
                }

            }
            return null;
        }
    }
}
