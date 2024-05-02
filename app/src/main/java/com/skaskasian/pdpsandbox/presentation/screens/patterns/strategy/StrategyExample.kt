package com.skaskasian.pdpsandbox.presentation.screens.patterns.strategy

/*
 * Паттерн стратегия
 *
 * Определяет разное поведение для определенных ситуаций.
 *
 * В случае ниже - если пэйджер при загрузке данных получил налл, то стратегия решает как отреагировать на это
 * В зависимости от выбранной стратегии из пэйджера вернётся null, данные начнут грузиться сначала или упадет ошибка
 *
 * Паттерн может помочь если есть ситуации, на которые нужно по разному реагировать
 *
 */

class Article

class ArticlesPager {

    private lateinit var strategy: OnItemsEndStrategy

    fun init(strategy: OnItemsEndStrategy) {
        this.strategy = strategy
    }

    fun loadNext(): Article? {
        val article = SampleArticlesDataSource.getNextArticle()

        return when (strategy) {
            OnItemsEndStrategy.RETURN_FIRST_ITEM -> article ?: SampleArticlesDataSource.getArticle()
            OnItemsEndStrategy.STOP_LOADING -> article
            OnItemsEndStrategy.THROW -> article ?: throw ArticlesEndedException()
        }
    }

    enum class OnItemsEndStrategy {
        RETURN_FIRST_ITEM, STOP_LOADING, THROW
    }
}

object SampleArticlesDataSource {

    fun getNextArticle(): Article? {
        return Article()
    }

    fun getArticle(): Article {
        return Article()
    }
}

class ArticlesEndedException : Exception()