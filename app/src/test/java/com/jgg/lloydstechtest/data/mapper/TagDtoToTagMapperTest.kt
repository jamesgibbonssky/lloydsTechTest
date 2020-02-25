package com.jgg.lloydstechtest.data.mapper

import com.jgg.lloydstechtest.data.model.TagDto
import com.jgg.lloydstechtest.domain.model.Tag
import org.junit.Assert.*
import org.junit.Test

class TagDtoToTagMapperTest {
    private val cut = TagDtoToTagMapper()

    @Test
    fun `given tagDto when mapToDomain then mapped Tag returned`() {
        // Given
        val name = "name"
        val url = "url"
        val tagDto = TagDto(name, url)

        // When
        val actual = cut.mapToDomain(tagDto)

        // Then
        val expected = Tag(name, url)
        assertEquals(expected, actual)
    }

    @Test
    fun `given list of tagDtos when mapToDomain then list of mapped Tags returned`() {
        // Given
        val name1 = "name1"
        val url1 = "url1"
        val tagDto1 = TagDto(name1, url1)
        val name2 = "name2"
        val url2 = "url2"
        val tagDto2 = TagDto(name2, url2)
        val tagDtoList = arrayListOf(tagDto1, tagDto2)

        // When
        val actual = cut.mapToDomain(tagDtoList)

        // Then
        val expected1 = Tag(name1, url1)
        val expected2 = Tag(name2, url2)
        assertEquals(2, actual.size)
        assertEquals(expected1, actual[0])
        assertEquals(expected2, actual[1])
    }
}