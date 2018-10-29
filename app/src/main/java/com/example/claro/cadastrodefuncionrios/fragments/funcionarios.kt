package com.example.claro.cadastrodefuncionrios.fragments

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
import com.example.claro.cadastrodefuncionrios.auxiliares.adapterFun
import com.example.claro.cadastrodefuncionrios.auxiliares.dbHelper
import com.example.claro.cadastrodefuncionrios.classes.dep
import com.example.claro.cadastrodefuncionrios.classes.funci

import kotlinx.android.synthetic.main.funcionarios.view.*

class funcionarios : Fragment()
{
    lateinit var db : dbHelper
    private lateinit var model: MainActivity.Compartilhado

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this).get(MainActivity.Compartilhado::class.java)
        } ?: throw Exception("Invalid Activity")

        var fgView = inflater.inflate(R.layout.funcionarios, container, false)

        var listView = fgView.lista_fun as ListView

        db = dbHelper(activity!!.applicationContext)

        val dptoAtual : Int = model.selecionado.value!!.component4()

        var nomesFunc: ArrayList<funci> = ArrayList()

        nomesFunc = db.listarFun(dptoAtual)

        Log.d("CURA", "FRAG: FUNCIONARIOS")


        if (nomesFunc.count() > 0)
        {
            listView.adapter = adapterFun(activity!!.applicationContext,nomesFunc)


            fgView.lista_fun.setOnItemLongClickListener() { adapterView, view, i, l ->

                db.deletarFunId(nomesFunc[i].component2())

                fragmentManager?.beginTransaction()?.replace(R.id.container,
                    funcionarios()
                )?.remove(this)?.commit()

                true
            }
        }
        else
        {
            fgView.txt_sem_cad_fun.visibility = View.VISIBLE
            listView.adapter = adapterFun(
                getActivity()!!.getApplicationContext(),
                nomesFunc
            )
        }

        fgView.bt_nv_fun.setOnClickListener { view ->

            getFragmentManager()?.beginTransaction()?.replace(
                R.id.container,
                funcionarios_cadastro()
            )?.remove(this)?.commit()

        }

        fgView.bt_lista_func_voltar.setOnClickListener { view ->

            getFragmentManager()?.beginTransaction()?.replace(
                R.id.container,
                departamentos()
            )?.remove(this)?.commit()

        }


        return fgView
    }

}