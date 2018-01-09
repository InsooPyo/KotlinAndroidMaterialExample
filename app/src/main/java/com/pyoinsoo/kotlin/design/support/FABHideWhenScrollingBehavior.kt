package com.pyoinsoo.kotlin.design.support

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

/*
 * Created by pyoinsoo on 2018-01-07
 * insoo.pyo@gmail.com
 * RecyclerView Scrolling  FAB hide/show Behavior
 * Float Action Button layout속성에 다음을 추가
 * app:layout_behavior="com.pyoinsoo.kotlin.design.support.FABHideWhenScrollingBehavior"
 */
class FABHideWhenScrollingBehavior: FloatingActionButton.Behavior {

    //constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    /*
     * FloatActionButton의 Dependency View(Current RecyclerView)
     */
    override fun layoutDependsOn(parent: CoordinatorLayout?, child: FloatingActionButton?, dependency: View?)
       = dependency is RecyclerView

    /*
     * FloatActionButton Behavior[스크롤시 보이기/안보이기]
     */
    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout,
                                child: FloatingActionButton,
                                target: View,
                                dxConsumed: Int, dyConsumed: Int,
                                dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        //Down Scroll
        if (child.visibility == View.VISIBLE && dyConsumed > 0) {
            //FloatingActionButton.OnVisibilityChangedListener은 SAM이 아니므로
            //object 객체로 만든다
            child.hide(object: FloatingActionButton.OnVisibilityChangedListener(){
                override fun onHidden(fab: FloatingActionButton?) {
                    super.onHidden(fab)
                    fab?.visibility = View.INVISIBLE
                }
            })
        //Up Scrolling
        } else if (child.visibility == View.INVISIBLE && dyConsumed < 0) {
            child.show()
        }
    }

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout,
                                     child: FloatingActionButton,
                                     directTargetChild: View,
                                     target: View, axes: Int, type: Int): Boolean {
        /*
         * Vertical Scrolling Check(수직인지)
         */
        return type == ViewCompat.TYPE_TOUCH && axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }
}