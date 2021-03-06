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

package com.zqlite.android.cyaneus.view.resume

import com.zqlite.android.cyaneus.R
import com.zqlite.android.cyaneus.base.BaseActivity
/**
 * Created by scott on 2017/10/21.
 */
class ResumeActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_resume
    }

    override fun initView() {

        val resumeFragment = ResumeFragment.getInstance(null)
        addFragment(resumeFragment,R.id.resume_container)
    }
}