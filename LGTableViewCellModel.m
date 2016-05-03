//
//  LGTableViewCellModel.m
//  UICatalog
//
//  Created by DengJinlong on 8/17/14.
//  Copyright (c) 2014 DJL. All rights reserved.
//

#import "LGTableViewCellModel.h"

@implementation LGTableViewCellModel
@synthesize cellClass           = _cellClass;
@synthesize cellHeight          = _cellHeight;
@synthesize cellIdentifier      = _cellIdentifier;
@synthesize dataModel           = _dataModel;
@synthesize cellTag             = _cellTag;
@synthesize enableAutolayout    = _enableAutolayout;
@synthesize enableCellHeightCache = _enableCellHeightCache;

// reload protocol
@synthesize reloadSignal        = _reloadSignal;
@synthesize updatePolicy        = _updatePolicy;


- (instancetype) init {
    if (self = [super init]) {
        // 初始化
        @weakify(self);
        self.reloadSignal = [[self rac_signalForSelector:@selector(reloadCell:)]
                             map:^id(id value) {
                                 @strongify(self);
                                 return self;
                             }];
        self.cellTag = INT_MAX;
    }
    return self;
}

- (instancetype)copyWithZone:(NSZone *)zone {
    LGTableViewCellModel *cellModel = [super copyWithZone:zone];
    cellModel.hideLowerLine = self.hideLowerLine;
    cellModel.hideUpperLine = self.hideUpperLine;
    cellModel.groupedCellPostion = self.groupedCellPostion;
    cellModel.separatorIndent = self.separatorIndent;
    cellModel.contentIndent = self.contentIndent;
    cellModel.disableSelectionStyle = self.disableSelectionStyle;
    cellModel.disableSelection = self.disableSelection;
    cellModel.enableNib = self.enableNib;
    cellModel.showIndicator = self.showIndicator;
    cellModel.separatorIndent = self.separatorIndent;
    
    // LGTableViewCellModelProtocol
    cellModel.cellClass = self.cellClass;
    cellModel.cellHeight = self.cellHeight;
    cellModel.cellIdentifier = self.cellIdentifier;
    cellModel.dataModel = self.dataModel;
    cellModel.cellTag = self.cellTag;
    cellModel.enableAutolayout = self.enableAutolayout;
    cellModel.enableCellHeightCache = self.enableCellHeightCache;
    
    // LGCellModelReloadProtocol
    cellModel.reloadSignal = self.reloadSignal;
    cellModel.updatePolicy = self.updatePolicy;
    
    return cellModel;
}

- (void)reloadCell:(id)sender {
}

- (instancetype)initWithDefault {
    NSAssert(NO ,@"cell item did not implement -initWithDefault");
    return nil;
}

- (NSString *)cellIdentifier {
    if (_cellIdentifier == nil) {
        if ([_cellClass isSubclassOfClass:[LGTableViewCell class]]) {
            _cellIdentifier = [_cellClass performSelector:@selector(cellIdentifier)];
        }
    }
    return _cellIdentifier;
}

@end

#pragma mark - helper

@implementation NSObject (CellHeightCache)

static void *kCellHeightAssociatedKey = &kCellHeightAssociatedKey;

- (void)setCachedCellHeight:(NSNumber *)cachedCellHeight {
    objc_setAssociatedObject(self, kCellHeightAssociatedKey, cachedCellHeight, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}

- (NSNumber *)cachedCellHeight {
    id theObject = objc_getAssociatedObject(self, kCellHeightAssociatedKey);
    return theObject;
}

@end

@implementation NSArray (GroupCell)

- (void)addGroupedCellPositionWithLineIndent:(NSNumber *)lineIndent {
    [self addGroupedCellPosition];
    [self enumerateObjectsUsingBlock:^(LGTableViewCellModel *model, NSUInteger idx, BOOL *stop) {
        if ([model isKindOfClass:[LGTableViewCellModel class]]) {
            model.separatorIndent = lineIndent;
        }
    }];
}

- (void)addGroupedCellPosition {
    LGTableViewCellModel *cellModel = nil;
    
    switch (self.count) {
        case 0:
            break;
        case 1: {
            cellModel = [self firstObject];
            if ([cellModel isKindOfClass:[LGTableViewCellModel class]]) {
                cellModel.groupedCellPostion = LGGroupedCellPositonNone;
            }
            break;
        }
        default: { // more then one item
            NSUInteger lastIdx = [self count]-1;
            [self enumerateObjectsUsingBlock:^(LGTableViewCellModel *model, NSUInteger idx, BOOL *stop) {
                if ([model isKindOfClass:[LGTableViewCellModel class]]) {
                    if (idx == 0) {
                        model.groupedCellPostion = LGGroupedCellPositonFirst;
                    } else if (idx != lastIdx) {
                        model.groupedCellPostion = LGGroupedCellPositonMiddle;
                    } else {
                        model.groupedCellPostion = LGGroupedCellPositonLast;
                    }
                }
            }];
        }
            break;
    }
}

@end

