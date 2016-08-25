package com.boxamazing.service.openiduid.model;

import com.boxamazing.service.common.ArrayUtil;
import com.boxamazing.service.common.StringUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

import java.util.Date;

/**
 * OpenIdUid
 * Created by pchome on 2015/8/4.
 */
@SuppressWarnings("serial")
public class Openiduid extends Model<Openiduid> {
    public static final Openiduid dao = new Openiduid();
    public Page<Openiduid> _page(int pn,int ps) {
        return dao.paginate(pn, ps, "select *", "from openiduid order by id desc");
    }

    public int deleteByIds(String... ids) {
        String[] ay = ArrayUtil.getPrePareArray(ids.length);
        String str = StringUtil.join(ay,",");
        return Db.update("delete from openiduid where id in (" + str + ")", ids);
    }

    /**
     * 创建新的关联openid <-> uid
     * @param openId
     * @param uid
     * @return
     */
    public boolean createOpeniduid(String openId, String uid) {
        Date now = new Date();
        return new Openiduid()
                .set("openid", openId)
                .set("uid", uid)
                .set("createTime", now)
                .set("updateTime", now)
                .save();
    }
}
