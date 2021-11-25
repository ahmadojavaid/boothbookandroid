package com.jobesk.boothbook.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.jobesk.boothbook.database.DatabaseHelper;
import com.jobesk.boothbook.fragments.AddFrag;
import com.jobesk.boothbook.fragments.calanderfrag.CalanderFrag;
import com.jobesk.boothbook.fragments.LeadShowFrag;
import com.jobesk.boothbook.fragments.SettingFrag;
import com.jobesk.boothbook.models.EventTypeModel;
import com.jobesk.boothbook.R;
import com.jobesk.boothbook.utils.ApiClient;
import com.jobesk.boothbook.utils.ApiInterface;
import com.jobesk.boothbook.utils.GlobalClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    final Fragment fragment1 = new CalanderFrag();
    final Fragment fragment2 = new LeadShowFrag();
    final Fragment fragment3 = new AddFrag();
    final Fragment fragment4 = new SettingFrag();
    private Fragment active = fragment1;
    private FragmentManager fm;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(MainActivity.this);


        fm = getSupportFragmentManager();


        fm.beginTransaction().add(R.id.container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.container, fragment1, "1").commit();


        bottomNavigation = findViewById(R.id.bottom_navigation);


        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int value = item.getItemId();

                GlobalClass.hideKeyboard(MainActivity.this);
                switch (item.getItemId()) {

                    case R.id.action_calander:
                        Log.d("itemID", "Calander");

//                        if (active != fragment1)
//                            fm.beginTransaction().show(fragment1).commit();
//                        else
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;

                        return true;
                    case R.id.action_contact:
                        Log.d("itemID", item.getItemId() + "");

                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;

                        return true;
                    case R.id.action_add:
                        Log.d("itemID", item.getItemId() + "");

//                        fm.beginTransaction().hide(active).show(fragment3).commit();
//                        active = fragment3;

                        Intent intentBusiness = new Intent(MainActivity.this, AddBusinessActivity.class);
                        startActivity(intentBusiness);

                        return true;
                    case R.id.action_setting:


                        fm.beginTransaction().hide(active).show(fragment4).commit();
                        active = fragment4;


                        return true;
                }


                return true;
            }
        });


//        registerReceiver(broadcastReceiver, new IntentFilter("open_frag_detail"));


        getAllTypes();

    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            String state = intent.getExtras().getString("extra");

            Log.d("state", "onReceive: " + state);
            switch (state) {

//                case "open_frag_detail":
//
////                    String id = intent.getExtras().getString("id");
////                    EventDetailFrag eventfrag = new EventDetailFrag();
//
//
//
//
////                    Bundle args = new Bundle();
////                    args.putString("id", id);
////                    FragmentManager FM = getSupportFragmentManager();
////                    FragmentTransaction FT = FM.beginTransaction();
////                    FT.addToBackStack(null);
////                    FT.replace(R.id.container, eventfrag);
////                    eventfrag.setArguments(args);
////                    FT.commit();
//
//
//                    break;

                case "open_frag_booking":
//                    SearchBookingFrag searFrag = new SearchBookingFrag();
//
//                    openFragBroadcasted(searFrag);

                    break;

                case "open_frag_signature":
//                    SignatureBookFrag signatureFrag = new SignatureBookFrag();
//
//                    openFragBroadcasted(signatureFrag);
                    break;
                case "open_frag_lead_detail":

//                    LeadDetailFrag leadFrag = new LeadDetailFrag();
//                    openFragBroadcasted(leadFrag);


                    break;

                case "open_frag_signature_config":

//                    SignatureConfigFrag signatureConfigFrag = new SignatureConfigFrag();
//                    openFragBroadcasted(signatureConfigFrag);


                    break;

                case "open_frag_search_lead":

//                    LeadSearchedFrag leadSearch = new LeadSearchedFrag();
//                    openFragBroadcasted(leadSearch);


                    break;


            }

//

            Log.d("Broadcasted", "broadcastReceiver");


        }
    };

//    private void openFragBroadcasted(Fragment f1) {
//
//        FragmentManager FM = getSupportFragmentManager();
//        FragmentTransaction FT = FM.beginTransaction();
//        FT.addToBackStack("frag");
//        FT.add(R.id.container, f1);
//        FT.commit();
//
//
//    }
//

    @Override
    protected void onDestroy() {

//        unregisterReceiver(broadcastReceiver);

        super.onDestroy();


    }

//    private void Openfrags(Fragment f1) {
//
//
////        FeedFragment f1 = new FeedFragment();
//        FragmentManager FM = getSupportFragmentManager();
//        FragmentTransaction FT = FM.beginTransaction();
//        FT.addToBackStack(null);
//        FT.add(R.id.container, f1);
//        FT.commit();
//
//
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Override this method in the activity that hosts the Fragment and call super
        // in order to receive the result inside onActivityResult from the fragment.
        super.onActivityResult(requestCode, resultCode, data);


        Fragment frg = getSupportFragmentManager().findFragmentById(R.id.container);
        if (frg != null) {
            frg.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void getAllTypes() {
        String key = GlobalClass.getPref("user_client_key", getApplicationContext());
        String secret = GlobalClass.getPref("user_client_secret", getApplicationContext());
        String base_url = GlobalClass.getPref("user_base_url", getApplicationContext());

        Log.d("token", "key=" + key + "     secret=" + secret);
        ApiInterface apiInterface = ApiClient.getApiClientWithoutUrl(base_url).create(ApiInterface.class);
        Call<List<EventTypeModel>> eventCall = apiInterface.GetTypesEvents(key, secret);
        eventCall.enqueue(new Callback<List<EventTypeModel>>() {
            @Override
            public void onResponse(Call<List<EventTypeModel>> call, Response<List<EventTypeModel>> response) {
                String urlHit = call.request().url().toString();
                Log.d("urlHit", urlHit);
                if (response.isSuccessful()) {
                    List<EventTypeModel> listOfTypes = response.body();
                    Log.d("ResponseTypes:", new Gson().toJson(response.body()) + "");
                    if (listOfTypes.size() > 0) {
                        int typesCount = db.getTypeCount();
                        for (int i = 0; i < listOfTypes.size(); i++) {
                            String label = listOfTypes.get(i).getLabel();
                            String id = listOfTypes.get(i).getCateGoryID();
                            if (typesCount > 0) {
                                EventTypeModel model = new EventTypeModel();
                                model.setCateGoryID(id);
                                model.setLabel(label);
                                db.updateType(model);
                            } else {
                                EventTypeModel model = new EventTypeModel();
                                model.setCateGoryID(id);
                                model.setLabel(label);
                                db.insertType(model);
                            }
                        }
                    }
                } else {
                    String errorBody = response.errorBody().toString();
                    Toast.makeText(MainActivity.this, "" + errorBody, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<EventTypeModel>> call, Throwable t) {
                Log.d("fails", "onFailure: " + t);
            }
        });
    }
}
