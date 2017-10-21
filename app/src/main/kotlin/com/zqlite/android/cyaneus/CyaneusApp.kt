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

import android.app.Application
import cn.bmob.v3.Bmob


class CyaneusApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initBomb()
    }

    private fun initBomb() {
        Bmob.initialize(this, BuildConfig.APP_ID,"none")
    }
}