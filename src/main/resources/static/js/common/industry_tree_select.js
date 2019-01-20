require(['component/iframeLayer', 'component/dataTable','common/util', 'common/http', 'ztree','ztreeCheck','handlebars', 'jquery','jquery.serialize','laydate'], function (layer, dataTable, util, http, handlebars) {

    init();

    /**
     * 初始化函数集合
     */
    function init() {
        ztreeInit();
        bind();
    }

    var treeObj = null;

    //权限树配置
    var treeSetting = {
			check: {
				enable: true,
				chkboxType:{ "Y" : "s", "N" : "ps" }    //被勾选时：不关联父
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {}
    };

    function zTreeOnCheck(event, treeId, treeNode) {
    	var zTree = $.fn.zTree.getZTreeObj("permisionTree");
        var  	nodes = zTree.getSelectedNodes();
    	alert(nodes);
    };
    
    /**
     * 点击触发事件
     */
    function onClick(e, treeId, treeNode) {
    	var zTree = $.fn.zTree.getZTreeObj("permisionTree"),
    	nodes = zTree.getSelectedNodes(),
    	v = "";
    	c="";
    	nodes.sort(function compare(a,b){return a.id-b.id;});
    	for (var i=0, l=nodes.length; i<l; i++) {
    		v += nodes[i].name + ",";
    	    c+=nodes[i].id+",";
    	}
    	if (v.length > 0 ) v = v.substring(0, v.length-1);
    	 
      $("#values").text(v);
    }

    /**
     * 初始化树状结构
     */
    function ztreeInit() {
        http.httpRequest({
            url: '/admin/industrytype/industryTreeListJSON',
            data:{},	
            success: function (data) {
                treeObj = $.fn.zTree.init($('#permisionTree'), treeSetting, data); 
            }
        });
    }

    function bind() {
        util.bindEvents([{
            el: '#cancel',
            event: 'click',
            handler: function () {
                layer.close();
            }
        }, {
            el: '#save',
            event: 'click',
            handler: function () {
            	var nodes = treeObj.getCheckedNodes(true);
    			var array = "";
    			var names= new Array();
    			$.each(nodes, function(k, v) {
    				array += v.id + ",";
    				names[k]=v.name;
    			});
            	 var data=new Object();
            	 data.returnname=names.toString();
            	 
            	if(array!=null&&array!=""){
           		  array=array.substring(0,array.length-1); 
             	}
            	data.returncode=array;
            	layer.close(data);
            }
        }])
    }
})