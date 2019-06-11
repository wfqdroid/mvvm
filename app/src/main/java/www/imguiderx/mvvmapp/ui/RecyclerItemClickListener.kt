package www.imguiderx.mvvmapp.ui

import android.content.Context
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import android.view.GestureDetector


class RecyclerItemClickListener(context: Context, recyclerView: RecyclerView, var listener: OnItemClickListener) : RecyclerView.OnItemTouchListener {


        var gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                val child = recyclerView.findChildViewUnder(e.x, e.y)
                if (child != null && listener != null) {
                    listener?.onItemClick(child, recyclerView.getChildAdapterPosition(child))
                }
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val child = recyclerView.findChildViewUnder(e.x, e.y)
                if (child != null && listener != null) {
                    listener?.onItemLongClick(child, recyclerView.getChildAdapterPosition(child))
                }
            }
        })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val childView = rv.findChildViewUnder(e.x, e.y)
        if (childView != null && listener != null && gestureDetector!!.onTouchEvent(e)) {
            listener?.onItemClick(childView, rv.getChildAdapterPosition(childView))
            return true
        }
        return false
    }


    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }


}