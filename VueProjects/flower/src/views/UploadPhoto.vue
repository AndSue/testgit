<template>
	<div class="upload">
		<van-nav-bar fixed placeholder>
			<template #left>
				<van-icon name="arrow-left" color="plum" size="18" @click="goBack()"></van-icon>
			</template>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">头像上传</span>
			</template>
			<template #right>
				<router-link to="/setting">
					<van-icon name="setting-o" color="plum" size="18"></van-icon>
				</router-link>
			</template>
		</van-nav-bar>
		<!--头像上传及预览-->
		<van-uploader v-model="file" max-count="1" :before-read="beforeRead" style="margin-left: 10%;margin-top: 20px;"
			preview-size="300" :preview-options="options" :preview-full-image="false">
		</van-uploader>
		<div style="margin: 16px;">
			<van-button block round text="确认上传" color="plum" @click="uploadPhoto()"></van-button>
		</div>
	</div>
</template>

<script>
	import {
		Toast
	} from 'vant'
	import axios from 'axios'
	export default {
		name: 'uploadPage',
		data() {
			return {
				file: [],
				options: {
					showIndex: false,
					closable: true
				},
				user: {
					id:0,
					name:'未登录',
					tel:'',
					job:'',
					sex:'',
					password:'',
					photo:''
				},
				isUpdate:false
			}
		},
		mounted:function(){
			this.getUser()
		},
		methods: {
			getUser: function() {
				this.user=JSON.parse(localStorage.getItem("user"))
			},
			uploadPhoto: function() {
				console.log(this.file[0])
				const fd = new FormData()
				fd.append('file', this.file[0].file)
				fd.append('uid',this.user.id)
				console.log(fd.get('file'))
				const config = {
					headers: {
						'Accept': 'multipart/form-data',
						'Content-Type': 'multipart/form-data',
					},
				};
				axios.post('http://localhost:5210/andsue/uploadingPhoto',fd,config).then(res => {
					Toast(res.data)
					if(res.data=='上传成功'){
						this.isUpdate=true
						this.file.splice(0,1)
					}
				})
			},
			beforeRead: function(file) {
				if (file.type !== 'image/jpeg') {
					Toast.fail('请上传 jpg 格式图片');
					return false;
				}
				return true;
			},
			goBack:function(){
				this.$router.push({
					path:'/mine',
					query:{
						isUpdate:this.isUpdate
					}
				})
			}
		}
	}
</script>
<style>
	.preview-cover {
		position: absolute;
		z-index: 10;
		bottom: 0;
		box-sizing: border-box;
		width: 100%;
		height: 100%;
		color: #fff;
		font-size: 12px;
		text-align: center;
		background: rgba(0, 0, 0, 0.3);
	}

	.preview-cover2 {
		position: absolute;
		z-index: 10;
		bottom: 0;
		box-sizing: border-box;
		width: 100%;
		height: 100%;
		color: #fff;
		font-size: 12px;
		text-align: center;
		background: rgba(0, 0, 0, 0.3);
	}
</style>
