package studio.eyesthetics.sbdelivery.data.mappers

import studio.eyesthetics.sbdelivery.data.models.auth.LoginResponse
import studio.eyesthetics.sbdelivery.data.models.profile.Profile

class LoginResponseToProfileMapper : Mapper<LoginResponse, Profile> {
    override fun mapFromEntity(type: LoginResponse): Profile {
        return Profile(
            id = type.id,
            firstName = type.firstName,
            lastName = type.lastName,
            email = type.email
            )
    }

    override fun mapFromListEntity(type: List<LoginResponse>): List<Profile> {
        return type.map { mapFromEntity(it) }
    }
}