package com.jgg.lloydstechtest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.jgg.lloydstechtest.R
import com.jgg.lloydstechtest.domain.model.Album

import com.jgg.lloydstechtest.ui.utils.SearchViewObservable
import com.jgg.lloydstechtest.ui.viewmodel.AlbumSearchViewModel
import com.jgg.lloydstechtest.ui.viewmodel.AlbumSearchViewState
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.album_search_list.view.*
import javax.inject.Inject

interface OnListFragmentInteractionListener {
    fun onListFragmentInteraction(item: Album)
}

class AlbumSearchFragment : Fragment(), OnListFragmentInteractionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var viewModel: AlbumSearchViewModel
    // TODO: Customize parameters
    private var columnCount = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.album_search_list, container, false)

        // Set the adapter
        if (view.list is RecyclerView) {
            recyclerView = view.list
            with(recyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
            }
            recyclerView.adapter = AlbumSearchRecyclerViewAdapter(this)
        }

        if (view.search_view is SearchView) {
            searchView = view.search_view
            searchView.onActionViewExpanded()
            searchView.setIconified(false)
            searchView.clearFocus()
            searchView.queryHint = getString(R.string.search_query_hint)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AlbumSearchViewModel::class.java)

        val searchTextObservable = SearchViewObservable.getObservable(searchView)
        viewModel.searchAlbums(searchTextObservable)
        subscribeUi(recyclerView.adapter as AlbumSearchRecyclerViewAdapter)

    }

    private fun subscribeUi(adapter: AlbumSearchRecyclerViewAdapter) {
        viewModel.albumListLiveData.observe(viewLifecycleOwner, Observer(::onViewState))
    }

    override fun onListFragmentInteraction(album: Album) {
        album?.let {
            viewModel.onAlbumSelected(album)
            // Clear the keyboard if is is shown
            searchView.clearFocus()
            // Navigate to detail view
            val action = AlbumSearchFragmentDirections.actionSearchFragmentToDetailsFragment()
            findNavController().navigate(action)
        }
    }

    private fun onViewState(viewState : AlbumSearchViewState) {
        if (!viewState.errorMessage.isEmpty()) {
            Toast.makeText(context, viewState.errorMessage, Toast.LENGTH_LONG).show()
        } else if (viewState.complete) {
            // Previous search completed - initiate a new one
            val searchTextObservable = SearchViewObservable.getObservable(searchView)
            viewModel.searchAlbums(searchTextObservable)
        } else {
            (recyclerView.adapter as AlbumSearchRecyclerViewAdapter)?.submitList(viewState.albumList)
        }
    }
}
