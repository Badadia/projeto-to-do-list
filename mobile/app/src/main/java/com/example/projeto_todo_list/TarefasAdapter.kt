package com.example.projeto_todo_list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.projeto_todo_list.api.Tarefa
import com.example.projeto_todo_list.databinding.ItemTarefaBinding

class TarefasAdapter(private val listaTarefas: List<Tarefa>, private val onClick: (Tarefa) -> Unit) :
    RecyclerView.Adapter<TarefasAdapter.TarefaViewHolder>() {

    inner class TarefaViewHolder(val binding: ItemTarefaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val binding = ItemTarefaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TarefaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        val tarefa = listaTarefas[position]

        holder.binding.tvTitulo.text = tarefa.titulo
        holder.binding.tvDescricao.text = tarefa.descricao
        holder.binding.tvPrioridade.text = tarefa.prioridade
        holder.itemView.setOnClickListener { onClick(tarefa) }

        when (tarefa.prioridade) {
            "Alta" -> holder.binding.tvPrioridade.setTextColor(Color.RED)
            "Média" -> holder.binding.tvPrioridade.setTextColor(Color.parseColor("#FFA500")) // Laranja
            "Baixa" -> holder.binding.tvPrioridade.setTextColor(Color.parseColor("#008000")) // Verde
        }
    }

    override fun getItemCount(): Int = listaTarefas.size
}