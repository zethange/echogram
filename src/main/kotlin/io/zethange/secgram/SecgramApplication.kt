package io.zethange.secgram

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@SpringBootApplication
class SecgramApplication

fun main(args: Array<String>) {
    runApplication<SecgramApplication>(*args)
}
