package com.example.quiz2android.data.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quiz2android.data.model.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Este archivo se encarga de gestionar las operaciones de base de datos en
 * Firestore relacionadas con los productos. Aquí están los métodos
 * principales y una explicación detallada de cada uno.
 */

public class ProductDao {

    /**
     * TAG: Se usa para etiquetar los logs en el Logcat de Android Studio, facilitando
     * la identificación de mensajes relacionados con esta clase.
     * COLLECTION_NAME: Define el nombre de la colección en Firestore, que en este caso
     * es "Productos".
     * db: Es la instancia de FirebaseFirestore que se usará para realizar las
     * operaciones de lectura y escritura en la base de datos.
    */

    private static final String TAG = "Quiz 2 Android";
    private static final String COLLECTION_NAME = "Productos";

    private final FirebaseFirestore db;

    /**
     * Constructor de la clase ProductDao.
     * Este constructor toma una instancia de FirebaseFirestore, lo cual permite
     * que la clase ProductDao tenga acceso a la base de datos Firestore.
Instancia de FirebaseFirestore para acceder a la base de datos.
     */
    public ProductDao(FirebaseFirestore db) {
        this.db = db;
    }

    /**
     *      * Método insert
     * Inserta un nuevo producto en la colección "productos".
     *
     *      * Explicación
     * Función: Inserta un nuevo producto en la colección "Productos" en Firestore.
     * Parámetros:
     * Product user: el producto a insertar.
     * OnSuccessListener<String> listener: escucha el resultado exitoso de la
     * operación e informa el ID del documento creado.
     *      * Proceso:
     * Se crea un Map (userData) con los datos del producto.
     * db.collection(COLLECTION_NAME).add(userData): añade el producto a Firestore.
     * addOnSuccessListener: si la inserción es exitosa, obtiene el ID del
     * documento y lo pasa al listener.
     * addOnFailureListener: si falla, registra el error en el log.
     *
Listener para notificar el resultado de la operación.
     */
    public void insert(Product user, OnSuccessListener<String> listener) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", user.getname());
        userData.put("password", user.getPassword());

        db.collection(COLLECTION_NAME)
                .add(userData)
                .addOnSuccessListener(documentReference -> {
                    Log.d(TAG, "onSuccess: " + documentReference.getId());
                    listener.onSuccess(documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "onFailure: ", e);
                    listener.onSuccess(null);
                });
    }

    /**
     *      * Método update
     * Actualiza los datos de un usuario existente.
     *
     *      * Explicación
     * Función: Actualiza un producto existente en Firestore utilizando su ID.
     * Parámetros:
     * String id: ID del producto que se quiere actualizar.
     * Product user: datos actualizados del producto.
     * OnSuccessListener<Boolean> listener: informa si la operación fue exitosa.
     *      * Proceso:
     * Crea un Map con los datos actualizados.
     * db.collection(COLLECTION_NAME).document(id).update(userData): actualiza el documento correspondiente.
     * addOnSuccessListener: confirma el éxito de la operación.
     * addOnFailureListener: si falla, registra el error en el log.
     *
     * @param id       El ID del usuario a actualizar.
     * @param user     El objeto User con los datos actualizados.
     * @param listener Listener para notificar el resultado de la operación.
     */
    public void update(String id, Product user, OnSuccessListener<Boolean> listener) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", user.getname());
        userData.put("password", user.getPassword());

        db.collection(COLLECTION_NAME)
                .document(id)
                .update(userData)
                .addOnSuccessListener(unused -> listener.onSuccess(true))
                .addOnFailureListener(e -> {
                    Log.e(TAG, "onFailure: ", e);
                    listener.onSuccess(false);
                });
    }

    /**
     *      * Método getById
         * Obtiene un usuario por su ID.
     *
     *      * Explicación
     *
     * Función: Obtiene un producto específico de Firestore usando su ID.
     * Parámetros:
     * String id: ID del producto a obtener.
     *
     * OnSuccessListener<Product> listener: escucha el resultado exitoso y
     * devuelve el objeto Product.
     *
     *
     *      * Proceso:
     * db.collection(COLLECTION_NAME).document(id).get(): obtiene el documento
     * del producto con el ID especificado.
     *
     * Verifica si el documento existe; si existe, lo convierte en un objeto
     * Product y lo envía al listener.
     *
     * Si no existe o falla la operación, informa con null.
     *
         *
         * @param id       El ID del usuario a obtener.
         * @param listener Listener para notificar el resultado de la operación.
         */
        public void getById (String id, OnSuccessListener < Product > listener){
            db.collection(COLLECTION_NAME)
                    .document(id)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Product user = document.toObject(Product.class);
                                listener.onSuccess(user);
                            } else {
                                listener.onSuccess(null);
                            }
                        } else {
                            Log.e(TAG, "onComplete: ", task.getException());
                            listener.onSuccess(null);
                        }
                    });
        }

    /**
     *      * Método getAll
         * Obtiene todos los usuarios de la colección.
         *
     *
     *      * Explicación
     * Función: Obtiene todos los productos en la colección "Productos".
     *
     * Parámetros:
     * OnSuccessListener<List<Product>> listener: escucha y devuelve una lista de productos.
     *
     *      * Proceso:
     * db.collection(COLLECTION_NAME).get(): obtiene todos los documentos en la colección.
     *
     * Si hay documentos, convierte cada uno en un Product y los añade a una lista.
     *
     * Si la colección está vacía o falla, devuelve null.
     *
         * @param listener Listener para notificar el resultado de la operación.
         */


        public void getAll (OnSuccessListener < List < Product >> listener) {
            db.collection(COLLECTION_NAME).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<Product> userList = new ArrayList<>();
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Product user = documentSnapshot.toObject(Product.class);
                            userList.add(user);
                        }
                        listener.onSuccess(userList);
                    } else {
                        listener.onSuccess(null);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "onFailure: ", e);
                    listener.onSuccess(null);
                }
            });
        }


        /**
         *      * Método delete
         * Elimina un usuario de la colección por su ID.
         *
         *      * Explicación
         * Función: Elimina un producto de Firestore según su ID.
         * Parámetros:
         * String id: ID del producto a eliminar.
         *
         * OnSuccessListener<Boolean> listener: informa el resultado de la operación.
         *
         *
         *      * Proceso:
         * db.collection(COLLECTION_NAME).document(id).delete(): elimina el documento
         * con el ID especificado.
         *
         * Si tiene éxito, devuelve true.
         *
         * Si falla, registra el error y devuelve false.
         *
         * @param id       El ID del usuario a eliminar.
         * @param listener Listener para notificar el resultado de la operación.
         */
        public void delete (String id, OnSuccessListener < Boolean > listener){
            db.collection(COLLECTION_NAME)
                    .document(id)
                    .delete()
                    .addOnSuccessListener(unused -> listener.onSuccess(true))
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "onFailure: ", e);
                        listener.onSuccess(false);
                    });
        }
    }

/**
 * Resumen
 * ProductDao proporciona métodos para:
 *
 * insertar (insert)
 * actualizar (update)
 * eliminar (delete)
 * obtener por ID (getById)
 * obtener todos (getAll)
 * Cada método usa FirebaseFirestore para interactuar con la base de datos Firestore y gestiona los resultados a través de listeners, registrando errores cuando ocurren.
 */
