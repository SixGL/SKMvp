package c.s.mvp

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.widget.Toast
import c.s.base.KBasePresenter

class KMainP<V> : KBasePresenter<V>() {



    var modle: KMianModle

    init {
        modle = KMianModle()
    }

    val mainLiveData: MutableLiveData<String> = MutableLiveData()

    fun getMainLiveData(): LiveData<String> {
        return mainLiveData
    }

    fun mvp(c: Context) {
        mainLiveData.value="LiveData数据"
    }

}