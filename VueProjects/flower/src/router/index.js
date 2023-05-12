import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'

Vue.use(VueRouter)

const routes = [{
		path: '/',
		name: 'home',
		component: HomeView
	},
	{
		path: '/mine',
		name: 'mine',
		// route level code-splitting
		// this generates a separate chunk (about.[hash].js) for this route
		// which is lazy-loaded when the route is visited.
		component: () => import( /* webpackChunkName: "about" */ '../views/MineView.vue')
	},
	{
		path: '/cart',
		name: 'cart',
		// route level code-splitting
		// this generates a separate chunk (about.[hash].js) for this route
		// which is lazy-loaded when the route is visited.
		component: () => import( /* webpackChunkName: "about" */ '../views/CartView.vue')
	},
	{
		path: '/info',
		name: 'info',
		// route level code-splitting
		// this generates a separate chunk (about.[hash].js) for this route
		// which is lazy-loaded when the route is visited.
		component: () => import( /* webpackChunkName: "about" */ '../views/InfoView.vue')
	},
	{
		path: '/search',
		name: 'search',
		component: () => import( /* webpackChunkName: "about" */ '../views/SearchView.vue')
	},
	{
		path: '/setting',
		name: 'setting',
		component: () => import( /* webpackChunkName: "about" */ '../views/SettingView.vue')
	},
	{
		path: '/discount',
		name: 'discount',
		component: () => import( /* webpackChunkName: "about" */ '../views/DiscountView.vue')
	},
	{
		path: '/flower-brunch',
		name: 'flowerBrunch',
		component: () => import( /* webpackChunkName: "about" */ '../views/FlowerBrunchView.vue')
	},
	{
		path: '/good-detail',
		name: 'goodDetail',
		component: () => import( /* webpackChunkName: "about" */ '../views/GoodDetailView.vue'),
		meta: {
			keepAlive: true //此页面需要缓存
		}
	},
	{
		path: '/type',
		name: 'type',
		component: () => import( /* webpackChunkName: "about" */ '../views/TypeView.vue')
	},
	{
		path: '/login',
		name: 'login',
		component: () => import( /* webpackChunkName: "about" */ '../views/LoginView.vue')
	},
	{
		path: '/logon',
		name: 'logon',
		component: () => import( /* webpackChunkName: "about" */ '../views/LogonView.vue')
	},
	{
		path: '/upload-photo',
		name: 'uploadPhoto',
		component: () => import( /* webpackChunkName: "about" */ '../views/UploadPhoto.vue')
	},
	{
		path: '/position',
		name: 'position',
		component: () => import( /* webpackChunkName: "about" */ '../views/PositionView.vue')
	},
	{
		path: '/position-update',
		name: 'positionUpdate',
		component: () => import( /* webpackChunkName: "about" */ '../views/UpdatePositionView.vue')
	},
	{
		path: '/add-order',
		name: 'addOrder',
		component: () => import( /* webpackChunkName: "about" */ '../views/AddOrderView.vue'),
		meta: {
			keepAlive: true //此页面需要缓存
		}
	},
	{
		path: '/finish-order',
		name: 'finishOrder',
		component: () => import( /* webpackChunkName: "about" */ '../views/FinishOrderView.vue')
	},
	{
		path: '/order',
		name: 'showOrders',
		component: () => import( /* webpackChunkName: "about" */ '../views/OrderView.vue')
	},
	{
		path: '/order-detail',
		name: 'orderDetail',
		component: () => import( /* webpackChunkName: "about" */ '../views/OrderDetailView.vue')
	},
	{
		path: '/hist',
		name: 'histPage',
		component: () => import( /* webpackChunkName: "about" */ '../views/HistView.vue')
	},
	{
		path: '/love',
		name: 'lovePage',
		component: () => import( /* webpackChunkName: "about" */ '../views/LoveView.vue')
	},
]

const router = new VueRouter({
	routes,
	scrollBehavior(to, from, savePosition) {
		if (savePosition) {
			//解决页面从列表页跳转到详情页返回,初始在原来位置
			return savePosition
		} else {
			//解决页面跳转后页面高度和前一个页面高度一样
			return {
				x: 0,
				y: 0
			}
		}
	}
})

export default router
