<template>
	<!-- Main Framework7 App component where we pass Framework7 params -->
	<div id="app">
		<f7-app :params="f7params">
			<!-- initial page is specified in routes.js -->
			<f7-view main url="/" tab tab-active id="home"></f7-view>
			<f7-view url="/search" tab id="search"></f7-view>
		</f7-app>
	</div>
</template>
<script>
	import routes from './router/index.js';

	export default {
		data() {
			return {
				// Framework7 parameters that we pass to <f7-app> component
				f7params: {
					// Array with app routes
					routes:routes,
					// App Name
					name: '用户管理',
					// App id
					id: 'com.myapp.test',
					view:{
						pushState:true
					}
				}
			}
		}
	}
</script>
