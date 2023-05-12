<!--设置页（不能用）-->
<template>
	<div class="setting">
		<!--导航栏-->
		<van-nav-bar fixed placeholder>
			<template #left>
				<van-icon name="arrow-left" color="plum" size="18" @click="goBack()"></van-icon>
			</template>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">设置</span>
			</template>
		</van-nav-bar>
		<!--信息编辑-->
		<van-list>
			<van-cell :border='false' is-link center>
				<van-row>
					<van-col span="6">
						<van-image :src="user.photo" height="80" width="80" round></van-image>
					</van-col>
					<van-col span="18">
						<van-cell :title="user.name" size="large" :label="'用户编号：'+user.id" />
					</van-col>
				</van-row>
			</van-cell>
			<van-cell is-link title="我的收货地址" to="/position"></van-cell>
			<van-cell is-link title="账号与安全"></van-cell>
			<van-cell is-link title="支付"></van-cell>
			<van-cell is-link title="推荐设置"></van-cell>
			<van-cell is-link title="消息设置"></van-cell>
			<van-cell is-link title="隐私"></van-cell>
			<van-cell is-link title="帮助与反馈"></van-cell>
			<van-cell is-link title="关于我们"></van-cell>
		</van-list>
		<div style="margin: 16px;">
			<van-button color="plum" block round text="退出登录" to="/" @click="logout()"></van-button>
		</div>
	</div>
</template>

<script>
	export default{
		name:'settingPage',
		data(){
			return{
				user: {
					id:0,
					name:'未登录',
					tel:'',
					job:'',
					sex:'',
					password:'',
					photo:'default.jpg'
				}
			}
		},
		mounted:function(){
			this.getUser()
		},
		methods:{
			goBack: function() {
				this.$router.back()
			},
			logout:function(){
				localStorage.setItem("user",null)
			},
			getUser: function() {
				this.user=JSON.parse(localStorage.getItem("user"))
				this.user.photo=require("../assets/user/"+this.user.photo)
			}
		}
	}
</script>

<style>
</style>