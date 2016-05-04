public class AndroidTextToSpeechActivity extends Activity implements 
                                                TextToSpeech.OnInitListener {
    
    private TextToSpeech tts;
    private Button btnSpeak;
    private EditText txtText;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        tts = new TextToSpeech(this, this);
 
        btnSpeak = (Button) findViewById(R.id.btnSpeak);
 
        txtText = (EditText) findViewById(R.id.txtText);
 
        btnSpeak.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                speakOut();
            }
 
        });
    }
 
    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
 
    @Override
    public void onInit(int status) {
 
        if (status == TextToSpeech.SUCCESS) {
 
            int result = tts.setLanguage(Locale.US);
 
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btnSpeak.setEnabled(true);
                speakOut();
            }
 
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
 
    }
 
    private void speakOut() {
 
        String text = txtText.getText().toString();
 
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}









/*NOTE:
you can change laguage: 
tts.setLanguage(Locale.CHINESE); // Chinese language




Changing Pitch Rate:
tts.setPitch(0.6); // default is 1.0




Changing spedd:
tts.setSpeechRate(2); // default is 1.0
*/










////////////////////////////////////////////////////////////////////////////////////////////// ANOTHER EXAMPLE
//////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////
public class MainActivity extends Activity {
   TextToSpeech t1;
   EditText ed1;
   Button b1;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      ed1=(EditText)findViewById(R.id.editText);
      b1=(Button)findViewById(R.id.button);
      
      t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
         @Override
         public void onInit(int status) {
            if(status != TextToSpeech.ERROR) {
               t1.setLanguage(Locale.UK);
            }
         }
      });
      
      b1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            String toSpeak = ed1.getText().toString();
            Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
         }
      });
   }
   
   public void onPause(){
      if(t1 !=null){
         t1.stop();
         t1.shutdown();
      }
      super.onPause();
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_main, menu);
      return true;
   }
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      
      int id = item.getItemId();
      
      //noinspection SimplifiableIfStatement
      if (id == R.id.action_settings) {
         return true;
      }
      return super.onOptionsItemSelected(item);
   }
}
