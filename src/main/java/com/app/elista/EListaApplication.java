package com.app.elista;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EListaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EListaApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner mappingDemo(AppCompanyRepository appCompanyRepository,
//                                         TermsRepository termsRepository,
//                                         GroupsRepository groupsRepository,
//                                         GroupsTermsRepository groupsTermsRepository
//    ) {
//
//        return args -> {
//
//            AppCompany appCompany = new AppCompany(
//                    "GoldenMMA",
//                    "asd@wp.pl",
//                    "asd",
//                    "asd",
//                    "asd",
//                    Offer.FIFTY,
//                    "12332",
//                    AppCompanyRole.USER);
//
//            appCompanyRepository.save(appCompany);
//
//            List<Teams> groupsList = Arrays.asList(
//                    new Teams(
//                            "Grupa 1",
//                            "aleksander Kowalski",
//                            "jeziorna 6",
//                            "1021",
//                            "2211",
//                            (short) 20,
//                            (short) 31,
//                            (short) 980,
//                            "#123",
//                            true,
//                            "desc",
//                            appCompany
//                    ),
//                    new Teams(
//                            "Grupa 1",
//                            "aleksander Kowalski",
//                            "jeziorna 6",
//                            "1021",
//                            "2211",
//                            (short) 20,
//                            (short) 31,
//                            (short) 980,
//                            "#123",
//                            true,
//                            "desc",
//                            appCompany
//                    ), new Teams(
//                            "Grupa 1",
//                            "aleksander Kowalski",
//                            "jeziorna 6",
//                            "1021",
//                            "2211",
//                            (short) 20,
//                            (short) 31,
//                            (short) 980,
//                            "#123",
//                            true,
//                            "desc",
//                            appCompany
//                    ), new Teams(
//                            "Grupa 1",
//                            "aleksander Kowalski",
//                            "jeziorna 6",
//                            "1021",
//                            "2211",
//                            (short) 20,
//                            (short) 31,
//                            (short) 980,
//                            "#123",
//                            true,
//                            "desc",
//                            appCompany
//                    )
//            );
//
//            groupsRepository.saveAll(groupsList);
//
//            List<Terms> termsList = Arrays.asList(
//                    new Terms(
//                            "poniedziałek",
//                            "20 00"
//
//                    ), new Terms(
//                            "Wtorek",
//                            "20 00"
//
//                    ), new Terms(
//                            "sroda",
//                            "20 00"
//
//                    ), new Terms(
//                            "niedizela",
//                            "20 00"
//
//                    ), new Terms(
//                            "sobota",
//                            "20 00"
//                    )
//            );
//
//            termsRepository.saveAll(termsList);
//
//            List<GroupsTerms> groupsTerms = Arrays.asList(
//                    new GroupsTerms(groupsList.get(0), termsList.get(1)),
//                    new GroupsTerms(groupsList.get(0), termsList.get(2)),
//                    new GroupsTerms(groupsList.get(0), termsList.get(3)),
//                    new GroupsTerms(groupsList.get(1), termsList.get(2)),
//                    new GroupsTerms(groupsList.get(2), termsList.get(3)),
//                    new GroupsTerms(groupsList.get(3), termsList.get(0)),
//                    new GroupsTerms(groupsList.get(3), termsList.get(3)),
//                    new GroupsTerms(groupsList.get(3), termsList.get(2))
//            );
//
//            groupsTermsRepository.saveAll(groupsTerms);
//
//            Groups groups = new Groups("Java 101", "John Doe", "123456");
//
//            Groups groups = new Groups("Java 101", "John Doe", "123456");
//
//            Groups groups = new Groups("Java 101", "John Doe", "123456");
//
//            Groups groups = new Groups("Java 101", "John Doe", "123456");
//
//
//            appCompanyRepository.
//            bookRepository.save(book);
//
//            pageRepository.save(new Page(1, "Introduction contents", "Introduction", book));
//            pageRepository.save(new Page(65, "Java 8 contents", "Java 8", book));
//            pageRepository.save(new Page(95, "Concurrency contents", "Concurrency", book));
//        };
//    }
}
