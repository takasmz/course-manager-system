require(['component/iframeLayer', 'component/dataTable','common/util', 'common/http', 'ztree','ztreeCheck','handlebars', 'jquery','jquery.serialize','laydate'], function (layer, dataTable, util, http, handlebars) {
	// 树对象
	var treeObj = null;
	

	// 树配置
	var treeSetting = {
		check: {
            enable: true,
            chkStyle: "radio",
            autoCheckTrigger: true,
            radioType: "all"
        },
		data : {
			simpleData : {
				enable : true,
				idKey : 'id',
				pIdKey : 'pId',
				rootPId : ''
			}
		}
	};
	init() 
	
	/**
     * 初始化函数集合
     */
    function init() {
        ztreeInit();
        bind();
    }
	
	/**
     * 点击触发事件
     */
    function onClick(e, treeId, treeNode) {
    	var zTree = $.fn.zTree.getZTreeObj("subjectTree"),
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
	// 加载栏目数据
	function ztreeInit() {
		http.httpRequest({
			type : 'post',
			url : '/admin/websiteSubject/queryAll',
			success : function(data) {
				if (data.status == 'success') {
					// 初始化树
					treeObj = $.fn.zTree.init($('#subjectTree'), treeSetting,
							data.data);
					treeObj.expandAll(true)
					var checked = $('#checked').val();
			        var node = treeObj.getNodes();
			        var nodes = treeObj.transformToArray(node);
			        if(checked!=null&&checked.length>0){
			        	var checkedItemIds = checked.split(",");
			        	for(var j=0;j< checkedItemIds.length;j++){
			        		var id = checkedItemIds[j];
			        		for (var i=0, l=nodes.length; i < l; i++) {
			                	if(nodes[i].id==id){
			                    	treeObj.checkNode(nodes[i], true, true);
			                    	break;
			                	}
			                }
			        	}
			        }
				}else{
					layer.msg(data.msg, {
						time : 2000
					});
				}
			},
			error : function(text){
				layer.msg("程序错误", {
					time : 2000
				});
			}
		});
	}


	
	// 获取指定节点的所有子节点id
	function getAllChildrenNodes(treeNode, result){
		if(result == ""){
			result = result + treeNode.id;
		}
		if (treeNode.isParent) {
			var childrenNodes = treeNode.children;
			if (childrenNodes) {
				for (var i = 0; i < childrenNodes.length; i++) {
					result += ',' + childrenNodes[i].id;
					result = getAllChildrenNodes(childrenNodes[i], result);
				}
			}
	    }
	    return result;
	}

	// 获取选中的节点
	function getSelectNode() {
		var node;
		if (treeObj) {
			var nodes = treeObj.getSelectedNodes();
			if (nodes.length > 0) {
				node = nodes[0];
			}
		}
		return node;
	}

    function bind() {
        util.bindEvents([ {
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