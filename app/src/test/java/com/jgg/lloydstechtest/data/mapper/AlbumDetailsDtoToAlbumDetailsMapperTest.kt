package com.jgg.lloydstechtest.data.mapper

import com.jgg.lloydstechtest.data.model.*
import com.jgg.lloydstechtest.domain.model.*
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AlbumDetailsDtoToAlbumDetailsMapperTest {

    @Mock
    private lateinit var imageDtoToImageMapper : ImageDtoToImageMapper
    @Mock
    private lateinit var tagDtoToTagMapper : TagDtoToTagMapper
    @Mock
    private lateinit var trackDtoToTrackMapper : TrackDtoToTrackMapper
    @Mock
    private lateinit var wikiDtoToWikiMapper : WikiDtoToWikiMapper

    private lateinit var cut : AlbumDetailsDtoToAlbumDetailsMapper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        cut = AlbumDetailsDtoToAlbumDetailsMapper(imageDtoToImageMapper, tagDtoToTagMapper, trackDtoToTrackMapper, wikiDtoToWikiMapper)
    }

    @Test
    fun mapToDomain() {
        // Given
        val name = "name"
        val artist = "artist"
        val url = "url"
        val imageDtoList = mock<List<ImageDto>>()
        val listeners = "4509"
        val playcount = "4431"
        val tracksDtoList = mock<List<TrackDto>>()
        val tracksDto = mock<TracksDto> { on {it.track} doReturn tracksDtoList}
        val tagDtoList = mock<List<TagDto>>()
        val tagsDto = mock<TagsDto> { on {it.tag} doReturn tagDtoList}
        val wikiDto = mock<WikiDto>()
        val albumDetailsDto = AlbumDetailsDto(name, artist, url, imageDtoList, listeners, playcount, tracksDto, tagsDto, wikiDto)
        val imageList = mock<List<Image>>()
        whenever(imageDtoToImageMapper.mapToDomain(imageDtoList)).thenReturn(imageList)
        val tagList = mock<List<Tag>>()
        whenever(tagDtoToTagMapper.mapToDomain(tagDtoList)).thenReturn(tagList)
        val trackList = mock<List<Track>>()
        whenever(trackDtoToTrackMapper.mapToDomain(tracksDtoList)).thenReturn(trackList)
        val wiki = mock<Wiki>()
        whenever(wikiDtoToWikiMapper.mapToDomain(wikiDto)).thenReturn(wiki)

        // When
        val actual = cut.mapToDomain(albumDetailsDto)

        // Then
        val expected = AlbumDetails(name, artist, url, imageList, listeners.toInt(), playcount.toInt(), trackList, tagList, wiki)
        assertEquals(expected, actual)
    }
}