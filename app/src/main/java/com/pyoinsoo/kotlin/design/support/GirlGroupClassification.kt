package com.pyoinsoo.kotlin.design.support

import android.annotation.TargetApi
import android.os.Build
import android.transition.Explode
import android.util.SparseArray
import com.pyoinsoo.kotlin.design.support.R.drawable.*
import java.util.*

/*
 * Created by pyoinsoo on 2018-01-07
 * insoo.pyo@gmail.com
 *
 * 걸그룹의 정보를 담고있는 Util클래스
 */
class GirlGroupClassification {
    /*
     * 공통모듈에 해당하므로 동반객체로 구성한다
     */
    companion object {
        //RecyclerView에 뿌릴때 소녀시대 getter 재정의
        var girlsMembers = mutableListOf<Int>()
            get() : MutableList<Int> {
                Collections.shuffle(
                        field,
                        Random(System.currentTimeMillis())
                )
                return field
            }
        /*
         * SparseArray[Int Key, String Value]를 가지는 맵구조
         * Int Type의 Key를 갖는 Map구성은 SparseArray가 더 효율적
         * 소녀시대 이미지(res/drawable)에 대해 이름을 나타내는구조(초기구성)
         */

        private val girlsMemberMap = SparseArray<String>()

        init {
            /*
             * 소녀시대 사진을 List에 강제입력
             */
            with(girlsMembers) {
                add(girls_generation_all)
                add(girls_generation_hyoyeon)
                add(girls_generation_jesica)
                add(girls_generation_seohyun)
                add(girls_generation_sunny)
                add(girls_generation_suyoung)
                add(girls_generation_taeyeon)
                add(girls_generation_tifany)
                add(girls_generation_yuna)
                add(girls_generation_yuri)
                add(girls_generation_all)
                add(girls_generation_hyoyeon)
                add(girls_generation_jesica)
                add(girls_generation_seohyun)
                add(girls_generation_sunny)
                add(girls_generation_suyoung)
                add(girls_generation_taeyeon)
                add(girls_generation_tifany)
                add(girls_generation_yuna)
                add(girls_generation_yuri)
                add(girls_generation_all)
            }

            with(girlsMemberMap) {
                append(R.drawable.girls_generation_all,"Girl's Generation")
                append(R.drawable.girls_generation_hyoyeon , "Hyoyeon")
                append(R.drawable.girls_generation_seohyun , "Seohyun")
                append(R.drawable.girls_generation_sunny , "Suney")
                append(R.drawable.girls_generation_suyoung , "Suyoung")
                append(R.drawable.girls_generation_taeyeon , "Taeyeon")
                append(R.drawable.girls_generation_tifany , "Tifany")
                append(R.drawable.girls_generation_yuri , "Yuri")
                append(R.drawable.girls_generation_yuna , "Yuna")
                append(R.drawable.girls_generation_jesica , "Jesica")
            }
        }
        //트와이스의 정보를 List(변경가능한)에 담기위함
        var twiceMembers = mutableListOf<Int>()
            get() : MutableList<Int> {
                Collections.shuffle(
                        field,
                        Random(System.currentTimeMillis())
                )
                return field
            }
        //트와이스의 리소스 파일과 멤버이름을 매핑하기 위함
        private val twiceMemberMap = SparseArray<String>()

        init {
            with(twiceMembers){
                add(R.drawable.twice_all)
                add(R.drawable.twice_chaeyeong)
                add(R.drawable.twice_dahyeon)
                add(R.drawable.twice_jihyo)
                add(R.drawable.twice_jungyeon)
                add(R.drawable.twice_mina)
                add(R.drawable.twice_momo)
                add(R.drawable.twice_nayeon)
                add(R.drawable.twice_sana)
                add(R.drawable.twice_tzuyu)
                add(R.drawable.twice_all)
                add(R.drawable.twice_chaeyeong)
                add(R.drawable.twice_dahyeon)
                add(R.drawable.twice_jihyo)
                add(R.drawable.twice_jungyeon)
                add(R.drawable.twice_mina)
                add(R.drawable.twice_momo)
                add(R.drawable.twice_nayeon)
                add(R.drawable.twice_sana)
                add(R.drawable.twice_tzuyu)
            }

            // 리소스와 이름을 매핑
            with(twiceMemberMap) {
                append(R.drawable.twice_all, "Twice")
                append(R.drawable.twice_chaeyeong, "Chaeyeong")
                append(R.drawable.twice_dahyeon, "Deohyun")
                append(R.drawable.twice_jihyo, "Jihyo")
                append(R.drawable.twice_jungyeon, "Jungyeon")
                append(R.drawable.twice_mina, "Mina")
                append(R.drawable.twice_momo, "Momo")
                append(R.drawable.twice_nayeon, "Nayeon")
                append(R.drawable.twice_sana, "Sana")
                append(R.drawable.twice_tzuyu, "Tzuyu")
            }
        }
        //RecyclerView의 아이템을 터치시 해당하는 소녀시대의 멤버 이름을 리턴
        internal fun findGirlsGenerationMemberName(key: Int) = girlsMemberMap.get(key)

        //RecyclerView의 아이템을 터치시 해당하는 트와이스의 이름을 리턴
        internal fun findTwiceMemberName(key: Int): String {
            return twiceMemberMap.get(key)
        }
        //Swipe Refresh시에 랜덤하게 멤버 3명을 중복 추가한다
        internal fun additionalRandomMember(groupName: String): MutableList<Int> {
            val members: MutableList<Int>
            /*
             * Kotlin에서는 "=="연산은 equals를 호출하여 깊은 비교를 진행한다
             */
            val seedValue = Random(System.nanoTime())
            if (groupName == "twice") {
                Collections.shuffle(twiceMembers, seedValue)
                members = twiceMembers.subList(0, 3)
            } else {
                Collections.shuffle(girlsMembers, seedValue)
                members = girlsMembers.subList(0, 3)
            }
            return members
        }
        /*
         * Explode 트랜지션객체를 리턴
         * 싱글턴으로 리턴
         */
        private val explode: Explode = Explode()

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        fun getExplodeInstance() : Explode{
            return explode
        }
    }
}