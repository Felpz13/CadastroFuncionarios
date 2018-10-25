package com.example.claro.cadastrodefuncionrios

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import kotlinx.android.synthetic.main.departamentos.view.*

class departamentos : Fragment()
{
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

           getFragmentManager()?.beginTransaction()?.replace(R.id.container, departamentos_cadastro())?.remove(this)?.commit()

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
