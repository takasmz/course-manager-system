require(['jquery','common/util', 'jquery.validate', 'jquery.serialize'],
	function ($,util) {
	// 执行初始化函数
	init();

	/**
	 * 初始化集合
	 */
	function init() {
        util.enterSubmit($(".layui-btn-fluid"));
		bind();
	}
			layui.use(['admin','form','user'], function(){
			  var $ = layui.$
	    	  ,setter = layui.setter
	    	  ,form = layui.form
	    	  ,admin = layui.admin
	    	  ,router = layui.router()
	    	  ,search = router.search;

			  form.render();
			  //提交
			  form.on('submit(LAY-user-login-submit)', function(obj){
			    //请求登入接口
			    admin.req({
			      url: '/user/login' //实际使用请改成服务端真实接口
			      ,data: obj.field
			      ,done: function(res){
			        //请求成功后，写入 access_token
			        layui.data(setter.tableName, {
			          key: setter.request.tokenName
			          ,value: res.data.accessToken
			        });
			        //登入成功的提示与跳转
			        layer.msg('登入成功', {
			          offset: '15px'
			          ,icon: 1
			          ,time: 1000
			        }, function(){
			          window.location.hash = search.redirect ? decodeURIComponent(search.redirect) : '/';
			        });
			      }
			    });
			    
			  });
			  
			});


	/**
	 * 事件绑定
	 */
	function bind() {
		util.bindEvents([ {
			el : '#LAY-user-get-vercode',
			event : 'click',
			handler : function() {
				$('#LAY-user-get-vercode').attr('src',
						'/captcha?t=' + Math.random());
			}
		},{
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
		}])
	}

})