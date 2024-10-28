package com.example.todoList
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TarefaDao {
    @Insert
    suspend fun inserir(tarefa: Tarefa)

    @Update
    suspend fun atualizarTarefa(tarefa: Tarefa)   // Novo método: Atualizar tarefa existente

    @Delete
    suspend fun deletarTarefa(tarefa: Tarefa)

    @Query("SELECT * FROM tarefa")
    suspend fun buscarTodos(): List<Tarefa>
}
