package com.example.capston.pcjari.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capston.pcjari.*
import com.example.capston.pcjari.Activity.A0_BaseActivity
import com.example.capston.pcjari.PC.PCListItem
import com.example.capston.pcjari.PC.PCListResponse
import com.example.capston.pcjari.Util.GPSTracker
import kotlinx.android.synthetic.main.f2_s_fragment_gps.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by KangSeungho on 2017-10-27.
 */
class F2_S_GpsFragment : F0_S_BaseFragment() {
    private lateinit var gps: GPSTracker
    private lateinit var dist: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        requireActivity().title = "내 주변"
        val view = inflater.inflate(R.layout.f2_s_fragment_gps, container, false)

        initUI(view)
        setListener()

        mListener = object : RefreshListener {
            override fun done(name: String?) {
                gps.Update()

                val lat = gps.getLatitude()
                val lng = gps.getLongitude()

                var pcItem: ArrayList<PCListItem> = ArrayList<PCListItem>()

                if(lat != null && lng != null) {
                    networkAPI.getPCListByGps(1, lat, lng, dist, name)
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
                                    Log.e(A0_BaseActivity.TAG, "retrofit getPCListByGps error")
                                }
                            })
                }
            }
        }

        gps = GPSTracker(requireActivity())
        dist = (main.dist.toDouble() / 10).toString()
        mListener.done(null)

        return view
    }

    private fun initUI(view : View) {
        search_name = view.gps_search_name
        search_button = view.gps_search_button

        swipe = view.gps_swipe
        listview = view.gps_listview
    }
}