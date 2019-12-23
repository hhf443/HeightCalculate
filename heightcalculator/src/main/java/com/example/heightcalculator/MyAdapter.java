package com.example.heightcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/*Adapter中集成增删改查操作：https://blog.csdn.net/UUUUUltraman/article/details/89176556*/

public class MyAdapter extends ArrayAdapter<HeightInfo> {

    private List<HeightInfo> infolist = null;
    private HcDatabaseHelper databaseHelper;

    private class ViewHolder {
        private TextView tvId, tvName, tvSex, tvfHeight, tvmHeight, tvsHeight;
    }


    public MyAdapter(Context context, int resource, List<HeightInfo> objects) {
        super(context, resource, objects);
        this.infolist = objects;
        databaseHelper = new HcDatabaseHelper(context, "heightinfo.db", null, 2);
        databaseHelper.getWritableDatabase();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        final ViewHolder viewHolder;

        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate( R.layout.layout_info_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvId = view.findViewById(R.id.tv_id_item);
            viewHolder.tvName = view.findViewById(R.id.tv_name_item);
            viewHolder.tvSex = view.findViewById(R.id.tv_sex_item);
            viewHolder.tvfHeight = view.findViewById(R.id.tv_fheight_item);
            viewHolder.tvmHeight = view.findViewById(R.id.tv_mheight_item);
            viewHolder.tvsHeight = view.findViewById(R.id.tv_sheight_item);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        HeightInfo heightInfo = getItem(position);
        Log.v("Adapter-heightinfo", heightInfo.toString());
        viewHolder.tvId.setText(String.valueOf(heightInfo.getId()));
        viewHolder.tvName.setText(heightInfo.getName());
        if(heightInfo.isIsmale()) {
            viewHolder.tvSex.setText("男");
        }else{
            viewHolder.tvSex.setText("女");
        }
        viewHolder.tvfHeight.setText(heightInfo.getFheight());
        viewHolder.tvmHeight.setText(heightInfo.getMheight());
        viewHolder.tvsHeight.setText(heightInfo.getSheight());
        return view;
    }


    /*
    * 增
    * */
    public void add(HeightInfo hInfo){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", hInfo.getName());
        values.put("ismale", hInfo.isIsmale());
        values.put("fheight", hInfo.getFheight());
        values.put("mheight", hInfo.getMheight());
        values.put("sheight", hInfo.getSheight());
        db.insert("heightinfo", null, values);
        db.close();
        notifyDataSetChanged();
    }

//    删
    public void remove(int pos){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete("heightinfo", "id = ?", new String[]{String.valueOf(getItem(pos).getId())});
    }

//    查
    public List<HeightInfo> find(List<HeightInfo> infos){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.query("heightinfo",null, null, null, null,null,null);
        infos.clear();
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt((cursor.getColumnIndex("id")));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String ismale = cursor.getString(cursor.getColumnIndex("ismale"));
                String fheight = cursor.getString(cursor.getColumnIndex("fheight"));
                String mheight = cursor.getString(cursor.getColumnIndex("mheight"));
                String sheight = cursor.getString(cursor.getColumnIndex("sheight"));
                HeightInfo h = new HeightInfo(id, name, Boolean.valueOf(ismale), fheight, mheight, sheight);
                Log.v("MyAdapter--query()", h.toString());
                infos.add(h);
            }while (cursor.moveToNext());
        }
        return infos;
    }

//    改
    public void update(int pos){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        HeightInfo hInfo = getItem(pos);
        ContentValues values = new ContentValues();
        values.put("name", hInfo.getName());
        values.put("ismale", hInfo.isIsmale());
        values.put("fheight", hInfo.getFheight());
        values.put("mheight", hInfo.getMheight());
        values.put("sheight", hInfo.getSheight());
        db.update("heightinfo", values, "id = ?" , new String[]{String.valueOf(getItem(pos).getId())});
    }
}
