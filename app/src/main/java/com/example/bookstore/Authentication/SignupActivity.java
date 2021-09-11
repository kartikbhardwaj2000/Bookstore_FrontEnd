package com.example.bookstore.Authentication;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.bookstore.Authentication.Fragments.FragmentListener;
import com.example.bookstore.Authentication.Fragments.Step1Fragment;
import com.example.bookstore.Authentication.Fragments.Step2Fragment;
import com.example.bookstore.Authentication.Fragments.Step3Fragment;
import com.example.bookstore.Authentication.Fragments.Step4Fragment;
import com.example.bookstore.HomePage.HomeActivity;
import com.example.bookstore.R;
import com.example.bookstore.SessionManager;
import com.example.bookstore.VolleySingleton;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignupActivity extends AppCompatActivity  implements FragmentListener,Step4Fragment.Listener,Step1Fragment.DataListener,Step2Fragment.DataListener, Step3Fragment.OtpListener,Response.ErrorListener,RetryPolicy {

    private ProgressBar progressBar;
    private String userName;
    private String phoneNumber;
    private String userLocation;
    private RequestQueue requestQueue;
    private final String  URL="https://bookstore-rc.herokuapp.com/";
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar supportToolBar= findViewById(R.id.tool_bar);
         progressBar =findViewById(R.id.progress_bar);

        setSupportActionBar(supportToolBar);


   ActionBar actionBar= getSupportActionBar();
   if(userName==null)
   {
       Log.d("state","null");

   }else {
       Log.d("state",userName);

   }

        if(actionBar!=null)
   {
       actionBar.setDisplayHomeAsUpEnabled(true);
   }

   if(savedInstanceState==null||!savedInstanceState.getBoolean("isRecreated"))
   {
       addFirstFragment();

   }



    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isRecreated",true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getSupportFragmentManager().getFragments().size();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment fragment=fragments.get(fragments.size()-1);
        if(fragment instanceof Step1Fragment)
        {
            setTitle("step-1");
            progressBar.setProgress(25);
        }
        if(fragment instanceof Step2Fragment)
        {
            setTitle("step-2");
            progressBar.setProgress(50);
        }
        if(fragment instanceof Step3Fragment)
        {
            setTitle("step-3");
            progressBar.setProgress(75);
        }
        if(fragment instanceof Step4Fragment)
        {
            setTitle("step-4");
            progressBar.setProgress(100);
        }



    }

    @Override
    public void nextButtonCLicked(String nextFragName) {
        Fragment nextFragment =null;
        switch (nextFragName)
        {
            case "step1":
                nextFragment=new Step1Fragment();
                break;
            case "step2":
                progressBar.setProgress(50);
                nextFragment=new Step2Fragment();
                setTitle("step-2");
                break;
            case "step3":
                progressBar.setProgress(75);
                nextFragment=new Step3Fragment(phoneNumber);
                setTitle("step-3");
                break;
            case "step4":
                progressBar.setProgress(100);
                nextFragment=new Step4Fragment(userName);
                setTitle("step-4");
                break;



        }
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer,nextFragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();


    }


    public void startHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        createNewSession();
        startActivity(intent);
        finish();

    }

    @Override
    public void onError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    private void addFirstFragment()
    {
        setTitle("step-1");

        Step1Fragment step1Fragment = new Step1Fragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer,step1Fragment);
        transaction.commit();

    }

    @Override
    public void setName(String name) {
        userName=name;
    }

    @Override
    public void setPhoneNo(String phoneNo) {
        phoneNumber=phoneNo;
    }

    @Override
    public void setLocation(String location) {
        userLocation=location;
    }

    @Override
    public void signUpUser() {
        int count = getSupportFragmentManager().getFragments().size();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment fragment=fragments.get(fragments.size()-1);
        if(!(fragment instanceof Step4Fragment))
        {
            return;
        }

        Step4Fragment step4Fragment = (Step4Fragment) fragment;
        step4Fragment.showProgressBar("creating new user  .....");
        if(requestQueue==null)
        {
            requestQueue= VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();

        }
        JSONObject jsonbody= new JSONObject();
        try {
            jsonbody.put("phoneNumber",phoneNumber);
            jsonbody.put("name",userName);
            JSONObject locationObj = new JSONObject();
            locationObj.put("city",userLocation);
            jsonbody.put("location",locationObj);

        }catch (JSONException e){
            Log.d("error",e.toString());
        }
        String requestBody =jsonbody.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URL+"auth/signup",null,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {

                Log.d("response",response.toString());
                String status = null;
                try {
                    status = response.getString("status");
                    JSONObject userObj=response.getJSONObject("doc");
                    userID=userObj.getString("_id");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(!status.equals("success"))
                {
                    onError("some error occurred please try again");
                }
                step4Fragment.closeProgressBar();
                startHomeActivity();
            }

        },this
        ){


            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody()  {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }


        };
        jsonObjectRequest.setRetryPolicy(this);

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void sendOtp(String phoneNo) {
        int count = getSupportFragmentManager().getFragments().size();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment fragment=fragments.get(fragments.size()-1);
        if(!(fragment instanceof Step3Fragment))
        {
            return;
        }

        Step3Fragment step3Fragment = (Step3Fragment) fragment;
        step3Fragment.showProgressBar("sending otp .....");
        if(requestQueue==null)
        {
            requestQueue= VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();


        }
        JSONObject jsonbody= new JSONObject();
        try {
            jsonbody.put("phoneNumber",phoneNumber);

        }catch (JSONException e){
            Log.d("error",e.toString());
        }
        String requestBody =jsonbody.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URL+"auth/sendOtp",null,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {

                Log.d("response",response.toString());
                step3Fragment.closeProgressBar();
            }

        },this

        ){


            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody()  {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }


        };
        jsonObjectRequest.setRetryPolicy(this);

        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void verifyOtp(String phoneNumber,String otpCode) {
        Log.d("api","inside verify otp");
        int count = getSupportFragmentManager().getFragments().size();
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment fragment=fragments.get(fragments.size()-1);
        if(!(fragment instanceof Step3Fragment))
        {
            return ;
        }

        Step3Fragment step3Fragment = (Step3Fragment) fragment;
        step3Fragment.showProgressBar("verifying otp ....");
        if(requestQueue==null)
        {
            requestQueue= VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();


        }
        JSONObject jsonbody= new JSONObject();
        try {
            jsonbody.put("phoneNumber","9094495400");
            jsonbody.put("otpCode",otpCode);

        }catch (JSONException e){
            Log.d("error",e.toString());
        }
        String requestBody =jsonbody.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URL+"auth/verifyOtp",null,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                step3Fragment.closeProgressBar();

                Log.d("response",response.toString());
                String isAuthorised = null;
                try {
                    isAuthorised = response.getString("authorised");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(!isAuthorised.equals("approved"))
                {
                    onError("otp code entered is wrong");

                }

                nextButtonCLicked("step4");
            }

        },this


        ){


            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody()  {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }


        };

        jsonObjectRequest.setRetryPolicy(this);

        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment fragment=fragments.get(fragments.size()-1);


        Log.d("signup error",error.toString());
        if(error instanceof ClientError)
        {

            byte[] response = error.networkResponse.data;
            JSONObject obj =null;
            try {
                String res = new String(response,"utf-8");
                obj = new JSONObject(res);

            } catch (Exception e) {
                e.printStackTrace();
            }

            String message = null;
            try {
                message = obj.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            onError(message);





        }else if(error instanceof NetworkError)
        {
            onError("network error please try again");
        } else ;if(error instanceof ServerError)
        {
            onError("server error please try again");
        }


        if(fragment instanceof Step3Fragment)
        {
            ((Step3Fragment) fragment).closeProgressBar();
        }
        if(fragment instanceof Step4Fragment)
        {
            ((Step4Fragment) fragment).closeProgressBar();

        }


    }

    @Override
    public int getCurrentTimeout() {
        return 5000;
    }

    @Override
    public int getCurrentRetryCount() {
        return 2;
    }

    @Override
    public void retry(VolleyError error) throws VolleyError {



    }

    public void createNewSession()
    {
        SessionManager sessionManager = new SessionManager(this);
        sessionManager.setNAME(userName);
        sessionManager.setPHONE_NO(phoneNumber);
        sessionManager.setUSER_ID(userID);
        sessionManager.setLOCATION(userLocation);
        setToken();
    }

    public void setToken()
    {
       SessionManager sessionManager=new SessionManager(this);
        String userId=sessionManager.getUSER_ID();
        String token =sessionManager.getDEVICE_ID();
        if(userId==null)
        {
            return;
        }



        if(requestQueue==null)
        {
            requestQueue= VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();



        }
        JSONObject jsonbody= new JSONObject();
        try {
            jsonbody.put("userId",userId);
            jsonbody.put("deviceId",token);


        }catch (JSONException e){
            Log.d("error",e.toString());
        }
        String requestBody =jsonbody.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL+"user/setToken", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("response", response.toString());
                String status = null;
                try {
                    status = response.getString("status");
                    Log.d("setToken","Token set successfully");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (!status.equals("success")) {
                    Log.d("setToken", "could not set token");
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("setToken", "could not set token");

            }
        }
        ){


            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody()  {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }


        };
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 5000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 2;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}