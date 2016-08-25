package com.boxamazing.service.pay.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jfinal.plugin.activerecord.Model;

/**
 * 提现明细表
 */
public class WithdrawalsDetail extends Model<WithdrawalsDetail> {

	private static final Log log = LogFactory.getLog(WithdrawalsDetail.class);

	public static final WithdrawalsDetail dao = new WithdrawalsDetail();

	public List<WithdrawalsDetail> findAllDetail(Long withdrawalsId,String batch_no) {
		log.info("获取提现详细表的id为：" + withdrawalsId+",批次号为："+batch_no);
		List<WithdrawalsDetail> list = new ArrayList<WithdrawalsDetail>();
		String sql = "select w.*,r.buyer_email,r.buyer_id from withdrawals_detail w left join recharge_detail_record r on (w.order_num = r.order_num) where w.status = 4 and w.withdrawals_id = ? and w.batch_no = ? order by w.withdrawals_success_at desc , w.create_at desc ";
		list = WithdrawalsDetail.dao.find(sql, withdrawalsId,batch_no);
		return list != null ? list : null;
	}
	
	
	/**
	 * 根据批次号查询提现详细数据
	 * by hecj
	 */
	public List<WithdrawalsDetail> findWithdrawalsDetailByBatchNo(String batch_no){
		
		StringBuffer querySQL = new StringBuffer("select wd.* from withdrawals_detail wd where 1=1 ");
		querySQL.append(" and wd.batch_no = "+batch_no);
		querySQL.append(" order by wd.id desc ");
		log.info(" 根据批次号查询提现详细数据 condition{} : " + querySQL.toString());
		try {
			List<WithdrawalsDetail> list = dao.find(querySQL.toString());
			return list;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据批次号和交易号查询提现详细数据
	 * by hecj
	 */
	public WithdrawalsDetail findWithdrawalsDetailByBatchNoAndTradeNo(String batch_no,String trade_no){
		
		StringBuffer querySQL = new StringBuffer("select wd.* from withdrawals_detail wd where 1=1 ");
		querySQL.append(" and wd.batch_no = "+batch_no);
		querySQL.append(" and wd.trade_no = "+trade_no);
		querySQL.append(" order by wd.id desc ");
		log.info(" 根据批次号和交易号查询提现详细数据 condition{} : " + querySQL.toString());
		try {
			List<WithdrawalsDetail> list = dao.find(querySQL.toString());
			if(list.size() > 0){
				return list.get(0);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
