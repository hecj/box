package com.boxamazing.service.balance_reg.model;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

public class Balance_reg extends Model<Balance_reg>{

	public static final Balance_reg dao = new Balance_reg();
	
	  /**
     * 基本分页方法,只显示充值记录
	 * @throws UnsupportedEncodingException 
     */
    public Page<Balance_reg> _page(int pn, int ps)  {   
    	return dao.paginate(pn, ps, "select *", "from balance_reg where content = ? order by id desc","充值");     
    }
    
    public Page<Balance_reg> _page(int pn, int ps,String way,int a){
    	if(a==1){
    		return dao.paginate(pn, ps, "select *", "from balance_reg where content = ? and status = ? order by id desc",way,0);  
    	}else{
    		return dao.paginate(pn, ps, "select *", "from balance_reg where username = ? and content = ? order by id desc",way,"充值");  
    	}
    }
    
    public Page<Balance_reg> _page(int pn, int ps,int a)  {      	
    	return dao.paginate(pn, ps, "select *", "from balance_reg where content = ? and status = ? order by id desc","提现",a); 
    }
   
    /**
     * 根据id删除多个对象
     *
     * @param ids
     * @return
     */
    public int deleteByIds(String... ids) {
        String[] ay = ArrayUtil.getPrePareArray(ids.length);
        String str = StringUtil.join(ay, ",");
        return Db.update("delete from balance_reg where id in (" + str + ")", ids);
    }
    
    /*
     * 查询全部
     */
    
    public List<Balance_reg> findAll(String id,String s){
    	List<Balance_reg> list = new ArrayList();
    	//查询所有记录
    	if(s.equals("")){
    		list = Balance_reg.dao.find("select * from balance_reg where username = ? order by regtime desc",id);
    	}else{
    		//查询提现记录
    		list = Balance_reg.dao.find("select * from balance_reg where username = ? and content like ? order by regtime desc",id,s+"");
    	}
    	return list;
    }
    
    //根据用户名查询
    public Balance_reg findUser(String id){
    	return Balance_reg.dao.findFirst("select * from balance_reg where username = ? order by regtime desc",id);
    }
    
    //生成单号
    public String newBid(String way,Date date,int change,String username){
    	int random = (int)(Math.random()*99999999+9999999);
		char[] abc=date.toLocaleString().toCharArray();
		
		StringBuffer sb = new StringBuffer();
		for(char s:abc){
			if(s>='0'&&s<='9'){
				sb.append(s);
			}
			
			if(s=='-'||s==' '){
				sb.append(0);
			}
		}

		return way+""+sb.toString()+change+random;
    }
 
}
