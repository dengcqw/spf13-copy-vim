public class TitleFragment extends Fragment {
    public static TitleFragment newInstance() {
        return new TitleFragment();
    }
    
    /*
     * (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_title, container, false);
        
        ViewUtil.setClickListener(root, new TitleClickListener(this), R.id.button_login, R.id.button_register);
        
        return root;
    }
    
    /**
     * This method is called when user registration is finished. 
     * See {@link RegisterCallback#onRegisterCompleted(int, KiiUser, Exception)}
     */
    void onRegisterFinished() {
        FragmentActivity activity = getActivity();
        if (activity == null) { return; }
        ViewUtil.showToast(activity, "Register succeeded");
        // to next fragment
        onLoginFinished();
    }
    
    /**
     * This method is called when login is finished.
     * See {@link LoginCallback#onLoginCompleted(int, KiiUser, Exception)}
     */
    void onLoginFinished() {
        FragmentActivity activity = getActivity();
        if (activity == null) { return; }
        // store access token
        KiiUser user = KiiUser.getCurrentUser();
        String token = user.getAccessToken();
        Pref.setStoredAccessToken(activity, token);
        
        ViewUtil.toNextFragment(getFragmentManager(), BalanceListFragment.newInstance(), false);
    }
}
