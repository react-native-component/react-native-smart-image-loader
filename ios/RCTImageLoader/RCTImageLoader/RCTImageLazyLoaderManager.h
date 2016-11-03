

#import <UIKit/UIKit.h>
#import "RCTViewManager.h"
#import "FLAnimatedImageView.h"

@class RCTImageLazyLoader;

@interface RCTImageLazyLoaderManager : RCTViewManager

@property (nonatomic, strong) FLAnimatedImageView *imageView;

- (void)removeImageForKey:(NSString*)cacheKey;


@end
