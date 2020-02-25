package com.jgg.lloydstechtest.data.mapper

import com.jgg.lloydstechtest.data.model.AlbumDto
import com.jgg.lloydstechtest.domain.model.Album
import java.util.ArrayList
import javax.inject.Inject

class AlbumDtoToAlbumMapper
@Inject constructor(
    private val imageDtoToImageMapper : ImageDtoToImageMapper
) {
    fun mapToDomain(toBeTransformed: AlbumDto) : Album {
        return with(toBeTransformed) {
            Album(name = name,
                artist = artist,
                url = url,
                image = imageDtoToImageMapper.mapToDomain(image),
                streamable = streamable == "1",
                mbid = mbid
                )
        }
    }

    fun mapToDomain(list: List<AlbumDto>): List<Album> {
        val targetList = ArrayList<Album>()

        for (source in list) {
            targetList.add(mapToDomain(source))
        }

        return targetList
    }
}