<template>
    <div>
        <!--搜索控件-->
        <el-form
                :inline="true"
                @submit.native.prevent
                class="search"
                size="small"
        >
            <el-form-item label="角色名称">
                <el-input
                        clearable
                        style="width: 150px"
                        v-model.trim="search.name"
                        v-p-i
                />
            </el-form-item>
            <el-form-item>
                <el-button
                        @click="refresh"
                        type="primary"
                >查询
                </el-button>
            </el-form-item>
            <el-form-item>
                <el-button
                        @click="add"
                        type="primary"
                >新增
                </el-button>
            </el-form-item>
        </el-form>
        <!--表格展示-->
        <el-table
                :data="roleList"
                border
                fit
                highlight-current-row
                row-key="id"
                style="width: 100%"
                v-loading="listLoading"
        >
            <el-table-column
                    align="center"
                    label="#"
                    type="index"
            />
            <el-table-column
                    align="center"
                    label="角色名称"
                    prop="name"
            >
                <template slot-scope="{row}">
                    <template v-if="row.edit">
                        <el-input
                                class="edit-input"
                                size="small"
                                v-model="row.name"
                        />
                    </template>
                    <span v-else>{{ row.name }}</span></template>
            </el-table-column>
            <el-table-column
                    align="center"
                    label="角色备注"
                    prop="remark"
            >
                <template slot-scope="{row}">
                    <template v-if="row.edit">
                        <el-input
                                class="edit-input"
                                size="small"
                                v-model="row.remark"
                        />
                    </template>
                    <span v-else>{{ row.remark }}</span></template>
            </el-table-column>
            <el-table-column
                    align="center"
                    label="操作"
                    width="360"
            >
                <template slot-scope="{row}">
                    <!--取消修改-->
                    <el-button
                            @click="noModify(row)"
                            class="cancel-btn"
                            icon="el-icon-close"
                            size="small"
                            type="warning"
                            v-if="row.edit"
                    > 取消
                    </el-button>
                    <!--确认修改-->
                    <el-button
                            @click="yesModify(row)"
                            icon="el-icon-check"
                            size="small"
                            type="success"
                            v-if="row.edit"
                    > 确认
                    </el-button>
                    <!--修改-->
                    <el-button
                            @click="row.edit = !row.edit"
                            icon="el-icon-edit"
                            size="small"
                            type="primary"
                            v-if="!row.edit"
                    > 修改
                    </el-button>
                    <!--删除-->
                    <el-button
                            @click="del(row)"
                            icon="el-icon-delete"
                            size="small"
                            type="danger"
                            v-if="!row.edit"
                    > 删除
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
                :visible.sync="dialogVisible"
                v-drag-dialog
                width="30%"
        >
            <el-form
                    :model="role"
                    @submit.native.prevent
                    label-position="right"
                    label-width="100px"
                    ref="roleForm"
            >
                <el-form-item label="角色名称">
                    <el-input
                            clearable
                            v-model.trim="role.name"
                            v-p-i
                    />
                </el-form-item>
                <el-form-item label="角色备注">
                    <el-input
                            clearable
                            v-model.trim="role.remark"
                            v-p-i
                    />
                </el-form-item>
            </el-form>
            <span
                    class="dialog-footer"
                    slot="footer"
            > <el-button @click="noAdd">取 消</el-button> <el-button
                    @click="yesAdd"
                    type="primary"
            >确 定</el-button> </span></el-dialog>
    </div>
</template>
<script>
  import { deleteRole, insertRole, searchRole, updateRole } from '@/api/system/role'
  import Pagination from '@/components/Pagination'

  export default {
    name: 'Role',
    components: { Pagination },
    data() {
      const config = {
        addTitle: '新增角色'
      }
      return {
        // 新增
        dialogVisible: false,
        // 当前编辑的role,新增,授权等都是用的这个参数
        role: {},
        // 按钮列表
        roleList: [],
        // 加载状态
        listLoading: false,
        // 检索条件
        search: {},
        // 总数
        total: 0,
        // 弹窗标题
        title: '',
        // 引用文字
        config
      }
    },
    created() {
      this.refresh()
    },
    methods: {
      // 刷新列表
      async refresh() {
        this.listLoading = true
        const data = await searchRole(this.search)
        this.total = data.total
        const list = data.list
        // 行内修改用
        this.roleList = list.map(v => {
          this.$set(v, 'edit', false) // https://vuejs.org/v2/guide/reactivity.html
          v.originalName = v.name
          v.originalRemark = v.remark
          return v
        })
        this.listLoading = false
      },
      // 点击新增按钮
      add() {
        this.title = this.config.addTitle
        this.dialogVisible = true
      },
      // 取消修改
      noModify(row) {
        row.edit = false
        row.name = row.originalName
        row.remark = row.originalRemark
      },
      // 确认修改
      async yesModify(row) {
        // 打开加载浮层
        this.listLoading = true
        // 向服务器发送修改请求
        updateRole(row).then(res => {
          // 修改成功后将修改后的值赋值给原来的行
          row.originalName = row.name
          row.originalRemark = row.remark
          // 关闭编辑
          row.edit = false
          this.listLoading = false
        }).catch(reason => {
          this.listLoading = false
        })
      },
      // 删除
      async del(row) {
        await this.$confirm(`此操作将删除角色(${row.name})及其关联的信息, 是否继续?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
        await deleteRole(row.id)
        for (let i = 0; i < this.roleList.length; i++) {
          if (this.roleList[i].id === row.id) {
            this.roleList.splice(i, 1)
            break
          }
        }
      },
      // 确认新增
      async yesAdd() {
        const role = await insertRole(this.role)
        // 在表格首行添加新增的信息
        this.$set(role, 'edit', false)
        role.originalName = role.name
        role.originalRemark = role.remark
        this.roleList.unshift(role)
        this.dialogVisible = false
      },
      // 取消新增
      noAdd() {
        this.role = {}
        this.dialogVisible = false
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
