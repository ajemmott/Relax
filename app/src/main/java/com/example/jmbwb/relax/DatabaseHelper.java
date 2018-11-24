package com.example.jmbwb.relax;

import android.content.ContentValues;
import android.content.Context;
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

    //Actualizar un usuario
    public void actualizarUsuario(Usuarios usuario){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nombre", usuario.getNombre());
        valores.put("edad", usuario.getEdad());
        valores.put("contraseña", usuario.getContraseña());

        // Actualizar esa fila
       db.update("usuarios", valores, "id_usuario = ?",
                new String[] { String.valueOf(usuario.getId_usuario()) });
       db.close();
    }

    //Obtener datos del usuario
    public Usuarios obtenerInfo(int idUser){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM usuarios WHERE id_usuario = " + idUser;

        Cursor c = db.rawQuery(query,null);
        Usuarios infoUsuario = new Usuarios();

        if(c != null && c.moveToFirst()){
            infoUsuario.setNombre(c.getString(c.getColumnIndex("nombre")));
            infoUsuario.setEdad(c.getInt(c.getColumnIndex("edad")));
            infoUsuario.setContraseña(c.getString(c.getColumnIndex("contraseña")));
            c.close();
        }

        return infoUsuario;
    }

    //Borrando Usuario
    public void borrarUsuario(String correo){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("usuarios", "correo = ?",
                new String[]{String.valueOf(correo)});
        db.close();
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

    //buscar id de usuario
    public int buscarId(String correo){
        int id = 0;
        String query = "SELECT id_usuario FROM usuarios WHERE correo = '" + correo + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        if (c != null){
            id = c.getInt(c.getColumnIndex("id_usuario"));
        }
        db.close();
        return id;
    }

    //Obtener todos las tecnicas
    public List<DatosTecnicas> getTecnicas(){
        List<DatosTecnicas> tecnicas = new ArrayList<DatosTecnicas>();
        String query = "SELECT * FROM tecnicas";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        //Loops de todas las filas para agregarlo a la lista
        if (c.moveToFirst()){
            do{
                DatosTecnicas datosTecnicas = new DatosTecnicas();
                datosTecnicas.setId_tecnica(c.getInt(c.getColumnIndex("id_tecnicas")));
                datosTecnicas.setTitulo(c.getString(c.getColumnIndex("titulo")));
                datosTecnicas.setUrl_video(c.getString(c.getColumnIndex("url_video")));
                datosTecnicas.setDescripcion(c.getString(c.getColumnIndex("descripcion")));
                datosTecnicas.setImagen(c.getInt(c.getColumnIndex("imagen")));
            }while(c.moveToNext());
        }
        return tecnicas;
    }

    //Obtener tecnicas mas vistas del usuario
    public List<DatosTecnicas> getTecnicasMasVistas(Integer usuarioActual) {
        List<DatosTecnicas> tecnicas = new ArrayList<DatosTecnicas>();

        String query = "SELECT * FROM tecnicas tec, usuarios us,"
                + "usuario_observa_tecnicas uot WHERE uot.nVeces > 4"
                + "AND us.id_usuario = " + usuarioActual;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        //Loops de todas las filas para agregarlo a la lista
        if (c.moveToFirst()) {
            do {
                DatosTecnicas datosTecnicas = new DatosTecnicas();
                datosTecnicas.setId_tecnica(c.getInt(c.getColumnIndex("id_tecnicas")));
                datosTecnicas.setTitulo(c.getString(c.getColumnIndex("titulo")));
                datosTecnicas.setUrl_video(c.getString(c.getColumnIndex("url_video")));
                datosTecnicas.setDescripcion(c.getString(c.getColumnIndex("descripcion")));
                datosTecnicas.setImagen(c.getInt(c.getColumnIndex("imagen")));
            } while (c.moveToNext());
        }
        return tecnicas;
    }
}
