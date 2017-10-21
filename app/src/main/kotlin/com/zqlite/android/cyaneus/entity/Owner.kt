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

package com.zqlite.android.cyaneus.entity

import cn.bmob.v3.BmobObject
import cn.bmob.v3.BmobUser
import cn.bmob.v3.datatype.BmobDate

/**
 * Created by scott on 2017/10/21.
 */
class Owner(var name:String,
            var nickname:String,
            var email:String,
            var mobilephone:String,
            var birthday:BmobDate,
            var qq:String,
            var wechat:String,
            var weibo:String,
            var github:String,
            var avatarUrl:String,
            var bio:String):BmobObject()