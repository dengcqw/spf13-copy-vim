//
//  LGTableViewCellModel.h
//  UICatalog
//
//  Created by DengJinlong on 8/17/14.
//  Copyright (c) 2014 DJL. All rights reserved.
//

#import "LGObject.h"
#import "LGTableViewCellModelProtocol.h"
#import "LGCellModelReloadProtocol.h"

/*!
 @brief  cell 数据模型，MVVM中的viewModel
 */
@class LGDataModel;
@interface LGTableViewCellModel : LGObject <LGTableViewCellModelProtocol, LGCellModelReloadProtocol>

/*!
 @brief  默认初始化，必须初始化cellClass, cellIdentifier
 */
- (instancetype)initWithDefault;

/*!
 @brief  上下分隔线
 */
@property (assign, nonatomic) BOOL hideUpperLine;
@property (assign, nonatomic) BOOL hideLowerLine;

/*!
 @abstract  cell的位置，用于指示画线类型， 默认是LGGroupedCellPositonNone
 */
@property (nonatomic, assign) LGGroupedCellPositon groupedCellPostion;

/*!
 @abstract  cell上下边线的缩进值
 */
@property (nonatomic, strong) NSNumber *separatorIndent;

/*!
 @brief  cell整体缩进, contentView width变小，x坐标右移
 */
@property (strong, nonatomic) NSNumber *contentIndent;

/*!
 @brief  YES 点击无灰色效果，NO 点击有灰色效果
 */
@property (assign, nonatomic) BOOL disableSelectionStyle;

/**
 @brief YES 不可以点击，NO 可以点击，影响contentView 上的subview
 */
@property (assign, nonatomic) BOOL disableSelection;

/*!
 @brief YES 使用nib布局，NO 不使用nib
 */
@property (nonatomic, assign) BOOL enableNib;

/*!
 @brief  显示右侧箭头指示图标，会影响contentView width
 */
@property (assign, nonatomic) BOOL showIndicator;

@end

#pragma mark - helper

/*!
 @brief  when height value is nil or less then 0.0, it is invalid.
 */
@interface NSObject (CellHeightCache)
@property (copy, nonatomic) NSNumber *cachedCellHeight;
@end

@interface NSArray (GroupCell)
- (void)addGroupedCellPositionWithLineIndent:(NSNumber *)lineIndent;
- (void)addGroupedCellPosition;
@end
