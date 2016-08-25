/// <reference path="jquery-1.3.2-vsdoc2.js" />

//显示的最大长度
combobox.prototype.maxLength = null;
//是否必须选择
combobox.prototype.mustSelect = false;
//获得焦点是是否显示数据
combobox.prototype.focusShow = false;
//数据id
combobox.prototype.fieldText = 'text';
//数据text
combobox.prototype.fieldValue = 'id';
//列表
combobox.prototype.optionsContainer = null;

//combobox初始化
combobox.prototype.init = function(id, url) {
    var defaultUrl = url == null ? 'dropdown.gif' : url;
    var textBoxs = $(id);
    textBoxs.each(function(i, dom) {
        textBox = $(dom);
        textBox.wrap('<div class="diapol_combobox"  />');
        var additionalHeight = $.browser.msie ? 3 : 3;

        var button1 = $('<input type="button" class="button" />').insertAfter(textBox).css({ height: textBox.height() + additionalHeight, "background-image": 'url(' + defaultUrl + ')' });
        //var button1 = $('<img class="button" src="' + defaultUrl + '" />').insertAfter(textBox).css({ height: textBox.height() + additionalHeight });
        textBox.width(textBox.width() - button1.width());
    });

    //选项容器。
    if (!this.optionsContainer) {
        this.optionsContainer = $('<ul id="diapol_comboboxDropdown"  />').appendTo($('body'));
        //if there is jquery.bgiframe plugin, use it to fix ie6 select/z-index bug.
        //search "z-index ie6 select bug" for more infomation
        if ($.fn.bgiframe)
            this.optionsContainer.bgiframe();
    }
   
}

function combobox(id,callback) {
    this.host = id;
    this.dataSource = null; //数据源
    this.defaultText = $(id).val();

    var textBox = $(id); //textBox
    var button = textBox.next();

    var keys = { up: 38, down: 40, enter: 13, tab: 9, esc: 27 };
    var suggestionsKey = 'combobox_suggestions';
    var showing = false;
    var hideTimer;
    var comboObj = this;
    //var selectFlag = false;

    //选择事件
    this.onSelected = null;

    this.dataBind = function() {
        textBox.val(this.defaultText);

        //事件触发设置
        textBox.attr('autocomplete', 'off').keydown(keydown).keyup(keyup);
        if (this.focusShow == true) {
            //todo..
        }
        textBox.blur(function() {
        if (!showing && comboObj.mustSelect) {
            // alert('blur');
            textBox.val(comboObj.defaultText);
                
            }
        });

        //设置图片的点击事件
        button.click(function() {
            show('');
        });

    }                         //dataBind_end


    function domClick(e) {
        var dom = $.browser.msie ? event.srcElement : e.target;

        if (showing == true && $(dom).attr("class") != button.attr("class")) { //层显示的话隐藏
            //alert('hide');
            hide();
            if (comboObj.mustSelect) {
                // alert('domclick');
                textBox.val(comboObj.defaultText);
            }
//            if (comboObj.mustSelect) {//
//                alert('domclick clear');
//                textBox.val('');
//            }
        }
    }

    function show(filter, len) {
        
        if (hideTimer) {
            window.clearTimeout(hideTimer);
            hideTimer = 0;
        }
        //alert(showing);

        //alert(comboObj.dataSource.length);
        oriValue = textBox.val(); //获得当前框值
        var html = '';
        var ulLen = 0;
        //alert(comboObj.dataSource.length);
        for (var k in comboObj.dataSource) {
            var v = comboObj.dataSource[k][comboObj.fieldText]; //text
            var vid = comboObj.dataSource[k][comboObj.fieldValue]; //value


            var inputStr = v.toLowerCase().indexOf($.trim(filter.toLowerCase()));

            if ((!filter) || (filter && inputStr >= 0)) {
                html += '<li id=' + vid + '>' + v + '</li>';
                ulLen++;
                //alert(len)
                if (len && ulLen >= len) {//
                    break;
                }
                //$(this).attr('id', vid);
            }
        }

        //position and size of the options UI
        var loc = { left: textBox.offset().left, top: textBox.offset().top + textBox.height() + 3, width: textBox.width() + button.width() + 5 }
        comboObj.optionsContainer.html(html).css(loc);
        // alert(html);
        // alert(comboObj.optionsContainer.html());

        //decide which option is currently selected
        selIndex = 0;
        var found = false;
        var options = comboObj.optionsContainer.children('li').each(function(i) {

            if (found) return;
            if ($(this).text().toLowerCase() == oriValue.toLowerCase()) {
                $(this).addClass('selected');
                selIndex = i;
                found = true;
            }
        });
        //if there is no items matched, hide the empty select list, so user can show options with down key
        if (!options.size()) {
            hide();
            return;
        }
        if (!found)
            options.eq(0).addClass('selected');

        //mouse hover to change the highlight option, clicking to select it
        options.click(function() {

            if (comboObj.onSelected != null) {
                comboObj.onSelected($(this));
            }
            //selectFlag = true;//选择
            // alert($(this).text())
            textBox.val($(this).text());
            hide();
        }).hover(function() {
            options.each(function() {
                $(this).removeClass('selected');
            });
            $(this).addClass('selected');
            selIndex = options.index(this);
        });

        if (!filter)
        //showing all the options
            comboObj.optionsContainer.slideDown();
        else
        //showing filtered options, happens when textbox.value changed, should not flick
            comboObj.optionsContainer.show();

        showing = true;

        $(document).bind("click", domClick);

    }         //show end




    function hide() {
        if (showing) {
            comboObj.optionsContainer.hide().children('li').each(function() { $(this).remove(); });
            showing = false;
            $(document).unbind("click");
//            if (comboObj.mustSelect && !selectFlag) {
//                //alert('hide clear');
//                textBox.val('');
//            }
//            selectFlag = false;//重置
        }
    } //hide_end


    var selIndex;
    function keydown(evt) {
        switch (evt.keyCode) {
            case keys.esc:
                hide();
                //textBox.val(oriValue);
                //fixes esc twice clears the textbox value bug in ie
                evt.preventDefault();
                return;
            case keys.enter:
                choose();
                //don't submit the form
                evt.preventDefault();

                return;
            case keys.tab:
                choose();
                return;
            case keys.up:
                goup();
                return;
            case keys.down:
                godown();
                return;
        }
    }

    var oldVal = '';
    function keyup(evt) {
        var v = $(this).val();
        if (v != oldVal) {
            show(oldVal = v, comboObj.maxLength);
        }
    }

    function godown() {
        if (showing) {
            var options = comboObj.optionsContainer.children('li');
            var n = options.size();
            if (!n)
                return;
            selIndex++;

            if (selIndex > n - 1)
                selIndex = 0;

            var v = options.eq(selIndex);
            if (v.size()) {
                options.each(function() { $(this).removeClass('selected') });
                v.addClass('selected');
            }
        } else {
            show('');
        }
    }

    function goup() {
        if (showing) {
            var options = comboObj.optionsContainer.children('li');
            var n = options.size();
            if (!n)
                return;
            selIndex--;

            if (selIndex < 0)
                selIndex = n - 1;

            var v = options.eq(selIndex);
            if (v.size()) {
                options.each(function() { $(this).removeClass('selected') });
                v.addClass('selected');
            }
        } else {
            show('');
        }
    }

    function choose() {
       
        if (showing) {
            var v = $('li', comboObj.optionsContainer).eq(selIndex);
            if (v.size()) {
                textBox.val(v.text());
                oldVal = v.text();
                hide();
                if (comboObj.onSelected != null) {
                    comboObj.onSelected($(v));
                }
                //selectFlag = true;//选择了
                //alert('xx');
                //                flag = true; //选择了
                //                if (callback != null) {///////////////////////////////////////////////添加回调。
                //                    //alert( $(v).attr('id'));
                //                    callback($(v).attr('id'));
                //                }
                
                 
            }
        }
    }






} //combobox_end


