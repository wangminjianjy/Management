package com.example.management.ui.main;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.management.R;
import com.example.management.base.BaseActivity;
import com.example.management.base.BaseConfig;
import com.example.management.bean.UserBean;
import com.example.management.ui.adapter.CustomViewPager;
import com.example.management.ui.adapter.MainPagerAdapter;
import com.example.management.util.AlertDialogUtil;
import com.example.management.util.AppUtil;
import com.example.management.util.SharedPreUtil;
import com.example.management.util.ToastUtil;
import com.example.management.widget.AlarmRadioButton;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private CustomViewPager mViewPager;
    private MainPagerAdapter mPagerAdapter;
    private MainFragment mainFragment;
    private CollectFragment collectFragment;
//    private QueryFragment queryFragment;
    private PersonFragment personFragment;

    private RadioGroup mMenuRg;
    private AlarmRadioButton mMainARB;
    private AlarmRadioButton mCollectARB;
//    private AlarmRadioButton mQueryARB;
    private AlarmRadioButton mPersonARB;

    private List<AlarmRadioButton> btnItems;
    private List<Fragment> fragments;

    private static final int VIEW_PAGER_START = 0;
    private int mViewNum = 0;

    private AlertDialogUtil alertDialog;

    NfcAdapter mNfcAdapter;
    PendingIntent mPendingIntent;
    MediaPlayer right;
    MediaPlayer ohNo;

    @Override
    protected Settings setActivitySettings(View contentView) {
        return null;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(View contentView) {
        mMenuRg = contentView.findViewById(R.id.radio_bar);
        mMainARB = mMenuRg.findViewById(R.id.tab_menu_main);
        mCollectARB = mMenuRg.findViewById(R.id.tab_menu_collect);
//        mQueryARB = mMenuRg.findViewById(R.id.tab_menu_query);
        mPersonARB = mMenuRg.findViewById(R.id.tab_menu_person);
        mViewPager = findViewById(R.id.view_pager);

        mMainARB.dismissSign();
        mCollectARB.dismissSign();
//        mQueryARB.dismissSign();
        mPersonARB.dismissSign();
    }

    @Override
    protected void bindEvent(View contentView) {
        mViewPager.addOnPageChangeListener(pageChange);
        mMenuRg.setOnCheckedChangeListener(checkedChange);
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        btnItems = new ArrayList<>();

        mainFragment = MainFragment.newInstance();
        collectFragment = CollectFragment.newInstance();
        personFragment = PersonFragment.newInstance();
//        queryFragment = QueryFragment.newInstance();

        Gson gson = new Gson();
        UserBean userBean = gson.fromJson(SharedPreUtil.getUserInfo(), UserBean.class);
//        String[] perArray = userBean.getFactoryCode().split(",");
//        String[] perArray = "1,2".split(",");
//        mMainARB.setVisibility(View.GONE);
//        mCollectARB.setVisibility(View.GONE);
//        for (int i = 0; i < perArray.length; i++) {
//            switch (perArray[i]) {
//                case "1":
//                    mMainARB.setVisibility(View.VISIBLE);
//                    fragments.add(mainFragment);
//                    btnItems.add(mMainARB);
//                    break;
//                case "2":
//                    mCollectARB.setVisibility(View.VISIBLE);
//                    fragments.add(collectFragment);
//                    btnItems.add(mCollectARB);
//                    break;
//                case "3":
//                    mRegisterARB.setVisibility(View.VISIBLE);
//                    fragments.add(registerFragment);
//                    btnItems.add(mRegisterARB);
//                    break;
//                case "4":
//                    mProductARB.setVisibility(View.VISIBLE);
//                    fragments.add(productFragment);
//                    btnItems.add(mProductARB);
//                    break;
//            }
//        }
        fragments.add(mainFragment);
        btnItems.add(mMainARB);
        fragments.add(collectFragment);
        btnItems.add(mCollectARB);
//        fragments.add(queryFragment);
//        btnItems.add(mQueryARB);
        fragments.add(personFragment);
        btnItems.add(mPersonARB);
        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setPagingEnabled(true);
        mViewPager.setAdapter(mPagerAdapter);
        mMenuRg.check(btnItems.get(VIEW_PAGER_START).getId());

        setFragmentData();
    }

    private void setFragmentData() {
        if (mainFragment != null && fragments.get(mViewNum) instanceof MainFragment) {
            mainFragment.getMainData();
        } else if (collectFragment != null && fragments.get(mViewNum) instanceof CollectFragment) {
            collectFragment.getCollectData();
//        } else if (queryFragment != null && fragments.get(mViewNum) instanceof QueryFragment) {
//            queryFragment.getQueryData("", "", "", "");
        } else if (personFragment != null && fragments.get(mViewNum) instanceof PersonFragment) {
            personFragment.setPersonData();
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    ViewPager.OnPageChangeListener pageChange = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_SETTLING) {
                mViewNum = mViewPager.getCurrentItem();
                mMenuRg.check(btnItems.get(mViewNum).getId());
                setFragmentData();
            }
        }
    };

    RadioGroup.OnCheckedChangeListener checkedChange = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            int index = 0;
            for (int i = 0; i < btnItems.size(); i++) {
                if (checkedId == btnItems.get(i).getId()) {
                    index = i;
                    break;
                }
            }
            mViewPager.setCurrentItem(index);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter == null) {
            return;
        } else {
            if (!mNfcAdapter.isEnabled()) {
                return;
            } else {
                mNfcAdapter.enableForegroundDispatch(MainActivity.this, mPendingIntent, null, null);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter == null) {
            return;
        } else {
            mNfcAdapter.disableForegroundDispatch(this);
            mNfcAdapter.disableForegroundNdefPush(this);
        }
    }

    private void initNFC() {
        // 获取默认的NFC控制器
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            ToastUtil.showSingleToast(getString(R.string.hint_no_nfc), Toast.LENGTH_SHORT);
            return;
        }
        if (!mNfcAdapter.isEnabled()) {
            OpenNfc();
        } else {
            initEnableNfc();
        }
    }

    private void initEnableNfc() {
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter filter = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        try {
            filter.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(
                Intent.FLAG_ACTIVITY_SINGLE_TOP), PendingIntent.FLAG_UPDATE_CURRENT);
        initSound();
        initSoundOther();
    }

    private void initSound() {
        right = new MediaPlayer();
        AssetFileDescriptor rFile = getResources().openRawResourceFd(R.raw.right);
        try {
            right.setDataSource(rFile.getFileDescriptor(), rFile.getStartOffset(), rFile.getLength());
            rFile.close();
            right.prepare();
        } catch (IOException e) {
            right = null;
        }
    }

    private void initSoundOther() {
        ohNo = new MediaPlayer();
        AssetFileDescriptor nFile = getResources().openRawResourceFd(R.raw.ohno);
        try {
            ohNo.setDataSource(nFile.getFileDescriptor(), nFile.getStartOffset(), nFile.getLength());
            nFile.close();
            ohNo.prepare();
        } catch (IOException e) {
            ohNo = null;
        }
    }

    private void OpenNfc() {
        alertDialog = new AlertDialogUtil(this, getString(R.string.hint_nfc_open), 0, false, BaseConfig.REQUEST_CODE_FIRST, onDialogClick);
        alertDialog.show();
    }

    @Override
    public void onNewIntent(Intent paramIntent) {
        setIntent(paramIntent);
        resolveIntent(paramIntent);
    }

    protected void resolveIntent(Intent intent) {
        // 得到是否检测到TAG触发
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())
                || NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            // 处理该intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            // 获取id数组
            byte[] bytesId = tag.getId();
            String cardNo = AppUtil.bytesToHexString(bytesId);
            getNFCResult(cardNo);
        }
    }

    AlertDialogUtil.OnDialogClickListener onDialogClick = new AlertDialogUtil.OnDialogClickListener() {
        @Override
        public void onDialogClick(int requestCode, boolean isPositive) {
            if (requestCode == BaseConfig.REQUEST_CODE_FIRST && isPositive) {
                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_NFC_SETTINGS);
                startActivityForResult(callGPSSettingIntent, BaseConfig.REQUEST_CODE_NFC);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BaseConfig.REQUEST_CODE_NFC) {
            alertDialog.dismiss();
            initNFC();
        }
    }

    public void getNFCResult(String cardStr) {
        if (TextUtils.isEmpty(cardStr)) {
            ToastUtil.showToast(getString(R.string.hint_nfc_fail), Toast.LENGTH_SHORT);
        } else {
//            if (tallyingFragment != null && fragments.get(mViewNum) instanceof TallyingFragment) {
//                tallyingFragment.getTallyingData(cardStr);
//            } else if (registerFragment != null && fragments.get(mViewNum) instanceof RegisterFragment) {
//                registerFragment.getRegisterData(cardStr);
//            } else if (chemicalFragment != null && fragments.get(mViewNum) instanceof ChemicalFragment) {
//                chemicalFragment.getChemicalData(cardStr);
//            } else if (productFragment != null && fragments.get(mViewNum) instanceof ProductFragment) {
//                productFragment.getProductData(cardStr);
//            }
        }
    }
}
