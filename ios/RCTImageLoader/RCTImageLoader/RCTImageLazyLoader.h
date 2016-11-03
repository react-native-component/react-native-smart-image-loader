

#import <Foundation/Foundation.h>
#import "RCTImageLazyLoaderManager.h"
#import "FLAnimatedImageView.h"

@interface RCTImageLazyLoader : FLAnimatedImageView

@property (nonatomic, copy) NSString *src;

- (id)initWithManager:(RCTImageLazyLoaderManager*)manager;

//临时新增缓存键
@property (nonatomic, copy) NSString *cacheKey;
//临时新增url
//@property (nonatomic, copy) NSString *src;

@end
