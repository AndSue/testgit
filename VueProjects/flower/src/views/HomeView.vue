<!--*主页
	*扫一扫、热卖、高分、新品不能用
	-->
<template>
	<div class="home">
		<!--导航栏-->
		<van-nav-bar fixed placeholder>
			<template #left>
				<van-icon name="scan" color="plum" size="18" @click="scan"></van-icon>
			</template>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">未闻花名</span>
			</template>
			<template #right>
				<router-link to="/search">
					<van-icon name="search" color="plum" size="18"></van-icon>
				</router-link>
			</template>
		</van-nav-bar>
		<!--轮播图-->
		<van-swipe :autoplay="3000" indicator-color="plum">
			<van-swipe-item v-for="(image,index) in images" :key="index">
				<van-image v-lazy="image" :src="image" fit="cover" height="200" width="100%"/>
			</van-swipe-item>
		</van-swipe>
		<!--商品分类-->
		<van-grid clickable :column-num="4" :border="false">
			<van-grid-item icon="flower-o" text="花束" @click="getType('花束')"></van-grid-item>
			<van-grid-item icon="photo-o" text="挂画" @click="getType('装饰画')"></van-grid-item>
			<van-grid-item icon="diamond-o" text="饰品" @click="getType('饰品')"></van-grid-item>
			<van-grid-item icon="bars" text="分类" to="/type"></van-grid-item>
			<van-grid-item icon="discount" text="特惠" to="/discount"></van-grid-item>
			<van-grid-item icon="hot-o" text="热卖"></van-grid-item>
			<van-grid-item icon="good-job-o" text="高分"></van-grid-item>
			<van-grid-item icon="new-arrival-o" text="新品"></van-grid-item>
		</van-grid>
		<!--广告位-->
		<van-cell>
			<van-count-down :time="time">
				<template #default="timeData">
					<span class="colon">距离本次特惠结束还剩</span>
					<span class="block">{{ timeData.hours }}</span>
					<span class="colon">:</span>
					<span class="block">{{ timeData.minutes }}</span>
					<span class="colon">:</span>
					<span class="block">{{ timeData.seconds }}</span>
				</template>
			</van-count-down>
		</van-cell>
		<template v-for="(good,index) in goods">
			<van-card :key="index" :price="good.discount.toFixed(2)" :origin-price="good.price.toFixed(2)" :desc="good.detail"
				:thumb="require('../assets/commodity/'+good.picture)" :title="good.name" v-if="good.discount<good.price"
				@click="getDetail(good.id)">
				<template #tags>
					<van-tag plain color="plum">{{good.type}}</van-tag>
					<van-tag plain color="plum">特惠</van-tag>
				</template>
			</van-card>
		</template>
		<!--菜单栏-->
		<van-tabbar fixed route active-color="plum" placeholder>
			<van-tabbar-item replace to="/" icon="wap-home-o">首页</van-tabbar-item>
			<van-tabbar-item replace :to="info" icon="comment-o">信息</van-tabbar-item>
			<van-tabbar-item replace :to="cart" icon="cart-o">购物车</van-tabbar-item>
			<van-tabbar-item replace :to="mine" icon="friends-o">我的</van-tabbar-item>
		</van-tabbar>
	</div>
</template>

<script>
	import axios from 'axios'
  import {Toast} from "vant";

  export default {
		name: 'homePage',
		data() {
			return {
				images: [
					require('../assets/banner/nav1.jpg'),
					require('../assets/banner/nav2.jpg'),
					require('../assets/banner/nav3.jpg')
				],
				goods:[],
				time:300*1000*60*60,
				info:'/info',
				cart:'/cart',
				mine:'/mine'
			}
		},
		mounted(){
			this.judgeLogin()
			this.findAllGoods()
		},
		methods: {
			findAllGoods() {
				axios.get('http://localhost:5210/andsue/findAllGoods').then((res) => {
					this.goods.splice(0, this.goods.length)
					this.goods = this.goods.concat(res.data)
				}).catch(res => alert(res));
			},
			getDetail(id){
				this.$router.push({
					path:'/good-detail',
					query:{
						fid:id
					}
				})
			},
			getType(type){
				this.$router.push({
					path:'/flower-brunch',
					query:{
						type:type
					}
				})
			},
			judgeLogin(){
				var user=JSON.parse(localStorage.getItem("user"))
				if(user === null || user === undefined){
					this.info="/login"
					this.cart="/login"
					this.mine="/login"
				}
			},
      scan(){
        Toast.success("欢迎使用扫一扫功能~")
      }
		}
	}
</script>

<style>
	.van-swipe-item {
		height: 200px;
	}
	.colon {
		display: inline-block;
		margin: 0 4px;
		color: plum;
		font-weight: bold;
	}
	
	.block {
		display: inline-block;
		width: 22px;
		color: #fff;
		font-size: 12px;
		text-align: center;
		background-color: plum;
	}
</style>
