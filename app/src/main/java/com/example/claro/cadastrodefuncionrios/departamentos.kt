package com.example.claro.cadastrodefuncionrios

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.claro.cadastrodefuncionrios.R.id.titulo
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dep_item.*
import kotlinx.android.synthetic.main.departamentos.*
import kotlinx.android.synthetic.main.departamentos.view.*

class departamentos : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        var fgView = inflater.inflate(R.layout.departamentos, container, false)

        var listView = fgView.findViewById(R.id.lista_dep) as ListView

        var nomes: ArrayList<dep> = ArrayList()


        if (nomes.count() > 0) {
            listView.adapter = adapterDep(getActivity()!!.getApplicationContext(), nomes)
        } else {

            nomes.add(dep("Nenhum departamento cadastrado", 30))
            listView.adapter = adapterDep(getActivity()!!.getApplicationContext(), nomes)
        }

        fgView.bt_nv_dep.setOnClickListener { view ->
            Toast.makeText(context, "ADD DEPARTAMENTO", Toast.LENGTH_SHORT).show()
        }

        return fgView
    }
}

data class dep(val nome:String, var img:Int)
{
    init
    {
        val regex = Regex(".*\\d+.*")

        require(!nome.matches(regex)) { "O Nome não pode conter numeros" }

        if (img == 30) img = R.drawable.ic_launcher_background
    }
}
