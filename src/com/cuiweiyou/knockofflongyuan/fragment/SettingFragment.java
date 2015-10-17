package com.cuiweiyou.knockofflongyuan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cuiweiyou.knockofflongyuan.R;

/**
 * 设置Fragment
 * @author Administrator
 */
public class SettingFragment extends Fragment{


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View mViewSettingfmg = inflater.inflate(R.layout.view_setting_fmg, container, false);
		
		
		return mViewSettingfmg;
	}
}
