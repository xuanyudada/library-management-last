import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import '@/assets/global.css'
import SlideVerify from 'vue-monoplasty-slide-verify';
import Router from 'vue-router'


Vue.use(SlideVerify);

const routerPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(error=> error)}



Vue.config.productionTip = false
Vue.use(ElementUI,{size:'small'});//medium small mini

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
