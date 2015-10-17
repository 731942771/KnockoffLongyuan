package com.cuiweiyou.knockofflongyuan.task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;

import android.os.AsyncTask;

import com.cuiweiyou.knockofflongyuan.back.IArticleListBack;
import com.cuiweiyou.knockofflongyuan.bean.ArticleBean;
import com.cuiweiyou.knockofflongyuan.fragment.HomeFragment;
import com.cuiweiyou.knockofflongyuan.util.HttpRequestUtil;
import com.cuiweiyou.knockofflongyuan.util.JsonUtil;

public class ArticleAsyncTask extends AsyncTask<String, Void, List<ArticleBean>> {
	
	private IArticleListBack back;

	public ArticleAsyncTask(IArticleListBack back){
		this.back = back;
	}

	@Override
	protected List<ArticleBean> doInBackground(String... params) {
		String url = params[0];
		
		try {
			List<ArticleBean> result;
			
			String request = new HttpRequestUtil().requestJson(url);
			
			result = new JsonUtil().analyzeArticleList(request, (HomeFragment)back);
			
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
	protected void onPostExecute(List<ArticleBean> result) {
		super.onPostExecute(result);
		
		back.setABList(result);
	}
}
