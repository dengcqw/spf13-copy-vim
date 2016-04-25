// ********************************************** Touch to get x,y position on the screen
img = (ImageView) findViewById(image id);
img.setOnTouchListener(new View.OnTouchListener(){
    @Override
    public boolean onTouch(View v, MotionEvent event){
        float x = event.getX();
        float y = event.getY();
        String message = String.format("x=%.2f ; y=%.2f", x, y);
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        return false;
    }
});







// ********************************************** set event for item on the listview
listView.setOnItemClickListener(new OnItemClickListener() {
    @Override 
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
        // TODO Auto-generated method stub 
    Toast.makeText(getApplicationContext(), YourArrayHere[position], Toast.LENGTH_SHORT).show(); 
    } 
});







// ********************************************** checkbox
chk = (CheckBox) findViewById(id);
chk.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v){
        if(chk.isChecked() ){

        }
        else{

        }
    }
});










// ********************************************** HANDLE TOUCH EVENTS (ONTOUCHEVENT)
public boolean onTouchEvent(MotionEvent event) {
    int eventaction = event.getAction();
 
    switch (eventaction) {
    case MotionEvent.ACTION_DOWN:
    // finger touches the screen
            break;
 
    case MotionEvent.ACTION_MOVE:
    // finger moves on the screen
            break;
 
    case MotionEvent.ACTION_UP:
    // finger leaves the screen
            break;
    }
 
// tell the system that we handled the event and no further processing is required
return true;
}










// ********************************************** DOUBLE BACK PRESS TO EXIT
private static long back_pressed;
    @Override
    public void onBackPressed(){
        if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
    back_pressed = System.currentTimeMillis();
}
