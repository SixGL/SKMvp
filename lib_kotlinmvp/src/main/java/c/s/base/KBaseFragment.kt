package c.s.base

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class KBaseFragment<V, P : KBasePresenter<V>> : Fragment() {

    var mActivity: Activity? = null
    var mContext: Context? = null
    var p: P? = null
    var isViewInitiated: Boolean = false
    var isVisibleToUser: Boolean = false
    var isDataInitiated: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        p = onCreatePresenter()
        p!!.onAtteachView(this as V)
        lifecycle.addObserver(p!!)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        this.mContext = getContext()
        this.mActivity = activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLatyout(), container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        proLogic()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        // 第一次打开V+f 时，setUserVisibleHint先与onViewCreated和onActivityCreated执行 这里调用避免第一次打开第一个fragment lazyLoadData不会回调
        prepareGetData(true)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        prepareGetData()
    }

    fun prepareGetData(): Boolean {
        return prepareGetData(false)
    }

    fun prepareGetData(forceUpdate: Boolean): Boolean {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            lazyLoadData()
            isDataInitiated = true
            return true
        }
        return false
    }


    abstract fun onCreatePresenter(): P

    abstract fun getLatyout(): Int

    abstract fun proLogic()

    abstract fun lazyLoadData()


    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(p!!)
    }


    // vararg 可变参数修饰
    fun setViewClick(listener: View.OnClickListener, vararg views: View) {
        for (it in views) {
            it.setOnClickListener(listener)
        }
    }

    /**
     * 动态的设置状态栏  实现沉浸式状态栏
     *
     * @param tabBar
     */
    fun initState(tabBar: ConstraintLayout) {
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            //透明状态栏
//            activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //透明导航栏
            //            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tabBar.visibility = View.VISIBLE
            //获取到状态栏的高度
            val statusHeight = getStatusBarHeight(activity)
            //动态的设置隐藏布局的高度
            val params = tabBar.layoutParams as ConstraintLayout.LayoutParams
            params.height = statusHeight
            tabBar.layoutParams = params
        }
    }

    /**
     * 通过反射的方式获取状态栏高度
     *
     * @return
     */
    fun getStatusBarHeight(context: Context?): Int {
        try {
            val c = Class.forName("com.android.internal.R\$dimen")
            val obj = c.newInstance()
            val field = c.getField("status_bar_height")
            val x = Integer.parseInt(field.get(obj).toString())
            return context!!.resources.getDimensionPixelSize(x)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0
    }


}