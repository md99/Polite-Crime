package polite.crime.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import polite.crime.model.User;

/**
 * Created by admin on 7/5/2017.
 */
@EBean(scope = EBean.Scope.Singleton)
public class DataStorage {
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;
    private static Gson GSON = new Gson();
    private User user;

    public DataStorage(Context context) {
        preferences = context.getSharedPreferences("Polite", Context.MODE_PRIVATE);
        editor = preferences.edit();
        this.user = getObject(DefineKey.USER, User.class);
    }

    public User getUser() {
        if (user == null)
            this.user = getObject(DefineKey.USER, User.class);
        return user;
    }

    public void putObject(String key, Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Object is null");
        }
        if (key == null || key.equals("")) {
            throw new IllegalArgumentException("Key is empty or null");
        }
        editor.putString(key, GSON.toJson(object));
        editor.commit();
    }

    public <T> T getObject(String key, Class<T> a) {
        String gson = preferences.getString(key, null);
        if (gson == null) {
            return null;
        } else {
            try {
                return GSON.fromJson(gson, a);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object stored with key " + key + " is instance of other class");
            }
        }
    }

    public <T> List<T> stringToArray(String key, Class<T[]> clazz) {
        String gson = preferences.getString(key, null);
        if (gson == null) {
            return null;
        } else {
            try {
                T[] arr = new Gson().fromJson(gson, clazz);
                List<T> array = Arrays.asList(arr);
                return new ArrayList<>(array);
            } catch (Exception e) {
                throw new IllegalArgumentException("Object stored with key " + key + " is instance of other class");
            }
        }

    }

    public void clear() {
        editor.clear();
        editor.commit();
    }
}