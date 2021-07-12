package com.example.management.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.management.R;
import com.example.management.bean.CategoryBean;
import com.example.management.bean.CollectBean;

import java.util.List;

/**
 * @ProjectName : Management
 * @Author : 王敏健
 * @Time : 2021-07-09 12:08
 * @Description : 描述
 */
public class CollectDetailAdapter extends BaseAdapter {

    private Context mContext;
    private List<CollectBean> CollectList;

    public CollectDetailAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<CollectBean> CollectList) {
        this.CollectList = CollectList;
    }

    public void clearData() {
        if (CollectList != null) {
            CollectList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return CollectList == null ? 0 : CollectList.size();
    }

    @Override
    public Object getItem(int position) {
        return CollectList == null ? null : CollectList.get(position);
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
            convertView = _LayoutInflater.inflate(R.layout.item_collect_detail, null);
            viewHolder = new ViewHolder();
            viewHolder.tvCollectSumTruckCode = convertView.findViewById(R.id.tv_collect_sum_truck);
            viewHolder.tvCollectSumDriver = convertView.findViewById(R.id.tv_collect_sum_driver);
            viewHolder.tvCollectSumMaterial = convertView.findViewById(R.id.tv_collect_sum_material);
            viewHolder.tvCollectSumNet = convertView.findViewById(R.id.tv_collect_sum_net);
            viewHolder.tvCollectSumDeliver = convertView.findViewById(R.id.tv_collect_sum_deliver);
            viewHolder.tvCollectSumCreate = convertView.findViewById(R.id.tv_collect_sum_create);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CollectBean collectBean = CollectList.get(position);
        viewHolder.tvCollectSumTruckCode.setText(collectBean.getLicenseNumber());
        viewHolder.tvCollectSumDriver.setText(collectBean.getDrivername());
        viewHolder.tvCollectSumMaterial.setText(collectBean.getMaterielName());
        viewHolder.tvCollectSumNet.setText(String.valueOf(collectBean.getNetWeight()));
        viewHolder.tvCollectSumDeliver.setText(collectBean.getDeliveName());
        viewHolder.tvCollectSumCreate.setText(collectBean.getCreatDate().replace("T", " ").substring(0, 19));

        return convertView;
    }

    static class ViewHolder {
        TextView tvCollectSumTruckCode;
        TextView tvCollectSumDriver;
        TextView tvCollectSumMaterial;
        TextView tvCollectSumNet;
        TextView tvCollectSumDeliver;
        TextView tvCollectSumCreate;
    }
}
