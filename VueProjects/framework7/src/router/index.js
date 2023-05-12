import Vue from 'vue'
import VueRouter from 'vue-router'
import HomePage from '../views/HomeView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomePage
  },
  {
	path:'/search',
	name:'search',
	component:()=>import('../views/SearchView.vue')
  }
]

export default routes
