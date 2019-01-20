require.config({
    //版本号，此处用时间表示
    bust : new Date().getTime(),
    //前置路径
    baseUrl: '/js',
    packages:[{
        name: "codemirror",
        location: "../js/lib/codemirror",
        main: "lib/codemirror"
    },{
        name:'editormd',
        location:'../js/lib/editormd',
        main:"lib/editormd.amd"
    }],
    paths: {
        'jquery': 'lib/jquery/jquery-1.12.3.min',
        'handlebars': 'lib/handlebars-1.0.0',
        'layer1': 'lib/layer/layer',
        'layui': 'layui/layui',
        'layui.config': 'layui/config',
        'layui.index': 'layui/index',
        'layui.table': 'layui/lay/modules/table',
        'pagination': 'lib/pagination/jquery.pagination',
        'jquery.validate': 'lib/validate/jquery.validate.min',
        'jquery.layout': 'lib/layout/jquery.layout-latest',
        'jquery.dataTables': 'lib/datatable/jquery.dataTables.min',
        'dataTables.bootstrap': 'lib/datatable/dataTables.bootstrap.min',
        'ztree': 'lib/ztree/jquery.ztree.core.min',
        'ztreeCheck': 'lib/ztree/jquery.ztree.excheck.min',
        'dataTables.fixedHeader': 'lib/datatable/dataTables.fixedHeader.min',
        'dataTables.select': 'lib/datatable/dataTables.select.min',
        'dataTables.buttons': 'lib/datatable/dataTables.buttons.min',
        'buttons.print': 'lib/datatable/buttons.print.min',
        'buttons.flash': 'lib/datatable/buttons.flash.min',
        'buttons.html5': 'lib/datatable/buttons.html5.min',
        'jszip': 'lib/datatable/jszip.min',
        'bootstrap': 'lib/bootstrap/js/bootstrap.min',
        'html5shiv.min': 'lib/bootstrap/js/html5shiv.min',
        'respond.min': 'lib/bootstrap/js/respond.min',
        'jquery.serialize': 'lib/jquery/jquery.serialize-object.min',
        'metisMenu': 'lib/metisMenu',
        'contabs': 'lib/contabs.min',
        'pace': 'lib/pace/pace.min',
        'select2': 'lib/select2/select2.min',
        'jquery.nanoscroller': 'lib/jquery/jquery.nanoscroller.min',
        'jquery.form':'lib/jquery/jquery.form.min',
        'jquery.nanoscroller': 'lib/jquery/jquery.nanoscroller.min',
        'swiper': 'lib/swiper/idangerous.swiper2.7.6.min',
        'placeholder': 'lib/placeholder/jquery.placeholder.min',
        'mui': 'lib/mui/mui.min',
        'echarts': 'echarts/echarts.min',
        'wordCloud':   'echarts/echarts-wordcloud.min',
        'taizhoumap': 'echarts/taizhou',
        'umeditor.template' :'lib/ueditor/third-party/template.min',
        'umeditor.config':'lib/ueditor/umeditor.config',
        'umeditor':'lib/ueditor/umeditor',
        'd3v4':'echarts/d3v4',
        'scroll':'lib/scroll/divscroll',
        'jsencrypt':'lib/jsencrypt/jsencrypt',
        'hbsHelper':'common/hbsHelper',
        'bootstrap-table': 'lib/bootstrap-table/bootstrap-table',
        'bootstrap-table-CN': 'lib/bootstrap-table/locale/bootstrap-table-zh-CN',
        'bootstrap-table-treegrid': 'lib/bootstrap-table/extensions/treegrid/bootstrap-table-treegrid',
        'jquery.treegrid':'lib/jquery-treegrid/js/jquery.treegrid',
        'simditor':'lib/simditor/scripts/simditor',
        'hotkeys':'lib/simditor/scripts/hotkeys',
        'simple-module':'lib/simditor/scripts/module',
        'uploader':'lib/simditor/scripts/uploader',
        'dompurify':'lib/simditor/scripts/dompurify',
        'marked' : 'lib/marked/marked.min',
        prettify        : "lib/editormd/lib/prettify.min",
        raphael         : "lib/editormd/lib/raphael.min",
        underscore      : "lib/editormd/lib/underscore.min",
        flowchart       : "lib/editormd/lib/flowchart.min",
        jqueryflowchart : "lib/editormd/lib/jquery.flowchart.min",
        sequenceDiagram : "lib/editormd/lib/sequence-diagram.min",
        katex           : "lib/editormd/lib/katex.min",
        'editormd.amd'        : "lib/editormd/lib/editormd.amd",
        'pdfobject':'lib/pdfobject/pdfobject.min'
    },
    shim: {
        'hotkeys':{
            deps:['jquery','simple-module']
        },
        'uploader':{
            deps:['jquery','simple-module','hotkeys']
        },
        'simditor':{
            deps:['jquery','simple-module','hotkeys','uploader']
        },
        'layui.config':{
            deps:['layui']
        },
        'layui.index':{
            deps:['layui']
        },
    	'bootstrap-table-CN':{
    		deps:['bootstrap-table','bootstrap','jquery']
    	},
    	'bootstrap-table':{
    		deps:['bootstrap','jquery']
    	},
    	'bootstrap-table-treegrid':{
    		deps:['bootstrap-table','bootstrap','jquery','jquery.treegrid']
    	},
    	'jquery.treegrid':{
    		deps:['jquery']
    	},
    	// 'mode_xml':{
    	// 	deps:['codemirror']
    	// },
    	// 'mode_css':{
    	// 	deps:['codemirror']
    	// },
    	// 'mode_javascript':{
    	// 	deps:['codemirror']
    	// },
    	// 'mode_html':{
    	// 	deps:['mode_xml','mode_css','mode_javascript','codemirror']
    	// },
    	// 'mode_java':{
    	// 	deps:['mode_xml','mode_css','mode_javascript','codemirror']
    	// },
        'bootstrap': {
            deps: ['jquery']
        },
        'layer1': {
            deps: ['jquery']
        },
        'pagination': {
            deps: ['jquery']
        },
        'jquery.layout': {
            deps: ['jquery']
        },
        'dataTables.fixedHeader': {
            deps: ['jquery.dataTables']
        },
        'dataTables.bootstrap': {
            deps: ['jquery.dataTables']
        },
        'dataTables.buttons': {
            deps: ['jquery.dataTables']
        },
        'buttons.print': {
            deps: ['jquery.dataTables','dataTables.buttons']
        },
        'buttons.flash': {
            deps: ['jquery.dataTables','dataTables.buttons']
        },
        'buttons.html5': {
            deps: ['jquery.dataTables','dataTables.buttons']
        },
        'ztree': {
            deps: ['jquery']
        },
        'ztreeCheck': {
            deps: ['jquery', 'ztree']
        },
        'metisMenu': {
            deps: ['jquery']
        },
        'contabs': {
        	deps: ['jquery']
        },
        'select2': {
            deps: ['jquery']
        },
        'jquery.form': {
            deps: ['jquery']
        },
        'swiper': {
            deps: ['jquery']
        },
        'placeholder': {
            deps: ['jquery']
        },
        'mui': {
            deps: ['jquery']
        },
        'echarts': {
            deps: ['jquery']
        },
        'umeditor.template':{
            deps: ['jquery']
        },
        'umeditor.config':{
            deps: ['jquery','umeditor.template']
        },
        'umeditor':{
            deps: ['jquery','umeditor.config']
        },
        'd3':{
        	deps:['jquery']
        },
        'jsencrypt':{
        	deps:['jquery']
        }
    },
    waitSeconds: 0
});