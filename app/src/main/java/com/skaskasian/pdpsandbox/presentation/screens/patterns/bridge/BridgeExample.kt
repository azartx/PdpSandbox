package com.skaskasian.pdpsandbox.presentation.screens.patterns.bridge

/*
* Паттерн мост
*
* Есть две не зависящие друг от друга реализации классов. Зависимости внутри имеются только на интерфейсы
* Таким образом, реализации классов могут меняться независимо друг от друга.
*
* */

interface Bus {

    fun drive()
    fun stop()
    fun loadPeople()
    fun unloadPeople()
}
interface Driver {

    fun getBehindTheWheel(bus: Bus)
    fun goOut()
    fun doThingsWithPassengers()
}

class BusImpl(private val driver: Driver) : Bus {

    override fun drive() {
        driver.getBehindTheWheel(this)
    }

    override fun stop() {
        driver.goOut()
    }

    override fun loadPeople() {
        driver.doThingsWithPassengers()
    }

    override fun unloadPeople() {
        driver.doThingsWithPassengers()
    }
}
class DriverImpl : Driver {

    private var bus: Bus? = null

    override fun getBehindTheWheel(bus: Bus) {
        this.bus = bus
    }

    override fun goOut() {
        bus = null
    }

    override fun doThingsWithPassengers() {
        bus?.loadPeople()
        // ...some actions
        bus?.unloadPeople()
    }
}

object DiContainer {

    fun getDriver() = DriverImpl()
    fun getBus() = BusImpl(getDriver())
}

fun initializeBusStationThings() {
    val bus = DiContainer.getBus()
    val driver = DiContainer.getDriver()

    driver.getBehindTheWheel(bus)

    driver.doThingsWithPassengers()

    driver.goOut()
}