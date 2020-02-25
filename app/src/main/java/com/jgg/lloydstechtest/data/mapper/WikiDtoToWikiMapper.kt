package com.jgg.lloydstechtest.data.mapper

import com.jgg.lloydstechtest.data.model.WikiDto
import com.jgg.lloydstechtest.domain.model.Wiki
import javax.inject.Inject

class WikiDtoToWikiMapper
@Inject constructor() {
    fun mapToDomain(toBeTransformed: WikiDto) : Wiki {
        return with(toBeTransformed) {
            Wiki(
                published = published,
                summary = summary,
                content = content
            )
        }
    }
}