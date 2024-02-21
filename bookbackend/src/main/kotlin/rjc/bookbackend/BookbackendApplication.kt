package rjc.bookbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BookbackendApplication

fun main(args: Array<String>) {
	runApplication<BookbackendApplication>(*args)
}
