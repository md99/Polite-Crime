package polite.crime.widget;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import polite.crime.R;
import polite.crime.model.emergencies;

/**
 * Created by admin on 7/5/2017.
 */
@EViewGroup(R.layout.item_emergencies)
public class ItemEmergencies extends CardView {
    @ViewById
    TextView dateTv;
    @ViewById
    TextView lat;
    @ViewById
    TextView lon;
    @ViewById
    TextView wordTv;

    public ItemEmergencies(Context context) {
        super(context);
    }

    public void bind(emergencies emergencies) {
        dateTv.setText(emergencies.getDate());
        lat.setText(String.valueOf(emergencies.getLat()));
        lon.setText(String.valueOf(emergencies.getLon()));
        wordTv.setText(emergencies.getW3w());
    }
}
