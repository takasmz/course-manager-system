require(['jquery','common/util','jquery.form'],
    function ($ , util , CodeMirror) {
        // 执行初始化函数
        init();

        /**
         * 初始化集合
         */
        function init() {
            initForm();
            bind();
        }

        function initForm(){
            layui.use(['form'], function(){
                var $ = layui.$
                    ,layer = layui.layer
                    ,form = layui.form;

                form.render();
                initDate();

                /* 自定义验证规则 */
                form.verify({
                    title: function(value){
                        if(value.length < 5){
                            return '标题至少得5个字符啊';
                        }
                    }
                    ,pass: [/(.+){6,12}$/, '密码必须6到12位']
                    ,content: function(value){
                        layedit.sync(editIndex);
                    }
                });

                /* 监听提交 */
                form.on('submit(createCourseBtn)', function(data){
                    $("#createCourse").ajaxSubmit({
                        url:'course/teacher/createNewCourse',
                        type:'post',
                        success:function (result) {
                            if(result.code === 1){
                                layer.msg(result.msg,{time:1500,icon:1});
                                $("#createCourse")[0].reset();
                                initDate();
                            }
                        }
                    });
                    return false;
                });
            });
        }

        function initDate() {
            var now = new Date();
            var fullYear = now.getFullYear();
            var month = now.getMonth()+1;
            var year,term;
            if(month<=7){
                year = (fullYear-1) + "-" + fullYear + "学年";
                term = "第二学期";
            }else{
                year = fullYear + "-" + (fullYear+1) + "学年";
                term = "第一学期";
            }
            $("input[name='year']").val(year);
            $("input[name='term']").val(term);
        }

        /**
         * 事件绑定
         */
        function bind() {
            util.bindEvents([ {
                el : '#fileupload',
                event : 'change',
                handler : function() {
                    //var filePath=$(this).val().split("\\");
                    var files = $(this)[0].files;
                    if(files.length>1){
                        layer.msg('一次只能导入一个文件', {icon: 2, time: 1500});
                        return;
                    }
                    var file = files[0];
                    $("#filename").html('<i class="layui-icon layui-icon-upload"></i>'+file.name);
                }
            }])
        }

    });
