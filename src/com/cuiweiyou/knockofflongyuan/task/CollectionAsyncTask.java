package com.cuiweiyou.knockofflongyuan.task;

import java.util.List;

import android.os.AsyncTask;

import com.cuiweiyou.knockofflongyuan.back.ICollectionListBack;
import com.cuiweiyou.knockofflongyuan.bean.CollectionBean;
import com.cuiweiyou.knockofflongyuan.util.ColSQLiteUtil;
import com.cuiweiyou.knockofflongyuan.util.ContextApplication;

/**
 * 收藏页面请求收藏记录<br/>
 * 读sqlite
 * @author Administrator
 */
public class CollectionAsyncTask extends AsyncTask< Void, Void, List<CollectionBean> > {
	
	private ICollectionListBack back;

	public CollectionAsyncTask(ICollectionListBack back){
		this.back = back;
	}

	@Override
	protected List<CollectionBean> doInBackground(Void... params) {

		return 
			new ColSQLiteUtil(
				ContextApplication.context, 
				null, 
				null, 
				1)
			.selectAll();
		
	}

	@Override
	protected void onPostExecute(List<CollectionBean> result) {
		super.onPostExecute(result);
		
		back.setColsList(result);
	}
}
