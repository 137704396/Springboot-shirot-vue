<template>
    <div>
        <!--搜索控件-->
        <el-form
                :inline="true"
                @submit.native.prevent
                class="search"
                size="small"
        >
            <el-form-item label="用户名">
                <el-input
                        clearable
                        style="width: 150px"
                        v-model.trim="search.username"
                        v-p-i
                />
            </el-form-item>
            <el-form-item label="备注">
                <el-input
                        clearable
                        style="width: 150px"
                        v-model.trim="search.remark"
                        v-p-i
                />
            </el-form-item>
            <el-form-item>
                <el-button
                        @click="refresh"
                        type="primary"
                >
                    查询
                </el-button>
            </el-form-item>
            <el-form-item>
                <el-button
                        @click="add"
                        type="primary"
                >
                    新增
                </el-button>
            </el-form-item>
        </el-form>
        <!--表格展示-->
        <el-table
                :data="userList"
                border
                fit
                highlight-current-row
                row-key="id"
                style="width: 100%"
                v-loading="listLoading">
            <el-table-column
                    align="center"
                    label="#"
                    type="index"
            />
            <el-table-column
                    align="center"
                    label="用户名"
                    prop="username" />
            <el-table-column
                    align="center"
                    label="手机号"
                    prop="mobile" />
            <el-table-column
                    align="center"
                    label="邮箱"
                    prop="email" />
            <el-table-column
                    align="center"
                    label="部门"
                    prop="department" />
            <el-table-column
                    align="center"
                    label="职位"
                    prop="job" />
            <el-table-column
                    align="center"
                    label="用户名"
                    prop="username" />
            <el-table-column
                    align="center"
                    label="备注"
                    prop="remark" />
            <el-table-column
                    align="center"
                    label="操作"
                    width="480">
                <template slot-scope="{row}">
                    <!--启用-->
                    <el-button
                            @click="enableUser(row)"
                            icon="el-icon-success"
                            size="small"
                            type="primary"
                            v-if="!row.enable">
                        启用
                    </el-button>
                    <!--禁用-->
                    <el-button
                            @click="disableUser(row)"
                            icon="el-icon-error"
                            size="small"
                            type="warning"
                            v-if="row.enable">
                        禁用
                    </el-button>
                    <!--修改-->
                    <el-button
                            @click="modify(row)"
                            icon="el-icon-edit"
                            size="small"
                            type="primary"
                            v-if="!row.edit">
                        修改
                    </el-button>
                    <!--删除-->
                    <el-button
                            @click="del(row)"
                            icon="el-icon-delete"
                            size="small"
                            type="danger"
                            v-if="!row.edit">
                        删除
                    </el-button>
                    <!--重置密码-->
                    <el-button
                            @click="resetPassword(row)"
                            icon="el-icon-refresh"
                            size="small"
                            type="danger"
                            v-if="!row.edit">
                        重置密码
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!--分页-->
        <pagination
                :limit.sync="search.pageSize"
                :page.sync="search.page"
                :total="total"
                @pagination="refresh"
                v-show="total>0"
        />
        <!--新增-->
        <el-dialog
                :title="title"
                :visible.sync="edit"
                v-drag-dialog
                width="30%">
            <el-form
                    :model="user"
                    @submit.native.prevent
                    label-position="right"
                    label-width="100px"
                    ref="userForm">
                <el-form-item label="用户名">
                    <el-input
                            clearable
                            v-model.trim="user.username"
                            v-p-i
                    />
                </el-form-item>
                <el-form-item label="手机号">
                    <el-input
                            clearable
                            v-model.trim="user.mobile"
                            v-p-i />
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input
                            clearable
                            v-model.trim="user.email"
                            v-p-i />
                </el-form-item>
                <el-form-item label="部门">
                    <el-input
                            clearable
                            v-model.trim="user.department"
                            v-p-i />
                </el-form-item>
                <el-form-item label="职务">
                    <el-input
                            clearable
                            v-model.trim="user.job"
                            v-p-i />
                </el-form-item>
                <el-form-item label="备注">
                    <el-input
                            clearable
                            type="textarea"
                            v-model="user.remark"
                            v-p-i
                    />
                </el-form-item>
            </el-form>
            <span
                    class="dialog-footer"
                    slot="footer"
            >
        <el-button @click="edit = false">取 消</el-button>
        <el-button
                @click="yesModify"
                type="primary"
        >确 定</el-button>
      </span>
        </el-dialog>
    </div>
</template>
<script>
  import {
    deleteUser,
    disableUser,
    enableUser,
    insertUser,
    resetUserPassword,
    searchUser,
    updateUser
  } from '@/api/system/user'
  import Pagination from '@/components/Pagination'

  export default {
    name: 'User',
    components: { Pagination },
    data() {
      return {
        // 新增/修改
        edit: false,
        // 当前编辑
        user: {},
        // 按钮列表
        userList: [],
        // 加载状态
        listLoading: false,
        // 检索条件
        search: {},
        // 总数
        total: 0,
        // 弹窗标题
        title: ''
      }
    },
    created() {
      this.refresh()
    },
    methods: {
      // 用户-启用
      enableUser(row) {
        enableUser(row.id)
        row.enable = true
      },
      // 用户-禁用
      disableUser(row) {
        disableUser(row.id)
        row.enable = false
      },
      // 重置密码
      async resetPassword(row) {
        await this.$confirm(`确认重置用户(${row.username})的密码? `, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        resetUserPassword(row.id)
      },
      async refresh() {
        this.listLoading = true
        const data = await searchUser(this.search)
        this.total = data.total
        this.userList = data.list
        this.listLoading = false
      },
      noModify(row) {
        row.edit = false
      },
      async yesModify() {
        let user = this.user
        if (user.id) {
          user = await updateUser(user)
        } else {
          this.userList.unshift(await insertUser(user))
        }
        // 关闭编辑
        this.edit = false
      },
      modify(row) {
        this.title = `修改用户(${row.username})`
        this.user = row
        this.edit = true
      },
      async del(row) {
        await this.$confirm(`此操作将删除用户(${row.username}), 是否继续?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        deleteUser(row.id).then(res => {
          for (let i = 0; i < this.userList.length; i++) {
            if (this.userList[i].id === row.id) {
              this.userList.splice(i, 1)
              break
            }
          }
        })
      },
      add() {
        this.user = {}
        this.title = '新增用户'
        this.edit = true
      }
    }
  }
</script>
<style lang="scss"
        scoped>
    @import "~@/styles/search.scss";

    .edit-input {
        padding-right: 100px;
    }
</style>
