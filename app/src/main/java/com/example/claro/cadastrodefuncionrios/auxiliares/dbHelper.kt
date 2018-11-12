package com.example.claro.cadastrodefuncionrios.auxiliares

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.claro.cadastrodefuncionrios.classes.dep
import com.example.claro.cadastrodefuncionrios.classes.funci

class dbHelper (context: Context) : SQLiteOpenHelper(context, dbname, factory, version)
{
    override fun onCreate(db: SQLiteDatabase?)
    {

        val query = "create table departamentos(id integer primary key autoincrement," + "name varchar(30), sigla varchar(5), img integer)"
        val query2 = "create table funcionarios(id integer primary key autoincrement," + "nome varchar(30), rg varchar(10), idpto integer, foto integer)"

        db?.execSQL(query)
        db?.execSQL(query2)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        onCreate(db)
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

    fun atualizarDep( id: Int, name: String, sigla: String, img: Int)
    {
        val db: SQLiteDatabase = writableDatabase
        val values = ContentValues()
        values.put("name",name)
        values.put("sigla",sigla)
        values.put("img",img)

        db.update("departamentos", values, "id == $id", null)
        db.close()
    }

    fun deletarDpto( id : Int)
    {
        val db = this.writableDatabase

        db.delete("departamentos", "id == + $id", null)

        db.close()
    }

    fun deleteAll()
    {
        val db = this.writableDatabase

        db.delete("departamentos", null, null)

        db.delete("funcionarios", null, null)

        db.close()
    }

    fun listarFun(id : Int) : ArrayList<funci>
    {
        Log.d("CURA", "QUERY FUNCIONARIOS")

        val db = writableDatabase
        var query = "SELECT * FROM funcionarios WHERE idpto = '$id'"
        if (id == -1) query = "SELECT * FROM funcionarios"
        val search = db.rawQuery(query, null)
        var lista : ArrayList<funci> = ArrayList()


            if (search.moveToFirst()) {
                do {
                    var nfunci = funci(
                        search.getString(1),
                        search.getInt(0),
                        search.getString(2),
                        search.getInt(3),
                        search.getInt(4)
                    )
                    lista.add(nfunci)
                } while (search.moveToNext())
            }

        search.close()

        return lista
    }

    fun inserirFun(nome: String, rg: String, idDpto: Int, foto: Int)
    {
            val db: SQLiteDatabase = writableDatabase
            val values = ContentValues()
            values.put("nome", nome)
            values.put("rg", rg)
            values.put("idpto", idDpto)
            values.put("foto", foto)

            db.insert("funcionarios", null, values)
            db.close()
    }


    fun deletarFun( id : Int)
    {
        val db = this.writableDatabase

        db.delete("funcionarios", "idpto == + $id", null)

        db.close()
    }

    fun deletarFunId( id : Int)
    {
        val db = this.writableDatabase

        db.delete("funcionarios", "id == + $id", null)

        db.close()
    }

    fun atualizarFun(id : Int, name : String, rg : String, img : Int)
    {
        val db: SQLiteDatabase = writableDatabase
        val values = ContentValues()
        values.put("nome",name)
        values.put("rg",rg)
        values.put("foto",img)

        db.update("funcionarios", values, "id == $id", null)

        db.close()
    }

    fun tableExists(tableName: String) : Int
    {
        val db = writableDatabase
        val query = "SELECT name FROM sqlite_master WHERE type='table' AND name='${tableName}'"
        val search = db.rawQuery(query, null)

        return search.count
    }

        companion object
        {
            internal val dbname = "newDB"
            internal val factory = null
            internal val version = 1
        }

}