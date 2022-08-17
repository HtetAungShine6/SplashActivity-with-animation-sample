package com.example.newsplashactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.walkthrough.OnboardingItem
import com.example.walkthrough.OnboardingItemsAdapter
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter
    private lateinit var indicatorsContainer : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)
    }

    private fun setOnboardingItems(){
        onboardingItemsAdapter = OnboardingItemsAdapter(
            listOf(
                OnboardingItem(
                    onboardingImage =  R.raw.touch,
                    title = "TOUCH",
                    description = "touch the animated button to enter text"
                ),
                OnboardingItem(
                    onboardingImage = R.raw.abctext,
                    title = "TEXT IT",
                    description = "text anything in the text box"
                ),
                OnboardingItem(
                    onboardingImage = R.raw.button,
                    title = "OK or CANCEL?",
                    description = "click ok button to save text and cancel button to decline"
                ),
                OnboardingItem(
                  onboardingImage = R.raw.editdelete,
                  title = "EDIT and DELETE",
                  description = "you can even edit and delete your entered text"
                ),
                OnboardingItem(
                    onboardingImage = R.raw.mobileph,
                    title = "TRY IT NOW",
                    description = "easy and simple"
                )
            )
        )
        val onboardingViewPager = findViewById<ViewPager2>(R.id.onBoardingViewPager)
        onboardingViewPager.adapter = onboardingItemsAdapter
        onboardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onboardingViewPager.getChildAt(0)as RecyclerView).overScrollMode= RecyclerView.OVER_SCROLL_NEVER
        val imageNext = findViewById<ImageView>(R.id.imageNext)
        imageNext.setOnClickListener{
            if(onboardingViewPager.currentItem + 1 < onboardingItemsAdapter.itemCount){
                onboardingViewPager.currentItem += 1
            }else{
                navigateToHomeActivity()
            }
        }
        val textSkip = findViewById<TextView>(R.id.textSkip)
        textSkip.setOnClickListener{
            navigateToHomeActivity()
        }
        val buttonGetStarted = findViewById<MaterialButton>(R.id.buttonGetStarted)
        buttonGetStarted.setOnClickListener{
            navigateToHomeActivity()
        }
    }

    private fun navigateToHomeActivity(){
        startActivity(Intent(applicationContext, HomeActivity::class.java))
        finish()
    }

    private fun setupIndicators(){
        indicatorsContainer = findViewById(R.id.indicatorsContainer)
        val indicators = arrayOfNulls<ImageView>(onboardingItemsAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8,0,8,0)
        for(i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let{
                it.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext,R.drawable.indicator_inactive_background)
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int){
        val childCount = indicatorsContainer.childCount
        for(i in 0 until childCount){
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if(i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext,R.drawable.indicator_active_background)
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext,R.drawable.indicator_inactive_background)
                )
            }
        }
    }
}