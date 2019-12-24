package c.s.base

import android.content.Context
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.View
import c.s.utils.KStatusBarUtil


/**
 * @param P 限定Presenter必须是继承 KBasePresenter
 * */
abstract class KBaseCommonActivity<V, P : KBasePresenter<V>> : KBaseActivity() {


    var p: P? = null

    /**
     * !! : 非空断言运算符（!!）将任何值转换为非空类型，若该值为空则抛出异常。
     * 安全的类型转换: as   ClassCastException
     * */
    override fun onAttachedToWindowFrame() {
        p = onCreatPresenter()
        lifecycle.addObserver(p!!)
        p!!.onAtteachView(this as V)
        setContentView(getLayout())
        prologic()
    }

    abstract fun prologic()

    abstract fun onCreatPresenter(): P

    override fun onDetachedFromWindowFrame() {
        lifecycle!!.removeObserver(p!!)
        p!!.onDetachView(this as V)
        // 移除Lifecycle
    }

    abstract fun getLayout(): Int

    // vararg 可变参数修饰
    fun setViewClick(listener: View.OnClickListener, vararg views: View) {
        for (it in views) {
            it.setOnClickListener(listener)
        }
    }


    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */ private fun getStatusBarHeight(context: Context?): Int {
        try {
            val c = Class.forName("com.android.internal.R\$dimen")
//            val c = Class.forName("com.android.internal.R.dimen")
            val obj = c.newInstance()
            val field = c.getField("status_bar_height")
            val x = Integer.parseInt(field.get(obj).toString())

            Log.e("状态栏高度", "" + context!!.resources.getDimensionPixelSize(x))
            return context!!.resources.getDimensionPixelSize(x)
        } catch (e: Exception) {
            Log.e("状态栏高度", "Exception")
            e.printStackTrace()
        }

        return 0
    }

    @Deprecated("废弃")
    fun initStatusbar(const: ConstraintLayout) {
        KStatusBarUtil.setStatusBarTranslucent(mActivity, true)
        initState(const)

    }
    fun initStatusbar(const: ConstraintLayout,isLightStatusBar:Boolean) {
        KStatusBarUtil.setStatusBarTranslucent(mActivity, isLightStatusBar)
        initState(const)
    }

    /**
     * 动态的设置状态栏  实现沉浸式状态栏
     *
     * @param tabBar
     */
   private fun initState(tabBar: ConstraintLayout) {
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
//            mActivity!!.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //透明导航栏
            //            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tabBar.visibility = View.VISIBLE
            //获取到状态栏的高度
            val statusHeight = getStatusBarHeight(mActivity)
            Log.i("状态栏高度", "high =" + statusHeight)
            //动态的设置隐藏布局的高度
            val params = tabBar.layoutParams as ConstraintLayout.LayoutParams
            params.height = statusHeight
            tabBar.layoutParams = params
        }
    }

}
