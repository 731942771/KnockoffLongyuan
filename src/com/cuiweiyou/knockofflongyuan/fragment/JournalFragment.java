package com.cuiweiyou.knockofflongyuan.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.cuiweiyou.knockofflongyuan.R;
import com.cuiweiyou.knockofflongyuan.adapter.LVJournalAdapter;
import com.cuiweiyou.knockofflongyuan.back.IJournalListBack;
import com.cuiweiyou.knockofflongyuan.bean.JournalBean;
import com.cuiweiyou.knockofflongyuan.model.UrlModel;
import com.cuiweiyou.knockofflongyuan.task.JournalAsyncTask;

/**
 * 杂志Fragment
 * 
 * @author Administrator
 */
public class JournalFragment extends Fragment implements IJournalListBack {

	private ListView mLVJournal;
	private LVJournalAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mViewJournalfmg = inflater.inflate(R.layout.view_journal_fmg, container, false);
		
		mLVJournal = (ListView) mViewJournalfmg.findViewById(R.id.lv_journals_jrlfmg);
		
		RadioGroup mRadioGroup = (RadioGroup) mViewJournalfmg.findViewById(R.id.rgroup_jrlfmg);
		((RadioButton)mRadioGroup.getChildAt(0)).setChecked(true);
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO 被选中的RB
				
			}
		});
		
		new JournalAsyncTask(this).execute(UrlModel.journalShopUrl);
		
		return mViewJournalfmg;
	}

	@Override
	public void setJrlList(List<JournalBean> srhList) {
		getActivity().findViewById(R.id.pb_isLoading_jrlfmg).setVisibility(View.GONE);
		
		if(srhList == null){
			getActivity().findViewById(R.id.iv_warning_jrlfmg).setVisibility(View.VISIBLE);
			return;
		}
		
		adapter = new LVJournalAdapter(getActivity(), srhList);
		mLVJournal.setAdapter(adapter);
	}
}
