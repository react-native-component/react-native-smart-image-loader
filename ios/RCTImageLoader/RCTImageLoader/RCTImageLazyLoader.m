

#import "RCTImageLazyLoader.h"
#import <React/UIView+React.h>

@interface RCTImageLazyLoader()

@property (nonatomic, weak) RCTImageLazyLoaderManager *manager;

@end


@implementation RCTImageLazyLoader

- (id)initWithManager:(RCTImageLazyLoaderManager*)manager
{
    
    if ((self = [super init])) {
        self.manager = manager;
    }
    return self;
    
}

- (void)removeFromSuperview
{
//    NSLog(@"removeFromSuperview...");
    
    [self.manager removeImageForKey:self.cacheKey];
    
    [super removeFromSuperview];
}

@end
