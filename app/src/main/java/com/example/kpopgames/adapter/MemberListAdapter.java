package com.example.kpopgames.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kpopgames.R;
import com.example.kpopgames.model.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberListAdapter extends BaseAdapter {

    private List<Member> listData;
    private ArrayList<Member> arrayList;
    private LayoutInflater layoutInflater;
    private Context context;

    public MemberListAdapter(Context aContext, List<Member> listData) {
        this.context = aContext;
        this.listData = listData;
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(listData);
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_layout_member, null);
            holder = new ViewHolder();
            holder.nameView = convertView.findViewById(R.id.textView_name);
            holder.gruppeView = convertView.findViewById(R.id.textView_gruppe);
            holder.geburtstagView = convertView.findViewById(R.id.textView_geburtsdatum);
            convertView.setTag(holder);
        } else {
            holder = (MemberListAdapter.ViewHolder) convertView.getTag();
        }

        Member member = this.listData.get(position);
        holder.gruppeView.setText(member.getGruppe());
        holder.nameView.setText(member.getName());
        holder.geburtstagView.setText(member.getGeburtstag());

        //31.536.000

        return convertView;
    }

    static class ViewHolder {
        TextView nameView;
        TextView gruppeView;
        TextView geburtstagView;
    }
}
