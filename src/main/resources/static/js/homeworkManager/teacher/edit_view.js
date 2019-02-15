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
            initForm();
            initEditor("#editor");
            initCourseSelect();
            bind();
        }

        function initCourseSelect() {
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

        function initForm(){
            layui.use(['form'], function(){
                var $ = layui.$
                    ,layer = layui.layer
                    ,form = layui.form;

                form.render();

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

                form.on('select(courseName)', function(data){
                    var data = data.value;
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
                                $("#exam-submit-form")[0].reset();
                                $("#examId").val("");
                                editor.setValue("");
                                form.render();
                            }else {
                                layer.msg("题目保存失败",{time:1500,icon:2})
                            }
                        }
                    })
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
                    var input = $("input[name='input']").val();
                    var output = $("input[name='output']").val();
                    var text = $("#testCase").val();
                    //var reg = new RegExp("input:.*","g");
                    var nowInput = text.match("input:.*")[0];
                    if(nowInput.length>6){
                        nowInput += "|" + input;
                    }else{
                        nowInput +=  input;
                    }
                    var nowOutput = text.match("output:.*")[0];
                    if(nowOutput.length>7){
                        nowOutput += "|" + output;
                    }else{
                        nowOutput +=  output;
                    }
                    text = nowInput + "\r\n" + nowOutput;
                    $("#testCase").val(text);
                    //text.
                }
            }])
        }

    });
