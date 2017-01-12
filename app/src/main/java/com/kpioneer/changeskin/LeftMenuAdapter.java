package com.kpioneer.changeskin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by xionhgu on 2016/9/19.
 * Email：965705418@qq.com
 */
public class LeftMenuAdapter extends BaseAdapter {

    public List<String> list;

    private LayoutInflater inflater = null;
    private Context context;

    public LeftMenuAdapter(Context context) {


        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<String> list) {
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item,
                    parent, false);
            holder = new ViewHolder(convertView);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.tvTitle.setText(list.get(position));

        return convertView;

    }


    static class ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_tip)
        TextView tvTip;
        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
