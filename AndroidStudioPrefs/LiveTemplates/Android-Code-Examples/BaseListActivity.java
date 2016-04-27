public abstract class BaseListActivity<V extends View, M extends Model, L extends BaseListAdapter<M>>
		extends BaseActivity<V> implements AdapterView.OnItemClickListener,
		AdapterView.OnItemSelectedListener {

	/**
	 * ListView resource id
	 */
	private final int listViewId;

	/**
	 * ListAdpater class
	 */
	private final Class<L> adapterClass;

	/**
	 * ListAdapter
	 */
	protected L adapter;

	/**
	 * ListView
	 */
	protected ListView listView;

	/**
	 * BaseListActivity
	 * 
	 * @param view
	 *            View clas type
	 * @param adapter
	 *            List adapter class type
	 * @param layout
	 *            layout resource id
	 * @param menu
	 *            menu resource id
	 * @param listView
	 *            list view resource id
	 */
	protected BaseListActivity(Class<V> view, Class<L> adapter, int layout,
			int menu, int listView) {
		super(view, layout, menu);
		this.adapterClass = adapter;
		this.listViewId = listView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (listViewId != 0) {
			listView = findListViewById(listViewId);
			if (headerView() != null) {
				listView.addHeaderView(headerView());
			}
			listView.setOnItemClickListener(this);
			android.view.View emptyView = findViewById(android.R.id.empty);
			if (emptyView != null) {
				listView.setEmptyView(emptyView);
			}

			adapter = Objects.createInstance(adapterClass, Context.class, this);
			listView.setAdapter(adapter);
			listView.setFocusable(true);
		}
	}

	/**
	 * Called after ListAdapter has been loaded
	 * 
	 * @param success
	 *            true is successfully loaded
	 */
	protected abstract void onLoaded(boolean success);

	protected abstract android.view.View headerView();

	@Override
	protected void onResume() {
		super.onResume();
		// new LoadingTask(this).execute((String)null);
	}

	@SuppressWarnings("unchecked")
	protected M getItem(int position) {
		return (M) listView.getItemAtPosition(position);
	}

	@SuppressWarnings("unchecked")
	protected M getSelectedItem() {
		return (M) listView.getSelectedItem();
	}

	public void onItemSelected(AdapterView<?> adapterView,
			android.view.View view, int position, long id) {
	}

	public void onNothingSelected(AdapterView<?> adapterView) {
	}

	/**
	 * ProgressTask sub-class for showing Loading... dialog while the
	 * BaseListAdapter loads the data
	 */
	protected class LoadingTask extends ProgressTask {
		public LoadingTask(FragmentActivity activity) {
			super(activity, R.string.loading_);
		}

		@Override
		protected Boolean doInBackground(String... args) {
			adapter.refresh();
			return true;
		}

		@Override
		protected void onPostExecute(Boolean success) {
			super.onPostExecute(success);
			onLoaded(success);
		}
	}

}
