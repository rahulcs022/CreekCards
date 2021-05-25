package a.com.creekcards.activities

import a.com.creekcards.R
import a.com.creekcards.adapters.CardsAdapter
import a.com.creekcards.models.CardItems
import a.com.creekcards.models.CardModel
import a.com.creekcards.utils.ApiHelper
import a.com.creekcards.utils.RetrofitBuilder
import a.com.creekcards.utils.Status
import a.com.creekcards.view_models.CardViewModel
import a.com.creekcards.view_models.ViewModelFactory
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CardViewModel
    private lateinit var adapter: CardsAdapter
    private var cardItemsList = ArrayList<CardItems>()
    private var totalPage = 0
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupObservers()
    }

    private fun setupUI() {
        adapter = CardsAdapter(cardItemsList, this@MainActivity)
        viewPager.adapter = adapter
        setIndicatorCounts()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(CardViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.getData().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        val response = Gson().fromJson(
                            resource.data.toString(),
                            CardModel::class.java
                        )
                        response.dataList.let { it ->
                            if (response.dataList.size > 1) {
                                pageLeft.visibility = View.GONE
                                pageRight.visibility = View.VISIBLE
                            } else {
                                pageLeft.visibility = View.GONE
                                pageRight.visibility = View.GONE
                            }

                            cardItemsList.clear()
                            cardItemsList.addAll(it)
                            setupUI()
                        }
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        pageLeft.visibility = View.GONE
                        pageRight.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setIndicatorCounts() {
        totalPage = adapter.count
        currentPageText.text = "${currentPage + 1}/$totalPage"
        val dots = arrayOfNulls<ImageView>(totalPage)

        for (i in 0 until totalPage) {
            dots[i] = ImageView(this)
            dots[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.unselected_dots
                )
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            pagerDots!!.addView(dots[i], params)
        }
        dots[0]?.setImageDrawable(
            ContextCompat.getDrawable(
                this, R.drawable.selected_dot
            )
        )

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                if (currentPage == totalPage)
                    viewPager.currentItem = 0
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                currentPage = viewPager.currentItem
                currentPageText.text = "${currentPage + 1}/$totalPage"
                if (currentPage == 0) {
                    pageLeft.visibility = View.GONE
                    pageRight.visibility = View.VISIBLE
                }

                if (currentPage == (totalPage - 1)) {
                    pageLeft.visibility = View.VISIBLE
                    pageRight.visibility = View.GONE
                }
            }

            override fun onPageSelected(position: Int) {
                for (i in 0 until totalPage) {
                    dots[i]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@MainActivity, R.drawable.unselected_dots
                        )
                    )
                }
                dots[position]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MainActivity, R.drawable.selected_dot
                    )
                )
            }
        })
    }

    fun onPageLeft(view: View) {
        setLeftPageCount()
    }

    @SuppressLint("SetTextI18n")
    private fun setLeftPageCount() {
        if (currentPage != 0) {
            currentPage--
            currentPageText.text = "${currentPage + 1}/$totalPage"
            viewPager.currentItem = currentPage
            pageRight.visibility = View.VISIBLE
            pageLeft.visibility = View.VISIBLE
        }
        if (currentPage == 0)
            pageLeft.visibility = View.GONE
    }

    fun onPageRight(view: View) {
        setRightPageCount()
    }

    @SuppressLint("SetTextI18n")
    private fun setRightPageCount() {
        if (currentPage != totalPage - 1) {
            currentPage++
            currentPageText.text = "${currentPage + 1}/$totalPage"
            viewPager.currentItem = currentPage
            pageRight.visibility = View.VISIBLE
            pageLeft.visibility = View.VISIBLE
        }
        if (currentPage == (totalPage - 1))
            pageRight.visibility = View.GONE
    }
}