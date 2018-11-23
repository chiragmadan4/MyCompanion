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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdminLeaveEntryPage extends AppCompatActivity {
    String json_string;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_leave_entry_page);
        listView = findViewById(R.id.listView);
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("getLeaveEntry");
    }


    class propertyArrayAdapter extends ArrayAdapter<LeaveEntryClass>
    {
        private Context context;
        private List<LeaveEntryClass> list;

        public propertyArrayAdapter(Context context,int resource,
                                    ArrayList<LeaveEntryClass> objects)
        {
            super(context,resource,objects);
            this.context = context;
            this.list = objects;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            //get the property we are displaying
            LeaveEntryClass obj = list.get(position);

            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.leaveentrylayout, null);
            TextView textView2 = view.findViewById(R.id.textView2);
            TextView textView3 = view.findViewById(R.id.textView3);
            TextView textView4 = view.findViewById(R.id.textView4);
            TextView textView5 = view.findViewById(R.id.textView5);
            textView2.setText(obj.getRoll_no());
            textView3.setText(obj.getRoom_no());
            textView4.setText(obj.getFrom_date());
            textView5.setText(obj.getTo_date());
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
            //Toast.makeText(ctx,"menu is....",Toast.LENGTH_LONG).show();
            ArrayList<LeaveEntryClass> leaveEntries = new ArrayList<>();
            try
            {
                JSONObject obj = new JSONObject(result);
                JSONArray arr = obj.getJSONArray("server response");
                for(int i=0;i<arr.length();i++)
                {
                    Log.i("msg",arr.getJSONObject(i).getString("Name"));
                    Log.i("msg","output");
                    leaveEntries.add(new LeaveEntryClass(arr.getJSONObject(i).getString("Name"),
                            arr.getJSONObject(i).getString("Roll_no"),
                            arr.getJSONObject(i).getString("Room_no"),
                            arr.getJSONObject(i).getString("From_date"),
                            arr.getJSONObject(i).getString("To_date")));
                }
                propertyArrayAdapter arrayAdapter = new propertyArrayAdapter(ctx, 0,leaveEntries);
                listView.setAdapter(arrayAdapter);

            }
            catch (Exception e)
            {
                Log.i("msg","error in parsing json",e);
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
            if(method.equals("getLeaveEntry"))
            {
                reg_url = "http://192.168.137.1/getLeaveEntry.php";
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
