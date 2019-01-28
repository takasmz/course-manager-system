require(['jquery','common/util','jquery.validate', 'jquery.serialize','bootstrap-table-treegrid',
        'bootstrap','bootstrap-table','bootstrap-table-CN','jquery.treegrid'],
	function ($ , util ) {
	// 执行初始化函数
	init();

	/**
	 * 初始化集合
	 */
	function init() {
        window.opearteEvents = {
            'click .layui-btn': function(e, value, row, index) {
                var event = $(e.target).attr("lay-event");
                if(event === 'detail'){
                    layer.msg('ID：'+ row.id + ' 的查看操作');
                } else if(event === 'del'){
                    layer.confirm('真的删除行么', function(index){
                        layer.close(index);
                    });
                } else if(event === 'submit'){
                    $.ajax({
                        url:'/views/homeworkManager/student/submit?examId=' + row.id + '&examName=' + row.name +'&courseExamId=' + row.pid,
                        type:'get',
                        dataType:'html',
                        success:function(result){
                            layer.open({
                                content:result,
                                type: 1,
                                area:['80%','720px'],
                                title:'提交代码'
                            });
                        }
                    })

                }
            }
        };

        util.getCourseList(initTag);
	    //initTag()
		bind();
	}

    function initTable(courseId){
	    var $table = $("#test-table-operate");
        $table.bootstrapTable({
            url: 'course/courseExam/queryExamList?courseId='+courseId,
            method: 'get',
            // ajaxOptions: {}, //传递参数
            striped:true,    //开启条纹
            sidePagination:'server', ////分页方式：client客户端分页，server服务端分页（*）
            idField:'id',
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            sortName:'expireTime',
            sortOrder:'desc',
            pagination: true, //是否分页
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            treeShowField: 'name',
            parentIdField: 'pid',
            columns: [
                {
                    field: 'name',
                    title: '课程/作业名称',
                    formatter:function (value, row) {
                        var answerPath = row.answerPath;
                        if(typeof answerPath === 'undefined' || answerPath === null
                        || row.showAnswerTime > new Date()){
                            return value;
                        }else{
                            var href = "/download/teacher/homework/"+row.id+"/answer/"+answerPath;
                            return '<a href="'+href+'">'+
                                value+'</a>';
                        }
                    }
                }
                , {field: 'examContent', title: '作业内容'}
                , {field: 'submitName', title: '提交方式'}
                , {field: 'identifyName', title: '批改方式'}
                , {field: 'number', title: '分值', sortable: true}
                , {field: 'expireTime', title: '截止日期', sortable: true}
                , {
                    field:'',
                    title:'完成状态',
                    events: opearteEvents,
                    formatter:
                        function formatter(value, row, index, field) {
                            var status;
                            var html =
                                '<a class="layui-btn layui-btn-primary layui-btn-xs ml-10"  lay-event="detail">查看</a>' +
                                '<a class="layui-btn layui-btn-xs" lay-event="submit">提交</a>' +
                                '';
                                //'<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>'
                            if(row.pid != 0){
                                if(row.finished > 0){
                                    status = "<i class='layui-icon layui-icon-ok icon-green right mr-10'>已提交</i>";
                                }else{
                                    status = "<i class='layui-icon layui-icon-about icon-yellow right mr-10'>未提交</i>";
                                }
                                return html + status;
                            }else{
                                if(row.total != null && typeof (row.total) != 'undefined'){
                                    if(row.finished == row.total){
                                        status = "<i class='layui-icon layui-icon-ok icon-green right'>已完成</i>";
                                    }else{
                                        status = "<i class='layui-icon layui-icon-about icon-yellow right'>未完成</i>";
                                    }
                                    return "<span>"+ row.finished + "/" + row.total + status + "</span>";
                                }else{
                                    return "";
                                }
                            }


                    }} ],
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




    function initTag(){
        layui.use(['element'], function(){
            var table = layui.table,
                element = layui.element;

            var courseId = -1;
            initTable(courseId);
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