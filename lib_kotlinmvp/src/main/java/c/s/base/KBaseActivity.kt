package c.s.base

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.lang.ref.WeakReference
import java.util.ArrayList

abstract class KBaseActivity : AppCompatActivity() {

    var arrayList: ArrayList<Activity>? = null
    var mActivity: Activity? = null
    var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeCreate()
        super.onCreate(savedInstanceState)

        mActivity = this
        mContext = this

        var weak: WeakReference<Activity> = WeakReference<Activity>(this)
        arrayList = ArrayList()

        arrayList!!.add(weak!!.get()!!)
        onCreateFrame(savedInstanceState)
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onAttachedToWindowFrame()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onDetachedFromWindowFrame()
    }

    override fun onDestroy() {
        arrayList!!.remove(this)
        super.onDestroy()
        onDestoryFrame()

    }


    abstract fun onDetachedFromWindowFrame()

    abstract fun onAttachedToWindowFrame()


    open fun beforeCreate() {

    }

    open fun onCreateFrame(savedInstanceState: Bundle?) {

    }

    open fun onDestoryFrame() {
    }

}
