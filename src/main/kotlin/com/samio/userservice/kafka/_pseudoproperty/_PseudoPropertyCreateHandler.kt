package com.samio.userservice.kafka._pseudoproperty

import com.samio.core.application.kafka.EntityEvent
import com.samio.core.application.kafka.handling.abstraction.ICreateHandler
import com.samio.core.model.abstraction.AugmentableBaseEntity
import com.samio.core.model.concretion._pseudoProperty._PseudoProperty
import com.samio.core.service.concretion._pseudoProperty._PseudoPropertyApplier
import com.samio.core.service.concretion._pseudoProperty._PseudoPropertyEventService
import com.samio.userservice.model.user.User
import org.springframework.stereotype.Service

@Suppress("ClassName")
@Service
class _PseudoPropertyCreateHandler(
    private val pseudoPropertyApplier: _PseudoPropertyApplier,
    private val _pseudoPropertyEventService: _PseudoPropertyEventService,
) : ICreateHandler<_PseudoProperty> {
    override fun applyChanges(event: EntityEvent) {
        _pseudoPropertyEventService.createByEvent(event)
        if (event.properties[_PseudoProperty::entitySimpleName.name] == User::class.simpleName)
            pseudoPropertyApplier.addPseudoPropertyToAllEntitiesOfType(
                User::class.java as Class<out AugmentableBaseEntity>,
                event.properties["key"].toString()
            )
    }
}