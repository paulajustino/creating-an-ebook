package com.example.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private val tasks = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtendo referências para os elementos do layout
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewTasks)
        val editTextTask: EditText = findViewById(R.id.editTextTask)
        val buttonAdd: Button = findViewById(R.id.buttonAdd)

        // Inicializando o adapter com a lista de tarefas
        taskAdapter = TaskAdapter(tasks)
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configurando o clique do botão de adicionar tarefa
        buttonAdd.setOnClickListener {
            val task = editTextTask.text.toString()
            if (task.isNotEmpty()) {
                tasks.add(task)  // Adicionando a tarefa à lista
                taskAdapter.notifyItemInserted(tasks.size - 1)  // Notificando o adapter que um item foi inserido
                editTextTask.text.clear()  // Limpando o campo de entrada de texto
            }
        }
    }


    // Classe interna TaskAdapter para gerenciar a RecyclerView (Lista de Tarefas)
    inner class TaskAdapter(private val tasks: MutableList<String>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

        // ViewHolder que contém as referências para os elementos de cada item da lista
        inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val taskTextView: TextView = itemView.findViewById(R.id.textViewTask)
            val removeButton: Button = itemView.findViewById(R.id.buttonRemove)
        }

        // Inflando o layout do item da lista e criando um ViewHolder
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
            return TaskViewHolder(view)
        }

        // Vinculando os dados do item à posição específica do RecyclerView
        override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
            val task = tasks[position]
            holder.taskTextView.text = task

            // Configurando o clique do botão de remoção
            holder.removeButton.setOnClickListener {
                tasks.removeAt(position)  // Removendo a tarefa da lista
                notifyItemRemoved(position)  // Notificando o adapter que um item foi removido
                notifyItemRangeChanged(position, tasks.size)  // Atualizando a faixa de itens afetados
            }
        }

        // Retornando o número total de itens na lista de tarefas
        override fun getItemCount() = tasks.size
    }
}