package com.example.todoList

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CadastroTarefasScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroTarefasScreen() {
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var prioridade by remember { mutableStateOf(0) } // 0: Baixa, 1: Média, 2: Alta
    val tarefas = remember { mutableStateListOf<Tarefa>() }
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val tarefaDao = db.tarefaDao()

    // Carregar as tarefas ao montar o Composable
    LaunchedEffect(Unit) {
        tarefas.clear()
        tarefas.addAll(buscarTarefas(tarefaDao))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown para selecionar a prioridade
        var expanded by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { expanded = true }) {
                Text("Prioridade: ${if (prioridade == 0) "Baixa" else if (prioridade == 1) "Média" else "Alta"}")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(text = { Text("Baixa") }, onClick = { prioridade = 0; expanded = false })
                DropdownMenuItem(text = { Text("Média") }, onClick = { prioridade = 1; expanded = false })
                DropdownMenuItem(text = { Text("Alta") }, onClick = { prioridade = 2; expanded = false })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val nomeTarefa = nome
                val descricaoTarefa = descricao

                // Verifique se os campos obrigatórios estão preenchidos
                if (nomeTarefa.isNotBlank() && descricaoTarefa.isNotBlank()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val tarefa = Tarefa(
                            nome = nomeTarefa,
                            descricao = descricaoTarefa,
                            prioridade = prioridade
                        )

                        tarefaDao.inserir(tarefa)
                        val tarefasAtualizadas = buscarTarefas(tarefaDao)

                        withContext(Dispatchers.Main) {
                            tarefas.clear()
                            tarefas.addAll(tarefasAtualizadas)
                        }
                    }
                    // Limpa os campos de texto após o cadastro
                    nome = ""
                    descricao = ""
                    prioridade = 0
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Cadastrar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    db.clearAllTables()
                    withContext(Dispatchers.Main) {
                        tarefas.clear()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Limpar Banco de Dados")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(tarefas) { tarefa ->
                TarefaItem(tarefa, tarefaDao, tarefas)
            }
        }
    }
}

@Composable
fun TarefaItem(tarefa: Tarefa, tarefaDao: TarefaDao, tarefas: SnapshotStateList<Tarefa>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = "Nome: ${tarefa.nome}")
            Text(text = "Descrição: ${tarefa.descricao}")
            Text(text = "Prioridade: ${if (tarefa.prioridade == 0) "Baixa" else if (tarefa.prioridade == 1) "Média" else "Alta"}")
        }

        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                tarefaDao.deletarTarefa(tarefa)
                val tarefasAtualizadas = tarefaDao.buscarTodos()

                withContext(Dispatchers.Main) {
                    tarefas.clear()
                    tarefas.addAll(tarefasAtualizadas)
                }
            }
        }) {
            Text("Deletar")
        }
    }
}

// Função para buscar todas as tarefas
suspend fun buscarTarefas(tarefaDao: TarefaDao): List<Tarefa> {
    return try {
        tarefaDao.buscarTodos()
    } catch (e: Exception) {
        Log.e("Erro ao buscar", "Erro ao buscar tarefas: ${e.message}")
        emptyList()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCadastroTarefasScreen() {
    CadastroTarefasScreen()
}
