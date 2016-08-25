package com.boxamazing.webfront.controller.user;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boxamazing.service.common.StringUtil;
import com.boxamazing.service.user.model.ReceiveAddress;
import com.boxamazing.service.user.model.User;
import com.boxamazing.webfront.common.BaseController;
import com.boxamazing.webfront.interceptor.LoginInterceptor;
import com.boxamazing.webfront.util.CheckNum;
import com.boxamazing.webfront.util.Code;
import com.boxamazing.webfront.util.ResultJson;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;

/**
 * 收货地址 by HECJ
 */
@Before({ LoginInterceptor.class })
public class ReceiveAddressController extends BaseController {

	private static final Log log = LogFactory.getLog(ReceiveAddressController.class);

	/**
	 * 添加收货地址 by HECJ
	 */
	@Before(POST.class)
	public void add() {

		User user = UserUtil.getUser(getSession());
		long user_id = user.getLong("id");
		String name = getPara("name");
		String phone = getPara("phone");
		String province = getPara("province");
		String city = getPara("city");
		String area = getPara("area");
		String zipcode = getPara("zipcode");
		String detail_address = getPara("detail_address");

		try {

			if (StringUtil.isNullOrEmpty(name)) {
				renderJson(new ResultJson(-1l, "请输入姓名"));
				return;
			}
			if (StringUtil.isNullOrEmpty(phone)) {
				renderJson(new ResultJson(-2l, "请输入联系方式"));
				return;
			}
			if (StringUtil.isNullOrEmpty(province)) {
				renderJson(new ResultJson(-3l, "请选择省份"));
				return;
			}
			if (StringUtil.isNullOrEmpty(city)) {
				renderJson(new ResultJson(-4l, "请选择城市"));
				return;
			}
			if (StringUtil.isNullOrEmpty(area)) {
				renderJson(new ResultJson(-5l, "请选择县城"));
				return;
			}
			if (name.length() < 2 && name.length() > 5) {
				renderJson(new ResultJson(-6l, "姓名长度在2-5个字符"));
				return;
			}
			if (!CheckNum.isPhone(phone)) {
				renderJson(new ResultJson(-7l, "手机号码格式不正确"));
				return;
			}
			if (detail_address.length() < 5) {
				renderJson(new ResultJson(-8l, "详细地址最少5个字符"));
				return;
			}
			if (detail_address.length() > 300) {
				renderJson(new ResultJson(-9l, "详细地址不能超过300个字符"));
				return;
			}

			List<ReceiveAddress> receiveAddressList = ReceiveAddress.dao.findListByUserId(user_id);

			ReceiveAddress receiveAddress = new ReceiveAddress();
			receiveAddress.put("user_id", user_id);
			receiveAddress.put("name", name);
			receiveAddress.put("phone", phone);
			receiveAddress.put("province", province);
			receiveAddress.put("city", city);
			receiveAddress.put("area", area);
			receiveAddress.put("zipcode", zipcode);
			receiveAddress.put("detail_address", detail_address);
			if (receiveAddressList == null || receiveAddressList.size() == 0) {
				receiveAddress.put("default", 1);
			} else {
				receiveAddress.put("default", 0);
			}
			receiveAddress.put("is_delete", 0);
			receiveAddress.put("create_at", System.currentTimeMillis());
			receiveAddress.put("update_at", System.currentTimeMillis());
			receiveAddress.save();

			renderJson(new ResultJson(200l, receiveAddress, "success"));

		} catch (Exception e) {
			log.error(e.getMessage() + ",user_id:" + user_id);
			e.printStackTrace();
			renderJson(new ResultJson(-100000l, e.getMessage()));
		}
	}

	/**
	 * 查询收货地址
	 */
	public void query() {

		User user = UserUtil.getUser(getSession());
		try {
			long user_id = user.getLong("id");
			long receiveAddressId = getParaToLong(0);
			log.info("user_id{},receiveAddressId{}:" + user_id + "," + receiveAddressId);
			ReceiveAddress receiveAddress = ReceiveAddress.dao.findById(receiveAddressId);
			if (receiveAddress.getLong("user_id").compareTo(user_id) == 0) {
				renderJson(new ResultJson(200l, receiveAddress, "success"));
			} else {
				log.info("未查到用户收货地址receiveAddressId：" + receiveAddressId);
				renderJson(new ResultJson(-1l, "not found"));
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			renderJson(new ResultJson(-100000l, e.getMessage()));
		}
	}

	/**
	 * 修改收货地址 by HECJ
	 */
	@Before(POST.class)
	public void update() {

		User user = UserUtil.getUser(getSession());
		long user_id = user.getLong("id");
		long id = getParaToLong("id");
		String name = getPara("name");
		String phone = getPara("phone");
		String province = getPara("province");
		String city = getPara("city");
		String area = getPara("area");
		String zipcode = getPara("zipcode");
		String detail_address = getPara("detail_address");

		try {

			if (StringUtil.isNullOrEmpty(name)) {
				renderJson(new ResultJson(-1l, "请输入姓名"));
				return;
			}
			if (StringUtil.isNullOrEmpty(phone)) {
				renderJson(new ResultJson(-2l, "请输入联系方式"));
				return;
			}
			if (StringUtil.isNullOrEmpty(province)) {
				renderJson(new ResultJson(-3l, "请选择省份"));
				return;
			}
			if (StringUtil.isNullOrEmpty(city)) {
				renderJson(new ResultJson(-4l, "请选择城市"));
				return;
			}
			if (StringUtil.isNullOrEmpty(area)) {
				renderJson(new ResultJson(-5l, "请选择县城"));
				return;
			}
			if (name.length() < 2 && name.length() > 5) {
				renderJson(new ResultJson(-6l, "姓名长度在2-5个字符"));
				return;
			}
			if (!CheckNum.isPhone(phone)) {
				renderJson(new ResultJson(-7l, "手机号码格式不正确"));
				return;
			}
			if (detail_address.length() < 5) {
				renderJson(new ResultJson(-8l, "详细地址最少5个字符"));
				return;
			}
			if (detail_address.length() > 300) {
				renderJson(new ResultJson(-9l, "详细地址不能超过300个字符"));
				return;
			}

			ReceiveAddress receiveAddress = ReceiveAddress.dao.findById(id);
			receiveAddress.set("name", name);
			receiveAddress.set("phone", phone);
			receiveAddress.set("province", province);
			receiveAddress.set("city", city);
			receiveAddress.set("area", area);
			receiveAddress.set("zipcode", zipcode);
			receiveAddress.set("detail_address", detail_address);
			receiveAddress.set("update_at", System.currentTimeMillis());
			receiveAddress.update();

			renderJson(new ResultJson(200l, receiveAddress, "success"));

		} catch (Exception e) {
			log.error(e.getMessage() + ",user_id:" + user_id);
			e.printStackTrace();
			renderJson(new ResultJson(-100000l, e.getMessage()));
		}
	}

	/**
	 * 设为默认地址
	 * 
	 * @author XuXD
	 * @date 2015-11-8
	 */
	public void setDefault() {
		// 获取用户id
		User user = UserUtil.getUser(getSession());
		if (null == user) {
			renderJson(new ResultJson(Code.F_1, "请登录"));
			return;
		}
		long userId = user.getLong("id");

		// 获取设置地址id
		long defaultId = getParam(0, -1L);

		try {
			// 获取用户地址
			List<ReceiveAddress> addressList = ReceiveAddress.dao.findListByUserId(userId);

			// 设置地址为默认
			for (ReceiveAddress receiveAddress : addressList) {
				// 原默认地址置0
				if (receiveAddress.getInt("default") == 1) {
					receiveAddress.set("default", 0);
					receiveAddress.update();
				}
				// 设置默认地址置1
				if (receiveAddress.getLong("id") == defaultId) {
					receiveAddress.set("default", 1);
					receiveAddress.update();
				}
			}

			// 返回结果
			renderJson(new ResultJson(Code.S_200, "success"));
			return;

		} catch (Exception e) {
			log.error("设置默认地址异常[userId:" + userId + ",defaultId:" + defaultId + "]", e);
		}
		renderJson(new ResultJson(Code.F_100000, "设置失败"));
	}

	/**
	 * 删除地址
	 * 
	 * @author XuXD
	 * @date 2015-11-9
	 */
	public void del() {
		// 获取删除地址id
		long delId = getParam(0, -1L);

		try {
			// 删除地址
			ReceiveAddress.dao.findById(delId).set("is_delete", 1).update();

			// 返回结果
			renderJson(new ResultJson(Code.S_200, "success"));
			return;
		} catch (Exception e) {
			log.error("删除地址异常[delId:" + delId + "]", e);
		}
		renderJson(new ResultJson(Code.F_100000, "删除失败"));
	}
}
