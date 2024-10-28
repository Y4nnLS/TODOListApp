## 🙋‍♂️ Equipe de desenvolvimento

<table align='center'>
  <tr>
    <td align="center">
        <img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/101208372?v=4" width="100px;" alt=""/><br /><sub><b><a href="https://github.com/Y4nnLS">Yann Lucas</a></b></sub></a><br />🤓☝</a></td>
    <td align="center">
        <img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/60533993?v=4" width="100px;" alt=""/><br /><sub><b><a href="https://github.com/Ypsiloon">Felipe Franco</a></b></sub></a><br />👻</a></td>
    <td align="center">
        <img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/96783942?v=4" width="100px;" alt=""/><br /><sub><b><a href="https://github.com/Ypsiloon">Thiago Martins</a></b></sub></a><br />🦤</a></td>
  </table>
  </table>

# Gerenciador de Tarefas

Este projeto é um aplicativo simples de **Gerenciamento de Tarefas** desenvolvido em Kotlin usando **Jetpack Compose** e **Room** como banco de dados local. O app permite que o usuário cadastre, visualize, defina a prioridade e exclua tarefas pessoais.

---

## Propósito do Aplicativo

Desenvolvimento de um aplicativo para o gerenciamento de tarefas, permitindo o cadastro de tarefas com descrição e prioridade, facilitando a visualização e exclusão das tarefas.

---

## Estrutura do Projeto

### 1. Entidade


> [!NOTE]
> `Tarefa`  representa cada tarefa cadastrada pelo usuário.

```kotlin
@Entity(tableName = "tarefa")
data class Tarefa(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,        
    val descricao: String,    
    val prioridade: Int       // 0: Baixa, 1: Média, 2: Alta)
)
```

- **id**: PRIMARY KEY.
- **nome**: Nome da tarefa.
- **descricao**: Descrição da tarefa.
- **prioridade**: Prioridade da tarefa.

---

### 2. DAO (Data Access Object)

> [!NOTE]
> `TarefaDao` é responsável por todas as operações de banco de dados relacionadas à entidade `Tarefa`, incluindo os métodos de criação, leitura e exclusão.


```kotlin
@Dao
interface TarefaDao {
    @Insert  
    suspend fun inserir(tarefa: Tarefa)       //Insere uma nova tarefa no banco de dados.

    @Query("SELECT * FROM tarefa")
    suspend fun buscarTodos(): List<Tarefa>   //Recupera e retorna as tarefas cadastradas no banco de dados.

    @Delete
    suspend fun deletarTarefa(tarefa: Tarefa) // Exclui uma tarefa específica do banco de dados com base em seu identificador (`id`).
}
```
---

## Funcionalidades

- **Cadastro de Tarefas**: Permite adicionar uma nova tarefa com nome, descrição e prioridade.
- **Visualização das Tarefas**: Exibe todas as tarefas cadastradas.
- **Exclusão de Tarefas**: Permite deletar uma tarefa específica da lista.

---

## Tecnologias Utilizadas
- [**Kotlin**](https://kotlinlang.org/docs/home.html) 
- [**Jetpack Compose**](https://developer.android.com/jetpack/compose/documentation) 
- [**Room**](https://developer.android.com/training/data-storage/room) 
- [**Coroutines**](https://kotlinlang.org/docs/coroutines-overview.html) 

---

## Como Executar o Projeto

1. Clone o repositório.
2. Abra o projeto no Android Studio.
3. Compile e execute no emulador ou em um dispositivo físico.

---

## Contribuições

Contribuições são bem-vindas! Se você tiver sugestões de melhorias ou novas funcionalidades, fique à vontade para abrir um *pull request*.

--- 

## Licença

Este projeto é de uso livre para fins educacionais e pessoais.

--- 
