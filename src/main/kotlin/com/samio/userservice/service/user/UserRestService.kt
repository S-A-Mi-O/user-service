package com.samio.userservice.service.user

import com.samio.core.service.abstraction.RestServiceTemplate
import com.samio.core.service.annotation.RestServiceFor
import com.samio.userservice.model.user.User
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
@RestServiceFor(User::class)
class UserRestService: RestServiceTemplate<User>() {
    fun importUsersFromExcel(file: MultipartFile, sheet: String, rows: Int, evaluationColumn: String) {
                TODO("Not yet implemented")
    }

    fun exportUsersToExcel(filter: String?, userInfoProperties: List<String>?): ByteArray? {
                TODO("Not yet implemented")
    }

    fun exportUsersToPdf(filter: String?): ByteArray? {
                TODO("Not yet implemented")
    }
}
