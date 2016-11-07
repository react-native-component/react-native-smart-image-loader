package com.reactnativecomponent.imageloader;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.react.bridge.ReactMethod;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


public class RCTLoaderImageView extends ImageView {
//    Context context;
    String src;
    //    MyImageLoadingListener listener;

    DisplayImageOptions options;

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }

    private String rowID;

    public RCTLoaderImageView(Context context) {
        super(context);
//        listener = new MyImageLoadingListener();

    }

   /* public RCTLoaderImageView(Context context, AbstractDraweeControllerBuilder draweeControllerBuilder, @Nullable Object callerContext) {
        super(context, draweeControllerBuilder, callerContext);
    }*/


    public void loaderImage(String src, DisplayImageOptions options) {
/*本地图片
    String imagePath = "/mnt/sdcard/image.png";
    String imageUrl = Scheme.FILE.wrap(imagePath);*/
        if (src != null && options != null) {
            ImageLoader imageLoader = ImageLoader.getInstance();
            this.src = src;
            this.options = options;
//            imageDisplay(imageLoader);
//        imageLoader.displayImage(src, RCTLoaderImageView.this, options);
//        imageLoader.loadImage(src,listener);
        }

    }



//    @Override
//    protected void onDetachedFromWindow() {
//
////        Log.i("RCTLoadImagView's log", String.valueOf(this.getRowID()) + " onDetachedFromWindow");
//
//        ImageLoader imageLoader = ImageLoader.getInstance();
//        imageLoader.cancelDisplayTask(this);
////        this.setDrawingCacheEnabled(true);
////        this.getDrawingCache().recycle();
////        this.setImageBitmap(null);
////        this.setDrawingCacheEnabled(false);
//        super.onDetachedFromWindow();
//
//    }

//    @Override
//    protected void onAttachedToWindow() {
////        Log.i("1Test",  "onAttachedToWindow=========");
//        int[] location=new int[2];
//
////        this.getLocationOnScreen(location);
//        this.getLocationInWindow(location);
//
//        if(location[1]<3300&&location[1]>-1560){
//            Log.i("1Test", "onAttachedToWindow========="+location[0]+","+location[1]);
//            ImageLoader imageLoader = ImageLoader.getInstance();
//
//            this.setDrawingCacheEnabled(false);
//            imageLoader.displayImage(src, RCTLoaderImageView.this, options);
//        }
//
//        super.onAttachedToWindow();
//    }


    /*    class MyImageLoadingListener implements ImageLoadingListener {

        @Override
        public void onLoadingStarted(String imageUri, View view) {

        }

        @Override
        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            bitmap = loadedImage;
            RCTLoaderImageView.this.setImageBitmap(loadedImage);
        }

        @Override
        public void onLoadingCancelled(String imageUri, View view) {

        }
    }*/


    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        ImageLoader imageLoader = ImageLoader.getInstance();
        if (visibility == View.VISIBLE) {
//            Log.i("VISIBLE rowID ->", String.valueOf(this.getRowID()));
            imageDisplay(imageLoader);
        } else if (visibility == View.INVISIBLE || visibility == View.GONE) {
//            Log.i("INVISIBLE rowID ->", String.valueOf(this.getRowID()));
            imageLoader.cancelDisplayTask(this);
//            this.setDrawingCacheEnabled(true);
//            this.getDrawingCache().recycle();
//            this.setImageBitmap(null);
        }

    }

    /**
     * imageLoader显示图片
     *
     * @param imageLoader
     */

    private void imageDisplay(ImageLoader imageLoader) {
        this.setDrawingCacheEnabled(false);

        imageLoader.displayImage(src, RCTLoaderImageView.this, options);
    }

//
//    protected boolean isCover() {
//        boolean cover = false;
//        Rect rect = new Rect();
//        cover = getGlobalVisibleRect(rect);
////        Log.i("1Test","可见区域："+rect.width()+", 高度："+rect.height());
//        this.setDrawingCacheEnabled(true);
//        this.getDrawingCache().recycle();
//        this.setImageBitmap(null);
////        this.setDrawingCacheEnabled(false);
//
//        return true;
//    }
//
//
//    @Override
//    public boolean isShown() {
//        return super.isShown();
//    }
}
