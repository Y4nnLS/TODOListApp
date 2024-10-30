## 🙋‍♂️ Equipe de desenvolvimento

<table align='center'>
  <tr>
    <td align="center">
        <img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/101208372?v=4" width="100px;" alt=""/><br /><sub><b><a href="https://github.com/Y4nnLS">Yann Lucas</a></b></sub></a><br />🤓☝</a></td>
    <td align="center">
        <img style="border-radius: 50%;" src="https://avatars.githubusercontent.com/u/60533993?v=4" width="100px;" alt=""/><br /><sub><b><a href="https://github.com/Ypsiloon">Felipe Franco</a></b></sub></a><br />👻</a></td>
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
### 3. Configuração do Banco de Dados com Room
> [!NOTE]
> A classe de banco de dados é configurada utilizando RoomDatabase com o padrão Singleton para garantir uma única instância.

```kotlin

@Database(entities = [Tarefa::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tarefaDao(): TarefaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
```

---

## Funcionalidades

- **Cadastro de Tarefas**: Permite adicionar uma nova tarefa com nome, descrição e prioridade.
- **Visualização das Tarefas**: Exibe todas as tarefas cadastradas.
- **Exclusão de Tarefas**: Permite deletar uma tarefa específica da lista.

---
## Melhorias Criativas
- **Validação de Campos Obrigatórios com Toast's**
  - Antes de salvar uma tarefa, o aplicativo fara a verificação e exibirá um Toast caso nome ou descrição estejam vazios. Informando o usuário que ele deve preencher os campos obrigatórios.
  - Essa validação evita que tarefas incompletas/mal descritas sejam salvas, melhorando a experiência do usuário na organização de suas tarefas.

- **Adição de Data de Conclusão e Notificação de Lembrete**
  - O usuário poderá definir uma data de conclusão para cada tarefa e selecionar um número de dias antes do prazo para ser notificado. Essa notificação seria enviada com antecedência, alertando o usuário sobre a aproximação do prazo de uma tarefa.
  - Essa funcionalidade ajuda os usuários a se organizarem melhor, permitindo que eles recebam lembretes antecipados para evitar atrasos.

- **Organização das Tarefas por Projetos**
  - Cada tarefa poderá ser associada a um projeto específico. Os projetos funcionariam como agrupadores, permitindo que o usuário organize as tarefas de forma hierárquica. Cada projeto conteria suas próprias tarefas, que podem ser visualizadas separadamente.
  - Essa organização hierárquica facilita a gestão de tarefas relacionadas a um objetivo ou projeto maior, dando mais clareza ao usuário e possibilitando o gerenciamento de atividades mais complexas.

- **Ordenação e Filtros**
  - Permitir que o usuário filtre as tarefas por prioridade (ex.: Alta, Média, Baixa). Assim, o usuário pode visualizar apenas as tarefas mais importantes, o que ajuda na organização e foco nas tarefas de maior prioridade.
  - Adicionar uma opção para ordenar as tarefas por diferentes critérios, como ordem alfabética, ordem de prioridade, por data de conclusão e por projeto.
  - Essas ordenações e filtragens proporcionam um maior controle e visibilidade das tarefas, melhorando a produtividade e ajudando o usuário a visualizar suas tarefas do jeito que preferir.

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
