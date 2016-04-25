// ********************************************** Set image from local
img.setImageResource(id)






//********************************************** Set background
screen = (RelativeLayout) findViewById(screen id)
screen.setBackgroundResource(drawable id)




//********************************************** Set image by path
String path = Environment.getExternalStorageDirectory()+ "/Images/test.jpg";

File imgFile = new File(path);
if(imgFile.exists())
{
   Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
   ImageView imageView=(ImageView)findViewById(R.id.imageView);
  imageView.setImageBitmap(myBitmap);
}




// ********************************************** imageView to byte array
public byte[] ImageView_To_Byte(){
        //Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.chomuc);
        BitmapDrawable drawable = (BitmapDrawable) imgvHinhSP.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
}



// byte array to imageView
Bitmap bitmap = BitmapFactory.decodeByteArray(BYTE[], 0, BYTE[].length);
imgv.setImageBitmap(bitmap);











//********************************************************************************************
// COMBINE MULTIPLE BITMAP INTO ONE
// Example:
// Bitmap bm1=BitmapFactory.decodeResource(getResources(),.drawable.ic_launcher);
// ArrayList<Bitmap> a=new ArrayList<Bitmap>();
// a.add(bm1);
// a.add(bm1);
// a.add(bm1);
// combineImageIntoOne(a);

// Cobine Multi Image Into One
private Bitmap combineImageIntoOne(ArrayList<Bitmap> bitmap) {
	int w = 0, h = 0;
	for (int i = 0; i < bitmap.size(); i++) {
	if (i < bitmap.size() - 1) {
	w = bitmap.get(i).getWidth() > bitmap.get(i + 1).getWidth() ? bitmap.get(i).getWidth() : bitmap.get(i + 1).getWidth();
	}
	h += bitmap.get(i).getHeight();
}

Bitmap temp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
Canvas canvas = new Canvas(temp);
int top = 0;
for (int i = 0; i < bitmap.size(); i++) {
Log.d("HTML", "Combine: "+i+"/"+bitmap.size()+1);

top = (i == 0 ? 0 : top+bitmap.get(i).getHeight());
canvas.drawBitmap(bitmap.get(i), 0f, top, null);
}
return temp;
}

