package com.cuiweiyou.knockofflongyuan.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cuiweiyou.knockofflongyuan.R;
import com.cuiweiyou.knockofflongyuan.activity.InformationActivity;
import com.cuiweiyou.knockofflongyuan.adapter.LVArticleAdapter;
import com.cuiweiyou.knockofflongyuan.back.IArticleListBack;
import com.cuiweiyou.knockofflongyuan.bean.ArticleBean;
import com.cuiweiyou.knockofflongyuan.model.UrlModel;
import com.cuiweiyou.knockofflongyuan.task.ArticleAsyncTask;
import com.cuiweiyou.knockofflongyuan.util.ContextApplication;

/**
 * 首页Fragment
 * @author Administrator
 */
public class HomeFragment extends Fragment implements IArticleListBack, OnItemClickListener{

	/** 显示主体ListView **/
	private ListView mLVMainBody;
	private LVArticleAdapter articleAdapter;
	private View mViewHmfmg;
	private List<ArticleBean> list = new ArrayList<ArticleBean>();
	private ImageView mIVRefresh;
	/** HmFmg正处于刷新的状态 **/
	public boolean isUpdating = false;
	/** 是否是双击刷新数据 **/
	public boolean isRefresh;
//	/** 第几次上滑 **/
//	private int pageindex = 0;
//	/** 下拉或上滑标识 **/
//	private int updateFlag = -1;
	/** 无数据提示图标 **/
	private ImageView mIVWarning;
	public int itemCount;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mViewHmfmg = inflater.inflate(R.layout.view_home_fmg, container, false);
		mIVWarning = (ImageView) mViewHmfmg.findViewById(R.id.iv_warning_hmfmg);
		
		mIVRefresh = (ImageView) mViewHmfmg.findViewById(R.id.iv_btn_refresh_hmfmg);
		
		mLVMainBody = (ListView) mViewHmfmg.findViewById(R.id.lv_mainbody_hmfmg);

		mLVMainBody.setOnItemClickListener(this);
		
		/* 须要从bottom层发来 TODO
		 "pagesize=30&" + // 每次请求的资讯数量 
		 "categoryid=1&" + // 主题，为空指“全部”。对应subjectUrl请求回的json解析出的“CategoryCode” 
		 "pageindex=1";                    */
		String end = "pagesize=30&categoryid=&pageindex=1";
		///////////////////////
		new ArticleAsyncTask(this).execute(UrlModel.articleListUrl + end);
		isUpdating = true;
		
		return mViewHmfmg;
	}
	
	/**
	 * 后台请求数据结束后发回条目集合设置适配器
	 */
	@Override
	public void setABList(List<ArticleBean> list){
		mViewHmfmg.findViewById(R.id.pb_isLoading_hmfmg).setVisibility(View.GONE);
		isUpdating = false;

		if(list == null){
			if(isRefresh){
				Toast.makeText(ContextApplication.context, "没有更新的数据", Toast.LENGTH_SHORT).show();
				isRefresh = false;
			} else {
				Toast.makeText(ContextApplication.context, "请求资讯列表失败", Toast.LENGTH_SHORT).show();
				mIVWarning.setVisibility(View.VISIBLE);
			}
			
			return;
		} else {
			mIVWarning.setVisibility(View.GONE);	// 初始运行无数据，之后又刷新出了数据
		}
		
		this.list.addAll(0, list);// 新数据放在最前面
			
		articleAdapter = new LVArticleAdapter(getActivity(), this.list);
		mLVMainBody.setAdapter(articleAdapter);
	}

	/**
	 * mLVMainBody.setOnItemClickListener(this);
	 * 添加header后，header的索引为0；否则第一个item的索引才是0
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		ArticleBean item = (ArticleBean) articleAdapter.getItem(position);	// 添加header后，header的索引为0
		String title = item.getTitle();
		String titleID = item.getTitleID();
		String infUrl = UrlModel.informationUrl + "titleid=" + titleID + "&fromtype=2"; //&categoryid=9";
		
		// 进入正文Aty
		Intent intent=new Intent(getActivity(), InformationActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("infUrl", infUrl);
		startActivity(intent);
		
		// 第一个参数是新Aty启动时动画效果，第二个参数是当前Aty退出时动画效果
		// 由左向右滑入的效果
		//getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right); // 系统的
		//getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);    			 // 自定义的
		// 改为在style.xml里自定义切换动画。并在AM.xml的Activity的theme上注册。
	}

	/**
	 * 双击“首页”后刷新<br/>
	 * 在HmAty中双击“首页”调用
	 */
	public void update() {
		isRefresh = true;
		isUpdating = true;
		
		// TODO 主题匹配
		String end = "pagesize=30&categoryid=&pageindex=1";
		new ArticleAsyncTask(this).execute(UrlModel.articleListUrl + end);
		
		final RotateAnimation ra = new RotateAnimation(
				1f, 						// 初始角度
				360f, 						// 目标角度
				Animation.RELATIVE_TO_SELF, // 指定pivotXValue的操作模式。按照自身属性操作
				0.5f, 						// pivotXValue，一个自身宽度的0-1百分比。0,5即百分之50-自身宽的中间
				Animation.RELATIVE_TO_SELF, // 
				0.5f);						// pivotYValue，自身高度百分比
		ra.setDuration(1000);
		ra.setInterpolator(new LinearInterpolator());
		ra.setAnimationListener(new AnimationListener() {
			public void onAnimationStart(Animation animation) { }
			public void onAnimationRepeat(Animation animation) { }
			@Override
			public void onAnimationEnd(Animation animation) {
				if(isUpdating){
					ra.reset();
					ra.start();	// 不执行reset不会导致循环播放。仅多播放一次
				}
			}
		});
		
		mIVRefresh.startAnimation(ra);
	}
	
	/**
	 * 上滑刷新
	 * @param updateCount 第几次上滑进行加载
	 */
	public void loadUpdate(int updateCount){
		/*
		 *  "pagesize=30&" +                          // 每页条目数量 
		 * 	"categoryid=1&" +                         // 主题id 
		 * 	"pageindex=2&" +                          // 分页，第几次上拉 
		 * 	"updatedate=2015%2F09%2F17+17%3A06%3A57"; // 刷新时刻。URL编码 2015/09/17 17:06:57
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");	// 指定格式
		String now = sdf.format(new Date());								// 格式化日期
		
		String end = null;
		try {
			end = 
			"pagesize=30&" +
			"categoryid=1&" +
			"pageindex=" + updateCount + "&" +
			"updatedate=" + URLEncoder.encode(now, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		new ArticleAsyncTask(this).execute(UrlModel.articleListUpdateUrl + end);
	}
}
