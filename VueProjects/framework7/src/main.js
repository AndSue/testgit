import Vue from 'vue'
// Import F7 Bundle
import Framework7 from 'framework7/framework7-lite.esm.bundle.js'
import Framework7Vue from 'framework7-vue/framework7-vue.esm.bundle.js';
import 'framework7/css/framework7.bundle.css';

import App from './App.vue'

Framework7.use(Framework7Vue);

new Vue({
	el:'#app',
	render: h => h(App),
	components: {
		app: App
	}
});
