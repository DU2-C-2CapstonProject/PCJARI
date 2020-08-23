package com.example.capston.pcjari.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capston.pcjari.Activity.A0_BaseActivity
import com.example.capston.pcjari.PC.PCListResponse
import com.example.capston.pcjari.R
import kotlinx.android.synthetic.main.f3_s_fragment_favorite.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by KangSeungho on 2017-10-27.
 */
class F3_S_FavoriteFragment : F0_S_BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        requireActivity().title = "즐겨찾기"
        val view = inflater.inflate(R.layout.f3_s_fragment_favorite, container, false)

        initUI(view)
        setListener()

        mListener = object : RefreshListener {
            override fun done(name: String?) {
                networkAPI.getPCListByFavorite(2, main.favorite, name)
                        .enqueue(object : Callback<PCListResponse> {
                            override fun onResponse(call: Call<PCListResponse>, response: Response<PCListResponse>) {
                                Log.d(A0_BaseActivity.TAG, "retrofit result : " + response.body())
                                var result = response.body() as PCListResponse

                                if(result.status == "OK") {
                                    pcListAdapter.setItem(result.pcList)
                                    pcListAdapter.notifyDataSetChanged()
                                }
                            }

                            override fun onFailure(call: Call<PCListResponse>, t: Throwable) {
                                Log.e(A0_BaseActivity.TAG, "retrofit getPCListByFavorite error")
                            }
                        })
            }
        }

        mListener.done(null)

        return view
    }

    private fun initUI(view : View) {
        search_name = view.favorite_search_name
        search_button = view.favorite_search_button

        swipe = view.favorite_swipe
        listview = view.favorite_listview
    }
}