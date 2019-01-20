require(['component/iframeLayer', 'component/dataTable','common/util', 'common/http', 'ztree','ztreeCheck','handlebars', 'jquery','jquery.serialize','laydate'], function (layer, dataTable, util, http, handlebars) {
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
 
			callback: {
			}
    };
    init();
    
  
    

    /**
     * 初始化函数集合
     */
    function init() {
        ztreeInit();
        bind();
        var checked = $('#checked').val();
        var nodes = treeObj.getNodes();
        if(checked!=null&&checked.length>0){
        	var checkedItemIds = checked.split(",");
        	for(var j=0;j< checkedItemIds.length;j++){
        		var id = checkedItemIds[j];
        		for (var i=0, l=nodes.length; i < l; i++) {
                	if(nodes[i].id==Number(id)){
                    	treeObj.checkNode(nodes[i], true, true);
                    	break;
                	}
                }
        	}
        }
    }

 

    

    /*function beforeClick(treeId, treeNode) {
    	var check = (treeNode && !treeNode.isParent);
    	if (!check)   layer.msg("父级不可选", {ltype: 0,time:500});;
    	return check;
    }*/
    
    function zTreeOnCheck(event, treeId, treeNode) {
    	var zTree = $.fn.zTree.getZTreeObj("permisionTree");
        var nodes = zTree.getSelectedNodes();
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
    	  var treeData = [ {
    		    "name" : "待提交",
    		    "pId" : "9",
    		    "id" : "0"
    		  },{
    		    "name" : "发布",
    		    "pId" : "9",
    		    "id" : "1"
    		  },{
    		    "name" : "通过",
    		    "pId" : "9",
    		    "id" : "2"
    		  },{
    		    "name" : "驳回",
    		    "pId" : "9",
    		    "id" : "3"
    		  },{
    		    "name" : "待审核",
    		    "pId" : "9",
    		    "id" : "4"
    		  },{
    		    "name" : "暂停",
    		    "pId" : "9",
    		    "id" : "5"
    		  }];  
          treeObj = $.fn.zTree.init($('#permisionTree'), treeSetting, treeData); 

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