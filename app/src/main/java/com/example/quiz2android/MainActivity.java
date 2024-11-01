package com.example.quiz2android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    /**
     *
     *  Esta actividad actúa como la pantalla principal de la aplicación
     *  y sirve de puente hacia la funcionalidad de ProductActivity2,
     *  donde se realiza la gestión de productos.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProductActivity2.class);
                startActivity(intent);
            }
        });

    }
}

/**
 *
 * Explicación Detallada:
 * Clase Principal (MainActivity):
 *
 * MainActivity extiende AppCompatActivity, lo cual permite utilizar características
 * de compatibilidad hacia atrás en dispositivos Android más antiguos.
 *
 * Método onCreate:
 * Este método es el punto de entrada de la actividad. Aquí se configura la vista
 * y se definen las acciones iniciales para esta pantalla.
 *
 *
 * Configuración de Pantalla Completa:
 * EdgeToEdge.enable(this); habilita una vista de pantalla completa.
 * Utilizamos ViewCompat.setOnApplyWindowInsetsListener para ajustar los márgenes
 * del contenido en función de los "insets" del sistema, como la barra de estado
 * y la barra de navegación. Esto asegura que el contenido se muestre adecuadamente
 * sin ser cubierto por las barras del sistema.
 *
 *
 * Botón de Inicio de Sesión (loginButton):
 * Se obtiene la referencia al botón mediante findViewById(R.id.loginButton).
 * Este botón tiene un OnClickListener que responde a los clics del usuario.
 *
 *
 * Listener del Botón:
 * Cuando el usuario hace clic en el botón, se crea un Intent que abre ProductActivity2.
 * Intent intent = new Intent(MainActivity.this, ProductActivity2.class); especifica
 * que ProductActivity2 será la próxima actividad en abrirse.
 * startActivity(intent); inicia la nueva actividad y lleva al usuario a la interfaz
 * de gestión de productos.
 * ¿Por qué es útil esta pantalla?
 * MainActivity funciona como una pantalla de inicio o menú principal. En este caso,
 * al hacer clic en el botón de inicio de sesión, se redirige al usuario hacia
 * ProductActivity2, que tiene las opciones para gestionar productos (crear, leer,
 * actualizar y eliminar). Esta separación ayuda a organizar la navegación de la
 * aplicación y mejora la experiencia de usuario.
 *
 * ¿Te gustaría más detalles sobre alguna parte específica o algún consejo adicional
 * para tu explicación en clase?
 *
 *
 */