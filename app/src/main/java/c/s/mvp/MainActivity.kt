package c.s.mvp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.Toast
import c.s.base.KBaseCommonActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : KBaseCommonActivity<KMainV,KMainP<KMainV>>(), View.OnClickListener {


    override fun prologic() {
        setViewClick(this,mvp)
        p?.getMainLiveData()?.observe(this, Observer { data->
            Toast.makeText(this,data,Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreatPresenter(): KMainP<KMainV> {
        return ViewModelProviders.of(this).get(KMainP<KMainV>()::class.java)
    }

    override fun getLayout(): Int {
       return R.layout.activity_main
    }


    override fun onClick(v: View?) {
        p?.mvp(this)
    }

}
