package com.pyoinsoo.kotlin.design.support

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast

/*
 * Kotlin Android Extension Import
 */
import kotlinx.android.synthetic.main.activity_main.*
/*
 * kotlin android Extension을 사용시에 layout을
 * include 해야한다면
 * <include android:id="@+id/includeViewPager"
 *   layout="@layout/include_list_viewpager"/>
 *
 * 위와 같이 반드시 android:id 속성을 정의해야 한다
 */
import kotlinx.android.synthetic.main.include_list_viewpager.*

/*
 * Created by pyoinsoo on 2018-01-07
 * insoo.pyo@gmail.com
 */
class GirlGroupMainActivity: AppCompatActivity() {

    private var backPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this,drawerLayout,
                                           toolbar,
                                           R.string.openDrawer, R.string.closeDrawer)
        /*
         * DrawerLayout과 ActionBar를 동기화 한다
         */
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener{
            val flag = true
            it.isChecked = flag
            drawerLayout.closeDrawers()
            flag
        }
        /*
         * ViewPager에 Fragment를 배치한다
         */
        setupGirlsViewPager(viewpager)
        /*
         * Tab에 ViewPager를 부착한다
         */
        tabLayout.setupWithViewPager(viewpager)

        /*
         * 코드에서 트랜지션을 적용한다
         */
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            with(GirlGroupClassification.getExplodeInstance()){
                duration = 200
                window.reenterTransition = this
            }
        }
        /*
         * FAB 터치시 오버랩된 RecyclerView Item의 이벤트를 없애기 위함
         */
        with(fab) {
            bringToFront()
            setOnClickListener{ _ -> Unit }
        }
    }
    /*
     * View Pager에 붙일 GirlGroup Fragment를 생성
     */
    private fun setupGirlsViewPager(viewPager: ViewPager){
        val girlsAdapter = GirlGroupPagerAdapter(supportFragmentManager)
        with(girlsAdapter){
            appendFragment(GirlGroupFragment.newInstance("girlsGeneration"), "Girl's Generation")
            appendFragment(GirlGroupFragment.newInstance("twice"), "Twice")
        }
        viewPager.adapter = girlsAdapter
    }
    /*
     * NavigationView의 메뉴아이템을 터치 했을때
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean{
        when(item?.itemId){
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                Toast.makeText(this, "${item?.title}  호출됨" ,Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    /*
     * Back Key를 1.5초안에 두번 누르면 앱종료
     */
    override fun onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START)

        var currentTime: Long = System.currentTimeMillis()

        var intervalTime: Long = currentTime - backPressedTime

        when {
            intervalTime in 0..1500 -> {
                super.onBackPressed()
            }
            else -> {
                backPressedTime = currentTime
                Toast.makeText(this, "뒤로 버튼을 한번 더 누르시면 종료됩니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
    /*
     * ViewPager에 추가 할 Fragment 어댑터 정의
     */
    private inner class GirlGroupPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm){
        private val girlGroupFragments = arrayListOf<GirlGroupFragment>()
        private val tabTitles = arrayListOf<String>()

        fun appendFragment(fragment:GirlGroupFragment, title:String){
            girlGroupFragments += fragment
            tabTitles += title
        }
        override fun getItem(position: Int): Fragment {
            return girlGroupFragments[position]
        }
        override fun getCount() = girlGroupFragments.size
        override fun getPageTitle(position: Int) = tabTitles[position]
    }
}