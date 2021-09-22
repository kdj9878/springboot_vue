import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Movie from '../views/Movie.vue'
import Mypage from '../views/MyPage.vue'
import Login from '../views/Login.vue'



const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/movie',
    name: 'movie',
    component : Movie
  },
  {
    path:'/mypage',
    name:'mypage',
    component : Mypage
  },
  {
    path:'/login',
    name : 'login',
    component : Login
  }
  
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),routes
})

export default router
