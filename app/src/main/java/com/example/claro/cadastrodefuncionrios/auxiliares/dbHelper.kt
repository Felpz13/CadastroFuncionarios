package com.example.claro.cadastrodefuncionrios.auxiliares

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.claro.cadastrodefuncionrios.classes.dep

class dbHelper (context: Context) : SQLiteOpenHelper(context, dbname, factory, version)
{
    override fun onCreate(db: SQLiteDatabase?)
    {
        val query = "CREATE TABLE departamentos(id integer primary key autoincrement," + "name varchar(30),sigla varchar(5), img integer)"
        db?.execSQL(query)

        Log.d("CURA", "EXECUTOU BANCO DE DADOS")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun inserirDpto (name: String, sigla: String, img: Int)
    {


        val db: SQLiteDatabase = writableDatabase
        val values = ContentValues()
        values.put("name",name)
        values.put("sigla",sigla)
        values.put("img",img)

        db.insert("departamentos", null, values)
        db.close()
    }

    fun listarDpto() : ArrayList<dep>
    {
        Log.d("CURA", "QUERY DEPARTAMENTOS")

        val db = writableDatabase
        val query = "SELECT * FROM departamentos"
        val search = db.rawQuery(query, null)
        var lista : ArrayList<dep> = ArrayList()

        if (search.moveToFirst()) {
            do {
                var Dpto = dep(
                    search.getString(1),
                    search.getInt(3),
                    search.getString(2),
                    search.getInt(0)
                )


                lista.add(Dpto)
            } while (search.moveToNext())
        }

        search.close()

        return lista
    }

    fun deletarDpto( id : Int)
    {
        val db = this.writableDatabase

        db.delete("departamentos", "id == + $id", null)

        db.close()
    }

    fun deleteAll() {
        val db = this.writableDatabase

        db.delete("departamentos", null, null)
        db.close()
    }

    companion object
    {
        internal val dbname = "userDB"
        internal val factory = null
        internal val version = 1
    }
}