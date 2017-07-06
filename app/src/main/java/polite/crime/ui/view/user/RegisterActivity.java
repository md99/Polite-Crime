package polite.crime.ui.view.user;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.List;

import polite.crime.R;
import polite.crime.utils.DefineKey;
import polite.crime.model.Save;
import polite.crime.model.User;
import polite.crime.ui.basic.BasicActivity;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EActivity
public class RegisterActivity extends BasicActivity {
    @ViewById
    EditText phoneEd;

    @ViewById
    EditText passwordEd;
    @ViewById
    EditText registerEd;
    @ViewById
    EditText passwordEd2;
    List<NameValuePair> nameValuePairs;
    HttpClient httpclient = new DefaultHttpClient();
    HttpPost httppost = new HttpPost(DefineKey.REGISTER);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @AfterViews
    void init() {
        setTitle("Бүргэл");
    }

    @Click
    void registerBtn() {
        if (TextUtils.isEmpty(phoneEd.getText().toString().trim())) {
            phoneEd.setError(getString(R.string.error_dugaar));
            return;
        }
        if (TextUtils.isEmpty(registerEd.getText().toString().trim())) {
            registerEd.setError(getString(R.string.error_password));
            return;
        }
        if (TextUtils.isEmpty(passwordEd2.getText().toString().trim())) {
            passwordEd2.setError(getString(R.string.error_password));
            return;
        }
        if (passwordEd.getText().toString().equals(passwordEd2.getText().toString())) {

            // Add your data
//            nameValuePairs = new ArrayList<NameValuePair>(1);
//            nameValuePairs.add(new BasicNameValuePair("mobile", phoneEd.getText().toString().trim(),"register", registerEd.getText().toString().trim()),));
//            nameValuePairs.add(new BasicNameValuePair("register", registerEd.getText().toString().trim()));
//            nameValuePairs.add(new BasicNameValuePair("password", passwordEd.getText().toString().trim()));
//            new RetrieveFeedTask().execute("");
            User user = new User();
            user.setMobile(phoneEd.getText().toString().trim());
            user.setRegister(registerEd.getText().toString().trim());
            user.setPassword(passwordEd.getText().toString().trim());
            connection.register(user)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Save>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Save response) {
                            if (response.isSave() == true) {
                                showInfo("Амжилттай бүртгэгдлээ.");
                                startActivity(new Intent(getApplicationContext(), LoginActivity_.class));
                            } else {
                                showError(response.getResult());
                            }
                        }
                    });


//
        } else
            showError("Нууц үгээ ижилхэн оруулна уу.");

    }

    class RetrieveFeedTask extends AsyncTask<String, Void, Void> {

        private Exception exception;

        protected Void doInBackground(String... urls) {
            try {
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                if (response != null) {
                    showError("Амжилттай бүртгэгдлээ.");
                    startActivity(new Intent(getApplicationContext(), LoginActivity_.class));
                }
                return null;
            } catch (Exception e) {
                this.exception = e;

                return null;
            }

        }

        protected void onPostExecute(String feed) {

            // TODO: check this.exception
            // TODO: do something with the feed
        }
    }
}