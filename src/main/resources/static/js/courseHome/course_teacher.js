var deps = ['jquery','common/util', 'marked',
    "editormd",
    "editormd/languages/en",
    "editormd/plugins/link-dialog/link-dialog",
    "editormd/plugins/reference-link-dialog/reference-link-dialog",
    "editormd/plugins/image-dialog/image-dialog",
    "editormd/plugins/code-block-dialog/code-block-dialog",
    "editormd/plugins/table-dialog/table-dialog",
    "editormd/plugins/emoji-dialog/emoji-dialog",
    "editormd/plugins/goto-line-dialog/goto-line-dialog",
    "editormd/plugins/help-dialog/help-dialog",
    "editormd/plugins/html-entities-dialog/html-entities-dialog",
    "editormd/plugins/preformatted-text-dialog/preformatted-text-dialog"
];
require(deps,
    function ($ , util , marked , editormd) {

    var msg,path,tabIdenx,Editor,intro;
    init();
    
    function init() {
        util.getCourseList(initTag);
        bind();
        //initTag();
    }



    function initTag() {
        $(".layui-tab-title").append('<li class="operation" style="float:right;color: cornflowerblue;cursor:none;"><button class="layui-btn layui-btn-sm editIntro">编辑</button></li>');
        layui.use(['element'], function(){
            var element = layui.element;
            element.on('tab(change)', function(data) {
                var courseId = $(this).attr("cid");
                if(typeof courseId == "undefined"){
                    $(this).removeClass("layui-this");
                    $(".layui-tab-title").children("li").eq(tabIdenx).addClass("layui-this");
                    return;
                }else{
                    $(".operation").html('<button class="layui-btn layui-btn-sm editIntro">编辑</button>');
                    $(".layui-tab-item").each(function(index){
                        if(index == data.index){
                            getCourseTeacher(courseId,index);
                        }else{
                            $(this).html("");
                        }
                    });
                }
                tabIdenx = data.index;
            });
            $(".layui-tab-title").children("li").eq(0).trigger("click");
            //getCourseTeacher(first,0);
        });
    }
    
    function getCourseTeacher(courseId,index) {
        $.ajax({
            url:'course/getCourseIntro',
            type:'post',
            dataType:'json',
            data:{courseId:courseId,type:'teachers'},
            complete:function (XMLHttpRequest) {
                var result = XMLHttpRequest.responseJSON;
                msg = result.data.data;
                path = result.data.path;
                intro = marked(result.data.data);
                $(".layui-tab-item").eq(index).append("<div id='editor' class='intro-content'>"+intro+"</div>");
            }
        })
    };

        editormd.loadCSS("/js/lib/codemirror/addon/fold/foldgutter");

        function initEditorMd(courseId) {
            Editor = editormd("editor", {
                width: "90%",
                height: 640,
                path : 'js/lib/editormd/lib/',
                markdown : msg,
                codeFold : true,
                searchReplace : true,
                saveHTMLToTextarea : false,                // 保存HTML到Textarea
                htmlDecode : "style,script,iframe|on*",       // 开启HTML标签解析，为了安全性，默认不开启
                emoji : true,
                taskList : true,
                tex : true,
                tocm            : true,         // Using [TOCM]
                autoLoadModules : false,
                previewCodeHighlight : true,
                flowChart : true,
                sequenceDiagram : true,
                //dialogLockScreen : false,   // 设置弹出层对话框不锁屏，全局通用，默认为true
                //dialogShowMask : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
                //dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
                //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
                //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
                imageUpload : true,
                imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
                imageUploadURL : "/course/courseExam/uploadImg?type=courseIntro&courseId="+courseId,
                onload : function() {
                    this.watch();
                }
            });
        };

        function bind(){
            util.bindEvents([ {
                el:'.editIntro',
                event:'click',
                handler: function () {
                    $(this).parent("li").html('<button class="layui-btn layui-btn-normal layui-btn-sm saveIntro">保存</button><button class="layui-btn layui-btn-sm layui-btn-warm exit">取消</button>')
                    $("#editor").html("");
                    var courseId = $(".layui-tab-title").children("li").eq(tabIdenx).attr("cid");
                    initEditorMd(courseId);
                    
                }
            },{
                el:'.saveIntro',
                event:'click',
                handler: function () {
                    var md = Editor.getMarkdown();
                    var mdHtml = Editor.getHTML();
                    var courseId = $("li.layui-this").attr("cid");
                    console.log(courseId);
                    $.ajax({
                        url:'course/saveIntro',
                        type:'post',
                        dataType:'json',
                        data:{md:md,courseId:courseId,type:'teachers'},
                        success:function (result) {
                            if(result.code == 1){
                                layer.msg("修改课程介绍成功",{icon:1,time:1500});
                                $("#editor").html(mdHtml);
                                $(".operation").html('<button class="layui-btn layui-btn-sm editIntro">编辑</button>');
                            }else{
                                layer.msg("修改课程介绍失败",{icon:2,time:1500});
                            }
                        }
                    })
                }
            },{
                el:'.exit',
                event:'click',
                handler:function () {
                    $("#editor").html(intro);
                    $(".operation").html('<button class="layui-btn layui-btn-sm editIntro">编辑</button>');
                }
            }])
        }

})