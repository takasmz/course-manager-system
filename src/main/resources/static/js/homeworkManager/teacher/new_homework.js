require(['jquery','common/util', 'jquery.form','bootstrap-table-treegrid',
        'bootstrap','bootstrap-table','bootstrap-table-CN','jquery.treegrid',
    'simple-module','hotkeys','uploader','simditor'],
    function ($ , util ) {

        var $table = $("#example-table");
        var Table,editor,batchEditor,courseId,courseExamId = "";
        // 执行初始化函数
        init();

        /**
         * 初始化集合
         */
        function init() {
            initCourseSelect();
            bind();
        }

        function initCourseSelect() {
            $.ajax({
                url:'/course/teacher/queryCourseList',
                type:'post',
                success:function (result) {
                    var list = result.data;
                    for(var i=0; i<list.length; i++){
                        var data = list[i];
                        $("select[name='courseId']").append("<option value='"+data.id+"'>"+data.courseName+"</option>")
                    }
                    initForm();
                    if(list !== null && list.length>0){
                        courseId = list[0].id;
                        initTable(courseId);
                    }else{
                        initTable('');
                    }
                }
            })
        }

        function initForm(){
            layui.use(['form','element','laydate'], function(){
                var $ = layui.$
                    ,layer = layui.layer
                    ,form = layui.form
                    ,element = layui.element
                    ,laydate = layui.laydate;

                form.render();
                laydate.render({
                    elem: '#expireTime'
                    ,type: 'datetime'
                });
                laydate.render({
                    elem: '#showAnswerTime'
                    ,type: 'datetime'
                })

                element.on('tab(change)', function(data) {
                    switch (data.index) {
                        case 0:{
                            //$("#examId").val("");
                        }
                        case 1:{
                            var opt = {
                                query:{
                                    courseExamId:$("#courseExamId").val()
                                },
                                url:'course/courseExam/newHomeworkExample?courseId='+courseId
                            };
                            $table.bootstrapTable("refresh",opt);
                        }
                        case 2:{
                            batchEditor = initEditor($("#txtContent"));
                            var toolHeight = $(window).width()<1380?80:40;
                            var height = $(window).height()-50-55-15-42-85-20-30-100-toolHeight;
                            $(".simditor-body").height(height);
                            batchEditor.setValue($("#batch-example").text());
                        }
                        case 3:{

                        }
                    }
                    //initTable(courseId);
                });

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

                $(document).on("click",".edit",function () {
                    var examId = $(this).attr("eid");
                    $(".layui-tab-title").children("li").eq(0).trigger("click");
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
                })

                $(document).on("click",".delete",function () {
                    var examId = $(this).attr("eid");
                    layer.confirm('确认删除该题目嘛？',{
                        icon:3,
                        title: '删除'
                    },function () {
                        $.ajax({
                            url:'course/exam/deleteExamById',
                            type:'post',
                            dataType:'json',
                            data:{examId:examId},
                            success:function (result) {
                                if(result.code == 1){
                                    layer.msg("删除成功",{icon:1});
                                    Table.refresh();
                                }
                            }
                        })
                    })

                })


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
                form.on('submit(courseExamSubmit)', function(data){
                    var form = data.field;
                    form.courseExamId = $("#courseExamId").val();
                    $("#courseExam-submit-form").ajaxSubmit({
                        url:'course/courseExam/createNewHomework',
                        type:'post',
                        dataType:'json',
                        data:{courseExamId:$("#courseExamId").val()},
                        success:function (result) {
                            if(result.code === 1){
                                layer.msg("作业保存成功", {
                                    time:1500,
                                    icon:1,
                                    shift: -1
                                },function () {
                                    window.location.reload();
                                });

                            }else {
                                layer.msg("作业保存失败",{time:1500,icon:2})
                            }
                        }
                    })
                    return false;
                });

            });
        }

        function dataURLtoBlob(url) {
            var arr = url.split(','), mime = arr[0].match(/:(.*?);/)[1],
                bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
            while (n--) {
                u8arr[n] = bstr.charCodeAt(n);
            }
            return new Blob([u8arr], {type: mime});
        }

        function initTable(courseId){
            $.ajax({
                url:'course/courseExam/newHomeworkExample?courseId='+courseId,
                type:'post',
                dataType:'json',
                success:function (result) {
                    var list = result.rows;
                    if(list.length === 1){
                        courseExamId = list[0].courseExamId;
                        $("#courseExamId").val(courseExamId);
                    }
                    editor = initEditor($("#editor"));
                }
            })
            Table = $table.bootstrapTable({
                data: {},
                // method: 'get',
                // ajaxOptions: {}, //传递参数
                striped:true,    //开启条纹
                idField:'id',
                // sortName:'expireTime',
                //sidePagination: 'client',
                // sortOrder:'desc',
                pagination: false, //是否分页
                strictSearch: true,
                showColumns: true,                  //是否显示所有的列
                showRefresh: false,                  //是否显示刷新按钮
                treeShowField: 'name',
                parentIdField: 'pid',
                columns: [
                    {field: 'name', title: '课程/作业名称'}
                    , {field: 'examContent', title: '作业内容'}
                    , {field: 'submitName', title: '提交方式'}
                    , {field: 'identifyName', title: '批改方式'}
                    , {field: 'number', title: '分值', sortable: true}
                    , {field: 'expireTime', title: '截止日期', sortable: true}
                    , {
                        field:'',
                        title:'操作',
                        formatter:function formatter(value, row, index, field) {
                            if(row.pid != 0)
                                return '<a class="layui-btn layui-btn-xs edit" eid="'+row.id+'">编辑</a>' +
                                    '<a class="layui-btn layui-btn-xs delete" eid="'+row.id+'">删除</a>';
                            else
                                return '';
                        }}],
                responseHandler:function (res) {
                    if(res.total == 0){
                        return new Array();
                    }else{
                        return res.rows;
                    }
                },
                onLoadSuccess: function(data) {
                    $table.treegrid({
                        initialState: 'collapsed',//收缩
                        treeColumn: 0,//指明第几列数据改为树形
                        expanderExpandedClass: 'glyphicon glyphicon-triangle-bottom',
                        expanderCollapsedClass: 'glyphicon glyphicon-triangle-right',
                        onChange: function () {
                            $table.bootstrapTable('resetWidth');
                        }
                    });
                }
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
                    console.log($(".simditor-body"))
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
                el : '.CodeMirror',
                event : 'click',
                handler : function() {

                }
            },{
                el : '.fileupload',
                event : 'change',
                handler : function() {
                    var filePath=$(this).val().split("\\");
                    var files = $(this)[0].files;

                    console.log(files);
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
