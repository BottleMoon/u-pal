package me.project.upal

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UPalApplication

fun main(args: Array<String>) {
    runApplication<UPalApplication>(*args)
}
