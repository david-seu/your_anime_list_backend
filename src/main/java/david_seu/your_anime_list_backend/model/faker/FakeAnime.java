package david_seu.your_anime_list_backend.model.faker;

import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FakeAnime extends AbstractProvider<BaseProviders> {

    private List<String> ANIME_TITLES;

    public FakeAnime(BaseProviders faker) {
        super(faker);
        try {
            ANIME_TITLES = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("anime_titles.txt").toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public String nextAnimeTitle() {
        return ANIME_TITLES.get(faker.random().nextInt(ANIME_TITLES.size()));
    }


}
