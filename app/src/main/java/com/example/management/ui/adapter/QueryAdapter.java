package com.example.management.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.management.R;
import com.example.management.bean.CollectBean;

import java.util.List;

/**
 * @ProjectName : Management
 * @Author : 王敏健
 * @Time : 2021-07-09 12:08
 * @Description : 描述
 */
public class QueryAdapter extends BaseAdapter {

    private Context mContext;
    private List<CollectBean> queryList;

    public QueryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<CollectBean> CollectList) {
        this.queryList = CollectList;
    }

    public void clearData() {
        if (queryList != null) {
            queryList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return queryList == null ? 0 : queryList.size();
    }

    @Override
    public Object getItem(int position) {
        return queryList == null ? null : queryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
            convertView = _LayoutInflater.inflate(R.layout.item_query, null);
            viewHolder = new ViewHolder();
            viewHolder.tvQueryTruckCode = convertView.findViewById(R.id.tv_query_truck);
            viewHolder.tvQueryDriver = convertView.findViewById(R.id.tv_query_driver);
            viewHolder.tvQueryMaterial = convertView.findViewById(R.id.tv_query_material);
            viewHolder.tvQueryNet = convertView.findViewById(R.id.tv_query_net);
            viewHolder.tvQueryDeliver = convertView.findViewById(R.id.tv_query_deliver);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CollectBean collectBean = queryList.get(position);
        viewHolder.tvQueryTruckCode.setText(collectBean.getLicenseNumber());
        viewHolder.tvQueryDriver.setText(collectBean.getDrivername());
        viewHolder.tvQueryMaterial.setText(collectBean.getMaterielName());
        viewHolder.tvQueryNet.setText(String.valueOf(collectBean.getNetWeight()));
        viewHolder.tvQueryDeliver.setText(collectBean.getDeliveName());

        return convertView;
    }

    static class ViewHolder {
        TextView tvQueryTruckCode;
        TextView tvQueryDriver;
        TextView tvQueryMaterial;
        TextView tvQueryNet;
        TextView tvQueryDeliver;
    }
}
