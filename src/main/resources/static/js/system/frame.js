require(['common/util', 'jquery.validate', 'jquery.serialize'],
	function (util) {

	// 执行初始化函数
	init();

	/**
	 * 初始化集合
	 */
	function init() {
		bind();
	}

	/**
	 * 事件绑定
	 */
	function bind() {
		util.bindEvents([ ])
	}

})