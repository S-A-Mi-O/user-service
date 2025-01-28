package com.samio.userservice.persistence.user

import com.samio.core.persistence.abstraction.EntityPersistenceAdapter
import com.samio.core.persistence.annotation.PersistenceAdapterFor
import com.samio.userservice.model.user.User
import org.springframework.stereotype.Service

@Service
@PersistenceAdapterFor(User::class)
class UserPersistenceAdapter: EntityPersistenceAdapter<User>()

