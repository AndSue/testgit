<!--*@author:sue
    *收藏页面
    *希望能够批量处理
    -->
<template>
  <div class="app-container">
    <!--导航栏-->
    <van-nav-bar fixed placeholder>
      <template #title>
        <span style="color: plum;font-family: 方正正黑简体;font-weight: bold;">我的收藏</span>
      </template>
      <template #left>
        <router-link to="/mine">
          <van-icon name="arrow-left" color="plum" size="18"></van-icon>
        </router-link>
      </template>
    </van-nav-bar>
    <!--收藏商品-->
    <template v-if="isFind">
      <template v-for="(good,index) in loveGoods">
        <van-card :key="index" :price="parseFloat(good.discount).toFixed(2)" :origin-price="parseFloat(good.price).toFixed(2)" :desc="good.detail"
                  :thumb="require('../assets/commodity/'+good.picture)" :title="good.name" v-if="good.discount<good.price"
                  @click="getDetail(good.id)">
          <template #tags>
            <van-tag plain color="plum">{{good.type}}</van-tag>
            <van-tag plain color="plum">特惠</van-tag>
          </template>
        </van-card>
        <van-card :price="parseFloat(good.discount).toFixed(2)" :desc="good.detail"
                  :thumb="require('../assets/commodity/'+good.picture)" :title="good.name" :key="index" v-else
                  @click="getDetail(good.id)">
          <template #tags>
            <van-tag plain color="plum">{{good.type}}</van-tag>
            <van-tag plain color="plum" v-if="false">高分</van-tag>
            <van-tag plain color="plum" v-if="false">新品</van-tag>
          </template>
        </van-card>
      </template>
      <van-divider
          :style="{ color: 'plum', borderColor: 'plum', padding: '0 16px' }">
        到底啦~去看看更多商品吧！
      </van-divider>
    </template>
    <van-empty description="你还没有收藏喜爱的商品哦~" v-else />
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "LoveView",
  data(){
    return{
      goods:[],
      isFind:true,
      love:[],
      loveGoods:[]
    }
  },
  created(){
    this.getUser()
    this.findAllGoods()
  },
  methods:{
    getLove(){
      this.love=JSON.parse(localStorage.getItem("love"))
      if(this.love&&this.love[this.user.id]){
        this.loveGoods=this.love[this.user.id]
        for(var index=0;index<this.loveGoods.length;index+=1){
          var i=this.goods.findIndex(item=>item.id==this.loveGoods[index])
          this.loveGoods[index]=this.goods[i]
        }
      }
      else{
        this.isFind=false
      }
    },
    findAllGoods() {
      axios.get('http://localhost:5210/andsue/findAllGoods').then((res) => {
        this.goods.splice(0, this.goods.length)
        this.goods = this.goods.concat(res.data)
        this.getLove()
      }).catch(res => alert(res));
    },
    getUser() {
      this.user = JSON.parse(localStorage.getItem("user"))
    },
    getDetail: function(id) {
      this.$router.push({
        path: '/good-detail',
        query: {
          fid: id
        }
      })
    },
  }
}
</script>

<style scoped>

</style>