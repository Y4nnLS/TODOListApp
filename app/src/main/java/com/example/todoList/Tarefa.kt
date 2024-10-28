package com.example.todoList

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tarefa")
data class Tarefa(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val descricao: String,
    val prioridade: Int
)
