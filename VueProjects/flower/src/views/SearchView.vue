<!--*@author:sue
	*
	*搜索界面
	*目前只能模糊查找已有字段
	*希望能智能设置标签
	-->
<template>
	<div class="search">
		<van-nav-bar fixed placeholder>
			<template #left>
				<van-icon name="arrow-left" color="plum" size="18" @click="goBack()"></van-icon>
			</template>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">未闻花名</span>
			</template>
		</van-nav-bar>
		<van-search shape="round" v-model="searchCondition" placeholder="花束" @search="findByLike()"
			background="plum" />
		<!--无搜索结果默认内容-->
		<van-empty image="search" description="无搜索结果，请搜索心仪的商品吧~" v-if="!isFind" />
		<!--展示搜索结果-->
		<template v-else>
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
				<van-card :price="good.discount.toFixed(2)" :desc="good.detail"
					:thumb="require('../assets/commodity/'+good.picture)" :title="good.name" :key="index" v-else
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
	export default {
		name: 'searchPage',
		data() {
			return {
				searchCondition: '',
				isFind: false,
				goods: []
			}
		},
		mounted: function() {
			this.searchCondition = JSON.parse(sessionStorage.getItem("condition"))
			if (this.searchCondition !== null&&this.searchCondition !== "") {
				this.findByLike()
			} 
		},
		methods: {
			findAllGoods: function() {
				axios.get('http://localhost:5210/andsue/findAllGoods').then((res) => {
					this.goods.splice(0, this.goods.length)
					this.goods = this.goods.concat(res.data)
				}).catch(res => alert(res));
			},
			findByLike: function() {
				if(this.searchCondition === null||this.searchCondition === ''){
					this.searchCondition='花束'
				}
				axios.get('http://localhost:5210/andsue/findGoodsByLike', {
					params: {
						condition: this.searchCondition
					}
				}).then((res) => {
					if (res.data.length > 0) {
						this.goods.splice(0, this.goods.length)
						this.goods = this.goods.concat(res.data)
						sessionStorage.setItem("condition", JSON.stringify(this.searchCondition))
						this.isFind = true
					} else {
						this.isFind = false
						this.findAllGoods()
					}
				}).catch(res => {
					this.isFind = false
					alert(res)
				});
			},
			getDetail: function(id) {
				this.$router.push({
					path: '/good-detail',
					query: {
						fid: id
					}
				})
			},
			goBack:function(){
				sessionStorage.setItem('condition',null)
				this.$router.back()
			}
		}
	}
</script>

<style>
</style>
