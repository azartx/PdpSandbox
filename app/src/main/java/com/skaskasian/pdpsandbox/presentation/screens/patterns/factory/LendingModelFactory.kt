package com.skaskasian.pdpsandbox.presentation.screens.patterns.factory

import com.skaskasian.pdpsandbox.presentation.screens.patterns.model.LandingModel
import com.skaskasian.pdpsandbox.utils.cloneTimes

/* Фабричный метод. Класс, который создаёт новый инстанс LandingModel объекта
*
*  Позволяет скрыть за собой сборку объекта.
*  Даёт возможность разбивать реализации на отдельные классы, которые можно отдельно тестировать.
*  */
class LendingModelFactory {

    private var index: Int = 0
        get() {
            val result = field.takeIf { it < 10 } ?: 0
            field = if ((field + 1) >= 10) result else field + 1
            return result
        }

    private val titles = listOf(
        "Some title 1",
        "Some title 2",
        "Some title 3",
        "Some title 4",
        "Some title 5",
        "Some title 6",
        "Some title 7",
        "Some title 8",
        "Some title 9",
        "Some title 10"
    )

    private val descriptions = listOf(
        "Some description 1".cloneTimes(10),
        "Some description 2".cloneTimes(10),
        "Some description 3".cloneTimes(10),
        "Some description 4".cloneTimes(10),
        "Some description 5".cloneTimes(10),
        "Some description 6".cloneTimes(10),
        "Some description 7".cloneTimes(10),
        "Some description 8".cloneTimes(10),
        "Some description 9".cloneTimes(10),
        "Some description 10".cloneTimes(10)
    )

    private val imageUrls = listOf(
        "https://refactoring.guru/images/patterns/cards/chain-of-responsibility-mini.png",
        "https://refactoring.guru/images/patterns/cards/command-mini.png",
        "https://refactoring.guru/images/patterns/cards/iterator-mini.png",
        "https://refactoring.guru/images/patterns/cards/mediator-mini.png",
        "https://refactoring.guru/images/patterns/cards/memento-mini.png",
        "https://refactoring.guru/images/patterns/cards/observer-mini.png",
        "https://refactoring.guru/images/patterns/cards/state-mini.png",
        "https://refactoring.guru/images/patterns/cards/strategy-mini.png",
        "https://refactoring.guru/images/patterns/cards/template-method-mini.png",
        "https://refactoring.guru/images/patterns/cards/visitor-mini.png",
    )

    fun createRandomLanding(): LandingModel {
        val _index = index
        return LandingModel(
            id = _index,
            imageUrl = imageUrls[_index],
            title = titles[_index],
            description = descriptions[_index]
        )
    }
}