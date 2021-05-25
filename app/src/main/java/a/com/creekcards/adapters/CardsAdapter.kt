package a.com.creekcards.adapters

import a.com.creekcards.R
import a.com.creekcards.models.CardItems
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter

class CardsAdapter(private val dataList: ArrayList<CardItems>, private val context: Context) : PagerAdapter() {

    override fun getCount(): Int {
        return dataList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(context).inflate(R.layout.card_items, container, false)
        val title = view.findViewById(R.id.title) as TextView
        title.text = dataList.get(position).title
        container.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
