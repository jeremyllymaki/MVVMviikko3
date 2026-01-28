package com.example.week3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.week3.domain.Priority
import com.example.week3.domain.Task
import java.time.LocalDate

@Composable
fun HomeScreen(
    taskViewModel: TaskViewModel = viewModel()
) {
    val tasks by taskViewModel.tasks.collectAsState()
    var newTitle by remember { mutableStateOf("") }
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Column(Modifier.padding(16.dp)) {
        Text("Task List", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))


        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                placeholder = { Text("Add new task...") },
                singleLine = true
            )
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newTitle.isNotBlank()) {
                        taskViewModel.addTask(
                            Task(
                                id = (tasks.maxOfOrNull { it.id } ?: 0) + 1,
                                title = newTitle,
                                description = "",
                                priority = Priority.LOW,
                                dueDate = LocalDate.now(),
                                done = false
                            )
                        )
                        newTitle = ""
                    }
                },
                modifier = Modifier.height(56.dp)
            ) {
                Text("Add")
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(tasks) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { selectedTask = task },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(modifier = Modifier.weight(1f)) {
                            Checkbox(
                                checked = task.done,
                                onCheckedChange = { taskViewModel.toggleDone(task.id) }
                            )
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text(
                                    task.title,
                                    style = MaterialTheme.typography.titleMedium,

                                )
                                if (task.description.isNotEmpty()) {
                                    Text(
                                        task.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Text(
                                    "Due: ${task.dueDate}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        IconButton(onClick = { taskViewModel.removeTask(task.id) }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }

    selectedTask?.let {
        DetailDialog(
            task = it,
            onDismiss = { selectedTask = null },
            onSave = { updated ->
                taskViewModel.updateTask(updated)
                selectedTask = null
            },
            onDelete = {
                taskViewModel.removeTask(it.id)
                selectedTask = null
            }
        )
    }
}