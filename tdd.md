# TDD - #31 Favorite Funktion implementieren
## Vorgehen
## 1. Anforderungen an das neue Feature lesen:
> Jeder User soll ein Favorite Video festlegen können, welches in der DB gespeichert wird und auf seinem Profil im Frontend angezeigt wird
## 2. Benötigte Funktionen für Autocomplete definieren (aber leer lassen!)

### VideoService.kt
```kotlin
    ...

    fun favoriteVideo() {

    }

    fun unfavoriteVideo() {
        
    }
```
## 3. Erste Tests für ein Feature schreiben
### VideoServiceTest.kt
```kotlin
@Nested
    @SpringBootTest
    inner class FavoriteTests(
        @param:Autowired val subject: VideoService,
        @param:Autowired val userService: UserService,
        @param:Autowired val config: GteeConfig
    ) {
        lateinit var user: User
        lateinit var video: VideoDto
        lateinit var jwtUtil: JwtUtil
        lateinit var token: String
        @BeforeEach
        fun setup() {
            val username = "testy"
            val pw = "pw"
            jwtUtil = JwtUtil()
            user = userService.register(username, pw)
            token = userService.login(username, pw)
            video = subject.uploadVideo(UploadVideoDto(
                user.username,
                "",
                MockMultipartFile("test.mp4", byteArrayOf(1.toByte())),
            ), token)
        }

        @AfterEach
        fun cleanUp() {
            Files.walk(Path(config.dir))
                .sorted(Comparator.reverseOrder()) // delete children first
                .forEach(Files::deleteIfExists)
        }

        @Test
        fun `User can favorite a video`() {
            subject.favoriteVideo(video.id, token)
            
            val actual = userService.getUser(user.username).favorite
            assertEquals(video, actual)
        }
    }
```
## 4. Funktion so weit anpassen, dass alle Tests grün werden
### VideoService.kt
```kotlin
    ...

    fun favoriteVideo(id: Int, token: String) {
        val username = jwtUtil.getUsernameFromToken(token)
        val user = userService.getUser(username)
        val video = repository.findById(id).getOrNull()
        userRepository.save(user.copy(favorite = video))
    }
```
## 5. Neue Tests schreiben & Funktion erweitern
Bis das Feature fertig ist, werden neue Tests geschrieben. 
Während dem Entwickeln habe ich IntelliJ's Auto-Rerun Tests Feature benutzt.