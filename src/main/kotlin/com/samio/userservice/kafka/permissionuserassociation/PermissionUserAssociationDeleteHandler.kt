package com.samio.userservice.kafka.permissionuserassociation

import com.samio.core.application.kafka.EntityEvent
import com.samio.core.application.kafka.EntityEventProducer
import com.samio.core.application.kafka.handling.abstraction.IDeleteHandler
import com.samio.core.model.concretion.permissionuserassociation.PermissionUserAssociation
import com.samio.core.service.concretion.permissionuserassociation.PermissionUserAssociationEventService
import com.samio.userservice.model.user.User
import com.samio.userservice.persistence.user.UserPersistenceAdapter
import org.springframework.stereotype.Service
import java.util.*

@Service
class PermissionUserAssociationDeleteHandler(
    private val permissionUserAssociationEventService: PermissionUserAssociationEventService,
    private val userAdapter: UserPersistenceAdapter,
    private val entityEventProducer: EntityEventProducer,
) : IDeleteHandler<PermissionUserAssociation> {
    override fun applyChanges(event: EntityEvent) {
        permissionUserAssociationEventService.deleteByEvent(event)
        val user = userAdapter.getById(event.id)
        val permissionId =
            event.properties[PermissionUserAssociation::permissionId.name] as? UUID
                ?: throw IllegalArgumentException("PermissionId is required")
        user.permissions.toMutableList().remove(permissionId)
        userAdapter.save(user)
        entityEventProducer.emit(
            User::class.java.simpleName,
            user.id,
            event.type,
            mutableMapOf(User::permissions.name to user.permissions)
        )
    }

}
