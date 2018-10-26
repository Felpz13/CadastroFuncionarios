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

class departamentos_update : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        var fgView = inflater.inflate(R.layout.departamentos_cadastro, container, false)

        Log.d("CURA", "DPTO CADASTRO")

        fgView.bt_cad_voltar.setOnClickListener { view ->

            getFragmentManager()?.beginTransaction()?.replace(
                R.id.container,
                departamentos()
            )?.remove(this)?.commit()

        }

        fgView.bt_cad_inserir.setOnClickListener { view ->

            Toast.makeText(activity, "INSERIR", Toast.LENGTH_SHORT).show()

        }

        return fgView
    }
}
