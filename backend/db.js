const { Pool } = require("pg")

const pool = new Pool({
  user: "postgres",
  host: "localhost",
  database: "postgres",
  password: "docker",
  port: 5432,
})

const initDb = async () => {
  try {
    await pool.query(`
            CREATE TABLE IF NOT EXISTS tarefas (
                id SERIAL PRIMARY KEY,
                titulo VARCHAR(100),
                descricao TEXT,
                prioridade VARCHAR(20)
            );
        `)

    await pool.query(`
            CREATE TABLE IF NOT EXISTS usuarios (
                id SERIAL PRIMARY KEY,
                usuario VARCHAR(50) UNIQUE NOT NULL,
                senha VARCHAR(50) NOT NULL
            );
        `)

    const usuarioPadrao = await pool.query(
      "SELECT * FROM usuarios WHERE usuario = 'aluno'",
    )

    if (usuarioPadrao.rows.length === 0) {
      await pool.query(
        "INSERT INTO usuarios (usuario, senha) VALUES ($1, $2)",
        ["aluno", "1234"],
      )
      console.log("Usuário padrão 'aluno' criado com sucesso!")
    }

    console.log("Banco de dados sincronizado (Tarefas e Usuários)!")
  } catch (error) {
    console.error("Erro ao iniciar o banco:", error)
  }
}

module.exports = { pool, initDb }
