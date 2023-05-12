<!--*@author:sue
	*确认订单界面
	*已完成
	*按钮模拟支付两种情况
	*变量存在sessionStorage里-->
<template>
	<div class="addOrder">
		<!--导航栏-->
		<van-nav-bar fixed placeholder>
			<!--返回-->
			<template #left>
				<van-icon name="arrow-left" color="plum" size="18" @click="goBack()"></van-icon>
			</template>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">未闻花名</span>
			</template>
		</van-nav-bar>
		<!--地址-->
		<van-cell is-link :title="showAddress" :label="position.name+' '+position.tel" to="/position"
			icon="wap-home" center size="large" :border="false"></van-cell>
		<!--商品信息-->
		<div style="margin: 16px;">
			<van-row>
				<van-col span="6">
					<van-image :src="good.picture" height="80" width="80"></van-image>
				</van-col>
				<van-col span="11">
					<van-cell :title="good.name" :label="good.detail" :border="false"></van-cell>
				</van-col>
				<van-col span="7">
					<p style="text-align: right;margin-top: 10px;">￥{{parseFloat(order.price).toFixed(2)}}</p>
					<br>
					<br>
					<van-stepper v-model="order.num" :max="good.num" />
				</van-col>
			</van-row>
		</div>
		<van-cell title="留言" :value="order.detail" :border="false"></van-cell>
		<van-cell title="配送服务" value="快递免邮,付款后48小时内发货"></van-cell>
		<!--卡券-->
		<van-coupon-cell :coupons="coupons" :chosen-coupon="chosenCoupon" @click="showList = true" />
		<!-- 优惠券列表 -->
		<van-popup v-model="showList" round position="bottom" style="height: 90%; padding-top: 4px;">
			<van-coupon-list :coupons="coupons" :chosen-coupon="chosenCoupon" :disabled-coupons="disabledCoupons"
				@change="onChange" @exchange="onExchange" />
		</van-popup>
		<!--付款方式-->
		<div style="height: 5px;"></div>
		<van-radio-group v-model="pay">
			<van-cell title="微信" icon="wechat-pay" clickable @click="pay = 'wechat'" :border="false">
				<template #right-icon>
					<van-radio name="wechat" checked-color="plum"></van-radio>
				</template>
			</van-cell>
			<van-cell title="支付宝" icon="alipay" clickable @click="pay = 'alipay'">
				<template #right-icon>
					<van-radio name="alipay" checked-color="plum"></van-radio>
				</template>
			</van-cell>
		</van-radio-group>
		<!--确认按钮-->
		<van-submit-bar button-color="plum" :label="'共'+order.num+'件 合计：'" :price="order.num*order.price*100"
			button-text="提交订单" @submit="clickBuy()" />
		<van-popup v-model="showPay" position="bottom" style="padding-top: 4px;">
			<div style="margin: 16px;">
				<van-button round block color="plum" text="确认支付" @click="addOrder"></van-button>
			</div>
			<div style="margin: 16px;">
				<van-button round block color="#b192df" text="取消支付" @click="cancelOrder"></van-button>
			</div>
		</van-popup>
	</div>
</template>

<script>
	const coupon = {
		available: 1,
		condition: '无使用门槛\n最多优惠12元',
		reason: '',
		value: 150,
		name: '优惠券名称',
		startAt: 1489104000,
		endAt: 1514592000,
		valueDesc: '1.5',
		unitDesc: '元',
	};

	import axios from 'axios'
	import {
		Toast
	} from 'vant'
	export default {
		name: 'addOrder',
		data() {
			return {
				order: {
					price: '',
					num: ''
				},
				position: {
					address: "",
					name: '暂无信息',
					tel: ''
				},
				good: {
					name: '商品名称',
					picture: ''
				},
				isFind: false,
				pay: 'wechat',
				chosenCoupon: -1,
				coupons: [coupon],
				disabledCoupons: [coupon],
				showList: false,
				showPay:false,
				showAddress:'你还没有添加地址或设置默认地址哦'
			}
		},
		mounted() {
			this.order = JSON.parse(sessionStorage.getItem("order"))
			if(this.order==null){
				this.goBack()
			}
			this.order.pid=JSON.parse(sessionStorage.getItem("pid"))
			if(this.order.pid==null){
				this.findPositionsByUid()
			}
			else{
				this.findPositionsById()
			}
			this.findGoodById()
		},
		methods: {
			clickBuy(){
				if(this.order.pid===null){
					Toast.fail("您没有选择收货地址！")
				}
				else{
					this.showPay=true
				}
			},
			addOrder() {
				sessionStorage.setItem("order",null)
				sessionStorage.setItem("pid",null)
				axios.get('http://localhost:5210/andsue/addNewOrder', {
					params: {
						fid:this.order.fid,
						uid:this.order.uid,
						pid:this.order.pid,
						detail:this.order.detail,
						price:this.order.price,
						num:this.order.num
					}
				}).then((res) => {
					if(res.data>0){
						this.$router.push({
							path:'/finish-order',
							query:{
								isFinished:true,
								order:this.order
							}
						});
					}
					else{
						Toast.fail("添加订单失败！")
					}
				}).catch(res => alert(res));
			},
			cancelOrder(){
				sessionStorage.setItem("order",null)
				sessionStorage.setItem("pid",null)
				var orderCancelled = JSON.parse(sessionStorage.getItem("orderCancelled"))
				if(orderCancelled===null){
					orderCancelled=[]
				}
				orderCancelled.push(this.order)
				sessionStorage.setItem("orderCancelled", JSON.stringify(orderCancelled))
				this.$router.push({
					path:'/finish-order',
					query:{
						isFinished:false,
						order:this.order
					}
				});
			},
			findGoodById() {
				axios.get('http://localhost:5210/andsue/findGoodById', {
					params: {
						id: this.order.fid + ''
					}
				}).then((res) => {
					this.good = res.data
					this.good.picture = require('../assets/commodity/' + this.good.picture)
				}).catch(res => alert(res));
			},
			findPositionsByUid() {
				axios.get('http://localhost:5210/andsue/findPositionsByUid', {
					params: {
						uid: this.order.uid
					}
				}).then((res) => {
					if (res.data.length > 0) {
						var index = res.data.findIndex(pos => pos.default === true)
						if (index != -1) {
							this.position = res.data[index]
							var ads = this.position.address.split(" ")
							this.showAddress = ads[3]
							this.order.pid=this.position.id
						}
					}
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
			goBack() {
				sessionStorage.setItem("order",null)
				sessionStorage.setItem("pid",null)
				this.$router.back();
			},
			onChange(index) {
				this.showList = false;
				this.chosenCoupon = index;
			},
			onExchange() {
				this.coupons.push(coupon);
			},
		},
	}
</script>

<style scope>
	.custom-title {
		margin-right: 4px;
		vertical-align: middle;
	}

	.search-icon {
		font-size: 16px;
		line-height: inherit;
	}
</style>
