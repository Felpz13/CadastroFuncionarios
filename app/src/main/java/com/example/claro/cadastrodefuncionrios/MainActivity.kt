package com.example.claro.cadastrodefuncionrios

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.claro.cadastrodefuncionrios.auxiliares.dbHelper
import com.example.claro.cadastrodefuncionrios.fragments.departamentos


class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicio()

    }

    fun inicio()
    {
        supportFragmentManager.beginTransaction().replace(R.id.container,
            departamentos()
        ).commit()
    }

}
