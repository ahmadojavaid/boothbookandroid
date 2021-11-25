package com.jobesk.boothbook.activities.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jobesk.boothbook.activities.MainActivity;
import com.jobesk.boothbook.R;
import com.jobesk.boothbook.utils.ApiClient;
import com.jobesk.boothbook.utils.ApiInterface;
import com.jobesk.boothbook.utils.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView login_tv;
    private EditText et_url, et_name, et_pass;
    private String urlvalue;
    private int urlsize;
    private String nameValue, passValue;
    private String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_tv = findViewById(R.id.login_tv);
        login_tv.setOnClickListener(this);

        et_url = (EditText) findViewById(R.id.et_url);
        et_name = (EditText) findViewById(R.id.et_name);
        et_pass = (EditText) findViewById(R.id.et_pass);

        String url = GlobalClass.getPref("booth_url", getApplicationContext());
        String name = GlobalClass.getPref("username", getApplicationContext());

        Log.d("taggg", "onCreate: "+url+" "+name);
        if(!url.equalsIgnoreCase("")){
            Log.d("taggg", "onCreate: here");
            et_url.setText(url);
            et_name.setText(name);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.login_tv:


                urlvalue = et_url.getText().toString().trim();
                urlsize = urlvalue.length();

                nameValue = et_name.getText().toString().trim();
                passValue = et_pass.getText().toString().trim();

//
//                String mLetter = String.valueOf(urlvalue.charAt(urlsize - 1));
//                String oLetter = String.valueOf(urlvalue.charAt(urlsize - 2));
//                String cLetter = String.valueOf(urlvalue.charAt(urlsize - 3));

                if (urlvalue.equalsIgnoreCase("")) {

                    Toast.makeText(this, getApplicationContext().getResources().getString(R.string.enter_url), Toast.LENGTH_SHORT).show();

                    return;
                }

//                if (urlvalue.contains("www") || urlvalue.contains("www.")) {
//
//                    Toast.makeText(this, getApplicationContext().getResources().getString(R.string.enter_valid_url), Toast.LENGTH_SHORT).show();
//                    return;
//                }

               /* if (urlvalue.equalsIgnoreCase("https://")) {

                    Toast.makeText(this, getApplicationContext().getResources().getString(R.string.enter_valid_url), Toast.LENGTH_SHORT).show();
                    return;
                }*/
//
//                if (!urlvalue.contains(".com")) {
//
//                    Toast.makeText(this, getApplicationContext().getResources().getString(R.string.enter_valid_url), Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if(!urlvalue.contains("http://")&&!urlvalue.contains("https://")){
                    urlvalue="https://"+urlvalue;
                }

                Boolean checkOne = URLUtil.isValidUrl(urlvalue);
                Boolean checkTwo = Patterns.WEB_URL.matcher(urlvalue).matches();


                if (checkOne == false) {
                    Toast.makeText(this, getApplicationContext().getResources().getString(R.string.enter_valid_url), Toast.LENGTH_SHORT).show();
                    return;

                }

                if (checkTwo == false) {
                    Toast.makeText(this, getApplicationContext().getResources().getString(R.string.enter_valid_url), Toast.LENGTH_SHORT).show();
                    return;

                }


//                if (!urlvalue.equalsIgnoreCase("https://vendor.myboothbooking.com")) {
//
//
//                    Toast.makeText(this, getApplicationContext().getResources().getString(R.string.enter_valid_url), Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (nameValue.equalsIgnoreCase("")) {

                    Toast.makeText(this, getApplicationContext().getResources().getString(R.string.enter_name), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passValue.equalsIgnoreCase("")) {

                    Toast.makeText(this, getApplicationContext().getResources().getString(R.string.enter_pass), Toast.LENGTH_SHORT).show();
                    return;
                }


                if (passValue.length() < 6) {

                    Toast.makeText(this, getApplicationContext().getResources().getString(R.string.enter_pass), Toast.LENGTH_SHORT).show();
                    return;
                }

                loginUser();

                break;
        }
    }

    private void loginUser() {
        if (!GlobalClass.isOnline(getApplicationContext())) {
            Toast.makeText(this, getApplicationContext().getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            return;
        }

        GlobalClass.showLoading(LoginActivity.this);

        ApiInterface apiInterface = ApiClient.getApiClientWithoutUrl(urlvalue + "/api/v1/").create(ApiInterface.class);
        Call<LoginModel> eventCall = apiInterface.LoginUser(nameValue, passValue);

        eventCall.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                String url = response.raw().request().url().toString();
                Log.e(TAG, "url: " + url);
                Log.e(TAG, "responseLogin: " + new Gson().toJson(response.body()));

                if (response.isSuccessful()) {

                    LoginModel loginModel = response.body();
                    String msg = loginModel.getMsg();

                    Toast.makeText(LoginActivity.this, "" + msg, Toast.LENGTH_SHORT).show();

                    String success = loginModel.getSuccess();

                    if (success.equalsIgnoreCase("false")) {
                        GlobalClass.dismissLoading();
                        return;

                    }


                    String uid = loginModel.getUid();
                    String active = loginModel.getActive();
                    String role = loginModel.getRole();
                    String client_key = loginModel.getClient_key();
                    String client_secret = loginModel.getClient_secret();


                    GlobalClass.putPref("userID", uid, getApplicationContext());
                    GlobalClass.putPref("userActive", active, getApplicationContext());
                    GlobalClass.putPref("userRole", role, getApplicationContext());
                    GlobalClass.putPref("user_client_key", client_key, getApplicationContext());
                    GlobalClass.putPref("user_client_secret", client_secret, getApplicationContext());
                    GlobalClass.putPref("user_client_secret", client_secret, getApplicationContext());
                    GlobalClass.putPref("user_base_url", urlvalue + "/api/v1/", getApplicationContext());
                    GlobalClass.putPref("booth_url", urlvalue,getApplicationContext());
                    GlobalClass.putPref("username", nameValue,getApplicationContext());

                    GlobalClass.dismissLoading();


                    Intent loginintent = new Intent(LoginActivity.this, MainActivity.class);
                    loginintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(loginintent);


                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

                GlobalClass.dismissLoading();
                Log.d(TAG, t + "");

                if (String.valueOf(t).contains("No address associated with hostname")) {
                    Toast.makeText(LoginActivity.this, getApplicationContext().getResources().getString(R.string.enter_valid_url), Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


}
