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

package com.zqlite.android.cyaneus

import android.content.Intent
import android.view.MenuItem
import com.zqlite.android.cyaneus.base.BaseActivity
import com.zqlite.android.cyaneus.view.resume.ResumeActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer.*

class MainActivity : BaseActivity() {


    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {


        //Bottom menu
        bottom_menu.setOnNavigationItemSelectedListener {
            item: MenuItem ->
            when(item.itemId){
                R.id.navigation_home-> goHome()
                R.id.navigation_resume-> goResume()
                else->true
            }
        }

        jump_resume.setOnClickListener {
            view ->
               val intent = Intent(this@MainActivity,ResumeActivity::class.java)
               startActivity(intent)

        }
    }

    private fun goHome():Boolean{

        return true
    }
    private fun goResume():Boolean{
        return true
    }
}
