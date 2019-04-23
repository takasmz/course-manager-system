require(['jquery','common/util','codemirror','jquery.validate', 'jquery.serialize','bootstrap-table-treegrid',
        'bootstrap','bootstrap-table','bootstrap-table-CN','jquery.treegrid',,'codemirror/mode/clike/clike','jquery.form',
        'codemirror/addon/display/autorefresh'],
	function ($ , util ,CodeMirror) {
    var editor;
	// 执行初始化函数
	init();

	/**
	 * 初始化集合
	 */
	function init() {
        window.recordOpearteEvents = {
            'click .record': function(e, value, row, index) {
                layer.open({
                    content:result,
                    type: 1,
                    area:['80%','720px'],
                    title:'提交代码'
                });
            }
        };
        util.getCourseList();
	    initTag();
		bind();
	}

    function initTable(courseId){
	    var $table = $("#record-table");
        $table.bootstrapTable({
            url: 'student/homework/getRecordList?courseId='+courseId,
            method: 'get',
            // ajaxOptions: {}, //传递参数
            striped:true,    //开启条纹
            sidePagination:'server', ////分页方式：client客户端分页，server服务端分页（*）
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
            showRefresh: true,                  //是否显示刷新按钮
            columns: [
                {field: 'submitTimeStr', title: '提交时间'},
                {field: 'examTitle', title: '作业标题'}
                , {
                    field: 'status',
                    title: '提交状态',
                    formatter:function (value, row, index, field) {
                        switch (row.status) {
                            case 0:return "正常提交";
                            case 1:return "迟交";
                            case 2:return "未交";
                            default:return "未交";
                        }
                    }
                }, {
                    field:'',
                    title:'提交结果',
                    formatter: function formatter(value, row, index, field) {
                            if(row.submitType === 0){
                                if(row.result === 'Accepted'){
                                    return '<a  examTitle="'+row.examTitle+'" errorMsg="'+row.error+'" submitContent="'+row.submitContent+'"  class="record ac">编译通过</a>';
                                }else{
                                    return '<a  class="record error">编译出错</a>';
                                }
                            }else{
                                if(row.result === 'delete'){
                                    return '<span class="error">删除文件</span>';
                                }else{
                                    return '<span class="ac">上传文件</span>';
                                }
                            }
                        },
                    events: {
                        'click .record': function(e, value, row, index) {
                            var html = $("#detail").html();
                            layer.open({
                                content:html,
                                type: 1,
                                area:['80%','720px'],
                                title:'提交代码',
                                success: function(layero, index){
                                    initEdit(row.submitContent);
                                    $("#result_state").text(row.result);
                                    $(".exam-title").text(row.examTitle);
                                    if(row.result !== 'Accepted'){
                                        $("#result_state").addClass("text-danger");
                                        if(row.result === 'Wrong Answer'){
                                            $("#result_progress").text(row.totalCorrect + "/" + row.totalTestcases);
                                            var errors = row.error.split(","),input = errors[0],output = errors[1],expected = errors[2];
                                            $("#result_wa_testcase_input").text(input);
                                            $("#result_wa_testcase_output").text(output);
                                            $("#result_wa_testcase_expected").text(expected);
                                            $("#wa_output").show();
                                            $("#result_progress_row").show();
                                        }else{
                                            $("#result_code_output").text(row.error);
                                            $("#result_code_output_row").show();
                                        }
                                    } else{
                                        $("#result_progress").text(row.totalCorrect + "/" + row.totalTestcases);
                                        $("#result_progress_row").show();
                                        $("#result_state").addClass("text-success");
                                    }

                                    $(".result_date").text(row.submitTimeStr);
                                    $(".submit").bind("click",function () {
                                        $.ajax({
                                            url:'/views/homeworkManager/student/submit?examId=' + row.examId,
                                            type:'get',
                                            dataType:'html',
                                            success:function(result){
                                                result = result.replace("<code>",row.submitContent);
                                                console.log(result);
                                                layer.open({
                                                    content:result,
                                                    type: 1,
                                                    area:['80%','720px'],
                                                    title:'提交代码',
                                                    success:function (layero, index) {

                                                    }
                                                });
                                            }
                                        });
                                        layer.close(index);
                                    })
                                }
                            });

                        }
                    },
                }],
            onLoadSuccess: function(data) {
            }
        });
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
            initTable(courseId);
            element.on('tab(change)', function(data) {
                courseId = $(this).attr("cid");
                $(".layui-tab-item").each(function(index){
                    if(index === data.index){
                        $(this).html($("#tab-demo").html());
                    }else{
                        $(this).html("");
                    }
                });
                initTable(courseId);
            });
        });
	}

	/**
	 * 事件绑定
	 */
	function bind() {
		util.bindEvents([ {
			el:'ivu-tooltip-rel',
			event:'moveenter',
			handler:function(){
				$(".ivu-tooltip-inner").show();
			}
		},{
			el:'ivu-tooltip-rel',
			event:'moveleave',
			handler:function(){
				$(".ivu-tooltip-inner").hide();
			}
		} ])
	}

	});