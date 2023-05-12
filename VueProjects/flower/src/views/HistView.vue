<!--*@author:sue
	*历史记录页面-->
<template>
	<div class="hist">
		<!--导航栏-->
		<van-nav-bar fixed placeholder>
			<!--返回-->
			<template #left>
				<van-icon name="arrow-left" color="plum" size="18" @click="goBack()"></van-icon>
			</template>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">我的足迹</span>
			</template>
		</van-nav-bar>
		<!--无搜索结果默认内容-->
		<van-empty description="你还没有浏览过商品哦~" v-if="!isFind" />
		<!--展示搜索结果-->
		<template v-else>
			<template v-for="(good,index) in showGoods">
				<van-card :price="good.discount.toFixed(2)" :origin-price="good.price.toFixed(2)" :desc="good.detail"
					:thumb="require('../assets/commodity/'+good.picture)" :title="good.name" :key="index"
					v-if="good.price>good.discount" @click="getDetail(good.id)">
					<template #tags>
						<van-tag plain color="plum">{{good.type}}</van-tag>
						<van-tag plain color="plum">特惠</van-tag>
						<van-tag plain color="plum" v-if="false">高分</van-tag>
						<van-tag plain color="plum" v-if="false">新品</van-tag>
					</template>
				</van-card>
				<van-card :price="good.discount.toFixed(2)" :desc="good.detail"
					:thumb="require('../assets/commodity/'+good.picture)" :title="good.name" :key="index" v-else
					@click="getDetail(good.id)">
					<template #tags>
						<van-tag plain color="plum">{{good.type}}</van-tag>
						<van-tag plain color="plum" v-if="false">高分</van-tag>
						<van-tag plain color="plum" v-if="false">新品</van-tag>
					</template>
				</van-card>
			</template>
			<van-divider
				:style="{ color: 'plum', borderColor: 'plum', padding: '0 16px' }">
				到底啦~去看看更多商品吧！
			</van-divider>
		</template>
		
	</div>
</template>

<script>
	import axios from 'axios'
	import {
		Toast
	} from 'vant'
	export default {
		name: "histPage",
		data() {
			return {
				fid: '',
				goods: [],
				showGoods:[],
				user:{},
				isFind:false,
				hist:{}
			}
		},
		mounted: function() {
			this.getUser()
			this.findAllGoods()
		},
		methods: {
			goBack: function() {
				this.$router.back()
			},
			showHist(){
				this.hist=JSON.parse(localStorage.getItem("hist"))
				if(this.hist&&this.hist[this.user.id]!==null){
					this.isFind=true
					this.hist[this.user.id].forEach((fid)=>{
						var index=this.goods.findIndex(item=>item.id==fid)
						if(index>=0){
							this.showGoods.push(this.goods[index])
						}
					})
				}
			},
			findAllGoods: function() {
				axios.get('http://localhost:5210/andsue/findAllGoods').then((res) => {
					this.goods.splice(0, this.goods.length)
					this.goods = this.goods.concat(res.data)
					this.showHist()
				}).catch(res => alert(res));
			},
			getUser() {
				this.user = JSON.parse(localStorage.getItem("user"))
			},
			onBuyClicked(skuData) {
				if(this.user==null||this.user==undefined){
					this.$router.push({
						path:'/login'
					})
				}
				else{
					var order={}
					order.fid=this.good.id
					order.uid=this.user.id
					order.num=skuData.selectedNum
					order.price=this.good.discount
					order.detail=skuData.messages.message_0
					order.isEnough=true
					order.isSend=false
					order.isReceive=false
					sessionStorage.setItem("order",JSON.stringify(order))
					this.$router.push({
						path:'/add-order',
					})
				}
			},
			onCartClicked(){
				
			},
			setSku: function() {
				this.sku.price = this.good.discount
				this.sku.stock_num = this.good.num // 商品总库存
				this.sku.collection_id = this.good.id // 无规格商品 skuId 取 collection_id，否则取所选 sku 组合对应的 id
				this.sku.list[0].id = this.good.id
				this.sku.list[0].stock_num = this.good.num
				this.sku.list[0].price = this.good.discount
			},
			onSelect: function() {
				Toast.success('分享成功！');
				this.showShare = false;
			},
		}
	}
</script>

<style>
</style>