package com.cuiweiyou.knockofflongyuan.task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;

import android.os.AsyncTask;

import com.cuiweiyou.knockofflongyuan.back.ISearchListBack;
import com.cuiweiyou.knockofflongyuan.bean.SearchBean;
import com.cuiweiyou.knockofflongyuan.util.HttpRequestUtil;
import com.cuiweiyou.knockofflongyuan.util.JsonUtil;

public class SearchAsyncTask extends AsyncTask<String, Void, List<SearchBean>>{
	
	private ISearchListBack back;

	public SearchAsyncTask(ISearchListBack back){
		this.back = back;
	}

	@Override
	protected List<SearchBean> doInBackground(String... params) {
		if(params[0] == null)
			return null;
		
		try {
			
			String json = new HttpRequestUtil().requestJson(params[0]);
			
			List<SearchBean> srhList = new JsonUtil().analyzeSearchList(json);
			
			return srhList;
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(List<SearchBean> result) {
		super.onPostExecute(result);
		
		back.setSrhList(result);
	}
}
