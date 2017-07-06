package polite.crime.ui.basic;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperToast;
import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialize.util.UIUtils;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import polite.crime.R;
import polite.crime.model.emergencies;
import polite.crime.service.AlertReceiver;
import polite.crime.utils.CrossfadeWrapper;
import polite.crime.utils.DataStorage;
import polite.crime.utils.DefineKey;
import polite.crime.connection.EPatrolConnection;
import polite.crime.connection.ServiceClient;
import polite.crime.model.Login;
import polite.crime.model.Result;
import polite.crime.model.Save;
import polite.crime.model.User;
import polite.crime.model.contacts;
import polite.crime.ui.view.MainActivity_;
import polite.crime.ui.view.user.HistoryActivity_;
import polite.crime.ui.view.user.LoginActivity_;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 7/5/2017.
 */

@SuppressWarnings("unused")
@EActivity
public class BasicActivity extends AppCompatActivity {

    @ViewById
    public FrameLayout loaderView;
    @ViewById
    public LinearLayout errorView;
    @ViewById
    public LinearLayout emptyView;

    @Bean
    public DataStorage storage;
    public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 100; // 10 meters
    public static final long MIN_TIME_BW_UPDATES = 10; // 1 minute
    public static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public Login user;
    Activity activity;
    IProfile profile = null;
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private MiniDrawer miniResult = null;
    public Result results;
    private Crossfader crossFader;
    public EPatrolConnection connection = ServiceClient.getInstance().getClient(EPatrolConnection.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.user = storage.getObject(DefineKey.USER, Login.class);
        if (user == null)
            Log.d("lol", "user null");
        else {
            Log.d("lol", "user not  null");
            if (user.getResult() == null)
                Log.d("lol", "getResults null");
            else {
                Log.d("lol", "getResults not  null");
                results = user.getResult();
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initToolbar(int selection, final Activity activity) {
        this.activity = activity;
        if (results == null)
            Log.d("lol", "results null");
        else

            Log.d("lol", "results not  null");
//        profile = new ProfileDrawerItem().withName(user.getLastname().substring(0, 1) + "." + user.getFirstname().toString().toUpperCase().trim()).withIcon(R.drawable.user);
        profile = new ProfileDrawerItem().withName(results.getMobile()).withIcon(R.drawable.user);

        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(true)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(new ColorDrawable(Color.parseColor("#FDFDFD")))
                .withHeightPx(UIUtils.getActionBarHeight(this))
                .withAccountHeader(R.layout.material_drawer_compact_persistent_header)
                .withTextColor(Color.BLACK)
                .addProfiles(
                        profile
                )
                .build();
        result = new DrawerBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Харах").withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Нэмэх").withIcon(FontAwesome.Icon.faw_plus).withIdentifier(2),
                        new SecondaryDrawerItem().withName("Дуудлагын түүх").withIcon(FontAwesome.Icon.faw_history).withIdentifier(3),
                        new SectionDrawerItem().withName("Систем"),
                        new SecondaryDrawerItem().withName("Тохиргоо").withIcon(FontAwesome.Icon.faw_cogs).withIdentifier(6),
                        new SecondaryDrawerItem().withName("Гарын авлага").withIcon(FontAwesome.Icon.faw_book).withIdentifier(8),
                        new SecondaryDrawerItem().withName("Гарах").withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(7)
                )
                .withGenerateMiniDrawer(true)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        long idenfier = drawerItem.getIdentifier();
                        if (idenfier == 1) {
                            startActivity(new Intent(activity, MainActivity_.class));
                        }
                        if (idenfier == 2) {
                            insert();
                        }
                        if (idenfier == 3) {
                            startActivity(new Intent(activity, HistoryActivity_.class));
                        }
                        if (idenfier == 6) {
                            sentAlert();
                        }
                        if (idenfier == 7) {
                            userExitBtn();

                        }
                        if (idenfier == 8) {
//                            voiceRecord();
                            Uri address = Uri.parse("w3w://show?threewords=beak.inserting.smoking");
                            Intent navigate = new Intent(Intent.ACTION_VIEW, address);
                            startActivity(navigate);
                        }
                        return false;
                    }
                })
                .withSelectedItem(selection)
                .buildView();
        miniResult = result.getMiniDrawer().withIncludeSecondaryDrawerItems(true);
        int firstWidth = (int) com.mikepenz.crossfader.util.UIUtils.convertDpToPixel(300, this);
        int secondWidth = (int) com.mikepenz.crossfader.util.UIUtils.convertDpToPixel(72, this);
        crossFader = new Crossfader()
                .withContent(findViewById(R.id.crossfade_content))
                .withFirst(result.getSlider(), firstWidth)
                .withSecond(miniResult.build(this), secondWidth)
                .build();

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

        //define and create the arrow ;)
        ImageView toggle = (ImageView) headerResult.getView().findViewById(R.id.material_drawer_account_header_toggle);
        //for RTL you would have to define the other arrow
        toggle.setImageDrawable(new IconicsDrawable(this, " 123 ").sizeDp(16).color(Color.BLACK));
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crossFader.crossFade();
            }
        });

    }

    void userExitBtn() {

        new MaterialDialog.Builder(activity)
                .content("Програмаас гарах")
                .positiveText("Үгүй")
                .negativeText("Тийм")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        storage.clear();

                        Intent intent = new Intent(getApplicationContext(), LoginActivity_.class);
                        ComponentName cn = intent.getComponent();
                        Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                        startActivity(mainIntent);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                })
                .show();
    }

    public void showEmptyView() {
        if (emptyView != null) {
            hideLoading();
            hideErrorView();
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    public void turnGPSOn() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (!gps_enabled) {
            new MaterialDialog.Builder(this)
                    .title("Та GPS-ээ асаана уу")
                    .positiveText("OK")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .show();
        }
    }

    public void showLoading() {
        if (loaderView != null) {
            loaderView.setVisibility(View.VISIBLE);
        }
        hideErrorView();
    }

    public void hideLoading() {
        if (loaderView != null) {
            loaderView.setVisibility(View.GONE);
        }
    }

    public void showErrorView() {
        if (errorView != null) {
            hideLoading();
            errorView.setVisibility(View.VISIBLE);
        }
    }

    public void hideErrorView() {
        if (errorView != null) {
            errorView.setVisibility(View.GONE);
        }
    }

    public void showInfo(String message) {
        SuperToast.create(this, message, Style.DURATION_MEDIUM, Style.green()).show();
    }

    public void showError(String message) {
        SuperToast.create(this, message, Style.DURATION_MEDIUM, Style.red()).show();
    }

    private void regBtn() {
        final AlertDialog dialog = new AlertDialog.Builder(getApplicationContext(), R.style.MyAlertDialogStyle)
                .setNegativeButton("Буцах", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        dialogInterface.cancel();
                    }
                })
                .setPositiveButton("Хадгалах", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        User user = new User();
//                        user.setMobile();
//                        user.setEmail();
//                        user.setName();
//                        user.setRelation();
//                        connection.registerHuman()
                    }
                })
                .create();
        dialog.setContentView(R.layout.add_person);
        dialog.setTitle("Шинээр бүртгэх");
        EditText phoneEd = (EditText) dialog.findViewById(R.id.phoneEd);
        EditText emailEd = (EditText) dialog.findViewById(R.id.emailEd);
        EditText nameEd = (EditText) dialog.findViewById(R.id.nameEd);
        dialog.show();

    }

    private void register() {

//        View alertLayout = inflater.inflate(R.layout.add_person, null);
//        final AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext(), R.style.MyAlertDialogStyle);
////        final MaterialSpinner shiidverleltSpinner = (MaterialSpinner) alertLayout.findViewById(R.id.shaltgaanSpinner);
//        final EditText phoneEd = (EditText) alertLayout.findViewById(R.id.phoneEd);
//        final EditText nameEd = (EditText) alertLayout.findViewById(R.id.nameEd);
//        final EditText emailEd = (EditText) alertLayout.findViewById(R.id.emailEd);
////        shiidverleltSpinner.setAdapter(LateArrayAdapter);
//        alert.setTitle("Шинээр бүртгэх");
//        alert.setCancelable(false);
//        alert.setPositiveButton("Хадгалах", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        alert.setNegativeButton("Буцах", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//                dialogInterface.cancel();
//            }
//        });
//        alert.setView(alertLayout);
//        alert.show();
    }


    public void insert() {
        final Dialog dialog = new Dialog(activity, R.style.MyAlertDialogStyle);
        dialog.setContentView(R.layout.add_person);
        dialog.setTitle("Шинээр бүртгэх");
        final EditText phoneEd = (EditText) dialog.findViewById(R.id.phoneEd);
        final Spinner spinner = (Spinner) dialog.findViewById(R.id.spinner);
        final EditText emailEd = (EditText) dialog.findViewById(R.id.emailEd);
        final EditText nameEd = (EditText) dialog.findViewById(R.id.nameEd);
        Button saveBtn = (Button) dialog.findViewById(R.id.saveBtn);
        Button cancelBtn = (Button) dialog.findViewById(R.id.cancelBtn);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.object_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User users = new User();
                if (results.get_id() == null)
                    Log.d("lol", "id null");
                else
                    Log.d("lol", "id not null");
                users.set_id(results.get_id());

                contacts contacts = new contacts();
                contacts.setRelation(spinner.getSelectedItem().toString());
                contacts.setName(nameEd.getText().toString().trim());
                contacts.setEmail(emailEd.getText().toString().trim());
                contacts.setMobile(phoneEd.getText().toString().trim());
                users.setContact(contacts);
                connection.registerHuman(users)
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
                                    dialog.dismiss();
                                    dialog.cancel();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), MainActivity_.class));
                                } else {
                                    showError(response.getResult());
                                }
                            }
                        });
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void sentAlert() {
        User user1 = new User();
        Result result = new Result();
        result = user.getResult();
        user1.set_id(result.get_id());
        emergencies emergencies = new emergencies();
        if (returnLocation == null) {
//            setCurrentLocation();
            Random r = new Random();
            double lat = 47.9265877 + r.nextInt(100)/1000;
            double lon =106.9116297+ r.nextInt(100)/1000;
            emergencies.setLat(lat);
            emergencies.setLon(lon);
        } else {
            emergencies.setLat(returnLocation.getLatitude());
            emergencies.setLon(returnLocation.getLongitude());
        }
        user1.setEmergency(emergencies);
        connection.emergency(user1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Save>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoading();
                        showInfo(e.toString());
                    }

                    @Override
                    public void onNext(Save response) {
                        hideLoading();
                        if (response.getResult() == "true" || response.getResult().equals("true")) {

                        } else {

                            showError(response.getResult());
                        }
                    }
                });
    }

    public Location returnLocation = null;

    Location setCurrentLocation() {

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (gps_enabled || network_enabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Та байршил авах эрх өгнө үү", Toast.LENGTH_SHORT).show();
                return null;
            }
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            if (location != null) {
                                returnLocation = location;

                            }
                        }

                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {

                        }

                        @Override
                        public void onProviderEnabled(String s) {

                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    });
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                returnLocation = location;
                return returnLocation;
            }

        } else {
            turnGPSOn();
        }
        return returnLocation;
    }

    private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
    private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    private static final String AUDIO_RECORDER_FOLDER = "AudioRecorder";
    private MediaRecorder recorder = null;
    private int output_formats[] = {MediaRecorder.OutputFormat.MPEG_4, MediaRecorder.OutputFormat.THREE_GPP};
    private int currentFormat = 0;
    private String file_exts[] = {AUDIO_RECORDER_FILE_EXT_MP4, AUDIO_RECORDER_FILE_EXT_3GP};

    private void voiceRecord() {

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(output_formats[currentFormat]);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(getFilename());
        recorder.setOnErrorListener(errorListener);
        recorder.setOnInfoListener(infoListener);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
        @Override
        public void onError(MediaRecorder mr, int what, int extra) {
        }
    };

    private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
        @Override
        public void onInfo(MediaRecorder mr, int what, int extra) {
        }
    };

    private String getFilename() {
        String filepath = Environment.getExternalStorageDirectory().getPath();
        File file = new File(filepath, AUDIO_RECORDER_FOLDER);

        if (!file.exists()) {
            file.mkdirs();
        }

        return (file.getAbsolutePath() + "/" + System.currentTimeMillis() + file_exts[currentFormat]);
    }

    private void stopRecording() {
        if (null != recorder) {
            recorder.stop();
            recorder.reset();
            recorder.release();

            recorder = null;
        }
    }
}
