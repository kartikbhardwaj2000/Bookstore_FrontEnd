package com.example.bookstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class SessionManager   {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE=0;
    private static final String PREF_NAME= "Bookstore";
    private String NAME ="name";
    private String PHONE_NO="phone_no";
    private String USER_ID="user_id";
    private String PROFILE_PIC_LINK="profile_pic_link";
    private String LOCATION ="location";
    private String DEVICE_ID="device_id";


    public SessionManager(Context context)
    {
        this.context=context;
        sharedPreferences=this.context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=sharedPreferences.edit();
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.d("newToken",newToken);
                setDEVICE_ID(newToken);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("token error",e.getMessage());
            }
        });

    }


    public String getNAME() {
        return this.sharedPreferences.getString(NAME,null);
    }

    public void setNAME(String name) {
        editor.putString(NAME,name);
        editor.commit();

    }

    public String getPHONE_NO() {
        return this.sharedPreferences.getString(PHONE_NO,null);
    }

    public void setPHONE_NO(String phone_no) {
        editor.putString(PHONE_NO,phone_no);
        editor.commit();
    }

    public String getUSER_ID() {
        return this.sharedPreferences.getString(USER_ID,null);
    }

    public void setUSER_ID(String user_id) {
        editor.putString(USER_ID,user_id);
        editor.commit();
    }

    public String getPROFILE_PIC_LINK() {
        return this.sharedPreferences.getString(PROFILE_PIC_LINK,null);
    }

    public void setPROFILE_PIC_LINK(String profile_pic_link) {
        editor.putString(PROFILE_PIC_LINK,profile_pic_link);
        editor.commit();
    }
    public String getLOCATION()
    {
        return this.sharedPreferences.getString(LOCATION,null);
    }
    public void setLOCATION(String location)
    {
        editor.putString(LOCATION,location);
        editor.commit();
    }

    public String getDEVICE_ID()
    {
        return this.sharedPreferences.getString(DEVICE_ID,null);

    }
    public void setDEVICE_ID(String token)
    {
        editor.putString(DEVICE_ID,token);
        editor.commit();
    }
}
