/**
 * 工具集
 */
define(['jquery'], function ($) {

    return {
        /**
         * 事件绑定
         * @param bindings [{el:x,event:y,handler:z}]
         *                  el为页面元素 event为绑定事件 handler为事件响应函数
         */
        bindEvents: function (bindings) {
            $.each(bindings, function (i, v) {
                if (typeof v.el == 'string') {
                    $(document).on(v.event, v.el, v.handler);
                } else {
                    $(v.el).on(v.event, v.handler);
                }
            });
        },
        
        /**
         * 获取Url参数
         * @param url url地址
         */
        getUrlParams: function (url) {
            // 需要返回的参数集合
            var rtnParams = {},
            // 参数键值对
                paramPair = [];
            if (!url || url.indexOf('?') === -1) {
                return rtnParams;
            }

            $.each(url.substr(url.indexOf('?') + 1).split('&'), function (i, v) {
                paramPair = v.split('=');
                rtnParams[paramPair[0]] = paramPair[1];
            });

            return rtnParams;
        },

        /**
         * 去除空格
         * @param array
         * @returns {Array}
         */
        trims: function (array) {
            var newArr = [],
                ele;
            $.each(array, function (i, v) {
                ele = v.replace(/ /g, '');
                ele != '' && newArr.push(ele);
            });
            return newArr;
        },

        /**
         *  时间间隔计算工具
         * @param strInterval
         * @param num
         * @returns {string}
         */
        timeInterval: function (strInterval, num) {
            var date = arguments[2] || new Date();
            switch (strInterval) {
                case 's' :
                    date = new Date(date.getTime() + (1000 * num));
                    break;
                case 'n' :
                    date = new Date(date.getTime() + (60000 * num));
                    break;
                case 'h' :
                    date = new Date(date.getTime() + (3600000 * num));
                    break;
                case 'd' :
                    date = new Date(date.getTime() + (86400000 * num));
                    break;
                case 'w' :
                    date = new Date(date.getTime() + ((86400000 * 7) * num));
                    break;
                case 'm' :
                    date = new Date(date.getFullYear(), (date.getMonth()) + num, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds());
                    break;
                default:
                    date = new Date((date.getFullYear() + num), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds());
            }
            date = date.getTime() >= (new Date()).getTime() ? new Date() : date;
            return [date.getFullYear(), date.getMonth() + 1, date.getDate()].join('/');
        },

        /**
         * 格式化日期  /yy/MM/dd/hh/mm/ss
         * @param date
         * @param formatStr
         * @returns {*}
         */
        dateFormat: function (date, formatStr) {
            var str = formatStr;
            var Week = ['日', '一', '二', '三', '四', '五', '六'];
            str = str.replace(/yy/, date.getFullYear());
            str = str.replace(/y/, (date.getYear() % 100) > 9 ? (date.getYear() % 100).toString() : '0' + (date.getYear() % 100));
            str = str.replace(/MM/, date.getMonth() >= 9 ? (date.getMonth() + 1).toString() : '0' + (date.getMonth() + 1));
            str = str.replace(/M/, date.getMonth() + 1);
            str = str.replace(/w|W/, Week[date.getDay()]);
            str = str.replace(/dd/, date.getDate() > 9 ? date.getDate().toString() : '0' + date.getDate());
            str = str.replace(/d/, date.getDate());
            str = str.replace(/hh/, date.getHours() > 9 ? date.getHours().toString() : '0' + date.getHours());
            str = str.replace(/h/, date.getHours());
            str = str.replace(/mm/, date.getMinutes() > 9 ? date.getMinutes().toString() : '0' + date.getMinutes());
            str = str.replace(/m/, date.getMinutes());
            str = str.replace(/ss/, date.getSeconds() > 9 ? date.getSeconds().toString() : '0' + date.getSeconds());
            str = str.replace(/s/, date.getSeconds());
            return str;
        },

        getCourseList: function(initTag) {
            $.ajax({
                url:'course/getCourseList',
                type:'post',
                dataType:'json',
                success:function (result) {
                    var data = result.data;
                    for(var i=0;i<data.length;i++){
                        $(".layui-tab-title").append("<li cid='"+data[i].id+"'>"+data[i].courseName+"</li>");
                        $(".layui-tab-content").append('<div class="layui-tab-item"></div>');
                    }
                    initTag();
                }
            })
        },

        enterSubmit: function ($obj) {
            $(document).keyup(function(event){
                if(event.keyCode === 13){
                    $obj.trigger("click");
                }
            });
        },
        /**
         * 格式为yyyy-MM-dd的字符串转Date
         * @param dateString 时间字符串
         * @returns {Date}
         */
        convertDateFromString : function (dateString) {
            if (dateString) {
                var date = new Date(dateString.replace(/-/,"/"))
                return date;
            }
        },
        /**
         * 格式为yyyy-MM-dd hh:mm:ss的字符串转Date
         * @param dateString 时间字符串
         * @returns {Date}
         */
        convertDateFromString : function (dateString) {
            if (dateString) {
                var arr1 = dateString.split(" ");
                var sdate = arr1[0].split('-');
                var date = new Date(sdate[0], sdate[1]-1, sdate[2]);
                return date;
            }
        },
        
        initBindEvents : function () {
            this.bindEvents([{
                el:'.addTest',
                event:'click',
                handler:function () {
                    var examId = $(this).attr("eid");
                    if(examId){
                        layer.open({
                            type: 1,
                            title : '添加测试用例',
                            area : [ '600px', '400px' ],
                            content :
                                '<div style="margin-top:20px;">'+
                                '<form class="form-horizontal form-input" id="testCaseForm" enctype="multipart/form-data">'+
                                    '<input name="examId" type="hidden" value="'+examId+'">'+
                                    '<div class="">'+
                                        '<div class="cl" style="text-align:center;">'+
                                            '<div class="layui-tab-item layui-show">\n' +
                                                '<div class="code-item">\n' +
                                                    '<div class="layui-form-item ">\n' +
                                                        '<label class="layui-form-label">程序输入:</label>\n' +
                                                        '<div class="layui-input-block">\n' +
                                                            '<textarea name="input" lay-verify="Minimum1" style="width: 80%;" placeholder="" class="layui-textarea textCase-input"></textarea>\n' +
                                                        '</div>\n' +
                                                        '<label class="layui-form-label">程序输出:</label>\n' +
                                                        '<div class="layui-input-block">\n' +
                                                            '<textarea name="output" lay-verify="Minimum1" style="width: 80%;margin: 20px 0;" placeholder="" class="layui-textarea textCase-input"></textarea>\n' +
                                                        '</div>\n' +
                                                        '<div class="layui-input-block" style="margin-left: 0px;">\n' +
                                                            '<button type="button"  class="layui-btn addTestCase" >添加用例</button>\n' +
                                                            '<button type="button"  class="layui-btn quit" >关闭</button>\n' +
                                                        '</div>\n' +
                                                    '</div>\n' +
                                                '</div>\n' +
                                            '</div>'+
                                        '</div>'+
                                    '</div>'+
                                '</form>'+
                                '</div>'
                        });
                    }
                }
            },{
                el:'.quit',
                event:'click',
                handler:function () {
                    $(this).parents(".layui-layer-page").remove();
                    $(".layui-layer-shade").remove();
                }
            },{
                el:'.addTestCase',
                event:'click',
                handler: function () {
                    var examId = $("#testCaseForm").find("input[name='examId']").val();
                    if(examId === '-1'){
                        layer.msg("请先保存题目再添加测试用例");
                        return;
                    }
                    var input = $("textarea[name='input']").val();
                    var output = $("textarea[name='output']").val();
                    if(!input && !output){
                        layer.msg("输入和输出值不能为空");
                        return;
                    }
                    $.ajax({
                        url:'examTestCase/addTestCase',
                        type:'post',
                        data:{
                            input:input,
                            output:output,
                            examId:examId
                        },
                        dataType:'json',
                        success:function (result) {
                            layer.msg(result.msg);
                            $("textarea[name='input']").val("");
                            $("textarea[name='output']").val("");
                        }
                    })
                }
            }])
        }
    }
});
