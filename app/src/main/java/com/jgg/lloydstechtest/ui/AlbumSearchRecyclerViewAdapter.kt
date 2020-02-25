package com.jgg.lloydstechtest.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jgg.lloydstechtest.R
import com.jgg.lloydstechtest.domain.model.Album

import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.album_search_item.view.*

class AlbumSearchRecyclerViewAdapter(
    private val mListener: OnListFragmentInteractionListener?
) : ListAdapter<Album, RecyclerView.ViewHolder>(AlbumDiffCallback()) {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Album
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.album_search_item, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        holder as AlbumViewHolder
        holder.albumNameView.text = item.name
        holder.artistNameView.text = item.artist
        if (!getThumbnailImageUrl(item).isEmpty()) {
            Picasso.get().load(getThumbnailImageUrl(item))
                .placeholder(R.drawable.navigation_empty_icon)
                .into(holder.thumbnailView);
        }
        with(holder.view) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    inner class AlbumViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val thumbnailView: ImageView = view.album_thumbnail
        val albumNameView: TextView = view.album_name
        val artistNameView: TextView = view.artist_name

        override fun toString(): String {
            return super.toString() + " '" + albumNameView.text + " " + artistNameView.text + "'"
        }
    }

    private fun getThumbnailImageUrl(album: Album) : String {
        return album.image.find { it.size == "medium" }?.url ?: ""
    }
}

private class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {

    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }
}
