import Vue from 'vue'
import App from './App.vue'
import router from './router'
import { Card, Icon, Lazyload, NavBar, Image, Search, Swipe, SwipeItem, Tabbar, TabbarItem, Grid, GridItem, Tag, List, Cell, SubmitBar } from 'vant'
import { Col, Row, CountDown, Empty, GoodsAction, GoodsActionButton, GoodsActionIcon, Sku, Rate, Button, Field, Form, Toast } from 'vant'
import { ShareSheet, Tabs, Tab, Radio, RadioGroup, Uploader, AddressList,Area,Popup,Switch,Stepper,CouponCell,CouponList,Divider } from 'vant'

import 'vant/lib/index.less'

Vue.config.productionTip = false
Vue.use(NavBar)
Vue.use(Tabbar)
Vue.use(TabbarItem)
Vue.use(Card)
Vue.use(Search)
Vue.use(Icon)
Vue.use(Swipe)
Vue.use(SwipeItem)
Vue.use(Lazyload)
Vue.use(Image)
Vue.use(Grid)
Vue.use(GridItem)
Vue.use(Tag)
Vue.use(List)
Vue.use(Cell)
Vue.use(Col)
Vue.use(Row)
Vue.use(CountDown)
Vue.use(Empty)
Vue.use(GoodsAction)
Vue.use(GoodsActionButton)
Vue.use(GoodsActionIcon)
Vue.use(Sku)
Vue.use(Rate)
Vue.use(Button)
Vue.use(Form)
Vue.use(Field)
Vue.use(Toast)
Vue.use(ShareSheet)
Vue.use(Tabs)
Vue.use(Tab)
Vue.use(Radio)
Vue.use(RadioGroup)
Vue.use(Uploader)
Vue.use(AddressList)
Vue.use(Area)
Vue.use(Popup)
Vue.use(Switch)
Vue.use(Stepper)
Vue.use(CouponCell)
Vue.use(CouponList)
Vue.use(SubmitBar)
Vue.use(Divider)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
