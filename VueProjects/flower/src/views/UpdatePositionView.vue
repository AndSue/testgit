<template>
	<div class="update-position">
		<!--导航栏-->
		<van-nav-bar fixed placeholder>
			<!--返回-->
			<template #left>
				<van-icon name="arrow-left" color="plum" size="18" @click="goBack()"></van-icon>
			</template>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">我的地址</span>
			</template>
		</van-nav-bar>
		<!--地址-->
		<van-form>
			<van-field v-model="position.name" label="收件人" name="name" placeholder="请填写收件人"></van-field>
			<van-field v-model="position.tel" label="手机号码" name="tel" placeholder="请填写手机号码" maxlength="11"></van-field>
			<van-field label="地区" is-link clickable @click="showArea=true"
				placeholder="请选择地区" v-model="area"></van-field>
			<van-popup v-model="showArea" position="bottom">
				<van-area title="选择地区" :area-list="areaList" :columns-placeholder="['请选择', '请选择', '请选择']" @confirm="setArea" />
			</van-popup>
			<van-field v-model="detail" label="详细地址" name="tel" placeholder="请填写详细地址(精确到门牌号)"></van-field>
			<van-field v-model="code" label="邮政编码" name="code" placeholder="请填写邮政编码" disabled></van-field>
			<van-cell center title="设为默认收货地址">
				<template #right-icon>
					<van-switch v-model="isDefault" size="24" @click="judgeDefault" />
				</template>
			</van-cell>
			<div style="margin: 16px;">
				<van-button block round text="确认" color="plum" @click="onClick"></van-button>
			</div>
			<div style="margin: 16px;">
				<van-button block round text="删除" color="#b192df" @click="deletePos" v-if="isUpdate"></van-button>
			</div>
		</van-form>
	</div>
</template>

<script>
	import {
		areaList
	} from '@vant/area-data';
	import axios from 'axios'
	import {
		Toast
	} from 'vant'
	export default {
		data() {
			return {
				areaList,
				searchResult: [],
				position:{
					id:0,
					name:'',
					tel:'',
					address:'',
					default:false,
					shown:true,
					uid:0
				},
				positions:[],
				showArea:false,
				area:'',
				detail:'',
				isDefault:false,
				isUpdate:false,
				code:'',
				user:{}
			};
		},
		mounted(){
			if(this.$route.query!==undefined&&this.$route.query.pos!=undefined&&this.$route.query.pos!==null){
				this.position = this.$route.query.pos
				this.isUpdate = true
				var array=this.position.address.split(" ")
				this.area=array[0]+" "+array[1]+" "+array[2]
				this.detail=array[3]
				this.isDefault=this.position.default
			}
			else{
				this.getUser()
				this.position.uid=this.user.id
			}
		},
		methods: {
			goBack() {
				this.$router.back()
			},
			onClick(){
				if(this.isUpdate===true){
					this.updatePos()
				}
				else{
					this.addPos()
				}
			},
			getUser() {
				this.user = JSON.parse(localStorage.getItem("user"))
			},
			updatePos(){
				this.position.address=this.area+" "+this.detail
				if(this.position.default==true&&this.isDefault==false){
					Toast("您必须选择一个地址作为默认地址！")
				}
				else{
					axios.get('http://localhost:5210/andsue/updatePositionById',{
						params:{
							id:this.position.id,
							name:this.position.name,
							tel:this.position.tel,
							address:this.position.address,
							default:this.isDefault,
							uid:this.position.uid
						}
					}).then((res) => {
						if(res.data==true){
							Toast.success("更新成功！")
							this.goBack()
						}
						else{
							Toast.fail("更新失败！")
						}
					}).catch(res => alert(res));
				}
			},
			addPos(){
				this.position.address=this.area+" "+this.detail
				axios.get('http://localhost:5210/andsue/addNewPosition',{
					params:{
						name:this.position.name,
						tel:this.position.tel,
						address:this.position.address,
						isDefault:this.isDefault,
						uid:this.position.uid
					}
				}).then((res) => {
					if(res.data==true){
						Toast.success("添加成功！")
						this.resetData()
					}
					else{
						Toast.fail("添加失败，请重新添加！")
					}
				}).catch(res => alert(res));
			},
			deletePos(){
				axios.get('http://localhost:5210/andsue/deletePositionById',{
					params:{
						id:this.position.id,
						uid:this.position.uid,
						default:this.position.default
					}
				}).then((res) => {
					if(res.data==true){
						Toast.success("删除成功！")
						this.goBack()
					}
					else{
						Toast.fail("删除失败！")
					}
				}).catch(res => alert(res));
			},
			setArea(data){
				this.area=data[0].name+" "+data[1].name+" "+data[2].name
				this.showArea=false
			},
			resetData(){
				this.position.id=0
				this.position.address=''
				this.area=''
				this.detail=''
				this.position.name=''
				this.isDefault=false
			},
			judgeDefault(){
				if(this.position.default==true&&this.isDefault==true){
					Toast("您必须选择一个地址作为默认地址！")
				}
			}
		},
	};
</script>

<style>
</style>
