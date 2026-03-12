package com.practicum.playlistmaker.creator

import com.practicum.playlistmaker.data.dto.TrackDto

class Storage {
    private val listTracks = listOf(
        TrackDto(
            trackName = "Владивосток 2000",
            artistName = "Мумий Троль",
            trackTimeMillis = 158000
        ),
        TrackDto(
            trackName = "Группа крови",
            artistName = "Кино",
            trackTimeMillis = 283000
        ),
        TrackDto(
            trackName = "Не смотри назад",
            artistName = "Ария",
            trackTimeMillis = 312000
        ),
        TrackDto(
            trackName = "Звезда по имени Солнце",
            artistName = "Кино",
            trackTimeMillis = 225000
        ),
        TrackDto(
            trackName = "Лондон",
            artistName = "Аквариум",
            trackTimeMillis = 272000
        ),
        TrackDto(
            trackName = "На заре",
            artistName = "Альянс",
            trackTimeMillis = 230000
        ),
        TrackDto(
            trackName = "Перемен",
            artistName = "Кино",
            trackTimeMillis = 296000
        ),
        TrackDto(
            trackName = "Розовый фламинго",
            artistName = "Сплин",
            trackTimeMillis = 195000
        ),
        TrackDto(
            trackName = "Танцевать",
            artistName = "Мельница",
            trackTimeMillis = 222000
        ),
        TrackDto(
            trackName = "Чёрный бумер",
            artistName = "Серега",
            trackTimeMillis = 241000
        ),
        TrackDto(
            trackName = "Дождь для нас",
            artistName = "Би-2",
            trackTimeMillis = 254000
        ),
        TrackDto(
            trackName = "Последний герой",
            artistName = "Кино",
            trackTimeMillis = 237000
        ),
        TrackDto(
            trackName = "Скованные одной цепью",
            artistName = "Наутилус Помпилиус",
            trackTimeMillis = 318000
        ),
        TrackDto(
            trackName = "Я свободен",
            artistName = "Кипелов",
            trackTimeMillis = 245000
        ),
        TrackDto(
            trackName = "Прогулка романтика",
            artistName = "Сплин",
            trackTimeMillis = 228000
        ),
        TrackDto(
            trackName = "Любовь – это всё",
            artistName = "Звери",
            trackTimeMillis = 198000
        ),
        TrackDto(
            trackName = "Конь",
            artistName = "Любэ",
            trackTimeMillis = 267000
        ),
        TrackDto(
            trackName = "Что такое осень",
            artistName = "ДДТ",
            trackTimeMillis = 276000
        ),
        TrackDto(
            trackName = "Владимирский централ",
            artistName = "Михаил Круг",
            trackTimeMillis = 294000
        ),
        TrackDto(
            trackName = "Комета",
            artistName = "Ночные снайперы",
            trackTimeMillis = 213000
        ),

        // Иностранные треки
        TrackDto(
            trackName = "Bohemian Rhapsody",
            artistName = "Queen",
            trackTimeMillis = 354000
        ),
        TrackDto(
            trackName = "Hotel California",
            artistName = "Eagles",
            trackTimeMillis = 391000
        ),
        TrackDto(
            trackName = "Sweet Child O'Mine",
            artistName = "Guns N' Roses",
            trackTimeMillis = 356000
        ),
        TrackDto(
            trackName = "Smells Like Teen Spirit",
            artistName = "Nirvana",
            trackTimeMillis = 301000
        ),
        TrackDto(
            trackName = "Billie Jean",
            artistName = "Michael Jackson",
            trackTimeMillis = 294000
        ),
        TrackDto(
            trackName = "Like a Prayer",
            artistName = "Madonna",
            trackTimeMillis = 319000
        ),
        TrackDto(
            trackName = "Yesterday",
            artistName = "The Beatles",
            trackTimeMillis = 125000
        ),
        TrackDto(
            trackName = "Stairway to Heaven",
            artistName = "Led Zeppelin",
            trackTimeMillis = 482000
        ),
        TrackDto(
            trackName = "Imagine",
            artistName = "John Lennon",
            trackTimeMillis = 183000
        ),
        TrackDto(
            trackName = "Shape of You",
            artistName = "Ed Sheeran",
            trackTimeMillis = 233000
        ),
        TrackDto(
            trackName = "Blinding Lights",
            artistName = "The Weeknd",
            trackTimeMillis = 200000
        ),
        TrackDto(
            trackName = "Bad Guy",
            artistName = "Billie Eilish",
            trackTimeMillis = 194000
        ),
        TrackDto(
            trackName = "Uptown Funk",
            artistName = "Mark Ronson ft. Bruno Mars",
            trackTimeMillis = 270000
        ),
        TrackDto(
            trackName = "Despacito",
            artistName = "Luis Fonsi",
            trackTimeMillis = 229000
        ),
        TrackDto(
            trackName = "Rolling in the Deep",
            artistName = "Adele",
            trackTimeMillis = 228000
        ),
        TrackDto(
            trackName = "Happy",
            artistName = "Pharrell Williams",
            trackTimeMillis = 233000
        ),
        TrackDto(
            trackName = "Someone Like You",
            artistName = "Adele",
            trackTimeMillis = 285000
        ),
        TrackDto(
            trackName = "Radioactive",
            artistName = "Imagine Dragons",
            trackTimeMillis = 186000
        ),
        TrackDto(
            trackName = "Counting Stars",
            artistName = "OneRepublic",
            trackTimeMillis = 257000
        ),
        TrackDto(
            trackName = "Havana",
            artistName = "Camila Cabello",
            trackTimeMillis = 217000
        ),
        TrackDto(
            trackName = "Shallow",
            artistName = "Lady Gaga, Bradley Cooper",
            trackTimeMillis = 215000
        ),
        TrackDto(
            trackName = "Old Town Road",
            artistName = "Lil Nas X",
            trackTimeMillis = 157000
        ),
        TrackDto(
            trackName = "Don't Start Now",
            artistName = "Dua Lipa",
            trackTimeMillis = 183000
        ),
        TrackDto(
            trackName = "Watermelon Sugar",
            artistName = "Harry Styles",
            trackTimeMillis = 174000
        ),
        TrackDto(
            trackName = "Dynamite",
            artistName = "BTS",
            trackTimeMillis = 199000
        ),
        TrackDto(
            trackName = "Levitating",
            artistName = "Dua Lipa",
            trackTimeMillis = 203000
        ),
        TrackDto(
            trackName = "Stay",
            artistName = "The Kid LAROI, Justin Bieber",
            trackTimeMillis = 141000
        ),
        TrackDto(
            trackName = "Good 4 U",
            artistName = "Olivia Rodrigo",
            trackTimeMillis = 178000
        ),
        TrackDto(
            trackName = "Butter",
            artistName = "BTS",
            trackTimeMillis = 165000
        ),
        TrackDto(
            trackName = "Industry Baby",
            artistName = "Lil Nas X, Jack Harlow",
            trackTimeMillis = 212000
        ),

        // Ещё российские
        TrackDto(
            trackName = "Пачка сигарет",
            artistName = "Кино",
            trackTimeMillis = 268000
        ),
        TrackDto(
            trackName = "Трава у дома",
            artistName = "Земляне",
            trackTimeMillis = 245000
        ),
        TrackDto(
            trackName = "Кукла колдуна",
            artistName = "Король и Шут",
            trackTimeMillis = 234000
        ),
        TrackDto(
            trackName = "Мой друг",
            artistName = "Машина Времени",
            trackTimeMillis = 287000
        ),
        TrackDto(
            trackName = "Орлёнок",
            artistName = "Любэ",
            trackTimeMillis = 200000
        ),
        TrackDto(
            trackName = "На север",
            artistName = "Мумий Троль",
            trackTimeMillis = 223000
        ),
        TrackDto(
            trackName = "Город",
            artistName = "Каста",
            trackTimeMillis = 256000
        ),
        TrackDto(
            trackName = "Районы-кварталы",
            artistName = "Звери",
            trackTimeMillis = 189000
        ),
        TrackDto(
            trackName = "Нева",
            artistName = "Аквариум",
            trackTimeMillis = 275000
        ),
        TrackDto(
            trackName = "Студент",
            artistName = "Король и Шут",
            trackTimeMillis = 198000
        ),

        // Ещё иностранные
        TrackDto(
            trackName = "Thriller",
            artistName = "Michael Jackson",
            trackTimeMillis = 357000
        ),
        TrackDto(
            trackName = "Back in Black",
            artistName = "AC/DC",
            trackTimeMillis = 255000
        ),
        TrackDto(
            trackName = "Wonderwall",
            artistName = "Oasis",
            trackTimeMillis = 258000
        ),
        TrackDto(
            trackName = "Sweet Dreams",
            artistName = "Eurythmics",
            trackTimeMillis = 216000
        ),
        TrackDto(
            trackName = "Every Breath You Take",
            artistName = "The Police",
            trackTimeMillis = 253000
        ),
        TrackDto(
            trackName = "Like a Rolling Stone",
            artistName = "Bob Dylan",
            trackTimeMillis = 369000
        ),
        TrackDto(
            trackName = "I Will Always Love You",
            artistName = "Whitney Houston",
            trackTimeMillis = 273000
        ),
        TrackDto(
            trackName = "Hey Jude",
            artistName = "The Beatles",
            trackTimeMillis = 431000
        ),
        TrackDto(
            trackName = "Sweet Home Alabama",
            artistName = "Lynyrd Skynyrd",
            trackTimeMillis = 285000
        ),
        TrackDto(
            trackName = "Lose Yourself",
            artistName = "Eminem",
            trackTimeMillis = 326000
        ),
        TrackDto(
            trackName = "Poker Face",
            artistName = "Lady Gaga",
            trackTimeMillis = 237000
        ),
        TrackDto(
            trackName = "Viva La Vida",
            artistName = "Coldplay",
            trackTimeMillis = 242000
        ),
        TrackDto(
            trackName = "Firework",
            artistName = "Katy Perry",
            trackTimeMillis = 227000
        ),
        TrackDto(
            trackName = "We Found Love",
            artistName = "Rihanna",
            trackTimeMillis = 215000
        ),
        TrackDto(
            trackName = "Gangnam Style",
            artistName = "PSY",
            trackTimeMillis = 219000
        ),
        TrackDto(
            trackName = "Waka Waka",
            artistName = "Shakira",
            trackTimeMillis = 202000
        ),
        TrackDto(
            trackName = "Sorry",
            artistName = "Justin Bieber",
            trackTimeMillis = 200000
        ),
        TrackDto(
            trackName = "Closer",
            artistName = "The Chainsmokers",
            trackTimeMillis = 244000
        ),
        TrackDto(
            trackName = "See You Again",
            artistName = "Wiz Khalifa",
            trackTimeMillis = 229000
        ),
        TrackDto(
            trackName = "Hello",
            artistName = "Adele",
            trackTimeMillis = 295000
        ),
        TrackDto(
            trackName = "7 Rings",
            artistName = "Ariana Grande",
            trackTimeMillis = 178000
        ),
        TrackDto(
            trackName = "Senorita",
            artistName = "Shawn Mendes, Camila Cabello",
            trackTimeMillis = 191000
        ),
        TrackDto(
            trackName = "Bad Romance",
            artistName = "Lady Gaga",
            trackTimeMillis = 295000
        ),
        TrackDto(
            trackName = "Just Dance",
            artistName = "Lady Gaga",
            trackTimeMillis = 242000
        ),
        TrackDto(
            trackName = "Paparazzi",
            artistName = "Lady Gaga",
            trackTimeMillis = 211000
        ),
        TrackDto(
            trackName = "Love Story",
            artistName = "Taylor Swift",
            trackTimeMillis = 235000
        ),
        TrackDto(
            trackName = "Shake It Off",
            artistName = "Taylor Swift",
            trackTimeMillis = 219000
        ),
        TrackDto(
            trackName = "Blank Space",
            artistName = "Taylor Swift",
            trackTimeMillis = 231000
        ),
        TrackDto(
            trackName = "Roar",
            artistName = "Katy Perry",
            trackTimeMillis = 224000
        ),
        TrackDto(
            trackName = "Dark Horse",
            artistName = "Katy Perry",
            trackTimeMillis = 215000
        ),
        TrackDto(
            trackName = "California Gurls",
            artistName = "Katy Perry",
            trackTimeMillis = 234000
        ),
        TrackDto(
            trackName = "Fireflies",
            artistName = "Owl City",
            trackTimeMillis = 228000
        ),
        TrackDto(
            trackName = "Titanium",
            artistName = "David Guetta",
            trackTimeMillis = 245000
        ),
        TrackDto(
            trackName = "Wake Me Up",
            artistName = "Avicii",
            trackTimeMillis = 247000
        ),
        TrackDto(
            trackName = "Levels",
            artistName = "Avicii",
            trackTimeMillis = 342000
        ),
        TrackDto(
            trackName = "Animals",
            artistName = "Martin Garrix",
            trackTimeMillis = 192000
        ),
        TrackDto(
            trackName = "Tsunami",
            artistName = "DVBBS",
            trackTimeMillis = 203000
        ),
        TrackDto(
            trackName = "Summer",
            artistName = "Calvin Harris",
            trackTimeMillis = 223000
        ),
        TrackDto(
            trackName = "We Will Rock You",
            artistName = "Queen",
            trackTimeMillis = 122000
        ),
        TrackDto(
            trackName = "Another One Bites the Dust",
            artistName = "Queen",
            trackTimeMillis = 215000
        ),
        TrackDto(
            trackName = "Don't Stop Me Now",
            artistName = "Queen",
            trackTimeMillis = 209000
        )
    )

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