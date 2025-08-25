package com.max.gtee.gteemax.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "user_account")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int? = null,

    val username: String,

    val password: String,

    val views: Int = 0,

    @OneToOne
    val favorite: Video,
)