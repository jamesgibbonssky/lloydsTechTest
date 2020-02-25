package com.jgg.lloydstechtest.data.mapper

import com.jgg.lloydstechtest.data.model.TrackDto
import com.jgg.lloydstechtest.domain.model.Track
import org.junit.Assert.*
import org.junit.Test

class TrackDtoToTrackMapperTest {
    private val cut = TrackDtoToTrackMapper()

    @Test
    fun `given trackDto when mapToDomain then mapped Track returned`() {
        // Given
        val name = "name"
        val url = "url"
        val duration = 123
        val rank = "60"
        val trackDto = TrackDto(name, url, duration, rank)

        // When
        val actual = cut.mapToDomain(trackDto)

        // Then
        val expected = Track(name, url, duration)
        assertEquals(actual, expected)
    }

    @Test
    fun `given list of trackDtos when mapToDomain then list of Tracks returned`() {
        // Given
        val name1 = "name1"
        val url1 = "url1"
        val duration1 = 123
        val rank1 = "60"
        val trackDto1 = TrackDto(name1, url1, duration1, rank1)

        val name2 = "name2"
        val url2 = "url2"
        val duration2 = 178
        val rank2 = "100"
        val trackDto2 = TrackDto(name2, url2, duration2, rank2)

        val trackDtoList = arrayListOf(trackDto1, trackDto2)

        // When
        val actual = cut.mapToDomain(trackDtoList)

        // Then
        val expected1 = Track(name1, url1, duration1)
        val expected2 = Track(name2, url2, duration2)
        assertEquals(2, actual.size)
        assertEquals(expected1, actual[0])
        assertEquals(expected2, actual[1])
    }
}