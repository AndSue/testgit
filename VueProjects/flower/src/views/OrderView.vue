<!--*@author:sue
	*订单页面
	*只有支付按钮能用
	*评价系统待完善-->
<template>
	<div class="order">
		<!--导航栏-->
		<van-nav-bar fixed placeholder>
			<!--返回-->
			<template #left>
				<router-link to="/mine">
					<van-icon name="arrow-left" color="plum" size="18"></van-icon>
				</router-link>
			</template>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">我的订单</span>
			</template>
		</van-nav-bar>
		<van-tabs v-model="title" color="#b192df" title-active-color="#b192df" @click="changeTags()">
			<van-tab title="全部" name="全部"></van-tab>
			<van-tab title="未付款" name="未付款"></van-tab>
			<van-tab title="未发货" name="未发货"></van-tab>
			<van-tab title="已发货" name="已发货"></van-tab>
			<van-tab title="待评价" name="待评价"></van-tab>
			<van-tab title="已退货" name="已退货"></van-tab>
		</van-tabs>
		<!--订单内容-->
		<template v-for="(order,index) in orders">
			<van-card :price="parseFloat(order.price).toFixed(2)" v-if="goods[order.fid]" :desc="goods[order.fid].detail||''"
				:thumb="require('../assets/commodity/'+goods[order.fid].picture)" 
				:title="goods[order.fid].name" :num="order.num" :key="index">
				<template #footer>
					<van-button size="small" color="darkgray" @click="showOrderDetail(index)" round plain>查看详情</van-button>
          <template v-if="order.shown==undefined">
            <van-button size="small" round text="删除订单" color="darkgray"  plain />
            <van-button size="small" color="plum"
                        @click="showPay=true;chosenId=index"
                        round plain>继续支付</van-button>
          </template>
          <template v-else-if="order.shown==true">
            <van-button size="small" round text="查看物流" color="darkgray" v-if="order.send==true" plain />
            <van-button size="small" round text="催催发货" color="darkgray" v-if="order.send==false" plain />
            <van-button size="small" color="plum" v-if="order.send==false"
                        round plain @click="deleteById(index)">商品退货</van-button>
            <van-button size="small" color="plum" @click="setReceive()"
                        v-if="order.send===true&&order.receive===false" round plain>确认到货</van-button>
            <van-button size="small" color="plum" v-if="order.receive===true" round plain>评价商品</van-button>
          </template>
				</template>
			</van-card>
		</template>
		<van-popup v-model="showPay" position="bottom" style="padding-top: 4px;">
			<div style="margin: 16px;">
				<van-button round block color="plum" text="确认支付" @click="addOrder"></van-button>
			</div>
			<div style="margin: 16px;">
				<van-button round block color="#b192df" text="取消支付" @click="showPay=false"></van-button>
			</div>
		</van-popup>
		<!--订单无内容-->
		<van-empty :description="tips" v-if="!isFind"></van-empty>
		<div style="height: 20px;"></div>
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
	import {
		Toast,Dialog
	} from 'vant'
	export default {
		name: 'finishOrder',
		data() {
			return {
				orders: [],
				positions:[],
				showAddress:'',
				goods:[],
				good:{
					id:'',
					detail:'',
					picture:'',
					num:0
				},
				showPay:false,
				orderCancelled:[],
				chosenId:0,
				flag:'',
				user:{},
				isFind:true,
				tips:'',
				title:''
			}
		},
		mounted() {
			this.flag=JSON.parse(sessionStorage.getItem("flag"))
			this.findAllGoods()
			this.getUser()
			this.title=this.flag
			this.changeTags()
		},
		methods: {
			changeTags(){
				if(this.title=='未付款'){
					this.orders=JSON.parse(sessionStorage.getItem("orderCancelled"))
					if(this.orders==null){
						this.isFind=false
					}
					this.tips="还没有未付款的订单哦~"
				}
				else if(this.title=='未发货'){
					this.findNotSendByUid()
					this.tips="还没有未发货的订单哦~"
				}
				else if(this.title=='已发货'){
					this.findSendByUid()
					this.tips="还没有已发货的订单哦~"
				}
				else if(this.title=='待评价'){
					this.findReceiveByUid()
					this.tips="还没有待评价的订单哦~"
				}
				else if(this.title=='全部'){
					this.findByUid()
					this.tips="你还没有订单哦~"
				}
				else{
					this.findDeletedByUid()
					this.tips="还没有已退货的订单哦~"
				}
				sessionStorage.setItem("flag",JSON.stringify(this.title))
			},
			getUser() {
				this.user = JSON.parse(localStorage.getItem("user"))
				this.user.photo = require("../assets/user/" + this.user.photo)
			},
			addOrder() {
				sessionStorage.setItem("orderCancelled",JSON.stringify(this.orderCancelled))
				axios.get('http://localhost:5210/andsue/addNewOrder', {
					params: {
						fid:this.goods[this.orders[this.chosenId].fid].id,
						uid:this.orders[this.chosenId].uid,
						pid:this.orders[this.chosenId].pid,
						detail:this.orders[this.chosenId].detail,
						price:this.orders[this.chosenId].price,
						num:this.orders[this.chosenId].num
					}
				}).then((res) => {
					if(res.data>0){
						this.showPay=false
						var chosenOrder=this.orders[this.chosenId]
						chosenOrder.fid=this.goods[chosenOrder.fid].id
						this.orderCancelled.splice(this.chosenId,1)
						this.$router.push({
							path:'/finish-order',
							query:{
								isFinished:true,
								order:chosenOrder
							}
						});
					}
					else{
						Toast.fail("添加订单失败！")
					}
				}).catch(res => alert(res));
			},
			findAllGoods() {
				axios.get('http://localhost:5210/andsue/findAllGoods').then((res) => {
					this.goods.splice(0, this.goods.length)
					this.goods = this.goods.concat(res.data)
				}).catch(res => alert(res));
			},
			findByUid(){
				axios.get('http://localhost:5210/andsue/findOrdersByUid',{
					params:{
						uid:this.user.id
					}
				}).then((res) => {
					if(res.data.length>0){
						if(this.orders!==null){
							this.orders.splice(0,this.orders.length)
							this.orders=this.orders.concat(res.data)
						}
						else{
							this.orders=res.data
						}
						if(this.goods!==null){
							for(var index=0;index<this.orders.length;index+=1){
								var i=this.goods.findIndex(good=>good.id===this.orders[index].fid)
								this.orders[index].fid=i
							}
						}
						this.isFind=true
					}
					else{
						this.orders=null
						this.isFind=false
					}
				}).catch(res => alert(res));
			},
			findNotSendByUid(){
				axios.get('http://localhost:5210/andsue/findNotSendOrdersByUid',{
					params:{
						uid:this.user.id
					}
				}).then((res) => {
					if(res.data.length>0){
						if(this.orders!==null){
							this.orders.splice(0,this.orders.length)
							this.orders=this.orders.concat(res.data)
						}
						else{
							this.orders=res.data
						}
						if(this.goods!==null){
							for(var index=0;index<this.orders.length;index+=1){
								var i=this.goods.findIndex(good=>good.id===this.orders[index].fid)
								this.orders[index].fid=i
							}
						}
						this.isFind=true
					}
					else{
						this.isFind=false
					}
				}).catch(res => alert(res));
			},
			findSendByUid(){
				axios.get('http://localhost:5210/andsue/findSendOrdersByUid',{
					params:{
						uid:this.user.id
					}
				}).then((res) => {
					if(res.data.length>0){
						if(this.orders!==null){
							this.orders.splice(0,this.orders.length)
							this.orders=this.orders.concat(res.data)
						}
						else{
							this.orders=res.data
						}
						if(this.goods!==null){
							for(var index=0;index<this.orders.length;index+=1){
								var i=this.goods.findIndex(good=>good.id===this.orders[index].fid)
								this.orders[index].fid=i
							}
						}
						this.isFind=true
					}
					else{
						this.isFind=false
					}
				}).catch(res => alert(res));
			},
			findReceiveByUid(){
				axios.get('http://localhost:5210/andsue/findReceiveOrdersByUid',{
					params:{
						uid:this.user.id
					}
				}).then((res) => {
					if(res.data.length>0){
						if(this.orders!==null){
							this.orders.splice(0,this.orders.length)
							this.orders=this.orders.concat(res.data)
						}
						else{
							this.orders=res.data
						}
						if(this.goods!==null){
							for(var index=0;index<this.orders.length;index+=1){
								var i=this.goods.findIndex(good=>good.id===this.orders[index].fid)
								this.orders[index].fid=i
							}
						}
						this.isFind=true
					}
					else{
						this.isFind=false
					}
				}).catch(res => alert(res));
			},
			findDeletedByUid(){
				axios.get('http://localhost:5210/andsue/findDeletedOrdersByUid',{
					params:{
						uid:this.user.id
					}
				}).then((res) => {
					if(res.data.length>0){
						if(this.orders!==null){
							this.orders.splice(0,this.orders.length)
							this.orders=this.orders.concat(res.data)
						}
						else{
							this.orders=res.data
						}
						//console.log(this.orders)
						if(this.goods!==null){
							for(var index=0;index<this.orders.length;index+=1){
								var i=this.goods.findIndex(good=>good.id===this.orders[index].fid)
								this.orders[index].fid=i
							}
						}
						this.isFind=true
					}
					else{
						this.isFind=false
					}
				}).catch(res => alert(res));
			},
			deleteById(index){
				Dialog.confirm({
					message:"您真的要退货吗？退货操作无法撤销！"
				}).then(()=>{
					axios.get('http://localhost:5210/andsue/deleteOrderById',{
						params:{
							id:this.orders[index].id
						}
					}).then((res) => {
						if(res.data>0){
							this.findNotSendByUid()
							Toast.success("退货成功！")
						}
					}).catch(res => alert(res));
				}).catch(() => {});
			},
			showOrderDetail(index){
				var chosenOrder=this.orders[index]
				if(this.goods[chosenOrder.fid]!==undefined){
					chosenOrder.fid=this.goods[chosenOrder.fid].id
					this.$router.push({
						path:'/order-detail',
						query:{
							order:chosenOrder
						}
					});
				}
			},
			getDetail(id){
				this.$router.push({
					path:'/good-detail',
					query:{
						fid:id
					}
				})
			},
			setReceive(){
				axios.get('http://localhost:5210/andsue/updateReceiveByUid',{
					params:{
						uid:this.user.id
					}
				}).then((res) => {
					if(res.data>0){
						this.findSendByUid()
					}
				}).catch(res => alert(res));
			}
		},
	}
</script>

<style>
</style>