private Bitmap getBitmap(String url)
    {
        File f=fileCache.getFile(url);

        //from SD cache
        Bitmap b = decodeFile(f);
        if(b!=null)
            return b;

        //from web
        try {
            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Exception ex){
           ex.printStackTrace();
           return null;
        }
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }
    
    
    
  // use that image as bitmap in below code:
Bitmap icon1 = downloadedBitmap;

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    this).setAutoCancel(true)
                    .setContentTitle("DJ-Android notification")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentText("Hello World!");

            NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
            bigPicStyle.bigPicture(icon1);
            bigPicStyle.setBigContentTitle("Dhaval Sodha Parmar");
            mBuilder.setStyle(bigPicStyle);

            // Creates an explicit intent for an Activity in your app
            Intent resultIntent = new Intent(this, testActivity.class);

            // The stack builder object will contain an artificial back stack
            // for
            // the
            // started Activity.
            // This ensures that navigating backward from the Activity leads out
            // of
            // your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

            // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(testActivity.class);

            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                    0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);

            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // mId allows you to update the notification later on.
            mNotificationManager.notify(100, mBuilder.build());
    
    
    
    
    
    
    
    
    
    





























































public Bitmap getBitmapFromURL(String strURL) {
    try {
        URL url = new URL(strURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        Bitmap myBitmap = BitmapFactory.decodeStream(input);
        return myBitmap;
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}



Bitmap bitmap = getBitmapFromURL("https://graph.facebook.com/YOUR_USER_ID/picture?type=large");





// CONSTRUCT THE NOTIFICATION DETAILS
builder.setAutoCancel(true);
builder.setSmallIcon(R.drawable.ic_launcher);
builder.setContentTitle("Some Title");
builder.setContentText("Some Content Text");
builder.setLargeIcon(bitmap);
builder.setContentIntent(pendingIntent);
