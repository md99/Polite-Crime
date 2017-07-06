package polite.crime.ui.view.user;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import polite.crime.R;
import polite.crime.model.Result;
import polite.crime.service.AlertReceiver;
import polite.crime.service.PowerButtonService;
import polite.crime.utils.DefineKey;
import polite.crime.utils.HttpHandler;
import polite.crime.model.CheckUser;
import polite.crime.model.Login;
import polite.crime.ui.basic.BasicActivity;
import polite.crime.ui.view.MainActivity_;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity
public class LoginActivity extends BasicActivity {
    @ViewById
    EditText userNameEd;
    @ViewById
    EditText passwordEd;
    Login login = new Login();
    CheckUser user = new CheckUser();
    private AlertReceiver myReceiver;
    public final static int REQUEST_CODE = 10101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
    }

    @AfterViews
    void init() {
        hideLoading();
        Result result = login.getResult();

        if (result != null) {
            startActivity(new Intent(this, MainActivity_.class));
            finish();
        }
    }

    @Click
    void loginBtn() {
        if (TextUtils.isEmpty(userNameEd.getText().toString().trim())) {
            userNameEd.setError(getString(R.string.error_dugaar));
            return;
        }
        if (TextUtils.isEmpty(passwordEd.getText().toString().trim())) {
            passwordEd.setError(getString(R.string.error_password));
            return;
        }
        connection();
    }

    private void connection() {
        showLoading();

        user.setPassword(passwordEd.getText().toString());
        user.setMobile(userNameEd.getText().toString());
        connection.checkUser(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Login>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                        showInfo(e.toString());
                    }

                    @Override
                    public void onNext(Login response) {
                        hideLoading();
                        if (response.getLogged() == "true" || response.getLogged().equals("true")) {
                            storage.putObject(DefineKey.USER, response);
                            if (checkDrawOverlayPermission()) {
                                startService(new Intent(getApplicationContext(), PowerButtonService.class));
                            }
                            startActivity(new Intent(getApplicationContext(), MainActivity_.class));
                        } else {

                            showError(response.getLogged());
                        }
                    }
                });

    }

    public boolean checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (!Settings.canDrawOverlays(this)) {
            /** if not construct intent to request permission */
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            /** request permission via start activity for result */
            startActivityForResult(intent, REQUEST_CODE);
            return false;
        } else {
            return true;
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                startService(new Intent(this, PowerButtonService.class));
            }
        }
    }

    @Click
    void registerBtn() {
        startActivity(new Intent(this, RegisterActivity_.class));
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

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
                        Log.d("lol", user.getPassword().toString().trim() + " -pass- " + c.get("password\n"));
                        Log.d("lol", user.getMobile().toString().trim() + " -mobi- " + c.get("mobile"));
                        if (user.getPassword().toString().trim().equals(c.get("password")) &&
                                user.getMobile().toString().trim().equals(c.get("mobile"))) {
                            showInfo("true");
                            Log.d("lol", "true");
                        } else {
                            showInfo("false");
                            Log.d("lol", "false");
                        }
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
