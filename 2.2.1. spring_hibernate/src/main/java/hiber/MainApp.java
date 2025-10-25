package hiber;

import hiber.config.AppConfig;
import hiber.model.User;
import hiber.model.Car;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        Car car1 = new Car("Toyota Camry", 7);
        Car car2 = new Car("Honda Civic", 9);
        Car car3 = new Car("BMW X5", 5);
        Car car4 = new Car("Audi A4", 8);

        userService.add(new User("User1", "Lastname1", "user1@mail.ru", car1));
        userService.add(new User("User2", "Lastname2", "user2@mail.ru", car2));
        userService.add(new User("User3", "Lastname3", "user3@mail.ru", car3));
        userService.add(new User("User4", "Lastname4", "user4@mail.ru", car4));

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            if (user.getCar() != null) {
                System.out.println("Car = " + user.getCar().getModel() + " series " + user.getCar().getSeries());
            }
            System.out.println();
        }

        System.out.println("=== Поиск пользователя по машине ===");
        Optional<User> userWithCar = userService.getUserByCar("BMW X5", 5);
        if (userWithCar.isPresent()) {
            User user = userWithCar.get();
            System.out.println("Пользователь с BMW X5 series 5:");
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("Car = " + user.getCar().getModel() + " series " + user.getCar().getSeries());
        } else {
            System.out.println("Пользователь с указанной машиной не найден");
        }

        context.close();
    }
}
