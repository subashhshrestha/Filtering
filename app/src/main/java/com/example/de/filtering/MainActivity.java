package com.example.de.filtering;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    Button recommend;
    ListView lv;
    RequestQueue requestQueue;
    String[] n=new String[3];
    String[][] r=new String[30000][3000];
    String[][] data1=new String[30000][30000];
    int tempcol=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recommend=findViewById(R.id.recommend);
        lv=findViewById(R.id.lv);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        n=getUser("jaLWTpoUdDhMr2ChUI9Lr1u2NIF3");
        r=getAllUser();


        //-------------------------------

        //------------------------------
    }
    public String[] getUser(final String id){
        final String[] uname=new String[100];
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.137.1/subash/getuserinformation.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject obj1 = new JSONObject(response);
                    JSONArray a1=obj1.getJSONArray("user");
                    JSONObject obj2=a1.getJSONObject(0);
                    String name=obj2.getString("name");
                    String username=obj2.getString("username");
                    uname[0] =username;
                    uname[1]=name;
//                    t1.setText("Welcome "+uname[0]);// ya bata set garda chai display vayo
                    Toast.makeText(MainActivity.this, username, Toast.LENGTH_SHORT).show();

                }
                catch(Exception e){

                    Log.d("VAL", ""+e);
                    Toast.makeText(MainActivity.this, "Internal Faliure", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                    progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Error in connection.", Toast.LENGTH_LONG).show();
                Log.d("VAL", ""+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> myMap = new HashMap<>();
                myMap.put("id", id);
                return myMap;
            }
        };
        requestQueue.add(stringRequest);
        return uname;//returning array of all the user
    }
    public String[][] getAllUser(){
        final String[][] ratinginfo=new String[30000][30000];
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.137.1/subash/show.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject obj1 = new JSONObject(response);
                    JSONArray a1=obj1.getJSONArray("user");
                    for(int i=0;i<a1.length();i++) {
                        JSONObject obj2 = a1.getJSONObject(i);
                        String uid = obj2.getString("uid");
                        String pid = obj2.getString("pid");
                        String rating =obj2.getString("rating");
                        ratinginfo[i][0]=uid;
                        ratinginfo[i][1]=pid;
                        ratinginfo[i][2]=rating;
                    }
//                    t1.setText("Welcome "+uname[0]);// ya bata set garda chai display vayo

                }
                catch(Exception e){

                    Log.d("VAL", ""+e);
                    Toast.makeText(MainActivity.this, "Internal Faliure", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                    progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Error in connection.", Toast.LENGTH_LONG).show();
                Log.d("VAL", ""+error);
            }
        });
        requestQueue.add(stringRequest);
        return ratinginfo;//returning array of all the user
    }
//    public void rearrange(){
//        TreeSet<String> userid=new TreeSet<>();
//        TreeSet<String> placeid=new TreeSet<>();
//        String rating="";
//        Map<String,Map<String,String>> dataMap=new HashMap<>();
//        String userlist;
//        String placelist;
//        for(int i=0;i<24896;i++) {
//            userlist=r[i][0];
//            placelist=r[i][1];
//            userid.add(userlist);
//            placeid.add(placelist);
//            rating=r[i][2];
//            Map<String,String> map =  dataMap.getOrDefault(
//                    userlist, new HashMap<>());
//            map.put(placelist, rating);
//            dataMap.put(userlist, map);
//        }
//        int i=1;
//        int j=0;
//        int temprow;
//        int a=1;
//        for(String t:placeid) {
//            data1[0][a]=t;
//            a++;
//        }
//        data1[0][0]="uid";
//        for(String s:userid) {
//            j=1;
//            data1[i][0]=s;
//            for(String t:placeid) {
//                Map<String,String> map=dataMap.getOrDefault(s, new HashMap<>());
//                String rate=map.getOrDefault(t, "0");
//                data1[i][j]=rate;
////	                System.out.print("\t"+rate);
//
//                j++;
//            }
//            i++;
////			System.out.println();
//            tempcol=j;
//        }
//    }
}

