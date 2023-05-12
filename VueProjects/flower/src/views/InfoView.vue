<template>
	<div class="info">
		<van-nav-bar fixed placeholder>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">与客服的消息</span>
			</template>
		</van-nav-bar>
		
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
</script>

<style>
</style>