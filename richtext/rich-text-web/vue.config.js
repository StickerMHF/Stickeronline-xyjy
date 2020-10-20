const path = require('path')

function resolve(dir) {
  return path.join(__dirname, dir)
}

// vue.config.js
module.exports = {
  publicPath:'/',
  // 如果你不需要生产环境的 source map，可以将其设置为 false 以加速生产环境构建。
  productionSourceMap: false,
  configureWebpack: {},
  chainWebpack: (config) => {
    config.resolve.alias
      .set('@$', resolve('src'))
      .set('@api', resolve('src/api'))
      .set('@assets', resolve('src/assets'))
      .set('@comp', resolve('src/components'))
      .set('@views', resolve('src/views'))
      .set('@layout', resolve('src/layout'))
      .set('@static', resolve('src/static'))
  },

  css: {
    loaderOptions: {
      less: {
        modifyVars: {
          /* less 变量覆盖，用于自定义 ant design 主题 */
          'primary-color': '#1890ff',
          'link-color': '#1890ff',
          'border-radius-base': '4px',
        },
        javascriptEnabled: true,
      }
    }
  },
}