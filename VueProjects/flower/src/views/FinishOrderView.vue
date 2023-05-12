<template>
	<div class="finish">
		<!--导航栏-->
		<van-nav-bar fixed placeholder>
			<!--返回-->
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">未闻花名</span>
			</template>
		</van-nav-bar>
		<van-cell :title="tips" :border="false"></van-cell>
		<!--订单内容-->
		<van-card :price="parseFloat(order.price).toFixed(2)" :desc="position.name+' '+position.tel+' '+showAddress"
			:thumb="require('../assets/commodity/'+good.picture)" :title="good.name" :num="order.num" v-if="good.picture!==undefined">
			<template #footer>
				<router-link to="/">
					<van-button size="small" color="plum">返回首页</van-button>
				</router-link>
				<van-button size="small" color="plum" v-if="isFinished==='false'" @click="addOrder()">继续支付</van-button>
			</template>
		</van-card>
		<!--广告栏-->
		<van-cell title="更多好物"></van-cell>
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
	</div>
</template>

<script>
	import axios from 'axios'
	export default {
		name: 'finishOrder',
		data() {
			return {
				order: {},
				good:{},
				position:{},
				isFinished:true,
				tips:'',
				showAddress:'',
				goods:[],
				showPay:false,
				orderCancelled:[]
			}
		},
		mounted() {
			this.isFinished=this.$route.query.isFinished
			if(this.isFinished==='true'){
				this.tips="您已完成订单，将在48小时内发货~"
				this.order=this.$route.query.order
			}
			else{
				this.tips="啊哦，还差一步就可以完成订单了哦~"
				this.orderCancelled=JSON.parse(sessionStorage.getItem("orderCancelled"))
				this.order=this.orderCancelled[this.orderCancelled.length-1]
			}
			this.findAllGoods()
			this.findPositionsById()
		},
		methods: {
			addOrder() {
				sessionStorage.setItem("flag",JSON.stringify('未付款'))
				this.$router.push({
					path:'/order',
				});
			},
			findAllGoods() {
				axios.get('http://localhost:5210/andsue/findAllGoods').then((res) => {
					this.goods.splice(0, this.goods.length)
					this.goods = this.goods.concat(res.data)
					var index=this.goods.findIndex((item)=>item.id===this.order.fid)
					this.good=this.goods[index]
					//console.log(this.goods)
					//console.log(this.good.picture)
					//this.good.picture=require("../assets/commodity/"+this.good.picture)
				}).catch(res => alert(res));
			},
			findPositionsById() {
				axios.get('http://localhost:5210/andsue/findPositionById', {
					params: {
						id: this.order.pid
					}
				}).then((res) => {
					this.position=res.data
					var ads = this.position.address.split(" ")
					this.showAddress = ads[3]
				}).catch(res => alert(res));
			},
			getDetail:function(id){
				this.$router.push({
					path:'/good-detail',
					query:{
						fid:id
					}
				})
			},
		},
	}
</script>

<style>
</style>