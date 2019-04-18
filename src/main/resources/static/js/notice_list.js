require(['jquery','common/util','jquery.validate', 'jquery.serialize','bootstrap-table-treegrid',
        'bootstrap','bootstrap-table','bootstrap-table-CN','jquery.treegrid'],
    function ($ , util ) {

    init();

    function init(){
        initTable();
    }

    function initTable(){
        $("#notice-table").bootstrapTable({
            url:'/notice/queryList',
            method:'get',
            striped:true,    //开启条纹
            sidePagination:'server', ////分页方式：client客户端分页，server服务端分页（*）
            idField:'id',
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            sortName:'insertTime',
            sortOrder:'desc',
            pagination: true, //是否分页
            strictSearch: false,
            showColumns: false,                  //是否显示所有的列
            showRefresh: false,                  //是否显示刷新按钮
            columns: [{
                field: 'title',
                title: '标题内容',
                formatter:function (value, row) {
                    var href = "/download/teacher/homework/"+row.id+"/answer/"+answerPath;
                    if(row.readStatus === 1){
                        return '<a href="'+href+'">'+
                            value+'</a>';
                    }else {
                        return '<a class="non-readed" href="'+href+'">'+
                            value+'</a>';
                    }

                }
            }, {
                field: 'insertTime',
                title: '时间',
                formatter:function (value, row) {
                    if(row.readStatus === 1){
                        return value;
                    }else {
                        return '<span class="non-readed">'+ value+'</span>';
                    }
                }
            }, {
                field: 'sendUser',
                title: '发送者',
                formatter:function (value, row) {
                    if(row.readStatus === 1){
                        return value;
                    }else {
                        return '<span class="non-readed">'+ value+'</span>';
                    }
                }
            }],
            onLoadSuccess:function (data) {
                var notice_num = 0;
                for(var i=0;i<data.length;i++){
                    if(data.readStatus === 1 && data.sendUser === 'admin'){
                        notice_num++;
                    }
                }
                $(".layui-badge").text(notice_num);
            }
        })
    }


        layui.use(['admin', 'table', 'util'], function () {
            var $ = layui.$
                , admin = layui.admin
                , element = layui.element;


            var DISABLED = 'layui-btn-disabled'

                //区分各选项卡中的表格
                , tabs = {
                    all: {
                        text: '全部消息'
                        , id: 'LAY-app-message-all'
                    }
                    , notice: {
                        text: '通知'
                        , id: 'LAY-app-message-notice'
                    }
                    , direct: {
                        text: '私信'
                        , id: 'LAY-app-message-direct'
                    }
                };
        });
    })