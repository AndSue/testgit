<!--*@author:sue
	*注册页面
	*不能验证手机号、验证密码是否符合规则
	-->
<template>
	<div class="logon">
		<!--导航-->
		<van-nav-bar fixed placeholder>
			<template #left>
				<van-icon name="arrow-left" color="plum" size="18" @click="goBack()"></van-icon>
			</template>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">用户注册</span>
			</template>
			<template #right>
				<router-link to="/login">
					<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">登录</span>
				</router-link>
			</template>
		</van-nav-bar>
		<!--表单-->
		<van-form>
			<van-field v-model="name" label="用户名" name="name" placeholder="请填写用户名"></van-field>
			<van-field v-model="password" label="密码" name="password" placeholder="请填写登录密码(6~16位)" 
				:type="passwordType" maxlength="16">
				<template #right-icon>
					<van-icon name="eye" @click="showPassword()" v-if="passwordType=='password'"></van-icon>
					<van-icon name="closed-eye" @click="showPassword()" v-else></van-icon>
				</template>
			</van-field>
			<van-field v-model="repassword" label="确认密码" name="password" placeholder="请再次输入密码"
				:type="passwordType" maxlength="16">
				<template #right-icon>
					<van-icon name="eye" @click="showPassword()" v-if="passwordType=='password'"></van-icon>
					<van-icon name="closed-eye" @click="showPassword()" v-else></van-icon>
				</template>
			</van-field>
			<van-field name="sex" label="性别">
				<template #input>
					<van-radio-group v-model="sex" direction="horizontal" checked-color="plum">
						<van-radio name="男">男</van-radio>
						<van-radio name="女">女</van-radio>
					</van-radio-group>
				</template>
			</van-field>
			<van-field v-model="tel" label="手机号码" name="tel" placeholder="请填写手机号码"></van-field>
			<van-field v-model="code" label="短信验证码" name="tel">
				<template #button>
					<van-button text="发送验证码" color="plum" size="small" @click="getCode()"></van-button>
				</template>
			</van-field>
			<van-field v-model="job" label="职业" name="job" placeholder="请填写用户职业"></van-field>
			<div style="margin: 16px;">
				<van-button block round text="注册" color="plum" @click="addNewUser()"></van-button>
			</div>
		</van-form>
	</div>
</template>

<script>
	import axios from 'axios'
	import {
		Toast
	} from 'vant'
	export default{
		name:'logonPage',
		data(){
			return{
				id: 0,
				name: '',
				password: '',
				repassword:'',
				sex: '男',
				tel: '',
				job: '',
				code:'',
				realCode:'',
				users: [],
				passwordType:'password'
			}
		},
		methods:{
			goBack: function() {
				this.$router.back()
			},
			getCode:function(){
				for(var i=0;i<6;i++){
					this.realCode=""+this.realCode+Math.floor(Math.random()*10)
				}
				console.log(this.realCode)
			},
			addNewUser: function() {
				if (this.name == '' || this.job == '' || this.tel == '' || this.password == '') {
					Toast.fail('您有字段未填写！')
				} 
				else if(this.password !== this.repassword){
					Toast.fail('确认密码与密码需保持一致！')
				}
				else if(this.realCode !== this.code){
					Toast.fail('验证码错误！')
				}
				else {
					axios.get('http://localhost:5210/andsue/addNewUser', {
						params: {
							id: this.id,
							name: this.name,
							password: this.password,
							sex: this.sex,
							tel: this.tel,
							job: this.job
						}
					}).then((res) => {
						if (res.data == true) {
							Toast.success('注册成功！')
							this.resetAdd()
						} else {
							Toast.fail('注册失败，请重新注册！')
						}
					}).catch(res => alert(res));
				}
			},
			resetAdd: function() {
				this.name = ''
				this.sex = '男'
				this.password = ''
				this.tel = ''
				this.job = ''
				this.code= ''
				this.repassword= ''
			},
			showPassword:function(){
				this.passwordType=this.passwordType=='password'?'text':'password'
			}
		}
	}
</script>
