package com.example.claro.cadastrodefuncionrios.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.example.claro.cadastrodefuncionrios.R
import com.example.claro.cadastrodefuncionrios.auxiliares.adapterDep
import com.example.claro.cadastrodefuncionrios.auxiliares.dbHelper
import kotlinx.android.synthetic.main.departamentos.view.*
import com.example.claro.cadastrodefuncionrios.classes.dep

class departamentos : Fragment()
{

    lateinit var db : dbHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {

        var fgView = inflater.inflate(R.layout.departamentos, container, false)

        var listView = fgView.lista_dep as ListView

        var nomes: ArrayList<dep> = ArrayList()

        db = dbHelper(activity!!.applicationContext)

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

                fragmentManager?.beginTransaction()?.replace(
                    R.id.container,
                    funcionarios()
                )?.remove(this)?.commit()
            }

            fgView.lista_dep.setOnItemLongClickListener() { adapterView, view, i, l ->

                db.deletarDpto(nomes[i].component4())

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
