// method 1:
URL newurl = new URL(photo_url_str); 
mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
profile_photo.setImageBitmap(mIcon_val);

// Method 2:
public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
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

// method 3:
private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}

// Method 4:
public static Bitmap loadBitmap(String url) {
    Bitmap bitmap = null;
    InputStream in = null;
    BufferedOutputStream out = null;

    try {
        in = new BufferedInputStream(new URL(url).openStream(), IO_BUFFER_SIZE);

        final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
        out = new BufferedOutputStream(dataStream, IO_BUFFER_SIZE);
        copy(in, out);
        out.flush();

        final byte[] data = dataStream.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 1;

        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);
    } catch (IOException e) {
        Log.e(TAG, "Could not load Bitmap from: " + url);
    } finally {
        closeStream(in);
        closeStream(out);
    }

    return bitmap;
}
