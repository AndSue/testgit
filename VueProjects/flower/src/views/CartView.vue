<template>
	<div class="cart">
		<van-nav-bar fixed placeholder>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">我的购物车</span>
			</template>
		</van-nav-bar>
		<!--无物品默认内容-->
		<van-empty description="购物车空空如也哦,请添加心仪的商品吧~" v-if="!isFind" />
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
	export default{
		name:'cartPage',
		data(){
			return{
				isFind:false
			}
		}
	}
</script>

<style>
</style>