/**
 * 가장 먼저 실행되는 javascript 파일
 * Vue 인스턴스를 생성하는 역할을 함
 */


import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { store } from './store/index'

const app = createApp(App);


app.use(router)
app.use(store)
app.mount("#app");