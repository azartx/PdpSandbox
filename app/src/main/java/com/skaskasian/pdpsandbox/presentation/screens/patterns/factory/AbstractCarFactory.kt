package com.skaskasian.pdpsandbox.presentation.screens.patterns.factory

/** Абстрактная фабрика */
/** Не используется в общем коде, только для примера */

/*
* Создаёт разные экземпляры интерфейса Car в зависимости от конкретной реализации CarFactory
*
* */

// ************ Интерфейс и конкретные реализации Car ********
interface Car
class Audi : Car
class Honda : Car
class Ford : Car

// *************************************************************

// ************ Интерфейс и конкретные реализации абстракнтой фабрики CarFactory ********
interface CarFactory {

    fun createCar(): Car
}

class AudiFactory : CarFactory {

    override fun createCar(): Car {
        return Audi()
    }
}

class HondaFactory : CarFactory {

    override fun createCar(): Car {
        return Honda()
    }
}

class FordFactory : CarFactory {

    override fun createCar(): Car {
        return Ford()
    }
}

// ***************************************************************************

class CarNotExistsException(msg: String) : Exception(msg)


// **************** Фабричная функция, отдающая нужную реализацию класса Car по номеру машины *****************************
fun getCar(number: Int): Car {
    return when (number) {
        1 -> AudiFactory()
        2 -> HondaFactory()
        3 -> FordFactory()
        else -> throw CarNotExistsException("Car with number $number is not exists.")
    }.createCar()
}

