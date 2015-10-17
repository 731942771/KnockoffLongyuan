package com.cuiweiyou.knockofflongyuan.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.cuiweiyou.knockofflongyuan.bean.ArticleBean;
import com.cuiweiyou.knockofflongyuan.bean.InformationBean;
import com.cuiweiyou.knockofflongyuan.bean.JournalBean;
import com.cuiweiyou.knockofflongyuan.bean.SearchBean;
import com.cuiweiyou.knockofflongyuan.bean.SubjectBean;
import com.cuiweiyou.knockofflongyuan.fragment.HomeFragment;

/**
 * JSON数据解析
 * @author Administrator
 */
public class JsonUtil {

	/**
	 * 解析版本信息json字串
	 * @param json 版本信息json字串
	 * @return List集合
	 */
	public List analyzeAppVersion(String json){
		
		return null;
	}
	
	/**
	 * 解析bottom主题列表json字串
	 * @param json 主题列表json字串
	 * @return List SubjectBean集合
	 * @throws JSONException 
	 */
	public List<SubjectBean> analyzeSubjectList(String json) throws JSONException{
		
		if(json==null || json.length() < 1)
			return null;
		
		List<SubjectBean> list = new ArrayList<SubjectBean>();
		
		JSONArray jarr = new JSONObject(json).getJSONArray("Catagorys");
		
		for (int i = 0; i < jarr.length(); i++) {
			JSONObject obj = (JSONObject) jarr.get(i);
			list.add(new SubjectBean(obj.getString("CategoryCode"), obj.getString("CategoryName"), false));
		}
		
		return list;
	}
	
	/**
	 * 解析首页主题列表json字串
	 * @param json 首页主题列表json字串
	 * @return List集合
	 * @throws JSONException 
	 */
	public List<ArticleBean> analyzeArticleList(String json, HomeFragment hf) throws JSONException{

		if(json == null || json.length() < 1)
			return null;
		
		List<ArticleBean> list = new ArrayList<ArticleBean>();
		
		JSONObject jo = new JSONObject(json);
		int ic = jo.getInt("ItemCount");	//据此判断是否有新数据
		if( ic <= hf.itemCount){
			// RuntimeException: An error occured while executing doInBackground()
			// Caused by: java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()
			// Toast.makeText(ContextApplication.context, "没有更新的数据", Toast.LENGTH_SHORT).show();
			Log.e("sst", "没有更新的数据");
			return null;
		} else {
			hf.itemCount = ic;
		}
		
		JSONArray jarr = jo.getJSONArray("Articlelist");
		
		for (int i = 0; i < jarr.length(); i++) {
			JSONObject obj = (JSONObject) jarr.get(i);
			list.add(new ArticleBean(
					obj.getString("TitleID"), 
					obj.getString("Title"), 
					obj.getString("Author"), 
					obj.getString("Introduction"), 
					obj.getString("PubStartDate"), 
					obj.getString("ArticleImgList") 
					));
		}
		
		return list;
		
	}
	
	/**
	 * 解析搜索页面json字串
	 * @param json 搜索页面json字串
	 * @return List集合
	 * @throws JSONException 
	 */
	public List<SearchBean> analyzeSearchList(String json) throws JSONException{
		
		if(json == null || json.length() < 1)
			return null;
		
		List<SearchBean> list = new ArrayList<SearchBean>();
		
		JSONArray jarr = new JSONObject(json).getJSONArray("HotWords");
		
		for (int i = 0; i < jarr.length(); i++) {
			JSONObject obj = (JSONObject) jarr.get(i);
			list.add(new SearchBean(
					obj.getString("HotWordSearchID"), 
					obj.getString("HotWordName")
					));
		}
		
		return list;
		
	}
	
	/**
	 * 解析杂志页面json字串
	 * @param json 杂志页面json字串
	 * @return List集合
	 * @throws JSONException 
	 */
	public List<JournalBean> analyzeJournalList(String json) throws JSONException{
		
		if(json == null || json.length() < 1)
			return null;
		
		List<JournalBean> list = new ArrayList<JournalBean>();
		
		JSONArray jarr = new JSONObject(json).getJSONArray("Magazinelist");
		
		for (int i = 0; i < jarr.length(); i++) {
			JSONObject obj = (JSONObject) jarr.get(i);
			list.add(new JournalBean(
					obj.getString("MagazineName"), 
					obj.getString("MagazineGUID"),
					obj.getString("Year"),
					obj.getString("Issue"),
					obj.getString("IconList")
					));
		}
		
		return list;
	}
	
	/**
	 * 解析资讯正文json字串
	 * @param json 主题正文json字串
	 * @return List集合
	 * @throws JSONException 
	 */
	public InformationBean analyzeInformation(String json) throws JSONException{

		if(json == null || json.length() < 1 )
			return null;
		
		JSONObject obj = new JSONObject(json);
		
		return new InformationBean(
					obj.getString("MagazineName"), 
					obj.getString("Titleid"), 
					obj.getString("Year"), 
					obj.getString("Issue"), 
					obj.getString("Title"), 
					obj.getString("Content")
				);
	}
}
