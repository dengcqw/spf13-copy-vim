//********************************************** Download image from internet
URL url = new URL(link text);
Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
img.setImageBitmap(bmp);






// **********************************************  read text / html from internet
private class GetXML extends AsyncTask <String, Void, String>{
	@Override
	protected String doInBackground(String ...urls){
		String xml = null;
		xml = getXmlFromUrl("http://....");
		return xml;
	}
	protected void onPostExecute(String xml){
		XMLDOMParser parser = new XMLDOMParser();
		Document doc = parser.getDocument(xml);
		NodeList nodeList = doc.getElementsByTagName("tag name");

		String result = "";
		for(int i=0; i<nodeList.getLength(); i++){
			Element e = (Element) nodeList.item(i);
			result += parser.getValue(e, "name");
		}
		Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
	}
}









// play mp3 from internet
public void PlayMp3FromInternet(String url){
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }








// ********************************************** XMLDOMPARSER
// step 1: create class
public class XMLDOMParser {
    public Document getDocument(String xml)
    { 
        Document document = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder db = factory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            is.setEncoding("UTF-8");
            document = db.parse(is);
        }catch(ParserConfigurationException e)
        {
            Log.e("Error: ", e.getMessage(), e);
            return null;
        }
        catch (SAXException e) {
            Log.e("Error: ", e.getMessage(), e);
            return null;
        }
        catch(IOException e){
            Log.e("Error: ", e.getMessage(), e);
            return null;
        }
        return document;
    }
    public String getValue(Element item, String name)
    {
        NodeList nodes = item.getElementsByTagName(name);
        return this.getTextNodeValue(nodes.item(0));
    }
    private final String getTextNodeValue(Node elem) {
        Node child;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
                    if( child.getNodeType() == Node.TEXT_NODE  ){
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
}

// step 2: MainActivity
//      2.1: create method getXmlFromUrl(String urlString)
private String getXmlFromUrl(String urlString) {
        String xml = null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(urlString);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml;
    }

//	    2.2: create Async
private class GetXMLTask extends AsyncTask <String, Void, String> {
	@Override
	protected String doInBackground(String ...urls){
		String xml = null;
		xml = getXmlFromUrl("your url here");
		return xml;
	}
	protected void onPostExecute(String xml){
		XMLDOMParser parser = new XMLDOMParser();
		Document doc = parser.getDocument(xml);
		NodeList nodeList = doc.getElementsByTagName("your tag name");

		String result = "";
		for(int i=0; i<nodeList.getLength(); i++){
			Element e = (Element) nodeList.item(i);
			result += parser.getValue(e, "name");
		}
		// Use "result"
	}
}
//	    2.3: onCreate:
runOnUiThread(new Runnable(){
	@Override
	public void run(){
		new GetXMLTask().execute("http url...");
	}
})















// ********************************************** send a request to web server
// use async!
private String makePostRequest() {
    HttpClient httpClient = new DefaultHttpClient();
    
    // URL of request reciever
    HttpPost httpPost = new HttpPost("http:... reciever");
    
    // params
    List nameValuePair = new ArrayList(2);
    nameValuePair.add(new BasicNameValuePair("number1", "111"));
    nameValuePair.add(new BasicNameValuePair("number2", "222"));
    
    //Encoding POST data
    try {
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }

    String result = "";
    try {
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        result = EntityUtils.toString(entity);
    } catch (ClientProtocolException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

    return result;
}













// ********************************************** read content from an URL from internet
private static String readFromURL(String theUrl)
{
    StringBuilder content = new StringBuilder();

    // many of these calls can throw exceptions, so i've just
    // wrapped them all in one try/catch statement.
    try
    {
        // create a url object
        URL url = new URL(theUrl);

        // create a urlconnection object
        URLConnection urlConnection = url.openConnection();

        // wrap the urlconnection in a bufferedreader
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String line;

        // read from the urlconnection via the bufferedreader
        while ((line = bufferedReader.readLine()) != null)
        {
            content.append(line + "\n");
        }
        bufferedReader.close();
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
    return content.toString();
}















// ********************************************** CHECK IF INTERNET IS AVAILABLE
private boolean haveInternet(){
	NetworkInfo info = ((ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
	if (info==null || !info.isConnected()) {
		return false;
	}
	if (info.isRoaming()) {
	// here is the roaming option you can change it if you want to disable internet while roaming, just return false
		return true;
	}
	return true;
}









// **********************************************Webview: Using url from internet or local - remember Internet permission in android manifest
wb = (WebView) findViewById(YourWebiewId);
wv.loadUrl("your url here");










// ********************************************** scan for wireless networks
public class WifiTester extends Activity {
TextView mainText;
WifiManager mainWifi;
WifiReceiver receiverWifi;
List<ScanResult> wifiList;
StringBuilder sb = new StringBuilder();
 
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    mainText = (TextView) findViewById(R.id.mainText);
    mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
    receiverWifi = new WifiReceiver();
    registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    mainWifi.startScan();
    mainText.setText("\\nStarting Scan...\\n");
}
 
public boolean onCreateOptionsMenu(Menu menu) {
    menu.add(0, 0, 0, "Refresh");
    return super.onCreateOptionsMenu(menu);
}
 
public boolean onMenuItemSelected(int featureId, MenuItem item) {
    mainWifi.startScan();
    mainText.setText("Starting Scan");
    return super.onMenuItemSelected(featureId, item);
}
 
protected void onPause() {
    unregisterReceiver(receiverWifi);
    super.onPause();
}
 
protected void onResume() {
    registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    super.onResume();
}
 
class WifiReceiver extends BroadcastReceiver {
    public void onReceive(Context c, Intent intent) {
        sb = new StringBuilder();
        wifiList = mainWifi.getScanResults();
        for(int i = 0; i < wifiList.size(); i++){
            sb.append(new Integer(i+1).toString() + ".");
            sb.append((wifiList.get(i)).toString());
            sb.append("\\n");
        }
        mainText.setText(sb);
        }
    }
}
