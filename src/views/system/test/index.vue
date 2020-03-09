<template>
    <div>
        <el-button @click="selectUser" v-permission="'selectUser'" >测试【selectUser】权限</el-button>
            <el-button @click="deleteUser" v-permission="'deleteUser'" >测试【deleteUser】权限</el-button>
        <el-button @click="deleteUser">该用户没有deleteUser权限却拿到了deleteUser接口</el-button>
    </div>
</template>

<script>

  import permission from '@/directive/permission'
  import request from '@/utils/request'

  export default {
    name: 'Test',
    directives: { permission },
    data() {
      return {
      }
    },
    created() {
    },
    methods: {
      async selectUser() {
        const res = await request.get(process.env.VUE_APP_BASE_API+'test/selectUser')
        alert('该用户具有selectUser权限')
      },
      async deleteUser() {
        const res = await request.get(process.env.VUE_APP_BASE_API+'test/deleteUser')
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
