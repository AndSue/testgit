<!--花束、挂画、饰品页面（已完成）-->
<template>
	<div class="flower">
		<!--导航栏-->
		<van-nav-bar fixed placeholder>
			<template #left>
				<router-link to="/">
					<van-icon name="arrow-left" color="plum" size="18"></van-icon>
				</router-link>
			</template>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">{{type}}</span>
			</template>
		</van-nav-bar>
		<!--商品-->
		<template>
			<template v-for="(good,index) in goods">
				<van-card :price="good.discount.toFixed(2)" :origin-price="good.price.toFixed(2)" :desc="good.detail"
					:thumb="require('../assets/commodity/'+good.picture)" :title="good.name" :key="index"
					v-if="good.price>good.discount" @click="getDetail(good.id)">
					<template #tags>
						<van-tag plain color="plum">{{good.type}}</van-tag>
						<van-tag plain color="plum">特惠</van-tag>
						<van-tag plain color="plum" v-if="false">高分</van-tag>
						<van-tag plain color="plum" v-if="false">新品</van-tag>
					</template>
				</van-card>
				<van-card :price="good.discount.toFixed(2)" :desc="good.detail" v-else
					:thumb="require('../assets/commodity/'+good.picture)" :title="good.name" :key="index"
					@click="getDetail(good.id)">
					<template #tags>
						<van-tag plain color="plum">{{good.type}}</van-tag>
						<van-tag plain color="plum" v-if="false">高分</van-tag>
						<van-tag plain color="plum" v-if="false">新品</van-tag>
					</template>
				</van-card>
			</template>
		</template>
	</div>
</template>

<script>
	import axios from 'axios'
	export default{
		name:'searchPage',
		data(){
			return{
				goods:[],
				type:''
			}
		},
		mounted:function(){
			this.type = this.$route.query.type
			this.findByType(this.type)
			if(this.type=='花束'){
				this.type='精美花束'
			}
			else if(this.type=='装饰画'){
				this.type='装饰挂画'
			}
			else{
				this.type='热销饰品'
			}
		},
		methods:{
			findByType:function(type){
				axios.get('http://localhost:5210/andsue/findGoodsByType',{
					params:{
						type:type
					}
				}).then((res) => {
					this.goods.splice(0, this.goods.length)
					this.goods = this.goods.concat(res.data)
				}).catch(res => alert(res));
			},
			getDetail:function(id){
				this.$router.push({
					path:'/good-detail',
					query:{
						fid:id
					}
				})
			}
		}
	}
</script>

<style>
</style>