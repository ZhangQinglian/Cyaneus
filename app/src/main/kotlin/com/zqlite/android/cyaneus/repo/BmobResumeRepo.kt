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

package com.zqlite.android.cyaneus.repo

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import com.zqlite.android.cyaneus.entity.Owner
import com.zqlite.android.cyaneus.entity.OwnerCareer
import com.zqlite.android.cyaneus.entity.OwnerContact
import com.zqlite.android.cyaneus.entity.OwnerTag
import com.zqlite.android.cyaneus.view.resume.ResumeCareer
import com.zqlite.android.cyaneus.view.resume.ResumeContact
import com.zqlite.android.cyaneus.view.resume.ResumeData
import com.zqlite.android.cyaneus.view.resume.ResumeIntro
import java.util.*

/**
 * Created by scott on 2017/10/22.
 */
class BmobResumeRepo : IResumeRepo,Observable() {

    private var resumeData : ResumeData = ResumeData()
    override fun getResumeData(): Observable {
        val query = BmobQuery<Owner>()
        query.getObject("9bc829e75d",object : QueryListener<Owner>(){
            override fun done(p0: Owner?, p1: BmobException?) {
                if(p0 != null){
                    resumeData.owner = p0
                    tryNotify()
                }
            }

        })

        val queryIntro = BmobQuery<OwnerTag>()
        queryIntro.findObjects(object : FindListener<OwnerTag>(){
            override fun done(p0: MutableList<OwnerTag>?, p1: BmobException?) {
                if(p1 == null && p0 != null && p0.size>0){
                    val resumeIntro = ResumeIntro(p0)
                    resumeData.resumeIntro = resumeIntro
                    tryNotify()
                }
            }

        })

        val queryCareer = BmobQuery<OwnerCareer>()
        queryCareer.findObjects(object : FindListener<OwnerCareer>(){
            override fun done(p0: MutableList<OwnerCareer>?, p1: BmobException?) {
                if(p1==null&&p0!=null&&p0.size>0){
                    val resumeCareer = ResumeCareer(p0)
                    resumeData.resumeCareer = resumeCareer
                    tryNotify()
                }
            }

        })

        val queryContact = BmobQuery<OwnerContact>()
        queryContact.findObjects(object :FindListener<OwnerContact>(){
            override fun done(p0: MutableList<OwnerContact>?, p1: BmobException?) {
                if(p1 == null && p0 != null && p0.size > 0){
                    val resumeContact = ResumeContact(p0[0])
                    resumeData.resumeContact = resumeContact
                    tryNotify()
                }
            }

        })
        return this
    }

    private fun tryNotify(){
        if(resumeData.isReady()){
            setChanged()
            notifyObservers(resumeData)
        }
    }
}