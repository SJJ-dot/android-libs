package sjj.rx

import android.arch.lifecycle.Lifecycle
import io.reactivex.disposables.Disposable

interface AutoDisposeEnhance {

    fun Disposable.destroy(onceKey: String? = null) {
        destroy(onceKey, getLifecycle())
    }

    fun Disposable.stop(onceKey: String? = null) {
        stop(onceKey, getLifecycle())
    }

    fun Disposable.pause(onceKey: String? = null) {
        pause(onceKey, getLifecycle())
    }

    fun getLifecycle():Lifecycle
}
