package com.example.claro.cadastrodefuncionrios

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.departamentos_cadastro.*
import kotlinx.android.synthetic.main.departamentos_cadastro.view.*

class departamentos_cadastro : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        var fgView = inflater.inflate(R.layout.departamentos_cadastro, container, false)

        fgView.bt_cad_voltar.setOnClickListener { view ->

            getFragmentManager()?.beginTransaction()?.replace(R.id.container, departamentos())?.remove(this)?.commit()

        }

        return fgView
    }
}
