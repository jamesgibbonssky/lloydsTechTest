package com.jgg.lloydstechtest.data.mapper

import com.jgg.lloydstechtest.data.model.TrackDto
import com.jgg.lloydstechtest.domain.model.Track
import javax.inject.Inject

class TrackDtoToTrackMapper
@Inject constructor() {
    fun mapToDomain(toBeTransformed: TrackDto) : Track {
        return with(toBeTransformed) {
            Track(
                name = name,
                url = url,
                duration = duration
            )
        }
    }

    fun mapToDomain(list: List<TrackDto>): List<Track> {
        val targetList = ArrayList<Track>()

        for (source in list) {
            targetList.add(mapToDomain(source))
        }

        return targetList
    }
}