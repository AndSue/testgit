<!--*@author:sue
	*订单详情页面
	*
	-->
<template>
	<div class="order-detail">
		<!--导航栏-->
		<van-nav-bar fixed placeholder>
			<!--返回-->
			<template #left>
				<van-icon name="arrow-left" color="plum" size="18" @click="goBack()"></van-icon>
			</template>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">订单详情</span>
			</template>
			<!--分享-->
			<template #right>
				<van-icon name="share-o" color="plum" size="18" @click="showShare=true"></van-icon>
			</template>
		</van-nav-bar>
		<!--订单详细信息展示-->
		<template v-if="order&&good&&position">
			<van-card :price="parseFloat(order.price).toFixed(2)" :desc="good.detail"
				:thumb="good.picture"
				:title="good.name" :num="order.num" :border="false">
			</van-card>
			<van-cell title="商品总价" :border="false">
				<template #default>
					<span>商品总价  </span>
					<span class="price">￥{{parseFloat(order.price*order.num).toFixed(2)}}</span>
				</template>
			</van-cell>
			<van-cell title="运费" :border="false">
				<template #default>
					<span>运费（快递）  </span>
					<span class="price">￥{{transport.toFixed(2)}}</span>
				</template>
			</van-cell>
			<van-cell title="优惠券抵扣" :border="false">
				<template #default>
					<span>优惠券抵扣  </span>
					<span class="price red">-￥{{minus.toFixed(2)}}</span>
				</template>
			</van-cell>
			<van-cell title="实付金额">
				<template #default>
					<span class="price red" style="font-size: 18px;">￥{{parseFloat(order.price*order.num+transport-minus).toFixed(2)}}</span>
				</template>
			</van-cell>
			<van-cell title="收货信息" :value="position.name+'，'+position.tel+'，'+position.address" :border="false"></van-cell>
			<van-cell title="订单编号" :value="order.id" :border="false"></van-cell>
			<van-cell title="备注" :value="order.detail" :border="false"></van-cell>
			<van-cell title="下单时间" :value="order.time" :border="false"></van-cell>
			<van-cell title="是否发货" :value="flag=='已发货'||flag=='待评价'?'是':'否'" :border="false"></van-cell>
			<van-cell title="是否到货" :value="flag=='待评价'?'是':'否'" :border="false"></van-cell>
		</template>
		<!--弹出分享页面-->
		<van-share-sheet v-model="showShare" title="立即分享给好友" :options="options" @select="onSelect" />
		<div style="height: 3.5rem;"></div>
		<!--工具栏-->
		<div class="tool" v-if="!(flag=='已退货')">
			<van-button round text="加入购物车" color="darkgray" plain />
      <template v-if="order.shown==undefined">
        <van-button round text="删除订单" color="darkgray"  plain />
        <van-button color="plum"
                    @click="showPay=true"
                    round plain>继续支付</van-button>
      </template>
      <template v-else-if="order.shown==true">
        <van-button round text="查看物流" color="darkgray" v-if="order.send==true" plain />
        <van-button round text="催催发货" color="darkgray" v-if="order.send==false" plain />
        <van-button color="plum" v-if="order.send==false"
                    round plain @click="deleteById()">商品退货</van-button>
        <van-button color="plum" @click="setReceive()"
                    v-if="order.send==true&&order.receive==false" round plain>确认到货</van-button>
        <van-button color="plum" v-if="order.receive==true" round plain>评价商品</van-button>
      </template>
		</div>
	</div>
</template>

<script>
	import axios from 'axios'
  import {
    Dialog,
    Toast
  } from 'vant'
	export default {
		name: "goodDetail",
		data() {
			return {
				order:null,
				showShare: false,
				good: null,
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
				flag:'',
				minus:0,
				transport:0,
				position:null,
        showPay:false,

			}
		},
		created() {
			this.flag=JSON.parse(sessionStorage.getItem("flag"))
			this.order=this.$route.query.order
			this.findGoodById()
			this.findPositionById()
			this.getUser()
		},
		methods: {
			goBack() {
				this.$router.back()
			},
			findPositionById(){
				axios.get('http://localhost:5210/andsue/findPositionById', {
					params: {
						id: this.order.pid
					}
				}).then((res) => {
					this.position=res.data
				}).catch(res => alert(res));
			},
			findGoodById() {
				axios.get('http://localhost:5210/andsue/findGoodById', {
					params: {
						id: this.order.fid
					}
				}).then((res) => {
					this.good = res.data
					this.good.picture = require('../assets/commodity/' + this.good.picture)
				}).catch(res => alert(res));
			},
			getUser() {
				this.user = JSON.parse(localStorage.getItem("user"))
			},
			onCartClicked(){
				
			},
			onSelect() {
				Toast.success('分享成功！');
				this.showShare = false;
			},
      deleteById(){
        Dialog.confirm({
          message:"您真的要退货吗？退货操作无法撤销！"
        }).then(()=>{
          axios.get('http://localhost:5210/andsue/deleteOrderById',{
            params:{
              id:this.order.id
            }
          }).then((res) => {
            if(res.data>0){
              Toast.success("退货成功！")
              this.goBack()
            }
          }).catch(res => alert(res));
        }).catch(() => {});
      },
      setReceive(){
        axios.get('http://localhost:5210/andsue/updateReceiveByUid',{
          params:{
            uid:this.user.id
          }
        }).then((res) => {
          if(res.data>0){
            this.order.receive=true
          }
        }).catch(res => alert(res));
      }
		}
	}
</script>

<style>
	.price{
		font-family: 方正正黑简体;
		font-size: 14px;
		font-weight: bold;
		color: black;
	}
	.red{
		color: red;
	}
	.tool{
		bottom: 0;
		position: fixed;
		padding-left: 10%;
		background-color: white;
	}
	.tool .van-button{
		margin-top:10px;
		margin-left: 15px;
		margin-bottom: 10px;
	}
</style>