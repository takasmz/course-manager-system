require(['component/iframeLayer',
         'component/dataTable',
         'common/util',
         'common/http',
         'handlebars',
         'jquery','jquery.serialize'], function (layer, dataTable, util, http, handlebars) {
	

    init();
    
    function init() {
    	queryData();
        bind();
    }
    
    
    
    function queryData(){
    	$.ajax({
    		url:'/admin/status/queryUserData',
    		type:'POST',
    		dataType:'json',
    		success:function(data){
    			var data = data.data;
    			$("#SolvedNum").text(data.Solved);
    			$("#SubmissionsNum").text(data.Submissions);
    			var html='';
    			
    			for(var i=0;i<data.problemList.length;i++){
    				html += '<button data-v-5d6fd9be="" type="button" class="ivu-btn ivu-btn-ghost" pid='+data.problemList[i].pid+'>'
    					+'<span>'+data.problemList[i].title +'</span>'
    					+'</button>';
    			}
    			$(".problem-btn").html(html);
    		}
    	})
    }
    
    function bind(){
        util.bindEvents([ 	            
	        {
	        	el:'.ivu-btn-ghost',
	        	event:'click',
	        	handler:function(){
	        		var pid = $(this).attr("pid");
	        		window.location.href = 'problemDetail/detail_view?pid='+pid;
	        	}
	        }
        ])
    }
  
});