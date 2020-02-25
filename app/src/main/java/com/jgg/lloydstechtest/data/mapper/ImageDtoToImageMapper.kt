package com.jgg.lloydstechtest.data.mapper

import com.jgg.lloydstechtest.data.model.ImageDto
import com.jgg.lloydstechtest.domain.model.Image
import java.util.ArrayList
import javax.inject.Inject

class ImageDtoToImageMapper
    @Inject constructor() {

    fun mapToDomain(toBeTransformed: ImageDto) : Image {
        return with(toBeTransformed) {
            Image(
                url = url,
                size = size
            )
        }
    }

    fun mapToDomain(list: List<ImageDto>): List<Image> {
        val targetList = ArrayList<Image>()

        for (source in list) {
            targetList.add(mapToDomain(source))
        }

        return targetList
    }
}