package david_seu.your_anime_list_backend.exception;

public class EmailAlreadyRegisteredException extends RuntimeException{

            public EmailAlreadyRegisteredException(String message){
                super(message);
            }
}
