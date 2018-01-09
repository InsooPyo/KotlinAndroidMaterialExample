package com.pyoinsoo.kotlin.design.support

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_girls_member_detail.*

/*
 * Created by pyoinsoo on 2017-12-18.
 * insoo.pyo@gmail.com
 *
 * 걸그룹멤버의 상세정보
 */
class GirlsMemberDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_girls_member_detail)


        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            with(GirlGroupClassification.getExplodeInstance()){
                duration = 200
                window.exitTransition = this
                window.enterTransition = this
            }
        }
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        with(intent){
            collapsingToolbar.title = getStringExtra("memberName")
            if (getStringExtra("groupName").equals("twice", ignoreCase = true)) {
                girls_title.text = resources.getString(R.string.twice_title)
                girls_result.text = resources.getString(R.string.twice_result)
                girls_strategy.text = resources.getString(R.string.twice_sta)
                girls_popul.text = resources.getString(R.string.twice_popularity)
            }else{
                girls_title.text = resources.getString(R.string.girl_generation_title)
                girls_result.text = resources.getString(R.string.girls_generation_result)
                girls_strategy.text = resources.getString(R.string.girls_generation_sta)
                girls_popul.text = resources.getString(R.string.girls_generation_popularity)
            }
        }
        GlideApp
                .with(this)
                .load(intent.getIntExtra("memberImage", -1))
                .into(member_image)
    }
}