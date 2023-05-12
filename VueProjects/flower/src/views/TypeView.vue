<!--*@author:sue
	*分类页
	*基本完成
	*希望有的功能：同一标签能够多项选择
	-->
<template>
	<div class="type">
		<!--导航栏-->
		<van-nav-bar fixed placeholder>
			<!--返回-->
			<template #left>
				<van-icon name="arrow-left" color="plum" size="18" @click="goBack()"></van-icon>
			</template>
			<template #title>
				<span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">分类</span>
			</template>
		</van-nav-bar>
		<!--分类栏-->
		<van-tabs v-model="tags[0]" color="#b192df" title-active-color="#b192df" @click="findByTags()">
			<van-tab title="全部" name="全部"></van-tab>
			<van-tab title="花束" name="花束"></van-tab>
			<van-tab title="挂画" name="装饰画"></van-tab>
			<van-tab title="饰品" name="饰品"></van-tab>
			<van-tab title="挂件" name="挂件"></van-tab>
			<van-tab title="摆件" name="摆件"></van-tab>
			<van-tab title="花环" name="花环"></van-tab>
		</van-tabs>
		<van-tabs v-model="tags[1]" color="#b192df" title-active-color="#b192df" @click="findByTags()">
			<van-tab title="全部" name="全部"></van-tab>
			<van-tab title="粉色" name="粉"></van-tab>
			<van-tab title="黄色" name="黄"></van-tab>
			<van-tab title="蓝色" name="蓝"></van-tab>
			<van-tab title="白色" name="白"></van-tab>
			<van-tab title="紫色" name="紫"></van-tab>
			<van-tab title="红色" name="红"></van-tab>
			<van-tab title="黑色" name="黑"></van-tab>
			<van-tab title="绿色" name="绿"></van-tab>
			<van-tab title="褐色" name="褐"></van-tab>
			<van-tab title="透明" name="透明"></van-tab>
		</van-tabs>
		<van-tabs v-model="tags[2]" color="#b192df" title-active-color="#b192df" @click="findByTags()">
			<van-tab title="全部" name="全部"></van-tab>
			<van-tab title="玫瑰" name="玫瑰"></van-tab>
			<van-tab title="菊花" name="菊"></van-tab>
			<van-tab title="夜来香" name="夜来香"></van-tab>
			<van-tab title="绣球花" name="绣球"></van-tab>
			<van-tab title="桃花" name="桃花"></van-tab>
			<van-tab title="荷花" name="荷"></van-tab>
			<van-tab title="花叶" name="叶"></van-tab>
		</van-tabs>
		<van-tabs v-model="tags[3]" color="#b192df" title-active-color="#b192df" @click="findByTags()">
			<van-tab title="全部" name="全部"></van-tab>
			<van-tab title="现代" name="现代"></van-tab>
			<van-tab title="创意" name="创意"></van-tab>
			<van-tab title="古风" name="古风"></van-tab>
			<van-tab title="插花" name="插花"></van-tab>
      <van-tab title="可爱" name="可爱"></van-tab>
			<van-tab title="童趣" name="童趣"></van-tab>
      <van-tab title="清新" name="清新"></van-tab>
		</van-tabs>
		<!--商品展示-->
		<template v-if="isFind">
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
		<!--无搜索结果默认内容-->
		<van-empty image="search" description="无搜索结果，请搜索心仪的商品吧~" v-else />
	</div>
</template>

<script>
	import axios from 'axios'
	import qs from 'qs'
	export default {
		name: 'typePage',
		data() {
			return {
				tags: [
					'', '', '', ''
				],
				isFind: true,
				goods: []
			}
		},
		mounted: function() {
			for (var i = 0; i < 4; i += 1) {
				this.tags[i] = '全部'
			}
			this.findAllGoods()
		},
		methods: {
			goBack: function() {
				this.$router.back()
			},
			findAllGoods: function() {
				axios.get('http://localhost:5210/andsue/findAllGoods').then((res) => {
					this.goods.splice(0, this.goods.length)
					this.goods = this.goods.concat(res.data)
				}).catch(res => alert(res));
			},
			findByTags: function() {
				var values = this.tags
				for (var index = 0; index < 4; index += 1) {
					if (values[index] === '全部') {
						values[index] = ''
					}
				}
				axios.get('http://localhost:5210/andsue/findGoodsByTags', {
					params: {
						tags: values
					},
					paramsSerializer: params => {
						return qs.stringify(params, {
							indices: false
						})
					}
				}).then((res) => {
					if (res.data.length > 0) {
						this.goods.splice(0, this.goods.length)
						this.goods = this.goods.concat(res.data)
						sessionStorage.setItem("condition", JSON.stringify(this.searchCondition))
						this.isFind = true
					} else {
						this.isFind = false
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
			}
		}
	}
</script>

<style>
</style>
