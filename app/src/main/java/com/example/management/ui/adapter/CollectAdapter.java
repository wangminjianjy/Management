package com.example.management.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.management.R;
import com.example.management.bean.CategoryBean;

import java.util.List;

/**
 * @ProjectName : Management
 * @Author : 王敏健
 * @Time : 2021-07-09 12:08
 * @Description : 描述
 */
public class CollectAdapter extends BaseAdapter {

    private Context mContext;
    private List<CategoryBean> CategoryList;

    public CollectAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<CategoryBean> CategoryList) {
        this.CategoryList = CategoryList;
    }

    public void clearData() {
        if (CategoryList != null) {
            CategoryList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return CategoryList == null ? 0 : CategoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return CategoryList == null ? null : CategoryList.get(position);
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
            convertView = _LayoutInflater.inflate(R.layout.item_collect, null);
            viewHolder = new ViewHolder();
            viewHolder.tvCollectName = convertView.findViewById(R.id.tv_collect_name);
            viewHolder.tvCollectNum = convertView.findViewById(R.id.tv_collect_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CategoryBean categoryBean = CategoryList.get(position);
        viewHolder.tvCollectName.setText(categoryBean.getCategoryName());
        viewHolder.tvCollectNum.setText(String.valueOf(categoryBean.getCategoryCount()));

        return convertView;
    }

    static class ViewHolder {
        TextView tvCollectName;
        TextView tvCollectNum;
    }
}
