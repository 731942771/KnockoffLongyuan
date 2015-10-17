package com.cuiweiyou.knockofflongyuan.task;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;

import android.os.AsyncTask;

import com.cuiweiyou.knockofflongyuan.back.IInformationListBack;
import com.cuiweiyou.knockofflongyuan.bean.InformationBean;
import com.cuiweiyou.knockofflongyuan.util.HttpRequestUtil;
import com.cuiweiyou.knockofflongyuan.util.JsonUtil;

public class InformationAsyncTask extends AsyncTask<String, Void, InformationBean> {
	
	private IInformationListBack back;

	public InformationAsyncTask(IInformationListBack back){
		this.back = back;
	}

	@Override
	protected InformationBean doInBackground(String... params) {
		String url = params[0];
		
		try {
			InformationBean result;
			
			String request = new HttpRequestUtil().requestJson(url);
			
			result = new JsonUtil().analyzeInformation(request);
			
			return result;
			
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
	protected void onPostExecute(InformationBean result) {
		super.onPostExecute(result);
		
		back.setIbList(result);
	}
}
