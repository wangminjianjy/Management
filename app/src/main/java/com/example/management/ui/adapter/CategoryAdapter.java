package com.example.management.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.management.R;
import com.example.management.bean.CategoryBean;

import java.util.List;
import java.util.Locale;

/**
 * @ProjectName : Management
 * @Author : 王敏健
 * @Time : 2021-07-09 12:08
 * @Description : 描述
 */
public class CategoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<CategoryBean> CategoryList;

    public CategoryAdapter(Context mContext) {
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
            convertView = _LayoutInflater.inflate(R.layout.item_category, null);
            viewHolder = new ViewHolder();
            viewHolder.tvCategoryName = convertView.findViewById(R.id.tv_category_name);
            viewHolder.tvCategoryNum = convertView.findViewById(R.id.tv_category_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CategoryBean categoryBean = CategoryList.get(position);
        viewHolder.tvCategoryName.setText(categoryBean.getCategoryName());
        viewHolder.tvCategoryNum.setText(String.valueOf(categoryBean.getCategoryCount()));

        return convertView;
    }

    static class ViewHolder {
        TextView tvCategoryName;
        TextView tvCategoryNum;
    }
}
