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
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.zqlite.android.cyaneus.R
import com.zqlite.android.cyaneus.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_resume.*
import com.zqlite.android.cyaneus.MainActivity



/**
 * Created by scott on 2017/10/21.
 */
class ResumeFragment : BaseFragment() {

    private var resumeModel:ResumeModel? = null

    private var adapter:ResumeAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resumeModel = ViewModelProviders.of(this).get(ResumeModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_resume,container,false)
    }

    override fun initView() {
        super.initView()
        val linearLayout = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter = ResumeAdapter()
        resume_list.layoutManager = linearLayout
        resume_list.adapter = adapter
        resume_list.itemAnimator = DefaultItemAnimator()
    }

    override fun initData() {

        resumeModel!!.loadOwner().observe(this,object :Observer<ResumeData>{
            override fun onChanged(t: ResumeData?) {
                if(t!=null){
                    Picasso.with(context).load(Uri.parse(t.owner!!.avatarUrl)).into(avatar)
                    auto_write_text.startWrite(t.owner!!.bio)
                    adapter!!.add(t.resumeIntro!!)
                    adapter!!.add(t.resumeCareer!!)
                    adapter!!.add(t.resumeContact!!)
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

        private var resumeList:MutableList<ResumeItem> = mutableListOf()

        fun updateAll(data:List<ResumeItem>){
            resumeList.clear()
            resumeList.addAll(data)
            notifyDataSetChanged()
        }

        fun add(data:ResumeItem){
            resumeList.add(data)
            notifyItemInserted(resumeList.size-1)
        }
        override fun onBindViewHolder(holder: ResumeHolder?, position: Int) {
            holder!!.bind(resumeList[position])
        }

        override fun getItemCount(): Int {
            return resumeList.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ResumeHolder? {
            val inflator = LayoutInflater.from(context)
            when(viewType){
                ResumeItem.INTRO-> return ResumeIntroHolder(inflator.inflate(R.layout.listitem_resume_intro,parent!!,false))
                ResumeItem.CAREER -> return ResumeCareerHolder(inflator.inflate(R.layout.listitem_resume_career,parent!!,false))
                ResumeItem.CONTACT -> return ResumeContactHolder(inflator.inflate(R.layout.listitem_resume_contact,parent!!,false))
                else-> return null
            }
        }

        override fun getItemViewType(position: Int): Int {
            return resumeList[position].getType()
        }
    }

    inner abstract class ResumeHolder(view:View) : RecyclerView.ViewHolder(view){

        abstract fun bind(resumeItem: ResumeItem)
    }

    inner class ResumeIntroHolder(view:View):ResumeHolder(view){

        private val tagsText = view.findViewById<TextView>(R.id.tags_container)

        override fun bind(resumeItem: ResumeItem) {
            if(resumeItem is ResumeIntro){
                val resumeIntro = resumeItem
                val sb = StringBuilder()
                for(tag in resumeIntro.tags){
                    sb.append("#${tag.tag}#")
                    if(tag.tag.length>=0){
                        sb.append("\n")
                    }
                }
                val ssb = SpannableStringBuilder(sb.toString())
                for(tag in resumeIntro.tags){
                    val start = ssb.indexOf("#${tag.tag}#")
                    val end = start + tag.tag.length+2
                    val clickableSpnan = object : ClickableSpan(){
                        override fun onClick(p0: View?) {
                            if(tag.url != null && tag.url.isNotEmpty()){
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data = Uri.parse(tag.url)
                                startActivity(intent)
                            }
                        }

                    }
                    ssb.setSpan(clickableSpnan,start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                tagsText.movementMethod = LinkMovementMethod.getInstance()
                tagsText.text = ssb

            }
        }

    }

    inner class ResumeCareerHolder(view:View):ResumeHolder(view){

        private val careerContainer : LinearLayout = view.findViewById(R.id.career_container)

        override fun bind(resumeItem: ResumeItem) {
            if(resumeItem is ResumeCareer){
                for(career in resumeItem.careers){
                    val careerItem = LayoutInflater.from(context).inflate(R.layout.item_carrer_left,careerContainer,false)
                    val llp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                    careerItem.layoutParams = llp
                    careerContainer.addView(careerItem)
                    careerItem.findViewById<TextView>(R.id.career_time).text = career.start + " - " + career.end
                    careerItem.findViewById<TextView>(R.id.career_company_name).text = career.companyName
                    careerItem.findViewById<TextView>(R.id.career_position).text = career.position
                    Picasso.with(context).load(career.companyAvatar).into(careerItem.findViewById<ImageView>(R.id.company_avatar))
                }
                val careerItem = LayoutInflater.from(context).inflate(R.layout.item_carrer_bottom,careerContainer,false)
                val llp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                careerItem.layoutParams = llp
                careerContainer.addView(careerItem)
                careerItem.findViewById<ImageView>(R.id.company_avatar).setImageResource(R.drawable.carrer_default_ic)
            }
        }

    }

    inner class ResumeContactHolder(view:View):ResumeHolder(view){
        override fun bind(resumeItem: ResumeItem) {
            if(resumeItem is ResumeContact){
                itemView!!.findViewById<TextView>(R.id.contact_container).movementMethod = LinkMovementMethod.getInstance()
                val ssb = SpannableStringBuilder(resumeItem.contact.email)
                val clickableSpnan = object : ClickableSpan(){
                    override fun onClick(p0: View?) {
                        if(resumeItem.contact.email != null && resumeItem.contact.email.isNotEmpty()){
                            val intent = Intent(Intent.ACTION_SENDTO)
                            intent.type = "plain/text"
                            intent.data = Uri.fromParts( "mailto",resumeItem.contact.email, null)
                            intent.putExtra(Intent.EXTRA_EMAIL, resumeItem.contact.email)
                            intent.putExtra(Intent.EXTRA_SUBJECT, "")
                            intent.putExtra(Intent.EXTRA_TEXT, "")
                            startActivity(intent)
                        }
                    }
                }
                ssb.setSpan(clickableSpnan,0,resumeItem.contact.email.length,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                itemView!!.findViewById<TextView>(R.id.contact_container).text = ssb
            }
        }

    }
}