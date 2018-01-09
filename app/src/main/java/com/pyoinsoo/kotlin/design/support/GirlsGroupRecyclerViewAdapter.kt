package com.pyoinsoo.kotlin.design.support

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.girls_group_recycler_item.view.*

/*
 * Created by pyoinsoo on 2018-01-07
 * 걸그룹에 대한 RecyclerView에 부착할 어댑터 구현
 */
class GirlsGroupRecyclerViewAdapter():RecyclerView.Adapter
              <GirlsGroupRecyclerViewAdapter.ViewHolder>()  {

    private lateinit  var girlsItems: MutableList<Int>
    private var groupName: String = ""
    private lateinit var owner: Activity
    /*
     * 소녀시대/트와이스에 맞게 생성자를 통해 초기화 한다
     */
    constructor(groupName: String, ownerActivity: Activity) : this(){
        this.groupName = groupName
        owner = ownerActivity
        if (groupName.equals("twice", ignoreCase = true)) {
            girlsItems = GirlGroupClassification.twiceMembers
        } else {
            girlsItems = GirlGroupClassification.girlsMembers
        }
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        /*
             Holder의 초기화는 이렇게 해도 된다.(다양한 방법이 존재)

             lateinit var girlsImage: ImageView
             lateinit var  memberName: TextView
             init {
                 with(itemView){
                     girlsImage = itemView.girls_group_member_image
                     memberName = itemView.member_name
                 }
             }
         */

        val girlGroupMemberImage = itemView.girls_group_member_image
        val girlGroupMemberName = itemView.member_name
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val itemView =LayoutInflater.from(parent.context).inflate(R.layout.girls_group_recycler_item, parent, false)
         return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val memberKey = girlsItems[position]
        /*
         * 걸그룹의 이름에 따라 멤버의 이름을 Item에 추가한다
         */
        if(groupName == "twice"){
            holder.girlGroupMemberName.text = GirlGroupClassification.findTwiceMemberName(memberKey)
        }else{
            holder.girlGroupMemberName.text = GirlGroupClassification.findGirlsGenerationMemberName(memberKey)
        }
        GlideApp
                .with(owner)
                .load(memberKey)
                .thumbnail(0.1f)
                .into(holder.girlGroupMemberImage)
        /*
         * RecyclerView Item Event
         */
        holder.itemView.setOnClickListener { _ ->
            /*
             * Item 터치시에 포워딩될 인텐트와 엑스트라값을 정의
             */
            val intent = Intent(
                    GirlsApplication.girlsContext,
                    GirlsMemberDetailActivity::class.java
            ).apply({
                putExtra("groupName", groupName)
                putExtra("memberName", holder.girlGroupMemberName.text.toString())
                putExtra("memberImage", girlsItems[position])
            })
            /*
             * Transition을 실행할 수 있도록 bundle을 구성하여 넘긴다
             */
            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                     owner,holder.girlGroupMemberImage,
                     ViewCompat.getTransitionName(holder.girlGroupMemberImage)
            ).toBundle()

            ActivityCompat.startActivity(owner, intent, bundle)
        }
    }
    override fun getItemCount() = girlsItems.size
    /*
     * Swipe Refresh시 멤버를 추가(랜덤하게 3명) 한다
     */
    fun  additionalInsertionMember(insertMembers:MutableList<Int>){
        girlsItems.addAll(insertMembers)
        notifyDataSetChanged()
    }
}