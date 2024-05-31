package david_seu.your_anime_list_backend.service;

public interface IEmailService {

    void sendEmail(String to, String subject, String text);
}
