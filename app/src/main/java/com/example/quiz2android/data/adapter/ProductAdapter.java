package com.example.quiz2android.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz2android.R;
import com.example.quiz2android.data.model.Product;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

/**
 * Este adaptador es una clase que permite gestionar y mostrar una lista de productos
 * en un RecyclerView, vinculando cada elemento de la lista de datos con una vista
 * en la interfaz de usuario.
 */


    /**
     * Esta clase extiende RecyclerView.Adapter, lo cual permite que el
     * RecyclerView maneje y muestre elementos en la pantalla de manera
     * eficiente. La clase incluye dos propiedades:
     *
     * userList: Una lista de objetos Product que contiene los datos que
     * se van a mostrar.
     * listener: Un OnSuccessListener que permite gestionar eventos
     * cuando el usuario interactúa con un producto, en este caso,
     * al hacer clic en un elemento de la lista.
     */
    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.UserViewHolder> {
        private List<Product> userList;
        private OnSuccessListener<Product> listener;

        public ProductAdapter(List<Product> userList, OnSuccessListener<Product> listener) {
            this.userList = userList;
            this.listener = listener;
        }


        /**
     *
     * 2. Método onCreateViewHolder
     * Este método es llamado por el RecyclerView para crear nuevos elementos
     * (ViewHolders). Aquí se infla el diseño definido en item_product.xml,
     * que representa cada ítem individual en el RecyclerView. Luego, se
     * retorna un nuevo UserViewHolder con la vista inflada
     */


    @NonNull
    @Override
    public ProductAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new UserViewHolder(view);
    }

    /**
     *
     *          * 3. Método onBindViewHolder
     * Este método enlaza los datos de cada Product (o producto) con la vista
     * (ViewHolder) correspondiente. Aquí:
     *
     * Se obtiene el Product en la posición actual de la lista
     * (userList.get(position)).
     *
     * Se establece el nombre y la contraseña del producto en los TextView
     * correspondientes dentro del ViewHolder.
     *
     * Se configura un OnClickListener en el ítem para que cuando se haga
     * clic, se llame al listener.onSuccess(user), permitiendo realizar
     * una acción, como mostrar un mensaje o abrir otra pantalla con
     * información detallada del producto.
     */


    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.UserViewHolder holder, int position) {
        Product user = userList.get(position);
        holder.userNameTextView.setText(user.getname());
        holder.userEmailTextView.setText(user.getPassword());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSuccess(user);
            }
        });
    }

/**
 *
 *   4. Método getItemCount
 *
 * Este método devuelve el número de elementos en userList.
 * Es necesario para que el RecyclerView sepa cuántos elementos debe mostra
 *
 * */


    @Override
    public int getItemCount() {
        return userList.size();
    }

    /**
     *
     *   5. Clase Interna UserViewHolder

     * La clase UserViewHolder representa cada elemento visible del RecyclerView. Aquí:
     *
     * TextView userNameTextView y TextView userEmailTextView: Son referencias
     * a los elementos visuales (textos) dentro de cada ítem. Se usan para mostrar
     * el nombre y la contraseña del producto.
     * En el constructor, se inicializan estas variables buscando los elementos de
     * item_product.xml por su ID (R.id.usernameTextView y R.id.passwordTextView).
     *          *
     *          */


    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameTextView;
        private TextView userEmailTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.usernameTextView);
            userEmailTextView = itemView.findViewById(R.id.passwordTextView);
        }
    }
}

/**
 * Resumen General
 * La clase ProductAdapter realiza la función de "puente" entre
 * los datos (lista de productos) y el RecyclerView que los muestra.
 * Para cada producto en la lista:
 *
 * onCreateViewHolder crea una nueva vista para el producto.
 * onBindViewHolder asigna los datos del producto a la vista.
 * getItemCount indica cuántos productos hay en la lista.
 * Cada vez que se hace clic en un elemento del RecyclerView, el listener
 * recibe el objeto Product correspondiente, permitiendo ejecutar una acción relacionada.
 *
 * Este adaptador es fundamental para mostrar listas en Android, y su
 * flexibilidad permite personalizar cómo cada elemento es representado
 * en la interfaz gráfica.
 */