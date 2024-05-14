package com.example.activecare.common

interface EventHandler<E> {
    fun obtainEvent(event: E)
}