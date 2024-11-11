package store

import store.app.DependencyInjector

fun main() {
    val di = DependencyInjector()
    di.injectMainView()
}
