package c.s.mvp

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import c.s.base.KBaseCommonActivity

class SecondActivity :  KBaseCommonActivity<KMainV, KMainP<KMainV>>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }


    override fun prologic() {
        Log.e("SecondActivity","${arrayList?.size}")
    }

    override fun onCreatPresenter(): KMainP<KMainV> {
        return ViewModelProviders.of(this).get(KMainP<KMainV>()::class.java)
    }

    override fun getLayout(): Int {
        return R.layout.activity_second
    }

}
