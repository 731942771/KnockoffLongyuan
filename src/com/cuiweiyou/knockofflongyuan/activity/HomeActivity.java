package com.cuiweiyou.knockofflongyuan.activity;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cuiweiyou.knockofflongyuan.R;
import com.cuiweiyou.knockofflongyuan.adapter.LVSubjectAdapter;
import com.cuiweiyou.knockofflongyuan.back.ISubjectListBack;
import com.cuiweiyou.knockofflongyuan.bean.SubjectBean;
import com.cuiweiyou.knockofflongyuan.fragment.CollectionFragment;
import com.cuiweiyou.knockofflongyuan.fragment.HomeFragment;
import com.cuiweiyou.knockofflongyuan.fragment.JournalFragment;
import com.cuiweiyou.knockofflongyuan.fragment.SearchFragment;
import com.cuiweiyou.knockofflongyuan.fragment.SettingFragment;
import com.cuiweiyou.knockofflongyuan.model.UrlModel;
import com.cuiweiyou.knockofflongyuan.task.SubjectAsyncTask;
import com.cuiweiyou.knockofflongyuan.util.ContextApplication;
import com.cuiweiyou.knockofflongyuan.view.ViewTopScrollerLinearLyt;

/**
 * 主题界面                                                                                                                                             <br/>
 * 界面布局分两层top、bottom，用于“抽屉”动画                                                                        <br/>
 * bottom层主体是ListView，每个条目即一个主题，默认“全部”                   <br/>
 *   &emsp;		选择一个主题，top层<首页 HomeFragment>主体对应改变内容                <br/>
 * top层容纳：                                                                                           <br/>
 *   &emsp;		首页 HomeFragment，可触发抽屉动画               <br/>
 *   &emsp;		搜索 SearchFragment                  <br/>
 *   &emsp;		杂志 JournalFragment                 <br/>
 *   &emsp;		设置 SettingFragment                 <br/>
 * @author Administrator
 */
public class HomeActivity extends FragmentActivity implements ISubjectListBack {
	/** 当前显示的Fmg索引 **/
	public int currentTag;
	
	/** 首页 HomeFragment **/
	HomeFragment mHomeFmg;
	/** 搜索 SearchFragment **/
	SearchFragment mSearchFmg;
	/** 杂志 JournalFragment **/
	JournalFragment mJournalFmg;
	/** 设置 SettingFragment **/
	SettingFragment mSettingFmg;
	/** 收藏 CollectionActivity,防止打开应用后一直不点开此fmg。直接在hmaty里将之实例化 **/
	CollectionFragment mCollectionFmg;
	
	/** Fragment管理器 **/
	private FragmentManager mFragmentManager;
	
	/** bottom层中的主题ListView，Item条目view是item_lv_subject.xml **/
	private ListView mLVSubject;
	/** bottom层中的主题集合 **/
	private List<SubjectBean> subjectList;
	/** bottom层主题LV的适配器 **/
	private LVSubjectAdapter mLVSubjectAdapter;
	
	/** 上滑刷新的次数。static：防止多次打开应用后上拉重复数据 TODO 重启系统时的数据去留 **/
	private static int updateCount = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		ViewTopScrollerLinearLyt mTop = (ViewTopScrollerLinearLyt) findViewById(R.id.ll_top_hmaty);
		mTop.setAty(this);
		
		mFragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		
		//////////////////////////////////////////////
		// 处理收藏功能
		mCollectionFmg = new CollectionFragment();
		ft.add(R.id.ll_fragment_container_hmaty, mCollectionFmg);
		ft.hide(mCollectionFmg);
//		
		/////////////////////////////////////////////
		// 主显示fmg
		mHomeFmg = new HomeFragment(); 
		ft.add(R.id.ll_fragment_container_hmaty, mHomeFmg);// 在top布局xml里指定的容器控件上加载Fmg（控件，Fmg）
		ft.commit();
		
		///////////////////////////////////////////////
		// 处理bottom层布局的内容
		///////////////////////////////////////////////
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		// 屏幕宽度
		RelativeLayout.LayoutParams mLParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		mLParams.width = dm.widthPixels/4*3;
		// 根据屏幕宽设置bottom宽
		LinearLayout mBtmViewHmAty = (LinearLayout) findViewById(R.id.ll_bottom_hmaty);
		mBtmViewHmAty.setLayoutParams(mLParams);
		
		// bottom层ListView，主题处理
		mLVSubject = (ListView) findViewById(R.id.lv_subject_hmaty);
		
		// 请求bottom主题数据 
		new SubjectAsyncTask(this).execute(UrlModel.subjectUrl);
		
		
		////////////////////////////////////
		// 主页面板切换bar按钮
		////////////////////////////////////
		final RadioGroup barGroup = (RadioGroup)this.findViewById(R.id.rg_bar_hmaty);
		// 不用这一步的话，在xml为每个RadioButton设定id
		((RadioButton)barGroup.getChildAt(0)).setChecked(true);
		currentTag = 0;
		barGroup.setTag(1);
		((RadioButton)barGroup.getChildAt(0)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int tag = (Integer)barGroup.getTag();

				// 如果是再次点击的hm_btn,那么处理
				if(tag == 1){
					Toast.makeText(ContextApplication.context, "刷新~~~~~~~~~", 0).show();
					// TODO 访问网络刷新
					if(!mHomeFmg.isUpdating){	// 当前不刷新的时候，才执行刷新
						mHomeFmg.update();
					}
				}
				
				//////// 注意上下两个if的顺序  ///////
				
				// 按钮被新点击时，设为0。如果是新点击的hm_btn,那么设为1
				if(tag == 0){
					barGroup.setTag(1);
				} 
			}
		});
		
		///////////////////////    
		// 首先触发group的setOnCheckedChangeListener，才会继续触发btn的setOnClickListener
		///////////////////////
		
		barGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			/** checkedId是按钮默认在系统中的注册ID。动态 */
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// ViewTopScrollerLinearLyt，如果显示的是“首页”，才侧滑
				currentTag = Integer.valueOf(barGroup.findViewById(checkedId).getTag().toString());
				
				// 按钮被新点击时，设为0。配合hm_btn的再次点击
				barGroup.setTag(0);
				
				/**
				 * 首页 mHomeFmg
				 * 搜索 mSearchFmg
				 * 杂志 mJournalFmg
				 * 收藏 mCollectionAty
				 * 设置 mSettingFmg          */
				int tag = Integer.parseInt(findViewById(checkedId).getTag().toString());
				switch(tag){
					case 0:	// 首页 mHomeFmg
						setShowOrHiteFmg(0);
					break;
					case 1:	// 搜索 mSearchFmg
						setShowOrHiteFmg(1);
					break;
					case 2: // 杂志 mJournalFmg
						setShowOrHiteFmg(2);
					break;
					case 3: // 收藏 mCollectionAty
						setShowOrHiteFmg(3);
					break;
					case 4: // 设置 mSettingFmg
						setShowOrHiteFmg(4);
					break;
				}
			}
		});
		
	}// end onCreate()
	
	/**
	 * 隐藏全部后 根据指定位置显示Fmg
	 * @param tag
	 */
	private void setShowOrHiteFmg(int tag){
		if(mHomeFmg != null){
			FragmentTransaction ft = mFragmentManager.beginTransaction();
			ft.hide(mHomeFmg);
			ft.commit();
		}
		
		if(mSearchFmg != null){
			FragmentTransaction ft = mFragmentManager.beginTransaction();
			ft.hide(mSearchFmg);
			ft.commit();
		}
		
		if(mJournalFmg != null){
			FragmentTransaction ft = mFragmentManager.beginTransaction();
			ft.hide(mJournalFmg);
			ft.commit();
		}
		
		if(mSettingFmg!= null){
			FragmentTransaction ft = mFragmentManager.beginTransaction();
			ft.hide(mSettingFmg);
			ft.commit();
		}
		
		if(mCollectionFmg!= null){
			FragmentTransaction ft = mFragmentManager.beginTransaction();
			ft.hide(mCollectionFmg);
			ft.commit();
		}
		
		if(tag == 0){
//			if(mHomeFmg == null){
//				mHomeFmg = new HomeFragment();
//				FragmentTransaction ft = mFragmentManager.beginTransaction();
//				ft.add(R.id.ll_fragment_container_hmaty, mHomeFmg);
//				ft.commit();
//			}else{
				FragmentTransaction ft = mFragmentManager.beginTransaction();
				ft.show(mHomeFmg);
				ft.commit();
//			}
		}
		
		if(tag == 1){
			if(mSearchFmg== null){
				mSearchFmg = new SearchFragment();
				FragmentTransaction ft = mFragmentManager.beginTransaction();
				ft.add(R.id.ll_fragment_container_hmaty, mSearchFmg);
				ft.commit();
			}else{
				FragmentTransaction ft = mFragmentManager.beginTransaction();
				ft.show(mSearchFmg);
				ft.commit();
			}
		}
		
		if(tag == 2){
			if(mJournalFmg== null){
				mJournalFmg = new JournalFragment();
				FragmentTransaction ft = mFragmentManager.beginTransaction();
				ft.add(R.id.ll_fragment_container_hmaty, mJournalFmg);
				ft.commit();
			}else{
				FragmentTransaction ft = mFragmentManager.beginTransaction();
				ft.show(mJournalFmg);
				ft.commit();
			}
		}
		
		if(tag == 3){
//			if(mCollectionFmg== null){
//				mCollectionFmg = new CollectionFragment();
//				FragmentTransaction ft = mFragmentManager.beginTransaction();
//				ft.add(R.id.ll_fragment_container_hmaty, mCollectionFmg);
//				ft.commit();
//			}else{
				FragmentTransaction ft = mFragmentManager.beginTransaction();
				ft.show(mCollectionFmg);
				ft.commit();
//			}
		}
		
		if(tag == 4){
			if(mSettingFmg== null){
				mSettingFmg = new SettingFragment();
				FragmentTransaction ft = mFragmentManager.beginTransaction();
				ft.add(R.id.ll_fragment_container_hmaty, mSettingFmg);
				ft.commit();
			}else{
				FragmentTransaction ft = mFragmentManager.beginTransaction();
				ft.show(mSettingFmg);
				ft.commit();
			}
		}
	}
	
	/**
	 * bottom, SubjectAsyncTask执行结束后，发回主题集合 
	 */
	@Override
	public void setSbList(List<SubjectBean> sbList) {
		if(sbList == null){
			Toast.makeText(ContextApplication.context, "请求主题数据失败", 0).show();
			return;
		}
			
		subjectList = sbList;
		subjectList.add(0, new SubjectBean("0", "全部", true));
		
		mLVSubjectAdapter = new LVSubjectAdapter(HomeActivity.this, subjectList);
		mLVSubject.setAdapter(mLVSubjectAdapter);
		mLVSubject.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> view, View item, int index, long id) {

				// 之前被选中的条目id
				int oldSelectedIndex = mLVSubjectAdapter.getSelectedIndex();
				
				// 修改条目集合中条目bean状态
				subjectList.set(
						oldSelectedIndex, 
						new SubjectBean(
								subjectList.get(oldSelectedIndex).getCategoryCode(), 
								subjectList.get(oldSelectedIndex).getCategoryName(),
								false
								));	// 旧的
				subjectList.set(
						index, 
						new SubjectBean(
								subjectList.get(index).getCategoryCode(), 
								subjectList.get(index).getCategoryName(),
								true
								));	// 新的
				
				// 刷新适配器
				mLVSubjectAdapter.setSelectedIndex(index);
				mLVSubjectAdapter.notifyDataSetChanged();
			}
		});
	}
}
