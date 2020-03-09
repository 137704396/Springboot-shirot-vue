<template>
    <div>
        <el-row :gutter="2">
            <!--查看-->
            <el-col :span="12">
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
                        <el-button type="primary"> 调整菜单</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button
                                @click="refresh"
                                type="primary"
                        > 刷新信息
                        </el-button>
                    </el-form-item>
                </el-form>
                <!--展示-->
                <el-tree
                        :data="routerList"
                        :expand-on-click-node="false"
                        :filter-node-method="filterNode"
                        :props="defaultProps"
                        @node-click="nodeClickHandle"
                        default-expand-all
                        highlight-current
                        node-key="id"
                        ref="routerTree"
                >
          <span
                  class="custom-tree-node"
                  slot-scope="{ node, data }">
            <span :title="data.description">{{ data.title }}</span>
            <span>
              <el-button
                      @click.stop="after(data)"
                      icon="el-icon-bottom"
                      size="mini"
                      title="添加同级菜单"
                      type="text"
              />
              <el-button
                      :disabled="!data.alwaysShow"
                      @click.stop="append(data)"
                      icon="el-icon-bottom-right"
                      size="mini"
                      title="添加子菜单"
                      type="text"
              />
              <el-button
                      :disabled="data.children&&data.children.length>0"
                      @click.stop="remove(node, data)"
                      icon="el-icon-delete"
                      size="mini"
                      title="删除"
                      type="text"
              />
            </span>
          </span>
                </el-tree>
            </el-col>
            <!--编辑-->
            <el-col :span="12">
                <el-form
                        :model="router"
                        @submit.native.prevent
                        label-position="right"
                        label-width="100px"
                        ref="routerForm"
                >
                    <el-form-item label="title">
                        <el-input
                                clearable
                                v-model.trim="router.title"
                                v-p-i
                        />
                    </el-form-item>
                    <el-form-item label="path">
                        <el-input
                                clearable
                                v-model.trim="router.path"
                                v-p-i
                        />
                    </el-form-item>
                    <el-form-item label="component">
                        <el-input
                                clearable
                                v-model.trim="router.component"
                                v-p-i
                        />
                    </el-form-item>
                    <el-form-item label="name">
                        <el-input
                                clearable
                                v-model.trim="router.name"
                                v-p-i
                        />
                    </el-form-item>
                    <el-form-item label="icon">
                        <el-select
                                :prefix="router.icon"
                                clearable
                                filterable
                                v-model="router.icon"
                                v-p-s
                        >
                            <svg-icon
                                    :icon-class="router.icon"
                                    slot="prefix"
                                    v-if="!!router.icon"
                            />
                            <el-option
                                    :key="item"
                                    :label="item"
                                    :value="item"
                                    v-for="item in svgIcons"
                            >
                                <svg-icon :icon-class="item" />
                                <span>{{ item }}</span>
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="description">
                        <el-input
                                clearable
                                type="textarea"
                                v-model="router.description"
                                v-p-i
                        />
                    </el-form-item>
                    <el-form-item label="redirect">
                        <el-input
                                clearable
                                v-model.trim="router.redirect"
                                v-p-i
                        />
                    </el-form-item>
                    <el-form-item label="层级菜单">
                        <el-switch v-model="router.alwaysShow" />
                    </el-form-item>
                    <el-form-item label="排序">
                        <el-input
                                clearable
                                v-model.number="router.order"
                                v-p-i
                        />
                    </el-form-item>
                    <el-form-item v-if="router.id>0">
                        <el-button
                                @click="yesModify"
                                type="primary"
                        >确认修改
                        </el-button>
                    </el-form-item>
                    <el-form-item v-if="router.id<0">
                        <el-button
                                @click="yesAdd"
                                type="primary"
                        >确认添加
                        </el-button>
                    </el-form-item>
                </el-form>
                详见:https://panjiachen.github.io/vue-element-admin-site/zh/guide/essentials/router-and-nav.html#配置项
            </el-col>
        </el-row>
    </div>
</template>

<script>
  import { deleteRouter, insertRouter, searchRouter, updateRouter } from '@/api/system/router'
  import svgIcons from './svg-icons'

  let id = -1
  export default {
    name: 'Router',
    data() {
      return {
        router: {},
        // 路由列表
        routerList: [],
        // 树配置
        defaultProps: {
          children: 'children',
          label: 'title'
        },
        // 用于路由过滤
        filterText: '',
        // 图标库
        svgIcons
      }
    },
    watch: {
      // 节点过滤
      filterText(val) {
        this.$refs.tree.filter(val)
      }
    },
    created() {
      this.refresh()
    },
    methods: {
      // 确认修改
      async yesModify() {
        delete this.router.meta
        this.router = await updateRouter(this.router)
      },
      // 确认添加
      async yesAdd() {
        this.router = await insertRouter(this.router)
      },
      // 追加菜单
      after(data) {
        const newChild = { id: id--, title: '新增路由', children: [], parentId: data.parentId }
        this.$refs['routerTree'].insertAfter(newChild, data.id)
        this.edit(newChild)
        this.$refs['routerTree'].setCurrentKey(newChild.id)
      },
      // 添加子菜单
      append(data) {
        const newChild = { id: id--, title: '新增路由', children: [], parentId: data.id }
        if (!data.children) {
          this.$set(data, 'children', [])
        }
        data.children.push(newChild)
        this.edit(newChild)
        this.$refs['routerTree'].setCurrentKey(newChild.id)
      },
      // 删除一个
      async remove(node, data) {
        await this.$confirm(`确定要删除此菜单(${data.title})吗`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'error'
        })
        await deleteRouter(data.id)
        const parent = node.parent
        const children = parent.data.children || parent.data
        const index = children.findIndex(d => d.id === data.id)
        children.splice(index, 1)
      },
      async refresh() {
        // 刷新信息
        this.routerList = await searchRouter()
        // 清空输入
        this.router = {}
      },
      // 节点过滤
      filterNode(value, data) {
        if (!value) return true
        return data.title.indexOf(value) !== -1
      },
      // 编辑/查看/授权
      nodeClickHandle(data) {
        this.router = data
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
