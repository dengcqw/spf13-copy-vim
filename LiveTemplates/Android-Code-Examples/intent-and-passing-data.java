

// ********************************************** open new activity via intent
Intent intent = new Intent(context, second.class);
startActivity(intent);







// ********************************************** passing data between two intent
//send
Intent intent = new Intent(context, second.class);
intent.putExtra("name", stringValueHere);
startActivity(intent);
//get
Bundle bundle = new getIntent().getExtras();
if(bundle != null){
	stringValueHere = bundle.getString("name");
}








// Passsing an Integer array via Intent
Intent i = new Intent(A.this, B.class);
i.putExtra("numbers", array);
startActivity(i);

Bundle extras = getIntent().getExtras();
int[] arrayB = extras.getIntArray("numbers");







// Passsing an OBJECT via Intent
ClassName details = new ClassName();
Intent i = new Intent(context, EditActivity.class);
i.putExtra("Editing", details);
startActivity(i);


//receive
ClassName model = (ClassName) getIntent().getSerializableExtra("Editing");

And 

Class ClassName implements Serializable {
} 









// ********************************************** open your camera
Intent camaraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
startActivityForResult(camaraIntent, CAMERA_REQUEST);
// return your Bitmap
protected void onActivityResult(int requestCode, int resultCode, Intent data){
	if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
		Bitmap photo = (Bitmap) data.getExtras().get("data");
		img = (ImageView) findViewById(yourImageId);
		img.setImageBitmap(photo);
	}
}
