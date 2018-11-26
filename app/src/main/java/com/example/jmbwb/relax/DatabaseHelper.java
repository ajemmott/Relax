package com.example.jmbwb.relax;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME = "tecnicasBD";

    //Creando las tablas: Usuarios, datos tecnicas y usuario_observa_tecnicas

    //Tabla usuarios
    private static final String CREAR_TABLA_USUARIOS = "CREATE TABLE "
            + "usuarios (id_usuario INTEGER PRIMARY KEY, nombre TEXT, "
            + " genero TEXT, contraseña TEXT,correo TEXT, edad INTEGER, tipo INTEGER)";

    //Tabla tecnicas
    private static final String CREAR_TABLA_TECNICAS = "CREATE TABLE "
            + "tecnicas (id_tecnicas INTEGER PRIMARY KEY, titulo TEXT, "
            + " url_video TEXT, descripcion TEXT, imagen INTEGER)";

    //Tabla usuarios_observa_tecnicas
    private static final String CREAR_TABLA_USUARIO_OBSERVA_TECNICAS = "CREATE TABLE "
            + "usuario_observa_tecnicas (id INTEGER PRIMARY KEY, id_usuario INTEGER, "
            + "id_tecnicas INTEGER, nVeces INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREAR_TABLA_USUARIOS);
        sqLiteDatabase.execSQL(CREAR_TABLA_TECNICAS);
        sqLiteDatabase.execSQL(CREAR_TABLA_USUARIO_OBSERVA_TECNICAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS usuarios");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tecnicas");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS usuario_observa_tecnicas");

        onCreate(sqLiteDatabase);
    }

    //METODOS DE CONSULTA

    //Crear usuario
    public void crearUsuario(Usuarios usuario){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("nombre", usuario.getNombre());
        valores.put("genero", usuario.getGenero());
        valores.put("contraseña",usuario.getContraseña());
        valores.put("correo", usuario.getCorreo());
        valores.put("edad", usuario.getEdad());
        valores.put("tipo", usuario.getTipo());

        //Insertando fila
        db.insert("usuarios", null,valores);
        db.close();
    }


    public void almacenarTecnicas(DatosTecnicas datosTecnicas){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("id_tecnicas",datosTecnicas.getid_tecnica());
        valores.put("titulo",datosTecnicas.getTitulo());
        valores.put("url_video",datosTecnicas.getUrl_video());
        valores.put("descripcion",datosTecnicas.getDescripcion());
        valores.put("imagen",datosTecnicas.getImagen());

        db.insert("tecnicas",null,valores);
        db.close();
    }

    public float actualizarHistorial(String correo, int id_tecnica){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columnas = {"id_usuario"};
        String busqueda = "correo = ?";
        String[] argumento = {correo};

        Cursor c = db.query("usuarios",
                columnas,
                busqueda,
                argumento,
                null,
                null,
                null);

        if (c != null){
            c.moveToFirst();
        }

        int id_usuario = c.getInt(c.getColumnIndex("id_usuario"));
        c.close();
        db.close();

        if (!validarHistorial(id_usuario, id_tecnica)){
            db = this.getWritableDatabase();
            ContentValues valores = new ContentValues();
            valores.put("id_usuario",id_usuario);
            valores.put("id_tecnicas",id_tecnica);
            valores.put("nVeces",1);
            int veces = 1;
            db.insert("usuario_observa_tecnicas", null, valores);
            db.close();
            return (float) veces;
        }else{
            db = this.getReadableDatabase();
            columnas = new String[] {"nVeces"};
            busqueda = "id_usuario = ? AND id_tecnicas = ?";
            argumento = new String[] {String.valueOf(id_usuario),String.valueOf(id_tecnica)};

            c = db.query("usuario_observa_tecnicas",
                    columnas,
                    busqueda,
                    argumento,
                    null, null, null);

            if(c != null){
                c.moveToFirst();
            }
            int veces = Integer.parseInt(c.getString(c.getColumnIndex("nVeces")));
            c.close();
            db.close();
            veces += 1;

            db = this.getWritableDatabase();
            ContentValues valores = new ContentValues();

            valores.put("nVeces", String.valueOf(veces));
            db.update("usuario_observa_tecnicas", valores,
                    "id_usuario = ? AND id_tecnicas = ?",
                    new String[] {String.valueOf(id_usuario), String.valueOf(id_tecnica)});
            db.close();
            return (float) veces;
        }
    }

    public boolean validarHistorial(int id_usuario, int id_tecnica) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columnas = {"id_tecnicas", "id_usuario"};
        String busqueda = "id_tecnicas = ? AND id_usuario = ?";
        String[] argumento = {String.valueOf(id_tecnica), String.valueOf(id_usuario)};

        Cursor cursor = db.query("usuario_observa_tecnicas",
                columnas,
                busqueda,
                argumento,
                null,
                null,
                null);
        int cursorConteo = cursor.getCount();
        cursor.close();
        db.close();
        return (cursorConteo > 0);
    }


    //Actualizar un usuario
    public void actualizarUsuario(String nuevoNombre, String nuevaContra, int nuevaEdad, String genero,String correo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nombre", nuevoNombre);
        valores.put("contraseña", nuevaContra);
        valores.put("edad", nuevaEdad);
        valores.put("genero", genero);

        // Actualizar esa fila
       db.update("usuarios", valores, "correo = ?",
                new String[] { correo });
       db.close();
    }

    //Obtener datos del usuario
    public Cursor obtenerInfo(String correo){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.query("usuarios",
                new String[] {"nombre","edad", "contraseña", "genero"},
                "correo = ?", new String[]{correo}, null,
                null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //Borrando Usuario
    public void borrarUsuario(String correo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("usuarios", "correo = ?",
                new String[]{String.valueOf(correo)});
        db.close();
    }
    public boolean validarTecnica(int id) {
        String[] columnas = {"id_tecnicas"};

        SQLiteDatabase db = this.getReadableDatabase();

        String busqueda = "id_tecnicas = ?";
        String[] argumento = {String.valueOf(id)};

        Cursor cursor = db.query("tecnicas",
                columnas,
                busqueda,
                argumento,
                null,
                null,
                null);
        int cursorConteo = cursor.getCount();
        cursor.close();
        db.close();
        return (cursorConteo > 0);
    }

    //Chequeando si existe
    public boolean validarUsuario(String correo) {
        // array of columns to fetch
        String[] columnas = {
                "id_usuario"
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String busqueda = "correo = ?";

        String[] argumento = {correo};

        Cursor cursor = db.query("usuarios",
                columnas,
                busqueda,
                argumento,
                null,
                null,
                null);
        int cursorConteo = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorConteo > 0) {
            return true;
        }
        return false;
    }

    //Validando al usuario
    public boolean validarUsuario(String correo, String contra){
        String[] columna = {
                "id_usuario"
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String seleccion = "correo = ?  AND contraseña = ?";
        String[] argumentos = {correo, contra};

        Cursor cursor = db.query("usuarios",
                columna,
                seleccion,
                argumentos,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    //Obtener todos las tecnicas
    //public List<DatosTecnicas> getTecnicas(){
    //    List<DatosTecnicas> tecnicas = new ArrayList<DatosTecnicas>();
    //    String query = "SELECT * FROM tecnicas";
    //
    //    SQLiteDatabase db = this.getReadableDatabase();
    //    Cursor c = db.rawQuery(query, null);
    //
    //    //Loops de todas las filas para agregarlo a la lista
    //    if (c.moveToFirst()){
    //        do{
    //            DatosTecnicas datosTecnicas = new DatosTecnicas();
    //            datosTecnicas.setId_tecnica(c.getInt(c.getColumnIndex("id_tecnicas")));
    //            datosTecnicas.setTitulo(c.getString(c.getColumnIndex("titulo")));
    //            datosTecnicas.setUrl_video(c.getString(c.getColumnIndex("url_video")));
    //            datosTecnicas.setDescripcion(c.getString(c.getColumnIndex("descripcion")));
    //            datosTecnicas.setImagen(c.getInt(c.getColumnIndex("imagen")));
    //        }while(c.moveToNext());
    //    }
    //    return tecnicas;
    //}

    //Obtener tecnicas mas vistas del usuario
    //public List<DatosTecnicas> getTecnicasMasVistas(Integer usuarioActual) {
    //    List<DatosTecnicas> tecnicas = new ArrayList<DatosTecnicas>();
    //
    //    String query = "SELECT * FROM tecnicas tec, usuarios us,"
    //            + "usuario_observa_tecnicas uot WHERE uot.nVeces > 4"
    //            + "AND us.id_usuario = " + usuarioActual;
    //
    //    SQLiteDatabase db = this.getReadableDatabase();
    //    Cursor c = db.rawQuery(query, null);
    //
    //    //Loops de todas las filas para agregarlo a la lista
    //    if (c.moveToFirst()) {
    //        do {
    //            DatosTecnicas datosTecnicas = new DatosTecnicas();
    //            datosTecnicas.setId_tecnica(c.getInt(c.getColumnIndex("id_tecnicas")));
    //            datosTecnicas.setTitulo(c.getString(c.getColumnIndex("titulo")));
    //            datosTecnicas.setUrl_video(c.getString(c.getColumnIndex("url_video")));
    //            datosTecnicas.setDescripcion(c.getString(c.getColumnIndex("descripcion")));
    //            datosTecnicas.setImagen(c.getInt(c.getColumnIndex("imagen")));
    //        } while (c.moveToNext());
    //    }
    //    return tecnicas;
    //}
}
