package david_seu.your_anime_list_backend.exception;

public class UsernameAlreadyExistsException extends RuntimeException{

        public UsernameAlreadyExistsException(String message){
            super(message);
        }
}
