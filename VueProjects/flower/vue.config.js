const {
	defineConfig
} = require('@vue/cli-service')
module.exports = defineConfig({
	transpileDependencies: true,
	css: {
		loaderOptions: {
			less: {
				// 若 less-loader 版本小于 6.0，请移除 lessOptions 这一级，直接配置选项。
				lessOptions: {
					modifyVars: {
						// 直接覆盖变量
						'address-list-item-radio-icon-color': 'plum',
						'address-list-add-button-z-index': '-999',
						'sku-item-background-color':'plum'
					},
				},
			},
		},
	},
})
