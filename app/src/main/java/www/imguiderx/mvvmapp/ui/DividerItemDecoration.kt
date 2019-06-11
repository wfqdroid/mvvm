package www.imguiderx.mvvmapp.ui

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration(val height: Int, val color: String) : RecyclerView.ItemDecoration() {


    var paint = Paint()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(0, 0, 0, height)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        paint.color = Color.parseColor(color)
        //遍历每个itemview，分别获取他们的位置信息  然后绘制对应的分割线
        for (i in 0 until childCount - 1) {
            val childView = parent.getChildAt(i)
            val layoutParams = childView.layoutParams as RecyclerView.LayoutParams
            val left = childView.paddingLeft
            val top = childView.bottom + layoutParams.bottomMargin
            val right = childView.width - childView.paddingRight - layoutParams.rightMargin
            val bottom = top + height
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }
}