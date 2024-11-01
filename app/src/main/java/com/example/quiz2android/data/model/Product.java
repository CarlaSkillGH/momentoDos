package com.example.quiz2android.data.model;

public class Product {

    /**
     *
     * Explicación de la Clase Product
     *
     * La clase Product es un modelo de datos que representa un producto
     * en la aplicación. Este modelo permite almacenar la información del
     * producto y facilita la integración con Firestore, ya que contiene
     * los métodos necesarios para acceder y modificar los atributos del producto.
     */

    /**
     *
     * 1. Atributos
     * id: Un identificador único del producto. Este campo se utiliza para
     * identificar y acceder al producto específico en la base de datos (Firestore).
     *
     * name: Representa el nombre del producto.
     *
     * password: Almacena una "contraseña" asociada al producto (aunque, por
     * su nombre, parece ser un campo poco común en un producto, podría
     * representar algo específico de tu aplicación).
     *
     */

    private String id;
    private String name;
    private String password;

    /**
     *
     * Constructor vacío: Es necesario para que Firestore pueda crear instancias de
     * esta clase automáticamente al recuperar datos. Firestore necesita un constructor
     * sin argumentos para deserializar los datos y convertirlos en objetos Product.
     * Constructor completo: Permite crear un objeto Product con un nombre y contraseña
     * definidos al momento de instanciar la clase. Esto es útil al crear nuevos productos.
     *
     */

    // Constructor vacío requerido para Firestore
    public Product() {
    }

    // Constructor completo
    public Product(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     *
     * getId y setId: Permiten obtener y establecer el valor del id del producto.
     * getname y setname: Permiten obtener y establecer el nombre del producto.
     * getPassword y setPassword: Permiten obtener y establecer la contraseña del producto.
     *
     */
    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String username) {
        this.name = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

/**
 * Resumen General
 * La clase Product es un simple modelo de datos que ayuda a estructurar
 * y almacenar la información de un producto en la aplicación. Al seguir
 * la convención de constructores, getters y setters, la clase se vuelve
 * compatible con la serialización en Firestore, permitiendo que los datos
 * se almacenen y recuperen fácilmente de la base de datos.
 */