require(['jquery','common/util','pdfobject'],function ($,util,PDFObject) {

    init();
    function init() {
        util.getCourseList(initTab);
    }

    function initTab() {
        layui.use(['element'], function(){
            var element = layui.element;
            element.on('tab(change)', function(data) {
                var courseId = $(this).attr("cid");
                $(".layui-tab-item").each(function(index){
                    if(index == data.index){
                        $(".layui-tab-item").eq(index).append("<div id='pdf-container'></div>");
                        $.ajax({
                            url:'course/getSyllabus',
                            type:'post',
                            dataType:'json',
                            data:{courseId:courseId},
                            complete:function (result) {
                                var height = $(window).height()-50-55-30-20-43-30;
                                $("#pdf-container").height(height);
                                PDFObject.embed(result.responseText, "#pdf-container");
                            }
                        })
                    }else{
                        $(this).html("");
                    }
                });
            });
            $(".layui-tab-title").children("li").eq(0).trigger("click");
        });

    }
})