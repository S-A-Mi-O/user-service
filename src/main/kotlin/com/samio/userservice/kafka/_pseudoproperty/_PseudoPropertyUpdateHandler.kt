package com.samio.userservice.kafka._pseudoproperty


import com.samio.core.application.kafka.EntityEvent
import com.samio.core.application.kafka.handling.abstraction.IUpdateHandler
import com.samio.core.application.validation.modification.ModificationType
import com.samio.core.model.concretion._pseudoProperty._PseudoProperty
import com.samio.core.service.concretion._pseudoProperty._PseudoPropertyApplier
import com.samio.core.service.concretion._pseudoProperty._PseudoPropertyEventService
import com.samio.core.service.concretion._pseudoProperty._PseudoPropertyRestService
import com.samio.userservice.model.user.User
import org.springframework.stereotype.Service

@Suppress("ClassName")
@Service
class _PseudoPropertyUpdateHandler(
    private val pseudoPropertyApplier: _PseudoPropertyApplier,
    private val _pseudoPropertyEventService: _PseudoPropertyEventService,
    private val _pseudoPropertyService: _PseudoPropertyRestService,
) : IUpdateHandler<_PseudoProperty> {
    override fun applyChanges(event: EntityEvent) {
        val before = _pseudoPropertyService.getSingle(event.id)
        _pseudoPropertyEventService.updateByEvent(event)
        if (
            (event.type == ModificationType.UPDATE
            && event.properties[_PseudoProperty::entitySimpleName.name] == User::class.simpleName)
            || before.entitySimpleName == User::class.simpleName)
            pseudoPropertyApplier.renamePseudoPropertyForAllEntitiesOfType(
                User::class.java,
                before.key,
                event.properties[_PseudoProperty::key.name].toString()
            )
    }
}