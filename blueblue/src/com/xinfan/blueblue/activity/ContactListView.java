package com.xinfan.blueblue.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.xinfan.blueblue.test.MessageListDataService;

public class ContactListView extends ListView implements OnScrollListener, OnItemClickListener {

	ArrayList list = new ArrayList();

	Contact2Adapter ad;
	
	Context context;

	// 底部footer
	View footer;
	// 当前页数
	int page = 0;
	// 每页显示数
	int pageSize = 10;
	// 是否加载数据中
	boolean isLoading = false;
	// Toast显示状态
	boolean isToast = false;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if (msg.what == 1) {
				// 得到消息中的数据

				List addList = (List) msg.obj;
				if (addList == null || addList.isEmpty()) {
					Toast.makeText(ContactListView.this.getContext(), "没有更多的数据了...", 0).show();
				} else {
					list.addAll(addList);
					// 告诉适配器，数据变化了，从新加载listview
					ad.notifyDataSetChanged();
					// 当前页数增加
					page++;
				}

				isLoading = false;
				// 移除底部footer
				ContactListView.this.removeFooterView(footer);
				ContactListView.this.setVisibility(View.VISIBLE);

			} else if (msg.what == 0) {
				// 设置加载中为false
				isToast = false;
			}
		}
	};

	public ContactListView(Context context) {
		super(context);
	}

	public ContactListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ContactListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = (ArrayList)list;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void loadData(){
		//list = (ArrayList) MessageListDataService.getData(pageSize, 1);
		ad = new Contact2Adapter(this.context, list);
		
		addFooterView(footer);
		setAdapter(ad);
		//removeFooterView(footer);
		setOnItemClickListener(this);
		setOnScrollListener(this);
		getFirstData();
		
		
	}
	
	private void getFirstData(){
		
		isLoading = true;
		
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				List addList = MessageListDataService.getData(pageSize, 1);
				handler.sendMessage(handler.obtainMessage(1, addList));
			}
		}.start();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// 如果正在加载，就return，防止加载多次
		if (isLoading)
			return;
		// 得到listview中显示在界面底部的id
		int lastItemid = getLastVisiblePosition();
		// 如果是listview中显示在界面底部的id=滚动条中Item总数，说明滑动到底部了，并且当前页<=总页数
		if ((lastItemid + 1) == totalItemCount) {
			// 设置正在加载中
			isLoading = true;
			if (totalItemCount > 0) {
				// 现在底部footer
				addFooterView(footer);
				new Thread() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						List list = MessageListDataService.getData(pageSize, page + 1);
						handler.sendMessage(handler.obtainMessage(1, list));
					}
				}.start();
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent = new Intent();
		intent.setClass(MainActivity.instance, ContactInfoActivity.class);
		this.getContext().startActivity(intent);
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	public View getFooter() {
		return footer;
	}

	public void setFooter(View footer) {
		this.footer = footer;
	}
	

	private ArrayList<HuiHua> getHuahui() {
		ArrayList<HuiHua> hhList = new ArrayList<HuiHua>();
		HuiHua h1 = new HuiHua();
		h1.setTxPath(R.drawable.icon + "");
		h1.setName1("Ф��");
		h1.setLastContent("����Ψһһ���������");
		h1.setLastTime("���� 18:00");

		HuiHua h2 = new HuiHua();
		h2.setTxPath(R.drawable.xiaohei + "");
		h2.setName1("С��");
		h2.setLastContent("�Ҵ����:�ĺڰ��У���ϲ�����ɹ�������");
		h2.setLastTime("���� 17:40");

		HuiHua h3 = new HuiHua();
		h3.setTxPath(R.drawable.searadd_icon + "");
		h3.setName1("ɵ�Ʋ���");
		h3.setLastContent("ɵ�Ʋ��ޣ�վ��4�¸ҵ�ߣ");
		h3.setLastTime("���� 17:00");

		HuiHua h4 = new HuiHua();
		h4.setTxPath(R.drawable.voip_camerachat + "");
		h4.setName1("���ٵ�����");
		h4.setLastContent("�Ӵ˲��ٵ����ޣ���Ҫ������");
		h4.setLastTime("���� 16:22");

		HuiHua h5 = new HuiHua();
		h5.setTxPath(R.drawable.headshow2 + "");
		h5.setName1("��������");
		h5.setLastContent("�紵�ú����£���Ʈ�����ϱߵ����");
		h5.setLastTime("���� 16:11");

		HuiHua h6 = new HuiHua();
		h6.setTxPath(R.drawable.headshow3 + "");
		h6.setName1("EatEvery");
		h6.setLastContent("Don't look me, I will eat you, Are you know");
		h6.setLastTime("���� 15:08");

		HuiHua h7 = new HuiHua();
		h7.setTxPath(R.drawable.headshow4 + "");
		h7.setName1("����");
		h7.setLastContent("û����ô��Čţ��Ͳ�ҪװB");
		h7.setLastTime("���� 15:01");

		HuiHua h8 = new HuiHua();
		h8.setTxPath(R.drawable.headshow5 + "");
		h8.setName1("������");
		h8.setLastContent("�Ҿ�����ôһ���ˣ�����ϲ��һ���ˣ������ǲ���һ����");
		h8.setLastTime("���� 14:50");

		HuiHua h9 = new HuiHua();
		h9.setTxPath(R.drawable.headshow6 + "");
		h9.setName1("Diacker");
		h9.setLastContent("this is very good fill");
		h9.setLastTime("���� 14:00");

		HuiHua h0 = new HuiHua();
		h0.setTxPath(R.drawable.headshow1 + "");
		h0.setName1("����漱");
		h0.setLastContent("���Ǹ�ϲ���͵��ˣ���������һ��Ҫ�������ҵ����֣��ٸ���˵��");
		h0.setLastTime("���� 12:00");

		hhList.add(h1);
		hhList.add(h2);
		hhList.add(h3);
		hhList.add(h4);
		hhList.add(h5);
		hhList.add(h6);
		hhList.add(h7);
		hhList.add(h8);
		hhList.add(h9);
		hhList.add(h0);
		return hhList;
	}

}
