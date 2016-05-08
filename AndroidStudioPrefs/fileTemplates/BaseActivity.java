/**
 * Activity基类，用于封装公共操作
 *
 * @author asce1885
 * @version 1.0.0
 * @date 2015.11.25
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置layout布局
        setContentView(initPageLayoutID());

        // 初始化页面控件
        initPageView();

        // 初始化页面控件点击
        initPageViewListener();

        // 业务逻辑处理
        processBusiness(savedInstanceState);
    }

    /**
     * 生成主文件布局ID
     */
    protected abstract int initPageLayoutID();

    /**
     * 初始化页面控件
     */
    protected abstract void initPageView();

    /**
     * 页面控件点击事件处理
     */
    protected abstract void initPageViewListener();

    /**
     * 业务逻辑处理
     *
     * @param savedInstanceState
     */
    protected abstract void processBusiness(Bundle savedInstanceState);

}