/** * IFrame无刷新文件上传 */
var jFileUpLoad = function (config) {

    //静态变量
    var IFRAME_NAME = 'juploadFileUpLoadIframe';
    var UPLOADFORM_NAME = 'juploadForm';
    var UPLOADFORM_FILE = 'juploadFile';

    var ifr = null,
        ifrom = null,
        fm = null,
        defConfig = {
            submitBtn: $('#uploadButton'),
            uploadurl: '/upload/uploadfile',
            uploadname: 'uploadFile',
            //上传成功后回调
            complete: function (response) {
            },
            //点击提交未上传时回调
            beforeUpLoad: function () {
            },
            //点击提交上传后回调
            afterUpLoad: function () {
            }
        };

    //配置
    config = $.extend(defConfig, config);
    config.submitBtn.bind('click', function (e) {
        $("#" + IFRAME_NAME).remove();
        $("#" + UPLOADFORM_NAME).remove();
        e.preventDefault();
        ifr = $('<iframe name="' + IFRAME_NAME + '" id="' + IFRAME_NAME + '" style="display:none;"></iframe>');
        ifr.appendTo($('body'));
        iform = $('<div style="display: none;">'
            + '<form action="' + config.uploadurl + '" id="' + UPLOADFORM_NAME
            + '"enctype="multipart/form-data" method="post" target="_blank">'
            + '<input type="file" id="' + UPLOADFORM_FILE + '" name="' + config.uploadname + '" /></form></div>');
        iform.appendTo($('body'));
        fm = $('#' + UPLOADFORM_NAME);
        fm.attr('target', IFRAME_NAME); //target目录设置为ifr
        $("#" + UPLOADFORM_FILE).click();
        $("#" + UPLOADFORM_FILE).bind('change', function (e) {
            e.preventDefault();
            //点击提交前触发事件, 函数返回false可阻止提交表单，用于file为空判断
            if (config.beforeUpLoad.call(this) === false) {
                return;
            }
            //上传完毕iframe onload事件
            ifr.load(function () {
                var response = this.contentWindow.document.body.innerHTML;
                config.complete.call(this, response);
                ifr.remove();
                ifr = null; //清除引用
            });
            fm.submit(); //提交表单
            //点击提交后触发事件
            config.afterUpLoad.call(this);
        });
    });
};