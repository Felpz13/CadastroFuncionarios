package com.example.claro.cadastrodefuncionrios.auxiliares

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.claro.cadastrodefuncionrios.R
import com.example.claro.cadastrodefuncionrios.classes.dep
import com.example.claro.cadastrodefuncionrios.fragments.departamentos
import kotlinx.android.synthetic.main.dep_item.view.*

class adapterDep (var context: Context, var dataSource: ArrayList<dep>, var mcontext : departamentos) : BaseAdapter()
{

    private class ViewHolder(row: View?) {
        var txtName: TextView? = null
        var imgAvatar: ImageView? = null

        init {
            this.txtName = row?.findViewById(R.id.nome_dpto_list) as TextView
            this.imgAvatar = row?.findViewById(R.id.imgFundo) as ImageView
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
    {
        val view: View?
        val viewHolder: ViewHolder
        if (convertView == null) {
            val layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.dep_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder

        }

        else
        {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val elemento : dep = getItem(position) as dep
        viewHolder.txtName?.text = elemento.nome
        viewHolder.imgAvatar?.setImageResource(elemento.img)

        view?.opcDep?.setOnClickListener { view ->

            mcontext.menuDep(elemento, view)

        }

        return view as View
    }

    override fun getItem(position: Int): Any
    {
        return dataSource.get(position)
    }

    override fun getItemId(position: Int): Long
    {
        return position.toLong()
    }

    override fun getCount(): Int
    {
        return dataSource.count()
    }
}