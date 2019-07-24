package c.s.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 20170331
 */
public class KStatusBarUtil {

    // 设置状态栏透明与字体颜色
    public static void setStatusBarTranslucent(Activity acitivty, boolean isLightStatusBar) {
        Window window = acitivty.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        KStatusbarColorUtils.setStatusBarDarkIcon(acitivty,isLightStatusBar);//参数 false 白色 true 黑色
    }

    /**
     * 设置状态栏图片背景
     * @param activity
     * @param statusBarBg
     */
    public static void setStatusBarBg(Activity activity, int statusBarBg) {
        View statusBarView = null;
        Window window = activity.getWindow();
        if (statusBarView == null) {
            //利用反射机制修改状态栏背景
            int identifier = activity.getResources().getIdentifier("statusBarBackground", "id", "android");
            statusBarView = window.findViewById(identifier);
        }
        if (statusBarView != null) {
            statusBarView.setBackgroundResource(statusBarBg);
        }
    }

    /**
     * 动态的设置状态栏  实现沉浸式状态栏
     *
     * @param tabBar
     */
//    public void initState(RelativeLayout tabBar) {
//        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            //透明状态栏
//            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
////            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            tabBar.setVisibility(View.VISIBLE);
//            //获取到状态栏的高度
//            int statusHeight = getStatusBarHeight(getActivity());
//            //动态的设置隐藏布局的高度
//            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tabBar.getLayoutParams();
//            params.height = statusHeight;
//            tabBar.setLayoutParams(params);
//        }
//    }
//
//    /**
//     * 通过反射的方式获取状态栏高度
//     *
//     * @return
//     */
//    public static int getStatusBarHeight(Context context) {
//        try {
//            Class<?> c = Class.forName("com.android.internal.R$dimen");
//            Object obj = c.newInstance();
//            Field field = c.getField("status_bar_height");
//            int x = Integer.parseInt(field.get(obj).toString());
//            return context.getResources().getDimensionPixelSize(x);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
}
