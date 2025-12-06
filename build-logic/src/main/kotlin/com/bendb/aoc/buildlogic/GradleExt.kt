package com.bendb.aoc.buildlogic

import org.gradle.api.Action
import org.gradle.api.Task
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.TaskProvider

inline fun <reified T : Task> TaskContainer.register(name: String, action: Action<T>): TaskProvider<T> {
    return this.register(name, T::class.java)
}

inline fun <reified T : Any> ExtensionContainer.getByType(): T {
    return this.getByType(T::class.java)
}
