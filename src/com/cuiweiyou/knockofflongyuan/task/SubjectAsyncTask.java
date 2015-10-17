package com.cuiweiyou.knockofflongyuan.task;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import android.os.AsyncTask;

import com.cuiweiyou.knockofflongyuan.back.ISubjectListBack;
import com.cuiweiyou.knockofflongyuan.bean.SubjectBean;
import com.cuiweiyou.knockofflongyuan.util.HttpRequestUtil;
import com.cuiweiyou.knockofflongyuan.util.JsonUtil;

/**
 * bottom层 主题列表异步请求
 * @author Administrator
 */
public class SubjectAsyncTask extends AsyncTask<String, Void, List<SubjectBean>> {
	
	private ISubjectListBack back;

	/**
	 * bottom层 主题列表异步请求
	 * @param back 回调对象
	 */
	public SubjectAsyncTask(ISubjectListBack back){
		this.back = back;
	}

	@Override
	protected List<SubjectBean> doInBackground(String... params) {
		String url = params[0];
		
		try {
			List<SubjectBean> result;
			
			String request = new HttpRequestUtil().requestJson(url);
			
			result = new JsonUtil().analyzeSubjectList(request);
			
			return result;
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected void onPostExecute(List<SubjectBean> result) {
		super.onPostExecute(result);
		
		back.setSbList(result);
	}
}
