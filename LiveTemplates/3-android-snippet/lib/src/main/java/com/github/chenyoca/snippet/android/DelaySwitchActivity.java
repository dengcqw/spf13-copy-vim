package com.github.chenyoca.snippet.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * USER : chenyoca@gmail.com
 * DATE : 2014-06-25
 * A abstract activity with the ability that can auto switch to next activity.
 */
public abstract class DelaySwitchActivity extends Activity {

    private Runnable toNextTask = new Runnable() {
        @Override
        public void run() {
            Intent i = new Intent(DelaySwitchActivity.this, toNextConfig.nextActivity);
            DelaySwitchActivity.this.startActivity(i);
            DelaySwitchActivity.this.finish();
        }
    };

    protected class Config{

        Class<? extends Activity> nextActivity;
        int toNextDelayed = 2000;

        /**
         * The target activity's class .
         * @param nextActivity Target
         * @return Config instance
         */
        public Config target(Class<? extends Activity> nextActivity){
            this.nextActivity = nextActivity;
            return this;
        }

        /**
         * Delay in ms.
         * @param delayed
         * @return Config instance
         */
        public Config delayed(int delayed){
            this.toNextDelayed = delayed;
            return this;
        }

    }

    private Handler toNextHandler = new Handler();
    private Config toNextConfig = new Config();

    /**
     * Force to next activity.
     */
    protected void toNext(){
        toNextHandler.removeCallbacks(toNextTask);
        toNextHandler.post(toNextTask);
    }

    protected Config getConfig(){
        return toNextConfig;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (toNextConfig.nextActivity == null || toNextConfig.toNextDelayed <= 0){
            throw new RuntimeException("Splash Activity Config required !!");
        }
        toNextHandler.postDelayed(toNextTask, toNextConfig.toNextDelayed);
    }

    @Override
    protected void onPause() {
        super.onPause();
        toNextHandler.removeCallbacks(toNextTask);
    }
}
