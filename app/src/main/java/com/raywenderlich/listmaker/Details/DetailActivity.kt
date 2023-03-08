package com.raywenderlich.listmaker.Details

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.raywenderlich.listmaker.MainActivity
import com.raywenderlich.listmaker.R
import com.raywenderlich.listmaker.TaskList
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var list: TaskList
    lateinit var taskListRecyclerView: RecyclerView
    lateinit var addTaskButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY) as TaskList
        title = list.name

        taskListRecyclerView = findViewById(R.id.task_list_recyclerview)
        taskListRecyclerView.layoutManager = LinearLayoutManager(this)
        taskListRecyclerView.adapter = DetailListAdapter(list)

        addTaskButton = findViewById(R.id.add_task_button)
        addTaskButton.setOnClickListener {
            showCreateTaskDialog()
        }

    }

    private fun showCreateTaskDialog() {
        val taskEditText = EditText(this)
        taskEditText.inputType = InputType.TYPE_CLASS_TEXT
        AlertDialog.Builder(this).setTitle(R.string.task_to_add)
            .setView(taskEditText)
            .setPositiveButton(R.string.task_to_add) { dialog, _ ->
                val task = taskEditText.text.toString()
                list.tasks.add(task)
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onBackPressed() {
        val bundle = Bundle()
        bundle.putParcelable(MainActivity.INTENT_LIST_KEY, list)
        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }
}