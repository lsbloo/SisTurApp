package com.example.osvaldoairon.app4so.BaseAdapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.osvaldoairon.app4so.Modelo.Municipios;
import com.example.osvaldoairon.app4so.R;

import java.util.HashMap;
import java.util.List;

public class ExpanAdapter extends BaseExpandableListAdapter {


    private List<Municipios> listGroup;
    private HashMap<String,List<Municipios>> listHashMap;
    private LayoutInflater inflater;


    public ExpanAdapter(Context ctx , List<Municipios> listmun , HashMap<String,List<Municipios>> listdata){
        this.listGroup=listmun;
        this.listHashMap=listdata;
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(listGroup.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroup;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listGroup.get(groupPosition)).get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.listCabecalho);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        if(convertView==null){
            convertView = inflater.inflate(R.layout.list_item,null);
        }
        TextView listarfilhos = (TextView)convertView.findViewById(R.id.listItem);

        listarfilhos.setText(childText);

        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}
