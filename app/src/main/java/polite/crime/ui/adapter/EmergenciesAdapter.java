package polite.crime.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import polite.crime.model.emergencies;
import polite.crime.widget.ItemEmergencies;
import polite.crime.widget.ItemEmergencies_;

/**
 * Created by admin on 7/5/2017.
 */
@EBean
public class EmergenciesAdapter extends RecyclerViewAdapterBase<emergencies, ItemEmergencies> {
    @RootContext
    Context context;

    @Override
    protected ItemEmergencies onCreateItemView(ViewGroup parent, int viewType) {
        return
                ItemEmergencies_.build(context);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ItemEmergencies> holder, int position) {
        holder.getView().bind(getItems().get(position));
    }
}
