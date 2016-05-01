// ********************************************** DISABLE (INITIAL) SCREEN LOCK
KeyguardManager keyguardManager = (KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE);
KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
lock.disableKeyguard();
// in android manifest:
<uses-permission android:name="android.permission.DISABLE_KEYGUARD"></uses-permission>








// ********************************************** get scren size of your device
Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay(); 
int width = display.getWidth(); 
int height = display.getHeight(); 
int ori = display.getOrientation();


