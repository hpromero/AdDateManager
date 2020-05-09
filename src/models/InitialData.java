package models;

import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static models.BBDD.*;

public class InitialData {
    static ODB odb = null;

    public static void openConection(){
        if(odb == null) {
            odb = ODBFactory.open("neodatis.bbdd");
            System.out.println("Conexion Open");
        }
    }
    public static void closeConection(){
        if(odb != null) {
            odb.close();
            odb=null;
            System.out.println("Conexion close");
        }
    }


    public static void loadExampleData(){
        openConection();
            loadUsers();
            loadCustomers();
            loadDepartments();
        closeConection();
            loadDates();
    }

    private static void loadUsers(){

        odb.store(new User("91032479C", "Hector", "blorem@blorem.eu", "Admin", "7821635976", "Activo", "1234"));
        odb.store(new User("86144345F", "Rocio", "blorem@blorem.es", "User", "6464676362", "Activo", "1234"));
        odb.store(new User("86225616L", "Gala", "blorem@blorem.eu", "User", "6979584974", "Activo", "1234"));
        odb.store(new User("48840097B", "Laura", "blorem@blorem.gov", "User", "6520334566", "Activo", "1234"));
        odb.store(new User("29732088B", "Gema", "blorem@blorem.es", "User", "7518093353", "Activo", "1234"));


    }


    private static void loadCustomers(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate lt = LocalDate.parse("31-12-2018", formatter);

        odb.store(new Customer("20372347M", "Naylu Sorriva Mantilla", LocalDate.parse("31-12-2018", formatter), "Femenino", "Lavinia Aostri Miar", "6246730695", "blorem@blorem.es", "Genesis Acebedo Langarica", "7226433611", true, "Alicante", "37962", "Lorem ipsum ", "Praesentium porro atque omnis ab cupiditate debitis quae pariatur iusto totam, odio veritatis aliquid, fugiat rem soluta sit consectetur porro aut iusto autem, labore eius magni accusamus non numquam culpa quasi deleniti error fugiat, dolor et a dicta deserunt dolorum magnam obcaecati quo quaerat molestiae ullam."));
        odb.store(new Customer("70472205K", "Lana Juan Sesmilo", LocalDate.parse("09-12-2013", formatter), "Femenino", "Gumersindo Velasco Rosinos", "6591938818", "blorem@blorem.net", "Remigio Carabajo Barga", "6942783156", true, "Alicante", "9521", "Lorem ipsum SL", "Totam eveniet harum laborum illum repellendus aspernatur temporibus similique."));
        odb.store(new Customer("52765677C", "Sebastián Arrese Errasti", LocalDate.parse("18-02-2019", formatter), "Masculino", "Fabritzio Guipuzcoa Sotorrio", "7772132220", "blorem@blorem.net", "Elena Sarra Areces", "7577111097", true, "Ávila", "25367", "Lorem ipsum SA", "Dolores asperiores aliquid reiciendis assumenda labore numquam voluptas architecto totam repellendus, necessitatibus iste quo maxime repellendus rerum id cumque est provident porro."));
        odb.store(new Customer("12770262H", "Saulo Oraa Miranes", LocalDate.parse("29-04-2018", formatter), "Masculino", "Janey Candamo Reinoso", "8162402994", "blorem@blorem.es", "Lutgandy Cadaval Bien", "6776442497", true, "Albacete", "51994", "Lorem ipsum SLU", "Numquam praesentium et molestiae nisi id, tenetur beatae repellendus doloribus quas cum id, iure quo sed provident eius magnam facilis, magnam vero reprehenderit cupiditate sequi distinctio deserunt, eligendi quaerat pariatur sit aperiam harum."));
        odb.store(new Customer("91896855X", "Yamileth Ferrer Ledo", LocalDate.parse("10-07-2019", formatter), "Femenino", "Agustina Canto Mendieta", "889335543", "blorem@blorem.net", "Domingo Redeño Mirones", "6367175537", true, "Melilla", "37021", "Lorem ipsum ", "Architecto est magni?"));
        odb.store(new Customer("61249991W", "Sabas Largacha Rano", LocalDate.parse("12-09-2010", formatter), "Femenino", "Velvi Belaustegui Montano", "8225877231", "blorem@blorem.es", "Iraima Vigil Bara", "8318570197", true, "Segovia", "48821", "Lorem ipsum SLU", "Debitis quos veritatis optio, eius modi odio incidunt pariatur ratione hic, ea fuga exercitationem eaque?"));
        odb.store(new Customer("70367303E", "Omar Bouceda Escudero", LocalDate.parse("29-05-2015", formatter), "Masculino", "Lenni Labra Muñana", "6439421147", "blorem@blorem.eu", "Laura Telleria Lacalle", "708650315", true, "Murcia", "34092", "Lorem ipsum ", "Quos voluptate odit voluptatem consequatur voluptas porro excepturi doloremque voluptatum, voluptatibus esse illo labore eligendi, accusantium atque assumenda beatae nostrum soluta provident excepturi incidunt repudiandae pariatur, ab deleniti eaque maxime, possimus adipisci molestias perspiciatis minima illum vel deleniti sit."));
        odb.store(new Customer("83509748Z", "Karla Tolosa Atauri", LocalDate.parse("08-05-2012", formatter), "Femenino", "Mariel Aspe Capitillo", "6113535835", "blorem@blorem.gov", "Mabel Guadiana Abecia", "7453985062", true, "Soria", "36046", "Lorem ipsum dolor ", "Voluptatem facere nam sint porro libero neque quas nemo sed suscipit commodi?"));
        odb.store(new Customer("92352958T", "Electra Toro Echaitia", LocalDate.parse("29-12-2012", formatter), "Femenino", "Annet Tineo Recio", "7940648860", "blorem@blorem.gov", "Fiorela Barreneche Ovejas", "7263090705", true, "Zamora", "6490", "Lorem ipsum SA", "Unde obcaecati perspiciatis illum dicta quas reiciendis, odio dolores illo optio libero nobis eos vero laboriosam enim?"));
        odb.store(new Customer("49369916W", "Karime Monagray Santo", LocalDate.parse("02-12-2014", formatter), "Femenino", "Uriel Polentinos Güell", "8781270307", "blorem@blorem.org", "Stella Ergueta Vazquez", "7533167546", true, "Tarragona", "17055", "Lorem ipsum dolor SA", "Maiores ducimus amet nam vel rerum reiciendis, vel magnam fugiat natus, autem inventore doloremque ex, quas accusantium ratione quasi dignissimos dolor sapiente dolorem iusto voluptatum autem aliquid?"));
        odb.store(new Customer("25539896Y", "Yamila Parrondo Fraile", LocalDate.parse("22-04-2016", formatter), "Femenino", "Zahid Sampil Izar", "6557870060", "blorem@blorem.com", "Kerim Diaz Ponte", "6879179670", true, "Almería", "28079", "Lorem ipsum CB", "Molestias suscipit hic doloremque enim perspiciatis fugiat magni quo, error sunt iste quisquam animi voluptate, ea exercitationem distinctio, veniam molestiae doloremque fugit?"));
        odb.store(new Customer("69195871A", "Hollman Cal Afaba", LocalDate.parse("20-03-2013", formatter), "Masculino", "Williams Santos San", "6351242187", "blorem@blorem.com", "Víctor Ruisuarez Usategui", "8443718456", true, "Burgos", "45523", "Lorem ipsum dolor SA", "Impedit fuga voluptatem?"));
        odb.store(new Customer("41391798E", "Devyaní Pose Perana", LocalDate.parse("27-12-2017", formatter), "Masculino", "Silvano Padriña Zaldo", "7318259017", "blorem@blorem.net", "Farid Cubas Monzon", "7154597822", true, "Cáceres", "19271", "Lorem ipsum CB", "Hic voluptates asperiores quam, facere possimus atque laborum nihil iusto dolorum eos et, ea id repellat minus veniam, earum doloremque molestias doloribus iste explicabo quae velit quam nisi voluptatibus."));


    }

    private static void loadDepartments() {

        odb.store(new Department(1,"Logopedia","48840097B","Laura","29732088B","Gema"));
        odb.store(new Department( 2,"Psicología","29732088B","Gema","48840097B","Laura"));
        odb.store(new Department(3,"Fisioterapia","86225616L","Gala","86144345F","Rocio"));
    }

    private static void loadDates() {

        saveDates(new Date("", "20372347M", "Naylu Sorriva Mantilla", 1, "Logopedia", LocalDate.now().plusDays(0), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "61249991W", "Sabas Largacha Rano", 1, "Logopedia", LocalDate.now().plusDays(1), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "49369916W", "Karime Monagray Santo", 1, "Logopedia", LocalDate.now().plusDays(2), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "52765677C", "Sebastián Arrese Errasti", 1, "Logopedia", LocalDate.now().plusDays(3), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "25539896Y", "Yamila Parrondo Fraile",1, "Logopedia", LocalDate.now().plusDays(4), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "69195871A", "Hollman Cal Afaba", 1, "Logopedia", LocalDate.now().plusDays(5), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "41391798E", "Devyaní Pose Perana", 1, "Logopedia", LocalDate.now().plusDays(6), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));

        saveDates(new Date("", "20372347M", "Naylu Sorriva Mantilla", 2, "Psicología", LocalDate.now().plusDays(1), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "61249991W", "Sabas Largacha Rano", 2, "Psicología", LocalDate.now().plusDays(2), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "49369916W", "Karime Monagray Santo", 2, "Psicología", LocalDate.now().plusDays(3), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "52765677C", "Sebastián Arrese Errasti", 2, "Psicología", LocalDate.now().plusDays(4), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "25539896Y", "Yamila Parrondo Fraile",2, "Psicología", LocalDate.now().plusDays(5), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "69195871A", "Hollman Cal Afaba", 2, "Psicología", LocalDate.now().plusDays(6), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "41391798E", "Devyaní Pose Perana", 2, "Psicología", LocalDate.now().plusDays(7), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));

        saveDates(new Date("", "20372347M", "Naylu Sorriva Mantilla", 3, "Fisioterapia", LocalDate.now().plusDays(3), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "61249991W", "Sabas Largacha Rano", 3, "Fisioterapia", LocalDate.now().plusDays(4), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "49369916W", "Karime Monagray Santo", 3, "Fisioterapia", LocalDate.now().plusDays(5), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "52765677C", "Sebastián Arrese Errasti", 3, "Fisioterapia", LocalDate.now().plusDays(6), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "25539896Y", "Yamila Parrondo Fraile",3, "Fisioterapia", LocalDate.now().plusDays(7), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "69195871A", "Hollman Cal Afaba", 3, "Fisioterapia", LocalDate.now().plusDays(8), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));
        saveDates(new Date("", "41391798E", "Devyaní Pose Perana", 3, "Fisioterapia", LocalDate.now().plusDays(9), LocalTime.of(13, 00, 00), LocalTime.of(13, 55, 00), false));

        saveDates(new Date("Lunes", "20372347M", "Naylu Sorriva Mantilla",3, "Fisioterapia", LocalDate.now(), LocalTime.of(8, 00, 00), LocalTime.of(8, 55, 00), true));
        saveDates(new Date("Lunes", "70472205K", "Lana Juan Sesmilo",3, "Fisioterapia", LocalDate.now(), LocalTime.of(9, 00, 00), LocalTime.of(9, 55, 00), true));
        saveDates(new Date("Lunes", "52765677C", "Sebastián Arrese Errasti",3, "Fisioterapia", LocalDate.now(), LocalTime.of(10, 00, 00), LocalTime.of(10, 55, 00), true));
        saveDates(new Date("Lunes", "12770262H", "Saulo Oraa Miranes",3, "Fisioterapia", LocalDate.now(), LocalTime.of(11, 00, 00), LocalTime.of(11, 55, 00), true));
        saveDates(new Date("Lunes", "91896855X", "Yamileth Ferrer Ledo",3, "Fisioterapia", LocalDate.now(), LocalTime.of(16, 00, 00), LocalTime.of(16, 55, 00), true));
        saveDates(new Date("Lunes", "61249991W", "Sabas Largacha Rano",3, "Fisioterapia", LocalDate.now(), LocalTime.of(14, 00, 00), LocalTime.of(14, 55, 00), true));
        saveDates(new Date("Lunes", "70367303E", "Omar Bouceda Escudero",3, "Fisioterapia", LocalDate.now(), LocalTime.of(17, 00, 00), LocalTime.of(17, 55, 00), true));
        saveDates(new Date("Lunes", "83509748Z", "Karla Tolosa Atauri",3, "Fisioterapia", LocalDate.now(), LocalTime.of(19, 00, 00), LocalTime.of(19, 55, 00), true));
        saveDates(new Date("Lunes", "92352958T", "Electra Toro Echaitia",3, "Fisioterapia", LocalDate.now(), LocalTime.of(20, 00, 00), LocalTime.of(20, 55, 00), true));
        saveDates(new Date("Lunes", "49369916W", "Karime Monagray Santo",3, "Fisioterapia", LocalDate.now(), LocalTime.of(21, 00, 00), LocalTime.of(21, 55, 00), true));
        saveDates(new Date("Martes", "25539896Y", "Yamila Parrondo Fraile",3, "Fisioterapia", LocalDate.now(), LocalTime.of(9, 00, 00), LocalTime.of(9, 55, 00), true));
        saveDates(new Date("Martes", "69195871A", "Hollman Cal Afaba",3, "Fisioterapia", LocalDate.now(), LocalTime.of(10, 00, 00), LocalTime.of(10, 55, 00), true));
        saveDates(new Date("Martes", "20372347M", "Naylu Sorriva Mantilla",3, "Fisioterapia", LocalDate.now(), LocalTime.of(11, 00, 00), LocalTime.of(11, 55, 00), true));
        saveDates(new Date("Martes", "70472205K", "Lana Juan Sesmilo",3, "Fisioterapia", LocalDate.now(), LocalTime.of(15, 00, 00), LocalTime.of(15, 55, 00), true));
        saveDates(new Date("Martes", "52765677C", "Sebastián Arrese Errasti",3, "Fisioterapia", LocalDate.now(), LocalTime.of(14, 00, 00), LocalTime.of(14, 55, 00), true));
        saveDates(new Date("Martes", "12770262H", "Saulo Oraa Miranes",3, "Fisioterapia", LocalDate.now(), LocalTime.of(18, 00, 00), LocalTime.of(18, 55, 00), true));
        saveDates(new Date("Martes", "91896855X", "Yamileth Ferrer Ledo",3, "Fisioterapia", LocalDate.now(), LocalTime.of(19, 00, 00), LocalTime.of(19, 55, 00), true));
        saveDates(new Date("Martes", "61249991W", "Sabas Largacha Rano",3, "Fisioterapia", LocalDate.now(), LocalTime.of(20, 00, 00), LocalTime.of(20, 55, 00), true));
        saveDates(new Date("Martes", "70367303E", "Omar Bouceda Escudero",3, "Fisioterapia", LocalDate.now(), LocalTime.of(21, 00, 00), LocalTime.of(21, 55, 00), true));
        saveDates(new Date("Miércoles", "83509748Z", "Karla Tolosa Atauri",3, "Fisioterapia", LocalDate.now(), LocalTime.of(10, 00, 00), LocalTime.of(10, 55, 00), true));
        saveDates(new Date("Miércoles", "92352958T", "Electra Toro Echaitia",3, "Fisioterapia", LocalDate.now(), LocalTime.of(11, 00, 00), LocalTime.of(11, 55, 00), true));
        saveDates(new Date("Miércoles", "49369916W", "Karime Monagray Santo",3, "Fisioterapia", LocalDate.now(), LocalTime.of(12, 00, 00), LocalTime.of(12, 55, 00), true));
        saveDates(new Date("Miércoles", "25539896Y", "Yamila Parrondo Fraile",3, "Fisioterapia", LocalDate.now(), LocalTime.of(14, 00, 00), LocalTime.of(14, 55, 00), true));
        saveDates(new Date("Miércoles", "69195871A", "Hollman Cal Afaba",3, "Fisioterapia", LocalDate.now(), LocalTime.of(18, 00, 00), LocalTime.of(18, 55, 00), true));
        saveDates(new Date("Miércoles", "20372347M", "Naylu Sorriva Mantilla",3, "Fisioterapia", LocalDate.now(), LocalTime.of(19, 00, 00), LocalTime.of(19, 55, 00), true));
        saveDates(new Date("Miércoles", "70472205K", "Lana Juan Sesmilo",3, "Fisioterapia", LocalDate.now(), LocalTime.of(20, 00, 00), LocalTime.of(20, 55, 00), true));
        saveDates(new Date("Jueves", "52765677C", "Sebastián Arrese Errasti",3, "Fisioterapia", LocalDate.now(), LocalTime.of(8, 00, 00), LocalTime.of(8, 55, 00), true));
        saveDates(new Date("Jueves", "12770262H", "Saulo Oraa Miranes",3, "Fisioterapia", LocalDate.now(), LocalTime.of(9, 00, 00), LocalTime.of(9, 55, 00), true));
        saveDates(new Date("Jueves", "91896855X", "Yamileth Ferrer Ledo",3, "Fisioterapia", LocalDate.now(), LocalTime.of(10, 00, 00), LocalTime.of(10, 55, 00), true));
        saveDates(new Date("Jueves", "61249991W", "Sabas Largacha Rano",3, "Fisioterapia", LocalDate.now(), LocalTime.of(11, 00, 00), LocalTime.of(11, 55, 00), true));
        saveDates(new Date("Jueves", "83509748Z", "Karla Tolosa Atauri",3, "Fisioterapia", LocalDate.now(), LocalTime.of(16, 00, 00), LocalTime.of(16, 55, 00), true));
        saveDates(new Date("Jueves", "92352958T", "Electra Toro Echaitia",3, "Fisioterapia", LocalDate.now(), LocalTime.of(12, 00, 00), LocalTime.of(12, 55, 00), true));
        saveDates(new Date("Jueves", "49369916W", "Karime Monagray Santo",3, "Fisioterapia", LocalDate.now(), LocalTime.of(17, 00, 00), LocalTime.of(17, 55, 00), true));
        saveDates(new Date("Jueves", "25539896Y", "Yamila Parrondo Fraile",3, "Fisioterapia", LocalDate.now(), LocalTime.of(18, 00, 00), LocalTime.of(18, 55, 00), true));
        saveDates(new Date("Jueves", "69195871A", "Hollman Cal Afaba",3, "Fisioterapia", LocalDate.now(), LocalTime.of(19, 00, 00), LocalTime.of(19, 55, 00), true));
        saveDates(new Date("Jueves", "20372347M", "Naylu Sorriva Mantilla",3, "Fisioterapia", LocalDate.now(), LocalTime.of(20, 00, 00), LocalTime.of(20, 55, 00), true));
        saveDates(new Date("Viernes", "70472205K", "Lana Juan Sesmilo",3, "Fisioterapia", LocalDate.now(), LocalTime.of(9, 00, 00), LocalTime.of(9, 55, 00), true));
        saveDates(new Date("Viernes", "52765677C", "Sebastián Arrese Errasti",3, "Fisioterapia", LocalDate.now(), LocalTime.of(10, 00, 00), LocalTime.of(10, 55, 00), true));
        saveDates(new Date("Viernes", "12770262H", "Saulo Oraa Miranes",3, "Fisioterapia", LocalDate.now(), LocalTime.of(11, 00, 00), LocalTime.of(11, 55, 00), true));
        saveDates(new Date("Viernes", "61249991W", "Sabas Largacha Rano",3, "Fisioterapia", LocalDate.now(), LocalTime.of(14, 00, 00), LocalTime.of(14, 55, 00), true));
        saveDates(new Date("Jueves", "20372347M", "Naylu Sorriva Mantilla",1, "Logopedia", LocalDate.now(), LocalTime.of(8, 00, 00), LocalTime.of(8, 55, 00), true));
        saveDates(new Date("Jueves", "70472205K", "Lana Juan Sesmilo",1, "Logopedia", LocalDate.now(), LocalTime.of(9, 00, 00), LocalTime.of(9, 55, 00), true));
        saveDates(new Date("Jueves", "52765677C", "Sebastián Arrese Errasti",1, "Logopedia", LocalDate.now(), LocalTime.of(10, 00, 00), LocalTime.of(10, 55, 00), true));
        saveDates(new Date("Jueves", "12770262H", "Saulo Oraa Miranes",1, "Logopedia", LocalDate.now(), LocalTime.of(11, 00, 00), LocalTime.of(11, 55, 00), true));
        saveDates(new Date("Jueves", "91896855X", "Yamileth Ferrer Ledo",1, "Logopedia", LocalDate.now(), LocalTime.of(16, 00, 00), LocalTime.of(16, 55, 00), true));
        saveDates(new Date("Jueves", "61249991W", "Sabas Largacha Rano",1, "Logopedia", LocalDate.now(), LocalTime.of(14, 00, 00), LocalTime.of(14, 55, 00), true));
        saveDates(new Date("Jueves", "70367303E", "Omar Bouceda Escudero",1, "Logopedia", LocalDate.now(), LocalTime.of(17, 00, 00), LocalTime.of(17, 55, 00), true));
        saveDates(new Date("Jueves", "83509748Z", "Karla Tolosa Atauri",1, "Logopedia", LocalDate.now(), LocalTime.of(19, 00, 00), LocalTime.of(19, 55, 00), true));
        saveDates(new Date("Jueves", "92352958T", "Electra Toro Echaitia",1, "Logopedia", LocalDate.now(), LocalTime.of(20, 00, 00), LocalTime.of(20, 55, 00), true));
        saveDates(new Date("Jueves", "49369916W", "Karime Monagray Santo",1, "Logopedia", LocalDate.now(), LocalTime.of(21, 00, 00), LocalTime.of(21, 55, 00), true));

        saveDates(new Date("Viernes", "25539896Y", "Yamila Parrondo Fraile",1, "Logopedia", LocalDate.now(), LocalTime.of(9, 00, 00), LocalTime.of(9, 55, 00), true));
        saveDates(new Date("Viernes", "69195871A", "Hollman Cal Afaba",1, "Logopedia", LocalDate.now(), LocalTime.of(10, 00, 00), LocalTime.of(10, 55, 00), true));
        saveDates(new Date("Viernes", "20372347M", "Naylu Sorriva Mantilla",1, "Logopedia", LocalDate.now(), LocalTime.of(8, 00, 00), LocalTime.of(8, 55, 00), true));
        saveDates(new Date("Viernes", "70472205K", "Lana Juan Sesmilo",1, "Logopedia", LocalDate.now(), LocalTime.of(15, 00, 00), LocalTime.of(15, 55, 00), true));
        saveDates(new Date("Viernes", "52765677C", "Sebastián Arrese Errasti",1, "Logopedia", LocalDate.now(), LocalTime.of(14, 00, 00), LocalTime.of(14, 55, 00), true));
        saveDates(new Date("Viernes", "12770262H", "Saulo Oraa Miranes",1, "Logopedia", LocalDate.now(), LocalTime.of(16, 00, 00), LocalTime.of(17, 55, 00), true));
        saveDates(new Date("Viernes", "91896855X", "Yamileth Ferrer Ledo",1, "Logopedia", LocalDate.now(), LocalTime.of(19, 00, 00), LocalTime.of(19, 55, 00), true));
        saveDates(new Date("Viernes", "61249991W", "Sabas Largacha Rano",1, "Logopedia", LocalDate.now(), LocalTime.of(20, 00, 00), LocalTime.of(20, 55, 00), true));
        saveDates(new Date("Viernes", "70367303E", "Omar Bouceda Escudero",1, "Logopedia", LocalDate.now(), LocalTime.of(21, 00, 00), LocalTime.of(21, 55, 00), true));

        saveDates(new Date("Lunes", "83509748Z", "Karla Tolosa Atauri",1, "Logopedia", LocalDate.now(), LocalTime.of(10, 00, 00), LocalTime.of(10, 55, 00), true));
        saveDates(new Date("Lunes", "92352958T", "Electra Toro Echaitia",1, "Logopedia", LocalDate.now(), LocalTime.of(11, 00, 00), LocalTime.of(11, 55, 00), true));
        saveDates(new Date("Lunes", "49369916W", "Karime Monagray Santo",1, "Logopedia", LocalDate.now(), LocalTime.of(12, 00, 00), LocalTime.of(12, 55, 00), true));
        saveDates(new Date("Lunes", "25539896Y", "Yamila Parrondo Fraile",1, "Logopedia", LocalDate.now(), LocalTime.of(14, 00, 00), LocalTime.of(14, 55, 00), true));
        saveDates(new Date("Lunes", "69195871A", "Hollman Cal Afaba",1, "Logopedia", LocalDate.now(), LocalTime.of(18, 00, 00), LocalTime.of(18, 55, 00), true));
        saveDates(new Date("Lunes", "20372347M", "Naylu Sorriva Mantilla",1, "Logopedia", LocalDate.now(), LocalTime.of(19, 00, 00), LocalTime.of(19, 55, 00), true));
        saveDates(new Date("Lunes", "70472205K", "Lana Juan Sesmilo",1, "Logopedia", LocalDate.now(), LocalTime.of(20, 00, 00), LocalTime.of(20, 55, 00), true));
        saveDates(new Date("Miércoles", "52765677C", "Sebastián Arrese Errasti",1, "Logopedia", LocalDate.now(), LocalTime.of(8, 00, 00), LocalTime.of(8, 55, 00), true));
        saveDates(new Date("Miércoles", "12770262H", "Saulo Oraa Miranes",1, "Logopedia", LocalDate.now(), LocalTime.of(9, 00, 00), LocalTime.of(9, 55, 00), true));
        saveDates(new Date("Miércoles", "91896855X", "Yamileth Ferrer Ledo",1, "Logopedia", LocalDate.now(), LocalTime.of(10, 00, 00), LocalTime.of(10, 55, 00), true));
        saveDates(new Date("Miércoles", "61249991W", "Sabas Largacha Rano",1, "Logopedia", LocalDate.now(), LocalTime.of(11, 00, 00), LocalTime.of(11, 55, 00), true));
        saveDates(new Date("Miércoles", "70367303E", "Omar Bouceda Escudero",1, "Logopedia", LocalDate.now(), LocalTime.of(15, 00, 00), LocalTime.of(15, 55, 00), true));
        saveDates(new Date("Miércoles", "83509748Z", "Karla Tolosa Atauri",1, "Logopedia", LocalDate.now(), LocalTime.of(16, 00, 00), LocalTime.of(16, 55, 00), true));
        saveDates(new Date("Miércoles", "92352958T", "Electra Toro Echaitia",1, "Logopedia", LocalDate.now(), LocalTime.of(12, 00, 00), LocalTime.of(12, 55, 00), true));
        saveDates(new Date("Miércoles", "49369916W", "Karime Monagray Santo",1, "Logopedia", LocalDate.now(), LocalTime.of(17, 00, 00), LocalTime.of(17, 55, 00), true));
        saveDates(new Date("Miércoles", "25539896Y", "Yamila Parrondo Fraile",1, "Logopedia", LocalDate.now(), LocalTime.of(18, 00, 00), LocalTime.of(18, 55, 00), true));
        saveDates(new Date("Miércoles", "69195871A", "Hollman Cal Afaba",1, "Logopedia", LocalDate.now(), LocalTime.of(19, 00, 00), LocalTime.of(19, 55, 00), true));
        saveDates(new Date("Miércoles", "20372347M", "Naylu Sorriva Mantilla",1, "Logopedia", LocalDate.now(), LocalTime.of(20, 00, 00), LocalTime.of(20, 55, 00), true));
        saveDates(new Date("Martes", "70472205K", "Lana Juan Sesmilo",1, "Logopedia", LocalDate.now(), LocalTime.of(9, 00, 00), LocalTime.of(9, 55, 00), true));
        saveDates(new Date("Martes", "52765677C", "Sebastián Arrese Errasti",1, "Logopedia", LocalDate.now(), LocalTime.of(10, 00, 00), LocalTime.of(10, 55, 00), true));
        saveDates(new Date("Martes", "12770262H", "Saulo Oraa Miranes",1, "Logopedia", LocalDate.now(), LocalTime.of(11, 00, 00), LocalTime.of(11, 55, 00), true));
        saveDates(new Date("Martes", "91896855X", "Yamileth Ferrer Ledo",1, "Logopedia", LocalDate.now(), LocalTime.of(20, 00, 00), LocalTime.of(20, 55, 00), true));
        saveDates(new Date("Martes", "61249991W", "Sabas Largacha Rano",1, "Logopedia", LocalDate.now(), LocalTime.of(14, 00, 00), LocalTime.of(14, 55, 00), true));
        saveDates(new Date("Lunes", "52765677C", "Sebastián Arrese Errasti",2, "Psicología", LocalDate.now(), LocalTime.of(10, 00, 00), LocalTime.of(10, 55, 00), true));
        saveDates(new Date("Lunes", "12770262H", "Saulo Oraa Miranes",2, "Psicología", LocalDate.now(), LocalTime.of(11, 00, 00), LocalTime.of(11, 55, 00), true));
        saveDates(new Date("Lunes", "91896855X", "Yamileth Ferrer Ledo",2, "Psicología", LocalDate.now(), LocalTime.of(16, 00, 00), LocalTime.of(16, 55, 00), true));
        saveDates(new Date("Lunes", "61249991W", "Sabas Largacha Rano",2, "Psicología", LocalDate.now(), LocalTime.of(14, 00, 00), LocalTime.of(14, 55, 00), true));
        saveDates(new Date("Lunes", "70367303E", "Omar Bouceda Escudero",2, "Psicología", LocalDate.now(), LocalTime.of(17, 00, 00), LocalTime.of(17, 55, 00), true));
        saveDates(new Date("Lunes", "83509748Z", "Karla Tolosa Atauri",2, "Psicología", LocalDate.now(), LocalTime.of(19, 00, 00), LocalTime.of(19, 55, 00), true));
        saveDates(new Date("Lunes", "92352958T", "Electra Toro Echaitia",2, "Psicología", LocalDate.now(), LocalTime.of(20, 00, 00), LocalTime.of(20, 55, 00), true));
        saveDates(new Date("Lunes", "49369916W", "Karime Monagray Santo",2, "Psicología", LocalDate.now(), LocalTime.of(21, 00, 00), LocalTime.of(21, 55, 00), true));
        saveDates(new Date("Martes", "25539896Y", "Yamila Parrondo Fraile",2, "Psicología", LocalDate.now(), LocalTime.of(22, 00, 00), LocalTime.of(22, 55, 00), true));
        saveDates(new Date("Martes", "69195871A", "Hollman Cal Afaba",2, "Psicología", LocalDate.now(), LocalTime.of(10, 00, 00), LocalTime.of(10, 55, 00), true));
        saveDates(new Date("Martes", "20372347M", "Naylu Sorriva Mantilla",2, "Psicología", LocalDate.now(), LocalTime.of(11, 00, 00), LocalTime.of(11, 55, 00), true));
        saveDates(new Date("Martes", "70472205K", "Lana Juan Sesmilo",2, "Psicología", LocalDate.now(), LocalTime.of(16, 00, 00), LocalTime.of(16, 55, 00), true));
        saveDates(new Date("Martes", "52765677C", "Sebastián Arrese Errasti",2, "Psicología", LocalDate.now(), LocalTime.of(14, 00, 00), LocalTime.of(14, 55, 00), true));
        saveDates(new Date("Martes", "12770262H", "Saulo Oraa Miranes",2, "Psicología", LocalDate.now(), LocalTime.of(15, 00, 00), LocalTime.of(15, 55, 00), true));
        saveDates(new Date("Martes", "91896855X", "Yamileth Ferrer Ledo",2, "Psicología", LocalDate.now(), LocalTime.of(19, 00, 00), LocalTime.of(19, 55, 00), true));
        saveDates(new Date("Martes", "61249991W", "Sabas Largacha Rano",2, "Psicología", LocalDate.now(), LocalTime.of(20, 00, 00), LocalTime.of(20, 55, 00), true));
        saveDates(new Date("Martes", "70367303E", "Omar Bouceda Escudero",2, "Psicología", LocalDate.now(), LocalTime.of(21, 00, 00), LocalTime.of(21, 55, 00), true));
        saveDates(new Date("Miércoles", "83509748Z", "Karla Tolosa Atauri",2, "Psicología", LocalDate.now(), LocalTime.of(17, 00, 00), LocalTime.of(17, 55, 00), true));
        saveDates(new Date("Miércoles", "92352958T", "Electra Toro Echaitia",2, "Psicología", LocalDate.now(), LocalTime.of(11, 00, 00), LocalTime.of(11, 55, 00), true));
        saveDates(new Date("Miércoles", "49369916W", "Karime Monagray Santo",2, "Psicología", LocalDate.now(), LocalTime.of(21, 00, 00), LocalTime.of(21, 55, 00), true));
        saveDates(new Date("Miércoles", "25539896Y", "Yamila Parrondo Fraile",2, "Psicología", LocalDate.now(), LocalTime.of(22, 00, 00), LocalTime.of(22, 55, 00), true));
        saveDates(new Date("Miércoles", "69195871A", "Hollman Cal Afaba",2, "Psicología", LocalDate.now(), LocalTime.of(18, 00, 00), LocalTime.of(18, 55, 00), true));
        saveDates(new Date("Miércoles", "20372347M", "Naylu Sorriva Mantilla",2, "Psicología", LocalDate.now(), LocalTime.of(19, 00, 00), LocalTime.of(19, 55, 00), true));
        saveDates(new Date("Miércoles", "70472205K", "Lana Juan Sesmilo",2, "Psicología", LocalDate.now(), LocalTime.of(20, 00, 00), LocalTime.of(20, 55, 00), true));
        saveDates(new Date("Jueves", "61249991W", "Sabas Largacha Rano",2, "Psicología", LocalDate.now(), LocalTime.of(11, 00, 00), LocalTime.of(11, 55, 00), true));
        saveDates(new Date("Jueves", "83509748Z", "Karla Tolosa Atauri",2, "Psicología", LocalDate.now(), LocalTime.of(16, 00, 00), LocalTime.of(16, 55, 00), true));
        saveDates(new Date("Jueves", "92352958T", "Electra Toro Echaitia",2, "Psicología", LocalDate.now(), LocalTime.of(12, 00, 00), LocalTime.of(12, 55, 00), true));
        saveDates(new Date("Jueves", "49369916W", "Karime Monagray Santo",2, "Psicología", LocalDate.now(), LocalTime.of(17, 00, 00), LocalTime.of(17, 55, 00), true));
        saveDates(new Date("Jueves", "25539896Y", "Yamila Parrondo Fraile",2, "Psicología", LocalDate.now(), LocalTime.of(18, 00, 00), LocalTime.of(18, 55, 00), true));
        saveDates(new Date("Jueves", "69195871A", "Hollman Cal Afaba",2, "Psicología", LocalDate.now(), LocalTime.of(19, 00, 00), LocalTime.of(19, 55, 00), true));
        saveDates(new Date("Jueves", "20372347M", "Naylu Sorriva Mantilla",2, "Psicología", LocalDate.now(), LocalTime.of(20, 00, 00), LocalTime.of(20, 55, 00), true));
        saveDates(new Date("Viernes", "70472205K", "Lana Juan Sesmilo",2, "Psicología", LocalDate.now(), LocalTime.of(9, 00, 00), LocalTime.of(9, 55, 00), true));
        saveDates(new Date("Viernes", "52765677C", "Sebastián Arrese Errasti",2, "Psicología", LocalDate.now(), LocalTime.of(10, 00, 00), LocalTime.of(10, 55, 00), true));
        saveDates(new Date("Viernes", "12770262H", "Saulo Oraa Miranes",2, "Psicología", LocalDate.now(), LocalTime.of(11, 00, 00), LocalTime.of(11, 55, 00), true));
        saveDates(new Date("Viernes", "61249991W", "Sabas Largacha Rano",2, "Psicología", LocalDate.now(), LocalTime.of(14, 00, 00), LocalTime.of(14, 55, 00), true));

    }

    private static void saveDates(Date date){
        date.setId();
        openConection();
        odb.store(date);
        closeConection();
    }
}
