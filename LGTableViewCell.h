//
//  UCTableViewCell.h
//  UICatalog
//
//  Created by DengJinlong on 8/17/14.
//  Copyright (c) 2014 DJL. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "LGCellModelInjectionProtocol.h"

@class LGTableViewCellModel;
@interface LGTableViewCell : UITableViewCell <LGCellModelInjectionProtocol>

/*!
 @brief  cell数据模型，重写setModel必须调用父类
 */
@property (weak, nonatomic, readonly) LGTableViewCellModel *model;

/*!
 @brief  下面方法由父类在`-setModel:`中调用，
         每次设置一个新model，需要重新建立绑定，才调用此方法，
         实现model与cell的绑定相关逻辑，
         可以继承此方法添加新功能。
 */
- (void)bindCellWithModel NS_REQUIRES_SUPER;

/*!
 @brief  下面方法由父类在`-setModel:`中调用，
         model非nil时，会调用此方法，
         实现非绑定状态需要静态刷新的逻辑，
         可以继承此方法添加新功能。
 */
- (void)updateCellWithModel NS_REQUIRES_SUPER;

/*!
 @brief  可以使用固定值`-cellHeight` 或重写下面方法动态计算高度
 @return cell height
 */
+ (CGFloat)tableView:(UITableView*)tableView rowHeightForObject:(id)object;


/*!
 @brief  custom separator color, equal to tableview separator color
 */
@property (strong, nonatomic) UIColor *separatorColor;

/*!
 @brief  默认45point
 */
+ (NSNumber *)cellHeight;

/*!
 @brief  默认为 class name
 */
+ (NSString *)cellIdentifier;

@end

#pragma mark - helper category

@interface UILabel (cellIndicator)
+ (id)cellIndicator;
@end
