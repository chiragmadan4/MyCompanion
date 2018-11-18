package com.example.chiragmadan.mycompanion;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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

public class MessMenuPage extends AppCompatActivity {
    String json_string;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menu_page);
        listView = findViewById(R.id.listView);
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("getMenu");

    }

    class propertyArrayAdapter extends ArrayAdapter<Menu>
    {
        private Context context;
        private List<Menu> list;

        public propertyArrayAdapter(Context context,int resource,
                                    ArrayList<Menu> objects)
        {
            super(context,resource,objects);
            this.context = context;
            this.list = objects;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            //get the property we are displaying
            Menu obj = list.get(position);

            //get the inflater and inflate the XML layout for each item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.menu_layout, null);
            TextView textView1 = view.findViewById(R.id.textView1);
            TextView textView2 = view.findViewById(R.id.textView2);
            TextView textView3 = view.findViewById(R.id.textView3);
            TextView textView4 = view.findViewById(R.id.textView4);
            textView1.setText(obj.getDay());
            textView2.setText(obj.getBreakfast());
            textView3.setText(obj.getLunch());
            textView4.setText(obj.getDinner());
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
            ArrayList<Menu> menu = new ArrayList<>();
            try
            {
                JSONObject obj = new JSONObject(result);
                JSONArray arr = obj.getJSONArray("server response");
                for(int i=0;i<arr.length();i++)
                {
                    Log.i("msg",arr.getJSONObject(i).getString("Day"));
                    Log.i("msg","output");
                    menu.add(new Menu(arr.getJSONObject(i).getString("Day"),
                            arr.getJSONObject(i).getString("Breakfast"),
                            arr.getJSONObject(i).getString("Lunch"),
                            arr.getJSONObject(i).getString("Dinner")));
                }
                propertyArrayAdapter arrayAdapter = new propertyArrayAdapter(ctx, 0,menu);
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
            if(method.equals("getMenu"))
            {
                reg_url = "http://192.168.137.1/getMenu.php";
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
