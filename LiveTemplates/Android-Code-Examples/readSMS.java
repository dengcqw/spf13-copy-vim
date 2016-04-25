<uses-permission android:name="android.permission.READ_SMS" />



public List<Sms> getAllSms() {
    List<Sms> lstSms = new ArrayList<Sms>();
    Sms objSms = new Sms();
    Uri message = Uri.parse("content://sms/");
    ContentResolver cr = mActivity.getContentResolver();

    Cursor c = cr.query(message, null, null, null, null);
    mActivity.startManagingCursor(c);
    int totalSMS = c.getCount();

    if (c.moveToFirst()) {
        for (int i = 0; i < totalSMS; i++) {

            objSms = new Sms();
            objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
            objSms.setAddress(c.getString(c
                    .getColumnIndexOrThrow("address")));
            objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
            objSms.setReadState(c.getString(c.getColumnIndex("read")));
            objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
            if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                objSms.setFolderName("inbox");
            } else {
                objSms.setFolderName("sent");
            }

            lstSms.add(objSms);
            c.moveToNext();
        }
    }
    // else {
    // throw new RuntimeException("You have no SMS");
    // }
    c.close();

    return lstSms;
}









public class Sms{
private String _id;
private String _address;
private String _msg;
private String _readState; //"0" for have not read sms and "1" for have read sms
private String _time;
private String _folderName;

public String getId(){
return _id;
}
public String getAddress(){
return _address;
}
public String getMsg(){
return _msg;
}
public String getReadState(){
return _readState;
}
public String getTime(){
return _time;
}
public String getFolderName(){
return _folderName;
}


public void setId(String id){
_id = id;
}
public void setAddress(String address){
_address = address;
}
public void setMsg(String msg){
_msg = msg;
}
public void setReadState(String readState){
_readState = readState;
}
public void setTime(String time){
_time = time;
}
public void setFolderName(String folderName){
_folderName = folderName;
}

}






























public class MainActivity extends Activity {
	private static final int TYPE_INCOMING_MESSAGE = 1;
	private ListView messageList;
	private MessageListAdapter messageListAdapter;
	private ArrayList<Message> recordsStored;
	private ArrayList<Message> listInboxMessages;
	private ProgressDialog progressDialogInbox;
	private CustomHandler customHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();

	}

	@Override
	public void onResume() {
		super.onResume();
		populateMessageList();
	}

	private void initViews() {
		customHandler = new CustomHandler(this);
		progressDialogInbox = new ProgressDialog(this);

		recordsStored = new ArrayList<Message>();

		messageList = (ListView) findViewById(R.id.messageList);
		populateMessageList();
	}

	public void populateMessageList() {
		fetchInboxMessages();

		messageListAdapter = new MessageListAdapter(this,
				R.layout.message_list_item, recordsStored);
		messageList.setAdapter(messageListAdapter);
	}

	private void showProgressDialog(String message) {
		progressDialogInbox.setMessage(message);
		progressDialogInbox.setIndeterminate(true);
		progressDialogInbox.setCancelable(true);
		progressDialogInbox.show();
	}

	private void fetchInboxMessages() {
		if (listInboxMessages == null) {
			showProgressDialog("Fetching Inbox Messages...");
			startThread();
		} else {
			// messageType = TYPE_INCOMING_MESSAGE;
			recordsStored = listInboxMessages;
			messageListAdapter.setArrayList(recordsStored);
		}
	}

	public class FetchMessageThread extends Thread {

		public int tag = -1;

		public FetchMessageThread(int tag) {
			this.tag = tag;
		}

		@Override
		public void run() {

			recordsStored = fetchInboxSms(TYPE_INCOMING_MESSAGE);
			listInboxMessages = recordsStored;
			customHandler.sendEmptyMessage(0);

		}

	}

	public ArrayList<Message> fetchInboxSms(int type) {
		ArrayList<Message> smsInbox = new ArrayList<Message>();

		Uri uriSms = Uri.parse("content://sms");
	
		Cursor cursor = this.getContentResolver()
				.query(uriSms,
						new String[] { "_id", "address", "date", "body",
								"type", "read" }, "type=" + type, null,
						"date" + " COLLATE LOCALIZED ASC");
		if (cursor != null) {
			cursor.moveToLast();
			if (cursor.getCount() > 0) {

				do {

					Message message = new Message();
					message.messageNumber = cursor.getString(cursor
							.getColumnIndex("address"));
					message.messageContent = cursor.getString(cursor
							.getColumnIndex("body"));
					smsInbox.add(message);
				} while (cursor.moveToPrevious());
			}
		}

		return smsInbox;

	}

	private FetchMessageThread fetchMessageThread;

	private int currentCount = 0;

	public synchronized void startThread() {

		if (fetchMessageThread == null) {
			fetchMessageThread = new FetchMessageThread(currentCount);
			fetchMessageThread.start();
		}
	}

	public synchronized void stopThread() {
		if (fetchMessageThread != null) {
			Log.i("Cancel thread", "stop thread");
			FetchMessageThread moribund = fetchMessageThread;
			currentCount = fetchMessageThread.tag == 0 ? 1 : 0;
			fetchMessageThread = null;
			moribund.interrupt();
		}
	}

	static class CustomHandler extends Handler {
		private final WeakReference<MainActivity> activityHolder;

		CustomHandler(MainActivity inboxListActivity) {
			activityHolder = new WeakReference<MainActivity>(inboxListActivity);
		}

		@Override
		public void handleMessage(android.os.Message msg) {

			MainActivity inboxListActivity = activityHolder.get();
			if (inboxListActivity.fetchMessageThread != null
					&& inboxListActivity.currentCount == inboxListActivity.fetchMessageThread.tag) {
				Log.i("received result", "received result");
				inboxListActivity.fetchMessageThread = null;
				
				inboxListActivity.messageListAdapter
						.setArrayList(inboxListActivity.recordsStored);
				inboxListActivity.progressDialogInbox.dismiss();
			}
		}
	}

	private OnCancelListener dialogCancelListener = new OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
			stopThread();
		}

	};

}
