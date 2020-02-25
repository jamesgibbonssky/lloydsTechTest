package com.jgg.lloydstechtest.data.mapper

import com.jgg.lloydstechtest.data.model.ImageDto
import com.jgg.lloydstechtest.domain.model.Image
import org.junit.Test

import org.junit.Assert.*

class ImageDtoToImageMapperTest {

    private val cut = ImageDtoToImageMapper()

    @Test
    fun `given imageDto when mapToDomain then mapped Image returned`() {
        // Given
        val url = "url"
        val size = "size"
        val imageDto = ImageDto(url, size)

        // When
        val actual = cut.mapToDomain(imageDto)

        // Then
        val expected = Image(url, size)
        assertEquals(expected, actual)
    }

    @Test
    fun `given list of imageDtos when mapToDomain then list of mapped Images returned`() {
        // Given
        val url1 = "url"
        val size1 = "size"
        val imageDto1 = ImageDto(url1, size1)
        val url2 = "url"
        val size2 = "size"
        val imageDto2 = ImageDto(url2, size2)
        val imageDtoList = arrayListOf(imageDto1, imageDto2)

        // When
        val actual = cut.mapToDomain(imageDtoList)

        // Then
        val expected1 = Image(url1, size1)
        val expected2 = Image(url2, size2)
        assertEquals(2, actual.size)
        assertEquals(expected1, actual[0])
        assertEquals(expected2, actual[1])
    }
}