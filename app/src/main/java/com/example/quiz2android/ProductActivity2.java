package com.example.quiz2android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quiz2android.data.adapter.ProductAdapter;
import com.example.quiz2android.data.dao.ProductDao;
import com.example.quiz2android.data.model.Product;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ProductActivity2 extends AppCompatActivity {
    private FirebaseFirestore db;
    private ProductDao userDao;
    private RecyclerView recyclerView;
    private ProductAdapter userAdapter;
    private Button btnLeer, btncrear, btneliminar, btnUpdate;
    private TextView editTextName, editTextPrecio, editTextID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Habilita el modo de borde a borde en la UI.
        setContentView(R.layout.activity_product2);

        // Configura los insets para el sistema (barra de estado, barra de navegación).
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializa la base de datos de Firestore y el DAO para productos.
        db = FirebaseFirestore.getInstance();
        userDao = new ProductDao(db);

        // Vincula los elementos de la interfaz con sus ID en el layout.
        btncrear = findViewById(R.id.btnCrear);
        btnLeer = findViewById(R.id.btnleer);
        btneliminar = findViewById(R.id.btnEliminar);
        btnUpdate = findViewById(R.id.btnUpdate);
        editTextName = findViewById(R.id.NombreProduct);
        editTextID = findViewById(R.id.IdProduct);
        editTextPrecio = findViewById(R.id.PrecioProduct);
        recyclerView = findViewById(R.id.recyclerView);

        // Configura el RecyclerView para mostrar la lista de productos.
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Configura el botón de "Leer" para obtener la lista de productos.
        btnLeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDao.getAll(new OnSuccessListener<List<Product>>() {
                    @Override
                    public void onSuccess(List<Product> products) {
                        // Configura el adaptador con la lista de productos obtenida y muestra un mensaje al seleccionar un producto.
                        userAdapter = new ProductAdapter(products, new OnSuccessListener<Product>() {
                            @Override
                            public void onSuccess(Product product) {
                                Toast.makeText(ProductActivity2.this, "Product: " + product.getname(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        recyclerView.setAdapter(userAdapter);

                        // Registra en el log la lista de productos.
                        for (Product product : products) {
                            Log.d("ProductActivity2", "Product: " + product.getname());
                        }
                    }
                });
            }
        });

        // Configura el botón de "Crear" para agregar un nuevo producto.
        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crea un nuevo producto con el nombre y precio ingresados.
                userDao.insert(new Product(editTextName.getText().toString(), editTextPrecio.getText().toString()), new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Toast.makeText(ProductActivity2.this, "Producto creado con éxito", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Configura el botón de "Eliminar" para borrar un producto por su ID.
        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDao.delete(editTextID.getText().toString(), new OnSuccessListener<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        Toast.makeText(ProductActivity2.this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Configura el botón de "Actualizar" para modificar un producto existente.
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDao.update(editTextID.getText().toString(), new Product(editTextName.getText().toString(), editTextPrecio.getText().toString()), new OnSuccessListener<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        Toast.makeText(ProductActivity2.this, "Producto actualizado", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // Limpia la caché de la base de datos al destruir la actividad.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.terminate();
        db.clearPersistence();
    }
}
