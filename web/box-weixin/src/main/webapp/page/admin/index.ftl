<html>
    <body>
        <h1>weixin更新菜单</h1>
        <div class="width:100px; height:10px;" id="info"></div>
        <form action="http://wc.duomeidai.com/api/createMenu" method="post">
            <textarea rows="10" cols="100" name="json">

            {"button":[
            {"type":"click","name":"推事件","key":"10000"},
            {"type":"view","name":"跳转","url":"http://114.66.196.204:10002"},
            {"type":"view","name":"绑定账号","url":"http://wc.duomeidai.com/"}
            ]}

            </textarea> <br />

            <input type="submit" value="更新weixin菜单" />

        </form>
    </body>
</html>