package com.hianuy.todolist.project.extension

import android.text.Editable
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun String.toText(): Editable =
        Editable.Factory.getInstance().newEditable(this)
