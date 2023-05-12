<!--*@author:Sue
    *我的页面
    *只有设置、上传、地址、订单、收藏能用-->
<template>
	<div class="mine">
		<!--导航栏-->
		<van-nav-bar fixed placeholder>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">未闻花名</span>
			</template>
			<template #right>
				<router-link to="/setting">
					<van-icon name="setting-o" color="plum" size="18"></van-icon>
				</router-link>
			</template>
		</van-nav-bar>
		<!--我的信息-->
		<van-list>
			<van-cell :border='false'>
				<van-row>
					<van-col span="8">
						<router-link to="/upload-photo">
							<van-image :src="user.photo" height="90" width="90" round fit="cover"></van-image>
						</router-link>
					</van-col>
					<van-col span="16">
						<van-cell :title="user.name" size="large" :label="'用户编号：'+user.id" />
						<!--<p style="font-weight: bold;">{{user.name}}</p>
						<p>用户编号：{{user.id}}</p>
						<p>手机号码：{{user.tel}}</p>-->
					</van-col>
				</van-row>
			</van-cell>
		</van-list>
		<!--功能按钮-->
		<van-cell title="用户功能" size="large"></van-cell>
		<van-grid clickable :column-num="4" :border="false">
			<van-grid-item icon="like-o" text="收藏" to="/love"></van-grid-item>
			<van-grid-item icon="credit-pay" text="卡包"></van-grid-item>
			<van-grid-item icon="home-o" text="地址" to="/position"></van-grid-item>
			<van-grid-item icon="guide-o" text="足迹" to="/hist"></van-grid-item>
		</van-grid>
		<!--订单按钮-->
		<van-cell title="我的订单" size="large" is-link @click="showOrders('全部')"></van-cell>
		<van-grid clickable :column-num="5" :border="false">
			<van-grid-item icon="bill-o" text="未付款" @click="showOrders('未付款')"></van-grid-item>
			<van-grid-item icon="orders-o" text="未发货" @click="showOrders('未发货')"></van-grid-item>
			<van-grid-item icon="logistics" text="已发货" @click="showOrders('已发货')"></van-grid-item>
			<van-grid-item icon="records" text="待评价" @click="showOrders('待评价')"></van-grid-item>
			<van-grid-item icon="failure" text="已退货" @click="showOrders('已退货')"></van-grid-item>
		</van-grid>
		<!--页底广告-->
		<van-cell>
			<van-image :src="require('../assets/banner/nav2.jpg')"></van-image>
		</van-cell>
		<!--菜单栏-->
		<van-tabbar fixed route active-color="plum" placeholder>
			<van-tabbar-item replace to="/" icon="wap-home-o">首页</van-tabbar-item>
			<van-tabbar-item replace to="/info" icon="comment-o">信息</van-tabbar-item>
			<van-tabbar-item replace to="/cart" icon="cart-o">购物车</van-tabbar-item>
			<van-tabbar-item replace to="/mine" icon="friends-o">我的</van-tabbar-item>
		</van-tabbar>
	</div>
</template>

<script>
	export default {
		name: 'MinePage',
		data() {
			return {
				user: {
					id: 0,
					name: '未登录',
					tel: '',
					job: '',
					sex: '',
					password: '',
					photo: ''
				},
				isLoading: false
			}
		},
		mounted: function() {
			this.getUser()
		},
		methods: {
			getUser: function() {
				this.user = JSON.parse(localStorage.getItem("user"))
				this.user.photo = require("../assets/user/" + this.user.photo)
				//console.log(this.user)
			},
			judgeUpdate: function() {
				this.isUpdate = this.$route.query.isUpdate
				if (this.isUpdate === true) {
					this.getUser()
				}
			},
			showOrders(text){
				sessionStorage.setItem("flag",JSON.stringify(text))
				this.$router.push({
					path:"/order",
				})
			}
		}
	}
</script>

<style>
	p {
		font-family: 方正正黑简体;
		font-size: 16px;
	}
</style>
