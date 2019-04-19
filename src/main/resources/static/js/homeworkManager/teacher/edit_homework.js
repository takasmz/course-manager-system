require(['jquery','common/util','bootstrap-table-treegrid',
        'bootstrap','bootstrap-table','bootstrap-table-CN','jquery.treegrid'],
    function ($ , util ) {

        // 执行初始化函数
        init();

        /**
         * 初始化集合
         */
        function init() {
            window.opearteEvents = {
                'click .layui-btn': function(e, value, row) {
                    var event = $(e.target).attr("lay-event");
                    if($(e.target).hasClass('edit')){
                        $.ajax({
                            url:'/views/homeworkManager/teacher/edit_view?examId=' + row.id + '&examName=' + row.name +'&courseExamId=' + row.pid,
                            type:'get',
                            dataType:'html',
                            success:function(result){
                                layer.open({
                                    content:result,
                                    type: 1,
                                    area:['80%','720px'],
                                    title:'作业编辑'
                                });
                            }
                        })
                    } else if($(e.target).hasClass('delete')){
                        layer.confirm('确定删除该作业内容吗', function(index){
                            $.ajax({
                               url:'/course/exam/deleteExamById',
                               type:'post',
                               dataType:'json',
                                data:{examId:row.id},
                                success:function (result) {
                                    if(result.code === 1){
                                        layer.msg(result.msg,{icon:1});
                                        $table.bootstrapTable('refresh');
                                    }else{
                                        layer.msg(result.msg,{icon:2});
                                    }
                                }
                            });
                            layer.close(index);
                        });
                    } else if($(e.target).hasClass('add')){
                        $.ajax({
                            url:'/views/homeworkManager/teacher/edit_view?examId=-1&examName=' + row.name +'&courseExamId=' + row.pid,
                            type:'get',
                            dataType:'html',
                            success:function(result){
                                layer.open({
                                    content:result,
                                    type: 1,
                                    area:['80%','720px'],
                                    title:'新增作业'
                                });
                            }
                        })
                    }
                }
            };
            initTable();
            //initTag()
            bind();
        }

        function initTable(){
            var $table = $("#table");
            $table.bootstrapTable({
                url:'course/courseExam/editHomeworkList',
                method: 'get',
                striped:true,    //开启条纹
                sidePagination:'server', ////分页方式：client客户端分页，server服务端分页（*）
                idField:'id',
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 10,                       //每页的记录行数（*）
                pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                sortName:'stauts',
                sortOrder:'desc',
                pagination: true, //是否分页
                strictSearch: true,
                showColumns: true,                  //是否显示所有的列
                showRefresh: true,                  //是否显示刷新按钮
                treeShowField: 'name',
                parentIdField: 'pid',
                columns: [
                    {field: 'name', title: '课程/作业名称',width:'15%'}
                    , {field: 'examContent', title: '作业内容',width:'50%'}
                    , {field: 'submitName', title: '提交方式',width:'5%'}
                    , {field: 'identifyName', title: '批改方式',width:'5%'}
                    , {field: 'number', title: '分值', sortable: true,width:'3%'}
                    , {field: 'expireTime', title: '截止日期', sortable: true,width:'8%'}
                    , {
                        field:'',
                        title:'操作',
                        width:'12%',
                        class:'operation',
                        events: opearteEvents,
                        formatter:function formatter(value, row, index, field) {
                            if(row.pid !== 0)
                                return '<a class="layui-btn layui-btn-normal layui-btn-radius layui-btn-xs addTest" eid="'+row.id+'">添加测试用例</a>' +
                                    '<a class="layui-btn layui-btn-xs layui-btn-radius edit" eid="'+row.id+'">编辑</a>' +
                                    '<a class="layui-btn layui-btn-danger layui-btn-radius layui-btn-xs delete" eid="'+row.id+'">删除</a>';
                            else{
                                if(util.convertDateFromString(row.expireTime) > new Date())
                                    return '<a class="layui-btn layui-btn-xs add" eid="'+row.id+'">添加题目</a>';
                                else{
                                    return "";
                                }
                            }

                        }}],
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


        /**
         * 事件绑定
         */
        function bind() {
            util.initBindEvents();
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