package com.ssi.dao;

import javax.annotation.Resource;

import com.ibatis.sqlmap.client.SqlMapClient;

public class SqlMapClientUtil {
	
	@Resource(name = "sqlMapClient")
	protected SqlMapClient sqlMapClient;
}
