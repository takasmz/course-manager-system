require(['common/util', 'common/http', 'jquery.validate', 'jquery.serialize','jquery.form'],
	function (util) {
	//执行初始化函数
    init();

    /**
     * 初始化集合
     */
    function init() {
//    	formValid("#register-form");
        bind();
    }
    
    layui.use(['admin','form', 'user'], function(){
    	  var $ = layui.$
    	  ,setter = layui.setter
    	  ,form = layui.form
    	  ,admin = layui.admin
    	  ,router = layui.router();


//    	  //发送邮箱验证码
//    	  admin.sendAuthCode({
//    	    elem: '#LAY-user-reg-getsmscode'
//    	    ,elemPhone: '#LAY-user-login-email'
//    	    ,elemVercode: '#LAY-user-login-vercode'
//    	    ,ajax: {
//    	      url: '/sms' //实际使用请改成服务端真实接口
//    	    }
//    	  });
    	  
		  form.render();
		  formValid("#register-form",setter);
		  //提交
	/*	  form.on('submit(LAY-user-reg-submit)', function(obj){
		    //请求登入接口
		    admin.req({
		      url: '/user/register' //实际使用请改成服务端真实接口
		      ,data: obj.field
		      ,done: function(res){
		    	  if(res.code == 0){
		    		  layer.msg('注册失败'+res.msg, {
				          offset: '15px'
				          ,icon: 2
				          ,time: 1000
				        }, function(){
				        });
		    		  return ;
		    	  }
		    	  
		        //请求成功后，写入 access_token
		        layui.data(setter.tableName, {
		          key: setter.request.tokenName
		          ,value: res.data.accessToken
		        });
		        
		        //登入成功的提示与跳转
		        layer.msg('注册成功', {
		          offset: '15px'
		          ,icon: 1
		          ,time: 1000
		        }, function(){
		          location.hash = search.redirect ? decodeURIComponent(search.redirect) : '/';
		        });
		      }
		    });
		    
		  });*/
    	  
    	});
    
    function formValid(formId) {
        $(formId).validate({
        		onsubmit: function(element, event) {
	                var value = $.trim(this.elementValue(element));
	                $(element).val(value);
	            },
        		onkeyup:false,
                rules: {
                	username: {
                        required: true,
                        blankSpace: true,
                        chrNumChinese: true,
                        rangelength: [3, 20],
                        remote: {
                            url: "/user/regNameCheck",
                            type: "post",
                            data: {
                            	username: function() {
                                    return $("#LAY-user-login-username").val();
                                }
                            }
                        }
                    },
                    password: {
                        required: true,
                        rangelength: [6, 20],
                        chrnum: true
                    },
                    repass: {
                        required: true,
                        equalTo: formId+" input[name='password']"
                    },
                    email: {
                        required: true,
                        blankSpace: true,
                        checkEmail:true
                    }
                },
                messages: {
                	username: {
                        required: '登录用户不能为空',
                        blankSpace: '所输入内容含有空格',
                        rangelength: '登录用户长度必须是{0}到{1}之间',
                        chrNumChinese: '所输入内容含特殊字符',
                        remote:'学号不合法'
                    },
                    password: {
                    	required: '登录密码不能为空',
                        rangelength: '登录密码长度必须是{0}到{1}之间',
                        chrnum: '只能输入数字和字母'
                    },
                    passwordre: {
                    	required: '请再次输入您的密码',
                        equalTo: '两次密码输入不一致，请重新输入'
                    },
                    email: {
                        required: '电子邮箱不能为空',
                        blankSpace: '所输入内容含有空格',
                        checkEmail:'请输入正确的邮箱格式'
                    }
                },
                errorPlacement:function(error,element){
            		$(element).parent().removeClass("ivu-form-item-error");
                	$(element).parent().find("div.ivu-form-item-error-tip").remove();
                	if(error[0].textContent!=''){
	                	$(element).parent().addClass("ivu-form-item-error");
	                	$(element).parent().append("<div class='ivu-form-item-error-tip'>"+error[0].textContent+"</div>")
                	}	
                },
                success:function(element){
                },
                submitHandler: function (formId) {                	
                    //var formParam = $(formId).serializeObject();
                    //delete formParam.passwordre;
                    $(formId).ajaxSubmit({
                        url: '/user/register',
                        type: 'post',
                        success: function (data) {
                        	if(data.code === 1){
//                        		$(".register-success").show();
                                //请求成功后，写入 access_token
                                layui.data(setter.tableName, {
                                    key: setter.request.tokenName
                                    ,value: data.data.studentId
                                });
                                //登入成功的提示与跳转
                                layer.msg('注册成功', {
                                    offset: '15px'
                                    ,icon: 1
                                    ,time: 1000
                                }, function(){
                                    location.hash = search.redirect ? decodeURIComponent(search.redirect) : '/';
                                });
                        	}else{
                        		layer.msg(data.msg,{icon: 2});
                        	}
                        }
                    });
                }
        	});
        $.validator.addMethod("blankSpace",function(value,element,params){
            var blankSpace = /\s+/;
            return !(blankSpace.test(value));
        },"所输入内容含有空格");
    	$.validator.addMethod("chrNumChinese",function(value,element,params){
            var chrNumChinese = /^[a-zA-Z0-9\u4e00-\u9fa5]+$/;
            return this.optional(element)||(chrNumChinese.test(value));  
        },"所输入内容含特殊字符");
        $.validator.addMethod("checkEmail",function(value,element,params){
            var checkEmail = /^[a-z0-9]+@([a-z0-9]+\.)+[a-z]{2,4}$/i;
            return this.optional(element)||(checkEmail.test(value));  
        },"请输入正确的邮箱格式");
        $.validator.addMethod("chrnum", function(value, element) {
            var chrnum = /^([a-zA-Z0-9]+)$/;
            return this.optional(element) || (chrnum.test(value));
        }, "只能输入数字和字母(字符A-Z, a-z, 0-9)");
    }

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