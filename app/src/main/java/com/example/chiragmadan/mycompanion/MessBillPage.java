package com.example.chiragmadan.mycompanion;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.List;

public class MessBillPage extends AppCompatActivity {
    ListView listView;
    String roll_no;
    String json_string;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_bill_page);
        roll_no = getIntent().getExtras().getString("roll_no");
        textView = findViewById(R.id.textView1);
        textView.setText("Mess bill for student "+roll_no);
        listView = findViewById(R.id.listView);

        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("getMessBill",roll_no);
    }

    class propertyArrayAdapter extends ArrayAdapter<Bill>
    {
        private Context context;
        private List<Bill> list;

        public propertyArrayAdapter(Context context,int resource,
                                    ArrayList<Bill> objects)
        {
            super(context,resource,objects);
            this.context = context;
            this.list = objects;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            //get the property we are displaying
            Bill obj = list.get(position);

            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.bill_layout, null);
            TextView textView1 = view.findViewById(R.id.textView1);
            TextView textView2 = view.findViewById(R.id.textView2);
            textView1.setText(obj.getMonth());
            textView2.setText(obj.getBill());
            return  view;
        }
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
            try
            {
                JSONObject obj = new JSONObject(result);
                JSONArray arr = obj.getJSONArray("server response");
                ArrayList<Bill> bills = new ArrayList<>();
                for (int i=0;i<arr.length();i++)
                {
                    JSONObject obj1= arr.getJSONObject(i);
                    bills.add(new Bill(obj1.getString("Month"),obj1.getString("mess_bill")));
                    Log.i("msg",obj1.getString("Month"));
                    Log.i("msg",obj1.getString("mess_bill"));
                }
                propertyArrayAdapter propertyArrayAdapter = new propertyArrayAdapter(ctx,0,bills);
                listView.setAdapter(propertyArrayAdapter);

            }catch (Exception e)
            {
                Log.i("msg","error in parsing",e);
                e.printStackTrace();
            }
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

            if(method.equals("getMessBill"))
            {
                reg_url = "http://192.168.137.1/getMessBill.php";
                String roll_no = params[1];
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

                    String data = URLEncoder.encode("roll_no","UTF-8")+"="+URLEncoder.encode(roll_no,"UTF-8");

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
                    Toast.makeText(ctx,"Please try again later",Toast.LENGTH_LONG).show();
                    Log.i("msg","reached catch");
                    Log.e("msg","exception",e);
                    e.printStackTrace();
                }

            }
            return null;
        }
    }
}
