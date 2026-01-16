const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');

const app = express();
app.use(cors());
app.use(bodyParser.json());

let tarefas = [
    { id: 1, titulo: "Estudar Python", descricao: "Ver aulas sobre Álgebra Linear e suas aplicações no código", prioridade: "Alta" },
    { id: 2, titulo: "Fazer compras", descricao: "Comprar café, leite e pão", prioridade: "Média" },
    { id: 3, titulo: "Limpar o quarto", descricao: "Organizar a mesa do computador", prioridade: "Baixa" },
    { id: 4, titulo: "Pagar internet", descricao: "Vence dia 10", prioridade: "Alta" },
    { id: 5, titulo: "Assistir série", descricao: "Terminar a temporada nova", prioridade: "Baixa" }
];
let idContador = 6;

app.post('/login', (req, res) => {
const { usuario, senha } = req.body;
console.log(`Tentativa de login: ${usuario}`);

    if (usuario === 'aluno' && senha === '1234') {
        res.json({ message: "Login realizado", token: "token_fake_123" });
    } else {
        res.status(401).json({ message: "Usuário ou senha incorretos" });
    }

});

app.get('/tarefas', (req, res) => {
res.json(tarefas);
});

app.post('/tarefas', (req, res) => {
const novaTarefa = req.body;
if(!novaTarefa.titulo) {
return res.status(400).json({message: "Titulo é obrigatório"});
}

    novaTarefa.id = idContador++;
    tarefas.push(novaTarefa);
    res.status(201).json(novaTarefa);

});

app.put('/tarefas/:id', (req, res) => {
const id = parseInt(req.params.id);
const index = tarefas.findIndex(t => t.id === id);

    if (index !== -1) {
        tarefas[index] = { ...tarefas[index], ...req.body, id: id };
        res.json(tarefas[index]);
    } else {
        res.status(404).json({ message: "Tarefa não encontrada" });
    }

});

app.delete('/tarefas/:id', (req, res) => {
const id = parseInt(req.params.id);
tarefas = tarefas.filter(t => t.id !== id);
res.status(204).send();
});

const PORT = 3000;
app.listen(PORT, () => {
console.log(`Servidor rodando! Acesse: http://localhost:${PORT}/tarefas`);
});