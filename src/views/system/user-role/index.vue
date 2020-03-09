<template>
    <div>
        <el-row :gutter="20">
            <!--user-->
            <el-col :span="12">
                <!--搜索控件-->
                <el-form
                        :inline="true"
                        @submit.native.prevent
                        class="search"
                        size="small">
                    <el-form-item label="用户名">
                        <el-input
                                clearable
                                style="width: 150px"
                                v-model.trim="search.username"
                                v-p-i />
                    </el-form-item>
                    <el-form-item label="备注">
                        <el-input
                                clearable
                                style="width: 150px"
                                v-model.trim="search.remark"
                                v-p-i />
                    </el-form-item>
                    <el-form-item>
                        <el-button
                                @click="refreshUser"
                                type="primary">
                            查询
                        </el-button>
                    </el-form-item>
                </el-form>
                <!--表格展示-->
                <el-table
                        :data="userList"
                        @current-change="userChange"
                        border
                        fit
                        highlight-current-row
                        row-key="id"
                        style="width: 100%"
                        v-loading="userListLoading">
                    <el-table-column
                            align="center"
                            label="#"
                            type="index" />
                    <el-table-column
                            align="center"
                            label="用户名"
                            prop="username" />
                    <el-table-column
                            align="center"
                            label="备注"
                            prop="remark" />
                </el-table>
                <!--分页-->
                <pagination
                        :limit.sync="search.pageSize"
                        :page.sync="search.page"
                        :total="userTotal"
                        @pagination="refreshUser"
                        v-show="userTotal>0" />
            </el-col>
            <!--role-->
            <el-col :span="12">
                <!--搜索控件-->
                <el-form
                        :inline="true"
                        @submit.native.prevent
                        class="search"
                        size="small">
                    <el-form-item label="角色名称">
                        <el-input
                                clearable
                                style="width: 150px"
                                v-model.trim="roleSearch.name"
                                v-p-i
                        />
                    </el-form-item>
                    <el-form-item>
                        <el-button
                                @click="refreshRole"
                                type="primary"
                        >查询
                        </el-button>
                    </el-form-item>
                </el-form>
                <!--表格展示-->
                <el-table :data="roleList"
                        border
                        fit
                        highlight-current-row
                        ref="roleTable"
                        row-key="id"
                        style="width: 100%"
                        v-loading="roleListLoading">
                    <!--复选框-->
                    <el-table-column
                            align="center"
                            label="#"
                            type="index" />
                    <el-table-column
                            align="center"
                            label="角色名称"
                            prop="name">
                    </el-table-column>
                    <el-table-column
                            align="center"
                            label="角色备注"
                            prop="remark" />
                    <el-table-column
                            align="center"
                            label="角色备注"
                            prop="remark">
                        <template slot-scope="{row}">
                            <!--授权-->
                            <el-button
                                    @click="grant(row)"
                                    icon="el-icon-lock"
                                    size="small"
                                    type="primary"
                                    v-if="relationRoleList.indexOf(row.id)<0">
                                分配
                            </el-button>
                            <!--取消-->
                            <el-button
                                    @click="relive(row)"
                                    icon="el-icon-delete"
                                    size="small"
                                    type="warning"
                                    v-else>
                                取消
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!--分页-->
                <pagination
                        :limit.sync="roleSearch.pageSize"
                        :page.sync="roleSearch.page"
                        :total="roleTotal"
                        @pagination="refreshRole"
                        v-show="roleTotal>0" />
            </el-col>
        </el-row>
    </div>
</template>

<script>
  import { searchUser } from '@/api/system/user'
  import Pagination from '@/components/Pagination'
  import { searchRole } from '@/api/system/role'
  import { deleteUserRole, getRelationRoleByUser, insertUserRole } from '@/api/system/user-role'

  export default {
    name: 'UserRole',
    components: { Pagination },
    data() {
      return {
        // 当前编辑
        user: {},
        // 按钮列表
        userList: [],
        // 加载状态
        userListLoading: false,
        // 检索条件
        search: {},
        // 检索角色
        roleSearch: {},
        // 总数
        userTotal: 0,
        // 角色加载
        roleListLoading: false,
        // 角色列表
        roleList: [],
        // 已关联的角色 id列表
        relationRoleList: [],
        roleTotal: 0
      }
    },
    created() {
      this.refreshUser()
      this.refreshRole()
    },
    methods: {
      // 授权角色
      async grant(row) {
        if (!this.user.id) {
          this.$message.warning('请先选择一个用户')
          return
        }
        await insertUserRole(this.user.id, row.id)
        this.relationRoleList.push(row.id)
      },
      // 解除角色
      async relive(row) {
        await deleteUserRole(this.user.id, row.id)
        // 移除删掉的角色id
        this.relationRoleList = this.relationRoleList.filter(v => v !== row.id)
      },
      // 点击用户时
      userChange(currentRow, oldCurrentRow) {
        this.user = currentRow
        this.getRelationRoleByUser()
      },
      async refreshUser() {
        this.userListLoading = true
        const data = await searchUser(this.search)
        this.userTotal = data.total
        this.userList = data.list
        this.userListLoading = false
      },
      async refreshRole() {
        this.roleListLoading = true
        const data = await searchRole(this.roleSearch)
        this.roleTotal = data.total
        this.roleList = data.list
        this.roleListLoading = false
      },
      // 获取应选中的行并选中
      async getRelationRoleByUser() {
        this.roleListLoading = true
        this.relationRoleList = await getRelationRoleByUser(this.user.id)
        this.roleListLoading = false
      }
    }
  }

</script>

<style scoped>
    @import "~@/styles/search.scss";

    .custom-tree-node {
        flex: 1;
        display: flex;
        align-items: center;
        justify-content: space-between;
        font-size: 14px;
        padding-right: 8px;
    }

    .edit-input {
        padding-right: 100px;
    }
</style>
