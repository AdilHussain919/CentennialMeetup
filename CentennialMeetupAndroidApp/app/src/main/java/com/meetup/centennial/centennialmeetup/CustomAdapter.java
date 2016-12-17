package com.meetup.centennial.centennialmeetup;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Satt-3 on 11/23/2016.
 */
public class CustomAdapter extends CursorAdapter {
    TextView txtId, txtName, txtEmail;
    private LayoutInflater mInflater;
    ViewHolder holder;
    String name=null;
    public CustomAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.txtId = (TextView) view.findViewById(R.id.txtId);
        holder.txtName = (TextView) view.findViewById(R.id.txtName);
        holder.txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        holder  =   (ViewHolder)    view.getTag();
        holder.txtId.setText(cursor.getString(cursor.getColumnIndex("centennialid")));
        holder.txtName.setText(cursor.getString(cursor.getColumnIndex("firstname")));
        holder.txtEmail.setText(cursor.getString(cursor.getColumnIndex("campus")));

    }
    public String getid()
    {
        name=holder.txtName.getText().toString();
        return name;
    }
    static class ViewHolder {
        TextView txtId;
        TextView txtName;
        TextView txtEmail;
    }
}