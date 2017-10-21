/*
 *    Copyright 2017 zhangqinglian
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.zqlite.android.cyaneus.base.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver

/**
 * Created by scott on 2017/10/21.
 */
class AutoWriteView:View {

    private var W:Int = 0
    private var H:Int = 0

    private var paint:TextPaint = TextPaint(TextPaint.ANTI_ALIAS_FLAG)
    private var linePaint:Paint = TextPaint(TextPaint.ANTI_ALIAS_FLAG)

    private var text : String = ""

    private var counter = 0
    private val mHandler = Handler(Looper.getMainLooper())

    private val fps = 30

    private var stop = true
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(text.trim().isEmpty()) return
        if(counter == Int.MAX_VALUE){
            counter = 0
        }
        ++counter




        var text2Draw  = ""

        val d = 15*text.length
        val din4 = d/4
        val delta = din4/text.length
        val s = counter % d
        if(s>=0 && s<d/2){
            text2Draw = text
        }
        if(s>=d/2&&s<d*3/4){
            val y = s - d/2
            var sub = y/delta
            if(sub>text.length) sub = text.length
            Log.d("scott","111 = " + y + "   sub = " + sub)
            text2Draw = text.substring(0,text.length-sub)
        }
        if(s>=d*3/4){
            val z = s - d*3/4
            var sub = z/delta
            if(sub > text.length) sub = text.length
            text2Draw = text.substring(0,sub)
        }
        val length = paint.measureText(text2Draw)
        val x = (W - length)/2
        val lineX = W/2+length/2

        canvas!!.drawText(text2Draw,x,H.toFloat()-20,paint)


        val a = counter % 30
        if(a > 15){
            canvas!!.drawLine(lineX.toFloat(),(H-20-paint.textSize),lineX.toFloat(),(H-10).toFloat(),linePaint)
        }
    }

    private fun init(){

        paint.color = Color.WHITE
        paint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16F,context.resources.displayMetrics)
        linePaint.color = Color.WHITE
        linePaint.strokeWidth = 3F

        viewTreeObserver.addOnPreDrawListener(object :ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                W = width
                H = height
                viewTreeObserver.removeOnPreDrawListener(this)
                return false
            }
        })
    }

    public fun startWrite(text:String){
        this.text = text

        stop = false

        mHandler.postDelayed(object :Runnable{
            override fun run() {
                if(stop) return
                invalidate()
                mHandler.postDelayed(this,1000L/fps)
            }
        },1000L/fps)

    }

    public fun stopWrite(){
        stop = true
    }
}