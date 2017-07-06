package polite.crime.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import polite.crime.model.contacts;
import polite.crime.widget.ItemContact;
import polite.crime.widget.ItemContact_;

/**
 * Created by admin on 7/5/2017.
 */
@EBean
public class ContactAdapter extends RecyclerViewAdapterBase<contacts, ItemContact> {
    @RootContext
    Context context;

    public ContactAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected ItemContact onCreateItemView(ViewGroup parent, int viewType) {
        return ItemContact_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ItemContact> holder, int position) {
        holder.getView().bind(getItems().get(position));
    }
}
