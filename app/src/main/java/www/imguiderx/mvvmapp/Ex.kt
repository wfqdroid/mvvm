package www.imguiderx.mvvmapp

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.View
import android.widget.Toast
import java.util.regex.Pattern

/**
 * toast扩展函数，便于调用toast方法
 */
fun Context.toast(message: Any, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message.toString(), duration).show()
}

/**
 * 给View扩展设置圆角的方法
 */

fun View.setRadiusDrawable(color: Int, radius: Int){
    val drawable = GradientDrawable()
    drawable.setColor(color)
    drawable.cornerRadius = radius.toFloat()
    this.background = drawable
}

/**
 * 给String扩展邮箱的政策
 */
fun String.checkEmail(): Boolean {
    val emailPattern = "[a-zA-Z0-9][a-zA-Z0-9._-]{2,16}[a-zA-Z0-9]@[a-zA-Z0-9]+.[a-zA-Z0-9]+"
    return Pattern.matches(emailPattern, this)
}
fun String.parseColor():Int{
    return Color.parseColor(this)
}

/**
 * log拓展函数
 */
fun log(message: Any) {
    if (BuildConfig.DEBUG) {
        Log.i("WFQ_", String.format("-%n%s%n", message))
    }
}

fun loge(message: Any) {
    if (BuildConfig.DEBUG) {
        Log.e("WFQ:--->", message.toString())
    }
}

/**
 * 点击事件
 */
fun <T : View> T.click(block: (T) -> Unit) = setOnClickListener {

    if (clickEnable()) {
        block(it as T)
    }
}

private var <T : View> T.triggerLastTime: Long
    get() = if (getTag(1123460103) != null) getTag(1123460103) as Long else 0
    set(value) {
        setTag(1123460103, value)
    }

private var <T : View> T.triggerDelay: Long
    get() = if (getTag(1123461123) != null) getTag(1123461123) as Long else 0
    set(value) {
        setTag(1123461123, value)
    }

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        flag = true
    }
    triggerLastTime = currentClickTime
    return flag
}

