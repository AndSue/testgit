<template>
	<div class="login">
		<!--导航栏-->
		<van-nav-bar fixed placeholder>
			<template #left>
				<router-link to="/">
					<van-icon name="arrow-left" color="plum" size="18"></van-icon>
				</router-link>
			</template>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">用户登录</span>
			</template>
			<template #right>
				<router-link to="/logon">
					<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">注册</span>
				</router-link>
			</template>
		</van-nav-bar>
		<van-image :src="require('../assets/user/default.jpg')" style="margin-left: 40%;margin-top:30px;" height="80" width="80" round></van-image>
		<!-- 登录表单 -->
		<van-form @submit="login()">
			<van-field :border='false' v-model="name" type="text" label="用户名" placeholder="请填写用户名" />
			<van-field :border='false' v-model="password" :type="passwordType" label="密码"  name="password" placeholder="请填写登录密码(6~16位)">
				<template #right-icon>
					<van-icon name="eye" @click="showPassword()" v-if="passwordType=='password'"></van-icon>
					<van-icon name="closed-eye" @click="showPassword()" v-else></van-icon>
				</template>
			</van-field>
			<van-cell :border='false'>
				<template #title>
					<span style="color: plum;">{{loginTip}}</span>
				</template>
			</van-cell>
			<div style="margin: 16px;">
				<van-button color="plum" text="登录" block round></van-button>
			</div>
		</van-form>
	</div>
</template>

<script>
	import axios from 'axios'
	import {
		Toast
	}from 'vant'
	export default{
		name:'loginPage',
		data(){
			return{
				passwordType:'password',
				name:'',
				password:'',
				loginTip:'*未注册用户请先注册'
			}
		},
		methods:{
			login:function(){
				axios.get('http://localhost:5210/andsue/userLogin', {
					params: {
						name: this.name,
						password: this.password
					}
				}).then((res) => {
					if (res.data !== null) {
						Toast.success('登录成功！')
						localStorage.setItem("user",JSON.stringify(res.data))
						window.location.href='/'
					} 
					else {
						Toast.fail('用户名或密码错误')
					}
				}).catch(res => alert(res));
			},
			showPassword:function(){
				this.passwordType=this.passwordType=='password'?'text':'password'
			}
		}
	}
</script>

<style>
</style>