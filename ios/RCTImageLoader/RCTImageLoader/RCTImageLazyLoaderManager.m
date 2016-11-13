//


#import "RCTImageLazyLoaderManager.h"
#import "RCTImageLazyLoader.h"
//#import "FLAnimatedImage.h"
//#import "FLAnimatedImageView.h"
#import "UIImageView+WebCache.h"

@implementation RCTImageLazyLoaderManager

RCT_EXPORT_MODULE(RCTImageLazyLoader)

- (UIView *)view
{
//    self.imageView = [[FLAnimatedImageView alloc] init];
//    return self.imageView;
//    return  [[FLAnimatedImageView alloc] init];
    return [[RCTImageLazyLoader alloc] initWithManager:self];
}

RCT_CUSTOM_VIEW_PROPERTY(options, NSDictionary, RCTImageLazyLoader) {
//    NSLog(@"set src...");
    NSDictionary *options = [RCTConvert NSDictionary:json];
    NSString *src = [options objectForKey:@"src"];
    NSString *placeholder =[options objectForKey:@"placeholder"];
    NSURL *url = [NSURL URLWithString:src];
    if(placeholder == nil) {
        [view sd_setImageWithURL:url];
    }
    else {
        UIImage *placeholderImage = [UIImage imageNamed:placeholder];
        [view sd_setImageWithURL:url placeholderImage:placeholderImage];
    }
    
//    [[SDImageCache sharedImageCache] setValue:nil forKey:@"memCache"];
    
//    view.src = src;
    view.cacheKey = [[SDWebImageManager sharedManager] cacheKeyForURL:url];
    
//    NSLog(@"setSrc sd_setImageWithURL cachekey = %@, src = %@", view.cacheKey, src);
}

//RCT_CUSTOM_VIEW_PROPERTY(hidden, BOOL, FLAnimatedImageView) {
//    NSLog(@"set hidden...");
//    BOOL *hidden = [RCTConvert BOOL:json];
//    if(hidden) {
//        [[SDImageCache sharedImageCache] removeImageForKey:view.cacheKey fromDisk:false withCompletion:nil];
//        NSLog(@"setHidden is true -> removeImageCachefromMemory cachekey = %@, src = %@", view.cacheKey, view.src);
//    }
//}

- (void)removeImageForKey: (NSString*)cacheKey {
//    NSLog(@"removeImageForKey...");
    [[SDImageCache sharedImageCache] removeImageForKey:cacheKey fromDisk:false withCompletion:nil];
//    NSLog(@"removeImageForKey cacheKey = %@", cacheKey);
}

//RCT_EXPORT_METHOD(clearCacheFromMemory) {
//    NSLog(@"clearCacheFromMemory");
//    [[SDImageCache sharedImageCache] clearMemory];
//}
//
//RCT_EXPORT_METHOD(clearCacheFromDisk) {
//    NSLog(@"clearCacheFromDisk");
//    [[SDImageCache sharedImageCache] clearDiskOnCompletion:nil];
//}
//
//RCT_EXPORT_METHOD(clearCache) {
//    NSLog(@"clearCacheFromMemoryAndDisk");
//    [[SDImageCache sharedImageCache] clearMemory];
//    [[SDImageCache sharedImageCache] clearDiskOnCompletion:nil];
//}
//
//RCT_EXPORT_METHOD(deleteOldFiles) {
//    NSLog(@"deleteOldFiles");
//    [[SDImageCache sharedImageCache] deleteOldFilesWithCompletionBlock:nil];
//}








@end
