   var refresh = function(wxShareTitle, wxShareDesc, locationUrl, wxImgUrl) {
    __currentShareUrl__ = locationUrl;
     jQuery.getScript("/api/getSign?url=" + encodeURIComponent(location.href.split('#')[0]), function() {
    	 alert(wx_share_appid);
    wx.config({
         debug: false,
         appId: wx_share_appid,
         timestamp: wx_timestamp,
         nonceStr: wx_nonceStr,
         signature: wx_signature,
         jsApiList: [
           'onMenuShareAppMessage',
           'onMenuShareTimeline'
         ]
       });
       wx.ready(function() {
         //----分享到朋友----
         wx.onMenuShareAppMessage({
           title: wxShareTitle,
           desc: wxShareDesc,
           link: locationUrl,
           imgUrl: wxImgUrl,
           trigger: function(res) {

             if (document.getElementById("share") != null) {
               document.getElementById("share").style.display = "none";
             }
             if (document.getElementById("gameOver_Share") != null) {
               document.getElementById("gameOver_Share").style.display = "none";
             }
           },
           success: function(res) {
            if (__canShare__) {
              shareSub();
            };
           },
           cancel: function(res) {

           },
           fail: function(res) {
             // alert(JSON.stringify(res));
           }
         });
         //----分享到朋友圈----
         wx.onMenuShareTimeline({
           title: wxShareTitle,
           link: locationUrl,
           imgUrl: wxImgUrl,
           trigger: function(res) {
             if (document.getElementById("share") != null) {
               document.getElementById("share").style.display = "none";
             }
             if (document.getElementById("gameOver_Share") != null) {
               document.getElementById("gameOver_Share").style.display = "none";
             }
           },
           success: function(res) {
            if (__canShare__) {
              shareSub();
            };
           },
           cancel: function(res) {

           },
           fail: function(res) {
             // alert(JSON.stringify(res));
           }
         });
       });
       wx.error(function(res) {
         // alert(res.errMsg);
       });
     });
   }
    //统计分享
    function shareSub() {
      var theUrl = __wwwHead__+'/pl/wx/share/sub';
      var xmlHttp = null;
      xmlHttp = new XMLHttpRequest();
      xmlHttp.open("POST", theUrl, true);
      xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
      xmlHttp.send('type=1&'+'kid_id='+__currentKidId__+'&share_url='+__currentShareUrl__);
      // alert('统计成功');
      return xmlHttp.responseText;
    }
    
    
    
    jQuery(function(){
    	var wxShareTitle="这里填写标题，可以自定义";
    	var wxShareDesc="这里填写描述分享信息";
    	var locationUrl="这里填写分享的路径";
    	var wxImgUrl="这里填写图片路径";
    	 refresh(wxShareTitle, wxShareDesc, locationUrl, wxImgUrl);
    })