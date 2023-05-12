<!--特惠页面（需要能够从数据库中提取时间，算出还有多久结束）-->
<template>
	<div class="discount">
		<van-nav-bar fixed placeholder>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">特惠专场</span>
			</template>
			<template #left>
				<router-link to="/">
					<van-icon name="arrow-left" color="plum" size="18"></van-icon>
				</router-link>
			</template>
		</van-nav-bar>
		<van-cell>
			<van-count-down :time="time">
				<template #default="timeData">
					<span class="colon">距离本次特惠结束还剩</span>
					<span class="block">{{ timeData.hours }}</span>
					<span class="colon">:</span>
					<span class="block">{{ timeData.minutes }}</span>
					<span class="colon">:</span>
					<span class="block">{{ timeData.seconds }}</span>
				</template>
			</van-count-down>
		</van-cell>
		<template v-for="(good,index) in goods">
			<van-card :key="index" :price="good.discount.toFixed(2)" :origin-price="good.price.toFixed(2)"
				:desc="good.detail" :thumb="require('../assets/commodity/'+good.picture)" :title="good.name"
				v-if="good.discount<good.price" @click="getDetail(good.id)">
				<template #tags>
					<van-tag plain color="plum">{{good.type}}</van-tag>
					<van-tag plain color="plum">特惠</van-tag>
				</template>
			</van-card>
		</template>
	</div>
</template>

<script>
	import axios from 'axios'
	export default {
		name: 'homePage',
		data() {
			return {
				images: [
					require('../assets/banner/nav1.jpg'),
					require('../assets/banner/nav2.jpg'),
					require('../assets/banner/nav3.jpg')
				],
				goods: [],
				time: 300 * 60 * 60 * 1000
			}
		},
		mounted: function() {
			this.findAllGoods()
		},
		methods: {
			findAllGoods: function() {
				axios.get('http://localhost:5210/andsue/findAllGoods').then((res) => {
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
	.colon {
		display: inline-block;
		margin: 0 4px;
		color: plum;
		font-weight: bold;
	}

	.block {
		display: inline-block;
		width: 22px;
		color: #fff;
		font-size: 12px;
		text-align: center;
		background-color: plum;
	}
</style>
