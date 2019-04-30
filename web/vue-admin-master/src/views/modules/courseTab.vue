<template>
    <el-tabs v-model="activeName">
        <template  v-for="(course,index) in this.courseList">
            <el-tab-pane  :label="course.courseName" :name="course.id+''" :onclick="Click(course.id)">{{content[index]}}</el-tab-pane>
        </template>

        <!--<el-tab-pane label="用户管理" name="first">用户管理</el-tab-pane>-->
        <!--<el-tab-pane label="配置管理" name="second">配置管理</el-tab-pane>-->
        <!--<el-tab-pane label="角色管理" name="third">角色管理</el-tab-pane>-->
        <!--<el-tab-pane label="定时任务补偿" name="fourth">定时任务补偿</el-tab-pane>-->
    </el-tabs>
</template>

<script>
    import {getCourseList} from "../../api/api"
    export default {
        name: "courseTab",
        data(){
            return {
                courseList:[],
                activeName: 0,
            }
        },
        methods : {
            getCourseList() {
                getCourseList().then(res => {
                    this.courseList = res.data.data;
                    this.activeName = this.courseList[0].id;
                })
            },
            Click(id){
                this.handleClick(id);
            },
            returnCourseList(){
                return this.courseList;
            }
        },
        created() {
            this.getCourseList();
        },
        props :[{
            content:{
                type:Array
            }
        },{
            handleClick:{
                type: Function
            }
        }],
    }
</script>

<style scoped>

</style>