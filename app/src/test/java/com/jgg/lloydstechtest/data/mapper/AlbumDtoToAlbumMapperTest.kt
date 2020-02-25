package com.jgg.lloydstechtest.data.mapper

import com.jgg.lloydstechtest.data.model.AlbumDto
import com.jgg.lloydstechtest.data.model.ImageDto
import com.jgg.lloydstechtest.domain.model.Album
import com.jgg.lloydstechtest.domain.model.Image
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AlbumDtoToAlbumMapperTest {

    @Mock
    private lateinit var imageDtoToImageMapper : ImageDtoToImageMapper

    private lateinit var cut : AlbumDtoToAlbumMapper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        cut = AlbumDtoToAlbumMapper(imageDtoToImageMapper)
    }

    @Test
    fun `given albumDto when mapToDomain then mapped Album returned`() {
        // Given
        val name = "name"
        val artist = "artist"
        val url = "url"
        val imageDtoList = mock<List<ImageDto>>()
        val streamable = "1"
        val mbid = "mbid"
        val imageList = mock<List<Image>>()
        val albumDto = AlbumDto(name, artist, url, imageDtoList, streamable, mbid)
        whenever(imageDtoToImageMapper.mapToDomain(imageDtoList)).thenReturn(imageList)

        // When
        val actual = cut.mapToDomain(albumDto)

        // Then
        val expected = Album(name, artist, url, imageList, true, mbid)
        assertEquals(expected, actual)
    }

    @Test
    fun `given list of albumDtos when mapToDomain then list of mapped Albums returned`() {
        // Given
        val name1 = "name1"
        val artist1 = "artist1"
        val url1 = "url1"
        val imageDtoList1 = mock<List<ImageDto>>()
        val streamable1 = "1"
        val mbid1 = "mbid1"
        val imageList1 = mock<List<Image>>()
        val albumDto1 = AlbumDto(name1, artist1, url1, imageDtoList1, streamable1, mbid1)
        whenever(imageDtoToImageMapper.mapToDomain(imageDtoList1)).thenReturn(imageList1)
        val name2 = "name2"
        val artist2 = "artist2"
        val url2 = "url2"
        val imageDtoList2 = mock<List<ImageDto>>()
        val streamable2 = "0"
        val mbid2 = "mbid2"
        val imageList2 = mock<List<Image>>()
        val albumDto2 = AlbumDto(name2, artist2, url2, imageDtoList2, streamable2, mbid2)
        whenever(imageDtoToImageMapper.mapToDomain(imageDtoList2)).thenReturn(imageList2)
        val albumDtoList = arrayListOf(albumDto1, albumDto2)

        // When
        val actual = cut.mapToDomain(albumDtoList)

        // Then
        val expected1 = Album(name1, artist1, url1, imageList1, true, mbid1)
        val expected2 = Album(name2, artist2, url2, imageList2, false, mbid2)
        assertEquals(2, actual.size)
        assertEquals(expected1, actual[0])
        assertEquals(expected2, actual[1])
    }
}