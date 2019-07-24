package c.s.base

import android.arch.lifecycle.*
import android.util.Log
import java.lang.ref.WeakReference

open abstract class KBasePresenter<V> : ViewModel(),LifecycleObserver {

    var mView: WeakReference<V>? = null
    val TAG: String? = "Kotlin Life "

    fun onAtteachView(v: V) {
        mView = WeakReference(v)
    }

    fun onDetachView(v: V) {
        mView!!.clear()
        mView = null
    }

    open fun getView(): V ? {
        return mView?.get()
    }


//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    fun onCreate(owner: LifecycleOwner) {
//        Log.i(TAG, "$TAG +  onCreate")
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    fun onStart(owner: LifecycleOwner) {
//        Log.i(TAG, "$TAG +  onCreate")
//    }
//
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(owner: LifecycleOwner) {
        Log.i(TAG, "$TAG +  onResume")
    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//    fun onPause(owner: LifecycleOwner) {
//        Log.i(TAG, "$TAG +  onPause")
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//    fun onStop(owner: LifecycleOwner) {
//        Log.i(TAG, "$TAG +  onStop")
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
//    fun onAny(owner: LifecycleOwner) {
//        Log.i(TAG, "$TAG +  onAny")
//    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory(owner: LifecycleOwner) {
        Log.i(TAG, "$TAG +  onDestory")
    }


}