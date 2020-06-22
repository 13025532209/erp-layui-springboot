package com.example.demo;

import com.example.demo.dao.MenuDao;
import com.example.demo.entity.Menu;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootLayuiErpApplicationTests {
	@Autowired
	private MenuDao menuDao;
	@Test
	public void contextLoads() {
		if(equalsStr("123","12322")){
			System.out.println("相等");
		}
	}

	private boolean equalsStr(String str1, String str2){
		if(StringUtils.isEmpty(str1) && StringUtils.isEmpty(str2)){
			return true;
		}
		if(!StringUtils.isEmpty(str1) && str1.equals(str2)){
			return true;
		}
		return false;
	}

}
