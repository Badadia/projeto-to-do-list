package com.example.projeto_todo_list

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projeto_todo_list.api.RetrofitClient
import com.example.projeto_todo_list.api.Tarefa
import com.example.projeto_todo_list.databinding.ActivityFormTarefaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FormTarefaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormTarefaBinding
    private var tarefaId: Int? = null // Se for nulo, é cadastro. Se tiver valor, é edição.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormTarefaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar o Spinner (Opções de Prioridade)
        val prioridades = arrayOf("Alta", "Média", "Baixa")
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, prioridades)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPrioridade.adapter = adapterSpinner

        // Verifica se veio dados para Edição (clique na lista)
        // Vamos passar esses dados via Intent na próxima etapa
        if (intent.hasExtra("id")) {
            tarefaId = intent.getIntExtra("id", 0)
            binding.etTitulo.setText(intent.getStringExtra("titulo"))
            binding.etDescricao.setText(intent.getStringExtra("descricao"))

            // Selecionar o spinner correto
            val prioridade = intent.getStringExtra("prioridade")
            val posicao = prioridades.indexOf(prioridade)
            if (posicao >= 0) binding.spinnerPrioridade.setSelection(posicao)

            binding.btnSalvar.text = "ATUALIZAR"
            binding.btnExcluir.visibility = View.VISIBLE
        }

        // Botão Salvar
        binding.btnSalvar.setOnClickListener {
            salvarTarefa()
        }

        // Botão Excluir
        binding.btnExcluir.setOnClickListener {
            excluirTarefa()
        }
    }