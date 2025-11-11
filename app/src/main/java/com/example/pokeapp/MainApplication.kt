package com.example.pokeapp // 1. Pacote corrigido (raiz)

import android.app.Application
// 2. Imports corrigidos (para as classes DI que salvamos)
import com.example.pokeapp.di.AppContainer
import com.example.pokeapp.di.AppContainerInterface

/**
 * A classe Application do Android, usada para inicializar
 * o contêiner de dependências uma única vez.
 */
class MainApplication : Application() {

    // 3. Renomeei a variável para 'container' (como no AppNavigation)
    lateinit var container: AppContainerInterface
        private set

    override fun onCreate() {
        super.onCreate()
        // 4. Inicializa o contêiner de dependências
        container = AppContainer(this)
    }
}