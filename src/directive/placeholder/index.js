/**
 * 定义了几个常用的文本框提示文本,已在main.js引入
 */

import Vue from 'vue'

// 提交以后禁用按钮一段时间，防止重复提交
Vue.directive('no-more-click', {
  inserted(el, binding) {
    el.addEventListener('click', e => {
      el.classList.add('is-disabled')
      el.disabled = true
      setTimeout(() => {
        el.disabled = false
        el.classList.remove('is-disabled')
      }, 5000)
    })
  }
})

// 在el-input组件中添加''请输入''(please input)提示
Vue.directive('p-i', {
  bind(el, binding) {
    if (el.getElementsByTagName('input')[0]) {
      el.getElementsByTagName('input')[0].placeholder = '请输入'
    }
  }
})

// 在el-input组件中添加''请选择''(please select)提示
Vue.directive('p-s', {
  bind(el, binding) {
    if (el.getElementsByTagName('input')[0]) {
      el.getElementsByTagName('input')[0].placeholder = '请选择'
    }
  }
})

// 在el-input组件中添加''请输入关键词''(please input keyword)提示
Vue.directive('p-i-k', {
  bind(el, binding) {
    if (el.getElementsByTagName('input')[0]) {
      el.getElementsByTagName('input')[0].placeholder = '请输入关键词'
    }
  }
})
