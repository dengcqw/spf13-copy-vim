// ********************************************** Animation
TranslateAnimation animation = new TranslateAnimation(0, 50, 0, 100);
animation.setDuration(6000);
animation.setFillAfter(false);

animation.setAnimationListener(new AnimationListener(){
    @Override
    public void onAnimationStart(Animation animation){

    }
    @Override
    public void onAnimationRepeat(Animation animation){

    }
    @Override
    public void onAnimationEnd(Animation animation){

    }
});
btnTimer.startAnimation(animation);








// **********************************************  Timer
new CountDownTimer(5000, 100){
    public void onTick(long millisUntilFinished){
        txt.setText("remain:" + millisUntilFinished / 100);
    }
    public void onFinish(){
        txt.setText("done!");
    }
}.start();
