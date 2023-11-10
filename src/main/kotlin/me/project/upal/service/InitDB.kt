package me.project.upal.service

import com.opencsv.CSVReader
import jakarta.annotation.PostConstruct
import me.project.upal.entity.Country
import me.project.upal.entity.Member
import me.project.upal.entity.Role
import me.project.upal.entity.Tag
import me.project.upal.repository.*
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.io.FileReader

@Profile("local")
@Component
@Transactional
class InitDB(
        private val memberRepository: MemberRepository,
        private val roleRepository: RoleRepository,
        private val countryRepository: CountryRepository,
        private val tagRepository: TagRepository,
        private val interestTagRepository: InterestTagRepository,
        private val interestCountryRepository: InterestCountryRepository,
        private val interestRepository: InterestRepository) {

    @PostConstruct
    fun init() {
        createCountries()
        createTags()
        createMembers()
    }

    fun createCountries() {
        val csvReader: CSVReader = CSVReader(FileReader("src/main/resources/countries.csv"))
        val countries: MutableList<Country> = mutableListOf();
        csvReader.readNext()
        csvReader.forEach { line -> countries.add(Country(line[0], line[3])) }
        countryRepository.saveAll(countries)
    }

    fun createTags() {
        val tags: List<String> = listOf("korean", "kpop", "friend")
        tagRepository.saveAll(tags.map { tag -> Tag(tag) }.toList())
    }


    fun createMembers() {

        var roleUser = Role("ROLE_USER")
        var roleAdmin = Role("ROLE_ADMIN")
        roleUser = roleRepository.save(roleUser)
        roleAdmin = roleRepository.save(roleAdmin)


        for (i: Int in 0..99) {
            val member = Member(
                    email = "email${i}",
                    password = BCryptPasswordEncoder().encode("123123"),
                    phoneNumber = "phoneNumber${i}",
                    nickName = "nickName${i}",
                    age = i,
                    country = countryRepository.findById("KR").orElseThrow()
            )
            member.roles.add(roleUser)
            memberRepository.save(member)
        }
    }
}