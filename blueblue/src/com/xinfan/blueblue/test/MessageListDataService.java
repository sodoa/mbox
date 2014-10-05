package com.xinfan.blueblue.test;

import java.util.ArrayList;
import java.util.List;

import com.xinfan.blueblue.activity.HuiHua;
import com.xinfan.blueblue.vo.ContactVo;

public class MessageListDataService {

	public static List<HuiHua> getData(int pageSize, int page) {
		List<HuiHua> list = new ArrayList<HuiHua>();
		int index = (pageSize * (page - 1) + 1);
		int max = pageSize * page;
		for (int i = index; i <= max; i++) {
			HuiHua map = new HuiHua();
			map.setName1("姓名:" + i);
			map.setLastTime("时间：" + i);
			map.setLastContent("内容:" + i);
			list.add(map);
		}
		return list;
	}

	public static List<ContactVo> getContactData(int pageSize, int page) {
		List<ContactVo> list = new ArrayList<ContactVo>();
		int index = (pageSize * (page - 1) + 1);
		int max = pageSize * page;
		for (int i = index; i <= max; i++) {
			ContactVo map = new ContactVo();
			map.setId(String.valueOf(index));
			map.setAccountId("帐号:" + i);
			map.setMark("备注:" + i);
			map.setUserid("userid" + i);
			map.setUsername("用户名:" + i);
			map.setCredit("10");
			list.add(map);
		}
		return list;
	}

}
