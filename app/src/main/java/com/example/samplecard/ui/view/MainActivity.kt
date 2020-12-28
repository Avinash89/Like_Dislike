package com.example.samplecard.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.example.mvvmkotlinexample.R
import com.example.samplecard.model.Result
import com.example.samplecard.ui.adapter.CardStackAdapter
import com.example.samplecard.ui.viewmodel.MainActivityViewModel
import com.example.samplecard.ui.viewmodel.SpotDiffCallback
import com.example.samplecard.util.*
import java.lang.Thread.sleep
import java.util.*

class MainActivity : AppCompatActivity(), CardStackListener {
    var results = ArrayList<Result>()
    lateinit var mainActivityViewModel: MainActivityViewModel
    private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }
    private val acceptText by lazy { findViewById<TextView>(R.id.txt_accept) }
    private val declineText by lazy { findViewById<TextView>(R.id.txt_decline) }
    private val cardStackView by lazy { findViewById<CardStackView>(R.id.card_stack_view) }
    private val manager by lazy { CardStackLayoutManager(this, this) }
    private val adapter by lazy { CardStackAdapter(createSpots()) }
    private val like by lazy { findViewById<View>(R.id.like_button) }
    private val skip by lazy { findViewById<View>(R.id.skip_button) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        setupCardStackView()
        setupButton()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCardDragging(direction: Direction, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
        if (manager.topPosition == adapter.itemCount - 5) {
            paginate()
        }
    }

    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardAppeared: ($position) ${textView.text}")
    }

    override fun onCardDisappeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardDisappeared: ($position) ${textView.text}")
    }

    private fun setupCardStackView() {
        initialize()
    }

    private fun setupButton() {

        skip.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(1)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
            showDeclineButton()
            saveData(false)
        }


        like.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(1)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
            showAcceptButton()
            saveData(true)
        }
    }

    private fun saveData(status: Boolean) {
        if (results != null && results.size > 0 && adapter.getPosition() < 10) {
            val resultSelected = results.get(adapter.getPosition())
            mainActivityViewModel.insertData(
                this,
                resultSelected.login.uuid,
                resultSelected.name.first,
                resultSelected.picture.large,
                status
            )
        }
    }

    private fun initialize() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.Automatic)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun paginate() {
        val old = adapter.getSpots()
        val new = old.plus(createSpots())
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun reload(resultsList: List<Result>) {
        val old = adapter.getSpots()
        val new = resultsList
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setSpots(new)
        result.dispatchUpdatesTo(adapter)
    }


    private fun createSpots(): List<Result> {
        mainActivityViewModel.getUser()!!.observe(this, Observer { serviceSetterGetter ->
            results = serviceSetterGetter.results as ArrayList<Result>
            reload(results)
        })
        return results
    }

    private fun showAcceptButton() {
        like.visibility = View.GONE
        acceptText.visibility = View.VISIBLE
        Thread {
            try {
                sleep(500)
            } catch (exception: InterruptedException) {
            }
            runOnUiThread {
                acceptText.visibility = View.GONE
                like.visibility = View.VISIBLE
            }
        }.start()
    }

    private fun showDeclineButton() {
        skip.visibility = View.GONE
        declineText.visibility = View.VISIBLE
        Thread {
            try {
                sleep(500)
            } catch (exception: InterruptedException) {
            }
            runOnUiThread {
                declineText.visibility = View.GONE
                skip.visibility = View.VISIBLE
            }
        }.start()
    }
}
