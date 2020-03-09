<template>
    <div>
        <el-row :gutter="2">
            <!--角色-->
            <el-col :span="8">
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
                                v-p-i />
                    </el-form-item>
                    <el-form-item>
                        <el-button
                                @click="refreshRole"
                                type="primary">
                            查询
                        </el-button>
                    </el-form-item>
                </el-form>
                <!--表格展示-->
                <el-table :data="roleList"
                        @current-change="roleChange"
                        border
                        fit
                        highlight-current-row
                        row-key="id"
                        style="width: 100%"
                        v-loading="roleListLoading">
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
                            prop="remark">
                    </el-table-column>
                </el-table>
                <!--分页-->
                <pagination
                        :limit.sync="roleSearch.pageSize"
                        :page.sync="roleSearch.page"
                        :total="roleTotal"
                        @pagination="refreshRole"
                        v-show="roleTotal>0"
                />
            </el-col>
            <!--路由-->
            <el-col :span="8">
                <!--过滤,保存更改-->
                <el-form
                        :inline="true"
                        @submit.native.prevent
                        class="search"
                        size="small"
                >
                    <el-form-item label="过滤">
                        <el-input
                                clearable
                                placeholder="输入关键字进行过滤"
                                show-word-limit
                                style="width: 300px"
                                v-model.trim="filterText"
                        />
                    </el-form-item>
                    <el-form-item>
                        <el-button
                                @click="refreshRouter"
                                type="primary"
                        > 刷新信息
                        </el-button>
                    </el-form-item>
                </el-form>
                <!--展示-->
                <el-tree :data="routerList"
                        :expand-on-click-node="false"
                        :filter-node-method="filterNode"
                        :props="defaultProps"
                        @node-click="routerClick"
                        default-expand-all
                        highlight-current
                        node-key="id"
                        ref="routerTree">
                    <span class="custom-tree-node"
                            slot-scope="{ node, data }">
                        <span :title="data.description">{{ data.title }}</span>
                        <span class="grant"
                                v-if="!data.alwaysShow">
                            <el-button @click="grantRouter(data)"
                                    size="mini"
                                    title="授予该角色用户访问此页面的权限"
                                    type="text"
                                    v-if="relationRouterIds.indexOf(data.id)<0">授权</el-button>
                            <el-button @click="reliveRouter(data)"
                                    size="mini"
                                    style="color: #b3450e"
                                    title="解除该角色用户访问此页面的权限"
                                    type="text"
                                    v-else>解除</el-button>
                        </span>
                    </span>
                </el-tree>
            </el-col>
            <!--权限-->
            <el-col :span="8">
                <!--表单-->
                <el-form
                        :inline="true"
                        @submit.native.prevent
                        class="search"
                        size="small">
                    <el-form-item>
                        {{this.router.title||'请选择菜单'}}
                    </el-form-item>
                </el-form>
                <el-table
                        :data="permissionList"
                        border
                        fit
                        highlight-current-row
                        row-key="id"
                        style="width: 100%"
                        v-loading="permissionListLoading">
                    <el-table-column
                            align="center"
                            label="#"
                            type="index" />
                    <el-table-column
                            align="center"
                            label="按钮编码"
                            prop="button" />
                    <el-table-column
                            align="center"
                            label="接口编码"
                            prop="port" />
                    <el-table-column
                            align="center"
                            label="备注"
                            prop="remark" />
                    <el-table-column
                            align="center"
                            label="操作"
                            width="240">
                        <template slot-scope="{row}">
                            <!--授权-->
                            <el-button
                                    @click="grantPermission(row)"
                                    size="small"
                                    type="primary"
                                    v-if="relationPermissionIds.indexOf(row.id)<0">
                                授权
                            </el-button>
                            <!--解除-->
                            <el-button
                                    @click="relivePermission(row)"
                                    size="small"
                                    type="warning"
                                    v-else>
                                解除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
    </div>
</template>

<script>

  import { searchRole } from '@/api/system/role'
  import Pagination from '@/components/Pagination/index'
  import { searchRouter } from '@/api/system/router'
  import { getPermissionList } from '@/api/system/permission'
  import { deleteRoleRouter, getRouterIdsByRoleId, insertRoleRouter } from '@/api/system/role-router'
  import {
    deleteRolePermission,
    getRelationPermissionIdsByRoleId,
    insertRolePermission
  } from '@/api/system/role-permission'

  export default {
    name: 'RoleRouterPermission',
    components: { Pagination },
    data() {
      return {
        // 当前角色
        role: {},
        // 所有角色
        roleList: [],
        // 角色列表加载
        roleListLoading: false,
        // 角色总数
        roleTotal: 0,
        // 角色搜索
        roleSearch: {},
        // 路由过滤
        filterText: '',
        // 树配置
        defaultProps: {
          children: 'children',
          label: 'title'
        },
        // 当前选中的路由
        router: {},
        // 路由列表
        routerList: [],
        // 权限加载状态
        permissionListLoading: false,
        // 权限列表
        permissionList: [],
        // 角色绑定的路由列表
        relationRouterIds: [],
        // 角色绑定的权限列表
        relationPermissionIds: []
      }
    },
    watch: {
      // 节点过滤
      filterText(val) {
        this.$refs.routerTree.filter(val)
      }
    },
    created() {
      this.refreshRole()
      this.refreshRouter()
    },
    methods: {
      // 检测此时是否可以执行授权操作
      checkCanGrant() {
        // 未选择角色则报错
        if (!this.role.id) {
          this.$message.warning('请选择要修改的角色')
          throw 1
        }
      },
      // 获取用户已授权的路由id
      async getRouterIdsByRoleId() {
        const tmp = this.routerList
        this.routerList = []
        this.relationRouterIds = await getRouterIdsByRoleId(this.role.id)
        this.routerList = tmp
      },
      // 点击角色
      roleChange(currentRow) {
        this.role = currentRow
        this.getRouterIdsByRoleId()
      },
      // 授权路由
      async grantRouter(router) {
        await this.checkCanGrant()
        await insertRoleRouter(this.role.id, router.id)
        this.relationRouterIds.push(router.id)
      },
      // 解除授权路由
      async reliveRouter(router) {
        await this.checkCanGrant()
        await this.$confirm(`此操作将一并解除该角色用户当前页面路由的授权, 是否继续?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await deleteRoleRouter(this.role.id, router.id)
        this.relationRouterIds = this.relationRouterIds.filter(v => v !== router.id)
        this.relationPermissionIds = []
      },
      // 授权按钮权限
      async grantPermission(permission) {
        await this.checkCanGrant()
        if (this.relationRouterIds.indexOf(this.router.id) < 0) {
          this.$message.warning('请先为页面进行授权')
          return
        }
        await insertRolePermission(this.role.id, permission.id)
        this.relationPermissionIds.push(permission.id)
      },
      // 解除授权按钮权限
      async relivePermission(permission) {
        await this.checkCanGrant()
        await deleteRolePermission(this.role.id, permission.id)
        this.relationPermissionIds = this.relationPermissionIds.filter(v => v !== permission.id)
      },
      // 刷新角色
      async refreshRole() {
        this.roleListLoading = true
        const data = await searchRole(this.search)
        this.roleTotal = data.total
        this.roleList = data.list
        this.roleListLoading = false
      },
      // 刷新路由树
      async refreshRouter() {
        // 刷新信息
        this.routerList = await searchRouter()
        // 清空输入
        this.router = {}
      },
      // 路由被点击时调用
      routerClick(data) {
        if (!data.alwaysShow) {
          this.router = data
          this.getPermissionList()
        }
      },
      // 获取当前路由拥有的按钮权限
      async getPermissionList() {
        this.permissionListLoading = true
        this.permissionList = await getPermissionList(this.router.id)
        if (this.role.id) {
          this.relationPermissionIds = await getRelationPermissionIdsByRoleId(this.role.id)
        }
        this.permissionListLoading = false
      },
      // 节点过滤
      filterNode(value, data) {
        if (!value) return true
        return data.title.indexOf(value) !== -1
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

    .grant *:hover {
        font-size: larger;
        font-weight: bold;
    }

    .edit-input {
        padding-right: 100px;
    }
</style>
