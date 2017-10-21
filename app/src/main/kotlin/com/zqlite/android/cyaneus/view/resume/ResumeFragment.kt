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

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.zqlite.android.cyaneus.R
import com.zqlite.android.cyaneus.base.BaseFragment
import com.zqlite.android.cyaneus.entity.Owner
import kotlinx.android.synthetic.main.fragment_resume.*
/**
 * Created by scott on 2017/10/21.
 */
class ResumeFragment : BaseFragment() {

    private var resumeModel:ResumeModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resumeModel = ViewModelProviders.of(this).get(ResumeModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_resume,container,false)
    }

    override fun initView() {
        super.initView()
    }

    override fun initData() {
        resumeModel!!.loadOwner()
        resumeModel!!.owner.observe(this,object :Observer<Owner>{
            override fun onChanged(t: Owner?) {
                if(t != null){
                    Picasso.with(context).load(Uri.parse(t.avatarUrl)).placeholder(R.mipmap.ic_launcher).into(avatar)
                    auto_write_text.startWrite(t.bio)
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object Factory{
        fun getInstance(args:Bundle?):ResumeFragment{
            val resumeFragment = ResumeFragment()
            if(args != null){
                resumeFragment.arguments = args
            }
            return resumeFragment
        }
    }

    inner class ResumeAdapter : RecyclerView.Adapter<ResumeHolder>(){
        override fun onBindViewHolder(holder: ResumeHolder?, position: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getItemCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ResumeHolder {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    inner class ResumeHolder(view:View) : RecyclerView.ViewHolder(view){

    }
}