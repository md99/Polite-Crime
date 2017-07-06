package polite.crime.ui.view.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import polite.crime.R;
import polite.crime.utils.DefineKey;
import polite.crime.utils.DividerItemDecoration;
import polite.crime.utils.ItemClickSupport;
import polite.crime.model.Login;
import polite.crime.model.Result;
import polite.crime.model.emergencies;
import polite.crime.ui.adapter.EmergenciesAdapter;
import polite.crime.ui.basic.BasicActivity;

@EActivity
public class HistoryActivity extends BasicActivity {
    @ViewById
    RecyclerView recyclerView;
    @ViewById
    TextView emptyTv;
    Login user;
    List<emergencies> emergenciesList;
    @Bean
    EmergenciesAdapter emergenciesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @AfterViews
    void init() {
        initToolbar(3, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL_LIST));
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(emergenciesAdapter);
        recyclerView.setAdapter(animationAdapter);
        ItemClickSupport support = ItemClickSupport.addTo(recyclerView);
        support.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                emergencies call = emergenciesAdapter.getItems().get(position);
                Uri address = Uri.parse("w3w://show?threewords="+call.getW3w());
                Intent navigate = new Intent(Intent.ACTION_VIEW, address);
                startActivity(navigate);
//                Intent intent = new Intent(getApplicationContext(), HistoryInfoActivity_.class);
//                intent.putExtra("emergencies", call);
//                startActivity(intent);
            }
        });
        user = storage.getObject(DefineKey.USER, Login.class);
        Result result = user.getResult();
        emergenciesList = result.getEmergencies();
        if (emergenciesList == null)
            Log.d("lol", " emergenciesList null");
        else
            Log.d("lol", " emergenciesList not null");
        if (emergenciesList.size() != 0) {
            emergenciesAdapter.getItems().addAll(emergenciesList);
            recyclerView.setAdapter(emergenciesAdapter);
        } else {
            emptyTv.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}
