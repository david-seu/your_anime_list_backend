package david_seu.your_anime_list_backend.exception;

public class InvalidAnimeException extends RuntimeException{
    public InvalidAnimeException(String message) {
        super(message);
    }
}
