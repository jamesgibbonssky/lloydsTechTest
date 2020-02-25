package com.jgg.lloydstechtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jgg.lloydstechtest.data.client.AlbumClient
import com.jgg.lloydstechtest.data.model.AlbumDetailsDto
import com.jgg.lloydstechtest.data.model.GetInfoResponse
import com.jgg.lloydstechtest.data.model.SearchDto
import com.jgg.lloydstechtest.data.model.SearchResponse
import com.jgg.lloydstechtest.domain.AlbumRepository
import com.jgg.lloydstechtest.domain.model.Album
import com.jgg.lloydstechtest.domain.model.AlbumDetails
import com.jgg.lloydstechtest.domain.schedulers.SchedulersProvider
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
