require(['common/util', 'component/iframeLayer', 'common/http', 'handlebars', 'layer1', 'ztree','ztreeCheck'], function (util, layer, http, handlebars) {
	init();
    /**
     * 初始化函数集合
     */
    function init() {
        loadTree();
        bind();
    }

    //区域树对象
    var treeObj = null;

    //加载区域树
    function loadTree() {
        var treeUrl = window._CONFIG.treeUrl;
        //区域树配置
        var setting = {
            check: {
                enable: true,
                chkStyle: "radio",
                autoCheckTrigger: true,
                radioType: "all"
            },
            data: {
                key: {
                    name: "areaName",
                    title: "areaName"
                },
                simpleData: {
                    enable: true,
                    idKey: "areaCode",
                    pIdKey: "parentId"

                }
            }
        };
        http.httpRequest({
            type: 'post',
            url: treeUrl,
            success: function (data) {
                treeObj = $.fn.zTree.init($('#areaTree'), setting, data);
                var tree = $.fn.zTree.getZTreeObj("areaTree");
                beforeExpand(tree.getNodes(),tree);
            }
        });
    }
    //区域树展开时事件
    function beforeExpand(treeNodes,tree) {
        if(treeNodes!=null){
            hideNullNode(treeNodes,tree);
        }
    }

    /**
     * 以递归的形式隐藏子节点为空的节点
     * @param treeNode
     * @param treeObj
     */
    function hideNullNode(treeNode,treeObj){
        for(var i in treeNode){
            if(treeNode[i].children!=null){
                hideNullNode(treeNode[i].children,treeObj);
            }else{
                if(treeNode[i].isParent==true){
                    /*treeObj.hideNode(treeNode[i]);*/
                }
            }
        }
    }

    function bind() {
        util.bindEvents([
            {
                el: '#areaSubmit',
                event: 'click',
                handler: function () {
                    var data = new Object();
                    var treeObj = $.fn.zTree.getZTreeObj("areaTree");
                    var nodes = treeObj.getCheckedNodes(true);
                    if (nodes.length == 0) {
                        layer.msg("请选择区域", {ltype: 0, time: 2000});
                        return false;
                    } else {
                        data.areaName = nodes[0].areaName;
                        data.areaCode = nodes[0].areaCode;
                        layer.close(data);
                    }
                }
            },
            {
                el: '#cancle',
                event: 'click',
                handler: function () {
                    layer.close();
                }
            }
        ])
    }
})