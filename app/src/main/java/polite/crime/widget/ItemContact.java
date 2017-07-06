package polite.crime.widget;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import polite.crime.R;
import polite.crime.model.contacts;

/**
 * Created by admin on 7/5/2017.
 */
@EViewGroup(R.layout.item_contact_list)
public class ItemContact extends CardView {
    @ViewById
    TextView phoneTv;
    @ViewById
    TextView nameTv;
    @ViewById
    TextView whoTv;
    @ViewById
    TextView mailTv;

    //
    public ItemContact(Context context) {
        super(context);
    }

    public void bind(contacts contact) {
        phoneTv.setText(contact.getMobile());
        nameTv.setText(contact.getName());
        whoTv.setText(contact.getRelation());
        mailTv.setText(contact.getEmail());
    }
}
