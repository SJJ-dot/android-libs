package sjj.serialize

import android.arch.lifecycle.MutableLiveData
import android.content.SharedPreferences
import android.os.Looper
import sjj.fiction.util.deSerialize
import sjj.fiction.util.serialize
import kotlin.reflect.KProperty


class SharedPreferencesDelegate<T>(private val def: T?, val sp: () -> SharedPreferences) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        val sp = sp()
        return when (property.returnType.classifier) {
            String::class -> sp.getString(property.name, def as String?)
            Boolean::class -> sp.getBoolean(property.name, def as Boolean)
            Float::class -> sp.getFloat(property.name, def as Float)
            Int::class -> sp.getInt(property.name, def as Int)
            Long::class -> sp.getLong(property.name, def as Long)
            else -> sp.getString(property.name, def.serialize()).toAny(def)
        } as T
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        val edit = sp().edit()
        when (property.returnType.classifier) {
            String::class -> edit.putString(property.name, value as String?)
            Boolean::class -> edit.putBoolean(property.name, value as Boolean)
            Float::class -> edit.putFloat(property.name, value as Float)
            Int::class -> edit.putInt(property.name, value as Int)
            Long::class -> edit.putLong(property.name, value as Long)
            else -> edit.putString(property.name, value.serialize())
        }
        edit.apply()
    }
}

class SharedPreferencesLiveData<T>(val def: T?, val sp: () -> SharedPreferences) {
    private var liveData: MutableLiveData<T>? = null
    operator fun getValue(thisRef: Any?, property: KProperty<*>): MutableLiveData<T> {
        if (liveData == null) {
            liveData = object : MutableLiveData<T>() {
                override fun setValue(value: T) {
                    val edit = sp().edit()
                    when (property.returnType.arguments[0].type?.classifier) {
                        String::class -> edit.putString(property.name, value as String?)
                        Boolean::class -> edit.putBoolean(property.name, value as Boolean)
                        Float::class -> edit.putFloat(property.name, value as Float)
                        Int::class -> edit.putInt(property.name, value as Int)
                        Long::class -> edit.putLong(property.name, value as Long)
                        else -> edit.putString(property.name, value.serialize())
                    }
                    edit.apply()
                    if (Thread.currentThread() == Looper.getMainLooper().thread) {
                        super.setValue(value)
                    } else {
                        super.postValue(value)
                    }
                }
            }
            liveData?.value = when (property.returnType.arguments[0].type?.classifier) {
                String::class -> sp().getString(property.name, def as String?)
                Boolean::class -> sp().getBoolean(property.name, def as Boolean)
                Float::class -> sp().getFloat(property.name, def as Float)
                Int::class -> sp().getInt(property.name, def as Int)
                Long::class -> sp().getLong(property.name, def as Long)
                else -> sp().getString(property.name, def.serialize()).toAny(def)

            } as T
        }
        return liveData!!
    }
}

private fun < T>String.toAny(def: T?):T? {
   return try {
        deSerialize<T>()
    } catch (e: Throwable) {
        def
    }
}