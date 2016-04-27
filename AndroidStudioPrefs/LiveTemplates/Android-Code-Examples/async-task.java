// ********************************************** Async Task
@Override{
    protected void onCreate(Bundle savedInstancedState){
        super.onCreate(savedInstancedState);
        setContentView(your main layout id);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new YourAsync().execute("your url or any string here");
            }
        });

    }
}

class YourAsync extends AsyncTask <String, Integer, String> {
    // onPreExecute()
    
    protected String doInBackground(String ... args) {
        String html = "";
        // connect data source
        return html;
    }
    protected void onProgressUpdate(Integer ...a) {

    }
    protected void onPostExecute(String result) {

    }
}
