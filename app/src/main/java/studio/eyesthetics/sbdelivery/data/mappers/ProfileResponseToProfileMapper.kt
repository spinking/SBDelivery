package studio.eyesthetics.sbdelivery.data.mappers

import studio.eyesthetics.sbdelivery.data.models.profile.Profile
import studio.eyesthetics.sbdelivery.data.models.profile.ProfileResponse

class ProfileResponseToProfileMapper : Mapper<ProfileResponse, Profile> {
    override fun mapFromEntity(type: ProfileResponse): Profile {
        return Profile(
            id = type.id,
            firstName = type.firstName ?: "",
            lastName = type.lastName ?: "",
            email = type.email ?: ""
        )
    }

    override fun mapFromListEntity(type: List<ProfileResponse>): List<Profile> {
        return type.map { mapFromEntity(it) }
    }
}