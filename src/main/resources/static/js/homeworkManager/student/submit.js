require(['jquery','common/util', 'codemirror','codemirror/mode/clike/clike','jquery.form',
        'codemirror/addon/display/autorefresh'],
	function ($ , util , CodeMirror) {
	// 执行初始化函数
    var editor;
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

            form.render(null, 'component-form-group');

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

            var examId = $("#examId").val();
            var courseExamId = $("#courseExamId").val();
            var type ;
            //获取课程作业相关信息
            $.ajax({
                url:'/course/exam/queryExamById',
                type:'post',
                dataType:'json',
                data:{
                    examId:examId,
                    courseExamId:courseExamId
                },
                success:function(result){
                    if(result.code === 0) {
                        layer.msg(result.msg,{icon:2,time:1500});
                    } else{
                        var data = result.data;
                        $("#exam-content").html(data.examContent);
                        $("#exam-title").text(data.examTitle);
                        // initCode(data);
                        type = data.submitType;
                        if(data.filePath){
                            $(".file").show();
                            var paths = data.filePath.split("|");
                            var path = "teacher/homework/"+data.courseExamId+"/file/"+data.examId;
                            for(var i=0 ; i< paths.length ; i++){
                                var html =
                                    '<li class="cl pd-5">'+
                                    '<a title="'+paths[i]+'" target="_blank" class="mr-10" href="/download/'+path+'/'+paths[i]+'">'+
                                    ' <i class="layui-icon layui-icon-link"></i>'+
                                    '	 <span>'+paths[i]+'</span>'+
                                    ' </a>'+
                                    '<button class="btn btn-danger-outline size-MINI ml-10 remove existed" >'+
                                    ' <i class="layui-icon layui-icon-close"></i>'+
                                    '</button>'+
                                    '</li>';
                                $("#teacher-file-items").append(html);
                            }
                        }
                        //程序验证
                        if(type == 0){
                            $(".code").show();
                            initEdit();
                        }else{//提交文件
                            $(".upload-item").show();
                            $.ajax({
                                url:'student/homework/getUploadedFile',
                                type:'post',
                                dataType:'json',
                                data:{examId:examId},
                                success:function (result) {
                                    var data = result.data.nameList;
                                    var path = result.data.path;
                                    if(result.code === '1' && data.length>0){
                                        for(var i=0 ; i< data.length ; i++){
                                            var html =
                                                '<li class="cl pd-5">'+
                                                '<a title="'+data[i]+'" target="_blank" class="mr-10" href="/download/'+path+'/'+data[i]+'">'+
                                                ' <i class="layui-icon layui-icon-link"></i>'+
                                                '	 <span>'+data[i]+'</span>'+
                                                ' </a>'+
                                                '<button class="btn btn-danger-outline size-MINI ml-10 remove existed" >'+
                                                ' <i class="layui-icon layui-icon-close"></i>'+
                                                '</button>'+
                                                '</li>';
                                            $("#file-items").append(html);
                                        }
                                    }
                                }
                            })
                        }
                    }
                }
            })

            form.on('select(theme)', function(data){
                editor.setOption("theme",data.value);
            });

            /* 监听提交 */
            form.on('submit(component-form-demo1)', function(data){
                if(type === 0){
                    $("#status-block").show();
                    $("#status-content").text("submitting");
                    $.ajax({
                        url:'student/homework/checkCode',
                        data:{code:editor.getValue(),examId:examId},
                        type:'post',
                        dataType:'json',
                        success: function(result){
                            if(result.code === 0){
                                layer.msg(result.msg,{icon:2,time:1000});
                            }else{
                                var data = result.data;
                                $("#status-content").text(data);
                                if(data === "Accepted"){
                                    $(".ivu-tag-dot-inner").css("background","#19be6b");
                                }else{
                                    $(".ivu-tag-dot-inner").css("background","#f90");
                                }
                            }

                        }
                    })
                }else{//提交文件
                    $("#submit-form").ajaxSubmit({
                        url: 'student/homework/submitHomework',
                        type: 'post',
                        data:{},
                        success: function (data) {
                            if(data.code=='1'){
                                layer.msg(data.msg,{icon:1,time:1500});
                                layer.closeAll();
                            }else{
                                layer.msg(data.msg,{icon: 2});
                            }
                        }
                    });
                }

                return false;
            });
        });
	}


	function initCode(data) {
        var returnType = data.returnType;
        var parameterType = data.inputParameterType;
        var parameterName = data.inputParameterName;
        if(!returnType){
            return;
        }else{
            if(parameterName && parameterType){
                var parameter = "",types=parameterType.split("|"),names=parameterName.split("|");
                if(types.length !== names.length){
                    return;
                }else{
                    for(var i=0;i<types.length;i++){
                        var type = types[i].split(".");
                        parameter += type[type.length-1] + " " + names[i];
                    }
                    $("#code").replace("<parameter>",parameter);
                }
            }else{
                $("#code").replace("<parameter>","");
            }
        }

    }

	
	
	function initEdit(){
        //根据DOM元素的id构造出一个编辑器
		editor = CodeMirror.fromTextArea(document.getElementById("codeEdit"), {
            lineNumbers: true,	//显示行号
            theme: "solarized",	//设置主题
            lineWrapping: true,	//代码折叠,在长行时文字是换行(wrap)还是滚动(scroll)，默认为滚动(scroll)。
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
            matchBrackets: true,	//括号匹配
            indentUnit:4, //缩进单位
            styleActiveLine: true, // 当前行背景高亮
            autoCloseBrackets: true,
            mode: "text/x-java", //实现Java代码高亮
            autofocus:true,
            autorefresh:true
            //smartIndent:true //自动缩进
            //tabSize:4 //tab符宽度
            //electricChars:true  //在输入可能改变当前的缩进时，是否重新缩进，默认为true （仅在mode支持缩进时有效）
            //readOnly: true,        //只读
        });
        setTimeout(function () {
            editor.refresh();
        },200);
        if($("#code").text().indexOf('<code>') > -1){
            editor.setValue('public class Main{\n' +
                '    public static void main(String[] args) {\n' +
                '\n' +
                '    }\n' +
                '}');
        }else {
            editor.setValue($("#code").text());
        }
        //editor.refresh();
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
            el:'.remove',
            event:'click',
            handler: function(){
                var $that = $(this);
                var pathName = $that.prev("a").attr("href");
                pathName = pathName.substring(pathName.indexOf("download")+9);
                var examId = $("#examId").val();
                if(pathName != null && pathName.length>0 ){
                    layer.confirm('删除附件将无法恢复，您确定要删除吗？',{
                        title: '删除',
                        icon:3
                    },function(index){
                        $.ajax({
                            url:'student/homework/deleteFile',
                            type: 'post',
                            data:{pathName:pathName,examId:examId,existed:$that.hasClass("existed")},
                            success:function (result) {
                                if(result.code == 1){
                                    $that.parent("li").remove();
                                    layer.msg(result.msg , {icon:1,time:1500});
                                }else{
                                    layer.msg(result.msg , {icon:2,time:1500});
                                }

                            }
                        });
                        layer.close(index);
                    });
                }else{
                    $that.parent("li").remove();
                }
            }
        },{
            el : '#fileupload',
            event : 'change',
            handler : function() {
                var filePath=$(this).val().split("\\");
                var files = $(this)[0].files;
                for(var i in files){
                    var file = files[i];
                    var size = file.size;
                    if(size>157286400){
                        layer.msg('单个文件大小不能超过150MB',{icon:2,time:1500});
                        return;
                    }
                }
                $("#submit-form").ajaxSubmit({
                    url: 'student/homework/uploadFile',
                    type: 'post',
                    data:{},
                    success: function (data) {
                        if(data.code=='1'){
                            var map = data.data;
                            var path = map.path;
                            var filenames = map.filenames.split("|");
                            var sizes = map.sizes.split("|");
                            for(var i=0;i<filenames.length-1;i++){
                                var html =
                                    '<li class="cl pd-5">'+
                                    '<input type="hidden" name="filename" value="'+filenames[i]+'" >'+
                                    '<input type="hidden" name="size" value="'+sizes[i]+'" >'+
                                    '<a title="'+filename[i]+'" target="_blank" class="mr-10" href="/download/'+path+'/'+filename[i]+'">'+
                                    ' <i class="layui-icon layui-icon-link"></i>'+
                                    '	 <span>'+filenames[i]+'</span>'+
                                    ' </a>'+
                                    '<button class="btn btn-danger-outline size-MINI ml-10 remove">'+
                                    ' <i class="layui-icon layui-icon-close"></i>'+
                                    '</button>'+
                                    '</li>'
                                $("#file-items").append(html);
                            }
                            layer.msg(data.msg,{time:1500});
                        }else{
                            layer.msg(data.msg,{icon: 2});
                        }
                    }
                });
            }
        }])
	}

	});
