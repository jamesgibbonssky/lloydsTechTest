package com.jgg.lloydstechtest.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.jgg.lloydstechtest.R
import com.jgg.lloydstechtest.databinding.AlbumDetailsFragmentBinding
import com.jgg.lloydstechtest.domain.model.AlbumDetails
import com.jgg.lloydstechtest.domain.model.Image
import com.jgg.lloydstechtest.ui.viewmodel.AlbumDetailsViewModel
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.album_details_fragment.view.*
import kotlinx.android.synthetic.main.album_search_item.view.*
import javax.inject.Inject

class AlbumDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = AlbumDetailsFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: AlbumDetailsViewModel
    private lateinit var imageView : ImageView
    private lateinit var listenersValueView: TextView
    private lateinit var playcountValueView: TextView
    private lateinit var summaryView: TextView

    private lateinit var albumDetailsFragmentBinding: AlbumDetailsFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        albumDetailsFragmentBinding = DataBindingUtil.inflate<AlbumDetailsFragmentBinding>(
            inflater, R.layout.album_details_fragment, container, false)

        with (albumDetailsFragmentBinding) {
            val view = root
            lifecycleOwner = viewLifecycleOwner
            imageView = view.album_art
            listenersValueView = view.listeners_value
            playcountValueView = view.playcount_value
            summaryView = view.summary
        }

        return albumDetailsFragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AlbumDetailsViewModel::class.java)
        albumDetailsFragmentBinding.viewModel = viewModel
        viewModel.getAlbumDetails()
        subscribeUi()
    }

    private fun subscribeUi() {
        viewModel.albumDetailsLiveData.observe(viewLifecycleOwner, Observer(::onAlbumDetailsAvailable))
        viewModel.albumDetailsErrorMessageLiveData.observe(viewLifecycleOwner, Observer(::onErrorMessage))
    }

    private fun onAlbumDetailsAvailable(albumDetails : AlbumDetails) {
        with(getImageUrl(albumDetails.image)) {
            if (!isEmpty()) {
                Picasso.get().load(this)
                    .placeholder(R.drawable.navigation_empty_icon)
                    .into(imageView);
            }
        }
        listenersValueView.text = albumDetails.listeners.toString()
        playcountValueView.text = albumDetails.playcount.toString()
        bindRenderHtml(summaryView, albumDetails.wiki.summary)
    }

    private fun bindRenderHtml(view: TextView, description: String?) {
        if (description != null) {
            view.text = HtmlCompat.fromHtml(description, HtmlCompat.FROM_HTML_MODE_COMPACT)
            view.movementMethod = LinkMovementMethod.getInstance()
        } else {
            view.text = ""
        }
    }

    private fun onErrorMessage(errorMessage : String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun getImageUrl(imageList : List<Image>) : String {
        return imageList.find { it.size == "extralarge" }?.url ?: ""
    }
}
