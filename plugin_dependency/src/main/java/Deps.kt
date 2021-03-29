/**
 * @author:  Tom Hawk
 * 添加时间: 2020/7/31 17:07
 * 功能描述: 依赖管理
 */
object Deps {
    // kotlinVersion
    val kotlinVersion = "1.4.30"
    // composeVersion
    val composeVersion = "1.0.0-beta01"

    /*
     * repository plugin
     */
    //gradlePlugin
    val gradlePlugin = "com.android.tools.build:gradle:4.2.0-beta05"
    //mavenGradlePlugin
    val mavenGradlePlugin = "com.github.dcendents:android-maven-gradle-plugin:2.1"
    //bintrayPlugin
    val bintrayPlugin = "com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4"
    //groovyPlugin
    val groovyPlugin = "org.codehaus.groovy:groovy-all:2.4.15"

    /*
     * test
     */
    //junit
    val junit = "junit:junit:4.13"
    //runner
    val runner = "androidx.test:runner:1.3.0"
    //rules
    val rules = "androidx.test:rules:1.3.0"
    //espresso
    val espresso = "androidx.test.espresso:espresso-core:3.3.0"
    //junitExt
    val junitExt = "androidx.test.ext:junit:1.1.2"

    /*
     * Compose 工具包依赖项
     */
    //composeRuntime
    val composeRuntime = "androidx.compose.runtime:runtime:$composeVersion"
    //composeRuntimeLiveData
    val composeRuntimeLiveData = "androidx.compose.runtime:runtime-livedata:$composeVersion"
    //composeUI
    val composeUI = "androidx.compose.ui:ui:$composeVersion"
    //composeUITooling
    val composeUITooling = "androidx.compose.ui:ui-tooling:$composeVersion"
    //composeFoundation
    val composeFoundation = "androidx.compose.foundation:foundation:$composeVersion"
    //composeFoundationLayout
    val composeFoundationLayout = "androidx.compose.foundation:foundation-layout:$composeVersion"
    //composeMaterial
    val composeMaterial = "androidx.compose.material:material:$composeVersion"
    //composeMaterialIcons
    val composeMaterialIcons = "androidx.compose.material:material-icons-extended:$composeVersion"
    //composeAnimation
    val composeAnimation = "androidx.compose.animation:animation:$composeVersion"
    //composeUITest : androidTestImplementation
    val composeUITest = "androidx.compose.ui:ui-test:$composeVersion"
    //composeUITestJunit4 : androidTestImplementation
    val composeUITestJunit4 = "androidx.compose.ui:ui-test-junit4:$composeVersion"

    /*
     * support
     */
    //appcompat
    val appcompat = "androidx.appcompat:appcompat:1.3.0-beta01"
    //androidxActivityKtx
    val androidxActivityKtx = "androidx.activity:activity-ktx:1.2.0"
    //coreKtx
    val coreKtx = "androidx.core:core-ktx:1.5.0-beta01"
    //activityCompose
    val activityCompose = "androidx.activity:activity-compose:1.3.0-alpha03"
    //supportV4
    val supportV4 = "androidx.legacy:legacy-support-v4:1.0.0"
    //supportFragment
    val supportFragment = "androidx.fragment:fragment:1.2.5"
    //supportCoreUtils
    val supportCoreUtils = "androidx.legacy:legacy-support-core-com.robot.app_ai.utils:1.0.0"
    //annotations
    val annotations = "androidx.annotation:annotation:1.1.0"
    //design
    val design = "com.google.android.material:material:1.2.1"
    //cardView
    val cardView = "androidx.cardview:cardview:1.0.0"
    //recyclerView
    val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
    //transition
    val transition = "androidx.transition:transition:1.3.1"
    //constraintLayout
    val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
    //multiDex
    val multiDex = "androidx.multidex:multidex:2.0.1"

    /*
     * room
     */
    //roomRuntime
    val roomRuntime = "androidx.room:room-runtime:2.2.5"
    //roomCompiler
    val roomCompiler = "androidx.room:room-compiler:2.2.5"
    //roomRxJava2
    val roomRxJava2 = "androidx.room:room-rxjava2:2.2.5"
    //roomTesting
    val roomTesting = "androidx.room:room-testing:2.2.5"
    //roomKtx
    val roomKtx = "androidx.room:room-ktx:2.2.5"


    //sqlcipher
    val sqlcipher = "net.zetetic:android-database-sqlcipher:4.2.0"
    /*
     * lifecycle
     */
    //lifecycleExtensions : view model + live data
    val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    //viewModel
    val viewModel = "androidx.lifecycle:lifecycle-viewmodel:2.3.0-beta01"
    //liveData
    val liveData = "androidx.lifecycle:lifecycle-livedata:2.3.0-beta01"
    //lifecycleRuntime : no view model or live data
    val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:2.3.0-beta01"
    //lifecycleCommonJava8 : if using Java8, use the following instead of compiler
    val lifecycleCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:2.3.0-beta01"
    //lifecycleReactiveStreams : ReactiveStreams support for LiveData
    val lifecycleReactiveStreams = "androidx.lifecycle:lifecycle-reactivestreams:2.3.0-beta01"
    // lifecycleCompiler
    val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:2.3.0-beta01"


    //lifecycleViewModelSavedState :ViewModel新出的状态保存库
    val lifecycleViewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:2.3.0"
    //liveDataKtx :alternatively - just LiveData
    val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.0"
    //viewModelCompose
    val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha02"
    //viewModelKtx :alternatively - just ViewModel
    val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    //lifecycleKtx : lifecycleScope.launch{}
    val lifecycleKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-beta01"
    //lifecycleRunntimeKtx : alternatively - Lifecycles only (no ViewModel or LiveData)
    val lifecycleRunntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.0-beta01"
    //lifecycleReactivestreamsKtx : optional - ReactiveStreams support for LiveData
    val lifecycleReactivestreamsKtx = "androidx.lifecycle:lifecycle-reactivestreams-ktx:2.3.0-beta01"


    /*
    * navigation
    * */
    //navigationSafeArgsGradlePlugin : classpath
    val navigationSafeArgsGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:2.3.2"
    //navigationFragmentKtx
    val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:2.3.2"
    //navigationUIKtx
    val navigationUIKtx = "androidx.navigation:navigation-ui-ktx:2.3.2"
    //navigationFragment
    val navigationFragment = "androidx.navigation:navigation-fragment:2.3.2"
    //navigationUI
    val navigationUI = "androidx.navigation:navigation-ui:2.3.2"

    /*
     * work
     */
    //workRuntimeKtx
    val workRuntimeKtx = "android.arch.work:work-runtime-ktx:1.0.1"
    //workRuntime
    val workRuntime = "android.arch.work:work-runtime:1.0.1"
    //workFirebase
    val workFirebase = "android.arch.work:work-firebase:1.0.0-alpha11"

    /*
     * google
     */
    //pagingRuntime
    val pagingRuntime = "androidx.paging:paging-runtime:2.1.2"
    //pagingRuntimeKtx
    val pagingRuntimeKtx = "androidx.paging:paging-runtime-ktx:2.1.2"

    //playServicesAd
    val playServicesAd = "com.google.android.gms:play-services-ads:19.6.0"
    //googleService
    val googleService = "com.google.gms:google-services:4.3.4"
    //flexBox : https://github.com/google/flexbox-layout
    val flexBox = "com.google.android:flexbox:1.1.0"
    //gson : https://github.com/google/gson
    val gson = "com.google.code.gson:gson:2.8.6"

    //kotlinAndroidExtensions :插件
    val kotlinAndroidExtensions = "org.jetbrains.kotlin:kotlin-android-extensions:$kotlinVersion"
    //kotlinStdlib
    val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    //kotlinStdlibJdk7
    val kotlinStdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
    //kotlinStdlibJdk8
    val kotlinStdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    //kotlinGradlePlugin
    val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    //kotlinXCoroutinesAndroid
    val kotlinXCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2"
    //kotlinXCoroutinesCore
    val kotlinXCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1"
    //kotlinReflect
    val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
    // koinAndroid
    val koinAndroid = "org.koin:koin-android:2.0.1"
    // koinViewModel
    val koinViewModel = "org.koin:koin-android-viewmodel:2.0.1"
    // koinScope
    val koinScope = "org.koin:koin-android-scope:2.0.1"
    // koinExt
    val koinExt = "org.koin:koin-android-ext:2.0.1"

    /*
     * 模块化框架
     */
    //appJoint : https://github.com/PrototypeZ/AppJoint/blob/master/README_zh.md
    val appJoint = "io.github.prototypez:app-joint:1.7"
    //appJointCore
    val appJointCore = "io.github.prototypez:app-joint-core:1.7"

    /*
     * 网络
     */
    //okHttp3 : https://github.com/square/okhttp
    val okHttp3 = "com.squareup.okhttp3:okhttp:4.7.2"
    //okHttpUrlConnection
    val okHttpUrlConnection = "com.squareup.okhttp3:okhttp-urlconnection:4.0.0"
    //okHttp3LoggingInterceptor
    val okHttp3LoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.7.2"

    /*
     * retrofit2
     */
    //retrofit2 : https://github.com/square/retrofit
    val retrofit2 = "com.squareup.retrofit2:retrofit:2.9.0"
    //retrofit2ConverterGson
    val retrofit2ConverterGson = "com.squareup.retrofit2:converter-gson:2.7.2"
    //retrofit2ConverterScalars
    val retrofit2ConverterScalars = "com.squareup.retrofit2:converter-scalars:2.5.0"
    //retrofit2AdapterRxJava2
    val retrofit2AdapterRxJava2 = "com.squareup.retrofit2:adapter-rxjava2:2.7.2"

    //retrofitUrlManager : https://github.com/JessYanCoding/RetrofitUrlManager
    val retrofitUrlManager = "me.jessyan:retrofit-url-manager:1.4.0"

    /*
     * 路由
     */
    //aRouterApi : https://github.com/alibaba/ARouter
    val aRouterApi = "com.alibaba:arouter-api:1.5.0"
    //aRouterCompiler
    val aRouterCompiler = "com.alibaba:arouter-compiler:1.2.2"

    /*
     * 图片加载
     */
    //picasso : https://github.com/square/picasso
    val picasso = "com.squareup.picasso:picasso:2.71828"

    //glideV4 : https://github.com/bumptech/glide
    val glideV4 = "com.github.bumptech.glide:glide:4.11.0"
    //glideV4Compiler
    val glideV4Compiler = "com.github.bumptech.glide:compiler:4.11.0"

    /*
     * 事件总线
     */
    //eventBus : https://github.com/greenrobot/EventBus
    val eventBus = "org.greenrobot:eventbus:3.2.0"
    //liveEventBus : https://github.com/JeremyLiao/LiveEventBus
    val liveEventBus = "com.jeremyliao:live-event-bus:1.4.0"

    /*
     * bug反馈
     */
    //buglyTinkerPlugin
    val buglyTinkerPlugin = "com.tencent.bugly:tinker-support:1.2.0"
    //buglyCrashReport
    val buglyCrashReport = "com.tencent.bugly:crashreport:3.1.0"
    //buglyCrashReportUpgrade
    val buglyCrashReportUpgrade = "com.tencent.bugly:crashreport_upgrade:1.4.5"
    //buglyTinkerAndroid : 指定tinker依赖版本（注：应用升级1.3.5版本起，不再内置tinker）
    val buglyTinkerAndroid = "com.tencent.tinker:tinker-android-lib:1.9.14.3"
    //buglyNativeCrashReport : 其中latest.release指代最新版本号，也可以指定明确的版本号，例如2.2.0
    val buglyNativeCrashReport = "com.tencent.bugly:nativecrashreport:3.7.1"



    /*
     * 自定义view
     */
    //discreteScrollview : https://github.com/yarolegovich/DiscreteScrollView
    val discreteScrollview = "com.yarolegovich:discrete-scrollview:1.4.9"
    //easySwipeMenuLayout : https://github.com/anzaizai/EasySwipeMenuLayout
    val easySwipeMenuLayout = "com.github.anzaizai:EasySwipeMenuLayout:1.1.2"
    //swipeDelMenuLayout :侧滑删除 https://github.com/mcxtzhang/SwipeDelMenuLayout/blob/master/README-cn.md
    val swipeDelMenuLayout = "com.github.mcxtzhang:SwipeDelMenuLayout:V1.3.0"

    //pageIndicatorView : https://github.com/romandanylyk/PageIndicatorView
    val pageIndicatorView = "com.romandanylyk:pageindicatorview:1.0.3"
    //swipeBack
    val swipeBack = "n.simonlee.widget:swipeback:1.0.15"
    //banner
    val banner = "com.youth.banner:banner:1.4.10"
    //viewPagerTransforms
    val viewPagerTransforms = "com.ToxicBakery.viewpager.transforms:view-pager-transforms:1.2.32@aar"
    //circleImageView : https://github.com/hdodenhof/CircleImageView
    val circleImageView = "de.hdodenhof:circleimageview:3.0.0"
    //roundedImageView :圆角图片 https://github.com/vinc3m1/RoundedImageView
    val roundedImageView = "com.makeramen:roundedimageview:2.3.0"
    //lRecyclerView
    val lRecyclerView = "com.github.jdsjlzx:LRecyclerView:1.5.4.3"
    //photoView : https://github.com/chrisbanes/PhotoView
    val photoView = "com.github.chrisbanes:PhotoView:2.2.0"
    //gifDrawable : https://github.com/koral--/android-gif-drawable
    val gifDrawable = "pl.droidsonroids.gif:android-gif-drawable:1.2.15"
    //androidPickerView : 时间,地址选择器 https://github.com/Bigkoo/Android-PickerView
    val androidPickerView = "com.contrarywind:Android-PickerView:4.1.8"
    //baseRecyclerViewAdapterHelper : recycleView相关 https://github.com/CymChad/BaseRecyclerViewAdapterHelper
    val baseRecyclerViewAdapterHelper = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.1"
    //numberPickerView : https://github.com/Carbs0126/NumberPickerView
    val numberPickerView = "n.carbswang.android:NumberPickerView:1.1.1"
    //swipeBackLayout :侧滑退出 https://github.com/ikew0ng/SwipeBackLayout
    val swipeBackLayout = "me.imid.swipebacklayout.lib:library:1.1.0"
    //switchButton : https://github.com/kyleduo/SwitchButton
    val switchButton = "com.kyleduo.switchbutton:library:2.0.0"
    //NiceImageView
    val NiceImageView = "com.github.SheHuan:NiceImageView:1.0.5"
    //flowLayout : 流式布局
    val flowLayout = "com.hyman:flowlayout-lib:1.1.2"

    //sectionedRecyclerview :分组的recyclerView
    val sectionedRecyclerview = "com.truizlop.sectionedrecyclerview:library:1.2.0"

    //indicatorSeekBar :进度条 带顶部气泡 https://github.com/warkiz/IndicatorSeekBar
    val indicatorSeekBar = "com.github.warkiz.widget:indicatorseekbar:2.1.0"
    /*
     * 动画
     */
    //transitionsEverywhere : https://github.com/andkulikov/Transitions-Everywhere
    val transitionsEverywhere = "com.andkulikov:transitionseverywhere:1.8.0"

    /*
     * 权限
     */
    //andPermission : https://github.com/yanzhenjie/AndPermission
    val andPermission = "com.yanzhenjie:permission:2.0.0-rc5"

    /*
     * 实用
     */
    //butterKnife : https://github.com/JakeWharton/butterknife
    val butterKnife = "com.jakewharton:butterknife:10.2.1"
    //butterKnifeCompiler
    val butterKnifeCompiler = "com.jakewharton:butterknife-compiler:10.2.1"
    //butterKnifeGradlePlugin
    val butterKnifeGradlePlugin = "com.jakewharton:butterknife-gradle-plugin:10.2.1"

    //rxJava2 : https://github.com/ReactiveX/RxJava
    val rxJava2 = "io.reactivex.rxjava2:rxjava:2.2.9"
    //rxAndroid
    val rxAndroid = "io.reactivex:rxandroid:1.0.1"
    //rxAndroid2
    val rxAndroid2 = "io.reactivex.rxjava2:rxandroid:2.1.0"

    //rxLifecycle : https://github.com/trello/RxLifecycle
    val rxLifecycle = "com.trello.rxlifecycle2:rxlifecycle:2.2.2"
    //rxLifecycleComponents
    val rxLifecycleComponents = "com.trello.rxlifecycle2:rxlifecycle-components:2.2.2"
    //rxLifecycleNavi
    val rxLifecycleNavi = "com.trello.rxlifecycle2:rxlifecycle-navi:2.2.2"

    //oneDrawable : https://github.com/maoruibin/OneDrawable
    val oneDrawable = "com.github.maoruibin:OneDrawable:1.1.0"

    //xutils
    val xutils = "org.xutils:xutils:3.5.0"
    //utilCode
    val utilCode = "com.blankj:utilcodex:1.23.7"
    //httpCore
    val httpCore = "org.apache.httpcomponents:httpcore:4.4.12"
    //zxing
    val zxing = "com.google.zxing:core:3.3.2"
    //logger
    val logger = "com.orhanobut:logger:1.15"

    //rxBinding
    val rxBinding = "com.jakewharton.rxbinding:rxbinding:0.3.0"
    //rxBindingAppcompatV7
    val rxBindingAppcompatV7 = "com.jakewharton.rxbinding:rxbinding-appcompat-v7:0.3.0"
    //rxBindingDesign
    val rxBindingDesign = "com.jakewharton.rxbinding:rxbinding-design:0.3.0"

    //greenDao
    val greenDao = "de.greenrobot:greendao:2.0.0"
    //mmkv : 基于mmap的高性能通用key-value组件 https://github.com/Tencent/MMKV/blob/master/readme_cn.md
    val mmkv = "com.tencent:mmkv:1.0.16"

    //javaxAnnotation
    val javaxAnnotation = "org.glassfish:javax.annotation:10.0-b28"

    //dagger
    val dagger = "com.google.dagger:dagger:2.29.1"
    //daggerCompiler
    val daggerCompiler = "com.google.dagger:dagger-compiler:2.29.1"

    // fgchecker
    val fgchecker = "com.rvalerio:fgchecker:1.1.0"

    //BroadcastReceiver : https://github.com/cbfg5210/BroadcastReceiver
    val BroadcastReceiver = "com.github.cbfg5210:BroadcastReceiver:0.6"
    //BRecyclerAdapter : https://github.com/cbfg5210/BRecyclerAdapter
    val BRecyclerAdapter = "com.github.cbfg5210:BRecyclerAdapter:0.5"
    //BDialog : https://github.com/cbfg5210/BDialog
    val BDialog = "com.github.cbfg5210:BDialog:0.6"
    //BUtil : https://github.com/cbfg5210/BUtil
    val BUtil = "com.github.cbfg5210:BUtil:0.2"

    /*
     * 插件
     */
    //saveState : https://github.com/PrototypeZ/SaveState
    val saveState = "io.github.prototypez:save-state:0.2.3"

    /*
    * 测试工具
    */
    //stetho : https://github.com/facebook/stetho
    val stetho = "com.facebook.stetho:stetho:1.5.0"
    //stethoOkHttp3
    val stethoOkHttp3 = "com.facebook.stetho:stetho-okhttp3:1.5.0"
    //stethoUrlConnection
    val stethoUrlConnection = "com.facebook.stetho:stetho-urlconnection:1.5.0"

    //leakCanaryAndroid : https://github.com/square/leakcanary
    val leakCanaryAndroid = "com.squareup.leakcanary:leakcanary-android:2.1"
    //leakCanaryAndroidNoOp
    val leakCanaryAndroidNoOp = "com.squareup.leakcanary:leakcanary-android-no-op:1.6.3"
    //leakCanarySupportFragment
    val leakCanarySupportFragment = "com.squareup.leakcanary:leakcanary-support-fragment:1.6.1"

    //blockCanaryAndroid :卡顿监控和提示 https://github.com/markzhai/AndroidPerformanceMonitor
    val blockCanaryAndroid = "com.github.markzhai:blockcanary-android:1.5.0"

    //crashwoodpecker
    val crashwoodpecker = "me.drakeet.library:crashwoodpecker:2.1.1"

    //debugDb
    val debugDb = "com.amitshekhar.android:debug-db:1.0.6"

    //rxAndroidBle : https://github.com/Polidea/RxAndroidBle
    val rxAndroidBle = "com.polidea.rxandroidble2:rxandroidble:1.9.0"
    //ble : https://github.com/xiaoyaoyou1212/BLE
    val ble = "com.vise.xiaoyaoyou:baseble:2.0.5"


    /*
     * RecyclerView 的 DataBinding套装
     */
    //bindingcollectionadapter
    val bindingcollectionadapter = "me.tatarka.bindingcollectionadapter2:bindingcollectionadapter:2.2.0"
    //bindingcollectionadapter_recyclerview
    val bindingcollectionadapter_recyclerview = "me.tatarka.bindingcollectionadapter2:bindingcollectionadapter-recyclerview:2.2.0"
    //bindingcollectionadapter3
    val bindingcollectionadapter3 = "me.tatarka.bindingcollectionadapter2:bindingcollectionadapter:3.0.0"
    //bindingcollectionadapter_recyclerview3
    val bindingcollectionadapter_recyclerview3 = "me.tatarka.bindingcollectionadapter2:bindingcollectionadapter-recyclerview:3.0.0"
    //rxbinding
    val rxbinding = "com.jakewharton.rxbinding2:rxbinding:2.1.1"
    //fastjson
    val fastjson = "com.alibaba:fastjson:1.2.75"


    //hotfix : 啊里热修复
    val hotfix = "com.aliyun.ams:alicloud-android-hotfix:3.2.14"
    //kLog
    val kLog = "com.github.zhaokaiqiang.klog:library:1.6.0"

    //viewpager2
    val viewpager2 = "androidx.viewpager2:viewpager2:1.1.0-alpha01"
    //coordinatorLayout
    val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.1.0"

    //wcdb : 微信的数据库加密工具
    val wcdb = "com.tencent.wcdb:wcdb-android:1.0.8"
    //safeRoom : 是 Room 支持 sqlcipher 的第三方库
    val safeRoom = "com.commonsware.cwac:saferoom:1.1.3"


    /*hilt support begin*/
    //hilt
    val hilt = "com.google.dagger:hilt-android:2.29.1-alpha"
    //hiltCompiler : kapt
    val hiltCompiler = "com.google.dagger:hilt-android-compiler:2.29.1-alpha"
    //dataStore
    val dataStore = "androidx.datastore:datastore-preferences:1.0.0-alpha05"
    //androidxActivity
    val androidxActivity = "androidx.activity:activity:1.2.0"
    //accompanist
    val accompanist = "dev.chrisbanes.accompanist:accompanist-insets:0.6.0"
    /*hilt support end*/
}
