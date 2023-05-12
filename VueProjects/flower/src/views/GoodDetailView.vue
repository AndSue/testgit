<!--*@author:sue
	*商品详情页面
	*不能加入购物车、分享
	-->
<template>
	<div class="good-detail">
		<!--导航栏-->
		<van-nav-bar fixed placeholder>
			<!--返回-->
			<template #left>
				<van-icon name="arrow-left" color="plum" size="18" @click="goBack()"></van-icon>
			</template>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">商品详情</span>
			</template>
			<!--分享-->
			<template #right>
				<van-icon name="share-o" color="plum" size="18" @click="showShare=true"></van-icon>
			</template>
		</van-nav-bar>
		<!--商品详细信息展示-->
		<van-image :src="this.good.picture" width="100%" height="400" fit="cover"></van-image>
		<van-cell size="large">
			<template #title>
				<span style="font-size: 28px;color:red;">
					￥{{good.discount.toFixed(2)}}
				</span>
				<span style="color:gray;text-decoration: line-through;font-size: 12px;" v-if="good.price>good.discount">
					原价￥{{good.price.toFixed(2)}}
				</span>
			</template>
			<template #label>
				<p style="color: black;font-weight: bold;font-size: 17px;">
					{{good.name}}
				</p>
			</template>
		</van-cell>
		<!--用户评价-->
		<van-cell title="用户评价" :label="'共'+commentNum+'条'" center>
			<template #default>
				<span>评分：</span>
				<van-rate v-model="rate" color="plum" allow-half readonly />
			</template>
		</van-cell>
		<!--弹出确认购买页面-->
		<van-sku v-model="showBuy" :sku="sku" :goods="goods" :goods-id="good.id" :some="goods.picture"
			:hide-stock="sku.hide_stock" :quota="good.num" :quota-used="0" :initial-sku="initialSku" :hide-quota-text="true"
			:close-on-click-overlay="closeOnClickOverlay" :stock-threshold="10" @buy-clicked="onBuyClicked">
			<!-- 自定义 sku actions -->
			<template #sku-actions="props">
				<div class="van-sku-actions">
					<!-- 直接触发 sku 内部事件，通过内部事件执行 onBuyClicked 回调 -->
					<van-button square size="large" color="plum" @click="props.skuEventBus.$emit('sku:buy')">
						确认
					</van-button>
				</div>
			</template>
		</van-sku>
		<!--弹出确认加入购物车页面-->
		<van-sku v-model="showCart" :sku="sku" :goods="goods" :goods-id="good.id" :some="goods.picture"
			:hide-stock="sku.hide_stock" :quota="good.num" :quota-used="0" :hide-quota-text="true"
			:initial-sku="initialSku" :close-on-click-overlay="closeOnClickOverlay" :stock-threshold="10"
			@buy-clicked="onCartClicked">
			<!-- 自定义 sku actions -->
			<template #sku-actions="props">
				<div class="van-sku-actions">
					<!-- 直接触发 sku 内部事件，通过内部事件执行 onBuyClicked 回调 -->
					<van-button square size="large" color="plum" @click="props.skuEventBus.$emit('sku:buy')">
						确认
					</van-button>
				</div>
			</template>
		</van-sku>
		<!--弹出分享页面-->
		<van-share-sheet v-model="showShare" title="立即分享给好友" :options="options" @select="onSelect" />
		<!--工具栏-->
		<van-goods-action>
			<van-goods-action-icon icon="chat-o" text="客服" color="plum" to="/info" />
			<van-goods-action-icon icon="cart-o" text="购物车" color="plum" to="/cart" />
			<van-goods-action-icon :icon="isLove?'like':'like-o'" :text="isLove?'已收藏':'收藏'" color="plum" @click="setLove()" />
			<van-goods-action-button type="warning" text="加入购物车" color="plum" @click="showCart=true" />
			<van-goods-action-button type="danger" text="立即购买" color="#b192df" @click="showBuy=true" />
		</van-goods-action>
	</div>
</template>

<script>
	import axios from 'axios'
	import {
		Toast
	} from 'vant'
	export default {
		name: "goodDetail",
		data() {
			return {
				fid: '',
				rate: 4.5,
				commentNum: 214,
				showShare: false,
				good: {
					id: '',
					num: 0,
					discount: 1.0,
					detail: '',
					name: '',
					type: '',
					price: 1.0,
				},
				showBuy: false,
				showCart: false,
				goods: {
					picture: require('../assets/commodity/shu1.jpg')
				},
				sku: {
					tree: [{
						k: '颜色', // skuKeyName：规格类目名称
						k_s: 's1', // skuKeyStr：sku 组合列表（下方 list）中当前类目对应的 key 值，value 值会是从属于当前类目的一个规格值 id
						v: [{
							id: '1', // skuValueId：规格值 id
							name: '默认', // skuValueName：规格值名称
						}],
						largeImageMode: false, //  是否展示大图模式
					}],
					list: [{
						id: 2259, // skuId
						s1: '1', // 规格类目 k_s 为 s1 的对应规格值 id
						price: 100, // 价格（单位分）
						stock_num: 110 // 当前 sku 组合对应的库存
					}],
					price: 100, // 默认价格（单位元）
					stock_num: 100,
					collection_id: '',
					none_sku: true, // 是否无规格商品
					hide_stock: false, // 是否隐藏剩余库存
					messages: [{
						// 商品留言
						datetime: '0', // 留言类型为 time 时，是否含日期。'1' 表示包含
						multiple: '1', // 留言类型为 text 时，是否多行文本。'1' 表示多行
						name: '留言', // 留言名称
						type: 'text', // 留言类型，可选: id_no（身份证）, text, tel, date, time, email
						required: '0', // 是否必填 '1' 表示必填
						placeholder: '', // 可选值，占位文本
						extraDesc: '' // 可选值，附加描述文案
					}],
				},
				initialSku: {
					s1: '1',
					selectedNum: 1
				},
				closeOnClickOverlay: true,
				options: [{
						name: '微信',
						icon: 'wechat'
					},
					{
						name: '微博',
						icon: 'weibo'
					},
					{
						name: 'QQ',
						icon: 'qq'
					},
					{
						name: '复制链接',
						icon: 'link'
					},
					{
						name: '分享图片',
						icon: 'poster'
					},
					{
						name: '二维码',
						icon: 'qrcode'
					},
				],
				user:{},
				love:{},
				isLove:false
			}
		},
		mounted: function() {
			this.fid = this.$route.query.fid
			this.findById()
			this.getUser()
      this.getLove()
			this.setHist()
		},
		methods: {
			goBack() {
				this.$router.back()
			},
			setHist(){
				var hist=JSON.parse(localStorage.getItem("hist"))
				if(hist&&hist[this.user.id]!==null){
					var index=hist[this.user.id].indexOf(this.fid)
					if(index!==-1){
						hist[this.user.id].splice(index,1)
					}
					hist[this.user.id].splice(0,0,this.fid)
				}
				else{
					hist={}
					hist[this.user.id]=[this.fid]
				}
				localStorage.setItem("hist",JSON.stringify(hist))
			},
			setLove(){
				if(!this.isLove){  //收藏
					if(this.love&&this.love[this.user.id]){
						var index=this.love[this.user.id].indexOf(this.fid)
						if(index!==-1){
							this.love[this.user.id].splice(index,1)
						}
						this.love[this.user.id].splice(0,0,this.fid)
					}
					else{
						this.love={}
						this.love[this.user.id]=[this.fid]
					}
					localStorage.setItem("love",JSON.stringify(this.love))
					this.isLove=true
					Toast.success("收藏成功！")
				}
				else{  //取消收藏
					var i=this.love[this.user.id].indexOf(this.fid)
					if(i>=0){
						if(this.love[this.user.id].length>1){
							this.love[this.user.id].splice(i,1)
						}
						else{
							this.$delete(this.love,this.user.id)
						}
					}
					localStorage.setItem("love",JSON.stringify(this.love))
					this.isLove=false
					Toast.success("取消收藏成功！")
				}
			},
			getLove(){
				this.love=JSON.parse(localStorage.getItem("love"))
				if(this.love&&this.love[this.user.id]){
					var index=this.love[this.user.id].indexOf(this.fid)
					if(index>=0){
						this.isLove=true
					}
				}
			},
			findById() {
				axios.get('http://localhost:5210/andsue/findGoodById', {
					params: {
						id: this.fid
					}
				}).then((res) => {
					this.good = res.data
					//console.log(res.data)
					this.goods.picture = require('../assets/commodity/' + this.good.picture)
					this.good.picture = require('../assets/commodity/' + this.good.picture)
					this.setSku()
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
			setSku() {
				this.sku.price = this.good.discount
				this.sku.stock_num = this.good.num // 商品总库存
				this.sku.collection_id = this.good.id // 无规格商品 skuId 取 collection_id，否则取所选 sku 组合对应的 id
				this.sku.list[0].id = this.good.id
				this.sku.list[0].stock_num = this.good.num
				this.sku.list[0].price = this.good.discount
			},
			onSelect() {
				Toast.success('分享成功！');
				this.showShare = false;
			},
		}
	}
</script>

<style>
</style>
