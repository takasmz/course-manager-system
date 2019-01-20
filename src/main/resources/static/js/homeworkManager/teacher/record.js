require(['jquery','common/util','jquery.validate', 'jquery.serialize','bootstrap-table-treegrid',
        'bootstrap','bootstrap-table','bootstrap-table-CN','jquery.treegrid',],
	function ($ , util ,CodeMirror) {
    var editor;
	// 执行初始化函数
	init();

	/**
	 * 初始化集合
	 */
	function init() {
        util.getCourseList(initTag);
		bind();
	}

    function initTable(courseId){
	    var $table = $("#record-table");
	    $.ajax({
            url: 'teacher/homework/getStudentRecord?courseId='+courseId,
            type:'post',
            dataType:'json',
            data:{
                courseId:courseId,
                offset:0,
                limit:10
            },
            success:function (result) {
                var columns = [];
                var num =  result.rows[0].statusDtoList.length;
                columns.push({'field':'studentName','title':'姓名学号'});
                for(var i=0 ; i<num; i++){
                    columns.push({
                        field:i,
                        title:'第'+(i+1)+'次作业',
                        formatter:function formatter(value, row, index, field) {
                            var statusDto = row.statusDtoList[field];
                            if(statusDto.total === statusDto.finished && statusDto.submitStatus === 0){
                                return "<i class='layui-icon layui-icon-ok-circle icon-green' /><span class='label-text icon-green'>正常</span>";
                            }else if(statusDto.submitStatus === null || typeof statusDto.submitStatus === 'undefined'){
                                return "<i class='layui-icon layui-icon-close-fill icon-red submit-error' cid="+statusDto.courseExamId+" sid="+statusDto.studentId+" />" +
                                    "<span class='label-text icon-red submit-error' cid="+statusDto.courseExamId+" sid="+statusDto.studentId+">未交</span>";
                            }else{
                                return "<i class='layui-icon layui-icon-about icon-yellow submit-error' cid="+statusDto.courseExamId+" sid="+statusDto.studentId+"/>" +
                                    "<span class='label-text icon-yellow submit-error' cid="+statusDto.courseExamId+" sid="+statusDto.studentId+">迟交</span>";
                            }
                        }
                    });
                }
                $table.bootstrapTable({
                    data:result.rows,
                    // ajaxOptions: {}, //传递参数
                    striped:true,    //开启条纹
                    sidePagination:'client', ////分页方式：client客户端分页，server服务端分页（*）
                    idField:'id',
                    pageNumber:1,                       //初始化加载第一页，默认第一页
                    pageSize: 10,                       //每页的记录行数（*）
                    pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                    //search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                    sortName:'submitTimeStr',
                    sortOrder:'asc',
                    // detailView: true,
                    pagination: true, //是否分页
                    strictSearch: true,
                    undefinedText:'该课程暂无提交记录',
                    //showRefresh: true,                  //是否显示刷新按钮
                    columns: columns,
                    toolbar:'#toolbar',
                    onLoadSuccess: function(data) {
                    }
                });
            }
        })

    }


        function initEdit(value){
	        console.log(value);
            //根据DOM元素的id构造出一个编辑器
            editor = CodeMirror.fromTextArea(document.getElementById("code-textarea"), {
                lineNumbers: true,	//显示行号
                //value:value,
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
                autorefresh:true,
                //smartIndent:true //自动缩进
                //tabSize:4 //tab符宽度
                //electricChars:true  //在输入可能改变当前的缩进时，是否重新缩进，默认为true （仅在mode支持缩进时有效）
                readOnly: true,        //只读
            });
            editor.setValue(value);
            // setTimeout(function () {
            //     editor.refresh();
            //
            // },200)
            //editor.refresh();
        }


    function initTag(){
        layui.use(['element'], function(){
            var table = layui.table,
                element = layui.element;
            var courseId = "";
            //initTable(courseId);
            element.on('tab(change)', function(data) {
                courseId = $(this).attr("cid");
                $(".layui-tab-item").each(function(index){
                    if(index == data.index){
                        $(this).html($("#tab-demo").html());
                    }else{
                        $(this).html("");
                    }
                });
                initTable(courseId);
            });
        });
        setTimeout(function () {
            $(".layui-tab-title li").eq(0).trigger("click");
        },300);
	}

	/**
	 * 事件绑定
	 */
	function bind() {
		util.bindEvents([{
		    el:'.submit-error',
            event:'mouseenter',
            handler:function () {
                var cid = $(this).attr("cid");
                var sid = $(this).attr("sid");
                var that = this;
                $.ajax({
                    url:'teacher/homework/queryHomeworkRecordDetail',
                    type:'post',
                    dataType:'json',
                    data:{
                        courseExamId:cid,
                        studentId:sid
                    },
                    success: function (result) {
                        if(result.code === 1){
                            var html = '<ul>';
                            for(var i = 0; i < result.data.length ; i++){
                                var data = result.data[i];
                                var status ;
                                if(data.stauts === null || typeof data.stauts == 'undefined' || data.stauts == 2){
                                    status = '<span class="label-text icon-red" >未交</span>';
                                }else if(data.stauts == 1){
                                    status = '<span class="label-text icon-yellow" >迟交</span>';
                                }else{
                                    status = '<span class="label-text icon-green">正常</span>';
                                }
                                html += '<li>'+data.examTitle+'&nbsp;&nbsp;&nbsp;&nbsp;'+status+'</li>';
                            }
                            html += "</ul>";
                            layer.tips(html,that);
                        }
                    }
                })
            }
        }])
	}

	});