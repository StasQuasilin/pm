package ua.svasilina.targeton.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONObject;

import ua.svasilina.targeton.dialogs.ListBuilder;
import ua.svasilina.targeton.dialogs.transactions.ItemBuilder;

public class SimpleListAdapter<T> extends ArrayAdapter<T> {

    private final LayoutInflater inflater;
    private final int resource;
    private final ListBuilder<T> listBuilder;
    private final ItemBuilder<T> itemBuilder;

    public SimpleListAdapter(@NonNull Context context, int resource, LayoutInflater inflater, ListBuilder<T> listBuilder, ItemBuilder<T> itemBuilder) {
        super(context, resource);
        this.inflater = inflater;
        this.resource = resource;
        this.listBuilder = listBuilder;
        this.itemBuilder = itemBuilder;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final View view = convertView == null ? inflater.inflate(resource, parent, false) : convertView;
        final T item = getItem(position);
        listBuilder.build(item, view);
        return view;
    }

    public void addItem(JSONObject jsonObject){
        add(itemBuilder.create(jsonObject));
    }
}
