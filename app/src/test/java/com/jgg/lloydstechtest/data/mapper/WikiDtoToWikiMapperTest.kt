package com.jgg.lloydstechtest.data.mapper

import com.jgg.lloydstechtest.data.model.WikiDto
import com.jgg.lloydstechtest.domain.model.Wiki
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class WikiDtoToWikiMapperTest {

    private val cut = WikiDtoToWikiMapper()

    @Test
    fun `given wikiDto when mapToDomain then mapped Wiki returned`() {
        // Given
        val published = "publishedString"
        val summary = "summaryString"
        val content = "contentString"
        val wikiDto = WikiDto(published, summary, content)

        // When
        val actual = cut.mapToDomain(wikiDto)

        // Then
        val expected = Wiki(published, summary, content)
        assertEquals(expected, actual)
    }
}