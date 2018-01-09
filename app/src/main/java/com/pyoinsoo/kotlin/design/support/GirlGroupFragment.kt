package com.pyoinsoo.kotlin.design.support

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.girls_group_fragment_recyclerview.*

/*
 * Created by pyoinsoo on 2018-01-07
 * insoo.pyo@gmail.com
 * twice와 소녀시대를 나타내는 Fragment구현
 */
class GirlGroupFragment: Fragment{

    constructor(): super()

    /*
     * 늦은 초기화를 통해 Fragment를 생성한 arguments를
     * 넘겨 받는다.
     * 만약 null이라면 twice를 할당한다
     */
    private val groupName: String by lazy {
        arguments?.getString("groupName") ?: "twice"
    }
    /*
     * GirlGroupFragment를 생성하는 Factory Method
     */
    companion object {
        fun newInstance(groupName: String): GirlGroupFragment {
            val bundle = Bundle()
            bundle.putString("groupName", groupName)
            val girlsFragment = GirlGroupFragment()
            girlsFragment.arguments = bundle
            return girlsFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(
                R.layout.girls_group_fragment_recyclerview,
                container,
                false
               )
    }
    /*
     * Swipe Refresh를 위한 Handler를 선언
     */
    private val handler = Handler(Looper.getMainLooper())

    /*
     * Kotlin 확장함수를 구현한다.
     * getActivity  FragmentActivity 객체를 반환하기 때문
     */
    private fun Fragment.ownerActivity() =  activity as Activity
    /*
     * Kotlin Android Extension을 Fragment에서는
     * onViewCreated에서 사용하여야 한다
     * onViewCreated -> Fragment에서 사용할 layout이 생성되고 나서 호출되는 callback 함수
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(GirlsApplication.girlsContext)
        val girlsAdapter = GirlsGroupRecyclerViewAdapter(
                groupName,
                ownerActivity() //확장함수
        )
        recyclerView.adapter = girlsAdapter
        swipeRefresh.setOnRefreshListener {
            handler.postDelayed({
                /*
                 * swipe시 girls member image 랜덤하게 3개 추가
                 */
                val members = GirlGroupClassification.additionalRandomMember(groupName)
                girlsAdapter.additionalInsertionMember(members)
                Toast.makeText(activity, "Additional Member Image", Toast.LENGTH_SHORT).show()
                swipeRefresh.isRefreshing = false
            }, 1000)
        }
    }

}