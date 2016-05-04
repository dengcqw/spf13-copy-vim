//
//  UITableViewCell+LGCellStyle.h
//  LeGengApp
//
//  Created by DengJinlong on 5/2/15.
//  Copyright (c) 2015 LeGeng. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UITableViewCell (LGCellStyle)

- (void)setCellDefaultStyle;

//clear background color
- (void)clearBackgroundColor;

//自定义设置cell背景色
- (void)setCustomBackgroundColor:(UIColor *)color;

@end
