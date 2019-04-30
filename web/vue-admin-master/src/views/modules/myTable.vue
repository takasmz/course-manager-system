<template>
    <el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
        <template v-for="table in this.tableObj">
            <el-table-column
                    :type="table.type"
                    :label="table.label"
                    :width="table.width"
                    :formatter="table.formatter"
            >{{table.template}}</el-table-column>
        </template>

        <!--<el-table-column type="selection" width="55">-->
        <!--</el-table-column>-->
        <!--<el-table-column type="index" width="60">-->
        <!--</el-table-column>-->
        <!--<el-table-column prop="name" label="姓名" width="120" sortable>-->
        <!--</el-table-column>-->
        <!--<el-table-column prop="sex" label="性别" width="100" :formatter="formatSex" sortable>-->
        <!--</el-table-column>-->
        <!--<el-table-column prop="age" label="年龄" width="100" sortable>-->
        <!--</el-table-column>-->
        <!--<el-table-column prop="birth" label="生日" width="120" sortable>-->
        <!--</el-table-column>-->
        <!--<el-table-column prop="addr" label="地址" min-width="180" sortable>-->
        <!--</el-table-column>-->
        <!--<el-table-column label="操作" width="150">-->
            <!--<template scope="scope">-->
                <!--<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>-->
                <!--<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>-->
            <!--</template>-->
        <!--</el-table-column>-->
    </el-table>
</template>

<script>
    export default {
        name: "myTable",
        data(){
            tableObj:[]
        }
    }
</script>

<style scoped>

</style>