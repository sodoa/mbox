package com.xinfan.blueblue.test;

import java.util.ArrayList;
import java.util.List;

import com.xinfan.blueblue.activity.HuiHua;

public class MessageListDataService {

	public static List<HuiHua> getData(int pageSize,int page){
		List<HuiHua> list = new ArrayList<HuiHua>();
		int index = (pageSize*(page-1)+1);
		int max = pageSize * page;
		for(int i = index; i <= max; i++){
			HuiHua map = new HuiHua();
			map.setName1( "姓名:"+i);
			map.setLastTime("时间："+i);
			map.setLastContent("内容:"+i);
			list.add(map);
		}
		return list;
	}
}
