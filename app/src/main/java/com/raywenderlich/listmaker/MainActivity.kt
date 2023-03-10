package com.raywenderlich.listmaker

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.listmaker.Details.DetailActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TodoListAdapter.TodoListClickListener {

    lateinit var todoListRecyclerView: RecyclerView
    val listDataManager: ListDataManager = ListDataManager(this)

    companion object {
        const val INTENT_LIST_KEY = "list"
        const val LIST_DETAIL_REQUEST_CODE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        todoListRecyclerView = findViewById(R.id.list_recyclerview)
        todoListRecyclerView.layoutManager = LinearLayoutManager(this)
        todoListRecyclerView.adapter = TodoListAdapter(listDataManager.readLists(), this)
        fab.setOnClickListener { _ ->
            showCreateTodoListDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LIST_DETAIL_REQUEST_CODE) {
            data?.let {
                val list = data.getParcelableExtra<TaskList>(INTENT_LIST_KEY)!!
                listDataManager.saveList(list)
                updateLists()
            }
        }
    }

    private fun updateLists() {
        val lists = listDataManager.readLists()
        todoListRecyclerView.adapter = TodoListAdapter(lists, this)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCreateTodoListDialog() {
        var dialogTitle = "What is the name of your list"
        var positiveButtonTitle = "Create"
        val myDialog = AlertDialog.Builder(this)
        val todoTitleEditText = EditText(this)
        todoTitleEditText.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
        myDialog.setTitle(dialogTitle)
        myDialog.setView(todoTitleEditText)
        myDialog.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            val adapter = todoListRecyclerView.adapter as TodoListAdapter
            val list = TaskList(todoTitleEditText.text.toString())
            listDataManager.saveList(list)
            showTaskListItems(list)
            adapter.addList(list)
            dialog.dismiss()
        }
        myDialog.create().show()

    }

    private fun showTaskListItems(list: TaskList) {
        val taskListItem = Intent(this, DetailActivity::class.java)
        taskListItem.putExtra(INTENT_LIST_KEY, list)
        startActivityForResult(taskListItem, LIST_DETAIL_REQUEST_CODE)
    }

    override fun listItemClicked(list: TaskList, position: Int) {
        showTaskListItems(list)
    }

}
