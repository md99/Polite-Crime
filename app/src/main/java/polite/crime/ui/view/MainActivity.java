package polite.crime.ui.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import polite.crime.R;
import polite.crime.model.BasicResponse;
import polite.crime.model.CheckUser;
import polite.crime.model.User;
import polite.crime.service.PowerButtonService;
import polite.crime.utils.DefineKey;
import polite.crime.utils.HttpHandler;
import polite.crime.model.Login;
import polite.crime.model.Result;
import polite.crime.model.contacts;
import polite.crime.ui.adapter.ContactAdapter;
import polite.crime.ui.basic.BasicActivity;
import retrofit2.Response;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity
public class MainActivity extends BasicActivity {
    @ViewById
    RecyclerView recyclerView;
    @ViewById
    TextView emptyTv;
    Login user;
    @Bean
    ContactAdapter contactAdapter;
    List<contacts> contactsList;
    Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @AfterViews
    void init() {
        setTitle("Дуудлага мэдээлэл");
        initToolbar(1, this);
        user = storage.getObject(DefineKey.USER, Login.class);
        result = user.getResult();
        connection();
//        turnGPSOn();
//        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.HORIZONTAL));
//        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(contactAdapter);
//        recyclerView.setAdapter(animationAdapter);
//        ItemClickSupport support = ItemClickSupport.addTo(recyclerView);
//        support.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                CallDetailView callDetailView = contactAdapter.getItems().get(position);
//                Intent intent = new Intent(getApplication(), PerviousCallInfoActivity_.class);
//                intent.putExtra("callDetailView", callDetailView);
//                startActivity(intent);
//            }
//        });

//        contactsList = result.getContacts();
//        if (contactsList.size() != 0) {
//            contactAdapter.getItems().addAll(contactsList);
//            recyclerView.addItemDecoration(new polite.crime.utils.DividerItemDecoration(getApplicationContext(), polite.crime.utils.DividerItemDecoration.VERTICAL_LIST));
//            recyclerView.setAdapter(contactAdapter);
//        } else {
//            emptyTv.setVisibility(View.VISIBLE);
//            recyclerView.setVisibility(View.GONE);
//        }

    }

    void connection() {
        User users = new User();
        users.set_id(result.get_id());
        connection.getContacts(users)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BasicResponse<contacts>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                        showInfo(e.toString());
                    }

                    @Override
                    public void onNext(BasicResponse<contacts> response) {
                        hideLoading();
                        if (response != null) {
                            contactAdapter.getItems().addAll(response.getItems());
                            recyclerView.setAdapter(contactAdapter);
                        } else {
                            emptyTv.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                });

    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        List<contacts> contactsList = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(DefineKey.GET_USER);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        c.get("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }
    }
}
