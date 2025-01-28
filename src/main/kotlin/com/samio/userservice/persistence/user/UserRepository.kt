package com.samio.userservice.persistence.user

import com.samio.core.persistence.abstraction.EntityRepository
import com.samio.userservice.model.user.User
import java.util.*

@Suppress("unused")
interface UserRepository : EntityRepository<User, UUID>