package com.example.claro.cadastrodefuncionrios.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.view.menu.ListMenuItemView
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.PopupMenu
import com.example.claro.cadastrodefuncionrios.MainActivity
import com.example.claro.cadastrodefuncionrios.R
import com.example.claro.cadastrodefuncionrios.auxiliares.adapterDep
import com.example.claro.cadastrodefuncionrios.auxiliares.dbHelper
import kotlinx.android.synthetic.main.departamentos.view.*
import com.example.claro.cadastrodefuncionrios.classes.dep
import com.example.claro.cadastrodefuncionrios.classes.funci
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dep_item.*

class departamentos : Fragment()
{

    lateinit var db : dbHelper
    private lateinit var model: MainActivity.Compartilhado

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {

        super.onCreate(savedInstanceState)
        model = activity?.run {ViewModelProviders.of(this).get(MainActivity.Compartilhado::class.java)
        } ?: throw Exception("Invalid Activity")

        val fgView = inflater.inflate(R.layout.departamentos, container, false)

        val listView = fgView.lista_dep as ListView

        var nomes: ArrayList<dep> = ArrayList()

        db = dbHelper(activity!!.applicationContext)

        nomes = db.listarDpto()

        if (nomes.count() > 0) {
            listView.adapter = adapterDep(
                activity!!.applicationContext,
                nomes, this
            )


            fgView.lista_dep.setOnItemClickListener() { adapterView, view, i, l ->

                val dptoAtual: dep = nomes[i]

                model.select(dptoAtual)

                fragmentManager?.beginTransaction()?.replace(
                    R.id.container,
                    funcionarios()
                )?.remove(this)?.commit()
            }

        }

        else
        {
            fgView.txt_sem_cad.visibility = View.VISIBLE
            listView.adapter = adapterDep(
                activity!!.applicationContext,
                nomes,this
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

    fun menuDep (dep : dep, v: View)
    {

        val opcoes = PopupMenu(ContextThemeWrapper(context?.applicationContext, R.style.popup), v)

        val dptoAtual : dep = dep

        model.select(dptoAtual)

        opcoes.setOnMenuItemClickListener { item ->
            when (item.itemId)
            {
                R.id.menu_deletar ->
                {

                    val builder = AlertDialog.Builder(activity as Context)

                    builder.setTitle("ATENÇÃO: ")

                    builder.setMessage("Deseja mesmo deletar ${dep.nome}?")

                    builder.setPositiveButton("SIM") { dialog, which ->

                        Log.d("CURA", "ALERTA : SIM")

                        db.deletarDpto(dep.component4())
                        val dptoAtual : Int = model.selecionado.value!!.component4()

                        db.deletarFun(dptoAtual)

                        fragmentManager?.beginTransaction()?.replace(R.id.container,
                            departamentos()
                        )?.remove(this)?.commit()



                    }

                    builder.setNegativeButton("Não") { dialog, which ->


                    }

                        .show()

                    true
                }

                R.id.menu_alterar -> {

                    fragmentManager?.beginTransaction()?.replace(R.id.container,
                        departamentos_update()
                    )?.remove(this)?.commit()

                    true
                }

                else -> false
            }
        }

        opcoes.inflate(R.menu.menu_main)
        opcoes.show()
        true
    }
}
