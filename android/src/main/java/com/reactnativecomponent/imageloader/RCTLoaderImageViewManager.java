package com.reactnativecomponent.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeControllerBuilder;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.image.ReactImageManager;
import com.facebook.react.views.image.ReactImageView;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;


public class RCTLoaderImageViewManager extends SimpleViewManager<RCTLoaderImageView> {
    private static final String REACT_CLASS = "RCTLoaderImageView";//要与类名一致
    private ImageLoaderConfiguration config;
    private DisplayImageOptions options;
    private Context context;
    /**
     * <=========================Config 变量   开始=======================>
     *
     */

    /**
     * 内存缓存数（MB）
     */
    private int memorySize = 5;
    /**
     * 线程使用数
     */
    public int threadSize = 3;
    /**
     * 超时时间
     */
    private int readTimeout = 30;
    /**
     * 链接超时时间
     */
    private int connectTimeout = 5;


    /**
     * <========================= Config 变量   结束=======================>
     */


    /**
     * <========================= option 变量   开始=======================>
     *
     */

    /**
     * 加载中图片
     */
//    public String loadingImage = "goods_placeholder";
    public String loadingImage = "";
    /**
     * 空链接图片
     */
//    public String emptyUriImage = "goods_placeholder";
    public String emptyUriImage = "";
    /**
     * 错误图片
     */
//    public String failImage = "goods_placeholder";
    public String failImage = "";
    /**
     * 内存缓存
     */
    private boolean cacheInMemory = false;

    /**
     * sd缓存
     */
    private boolean cacheOnDisc = true;

    /**
     * 圆角，0为不加远角
     */
    private int Round = 0;

    /**
     * 渐变加载动画时间,0不渐变,单位 255
     */
    private int fadeInDuration = 0;

    /**
     * <=========================option 变量   结束=======================>
     */


   /* private @javax.annotation.Nullable
    AbstractDraweeControllerBuilder mDraweeControllerBuilder;
    private final @javax.annotation.Nullable
    Object mCallerContext;*/


    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public RCTLoaderImageViewManager(Context context) {
        this.context=context;


      /*  if (config == null) {

            initConfig(context);
        }

        if (options == null) {
            initOptions();
        }*/
        // Lazily initialize as FrescoModule have not been initialized yet
//        mDraweeControllerBuilder = null;
//        mCallerContext = null;
    }

  /*  public RCTLoaderImageViewManager(
            AbstractDraweeControllerBuilder draweeControllerBuilder,
            Object callerContext) {
        mDraweeControllerBuilder = draweeControllerBuilder;
        mCallerContext = callerContext;
    }*/



    @Override
    protected RCTLoaderImageView createViewInstance(ThemedReactContext reactContext) {

        if (context == null) {
            context = reactContext;
        }
/*
        if (config == null) {

            initConfig(reactContext);
        }

        if (options == null) {
            initOptions();
        }*/


       /* return new RCTLoaderImageView(
                context,
                getDraweeControllerBuilder(),
                getCallerContext());*/
        return new RCTLoaderImageView(context);
    }

    private void initOptions() {
        if(options==null){


        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(getSplashId(loadingImage)) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(getSplashId(emptyUriImage))//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(getSplashId(failImage))  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(cacheInMemory)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(cacheOnDisc)//设置下载的图片是否缓存在SD卡中
//                    .considerExifParams(false)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
//.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
//设置图片加入缓存前，对bitmap进行设置
//.preProcessor(BitmapProcessor preProcessor)
//                    .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
//                    .displayer(new RoundedBitmapDisplayer(Round))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(fadeInDuration))//是否图片加载好后渐入的动画时间
                .build();//构建完成
    }
    }
    private void initConfig(Context reactContext) {
        if(config==null) {
//            Log.i("!Test", "creatImageLoader Config================threadSize:" + threadSize + ",loadingImage:" + loadingImage+"fadeInDuration:"+fadeInDuration);
            config = new ImageLoaderConfiguration.Builder(reactContext)
//                    .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
//                    .taskExecutor()
//            .taskExecutorForCachedImages()
                    .threadPoolSize(threadSize)//线程池内加载的数量
                    .threadPriority(Thread.NORM_PRIORITY - 1)//设置图片加载线程的优先级(比正常线程优先级低1级) // 注:如果设置了taskExecutor或者taskExecutorForCachedImages 此设置无效
//                    .denyCacheImageMultipleSizesInMemory() // 设置拒绝缓存在内存中一个图片多个大小 默认为允许,(同一个图片URL)根据不同大小的imageview保存不同大小图片
//                    .memoryCache(new UsingFreqLimitedMemoryCache(memorySize * 1024 * 1024)) // 如果缓存的图片总量超过限定值，先删除使用频率最小的bitmap，  // 设置内存缓存 默认为一个当前应用可用内存的1/8大小的LruMemoryCache
                    // 设置内存缓存的最大大小 默认为一个当前应用可用内存的1/8
                    // .memoryCacheSize(2 * 1024 * 1024)
                    // 设置内存缓存最大大小占当前应用可用内存的百分比 默认为一个当前应用可用内存的1/8
                    // .memoryCacheSizePercentage(13)
                    // 设置硬盘缓存
                    // 默认为StorageUtils.getCacheDirectory(getApplicationContext())
                    // 即/mnt/sdcard/android/data/包名/cache/
                    //.discCache(new UnlimitedDiscCache(StorageUtils.getCacheDirectory(getApplicationContext())))
                    // 设置硬盘缓存的最大大小
//                    .discCacheSize(50 * 1024 * 1024)

                    // 设置硬盘缓存的文件的最多个数
//                    .discCacheFileCount(100)
                    // 设置图片下载器
                    // 默认为 DefaultConfigurationFactory.createBitmapDisplayer()
                    // .imageDownloader(
                    // new HttpClientImageDownloader(getApplicationContext(),
                    // new DefaultHttpClient()))
                    // 设置图片解码器
                    // 默认为DefaultConfigurationFactory.createImageDecoder(false)
                    // .imageDecoder(DefaultConfigurationFactory.createImageDecoder(false))

                    // 设置默认的图片显示选项
                    // 默认为DisplayImageOptions.createSimple()
                    // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                    .tasksProcessingOrder(QueueProcessingType.LIFO)  // 设置图片加载和显示队列处理的类型 默认为QueueProcessingType.FIFO  // 注:如果设置了taskExecutor或者taskExecutorForCachedImages 此设置无效
                    .diskCacheExtraOptions(480, 320, null)
//                    .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
//                    .imageDownloader(new BaseImageDownloader(reactContext, connectTimeout * 1000, readTimeout * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
//                    .writeDebugLogs() // Remove for release app
                    .build();//开始构建
            ImageLoader.getInstance().init(config);
        }
    }

   /* public Object getCallerContext() {
        return mCallerContext;
    }

    public AbstractDraweeControllerBuilder getDraweeControllerBuilder() {
        if (mDraweeControllerBuilder == null) {
            mDraweeControllerBuilder = Fresco.newDraweeControllerBuilder();
        }
        return mDraweeControllerBuilder;
    }*/

    /**
     * "http://site.com/image.png" // from Web
     * "file:///mnt/sdcard/image.png" // from SD card
     * "file:///mnt/sdcard/video.mp4" // from SD card (video thumbnail)
     * "content://media/external/images/media/13" // from content provider
     * "content://media/external/video/media/13" // from content provider (video thumbnail)
     * "assets://image.png" // from assets
     * "drawable://" + R.drawable.img // from drawables (non-9patch images)
     *
     * @param view
     * @param src
     */


   /* *//**
     * 线程数
     * @param view
     * @param size
     *//*
    @ReactProp(name = "threadSize",defaultInt = 2)
    public void setThreadSize(RCTLoaderImageView view, int size) {
        this.threadSize=size;

    }



    *//**
     * 预加载图片名称
     * @param view
     * @param image
     *//*
    @ReactProp(name = "loadingImage")
    public void setLoadingImage(RCTLoaderImageView view, @Nullable String image) {
        this.loadingImage=image;

    } *//**
     * 预加载图片名称
     * @param view
     * @param image
     *//*
    @ReactProp(name = "emptyUriImage")
    public void setEmptyUriImage(RCTLoaderImageView view, @Nullable String image) {
        this.emptyUriImage=image;

    } *//**
     * 预加载图片名称
     * @param view
     * @param image
     *//*
    @ReactProp(name = "failImage")
    public void setFailImage(RCTLoaderImageView view, @Nullable String image) {
        this.failImage=image;

    }

    *//**
     * 线程数
     * @param view
     * @param time
     *//*
    @ReactProp(name = "fadeInDuration",defaultInt = 510)
    public void setTFadeIn(RCTLoaderImageView view, int time) {
        this.fadeInDuration=time;

    }

    *//**
     * 图片路径
     * @param view
     * @param
     *//*
    @ReactProp(name = "src")
    public void setSrc(RCTLoaderImageView view, @Nullable String src) {
        initConfig(context);
        initOptions();
        view.loaderImage(src, options);
    }
*/

    @ReactProp(name = "options")
    public void setData(RCTLoaderImageView view, ReadableMap map) {
        if(map.hasKey("threadSize")) {
            this.threadSize = map.getInt("threadSize");
        }
        if(map.hasKey("fadeInDuration")) {
            this.fadeInDuration = map.getInt("fadeInDuration");
        }

        this.loadingImage = map.getString("placeholder");

        this.emptyUriImage = map.getString("placeholder");

        this.failImage = map.getString("placeholder");

        String src = map.getString("src");

        String rowID = map.getString("rowID");
        view.setRowID(rowID);

        initConfig(context);
        initOptions();
        view.loaderImage(src, options);
    }

    /**
     * 获得图片资源ID
     *
     * @return
     */
    private int getSplashId(String s) {
        if (context != null&&s!=null&&!s.isEmpty()) {
            int drawableId = context.getResources().getIdentifier(s, "drawable", context.getClass().getPackage().getName());
            if (drawableId == 0) {
                drawableId = context.getResources().getIdentifier(s, "drawable", context.getPackageName());
            }
            return drawableId;
        }
        return 0;
    }
}
