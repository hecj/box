package com.boxamazing.service.sms.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;

public class AuthToken extends Model<AuthToken>{
	
	private static final long serialVersionUID = 1L;
	
	public static final AuthToken dao = new AuthToken();
	
	//根据token验证链接
	public AuthToken findByToken(String uuid){
		String sql = "select * from auth_token where  token = ? order by create_at desc";
		List<AuthToken> authToken = AuthToken.dao.find(sql,uuid);
		if(authToken.size()!=0){
			return authToken.get(0);
		}else{
			return null;
		}
	}
}
