package com.jgg.lloydstechtest.data.mapper

import com.jgg.lloydstechtest.data.model.AlbumDetailsDto
import com.jgg.lloydstechtest.domain.model.AlbumDetails
import com.jgg.lloydstechtest.domain.model.Wiki
import javax.inject.Inject

class AlbumDetailsDtoToAlbumDetailsMapper
@Inject constructor(
    private val imageDtoToImageMapper: ImageDtoToImageMapper,
    private val tagDtoToTagMapper: TagDtoToTagMapper,
    private val trackDtoToTrackMapper: TrackDtoToTrackMapper,
    private val wikiDtoToWikiMapper: WikiDtoToWikiMapper
){
    fun mapToDomain(toBeTransformed: AlbumDetailsDto) : AlbumDetails {
        return with(toBeTransformed) {
            AlbumDetails(name = name,
                artist = artist,
                url = url,
                image = imageDtoToImageMapper.mapToDomain(image),
                listeners = listeners.toInt(),
                playcount = playcount.toInt(),
                tracks = trackDtoToTrackMapper.mapToDomain(tracks.track),
                tags = tagDtoToTagMapper.mapToDomain(tags.tag),
                wiki = wiki?.let { wikiDtoToWikiMapper.mapToDomain(it) } ?: Wiki.createEmpty()
                )
        }
    }
}