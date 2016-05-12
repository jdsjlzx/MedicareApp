package com.nuoxin.enterprise;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.loopj.android.http.AsyncHttpClient;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nuoxin.enterprise.api.ApiHttpClient;
import com.nuoxin.enterprise.bean.User;
import com.nuoxin.enterprise.event.UserChangedEvent;
import com.nuoxin.enterprise.util.CyptoUtils;
import com.nuoxin.enterprise.util.TLog;

import java.util.Properties;
import java.util.Stack;

import de.greenrobot.event.EventBus;

/**
 * Created by lizhixian on 16/2/27.
 */
public class AppContext extends Application {
    private static AppContext instance;
    private SharedPreferences sp;

    public User getUser() {
        return user;
    }

    private void setUser(User user) {
        this.user = user;
    }

    private User user;
    private static final String PREFS_NAME = "user_token";

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        AppContext.token = token;
        ApiHttpClient.setTokenHeader(token);
    }

    private boolean isLogin = false;
    private static String token = null;
    // 记录打开的Activity
    public static Stack<Activity> Acts = new Stack<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    private void init() {
        // 初始化网络请求
        AsyncHttpClient client = new AsyncHttpClient();
        ApiHttpClient.setHttpClient(client);

        // Log控制器
        TLog.DEBUG = BuildConfig.DEBUG;
        initImageLoader(this);
        sp = getSharedPreferences(PREFS_NAME, 0);
    }

    public static AppContext getInstance() {
        return instance;
    }

    /**
     * ImageLoader 图片组件初始化
     *
     * @param context
     */
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    public DisplayImageOptions getDefaultOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false) // default
                .delayBeforeLoading(100)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .cacheInMemory(true)
                .cacheOnDisk(true) // default 不缓存至手机SDCard
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
        return options;
    }

    public void setProperties(Properties props) {
        AppConfig.getAppConfig(this).setProps(props);
    }

    public void setProperty(String key, String value) {
        AppConfig.getAppConfig(this).set(key, value);
    }

    public void getProperties() {
        AppConfig.getAppConfig(this).getProperties();
    }

    public String getProperty(String key) {
        return AppConfig.getAppConfig(this).get(key);
    }

    public void removeProperty(String... keys) {
        AppConfig.getAppConfig(this).remove(keys);
    }

    /**
     * 保存token
     */

    public void saveUserToken(String token){
        // 写入配置文件
        SharedPreferences.Editor spEd = sp.edit();
        spEd.putString("mToken", token);
        spEd.commit();
    }

    public String  getUserToken(){
        String mToken = sp.getString("mToken", "");
        return  mToken;
    }

    /**
     * 保存登录信息
     *
     * @param user
     */
    public void saveUserInfo(final User user) {
        this.isLogin = true;
        setUser(user);
        setProperty("user.saleName", user.saleName);
        setProperty("user.email", user.email);
        setProperty("user.pwd", CyptoUtils.encode("custmoerApp", user.password));
        setProperty("user.autoLogin", String.valueOf(true));
        setProperty("user.isFirstOpenApp", "customer");
    }

    /**
     * 更新用户信息
     *
     * @param user
     */
    public void updateUserInfo(final User user) {
        setProperties(new Properties() {
            {
                setProperty("user.pwd", CyptoUtils.encode("custmoerApp", user.password));
            }
        });
    }

    public User getLoginUser() {
        User user = new User();
        user.saleName = getProperty("user.saleName");
        user.email = getProperty("user.email");
        user.password = CyptoUtils.decode("custmoerApp", getProperty("user.pwd"));
        return user;
    }

    /**
     * 清除登录信息
     */
    public void cleanLoginInfo() {
        removeProperty("user.pwd", "user.saleName", "user.email", "user.autoLogin");
    }

    /**
     * 退出登录,清空数据
     */
    public void handleLogout() {
        setToken(null);
        this.isLogin = false;
        this.user = null;
        cleanLoginInfo();
        EventBus.getDefault().post(new UserChangedEvent());
    }

    public static void finishAll() {
        for (Activity activity : Acts) {
            activity.finish();
        }
    }

    public static void addActivity(Activity activity) {
        Acts.add(activity);
    }
}
