require(['jquery','common/util', 'jquery.form','bootstrap-table-treegrid',
        'bootstrap','bootstrap-table','bootstrap-table-CN','jquery.treegrid',
        'simple-module','hotkeys','uploader','simditor'],
    function ($ , util ) {

        var editor,courseId;
        // 执行初始化函数
        init();

        /**
         * 初始化集合
         */
        function init() {
            editor = initEditor("#editor");
            initForm();
            bind();
        }

        function initCourseSelect(form) {
            var examId = $("#examId").val();
            $.ajax({
                url:'course/exam/queryExamById',
                type:'post',
                dataType:'json',
                data:{examId:examId},
                success:function (result) {
                    var data = result.data;
                    data.inputs = 'input:' + data.inputs + '\n' + 'output:' + data.answer;
                    form.val("examInfo", data);
                    editor.setValue(data.examContent);
                }
            })
        }

        function initAdd() {
            $.ajax({
                url:'course/courseExam/createExamInfo',
                data:{
                    courseExamId:courseExamId
                },
                type:'post',
                dataType:'json',
                success:function (result) {

                }
            })
        }

        function initForm(){
            layui.use(['form','element'], function(){
                var $ = layui.$
                    ,layer = layui.layer
                    ,element = layui.element
                    ,form = layui.form;
                form.render();
                /* 自定义验证规则 */
                form.verify({
                    /**
                     * @return {string}
                     */
                    Minimum1: function(){
                        var status;
                        $.ajax({
                            url:'examTestCase/checkTestCaseNum',
                            type:'post',
                            data:{examId:examId},
                            dataType:'json',
                            success:function (num) {
                                status = num>0;
                                if(!status)
                                    return "请至少添加一个测试用例";
                            }
                        })
                        if(!status)
                            return "请至少添加一个测试用例";
                    }
                });

                var examId = $("#examId").val();
                //新增题目
                if(examId === '-1'){
                    //initAdd();
                }else{
                    initCourseSelect(form);
                }

                form.on('select(courseName)', function(data){
                    data = data.value;
                    courseId = data;
                });

                /* 监听提交 */
                form.on('submit(examSubmit)', function(data){
                    var form = data.field;
                    form.examContent = editor.getValue();
                    $("#exam-submit-form").ajaxSubmit({
                        url:'course/courseExam/createExamInfo',
                        type:'post',
                        dataType:'json',
                        data:{examContent:editor.getValue()},
                        success:function (result) {
                            if(result.code === 1){
                                layer.msg("题目保存成功",{time:1500,icon:1})
                                if(result.data){
                                    $("#examId").val(result.data);
                                }
                                form.render();
                            }else {
                                layer.msg("题目保存失败",{time:1500,icon:2})
                            }
                        }
                    });
                    return false;
                });
            });
        }


        function initEditor(obj) {
            Simditor.locale = 'zh-CN';//设置中文
            var e = new Simditor({
                textarea: obj,  //textarea的id
                placeholder: '',
                toolbar:  ['title', 'bold', 'italic', 'underline', 'strikethrough', 'fontScale', 'color', '|', 'ol', 'ul', 'blockquote', 'code', 'table', '|', 'link', 'image', 'hr', '|', 'indent', 'outdent', 'alignment'], //工具条都包含哪些内容
                pasteImage: true,//允许粘贴图片
                defaultImage: '/simditor/images/image.png',//编辑器插入的默认图片，此处可以删除
                upload : {
                    url : '/course/courseExam/uploadImg', //文件上传的接口地址
                    params: {courseExamId:$("#courseExamId").val(),type:'teacherHomework'}, //键值对,指定文件上传接口的额外参数,上传的时候随文件一起提交
                    fileKey:'fileName', //服务器端获取文件数据的参数名
                    connectionCount: 3,
                    leaveConfirm: '正在上传文件，确定要离开嘛'
                },
                success:function (data) {
                    $(".simditor-body").css("height","auto!important");
                    var bodyWidth = $(".simditor-body")[0].offsetWidth-30;
                    $(".simditor").find("img").each(function (index,element){
                        if($(this).width()>1000){
                            var imgSize = $(this).attr("data-image-size");
                            $(this).width(bodyWidth);
                            $(this).height($(this).height()*bodyWidth/imgSize.split(",")[0]);
                        }
                    })
                }
            });
            return e;
        }


        /**
         * 事件绑定
         */
        function bind() {
            util.bindEvents([ {
                el : '.fileupload',
                event : 'change',
                handler : function() {
                    var files = $(this)[0].files;
                    if(files.length>1){
                        layer.msg('只能上传1个文件',{icon:2,time:1500});
                        return;
                    }else if(files.length === 0){
                        $(this).prev(".filename").html('<i class="layui-icon layui-icon-upload"></i>上传题目附件');
                    }else{
                        var file = files[0];
                        var size = file.size;
                        if(size>157286400){
                            layer.msg('单个文件大小不能超过150MB',{icon:2,time:1500});
                            return;
                        }
                        $(this).prev(".filename").html('<i class="layui-icon layui-icon-upload"></i>' + file.name);
                    }

                }
            },{
                el:'.layui-icon-help',
                event:'mouseenter',
                handler:function () {
                    var that = this;
                    var text = $(this).parent(".layui-form-label").next(".layui-input-block").children("textarea").attr("placeholder");
                    layer.tips(text,that);
                }
            },{
                el:'.addTestCase',
                event:'click',
                handler: function () {
                    var examId = $("#examId").val();
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

    });
