//
//  UITableViewCell+LGCellStyle.m
//  LeGengApp
//
//  Created by DengJinlong on 5/2/15.
//  Copyright (c) 2015 LeGeng. All rights reserved.
//

#import "UITableViewCell+LGCellStyle.h"

@implementation UITableViewCell (LGCellStyle)

- (void)setCellDefaultStyle {
    [self setCustomBackgroundColor:[UIColor clearColor]];
    self.accessoryType             = UITableViewCellAccessoryNone;
    self.selectionStyle            = UITableViewCellSelectionStyleNone;
}

- (void)clearBackgroundColor{
    UIView * backgroundView          = [[UIView alloc] initWithFrame:CGRectMake(0, 0, self.frame.size.width, self.frame.size.height)];
    backgroundView.backgroundColor   = [UIColor clearColor];
    
    self.backgroundColor             = [UIColor clearColor];
    self.backgroundView              = backgroundView;
    self.contentView.backgroundColor = [UIColor clearColor];
}

- (void)setCustomBackgroundColor:(UIColor *)color{
    UIView * backgroundView          = [[UIView alloc] initWithFrame:CGRectZero];
    backgroundView.backgroundColor   = color;
    
    self.backgroundColor             = color;
    self.backgroundView              = backgroundView;
    self.contentView.backgroundColor = color;
}

@end
