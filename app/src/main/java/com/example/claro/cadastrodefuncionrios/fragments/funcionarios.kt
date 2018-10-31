package com.example.claro.cadastrodefuncionrios.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.claro.cadastrodefuncionrios.MainActivity
import com.example.claro.cadastrodefuncionrios.R
import com.example.claro.cadastrodefuncionrios.auxiliares.adapterFun
import com.example.claro.cadastrodefuncionrios.auxiliares.dbHelper
import com.example.claro.cadastrodefuncionrios.classes.dep
import com.example.claro.cadastrodefuncionrios.classes.funci
import kotlinx.android.synthetic.main.funcionarios.*
import kotlinx.android.synthetic.main.funcionarios.view.*

class funcionarios : Fragment()
{
    lateinit var db : dbHelper
    private lateinit var model: MainActivity.Compartilhado
    var nomesFunc: ArrayList<funci> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreate(savedInstanceState)
        model = activity?.run {
            ViewModelProviders.of(this).get(MainActivity.Compartilhado::class.java)
        } ?: throw Exception("Invalid Activity")

        var fgView = inflater.inflate(R.layout.funcionarios, container, false)

        var listView = fgView.lista_fun as ListView

        db = dbHelper(activity!!.applicationContext)

        val dptoAtual : dep = model.selecionado.value as dep

        fgView.titulo_fun.setText("Funcionarios (${dptoAtual.component3()}):")

        nomesFunc = db.listarFun(dptoAtual.component4())

        Log.d("CURA", "FRAG: FUNCIONARIOS")

        if (nomesFunc.count() > 0)
        {
            listView.adapter = adapterFun(activity!!.applicationContext,nomesFunc, this)

            fgView.lista_fun.setOnItemClickListener{ adapterView, view, i, l ->

                model.selectFun(nomesFunc[i])

            }

            /*fgView.lista_fun.setOnItemLongClickListener{ adapterView, view, i, l ->

                val opcoes = PopupMenu(context,view)

                model.selectFun(nomesFunc[i])

                opcoes.setOnMenuItemClickListener { item ->
                    when (item.itemId)
                    {
                        R.id.menu_deletar -> {

                            db.deletarFunId(nomesFunc[i].component2())

                            fragmentManager?.beginTransaction()?.replace(R.id.container,
                                funcionarios()
                            )?.remove(this)?.commit()

                            true
                        }

                        R.id.menu_alterar -> {
                            fragmentManager?.beginTransaction()?.replace(R.id.container,
                                funcionarios_update()
                            )?.remove(this)?.commit()

                            true
                        }

                        else -> false
                    }
                }

                opcoes.inflate(R.menu.menu_main)
                opcoes.show()
                true
            }*/
        }
        else
        {
            fgView.txt_sem_cad_fun.visibility = View.VISIBLE
            listView.adapter = adapterFun(
                activity!!.applicationContext,
                nomesFunc, this)
        }

        fgView.bt_nv_fun.setOnClickListener { view ->

            fragmentManager?.beginTransaction()?.replace(
                R.id.container,
                funcionarios_cadastro()
            )?.remove(this)?.commit()

        }

        fgView.bt_lista_func_voltar.setOnClickListener { view ->

            fragmentManager?.beginTransaction()?.replace(
                R.id.container,
                departamentos()
            )?.remove(this)?.commit()

        }

        return fgView
    }

    fun apagar(funci: funci)
    {
        db.deletarFunId(funci.component2())

        fragmentManager?.beginTransaction()?.replace(R.id.container,
            funcionarios()
        )?.remove(this)?.commit()
    }

    fun modificar(funci: funci)
    {
        model.selectFun(funci)

        fragmentManager?.beginTransaction()?.replace(R.id.container,
            funcionarios_update()
        )?.remove(this)?.commit()
    }

    fun alert(funci: funci)
    {
        Log.d("CURA", "ALERTA")

        val builder = AlertDialog.Builder(activity as Context)

        builder.setTitle("ATENÇÃO: ")

        builder.setMessage("Deseja mesmo deletar ${funci.nome}?")

        builder.setPositiveButton("SIM") { dialog, which ->

            Log.d("CURA", "ALERTA : SIM")

            apagar(funci)

        }

        builder.setNegativeButton("Não") { dialog, which ->



        }

            .show()


    }
}
