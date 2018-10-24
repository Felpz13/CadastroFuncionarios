package com.example.claro.cadastrodefuncionrios

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dep_item.*
import kotlinx.android.synthetic.main.departamentos.*

class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titulo.setText("DEPARTAMENTOS")

        supportFragmentManager.beginTransaction().replace(R.id.container, departamentos()).commit()
    }
}
