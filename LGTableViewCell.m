//
//  UCTableViewCell.m
//  UICatalog
//
//  Created by DengJinlong on 8/17/14.
//  Copyright (c) 2014 DJL. All rights reserved.
//

#import "LGTableViewCell.h"
#import "LGTableViewCellModelProtocol.h"
#import "UITableViewCell+LGCellStyle.h"
#import "LGTableViewCellModel.h"

#define LGTableViewCellHeight (45)
#ifndef UIColorFromRGB
#define UIColorFromRGB(rgbValue) [UIColor colorWithRed:((float)((rgbValue & 0xFF0000) >> 16))/255.0  \
                                                 green:((float)((rgbValue & 0xFF00) >> 8))/255.0 \
                                                  blue:((float)(rgbValue & 0xFF))/255.0 alpha:1.0]
#endif
#ifndef OnePoint
#define OnePoint (1/[UIScreen mainScreen].scale)
#endif
#define COLOR_FONT_MEDIUM_GRAY              [UIColor colorWithRed:242.0f/255.0f green:242.0f/255.0f blue:242.0f/255.0f alpha:1.0f]

@interface LGTableViewCell()
/*!
 @brief  分隔线offset
 */
@property (assign, nonatomic) CGFloat upperOffset;
@property (assign, nonatomic) CGFloat lowerOffset;
/*!
 @brief  自定义分隔线
 */
@property (nonatomic, strong) UILabel       *lineUpper;
@property (nonatomic, strong) UILabel       *lineLower;
@property (nonatomic, strong) UILabel       *bgLineUpper;
@property (nonatomic, strong) UILabel       *bgLineLower;

@property (nonatomic, strong) UILabel       *selectedView;
@property (nonatomic, retain) UILabel       *indicatorView;
@end

@implementation LGTableViewCell

- (void)dealloc {
    [self logInstanceNameForDealloc];
}

- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        [self setCellDefaultStyle];
        
        self.clipsToBounds = NO;
        self.contentView.clipsToBounds = NO;
        if ([self.contentView.superview isKindOfClass:[NSClassFromString(@"UITableViewCellScrollView") class]]) {
            self.contentView.superview.clipsToBounds = NO;
        }
        
        self.selectedView = [[UILabel alloc] initWithFrame: CGRectZero];
        self.selectedView.backgroundColor = COLOR_FONT_MEDIUM_GRAY;
        self.selectedView.autoresizingMask = UIViewAutoresizingFlexibleHeight;
        self.selectedBackgroundView = self.selectedView;
        
        self.bgLineUpper = [[UILabel alloc] initWithFrame: CGRectZero];
        self.bgLineLower = [[UILabel alloc] initWithFrame: CGRectZero];
        [self.selectedView addSubview:self.bgLineUpper];
        [self.selectedView addSubview:self.bgLineLower];
        
        self.lineUpper = [[UILabel alloc] initWithFrame: CGRectZero];
        self.lineLower = [[UILabel alloc] initWithFrame: CGRectZero];
        [self.contentView addSubview:self.lineUpper];
        [self.contentView addSubview:self.lineLower];
    }
    return self;
}

- (void)setSeparatorColor:(UIColor *)separatorColor {
    if (separatorColor == nil) {
        return;
    }
    _separatorColor = separatorColor;
    self.lineUpper.backgroundColor = separatorColor;
    self.lineLower.backgroundColor = separatorColor;
    self.bgLineUpper.backgroundColor = separatorColor;
    self.bgLineLower.backgroundColor = separatorColor;
}

// 通过运行时处理
//- (void)setModel:(LGTableViewCellModel *)model {
//    if (model != _model) { // 设置一个新model，需要重新建立绑定
//        _model = model;
//        [self bindCellWithModel];
//    }
//    if (model != nil) { // 非绑定状态，需要静态刷新数据
//        [self updateCellWithModel];
//    }
//}

- (UILabel *)indicatorView {
    if (nil == _indicatorView) {
        self.indicatorView = [UILabel cellIndicator];
    }
    return _indicatorView;
}

- (void)bindCellWithModel {
}

- (void)updateCellWithModel {
    // 使能选中效果
    if (self.model.disableSelectionStyle) {
        self.selectionStyle = UITableViewCellSelectionStyleNone;
    } else {
        self.selectionStyle = UITableViewCellSelectionStyleGray;
    }
    
    // 使能交互
    if (self.model.disableSelection) {
        self.userInteractionEnabled = NO;
    } else {
        self.userInteractionEnabled = YES;
    }
    
    // 指示箭头
    if (self.model.showIndicator) {
        self.accessoryView = self.indicatorView;
    } else {
        self.accessoryView = nil;
    }
    
    // 分隔线隐藏
    self.lineUpper.hidden = self.model.hideUpperLine;
    self.lineLower.hidden = self.model.hideLowerLine;
    self.bgLineUpper.hidden = self.model.hideUpperLine;
    self.bgLineLower.hidden = self.model.hideLowerLine;
    
    // 获得分隔线缩进值
    CGFloat offset = self.model.separatorIndent.floatValue;
    switch (self.model.groupedCellPostion) {
        case LGGroupedCellPositonFirst:
            _lowerOffset = offset;
            break;
        case LGGroupedCellPositonMiddle:
            _upperOffset = _lowerOffset = offset;
            break;
        case LGGroupedCellPositonLast:
            _upperOffset = offset;
            break;
        case LGGroupedCellPositonNone:
            _upperOffset = _lowerOffset = 0.0f;
        default:
            break;
    }
}


- (void)layoutSubviews{
    [super layoutSubviews];
    
    CGFloat lineHeight = OnePoint;
    CGFloat upperOffset = self.upperOffset;
    CGFloat lowerOffset = self.lowerOffset;
    
    self.contentView.left = self.model.contentIndent.floatValue;
    self.contentView.width = self.contentView.width-self.model.contentIndent.floatValue;
    
    self.lineUpper.frame = CGRectMake(upperOffset, 0, self.width-upperOffset, lineHeight);
    self.lineLower.frame = CGRectMake(lowerOffset, self.height, self.width-lowerOffset, lineHeight);
    [self.contentView bringSubviewToFront:self.lineUpper];
    [self.contentView bringSubviewToFront:self.lineLower];
    
    self.selectedBackgroundView.frame = self.bounds;
    self.bgLineUpper.frame = CGRectMake(upperOffset, 0, self.width-upperOffset, lineHeight);
    self.bgLineLower.frame = CGRectMake(lowerOffset, self.height, self.width-lowerOffset, lineHeight);
    [self.selectedBackgroundView bringSubviewToFront:self.bgLineLower];
    [self.selectedBackgroundView bringSubviewToFront:self.bgLineUpper];
}

#pragma mark - instance method

+ (BOOL)resolveClassMethod:(SEL)sel {
    SEL heightSel = @selector(tableView:rowHeightForObject:);
    if (sel_isEqual(sel, heightSel)) {
        return YES;
    } else {
        return [super resolveClassMethod:sel];
    }
}

+ (CGFloat)tableView:(UITableView *)tableView rowHeightForObject:(id)object {
    if ([object conformsToProtocol:@protocol(LGTableViewCellModelProtocol)]) {
        if ([object enableCellHeightCache] && [object cachedCellHeight]) {
            CGFloat tempH = [object cachedCellHeight].floatValue;
            if (tempH > 0) { // use cache
                return tempH;
            }
        }
        if ([object enableAutolayout]) { // autolayout calc here
            return 0.0;
        } else {
            return [[object cellHeight] floatValue];
        }
    } else {
        return 0.0;
    }
}

+ (NSString *)cellIdentifier {
    return NSStringFromClass([self class]);
}

+ (NSNumber *)cellHeight {
    return @(LGTableViewCellHeight);
}

@end

#pragma mark - helper category

@implementation UILabel (cellIndicator)

+ (id)cellIndicator {
    UILabel *indicator = [[UILabel alloc] initWithFrame:CGRectMake(0, 0, 15, 15)];
    indicator.font = [UIFont fontWithName:@"iconfont" size:14];
    indicator.text = @"\U0000e61f";
    indicator.textColor = UIColorFromRGB(0xc7c7cc);
    indicator.backgroundColor = [UIColor clearColor];
    return indicator;
}

@end