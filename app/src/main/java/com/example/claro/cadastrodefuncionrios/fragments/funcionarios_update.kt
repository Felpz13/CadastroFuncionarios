package com.example.claro.cadastrodefuncionrios.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.claro.cadastrodefuncionrios.R
import kotlinx.android.synthetic.main.departamentos_cadastro.view.*
import kotlinx.android.synthetic.main.funcionarios_cadastro.view.*

class funcionarios_update : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        var fgView = inflater.inflate(R.layout.funcionarios_cadastro, container, false)

        Log.d("CURA", "FUNCIONARIO CADASTRO")

        fgView.bt_fun_voltar.setOnClickListener { view ->

            getFragmentManager()?.beginTransaction()?.replace(
                R.id.container,
                funcionarios()
            )?.remove(this)?.commit()

        }

        fgView.bt_fun_inserir.setOnClickListener { view ->

            Toast.makeText(activity, "INSERIR", Toast.LENGTH_SHORT).show()

        }

        return fgView
    }
}
