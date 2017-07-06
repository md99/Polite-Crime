package polite.crime.ui.view.user;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import polite.crime.R;
import polite.crime.model.emergencies;
import polite.crime.ui.basic.BasicActivity;

@EActivity
public class HistoryInfoActivity extends BasicActivity implements OnMapReadyCallback {
    @Extra
    emergencies emergencies;
    @ViewById
    WebView webView;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_info);


    }

    @AfterViews
    void init() {
        setTitle("Дуудлагын түүх");
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("w3w://show?threewords="+emergencies.+"."home+"."+raft);
        webView.loadUrl("https://map.what3words.com/" + emergencies.getW3w());
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        this.mMap = googleMap;
//        LatLng location = new LatLng(emergencies.getLat(), emergencies.getLon());
//        mMap.addMarker(new MarkerOptions().position(location).title(emergencies.getDate()));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_finish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_finish:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
