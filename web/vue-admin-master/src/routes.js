import Login from './views/Login.vue'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import Main from './views/Main.vue'
import introduction from './views/courseHome/course_intro.vue'
import instructors from './views/courseHome/course_teacher.vue'
import syllabus from './views/courseHome/syllabus.vue'
import createNewCourse from './views/courseManager/teacher/createNewCourse.vue'
import exercises from './views/courseMaterials/exercises.vue'
import resource from './views/courseMaterials/resource.vue'
import video from './views/courseMaterials/video.vue'
import stuList from './views/homeworkManager/student/homework_list.vue'
import stuRecode from './views/homeworkManager/student/submit_record.vue'
import teaNew from './views/homeworkManager/teacher/new_homework.vue'
import teaRecode from './views/homeworkManager/teacher/record.vue'
import teaEdit from './views/homeworkManager/teacher/edit_homework.vue'

let user = JSON.parse(sessionStorage.getItem("user")),role=-1;
if(user){
    role = user.type;
}

let routes = [
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    {
        path: '/',
        component: Home,
        name: '课程主页',
        iconCls: 'el-icon-s-home',//图标样式class
        children: [
            {name: "课程介绍", component: introduction, path: "/courseHome/course_intro", iconCls: null, children: []},
            {name: "教师简介", component: instructors, path: "/courseHome/course_teacher", iconCls: null, children: []},
            {name: "教学大纲", component: syllabus, path: "/courseHome/syllabus", iconCls: null, children: []}
        ]
    },
    {
        path: '/',
        component: Home,
        name: '课程材料',
        iconCls: 'el-icon-folder',
        children: [
            { path: '/courseMaterials/resource', component: resource, name: '课程附件' },
            { path: '/courseMaterials/exercises', component: exercises, name: '历年习题' },
            { path: '/courseMaterials/video', component: video, name: '课程音频' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '作业管理',
        iconCls: 'el-icon-reading',
        // leaf: true,//只有一个节点
        children: [
            { path: '/homeworkManager/student/homework_list', component: stuList, name: '提交作业' ,hidden: role === 1},
            { path: '/homeworkManager/student/submit_record', component: stuRecode, name: '提交记录' ,hidden: role === 1},
            { path: '/homeworkManager/teacher/new_homework', component: teaNew, name: '新建作业' ,hidden: role === 0},
            { path: '/homeworkManager/teacher/record', component: teaRecode, name: '作业记录' ,hidden: role === 0},
            { path: '/homeworkManager/teacher/edit_homework', component: teaEdit, name: '编辑作业' ,hidden: role === 0}
        ]
    },
    {
        path: '/',
        component: Home,
        name: '课程管理',
        iconCls: 'el-icon-setting',
        hidden: role === 0,
        children: [
            { path: '/createNewCourse', component: createNewCourse, name: '新建课程' },
        ]
    },
    {
        path: '/',
        component: Home,
        name: '课程交流',
        iconCls: 'el-icon-chat-square',
        children: [
            { path: '/createNewCourse1', component: createNewCourse, name: '问题交流' },
            { path: '/createNewCourse2', component: createNewCourse, name: '课后讨论' },
        ]
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    },
    // menu
];

export default routes;