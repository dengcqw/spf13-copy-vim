public class SaveImageTask extends AsyncTask<Void, Void, File> implements Constants {

	private static final String PROGRESS_FRAGMENT_TAG = "progress";

	private final File src;
	private final Activity activity;

	public SaveImageTask(final Activity activity, final File src) {
		this.activity = activity;
		this.src = src;
	}

	@Override
	protected File doInBackground(final Void... args) {
		if (src == null) return null;
		return saveImage(activity, src);
	}

	@Override
	protected void onCancelled() {
		final FragmentManager fm = activity.getFragmentManager();
		final DialogFragment fragment = (DialogFragment) fm.findFragmentByTag(PROGRESS_FRAGMENT_TAG);
		if (fragment != null && fragment.isVisible()) {
			fragment.dismiss();
		}
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(final File result) {
		final FragmentManager fm = activity.getFragmentManager();
		final DialogFragment fragment = (DialogFragment) fm.findFragmentByTag(PROGRESS_FRAGMENT_TAG);
		if (fragment != null) {
			fragment.dismiss();
		}
		super.onPostExecute(result);
		if (result != null && result.exists()) {
			Crouton.showText(activity, activity.getString(R.string.file_saved_to, result.getPath()),
					CroutonStyle.CONFIRM);
		} else {
			Crouton.showText(activity, R.string.error_occurred, CroutonStyle.ALERT);
		}
	}

	@Override
	protected void onPreExecute() {
		final DialogFragment fragment = new ProgressDialogFragment();
		fragment.setCancelable(false);
		fragment.show(activity.getFragmentManager(), PROGRESS_FRAGMENT_TAG);
		super.onPreExecute();
	}









	public static File saveImage(final Context context, final File image_file) {
		if (context == null && image_file == null) return null;
		try {
			final String name = image_file.getName();
			if (isEmpty(name)) return null;
			final String mime_type = getImageMimeType(image_file);
			final MimeTypeMap map = MimeTypeMap.getSingleton();
			final String extension = map.getExtensionFromMimeType(mime_type);
			if (extension == null) return null;
			final String name_to_save = name.indexOf(".") != -1 ? name : name + "." + extension;
			final File pub_dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			if (pub_dir == null) return null;
			final File save_dir = new File(pub_dir, "Twidere");
			if (!save_dir.isDirectory() && !save_dir.mkdirs()) return null;
			final File save_file = new File(save_dir, name_to_save);
			FileUtils.copyFile(image_file, save_file);
			if (save_file != null && mime_type != null) {
				MediaScannerConnection.scanFile(context, new String[] { save_file.getPath() },
						new String[] { mime_type }, null);
			}
			return save_file;
		} catch (final IOException e) {
			Log.w(LOGTAG, "Failed to save file", e);
			return null;
		}
	}

}
