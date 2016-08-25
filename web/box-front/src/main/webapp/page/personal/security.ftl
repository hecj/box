<html> 	 
	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8">
		<title>盒子众筹-中国最具影响力的创业众筹融资平台</title>
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="keywords" content="众筹,融资,众筹平台,创业融资"/>
		<meta name="description" content="盒子众筹是中国最具影响力的创业众筹融资平台，专业为有梦想、有创意、有项目的朋友提供募资、投资、孵化、运营一站式综合众筹服务，协助您实现自己的创业梦想。好平台，好起点，尽在盒子众筹！"/>
		<#include "/page/common/base/include.ftl">
	</head>
	<body>
		<#include "/page/common/n_head.ftl" />
	<div class="bg_wrap">
	<#include "/page/common/personal_head.ftl" />
	<div class="cf-personal-center-content">
		<div class="pcenter-subfield">
			<!-- 个人设置 -->
			<div class="pcenter-subfield-2 pcs2-personal-setting" style="display:block">
				<div class="pcs2-personal-setting-menu">
					<ul>
						<li><a href="/personal/getPersonalDetail">基本资料</a></li>
						<li><a href="/personal/security" class="current">安全中心</a></li>
						<li><a href="/usercertify/certify">实名认证</a></li>
						<li class="last"><a href="/personal/receiveAddress">收货地址</a></li>
					</ul>
				</div>
				<div class="pcs2-personal-setting-cont">
					<!-- 基本信息 -->
					<!-- 安全中心 -->
					<div class="security-center pcs2-psm1" style="display:block">
						<div class="sc-tittle">
							<h6>您的安全等级：${c_security_level!}</h6>
							<div class="sc-tittle-img" id="sc-tittle-img">
								<span class="sc-tittle-percent">${security_level}%</span>
							</div>
						</div>
						<div class="sc-cont" id="sc-cont">
							<#if user.phone??>	
							<div class="sc-cont-block sc-cont-phone sc-cont-complete">
								<span id="phoneval" style="display:none">${user.phone!}</span>
								<span class="sc-cont-title sc-cont-complete"><em class="sc-cont-icon1"></em>手机号</span>
								<span class="sc-cont-state">已设置</span>
								<span class="sc-cont-explain">${user.encrypt_phone!}</span>
								<span class="sc-cont-icon2"></span>
								<div class="sc-cont-set">
									<a href="javascript:;" class="sc-cont-go">已绑定</a>
								</div>
							</div>
							<#else>
							<div class="sc-cont-block sc-cont-phone">
								<span class="sc-cont-title"><em class="sc-cont-icon1"></em>手机号</span>
								<span class="sc-cont-state">未设置</span>
								<span class="sc-cont-explain">绑定手机，可直接使用手机号登录</span>
								<span class="sc-cont-icon2"></span>
								<div class="sc-cont-set">
									<a href="javascript:;" class="sc-cont-go">立即绑定</a>
								</div>
							</div>
							</#if>
							<#if user.password??>	
							<div class="sc-cont-block sc-cont-password sc-cont-complete">
								<span class="sc-cont-title"><em class="sc-cont-icon1"></em>登录密码</span>
								<span class="sc-cont-state">已设置</span>
								<span class="sc-cont-explain"></span>
								<span class="sc-cont-icon2"></span>
								<div class="sc-cont-set">
									<a href="javascript:;" class="sc-cont-go">修改</a>
								</div>
							</div>
							<#else>
							<div class="sc-cont-block sc-cont-password">
								<span class="sc-cont-title"><em class="sc-cont-icon1"></em>登录密码</span>
								<span class="sc-cont-state">未设置</span>
								<span class="sc-cont-explain">设置登录密码，降低盗号危险</span>
								<span class="sc-cont-icon2"></span>
								<div class="sc-cont-set">
									<a href="javascript:;" class="sc-cont-go">立即设置</a>
								</div>
							</div>
							</#if>
							<#if user.email??>
							<div class="sc-cont-block sc-cont-mailbox sc-cont-complete">
								<span class="sc-cont-title"><em class="sc-cont-icon1"></em>邮箱</span>
								<span class="sc-cont-state">已设置</span>
								<span class="sc-cont-explain">${user.email!}</span>
								<span class="sc-cont-icon2"></span>
								<div class="sc-cont-set">
									<a href="javascript:;" class="sc-cont-go">已绑定</a>
								</div>
							</div>
							<#elseif user.valid_email??>
							<div class="sc-cont-block sc-cont-mailbox">
								<span class="sc-cont-title"><em class="sc-cont-icon1"></em>邮箱</span>
								<span class="sc-cont-state">待验证</span>
								<span class="sc-cont-explain">${user.valid_email!}</span>
								<span class="sc-cont-icon2"></span>
								<div class="sc-cont-set">
									<a href="javascript:;" class="sc-cont-go sc-cont-mailbox-go">立即验证</a>
									<a href="javascript:;" class="sc-cont-go sc-cont-mailbox-go">修改邮箱</a>
									<span class="sc-cont-go sc-cont-mailbox-last">已发送</span>
									<a href="javascript:;" class="sc-cont-go sc-cont-mailbox-last">激活邮箱</a>
								</div>
							</div>						
							<#else>
							<div class="sc-cont-block sc-cont-mailbox">
								<span class="sc-cont-title"><em class="sc-cont-icon1"></em>邮箱</span>
								<span class="sc-cont-state">未设置</span>
								<span class="sc-cont-explain">绑定邮箱</span>
								<span class="sc-cont-icon2"></span>
								<div class="sc-cont-set">
									<a href="javascript:;" class="sc-cont-go">立即绑定</a>
								</div>
							</div>
							</#if>
							<#if payPwd.pay_pwd??>
							<div class="sc-cont-block sc-cont-payword sc-cont-complete">
								<span class="sc-cont-title"><em class="sc-cont-icon1"></em>支付密码</span>
								<span class="sc-cont-state">已设置</span>
								<span class="sc-cont-explain">保护账号安全，在余额支付时使用</span>
								<span class="sc-cont-icon2"></span>
								<div class="sc-cont-set">
									<a href="javascript:;" class="sc-cont-go">修改</a>
								</div>
							</div>		
							<#else>	
							<div class="sc-cont-block sc-cont-payword">
								<span class="sc-cont-title"><em class="sc-cont-icon1"></em>支付密码</span>
								<span class="sc-cont-state">未设置</span>
								<span class="sc-cont-explain">保护账号安全，在余额支付时使用</span>
								<span class="sc-cont-icon2"></span>
								<div class="sc-cont-set">
									<a href="javascript:;" class="sc-cont-go">立即设置</a>
								</div>
							</div>	
							</#if>																	
						</div>
							<div class="pub-mask-layer"></div>
							<div class="pub-popup">
								<!-- 绑定手机 -->
								<div class="pub-popup-in sc-bind-phone">
									<div class="pub-popup-top">
										<h6>绑定手机号</h6>
										<a href="javascript:;" class="popup-close popup-close-o"></a>
									</div>
									<div class="pub-popup-cont">
										<dl class="tellphone">
											<dt>手机号</dt>
											<dd>
												<input type="text" name="tellphone" />
												<span class="pb-ph unique2">请输入注册手机号</span>
											</dd>
											<span class="pb-sign pb-empty"></span>
										</dl>
										<dl class="dynamic-code">
											<dt>动态码</dt>
											<dd><input type="text" name="dynamic-code" onkeyup="value=value.replace(/[^\w]|_/ig,'')" /></dd>
											<dd><span class="settimeout" onclick="sc_tellphone(this);">获取短信验证码</span></dd>
											<span class="pb-sign pb-empty"></span>
										</dl>
										<dl class="submit phone-submit"><input type="button" name="tellphone-subbtn" value="绑定" /></dl>
									</div>
								</div>
								<!-- 登录密码 -->
								<div class="pub-popup-in sc-set-password">
									<div class="pub-popup-top">
										<h6>设置密码</h6>
										<a href="javascript:;" class="popup-close-p"></a>
									</div>
									<div class="pub-popup-cont">
										<dl class="password">
											<dt>密码</dt>
											<dd>
												<input type="password" name="password" />
												<span class="pb-ph unique2">请输入要设置的密码</span>
											</dd>
											<span class="pb-sign pb-empty"></span>
										</dl>
										<dl class="surepassword">
											<dt>确认密码</dt>
											<dd>
												<input type="password" name="surepassword" />
												<span class="pb-ph unique2">请再次输入设置的密码</span>
											</dd>
											<span class="pb-sign pb-empty"></span>
										</dl>
										<dl class="submit password-submit"><input type="button" name="password-subbtn" value="确定" /></dl>
									</div>
								</div>
								<!-- 绑定邮箱 -->
								<div class="pub-popup-in sc-bind-mailbox">
									<div class="pub-popup-top">
										<h6>绑定邮箱</h6>
										<a href="javascript:;" class="popup-close-o"></a>
									</div>
									<div class="pub-popup-cont not-sent">
										<dl class="mailbox">
											<dt>邮箱</dt>
											<dd>
												<input type="text" name="mailbox" />
												<span class="pb-ph unique2">请输入正确邮箱</span>
											</dd>
											<span class="pb-sign pb-empty"></span>
										</dl>
										<dl class="dynamic-code">
											<dt>验证码</dt>
											<dd><input type="text" name="verification-code" /></dd>
											<dd>
												<img  class="code-img" src="/image?Math.random()" alt="动态码图片" />
											</dd>
											<dd><span class="refresh-code" onclick="validityCode(this);"></span></dd>
											<span class="pb-sign pb-empty"></span>
										</dl>
										<dl class="submit mailbox-submit"><input type="button" name="mailbox-subbtn" value="绑定" /></dl>
									</div>
									<div class="pub-popup-cont sent">
										<span class="sent-icon"></span>
										<span class="sent-word">已将激活邮件发送到您的邮箱</span>
										<span class="sent-word1" onclick="openEmail()"></span>
										<div class="submit sent-subbtn" onclick="openEmail()"><input type="button" value="查看邮箱" /></div>
									</div>
								</div>
								<!-- 支付密码 -->
								<div class="pub-popup-in sc-set-paypassword">
									<div class="pub-popup-top">
										<h6>设置支付密码</h6>
										<a href="javascript:;" class="popup-close-o"></a>
									</div>
									<div class="pub-popup-cont">
										<dl class="paypassword">
											<dt>设置支付密码</dt>
											<dd>
												<input type="password" name="paypassword" />
												<span class="pb-ph unique2">请输入支付密码</span>
											</dd>
											<span class="pb-sign pb-empty"></span>
										</dl>
										<dl class="surepaypassword">
											<dt>确认支付密码</dt>
											<dd>
												<input type="password" name="surepaypassword" />
												<span class="pb-ph unique2">请输入确认支付密码</span>
											</dd>
											<span class="pb-sign pb-empty"></span>
										</dl>
										<dl class="bindphone">
											<dt>绑定手机</dt>
											<dd>
												<input type="text" readonly="true" name="bindphone" value="${user.encrypt_phone!}" />
												<span class="pb-ph unique2">绑定手机号</span>
											</dd>
											<span class="pb-sign pb-empty"></span>
										</dl>	
										<dl class="dynamic-code">
											<dt>动态码</dt>
											<dd><input type="text" name="dynamic-code" /></dd>
											<dd><span class="settimeout" onclick="sc_pay_tellphone(this, ${user.phone!})">获取短信验证码</span></dd>
											<span class="pb-sign pb-empty"></span>
										</dl>																			
										<dl class="submit paypassword-submit"><input type="button" name="paypassword-subbtn" value="确定" /></dl>
									</div>
								</div>
								<!-- 修改登录密码 -->
								<div class="pub-popup-in sc-change-password">
									<div class="pub-popup-top">
										<h6>修改密码</h6>
										<a href="javascript:;" class="popup-close-o"></a>
									</div>
									<div class="pub-popup-cont">
										<dl class="now-password">
											<dt>当前密码</dt>
											<dd>
												<input type="password" name="n-password" />
												<span class="pb-ph unique2">请输入要设置的密码</span>
											</dd>
											<span class="pb-sign pb-empty"></span>
										</dl>
										<dl class="c-password">
											<dt>密码</dt>
											<dd>
												<input type="password" name="c-password" />
												<span class="pb-ph unique2">请输入要设置的密码</span>
											</dd>
											<span class="pb-sign pb-empty"></span>
										</dl>										
										<dl class="c-surepassword">
											<dt>确认密码</dt>
											<dd>
												<input type="password" name="c-surepassword" />
												<span class="pb-ph unique2">请再次输入设置的密码</span>
											</dd>
											<span class="pb-sign pb-empty"></span>
										</dl>
										<dl class="submit c-password-submit"><input type="button" name="c-password-subbtn" value="确定" /></dl>
									</div>
								</div>	
								<!-- 修改支付密码 -->
								<div class="pub-popup-in sc-change-paypassword">
									<div class="pub-popup-top">
										<h6>修改支付密码</h6>
										<a href="javascript:;" class="popup-close-o"></a>
									</div>
									<div class="pub-popup-cont">
										<dl class="now-paypassword">
											<dt>当前支付密码</dt>
											<dd>
												<input type="password" name="n-paypassword"/>
												<span class="pb-ph unique2">请输入要设置的密码</span>
											</dd>
											<span class="pb-sign pb-empty"></span>
										</dl>
										<dl class="c-paypassword">
											<dt>新的支付密码</dt>
											<dd>
												<input type="password" name="c-paypassword" />
												<span class="pb-ph unique2">请输入要设置的密码</span>
											</dd>
											<span class="pb-sign pb-empty"></span>
										</dl>
										<dl class="c-surepaypassword">
											<dt>确认新支付密码</dt>
											<dd>
												<input type="password" name="c-surepaypassword" />
												<span class="pb-ph unique2">请再次输入设置的密码</span>
											</dd>
											<span class="pb-sign pb-empty"></span>
										</dl>
										<dl class="submit c-paypassword-submit"><input type="button" name="c-paypassword-subbtn" value="确定" /></dl>
									</div>
								</div>
								<!-- 成功提交页面 刷新页面-->
								<div class="pub-popup-in everything-success other-success">
									<div class="pub-popup-top">
										<h6>意见反馈</h6>
										<a href="javascript:;" class="popup-close popup-close-o"></a>
									</div>
									<div class="pub-popup-cont">
										<span></span>
										<dl class="submit success-submit"><input type="button" name="submit-btn-os" value="确定" /></dl>
									</div>									
								</div>
								<!-- 成功提交页面 不刷新页面-->
								<div class="pub-popup-in everything-success password-success">
									<div class="pub-popup-top">
										<h6>提示</h6>
										<a href="javascript:;" class="popup-close popup-close-p"></a>
									</div>
									<div class="pub-popup-cont">
										<span></span>
										<dl class="submit success-submit"><input type="button" name="submit-btn-ps" value="确定" /></dl>
									</div>									
								</div>												
							</div>
					</div>
					<!-- 实名认证 -->
					<!-- 收货地址 -->
				</div>
			</div>
			<!-- 消息中心 -->
		</div>
	</div>
	</div>
	<!-- 引用底部开始 -->
	<#include "/page/common/foot.ftl" />
	</body>
		<script type="text/javascript" src="/static/js/page/personal/cf-personalcenter-personalsetting-basic.js?v=20151124"></script>	
		<script type="text/javascript" src="/static/js/page/personal/security.js?v=20151124"></script>
</html>