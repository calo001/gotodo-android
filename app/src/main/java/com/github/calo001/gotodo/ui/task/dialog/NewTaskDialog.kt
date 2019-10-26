package com.github.calo001.gotodo.ui.task.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.github.calo001.gotodo.R
import com.github.calo001.gotodo.util.Validator
import kotlinx.android.synthetic.main.task_dialog.*

class NewTaskDialog: DialogFragment() {
    private lateinit var listener: NewTaskListener
    private val validator = Validator()

    companion object {
        const val TAG = "new_dialog"
        fun newInstance(): NewTaskDialog {
            return NewTaskDialog()
        }
    }

    override fun onStart() {
        super.onStart()

        dialog?.let {
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.setLayout(width, height)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.task_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validator.add(inputLayoutTitle)
        tvDialogTitle.text = resources.getString(R.string.add_new_task)

        btnOk.setOnClickListener {
            val title = inputLayoutTitle.editText?.text.toString()
            val description = inputLayoutDescription.editText?.text.toString()

            if (validator.result()) {
                listener.createTask(title, description)
                dismiss()
            }
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as NewTaskListener
        } catch (error: ClassCastException) {
            throw ClassCastException(error.message)
        }
    }
}

interface NewTaskListener {
    fun createTask(title: String, description: String)
}