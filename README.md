### 引用方式
#### 注意
因为库中时直接引用的项目的kotlin（目的是为了跟项目的Kotlin版本一致）
```
implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version
```
所以在项目gradle中需要支持kotlin：
```
ext.kotlin_version = '1.3.31'  // 根据自己需求选择版本
    repositories {
        google()
        jcenter()
    }
```

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
``` 
```
dependencies {
	         implementation 'com.github.SixGL:SKMvp:v0.1.01'
	}
```
### 简介
项目已经默认集成谷歌的Lifecycle、LiveData生命周期管理框架
```
open abstract class KBasePresenter<V> : ViewModel(),LifecycleObserver {

}
```
LiveData具体使用看下demo

### 代码使用
Activity
```

class MainActivity : KBaseCommonActivity<KMainV,KMainP<KMainV>>(), View.OnClickListener {


    override fun prologic() {
    // 设置点击事件，这里可以传入多个控件    setViewClick(this,mvp,a,b)也是可以的
        setViewClick(this,mvp)
        // livedata
        p?.getMainLiveData()?.observe(this, Observer<String>{data->
        // 利用LiveData回传的数据
            Toast.makeText(mActivity, data, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onCreatPresenter(): KMainP<KMainV> {
        // ViewModelProviders.of 使用LiveData
        return ViewModelProviders.of(this).get(KMainP<KMainV>()::class.java)
    }

    override fun getLayout(): Int {
       return R.layout.activity_main
    }


    override fun onClick(v: View?) {
        p?.mvp(this)
    }

}
```
Presenter
```

class KMainP<V> : KBasePresenter<V>() {



    var modle: KMianModle

    init {
    // 真正的网络请求放到 Modle里
        modle = KMianModle()
    }

    val mainLiveData: MutableLiveData<String> = MutableLiveData()

    fun getMainLiveData(): LiveData<String> {
        return mainLiveData
    }

    fun mvp(c: Context) {
    // mainLiveData.postValue("LiveData数据")
    // 这里由于使用kotlin的原因，所以直接是value  java方式应该setValue
        mainLiveData.value="LiveData数据"
    }

}
```
fragment使用方式一样的

### Presenter构造方法传参
具体可以看下谷歌的LiveData使用方式
