package com.practicum.playlistmaker.creator

import com.practicum.playlistmaker.data.dto.TrackDto

class Storage {
    private val listTracks = generateTracks()

    private fun generateTracks(): List<TrackDto> {
        val songs = listOf(
            "Мумий Троль" to "Владивосток 2000",
            "Звери" to "Районы-кварталы",
            "Кино" to "Группа крови",
            "ДДТ" to "Что такое осень",
            "Сплин" to "Выхода нет",
            "Би-2" to "Полковнику никто не пишет",
            "Ария" to "Потерянный рай",
            "Наутилус Помпилиус" to "Прогулки по воде",
            "Агата Кристи" to "Как на войне",
            "Чайф" to "Аргентина – Ямайка 5:0",
            "Любэ" to "Конь",
            "Григорий Лепс" to "Рюмка водки",
            "Ирина Аллегрова" to "Угонщица",
            "Алла Пугачёва" to "Миллион алых роз",
            "Филипп Киркоров" to "Цвет настроения синий",
            "Валерий Меладзе" to "Сэра",
            "Вера Брежнева" to "Любовь спасет мир",
            "Максим" to "Знаешь ли ты",
            "Винтаж" to "Ева",
            "Нюша" to "Выбирать чудо",
            "Егор Крид" to "Самая лучшая",
            "Тимати" to "GQ",
            "Мот" to "Капкан",
            "Руки Вверх" to "Крошка моя",
            "Дискотека Авария" to "Новогодняя",
            "Отпетые мошенники" to "Люби меня, люби",
            "Иванушки International" to "Тучи",
            "Руки Вверх" to "Чужие губы",
            "Краски" to "Оранжевое солнце",
            "Фабрика" to "Про любовь",
            "Стрелки" to "Ты бросил меня",
            "Тату" to "Я сошла с ума",
            "Ленинград" to "Экспонат",
            "Serebro" to "Мама Люба",
            "Время и Стекло" to "Имя 505",
            "Грибы" to "Тает лёд",
            "Монеточка" to "Каждый раз",
            "Хаски" to "Пуля-дура",
            "Oxxxymiron" to "Город под подошвой",
            "Баста" to "Сансара",
            "Скриптонит" to "Дом с нормальными явлениями",
            "Фараон" to "5 минут назад",
            "Элджей" to "Розовое вино",
            "Feduk" to "Моряк",
            "Моргенштерн" to "Cadillac",
            "Клава Кока" to "Покинула чат",
            "Zivert" to "Life",
            "Artik & Asti" to "Грустный дэнс",
            "Дима Билан" to "Невозможное возможно",
            "Полина Гагарина" to "Кукушка"
        )
        return songs.mapIndexed { index, (artist, track) ->
            val duration = when (index) {
                0 -> 158000 // Владивосток 2000
                else -> 180000 + (index * 1000) % 120000 // от 3:00 до 5:00
            }
            TrackDto(
                id = (index + 1).toLong(),
                trackName = track,
                artistName = artist,
                trackTime = formatTime(duration),
                image = "https://example.com/image${index + 1}.jpg",
                favorite = false,
                playlistId = 0L
            )
        }
    }

    private fun formatTime(millis: Int): String {
        val minutes = millis / 60000
        val seconds = (millis % 60000) / 1000
        return String.format("%02d:%02d", minutes, seconds)
    }

    fun search(request: String): List<TrackDto> {
        val result = listTracks.filter {
            it.trackName
                .lowercase()
                .contains(request.lowercase()) ||
            it.artistName
                .lowercase()
                .contains(request.lowercase())
        }
        return result
    }
}