var deps = ['jquery','common/util', 'marked',
    "editormd.amd",
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

    var msg;
    init();
    
    function init() {
        util.getCourseList(initTag);
        //initTag();
    }



    function initTag() {
        $(".layui-tab-title").append('<li class="" style="float:right;color: cornflowerblue;">编辑</li>');
        layui.use(['element'], function(){
            var element = layui.element;
            element.on('tab(change)', function(data) {
                var courseId = $(this).attr("cid");
                if(typeof courseId == "undefined"){
                    initEditorMd();
                }
                $(".layui-tab-item").each(function(index){
                    if(index == data.index){
                        getCourseIntro(courseId,index);
                    }else{
                        $(this).html("");
                    }
                });
            });
            $(".layui-tab-title").children("li").eq(0).trigger("click");
            //getCourseIntro(first,0);
        });
    }
    
    function getCourseIntro(courseId,index) {
        $.ajax({
            url:'course/getCourseIntro',
            type:'post',
            dataType:'json',
            data:{courseId:courseId},
            complete:function (XMLHttpRequest) {
                var result = XMLHttpRequest.responseJSON;
                msg = result.data.data;
                $(".layui-tab-item").eq(index).append("<div id='editor' class='intro-content'>"+marked(result.data.data)+"</div>");
                $(".intro-content").find("img").each(function (index,element) {
                    var src = $(element).attr("src");
                    $(element).attr("src",result.data.path + src);
                })
            }
        })
    };

        editormd.loadCSS("/js/lib/codemirror/addon/fold/foldgutter");

        function initEditorMd() {
            console.log(msg)
            var Editor = editormd("editor", {
                width: "90%",
                height: 640,
                path : 'js/lib/editormd/lib/',
                markdown : msg,
                codeFold : true,
                searchReplace : true,
                saveHTMLToTextarea : true,                // 保存HTML到Textarea
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
                imageUploadURL : "/course/courseExam/uploadImg",
                onload : function() {
                    console.log('onload', this);
                    //this.fullscreen();
                    //this.unwatch();
                    //this.watch().fullscreen();

                    //this.setMarkdown("#PHP");
                    //this.width("100%");
                    //this.height(480);
                    //this.resize("100%", 640);
                }
            });
        };

        $("#show-btn").bind('click', function(){
            testEditor.show();
        });

        $("#hide-btn").bind('click', function(){
            testEditor.hide();
        });

        $("#get-md-btn").bind('click', function(){
            alert(testEditor.getMarkdown());
        });

        $("#get-html-btn").bind('click', function() {
            alert(testEditor.getHTML());
        });

        $("#watch-btn").bind('click', function() {
            testEditor.watch();
        });

        $("#unwatch-btn").bind('click', function() {
            testEditor.unwatch();
        });

        $("#preview-btn").bind('click', function() {
            testEditor.previewing();
        });

        $("#fullscreen-btn").bind('click', function() {
            testEditor.fullscreen();
        });

        $("#show-toolbar-btn").bind('click', function() {
            testEditor.showToolbar();
        });

        $("#close-toolbar-btn").bind('click', function() {
            testEditor.hideToolbar();
        });

        $("#toc-menu-btn").click(function(){
            testEditor.config({
                tocDropdown   : true,
                tocTitle      : "目录 Table of Contents",
            });
        });

        $("#toc-default-btn").click(function() {
            testEditor.config("tocDropdown", false);
        });


})