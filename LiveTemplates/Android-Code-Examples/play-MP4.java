Uri uri = Uri.parse("your mp4 url stream"); 
mVideoView.setMediaController(new android.widget.MediaController(getApplicationContext()));
mVideoView.setVideoURI(uri); 
mVideoView.requestFocus();
mVideoView.start();

/*
NOTICE:
1 - Remember to declare a new permission in AndroidManifest, allow your app to connect to the Internet.
2 - mVideoView: This is VideoView.
3 - To stop video, we use: mVideoView.stopPlayback();
*/
