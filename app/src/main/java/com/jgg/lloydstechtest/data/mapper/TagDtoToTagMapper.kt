package com.jgg.lloydstechtest.data.mapper

import com.jgg.lloydstechtest.data.model.TagDto
import com.jgg.lloydstechtest.domain.model.Tag
import javax.inject.Inject

class TagDtoToTagMapper
@Inject constructor() {
    fun mapToDomain(toBeTransformed : TagDto) : Tag {
        return with(toBeTransformed) {
            Tag(name = name,
                url = url)
        }
    }

    fun mapToDomain(list: List<TagDto>): List<Tag> {
        val targetList = ArrayList<Tag>()

        for (source in list) {
            targetList.add(mapToDomain(source))
        }

        return targetList
    }
}

