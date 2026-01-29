const express = require("express")
const cors = require("cors")
const bodyParser = require("body-parser")
const { pool, initDb } = require("./db")

const app = express()
app.use(cors())
app.use(bodyParser.json())

initDb()

app.post("/login", async (req, res) => {
  const { usuario, senha } = req.body

  try {
    const query = "SELECT * FROM usuarios WHERE usuario = $1 AND senha = $2"
    const values = [usuario, senha]

    const resultado = await pool.query(query, values)

    if (resultado.rows.length > 0) {
      const userEncontrado = resultado.rows[0]
      res.json({
        message: "Login realizado",
        token: "token_fake_123",
        usuario: userEncontrado.usuario,
      })
    } else {
      res.status(401).json({ message: "Usuário ou senha incorretos" })
    }
  } catch (err) {
    console.error(err)
    res.status(500).json({ error: "Erro no servidor ao tentar logar" })
  }
})

app.get("/tarefas", async (req, res) => {
  try {
    const resultado = await pool.query("SELECT * FROM tarefas ORDER BY id ASC")
    res.json(resultado.rows)
  } catch (err) {
    console.error(err)
    res.status(500).json({ error: "Erro ao buscar tarefas" })
  }
})

app.post("/tarefas", async (req, res) => {
  const { titulo, descricao, prioridade } = req.body
  try {
    const query =
      "INSERT INTO tarefas (titulo, descricao, prioridade) VALUES ($1, $2, $3) RETURNING *"
    const values = [titulo, descricao, prioridade]

    const resultado = await pool.query(query, values)
    res.status(201).json(resultado.rows[0])
  } catch (err) {
    console.error(err)
    res.status(500).json({ error: "Erro ao salvar tarefa" })
  }
})

app.put("/tarefas/:id", async (req, res) => {
  const { id } = req.params
  const { titulo, descricao, prioridade } = req.body
  try {
    const query =
      "UPDATE tarefas SET titulo = $1, descricao = $2, prioridade = $3 WHERE id = $4 RETURNING *"
    const values = [titulo, descricao, prioridade, id]

    const resultado = await pool.query(query, values)

    if (resultado.rows.length > 0) {
      res.json(resultado.rows[0])
    } else {
      res.status(404).json({ message: "Tarefa não encontrada" })
    }
  } catch (err) {
    console.error(err)
    res.status(500).json({ error: "Erro ao atualizar" })
  }
})

app.delete("/tarefas/:id", async (req, res) => {
  const { id } = req.params
  try {
    const query = "DELETE FROM tarefas WHERE id = $1"
    await pool.query(query, [id])
    res.status(204).send()
  } catch (err) {
    console.error(err)
    res.status(500).json({ error: "Erro ao excluir" })
  }
})

const PORT = 3000
app.listen(PORT, () => {
  console.log(`Servidor rodando com PostgreSQL na porta ${PORT}`)
})
