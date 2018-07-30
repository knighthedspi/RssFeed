package moneyforward.rssfeed.ui.main

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import moneyforward.rssfeed.R
import moneyforward.rssfeed.data.model.FeedItem
import moneyforward.rssfeed.databinding.FeedItemBinding
import moneyforward.rssfeed.databinding.MainFragmentBinding
import moneyforward.rssfeed.di.Injectable
import moneyforward.rssfeed.ui.detail.DetailFragment
import moneyforward.rssfeed.utils.listen
import moneyforward.rssfeed.utils.observe
import javax.inject.Inject

class MainFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: MainAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return DataBindingUtil.inflate<MainFragmentBinding>(inflater, R.layout.main_fragment, container, false).also {
            binding = it
        }.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(MainViewModel::class.java)
        adapter = MainAdapter(context!!, this)
        binding.container.adapter = adapter
        binding.tabs.setViewPager(binding.container)

        adapter.onFeedItemClick = {
            val detailFragment = DetailFragment.newInstance(it.link!!, it.title!!)
            fragmentManager!!.beginTransaction()
                    .replace(R.id.containerLayout, detailFragment)
                    .addToBackStack(null)
                    .commit()
        }
        adapter.data.addAll(viewModel.data)
        adapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}

class MainAdapter(private var context: Context,
                  private var lifecycleOwner: LifecycleOwner) : PagerAdapter() {

    val data = ArrayList<MainViewModel.FeedData>()

    var onFeedItemClick : ((FeedItem) -> Unit)? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.frame, container, false)
        val listView = view.findViewById<RecyclerView>(R.id.list_view)
        listView.setHasFixedSize(true)
        listView.layoutManager = LinearLayoutManager(context)
        val feedData = data[position]
        val feedItemAdapter = FeedItemAdapter()
        feedItemAdapter.onItemClick = onFeedItemClick
        listView.adapter = feedItemAdapter
        feedData.liveData.observe(lifecycleOwner) {
            it ?: return@observe
            feedItemAdapter.run {
                items.clear()
                items.addAll(it.feedItems!!)
                notifyDataSetChanged()
            }
        }
        container.addView(view)
        return view
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return data[position].channel
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}

class FeedItemAdapter : RecyclerView.Adapter<FeedItemAdapter.ViewHolder>() {

    val items = ArrayList<FeedItem>()
    var onItemClick : ((FeedItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedItemAdapter.ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.feed_item,
                parent,
                false)).listen { position, _ ->
            val item = items[position]
            onItemClick?.invoke(item)
        }
    }

    override fun onBindViewHolder(holder: FeedItemAdapter.ViewHolder, position: Int) {
        holder.binding.feedItem = items[position]
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(val binding: FeedItemBinding) : RecyclerView.ViewHolder(binding.root)
}