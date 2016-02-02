package com.example.qiang_pc.newtalkpal.widget;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qiang_pc.newtalkpal.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;


public class AutoScrollViewPager extends ViewPager {

	// .xml
	public AutoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// 暴露添加图片地址的方法
	private List<String> imgurls = new ArrayList<String>();

	public void setImgUrls(List<String> imgurls) {
		this.imgurls = imgurls;
	}

	// 暴露添加本地图片的方法
	private List<Integer> imgIds = new ArrayList<Integer>();

	public void setImgIds(List<Integer> imgIds) {
		this.imgIds = imgIds;
	}

	// 暴露添加标题的方法
	private List<String> titles = null;
	private TextView titleView;

	public void setTitles(List<String> titles, TextView titleView) {
		this.titles = titles;
		this.titleView = titleView;
		this.titleView.setText(titles.get(0));
	}

	private int pageCount = 2;
	// 支持无限滑动
	private boolean isLooping = true;//默认支持无限滑动

	// 设置是否可以循环
	public void setLooping(boolean flag) {
		isLooping = flag;
	}
	// 暴露设置轮播间隔时间的方法
	private long interval=2000;//默认是2秒
	public void setInterval(long interval){
		this.interval=interval;
	}
	// 自动轮播功能
	private Handler handler = new Handler() {
		// 回调函数: 1.重写 2.系统或其它对象调用
		// on
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				Log.i("wzx", "切换到下一页");
				// 切换到下一页
				int curr = getCurrentItem();
				++curr;
				setCurrentItem(curr);// 显示指定页面
				// 重写给自己发一个消息
				// Message msg=new Message();
				Message msg2 = handler.obtainMessage();// obtain=get 是一种优化写法
				// 内部查找可重用的Message 如果有就重用 没有呢 才创建新的。
				msg2.what = 1;
				handler.sendMessageDelayed(msg2, interval);// ---->handleMessage
				// 循环发送息
			}
		};
	};

	// 支持自动播放
	// 暴露给外面调用。
	public void startScroll() {
		// 3000
		// Message msg=new Message();
		if(imgurls.size()>1||imgIds.size()>1){//如果只有一张图片就不滚动
			Message msg = handler.obtainMessage();// obtain=get 是一种优化写法
			// 内部查找可重用的Message 如果有就重用 没有呢 才创建新的。
			msg.what = 1;
			handler.sendMessageDelayed(msg, 3000);// ---->handleMessage
		}

	}

	/**
	 * 停止播放
	 */
	public void stopScroll() {
		// 清除所有消息 handleMessage就不能执行
		// 使用回调函数没有条件满足
		handler.removeCallbacksAndMessages(null);//

	}

	// 选择器:selector 管理素材的对象 根据 不同的状态显示不同的图片 press
	// select=true false
	// view.setSlected()
	private List<ImageView> dots = new ArrayList<ImageView>();

	public void init(int pageNumber, LinearLayout layoutDot) {
		// 3
		pageCount = pageNumber;
		// .xml
		// .java
		for (int i = 0; i < pageNumber; i++) {
			ImageView img = new ImageView(getContext());
			img.setBackgroundResource(R.drawable.dot_selector);
			img.setSelected(false);// 红
			// .xml layout_width layout_height
			// .java LinearLayout.LayoutParams 布局参数
			LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
					//
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			// .xml marginRight
			// .java rightMargin
			if(i<pageNumber-1){
				p.rightMargin = 15;
			}
			img.setLayoutParams(p);
			layoutDot.addView(img);
			dots.add(img);
		}
		// 设置内容
		PagerAdapter adpater = new ImageViewAdapter();
		this.setAdapter(adpater);// PageAdpater FragmentPageAdapter

		// ④　添加监听OnPageChangedListener
		// ⑤　自动滚动保持点的同步
		// ⑥　手动滚动时 停止滚动
		OnPageChangeListener listener = new MyOnPageChangeListener();
		this.setOnPageChangeListener(listener);// OnPageChangeListener监听滑动到第几页
		// 实现一开始能向左移动
		setCurrentItem((imgurls.size()>0?imgurls.size():imgIds.size())*1000);
		if(dots.size()>0){
			dots.get(0).setSelected(true);
		}
	}

	private class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		// 滑动第几页面
		@Override
		public void onPageSelected(int arg0) {
			// 旧点不亮
			dots.get(currPageIndex % pageCount).setSelected(false);
			// 新点高亮
			currPageIndex = arg0;
			dots.get(currPageIndex % pageCount).setSelected(true);
			if (titleView != null && titles != null) {
				titleView.setText(titles.get(currPageIndex % pageCount));
			}

		}

	}

	private int currPageIndex = 0;

	private class ImageViewAdapter extends PagerAdapter {

		// 页数
		@Override
		public int getCount() {
			if (isLooping) {
				return Integer.MAX_VALUE;
			} else {
				return pageCount;
			}
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		// 当前显示视图
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// 创建显示的页面 是一张图片
			SimpleDraweeView simpleDraweeView = new SimpleDraweeView(getContext());

			ViewPager.LayoutParams p = new ViewPager.LayoutParams();
			p.width = ViewPager.LayoutParams.MATCH_PARENT;
			p.height = ViewPager.LayoutParams.MATCH_PARENT;
			container.addView(simpleDraweeView, p);
			if (imgurls != null && imgurls.size() > 0) {
				simpleDraweeView.setImageURI(Uri.parse(imgurls.get(position % pageCount)));
			} else if (imgIds != null && imgIds.size() > 0) {// 设置本地图片
				simpleDraweeView.setBackgroundResource(imgIds.get(position % pageCount));
			}
			// OnTouchListener什么是touch事件?
			// touch由 ACTION_DOWN ACTION_MOVE.... ACTION_UP ACTION_CANCLE
			// ACTION_DOWN 调用停止 ACTION_UP ACTION_CANCLE 结束
			OnTouchListener listener = new MyOnTouchListener();
			simpleDraweeView.setOnTouchListener(listener);
			return simpleDraweeView;
		}

		private class MyOnTouchListener implements OnTouchListener {

			private int downX = 0;
			private long downTime = 0;

			// MotionEvent 归属地位置改变 小火箭
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {// 获取动作
				case MotionEvent.ACTION_DOWN:// 按下
					stopScroll();
					downX = (int) event.getX();// 获取按下位
					downTime = System.currentTimeMillis();// 保存按下时间
					Log.i("wzx", "ACTION_DOWN");
					break;
				case MotionEvent.ACTION_MOVE:// 移动
					Log.i("wzx", "ACTION_MOVE");
					break;
				case MotionEvent.ACTION_UP:// 提起
					Log.i("wzx", "ACTION_UP");
					int upX = (int) event.getX();
					if (downX == upX
							&& System.currentTimeMillis() - downTime < 300) {

						// 使用Command设计模式 可以增加一个接口作监听器
						// 1.创建interface
						// 2.抽象方法 (抽取代码 去掉方法体)
						// 3.添加监听器
						// 4.监听器是要被调用。
						// 让开发者把代码写监听器里（控件外边）
						// onItemClick();

						// 获取当前的position
						int currentItem = getCurrentItem();
						if (listener != null) {
							listener.onItemClick(currentItem);// 方法内部的代码
						}
					}
					startScroll();
					break;
				case MotionEvent.ACTION_CANCEL:// 取消
					Log.i("wzx", "ACTION_CANCEL");
					startScroll();
					break;
				}
				return true;// 返回值 处理这个事件就返回true
			}

		}

		// 移除显示

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((ImageView) object);
		}

	}

	float x = 0;
	float y = 0;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {

		case MotionEvent.ACTION_DOWN:
			Log.i("tt", "ACTION_DOWN");
			// 点击下去的时候获取坐标，父控件下发事件
			x = ev.getX();
			y = ev.getY();
			getParent().requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i("tt", "ACTION_MOVE");
			float dx = ev.getX() - x;
			float dy = ev.getY() - y;
			if (Math.abs(dx) > Math.abs(dy)) {// 左右滑动
				getParent().requestDisallowInterceptTouchEvent(true);
			} else {// 上下滑动
				Log.i("tag", "父控件");
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;

		}
		return super.dispatchTouchEvent(ev);
	}

	public static interface OnViewItemClickListener {
		// 使用Command设计模式 可以增加一个接口作监听器
		// 1.创建interface
		// 2.抽象方法 (抽取代码 去掉方法体)
		public void onItemClick(int position);
		// 3.添加监听器
		// 4.监听器是要被调用。
		// 让开发者把代码写监听器里（控件外边）
	}

	private OnViewItemClickListener listener;

	public void setOnViewItemClickListener(OnViewItemClickListener listener) {
		this.listener = listener;
	}

}
