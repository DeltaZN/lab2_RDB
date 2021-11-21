import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Book;
import model.Response;
import model.State;
import model.Task;
import repository.BookList;

public class ConsoleApp {

    int state = State.IDLE;
    String currentBy = null;
    Book currentBook = new Book();
    SocketConnector client = new SocketConnector();


    private String sendTask(Task task) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String message = gson.toJson(task);
        //System.out.println(gson.toJson(task));
        return client.sendMessage(message);
    }

    private int addBook() {
        Task task = new Task("add", this.currentBook);
        String response = this.sendTask(task);
        if (response == null) return -1;
        return 0;
    }

    private String getFindCollection(String value) {
        Task task = new Task("find", this.currentBy, value);
        return this.sendTask(task);
    }


    private void handleIdle(String input) {
        this.currentBy = null;
        this.currentBook = new Book();
        switch (input) {
            case ("1"):
                this.state = State.INPUT_NAME;
                break;
            case ("2"):
                this.state = State.FIND;
                break;
            case ("EXIT"):
                this.state = State.EXIT;
                break;
            default:
                System.out.println("Неправильные данные: Повторите ввод");
                break;
        }
    }

    private void handleInputName(String input) {
        if ("EXIT".equals(input)) {
            this.state = State.IDLE;
        } else {
            this.currentBook.setName(input);
            this.state = State.INPUT_AUTHORNAME;
        }

    }

    private void handleInputAuthorName(String input) {
        if ("EXIT".equals(input)) {
            this.state = State.IDLE;
        } else {
            this.currentBook.setAuthorName(input);
            this.state = State.INPUT_GENRE;
        }

    }

    private void handleInputGenre(String input) {
        if ("EXIT".equals(input)) {
            this.state = State.IDLE;
        } else {
            this.currentBook.setGenre(input);
            this.state = State.INPUT_PUBLISHDATE;
        }
    }

    private void handleInputPublishDate(String input) {
        if ("EXIT".equals(input)) {
            this.state = State.IDLE;
        } else {
            this.currentBook.setPublishDate(input);
            this.state = State.INPUT_ANNOTATION;
        }

    }

    private void handleInputAnnotation(String input) {
        if ("EXIT".equals(input)) {
            this.state = State.IDLE;
        } else {
            this.currentBook.setAnnotation(input);
            this.state = State.INPUT_ISBN;
        }
    }

    private void handleInputIsbn(String input) {
        if ("EXIT".equals(input)) {
            this.state = State.IDLE;
        } else {
            this.currentBook.setIsbn(input);
            int result = this.addBook();

            if (result == 0) System.out.println("Книга добавлена в коллекцию");
            else System.out.println("Книга не была добавлена в коллекцию. Произошла ошибка");

            this.state = State.IDLE;
        }
    }


    private void handleFind(String input) {
        switch (input) {
            case ("1"):
                this.state = State.FIND_VALUE;
                this.currentBy = "name";
                break;
            case ("2"):
                this.state = State.FIND_VALUE;
                this.currentBy = "authorName";
                break;
            case ("3"):
                this.state = State.FIND_VALUE;
                this.currentBy = "isbn";
                break;
            case ("4"):
                this.state = State.FIND_VALUE;
                this.currentBy = "annotation";
                break;
            case ("EXIT"):
                this.state = State.IDLE;
                break;
            default:
                System.out.println("Неправильные данные: Повторите ввод");
        }
    }

    private void handleFindValue(String input) {
        if ("EXIT".equals(input)) {
            this.state = State.IDLE;
        } else {
            String result = this.getFindCollection(input);
            System.out.println("Результаты поиска:\n");
            Gson gson = new Gson();
            try {
                BookList responseBookList = gson.fromJson(result, BookList.class);
                System.out.println(responseBookList);
            } catch (Exception e) {
                Response response = gson.fromJson(result, Response.class);
                System.out.println(response);
            }
            this.state = State.IDLE;
        }
    }

    private void handleExit(String input) {
        switch (input) {
            case ("y"):
                System.exit(0);
                break;
            case ("n"):
                this.state = State.IDLE;
                break;
            default:
                System.out.println("Повторите ввод");
                break;
        }
    }


    public void handle(String input) {
        //System.out.println("state=");
        //System.out.println(this.state);
        switch (this.state) {
            case (State.IDLE):
                this.handleIdle(input);
                break;
            case (State.INPUT_NAME):
                this.handleInputName(input);
                break;
            case (State.INPUT_AUTHORNAME):
                this.handleInputAuthorName(input);
                break;
            case (State.INPUT_GENRE):
                this.handleInputGenre(input);
                break;
            case (State.INPUT_PUBLISHDATE):
                this.handleInputPublishDate(input);
                break;
            case (State.INPUT_ANNOTATION):
                this.handleInputAnnotation(input);
                break;
            case (State.INPUT_ISBN):
                this.handleInputIsbn(input);
                break;
            case (State.FIND):
                this.handleFind(input);
                break;
            case (State.FIND_VALUE):
                this.handleFindValue(input);
                break;
            case (State.EXIT):
                this.handleExit(input);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + this.state);
        }

    }

    public void print() {
        switch (this.state) {
            case (State.IDLE):
                System.out.println("1 - добавить книгу\n2 - найти книгу\nEXIT - выход");
                break;
            case (State.INPUT_NAME):
                System.out.println("Введите название книги\nEXIT - выход в главное меню");
                break;
            case (State.INPUT_AUTHORNAME):
                System.out.println("Введите имя автора\nEXIT - выход в главное меню");
                break;
            case (State.INPUT_GENRE):
                System.out.println("Введите жанр\nEXIT - выход в главное меню");
                break;
            case (State.INPUT_PUBLISHDATE):
                System.out.println("Введите дату публикации\nEXIT - выход в главное меню");
                break;
            case (State.INPUT_ISBN):
                System.out.println("Введите isbn\nEXIT - выход в главное меню");
                break;
            case (State.INPUT_ANNOTATION):
                System.out.println("Введите аннотацию\nEXIT - выход в главное меню");
                break;
            case (State.FIND):
                System.out.println("1 - поиск по названию\n2 - поиск по имени автора\n3 - поиск по isbn\n4 - поиск по ключевым словам\nEXIT  - выход в главное меню");
                break;
            case (State.FIND_VALUE):
                switch (this.currentBy) {
                    case ("name"):
                        System.out.println("Введите название книги:" + "\nEXIT - выход в главное меню");
                        break;
                    case ("authorName"):
                        System.out.println("Введите имя автора:" + "\nEXIT - выход в главное меню");
                        break;
                    case ("isbn"):
                        System.out.println("Введите isbn книги:" + "\nEXIT - выход в главное меню");
                        break;
                    case ("annotation"):
                        System.out.println("Введите ключевые слова:" + "\nEXIT - выход в главное меню");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected by parameter: " + this.currentBy);
                }

            case (State.EXIT):
                System.out.println("Вы действительно хотите выйти? (y/n)");
                break;
            default:
                throw new IllegalStateException("Unexpected state: " + this.state);
        }
    }
}
