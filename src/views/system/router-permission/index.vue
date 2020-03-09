<template>
    <div>
        <el-row :gutter="20">
            <!--router-->
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
                    <span :title="data.description">
                        <span v-if="data.alwaysShow">{{ data.title }}</span>
                        <span style="color: #ff9b33"
                                v-else>{{ data.title }}</span>
                    </span>
                  </span>
                </el-tree>
            </el-col>
            <!--permission-->
            <el-col :span="16">
                <!--表单-->
                <el-form
                        :inline="true"
                        @submit.native.prevent
                        class="search"
                        size="small">
                    <el-form-item>
                        <el-button
                                @click="add"
                                type="primary">
                            添加
                        </el-button>
                    </el-form-item>
                    <el-form-item>
                        {{this.router.title}}
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
                            label="按钮文字"
                            prop="text" />
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
                                    @click="delPermission(row)"
                                    icon="el-icon-delete"
                                    size="small"
                                    type="danger"
                                    v-if="!row.edit">
                                删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </el-col>
        </el-row>
        <!--新增-->
        <el-dialog
                :title="title"
                :visible.sync="addNewPermissionDialog"
                v-drag-dialog
                width="30%">
            <el-form
                    :model="permission"
                    @submit.native.prevent
                    label-position="right"
                    label-width="100px"
                    ref="permissionForm">
                <el-form-item label="按钮编码">
                    <el-input
                            clearable
                            v-model.trim="permission.button"
                            v-p-i />
                </el-form-item>
                <el-form-item label="按钮文字">
                    <el-input
                            clearable
                            v-model.trim="permission.text"
                            v-p-i />
                </el-form-item>
                <el-form-item label="接口编码">
                    <el-input
                            clearable
                            v-model.trim="permission.port"
                            v-p-i />
                </el-form-item>
                <el-form-item label="备注">
                    <el-input
                            clearable
                            type="textarea"
                            v-model.trim="permission.remark"
                            v-p-i />
                </el-form-item>
            </el-form>
            <span
                    class="dialog-footer"
                    slot="footer"
            >
        <el-button @click="noModify">取 消</el-button>
        <el-button
                @click="yesModify"
                type="primary"
        >确 定</el-button>
      </span>
        </el-dialog>
    </div>
</template>

<script>
  import { searchRouter } from '@/api/system/router'
  import { delPermission, getPermissionList, insertPermission, updatePermission } from '@/api/system/permission'

  export default {
    name: 'RouterPermission',
    data() {
      return {
        // 当前编辑的路由
        router: {},
        routerList: [],
        // 用于路由过滤
        filterText: '',
        // 树配置
        defaultProps: {
          children: 'children',
          label: 'title'
        },
        // 弹窗标题
        title: '',
        // 当前路由下的权限列表
        permissionList: [],
        // 权限加载状态
        permissionListLoading: false,
        // 新增权限时的弹窗效果
        addNewPermissionDialog: false,
        // 当前编辑的权限
        permission: {},
        tempPermission: {}
      }
    },
    created() {
      this.refreshRouter()
    },
    methods: {
      async delPermission(row) {
        await this.$confirm(`确定要删除此权限(${row.button})吗`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'error'
        })
        await delPermission(row.id)
        this.permissionList = this.permissionList.filter(v => v.id !== row.id)
      },
      // 节点过滤
      filterNode(value, data) {
        if (!value) return true
        return data.title.indexOf(value) !== -1
      },
      // 刷新路由树
      async refreshRouter() {
        // 刷新信息
        this.routerList = await searchRouter()
        // 清空输入
        this.router = {}
      },
      // 添加权限
      add() {
        if (!this.router.id) {
          this.$message.warning('请先选中一个菜单')
          return
        }
        this.title = `为(${this.router.title})添加按钮权限`
        this.permission = { router: this.router.id }
        this.addNewPermissionDialog = true
      },
      // 修改权限信息
      modify(permission) {
        this.tempPermission = { ...permission }
        this.permission = permission
        this.title = `修改按钮权限(${this.router.title})`
        this.addNewPermissionDialog = true
      },
      noModify() {
        this.addNewPermissionDialog = false
        for (let key in this.permission) {
          this.permission[key] = this.tempPermission[key]
        }
      },
      async yesModify() {
        let tmp
        if (this.permission.id) {
          tmp = await updatePermission(this.permission)
        } else {
          tmp = await insertPermission(this.permission)
          this.permissionList.unshift(tmp)
        }
        this.addNewPermissionDialog = false
      },
      // 路由被点击时调用
      routerClick(data) {
        if (data.alwaysShow) {
          return
        }
        this.router = data
        this.getPermissionList()
      },
      // 获取当前路由拥有的按钮权限
      async getPermissionList() {
        this.permissionListLoading = true
        this.permissionList = await getPermissionList(this.router.id)
        this.permissionListLoading = false
      }
    },
    watch: {
      // 节点过滤
      filterText(val) {
        this.$refs.tree.filter(val)
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
