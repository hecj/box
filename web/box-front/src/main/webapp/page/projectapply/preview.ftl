<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta name="renderer" content="webkit">
    <title>商品详情</title>
    <META http-equiv=Content-Type content="text/html; charset=UTF-8">
    <#include "/page/common/base/include.ftl">
</head>
<body>

<#include "/page/common/head.ftl" />

<br/><br/><br/><br/>

<ul>
    <li><h1>标题: ${p.name}</h1>
    <li>发布人: ${p.uid}
    <li>发布时间:${p.createtime!''}
    <li>开始时间:${p.starttime!''}
    <li>截至时间:${p.expirestime!''}
    <li>描述:${p.desc}
    <li>状态:<!--${p.status}-->${p.statusText}
</ul>

<br/><br/><br/><br/>

<p>

    <img src="${p.pic1!''}"/>
    <img src="${p.pic2!''}"/>
    <img src="${p.pic3!''}"/>
    <img src="${p.pic4!''}"/>
    <img src="${p.pic5!''}"/>

    <br/>
    封面图:
    <img src="${p.pic0!''}"/>
</p>

<br/><br/><br/><br/>

<p> 感谢信: ${p.letter!''} </p>

<p> 最新公告: ${p.notice!''} </p>

<br/><br/><br/><br/>

<ul>
    <li> 关注: ${subscribeNum!''}
        <a href="/sub/isSub/${p.id}-${uid!''}">用户是否关注</a>
        <a href="/sub/sub/${p.id}-${uid!''}">关注</a>
        <a href="/sub/getCount/${p.id}">已关注数量</a>
    <li> 支持: ${fundpcount!''}
    <li> 转发到各平台:
        <a target="_blank"
           href="http://service.weibo.com/share/share.php?url=${snsUrl}&title=${snsDesc}&source=京东金融&sourceUrl=${snsUrl}&pic=${snsPic}">分享到新浪微博</a>
        <a target="_blank"
           href="http://share.v.t.qq.com/index.php?c=share&a=index&title=${snsDesc}&site=http://www.duomeidai.com/&pic=${snsPic}&url=${snsUrl}&title=${snsTitle}&pics=${snsPic}&summary=${snsDesc}">分享到腾讯微博</a>
        <a target=_blank
           href="http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=${snsUrl}&title=${snsTitle}&pics=${snsPic}&summary=${snsDesc}">分享到QZONE</a>
        <a target=_blank
           href="http://www.douban.com/share/service?image=${snsPic}&href=${snsUrl}&name=${snsDesc}">分享到豆瓣</a>
        <a target=_blank
           href="http://widget.renren.com/dialog/share?resourceUrl=${snsUrl}&amp;title=${snsTitle}&amp;images=${snsPic}&amp;description=${snsDesc}">分享到人人</a>
</ul>

<br/><br/><br/><br/>

<ul>
    <li> 已筹到: ${p.fundnow}
    <li> 目标: ${p.fundgoal}
    <li> 完成: ${p.progress} %
    <li> 剩余天数: ${p.beLeftDays}天
</ul>

<br/><br/><br/><br/>

<h3> -项目介绍- </h3>
${p.desc}
<h3> -项目介绍- </h3>
${p.context}

<br/><br/><br/><br/>

<p>

<h3>-项目进展-</h3>
<ul>
<#list notice_historyList as n>
<li> ${n.time} - ${n.info}
</#list>
</ul>
</p>

<br/><br/><br/><br/>

<h3>-评论-</h3>
<ul>
<#list comments as c>
    <li>
        用户:${c.uid} <br/>
        发布时间: ${c.create_time} <br/>
    ${c.content} <br/>
        <a href="/comment/support/${c.id}-${uid!''}" target="_blank">赞(${c.support_num})</a>
        <a href="/comment/getReply/${c.id}" target="_blank">评论(${c.reply_num})</a>

        <form action="/comment/reply">
            <input type="hidden" name="pid" value="${pid}"/> <br/>
            <input type="hidden" name="uid" value="${uid!''}"/> <br/>
            <input type="hidden" name="parentid" value="${c.id}"/> <br/>
            <textarea name="content" rows="3" cols="20"></textarea> <br/>
            <input type="submit" value="回复"/>
        </form>
        <br/><br/>
    </li>
</#list>

</ul>
<h5>发表评论</h5>

<form action="/comment/post">
    <input type="hidden" name="pid" value="${pid}"/> <br/>
    <input type="hidden" name="uid" value="${uid!''}"/> <br/>
    <textarea name="content" rows="3" cols="20"></textarea> <br/>
    <input type="submit" value="发表评论"/>
</form>


<br/><br/><br/><br/>

<h3>-支持-</h3>
<a href="/support/getSupportList/${pid}" target="_blank">投资者名单</a>
<ul>
<#if supports ??>
    <#list supports as s>
    <li><b>${s.nickname}</b> 支持了 <b>${s.fund}</b>元
    </#list>
</#if>
</ul>
XXX

<hr/>

<br/><br/><br/><br/>
-php- <br/>
发布人: 昵称 ${p.uid} <br/>
发私信:<br/>
加关注:<br/>

<br/><br/><br/><br/>

<#list wayList as way>
<p>
    众筹方式:
<ul>
    <li><b>¥</b>${way.fund}元 (${way.totalnum}位参与, 剩余${way.remainderNum}位)
    <li> 图片: <img src="${way.pic0}"/>
    <li> 回报内容:${way.desc}
    <li> 邮寄方式:${way.postage}
    <li> 回报时间:项目成功结束后<b>${way.sendtime}</b>天内
    <li><a href="/support/pay/${pid}-${way.id}-${uid!''}">立即支付</a>
</ul>
</p>
</#list>


<hr/>
<#include "/page/common/foot.ftl" />

</body>
</html>
