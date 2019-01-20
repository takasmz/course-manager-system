require(['common/util', 'common/http', 'jquery.validate', 'jquery.serialize'],
	function (util, layer, http, JSEncrypt) {
	//执行初始化函数
	var flag = false;
    init();

    /**
     * 初始化集合
     */
    function init() {
        bind();
    }
    
    layui.use(['admin', 'form', 'user'], function(){
    	  var $ = layui.$
    	  ,setter = layui.setter
    	  ,admin = layui.admin
    	  ,form = layui.form
    	  ,router = layui.router();

    	  form.render();
    	  
    	  //发送邮箱验证码
    	  admin.sendAuthCode({
    	    elem: '#LAY-user-forget-getsmscode'
    	    ,elemPhone: '#LAY-user-login-email'
    	    ,elemVercode: '#LAY-user-login-vercode'
    	    ,ajax: {
    	      url: '/sms' //实际使用请改成服务端真实接口
    	    }
    	  });

    	  //找回密码下一步
    	  form.on('submit(LAY-user-forget-submit)', function(obj){
    	    var field = obj.field;

    	    //请求接口
    	    admin.req({
    	      url: '/forget' //实际使用请改成服务端真实接口
    	      ,data: field
    	      ,done: function(res){        
    	        location.hash = router.href + '/type=resetpass';
    	      }
    	    });
    	    
    	    return false;
    	  });
    	  
    	  //重置密码
    	  form.on('submit(LAY-user-forget-resetpass)', function(obj){
    	    var field = obj.field;
    	    
    	    //确认密码
    	    if(field.password !== field.repass){
    	      return layer.msg('两次密码输入不一致');
    	    }

    	    //请求接口
    	    admin.req({
    	      url: '/resetpass' //实际使用请改成服务端真实接口
    	      ,data: field
    	      ,done: function(res){        
    	        layer.msg('密码已成功重置', {
    	          offset: '15px'
    	          ,icon: 1
    	          ,time: 1000
    	        }, function(){
    	          location.hash = '/login'; //跳转到登入页
    	        });
    	      }
    	    });
    	    
    	    return false;
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
		} ])
	}

})