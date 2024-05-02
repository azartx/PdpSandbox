package com.skaskasian.pdpsandbox.presentation.screens.patterns.model

// Паттерн билдер. Для классов в котлине уже не так актуален как для классов в джаве, т.к. в котлине для полей
// конструктора класса (и не только) можно устанавливать значения по умолчанию, что даёт возможность во время вызова
// конструктора устанавливать значения только в нужные поля, либо не устанавливать значения конструктора вовсе

// Ручная реализация билдера в котлине так же может быть актуальна в случае если класс нужно настроить как-то по особому
// во время его создания
data class LandingModel(
    val id: Int,
    val imageUrl: String,
    val title: String,
    val description: String
) {

    /* Реализация паттерна Builder */
    class Builder {

        private var _id: Int = 11
        private var _imageUrl: String = ""
        private var _title: String = ""
        private var _description: String = ""

        fun setId(id: Int): Builder {
            _id = id
            return this
        }

        fun setImageUrl(imageUrl: String): Builder {
            _imageUrl = imageUrl
            return this
        }

        fun setTitle(title: String): Builder {
            _title = title
            return this
        }

        fun setDescription(description: String): Builder {
            _description = description
            return this
        }

        fun build(): LandingModel {
            return LandingModel(_id, _imageUrl, _title, _description)
        }
    }
}