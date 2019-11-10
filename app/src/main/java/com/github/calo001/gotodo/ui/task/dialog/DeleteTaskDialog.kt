package com.github.calo001.gotodo.ui.task.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.github.calo001.gotodo.R
import kotlinx.android.synthetic.main.delete_task_dialog.*

class DeleteTaskDialog: DialogFragment() {
    private lateinit var listener: DeleteTaskListener

    companion object {
        private const val EXTRA_ID = "id"
        private const val EXTRA_TITLE = "title"
        private const val EXTRA_DESCRIPTION = "description"
        const val TAG = "delete_dialog"

        fun newInstance(id: Int, title: String, description: String): DeleteTaskDialog {
            val fragment = DeleteTaskDialog()

            val args = Bundle()
            args.putInt(EXTRA_ID, id)
            args.putString(EXTRA_TITLE, title)
            args.putString(EXTRA_DESCRIPTION, description)

            fragment.arguments = args
            return fragment
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
        return inflater.inflate(R.layout.delete_task_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt(EXTRA_ID)
        val title = arguments?.getString(EXTRA_TITLE)
        val description = arguments?.getString(EXTRA_DESCRIPTION)

        tvTitle.text = title
        tvDescription.text = description

        btnOk.setOnClickListener {
            id?.let { listener.deleteTask(it) }
            dismiss()
        }

        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as DeleteTaskListener
        } catch (error: ClassCastException) {
            throw ClassCastException(error.message)
        }
    }
}

interface DeleteTaskListener {
    fun deleteTask(id: Int)
}