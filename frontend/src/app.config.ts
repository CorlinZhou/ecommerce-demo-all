export default defineAppConfig({
  pages: [
    'pages/index/index'
  ],
  window: {
    backgroundTextStyle: 'light',
    navigationBarBackgroundColor: '#fff',
    navigationBarTitleText: 'Products',
    navigationBarTextStyle: 'black'
  },
  // WeChat Mini Program component on-demand injection configuration
  usingComponents: {},
  // Enable new component framework
  componentFramework: "glass-easel",
  // Enable component on-demand loading
  lazyCodeLoading: "requiredComponents"
})
