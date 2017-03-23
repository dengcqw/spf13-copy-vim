//
//  FakeWeChatLocationManager.m
//  temp
//
//  Created by Pandara on 16/8/14.
//  Copyright © 2016年 Pandara. All rights reserved.
//

#import "FakeWeChatLocationManager.h"

@implementation FakeWeChatLocationManager

+ (FakeWeChatLocationManager *)shareManager {
    static dispatch_once_t onceToken;
    static FakeWeChatLocationManager *shareManager;
    dispatch_once(&onceToken, ^{
        shareManager = [[FakeWeChatLocationManager alloc] init];
    });
    
    return shareManager;
}

- (void)showSetLocationAlert {
    UIAlertController *alert = [UIAlertController alertControllerWithTitle:@"Set Location" message:@"Default in 心斎橋筋商店街" preferredStyle:UIAlertControllerStyleAlert];

    NSString *lat = [[NSUserDefaults standardUserDefaults] objectForKey:@"PD_FAKE_LOCATION_LAT"];
    NSString *lng = [[NSUserDefaults standardUserDefaults] objectForKey:@"PD_FAKE_LOCATION_LNG"];
    if (lat.doubleValue < 0.1 || lng.doubleValue < 0.1) {
        lat = @"34.671470";
        lng = @"135.502326";
    }
    
    //Latitude and longitude textField
    [alert addTextFieldWithConfigurationHandler:^(UITextField * _Nonnull textField) {
        textField.placeholder = @"Latitude";
        textField.text = lat;
    }];
    [alert addTextFieldWithConfigurationHandler:^(UITextField * _Nonnull textField) {
        textField.placeholder = @"Longitude";
        textField.text = lng;
    }];
    
    //Actions
    [alert addAction:[UIAlertAction actionWithTitle:@"Cancel" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
    }]];
    [alert addAction:[UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        NSString *lat = alert.textFields[0].text;
        NSString *lng = alert.textFields[1].text;
        [[NSUserDefaults standardUserDefaults] setObject:lat forKey:@"PD_FAKE_LOCATION_LAT"];
        [[NSUserDefaults standardUserDefaults] setObject:lng forKey:@"PD_FAKE_LOCATION_LNG"];
    }]];

    [[UIApplication sharedApplication].keyWindow.rootViewController presentViewController:alert animated:YES completion:nil];
}

@end





















