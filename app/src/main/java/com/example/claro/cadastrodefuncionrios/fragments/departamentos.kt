package com.example.claro.cadastrodefuncionrios.fragments

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.example.claro.cadastrodefuncionrios.MainActivity
import com.example.claro.cadastrodefuncionrios.R
import com.example.claro.cadastrodefuncionrios.auxiliares.adapterDep
import com.example.claro.cadastrodefuncionrios.auxiliares.dbHelper
import kotlinx.android.synthetic.main.departamentos.view.*
import com.example.claro.cadastrodefuncionrios.classes.dep
import com.example.claro.cadastrodefuncionrios.classes.funci
import java.nio.channels.Selector

class departamentos : Fragment()
{

    lateinit var db : dbHelper
    private lateinit var model: MainActivity.Compartilhado

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {

        super.onCreate(savedInstanceState)
        model = activity?.run {ViewModelProviders.of(this).get(MainActivity.Compartilhado::class.java)
        } ?: throw Exception("Invalid Activity")

        var fgView = inflater.inflate(R.layout.departamentos, container, false)

        var listView = fgView.lista_dep as ListView

        var nomes: ArrayList<dep> = ArrayList()

        db = dbHelper(activity!!.applicationContext)

        //Log.d("CURA", "${db.tableExists("funcionarios" )}")

        //db.deleteAll()

        nomes = db.listarDpto()


        Log.d("CURA", "DEPARTAMENTOS")

        if (nomes.count() > 0)
        {
            listView.adapter = adapterDep(
                activity!!.applicationContext,
                nomes
            )


            fgView.lista_dep.setOnItemClickListener(){adapterView, view, i, l ->

                val dptoAtual : dep = nomes[i]

                model.select(dptoAtual)

                Log.d("CURA", "${model.selecionado.value}")

                fragmentManager?.beginTransaction()?.replace(
                    R.id.container,
                    funcionarios()
                )?.remove(this)?.commit()
            }

            fgView.lista_dep.setOnItemLongClickListener() { adapterView, view, i, l ->

                db.deletarDpto(nomes[i].component4())
                val dptoAtual : Int = model.selecionado.value!!.component4()

                var nomesFunc: ArrayList<funci> = db.listarFun(dptoAtual)

                for (i in 0 .. nomesFunc.count() - 1)
                {
                    db.deletarFun(nomesFunc[i].component4())
                }

                fragmentManager?.beginTransaction()?.replace(R.id.container,
                    departamentos()
                )?.remove(this)?.commit()
                true
            }
        }
        else
        {
            Log.d("CURA", "LISTA VAZIA")
            fgView.txt_sem_cad.visibility = View.VISIBLE
            listView.adapter = adapterDep(
                activity!!.applicationContext,
                nomes
            )
        }

        fgView.bt_nv_dep.setOnClickListener { view ->

          fragmentManager?.beginTransaction()?.replace(
               R.id.container,
               departamentos_cadastro()
           )?.remove(this)?.commit()

        }


        return fgView
    }
}
