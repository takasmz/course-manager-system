require(['jquery','common/util','jquery.validate', 'jquery.serialize','bootstrap-table-treegrid',
        'bootstrap','bootstrap-table','bootstrap-table-CN','jquery.treegrid'],
    function ($ , util ) {
        // 执行初始化函数
        init();
        var $table = $("#table");
        var Table,editor,courseExamId = "";
        /**
         * 初始化集合
         */
        function init() {
            window.opearteEvents = {
                'click .layui-btn': function(e, value, row, index) {
                    var event = $(e.target).attr("lay-event");
                    if(event === 'edit'){
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
                    } else if(event === 'del'){
                        layer.confirm('确定删除该作业内容吗', function(index){

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
            Table = $table.bootstrapTable({
                query:{
                    courseExamId:$("#courseExamId").val()
                },
                url:'course/courseExam/editHomeworkList',
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