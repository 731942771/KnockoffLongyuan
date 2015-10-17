package com.cuiweiyou.knockofflongyuan.task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;

import android.os.AsyncTask;

import com.cuiweiyou.knockofflongyuan.back.IJournalListBack;
import com.cuiweiyou.knockofflongyuan.bean.JournalBean;
import com.cuiweiyou.knockofflongyuan.util.HttpRequestUtil;
import com.cuiweiyou.knockofflongyuan.util.JsonUtil;

public class JournalAsyncTask extends AsyncTask<String, Void, List<JournalBean>>{
	
	private IJournalListBack back;

	public JournalAsyncTask(IJournalListBack back){
		this.back = back;
	}

	@Override
	protected List<JournalBean> doInBackground(String... params) {
		if(params[0] == null)
			return null;
		
		try {
			
			String json = new HttpRequestUtil().requestJson(params[0]);
			
			List<JournalBean> srhList = new JsonUtil().analyzeJournalList(json);
			
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
	protected void onPostExecute(List<JournalBean> result) {
		super.onPostExecute(result);
		
		back.setJrlList(result);
	}
}
