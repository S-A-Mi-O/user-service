package com.samio.userservice.controller


import com.samio.core.controller.abstraction.RestControllerTemplate
import com.samio.core.controller.annotation.ControllerFor
import com.samio.userservice.model.user.User
import com.samio.userservice.service.user.UserRestService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/users")
@Validated
@ControllerFor(User::class)
@Tag(name = "User Management", description = "Endpoints for managing users and their details.")
class UserController(
    private val service: UserRestService
): RestControllerTemplate<User>() {

    @Operation(summary = "Import users from an Excel file.")
    @PostMapping("/import")
    fun importUsersFromExcel(
        @RequestParam("file") file: MultipartFile,
        @RequestParam sheet: String,
        @RequestParam rows: Int,
        @RequestParam evaluationColumn: String
    ): ResponseEntity<Void> {
        service.importUsersFromExcel(file, sheet, rows, evaluationColumn)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/export/excel")
    fun exportUsersToExcel(
        @RequestParam(required = false) filter: String?,
        @RequestParam(required = false) userInfoProperties: List<String>?
    ): ResponseEntity<ByteArray> {
        val excelData = service.exportUsersToExcel(filter, userInfoProperties)
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=users.xlsx")
            .body(excelData)
    }

    @GetMapping("/export/pdf")
    fun exportUsersToPdf(@RequestParam(required = false) filter: String?): ResponseEntity<ByteArray> {
        val pdfData = service.exportUsersToPdf(filter)
        return ResponseEntity.ok()
            .header("Content-Disposition", "attachment; filename=users.pdf")
            .body(pdfData)
    }

}