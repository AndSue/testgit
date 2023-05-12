<!--*@author:sue
	*地址管理页
	*已完成
	*希望可批量管理-->
<template>
	<div class="position">
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
		<van-address-list v-model="chosenId" :list="positions" default-tag-text="默认"
			@edit="onEdit" add-button-text="null">
			<template #default>
				<div>
					<van-button block round color="plum" text="新增送货地址" @click="onAdd()"
						style="position: fixed;bottom: 5px;width: 95%;"></van-button>
				</div>
			</template>
		</van-address-list>
	</div>
</template>

<script>
	import axios from 'axios'
	export default {
		name: 'positionPage',
		data() {
			return {
				chosenId:'',
				user: {
					id: 0,
					name: '未登录',
					tel: '',
					job: '',
					sex: '',
					password: '',
					photo: ''
				},
				positions: [],
        isOrder:false
			}
		},
		mounted() {
			this.getUser()
			this.findPositionsByUid()
      if(sessionStorage.getItem("order")!=null){
        this.isOrder=true
      }
		},
		methods: {
			getUser() {
				this.user = JSON.parse(localStorage.getItem("user"))
			},
			findPositionsByUid() {
				axios.get('http://localhost:5210/andsue/findPositionsByUid', {
					params: {
						uid: this.user.id
					}
				}).then((res) => {
					this.positions.splice(0, this.positions.length)
					this.positions = this.positions.concat(res.data)
					if(res.data.length>0){
						var index=this.positions.findIndex(pos=>pos.default===true)
						if(index!=-1){
							this.chosenId=this.positions[index].id
							this.positions[index].isDefault=true
							var defaultPos=this.positions[index]
							this.positions.splice(index,1)
							this.positions.splice(0,0,defaultPos)
						}
					}
				}).catch(res => alert(res));
			},
			onAdd() {
				this.$router.push({
					path:'/position-update'
				})
			},
			onEdit(item, index) {
				this.$router.push({
					path:'/position-update',
					query:{
						pos:this.positions[index]
					}
				})
			},
			goBack() {
				if(this.isOrder==true){
					sessionStorage.setItem("pid",JSON.stringify(this.chosenId))
				}
				this.$router.back()
			},
		}
	}
</script>

<style>
</style>
