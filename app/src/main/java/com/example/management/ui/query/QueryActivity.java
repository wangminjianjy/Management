package com.example.management.ui.query;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.OnDismissListener;
import com.example.management.R;
import com.example.management.base.BaseActivity;
import com.example.management.base.BaseConfig;
import com.example.management.bean.CollectBean;
import com.example.management.bean.CollectDetailBean;
import com.example.management.bean.UserBean;
import com.example.management.net.HttpCallback;
import com.example.management.net.HttpModel;
import com.example.management.net.HttpResult;
import com.example.management.ui.adapter.QueryAdapter;
import com.example.management.util.SharedPreUtil;
import com.example.management.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QueryActivity extends BaseActivity {

    private TextView queryBack;
    private TextView queryTitle;
    private EditText queryTruckCode;
    private TextView queryStart;
    private TextView queryEnd;
    private TextView tvQuerySearch;
    private TextView tvQueryNull;
    private ListView lvQueryData;
    private LinearLayout llSearchTitle;
    private LinearLayout llSearch;
    private LinearLayout llLine;

    private List<CollectBean> queryList;
    private QueryAdapter queryAdapter;

    private TimePickerView datePickerStart, datePickerEnd;
    private Calendar startDate, endDate;
    private Date dateStart, dateEnd;

    @Override
    protected Settings setActivitySettings(View contentView) {
        return new Settings();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_query;
    }

    @Override
    protected void initView(View contentView) {
        queryBack = contentView.findViewById(R.id.custom_back);
        queryTitle = contentView.findViewById(R.id.custom_title);
        queryTruckCode = contentView.findViewById(R.id.et_query_truck);
        queryStart = contentView.findViewById(R.id.tv_query_start);
        queryEnd = contentView.findViewById(R.id.tv_query_end);
        tvQuerySearch = contentView.findViewById(R.id.tv_query_search);
        tvQueryNull = contentView.findViewById(R.id.tv_query_null);
        lvQueryData = contentView.findViewById(R.id.lv_query_data);
        llSearchTitle = contentView.findViewById(R.id.ll_search_title);
        llSearch = contentView.findViewById(R.id.ll_search);
        llLine = contentView.findViewById(R.id.ll_line);
    }

    @Override
    protected void bindEvent(View contentView) {
        lvQueryData.setOnItemClickListener(onItemClick);
        queryBack.setOnClickListener(this);
        queryStart.setOnClickListener(this);
        queryEnd.setOnClickListener(this);
        tvQuerySearch.setOnClickListener(this);
        llSearchTitle.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        String title = getIntent().getStringExtra(BaseConfig.PARAM_QUERY);
        queryTitle.setText(String.format("%s%s", title, getString(R.string.query_title)));

        queryList = new ArrayList<>();
        queryAdapter = new QueryAdapter(this);
        lvQueryData.setAdapter(queryAdapter);

        searchQueryData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custom_back:
                finish();
                break;
            case R.id.tv_query_start:
                datePickerStart.show();
                break;
            case R.id.tv_query_end:
                datePickerEnd.show();
                break;
            case R.id.tv_query_search:
                searchQueryData();
                break;
            case R.id.ll_search_title:
                if (llSearch.getVisibility() == View.VISIBLE) {
                    llSearch.setVisibility(View.GONE);
                    llLine.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    llSearch.setVisibility(View.VISIBLE);
                    llLine.setVisibility(View.VISIBLE);
                    setDatePicker(new Date());
                }
                break;
            default:
                break;
        }
    }

    private void searchQueryData() {
        String truckCode = queryTruckCode.getText().toString();
        String startTime = queryStart.getText().toString();
        String endTime = queryEnd.getText().toString();
        getQueryData(startTime, endTime, truckCode);
    }

    public void getQueryData(String startTime, String endTime, String truckCode) {
        String flowName = getIntent().getStringExtra(BaseConfig.PARAM_QUERY);
        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(SharedPreUtil.getUserInfo(), UserBean.class);
        String companyID = userBean.getFactoryCode();
        HttpModel.getInstance().QueryData(flowName, companyID, startTime, endTime, truckCode, new HttpCallback(this) {
            @Override
            public void onSuccessStr(HttpResult httpResult) {
                Gson gson = new Gson();
                queryList = gson.fromJson(gson.toJson(httpResult.getResult()), new TypeToken<List<CollectBean>>() {
                }.getType());
                if (queryList.size() == 0) {
                    tvQueryNull.setVisibility(View.VISIBLE);
                } else {
                    tvQueryNull.setVisibility(View.GONE);
//                    if (llSearch.getVisibility() == View.VISIBLE) {
//                        llSearch.setVisibility(View.GONE);
//                        llLine.setVisibility(View.GONE);
//                    }
                    queryAdapter.setData(queryList);
                    queryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onErrorStr(HttpResult httpResult) {
                super.onErrorStr(httpResult);
                tvQueryNull.setVisibility(View.VISIBLE);
//                if (llSearch.getVisibility() == View.VISIBLE) {
//                    llSearch.setVisibility(View.GONE);
//                    llLine.setVisibility(View.GONE);
//                }
                queryList.clear();
                queryAdapter.setData(queryList);
                queryAdapter.notifyDataSetChanged();
            }
        });
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            queryDetail(queryList.get(position).getID());
        }
    };

    private void queryDetail(String ID) {
        Intent intent = new Intent(this, QueryDetailActivity.class);
        intent.putExtra(BaseConfig.PARAM_QUERY_DETAIL, ID);
        startActivity(intent);
    }

    private void setDatePicker(Date curDate) {
        initDate(curDate);

        datePickerStart = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                boolean isLegal = true;
                if (dateEnd != null) {
                    if (compareDate(date, dateEnd) > 0) {
                        ToastUtil.showToast("开始时间应小于结束时间", Toast.LENGTH_SHORT);
                        isLegal = false;
                    }
                }
                if (isLegal) {
                    dateStart = date;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    String dateYear = String.format(Locale.CHINA, "%d", calendar.get(Calendar.YEAR));
                    String dateMonth = String.format(Locale.CHINA, "%02d", calendar.get(Calendar.MONTH) + 1);
                    String dateDay = String.format(Locale.CHINA, "%02d", calendar.get(Calendar.DAY_OF_MONTH));
                    queryStart.setText(String.format(Locale.CHINA, "%s-%s-%s", dateYear, dateMonth, dateDay));
                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setCancelText("取消")
                .setSubmitText("确定")
                .setTitleText("选择开始日期")
                .setOutSideCancelable(false)
                .setDate(startDate)
                .isCenterLabel(false)
                .isDialog(false)
                .build();
        datePickerStart.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {

            }
        });

        datePickerEnd = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                boolean isLegal = true;
                if (dateStart != null) {
                    if (compareDate(dateStart, date) > 0) {
                        ToastUtil.showToast("结束时间应大于开始时间", Toast.LENGTH_SHORT);
                        isLegal = false;
                    }
                }
                if (isLegal) {
                    dateEnd = date;
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    String dateYear = String.format(Locale.CHINA, "%d", calendar.get(Calendar.YEAR));
                    String dateMonth = String.format(Locale.CHINA, "%02d", calendar.get(Calendar.MONTH) + 1);
                    String dateDay = String.format(Locale.CHINA, "%02d", calendar.get(Calendar.DAY_OF_MONTH));
                    queryEnd.setText(String.format(Locale.CHINA, "%s-%s-%s", dateYear, dateMonth, dateDay));
                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .setCancelText("取消")
                .setSubmitText("确定")
                .setTitleText("选择结束日期")
                .setOutSideCancelable(false)
                .setDate(endDate)
                .isCenterLabel(false)
                .isDialog(false)
                .build();
        datePickerEnd.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(Object o) {

            }
        });
    }

    private void initDate(Date curDate) {
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();

        endDate.setTime(curDate);
        startDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH) - 3);

        String yearStart = String.format(Locale.CHINA, "%d", startDate.get(Calendar.YEAR));
        String monthStart = String.format(Locale.CHINA, "%02d", startDate.get(Calendar.MONTH) + 1);
        String dayStart = String.format(Locale.CHINA, "%02d", startDate.get(Calendar.DAY_OF_MONTH));
//        queryStart.setText(String.format(Locale.CHINA, "%s-%s-%s", yearStart, monthStart, dayStart));

        String yearEnd = String.format(Locale.CHINA, "%d", endDate.get(Calendar.YEAR));
        String monthEnd = String.format(Locale.CHINA, "%02d", endDate.get(Calendar.MONTH) + 1);
        String dayEnd = String.format(Locale.CHINA, "%02d", endDate.get(Calendar.DAY_OF_MONTH));
//        queryEnd.setText(String.format(Locale.CHINA, "%s-%s-%s", yearEnd, monthEnd, dayEnd));
    }

    public int compareDate(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        if (calendar1.get(Calendar.YEAR) > calendar2.get(Calendar.YEAR)) {
            return 1;
        } else if (calendar1.get(Calendar.YEAR) < calendar2.get(Calendar.YEAR)) {
            return -1;
        } else {
            if (calendar1.get(Calendar.MONTH) > calendar2.get(Calendar.MONTH)) {
                return 1;
            } else if (calendar1.get(Calendar.MONTH) < calendar2.get(Calendar.MONTH)) {
                return -1;
            } else {
                if (calendar1.get(Calendar.DAY_OF_MONTH) > calendar2.get(Calendar.DAY_OF_MONTH)) {
                    return 1;
                } else if (calendar1.get(Calendar.DAY_OF_MONTH) < calendar2.get(Calendar.DAY_OF_MONTH)) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }
}