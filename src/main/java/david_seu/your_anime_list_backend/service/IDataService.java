package david_seu.your_anime_list_backend.service;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IDataService {

    void generateFakeData() throws IOException, URISyntaxException;

    void addAnimesFromJsonFileToDatabase() throws IOException, URISyntaxException;

    void addRecommendedAnimeAndRelatedAnime() throws IOException, URISyntaxException;

}
