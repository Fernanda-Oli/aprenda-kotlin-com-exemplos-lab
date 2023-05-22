// [Template no Kotlin Playground](https://pl.kotl.in/WcteahpyN)

enum class Nivel { BASICO, INTERMEDIARIO, DIFICIL }

data class Usuario(val nome: String, var id: Int = 0)

data class ConteudoEducacional(val nome: String, val duracao: Int)

data class Formacao(val nome: String, val nivel: Nivel, var conteudos: List<ConteudoEducacional>) {
    val inscricoes: MutableList<Matricula> = mutableListOf()
}

data class Matricula(val usuario: Usuario, val formacao: Formacao, val dataMatricula: String)

class Curso(val nome: String) {
    val formacoes = mutableListOf<Formacao>()
}

class GerenciadorCursos {
    private val cursos = mutableListOf<Curso>()
    private var contadorIdUsuario = 0

    fun adicionarCurso(curso: Curso) {
        cursos.add(curso)
    }

    fun criarFormacao(curso: Curso, formacao: Formacao) {
        curso.formacoes.add(formacao)
    }

    fun matricularUsuario(usuario: Usuario, formacao: Formacao) {
        val matriculaExistente = cursos.flatMap { it.formacoes }
                .flatMap { it.inscricoes }
                .any { it.usuario == usuario && it.formacao == formacao }

        if (matriculaExistente) {
            println("Usuário '${usuario.nome}' já está matriculado na formação '${formacao.nome}'.")
        } else {
            contadorIdUsuario++
            usuario.id = contadorIdUsuario
            val matricula = Matricula(usuario, formacao, "22/05/2023")
            formacao.inscricoes.add(matricula)
            println("Usuário '${usuario.nome}' (ID: ${usuario.id}) matriculado na formação '${formacao.nome}'.")
        }
    }

    fun consultarInscricoesPorFormacao(formacao: Formacao) {
        val inscricoes = formacao.inscricoes.map { it.usuario.nome }
        println("Inscritos na Formação '${formacao.nome}': ${inscricoes.joinToString(", ")}")
    }

    fun consultarInscricoesPorCurso(curso: Curso) {
        val inscricoes = curso.formacoes.flatMap { it.inscricoes }
                .map { it.usuario.nome }
        println("Inscritos no Curso '${curso.nome}': ${inscricoes.joinToString(", ")}")
    }
}

fun main() {
    val gerenciador = GerenciadorCursos()

    val curso1 = Curso("Desenvolvimento Web")
    val curso2 = Curso("Desenvolvimento Multiplataforma")
    val curso3 = Curso("Ciência de Dados")

    gerenciador.adicionarCurso(curso1)
    gerenciador.adicionarCurso(curso2)
    gerenciador.adicionarCurso(curso3)

    val formacao1 = Formacao("Formação Front-end", Nivel.INTERMEDIARIO, listOf(
            ConteudoEducacional("HTML", 60),
            ConteudoEducacional("CSS", 90),
            ConteudoEducacional("JavaScript", 120)
    ))
    val formacao2 = Formacao("Formação Back-end", Nivel.BASICO, listOf(
            ConteudoEducacional("Java", 90),
            ConteudoEducacional("Spring Boot", 120),
            ConteudoEducacional("Banco de Dados", 90)
    ))

    val formacao3 = Formacao("Formação Mobile", Nivel.DIFICIL, listOf(
            ConteudoEducacional("Java", 90),
            ConteudoEducacional("Kotlin", 120),
            ConteudoEducacional("MVVM", 90)
    ))

    gerenciador.criarFormacao(curso1, formacao1)
    gerenciador.criarFormacao(curso2, formacao1)
    gerenciador.criarFormacao(curso3, formacao2)
    gerenciador.criarFormacao(curso3, formacao3)

    val usuario1 = Usuario("João")
    val usuario2 = Usuario("Maria")
    val usuario3 = Usuario("Pedro")
    val usuario4 = Usuario("DIO")

    gerenciador.matricularUsuario(usuario1, formacao1)
    gerenciador.matricularUsuario(usuario2, formacao3)
    gerenciador.matricularUsuario(usuario4, formacao2)
    gerenciador.matricularUsuario(usuario3, formacao1)

    gerenciador.consultarInscricoesPorFormacao(formacao1)
    gerenciador.consultarInscricoesPorFormacao(formacao2)
    gerenciador.consultarInscricoesPorFormacao(formacao3)

    gerenciador.consultarInscricoesPorCurso(curso1)
    gerenciador.consultarInscricoesPorCurso(curso2)
    gerenciador.consultarInscricoesPorCurso(curso3)
}
