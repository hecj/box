	<ul class="forminfo">
					<input type="hidden" name="r.id" value="${(r.id)!}" />
		
				<li>
					<label>n</label>
					<input type="text" class="dfinput"  name="r.n" value="${(r.n)!}" />
					<i>
						<#if (n_msg)??>
							<font color="red" class="tip_error">${n_msg!}</font>
						<#else>
							(角色名称)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>qs</label>
					<input type="text" class="dfinput"  name="r.qs" value="${(r.qs)!}" onclick="openqs()" />
					<i>
						<#if (qs_msg)??>
							<font color="red" class="tip_error">${qs_msg!}</font>
						<#else>
							(通用查询权限字符逗号分隔)
						</#if>
					</i>
				</li>
				
		
				<li>
					<label>ms</label>
					<input type="text" class="dfinput"  name="r.ms" value="${(r.ms)!}"  onclick="openms()"/>
					<i>
						<#if (ms_msg)??>
							<font color="red" class="tip_error">${ms_msg!}</font>
						<#else>
							(菜单权限id字符 逗号分开)
						</#if>
					</i>
				</li>
				
			 
				
		
		<li>
			<label>&nbsp;</label>
			<input value="提交" type="submit" class="btn">
		</li>
	</ul>
	<style>
	
	.pop_sel {
		display:none;
	    background: #ced none repeat scroll 0 0;
	    border: 3px solid #bdc;
	    display: block;
	    left: 700px;
	    position: fixed;
	    top: 100px;
	}
	#openqs span{
		display:inline;
		margin-right:15px;
	}
	#openms span{
		display:inline;
		margin-right:15px;
	}
	</style>
	 
	
	<div  id="openqs" style="margin: 0px auto; width: 485px; min-height: 100px; z-index: 1000010; left: 515.5px; top: 134px; position: fixed;  " class="mesWindow" class="pop_sel"><div class="mesWindowTop" style="cursor: move;"><div style="width:auto;" class="msg_title tiptop"><span class="titleText">消息提示</span><a onclick="jQuery(this).parents('.mesWindow').hide();" href="javascript:void(0)" style="float:right" class="close a_close"></a></div></div><div style="height: auto; min-height: 50px; max-height: 408px; overflow: auto;" class="mesWindowContent"><p style="padding-left: 0px;" class="txtcenter"></p><div style="padding: 20px;">
			<span >
				<input type="checkbox" onclick="jQuery(this).parents('.mesWindow').find('.sel_op').attr('checked',jQuery(this).attr('checked'))">全选/取消
				&nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
				<a href="javascript:void(0);" style="color:green" onclick="jQuery(this).parents('.mesWindow').hide();">取消</a>
				&nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
				<a  href="javascript:void(0);" style="color:red" onclick="var sels=jQuery(this).parents('.mesWindow').find('.sel_op:checked').map(function(){return this.value;}); jQuery('[name=r.qs]').val(sels.get().join(','));jQuery(this).parents('.mesWindow').hide();">确定</a>
			</span>
			
			<br>
			<br>
			
			<#list qs as q>
			<span >
			<input class="sel_op" value="${q.id}" type="checkbox"  >${q.n}
			</span>
			</#list>
		
	</div><p></p></div></div>
	
	
	<div  id="openms" style="margin: 0px auto; width: 485px; min-height: 300px; z-index: 1000010; left: 515.5px; top: 134px; position: fixed;  " class="mesWindow" class="pop_sel"><div class="mesWindowTop" style="cursor: move;"><div style="width:auto;" class="msg_title tiptop"><span class="titleText">消息提示</span><a onclick="jQuery(this).parents('.mesWindow').hide();" href="javascript:void(0)" style="float:right" class="close a_close"></a></div></div><div style="height: auto; min-height: 50px; max-height: 408px; overflow: auto;" class="mesWindowContent"><p style="padding-left: 0px;" class="txtcenter"></p><div style="padding: 20px;">
			<span >
				<input type="checkbox" onclick="jQuery(this).parents('.mesWindow').find('.sel_op').attr('checked',jQuery(this).attr('checked'))">全选/取消
				&nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
				<a href="javascript:void(0);" style="color:green" onclick="jQuery(this).parents('.mesWindow').hide();">取消</a>
				&nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
				<a  href="javascript:void(0);" style="color:red" onclick="var sels=jQuery(this).parents('.mesWindow').find('.sel_op:checked').map(function(){return this.value;}); jQuery('[name=r.ms]').val(sels.get().join(','));jQuery(this).parents('.mesWindow').hide();">确定</a>
			</span>

			<br /><br />

			<span>
			<#list ms as m>
			<#if (m.pid == 0)><br /></#if>
                    <input class="sel_op" value="${m.id}" type="checkbox" onclick="checkMenuBox(${m.id},${m.pid})" parentid="${m.pid}">
                    ${m.n}
			</#list>
			</span>

	</div><p></p></div></div>

	<script>
		function openqs(){
			jQuery("[name='r.qs']").attr("readonly","true");
			jQuery("#openqs").show();

			var v=jQuery('[name=r.qs]').val();
			var vs=v.split(",")
			
			jQuery("#openqs .sel_op").attr("checked",false);
			for(var i in vs){
				jQuery("#openqs .sel_op[value='"+vs[i]+"']").attr("checked","checked")
			}
			//peon.popx.tip('<div style="padding:20px;">'+jQuery("#openqs").clone().removeAttr("id").html()+'</div>');
		}
		function openms(){
			jQuery("[name='r.qs']").attr("readonly","true");
			jQuery("#openms").show();

			var v=jQuery('[name=r.ms]').val();
			var vs=v.split(",");
			
			jQuery("#openms .sel_op").attr("checked",false);
			for(var i in vs){
				jQuery("#openms .sel_op[value='"+vs[i]+"']").attr("checked","checked")
			}
			//peon.popx.tip('<div style="padding:20px;">'+jQuery("#openqs").clone().removeAttr("id").html()+'</div>');
		}

        //菜单权限-点击父级选中子级功能
		function checkMenuBox(id, pid){
			if(pid > 0) return;
            jQuery("#openms .sel_op[parentid='"+ id +"']")
                .attr("checked",jQuery("#openms .sel_op[value='"+id+"']").attr("checked"));
		}
	</script>