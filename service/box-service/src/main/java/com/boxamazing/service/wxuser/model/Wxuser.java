package com.boxamazing.service.wxuser.model;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.Date;
import java.util.Map;

@SuppressWarnings("serial")
public class Wxuser extends Model<Wxuser> {
    public static final Wxuser dao = new Wxuser();

    public Page<Wxuser> _page(int pn, int ps) {
        return dao.paginate(pn, ps, "select *", "from wxuser order by id desc");
    }

    public int deleteByIds(String... ids) {
        String[] ay = ArrayUtil.getPrePareArray(ids.length);
        String str = StringUtil.join(ay, ",");
        return Db.update("delete from wxuser where id in (" + str + ")", ids);
    }

    /**
     * 通过OpenId查找weixin用户
     *
     * @param openId
     * @return
     */
    public Wxuser findFirstByOpenId(String openId) {
        return Wxuser.dao.findFirst("select * from wxuser where openid=?", openId);
    }

    /**
     * 通过用户wx基本信息,创建weixin用户,写wxuser表.
     *
     * @param openid
     * @param nickname
     * @param sex
     * @param headimgurl
     * @return
     */
    public boolean createWxuserByUserInfo(Object openid, Object nickname, Object sex, Object headimgurl) {
        Date now = new Date();
        return new Wxuser()
                .set("openid", openid)
                .set("nickname", nickname)
                .set("sex", sex)
                .set("type", "wx")
                .set("img", headimgurl)
                .set("loginTime", now)
                .set("updateTime", now)
                .set("createTime", now)
                .save();
    }

    /**
     * 更新weixin用户信息.更新表wxuser.
     *
     * @param openid
     * @param nickname
     * @param sex
     * @param headimgurl
     * @return
     */
    public boolean updateWxuserInfo(Object openid, Object nickname, Object sex, Object headimgurl) {

        Date now = new Date();
        return new Wxuser()
                .set("openid", openid)
                .set("nickname", nickname)
                .set("sex", sex)
                .set("type", "wx")
                .set("img", headimgurl)
                .set("loginTime", now)
                .set("updateTime",now)
                .update();
    }
}