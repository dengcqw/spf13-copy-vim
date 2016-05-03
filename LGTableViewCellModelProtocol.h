//
//  UCTableViewCellModelProtocol.h
//  UICatalog
//
//  Created by DengJinlong on 8/17/14.
//  Copyright (c) 2014 DJL. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSUInteger, LGGroupedCellPositon) {
    LGGroupedCellPositonNone,  // 分隔线不缩进
    LGGroupedCellPositonFirst, // 上分隔线不缩进，下分隔线缩进
    LGGroupedCellPositonMiddle, // 上下分隔线缩进
    LGGroupedCellPositonLast // 上分隔线缩进，下分隔线不缩进
};

@protocol LGTableViewCellModelProtocol <NSObject>

@required

/*!
 @abstract  cell的类
 */
@property (nonatomic, assign) Class cellClass;
/*!
 @abstract  cell类型
 */
@property (nonatomic, copy) NSString *cellIdentifier;
/*!
 @abstract  cell高度
 */
@property (nonatomic, copy) NSNumber *cellHeight;


/*!
 @abstract  cell enum标识
 */
@property (nonatomic, assign) NSUInteger cellTag;
/*!
 @brief  引用相关数据模型
 */
@property (strong, nonatomic) id dataModel;


/*!
 @brief enable auto layout
 */
@property (assign, nonatomic) BOOL enableAutolayout;
/*!
 @brief  enable cell height cache
 */
@property (assign, nonatomic) BOOL enableCellHeightCache;


@end

