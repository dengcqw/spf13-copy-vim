#import "FakeWeChatLocationManager.h"

%hook CLLocationManager
- (void)startUpdatingLocation {
    NSLog(@"Fake location");
    CGFloat lat = [[[NSUserDefaults standardUserDefaults] objectForKey:@"PD_FAKE_LOCATION_LAT"] doubleValue];
    CGFloat lng = [[[NSUserDefaults standardUserDefaults] objectForKey:@"PD_FAKE_LOCATION_LNG"] doubleValue];
    if (lat < 0.1 || lng < 0.1) {
        lat = 34.671470;
        lng = 135.502326;
    }
    
    CLLocation *tokyoLocation = [[CLLocation alloc] initWithLatitude:lat longitude:lng];

    CLLocation *cantonLocation = [[CLLocation alloc] initWithLatitude:31.172120 longitude:121.406227];

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wdeprecated-declarations"
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1.0f * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        [self.delegate locationManager:self didUpdateToLocation:tokyoLocation fromLocation:cantonLocation];
    });
#pragma clang diagnostic pop
}
%end

%hook NewSettingViewController
- (void)viewDidAppear:(BOOL)animated {
    %orig;
    [[FakeWeChatLocationManager shareManager] showSetLocationAlert];
}
%end

%hook QIYISettingViewController
- (void)viewDidAppear:(BOOL)animated {
    %orig;
    [[FakeWeChatLocationManager shareManager] showSetLocationAlert];
}
%end

