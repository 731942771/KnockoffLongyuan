package com.cuiweiyou.knockofflongyuan.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.cuiweiyou.knockofflongyuan.R;
import com.cuiweiyou.knockofflongyuan.back.ISearchListBack;
import com.cuiweiyou.knockofflongyuan.bean.SearchBean;
import com.cuiweiyou.knockofflongyuan.model.UrlModel;
import com.cuiweiyou.knockofflongyuan.task.SearchAsyncTask;
import com.cuiweiyou.knockofflongyuan.util.ContextApplication;

/**
 * 搜索Fragment
 * @author Administrator
 */
public class SearchFragment extends Fragment implements ISearchListBack{

	List<SearchBean> list;
	private GridView mGV;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View mViewSearchfmg = inflater.inflate(R.layout.view_search_fmg, container, false);
		
		mGV = (GridView) mViewSearchfmg.findViewById(R.id.gv_hotwords_srhfmg);
		
		// 首先的数据
		new SearchAsyncTask(this).execute(UrlModel.searchUrl);
		
		return mViewSearchfmg;
	}

	@Override
	public void setSrhList(List<SearchBean> srhList) {
		getActivity().findViewById(R.id.pb_isLoading_srhaty).setVisibility(View.GONE);
		
		if(srhList == null || srhList.size() < 1){
			getActivity().findViewById(R.id.iv_warning_srhaty).setVisibility(View.VISIBLE);
			return;
		}
		
		this.list = srhList;
		
		String[] words = new String[ list.size() ];
		for (int i = 0; i < list.size(); i++) {
			words[i] = list.get(i).getHotWordName();
		}
		
		//GVSearchAdapter adapter = new GVSearchAdapter(getActivity(), list);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				ContextApplication.context, 
				R.layout.item_gv_srhfmg, 
				R.id.tv_keyword_srhfmg, 
				words);
		
		mGV.setAdapter(adapter);
		
	}
}
