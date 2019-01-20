require(['jquery','common/util','bootstrap','bootstrap-table','bootstrap-table-CN','common/myFont'],
    function ($ , util ) {
        // 执行初始化函数
        init();

        /**
         * 初始化集合
         */
        function init() {
            window.opearteEvents = {
                'click .download': function(e, value, row, index) {
                    $.ajax({
                        url:''
                    })
                }
            };
            initTable();
            bind();
        }

        function initTable(){
            var $table = $("#table");
            $table.bootstrapTable({
                url: 'course/courseExam/queryHistoryHomeworkList',
                method: 'post',
                striped:true,    //开启条纹
                contentType:'application/x-www-form-urlencoded',
                function(params) {
                    var data = {};
                    params.data = data;
                    return params;
                },
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
                columns: [
                    {field: '', title: '序号',
                        formatter: function (value, row, index) {
                            var pageSize = $table.bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                            var pageNumber = $table.bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                            return pageSize * (pageNumber - 1) + index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
                        }}
                    , {field: 'date', title: '时间'}
                    , {field: 'courseName', title: '课程名称'}
                    , {field: 'courseExamName', title: '作业名称'}
                    , {field: '', title: '作业文件',
                        formatter: function (value, row, index) {
                            var href = "course/courseExam/downloadHistoryHomework?courseExamId="+row.id+"&courseExamName="+ row.courseName + row.courseExamName;
                            return '<a href="'+href+'"><svg class="icon icon-file download" aria-hidden="true">' +
                                '<use xlink:href="#icon-PDF"></use>' +
                                '</svg>';
                        }}
                    //, {field: 'creator', title: '创建人'}
                ],
                onLoadSuccess: function(data) {
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